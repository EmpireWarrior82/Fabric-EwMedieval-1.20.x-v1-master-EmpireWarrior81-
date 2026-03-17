package net.empire.ewmedieval.gui.forge;

import net.empire.ewmedieval.block.entity.ForgeBlockEntity;
import net.empire.ewmedieval.gui.ModScreenHandlers;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class ForgeScreenHandler extends ScreenHandler {
    private final Inventory inventory;            // the block's inventory (6 slots)
    private final PropertyDelegate propertyDelegate;
    private final ForgeBlockEntity blockEntity;

    private static final int BLOCK_INV_SIZE = 6;
    private static final int INPUT_START = 0;
    private static final int INPUT_END = 4; // exclusive: slots 0..3 are inputs
    private static final int OUTPUT_SLOT = 4;
    private static final int FUEL_SLOT = ForgeBlockEntity.FUEL_SLOT; // should be 5

    // Client-side constructor (PacketByteBuf). IMPORTANT: use size 4 here.
    public ForgeScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4)); // <<< 4, niet 2
    }

    // Server-side constructor (blockEntity passed in)
    public ForgeScreenHandler(int syncId, PlayerInventory playerInventory,
                              @Nullable BlockEntity blockEntity, PropertyDelegate delegate) {
        super(ModScreenHandlers.FORGE_SCREEN_SCREEN_HANDLER, syncId);

        // Veilig: als blockEntity null is (zeldzaam op client), gebruik tijdelijke inventory
        Inventory inv;
        if (blockEntity instanceof Inventory) {
            inv = (Inventory) blockEntity;
        } else {
            inv = new SimpleInventory(BLOCK_INV_SIZE);
        }

        // controleer juiste grootte (6 slots)
        checkSize(inv, BLOCK_INV_SIZE);

        this.inventory = inv;
        this.inventory.onOpen(playerInventory.player);

        this.propertyDelegate = delegate;
        addProperties(this.propertyDelegate);

        this.blockEntity = blockEntity instanceof ForgeBlockEntity ? (ForgeBlockEntity) blockEntity : null;

        // --- block slots ---
        // inputs 0..3
        this.addSlot(new Slot(this.inventory, 0, 14, 16));
        this.addSlot(new Slot(this.inventory, 1, 32, 16));
        this.addSlot(new Slot(this.inventory, 2, 50, 16));
        this.addSlot(new Slot(this.inventory, 3, 68, 16));

        // output slot (no inserting into output)
        this.addSlot(new Slot(this.inventory, OUTPUT_SLOT, 120, 17) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        // fuel slot (alleen items die in FuelRegistry staan mogen erin)
        this.addSlot(new Slot(this.inventory, FUEL_SLOT, 41, 55) {
            @Override
            public boolean canInsert(ItemStack stack) {
                Integer fuelTicks = FuelRegistry.INSTANCE.get(stack.getItem());
                return fuelTicks != null && fuelTicks > 0;
            }
        });

        // player inv & hotbar
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public boolean isCrafting() {
        return this.propertyDelegate.get(0) > 0;
    }

    // schaal voor het craft-pijltje (bijv. breedte 26 px)
    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 26;
        return maxProgress > 0 && progress > 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // brandstatus
    public boolean isBurning() {
        return this.propertyDelegate.get(2) > 0;
    }

    // schaal voor de vlam (bijv. hoogte 14 px)
    public int getFuelScaled() {
        int burn = this.propertyDelegate.get(2);
        int total = this.propertyDelegate.get(3);
        int flameHeight = 14;
        return total > 0 && burn > 0 ? burn * flameHeight / total : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            int playerInvStart = BLOCK_INV_SIZE;
            int playerInvEnd = playerInvStart + 27; // main inventory
            int hotbarStart = playerInvEnd;
            int hotbarEnd = hotbarStart + 9; // hotbar

            if (invSlot < BLOCK_INV_SIZE) {
                // van block naar speler
                if (!this.insertItem(originalStack, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // van speler naar block: probeer fuel eerst, anders inputs
                Integer fuelTicks = FuelRegistry.INSTANCE.get(originalStack.getItem());
                if (fuelTicks != null && fuelTicks > 0) {
                    // probeer fuel-slot
                    if (!this.insertItem(originalStack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        // als fuel-slot vol is, probeer inputs
                        if (!this.insertItem(originalStack, INPUT_START, INPUT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else {
                    // niet-brandstof: probeer input slots
                    if (!this.insertItem(originalStack, INPUT_START, INPUT_END, false)) {
                        // als niet in inputs past: verplaats tussen player inv <-> hotbar
                        if (invSlot >= playerInvStart && invSlot < playerInvEnd) {
                            if (!this.insertItem(originalStack, hotbarStart, hotbarEnd, false)) return ItemStack.EMPTY;
                        } else if (invSlot >= hotbarStart && invSlot < hotbarEnd) {
                            if (!this.insertItem(originalStack, playerInvStart, playerInvEnd, false)) return ItemStack.EMPTY;
                        } else return ItemStack.EMPTY;
                    }
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, originalStack);
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
