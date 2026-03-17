package net.empire.ewmedieval.gui.earlyforge;

import net.empire.ewmedieval.block.entity.EarlyForgeBlockEntity;
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

public class EarlyForgeScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final EarlyForgeBlockEntity blockEntity;

    private static final int BLOCK_INV_SIZE = 6;
    private static final int INPUT_START = 0;
    private static final int INPUT_END = 4;
    private static final int OUTPUT_SLOT = 4;
    private static final int FUEL_SLOT = EarlyForgeBlockEntity.FUEL_SLOT;


    public EarlyForgeScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4)); // < 4, not 2
    }

    public EarlyForgeScreenHandler(int syncId, PlayerInventory playerInventory,
                              @Nullable BlockEntity blockEntity, PropertyDelegate delegate) {
        super(ModScreenHandlers.EARLY_FORGE_SCREEN_SCREEN_HANDLER, syncId);

        Inventory inv = blockEntity instanceof Inventory i ? i : new SimpleInventory(BLOCK_INV_SIZE);

        checkSize(inv, BLOCK_INV_SIZE);

        this.inventory = inv;
        this.inventory.onOpen(playerInventory.player);

        this.propertyDelegate = delegate;
        addProperties(this.propertyDelegate);

        this.blockEntity = blockEntity instanceof EarlyForgeBlockEntity ? (EarlyForgeBlockEntity) blockEntity : null;

        this.addSlot(new Slot(this.inventory, 0, 14, 16));
        this.addSlot(new Slot(this.inventory, 1, 32, 16));
        this.addSlot(new Slot(this.inventory, 2, 50, 16));
        this.addSlot(new Slot(this.inventory, 3, 68, 16));

        this.addSlot(new Slot(this.inventory, OUTPUT_SLOT, 120, 17) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        this.addSlot(new Slot(this.inventory, FUEL_SLOT, 41, 55) {
            @Override
            public boolean canInsert(ItemStack stack) {
                Integer fuelTicks = FuelRegistry.INSTANCE.get(stack.getItem());
                return fuelTicks != null && fuelTicks > 0;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 26;
        return maxProgress > 0 && progress > 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(2) > 0;
    }

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
            int playerInvEnd = playerInvStart + 27;
            int hotbarStart = playerInvEnd;
            int hotbarEnd = hotbarStart + 9;

            if (invSlot < BLOCK_INV_SIZE) {
                if (!this.insertItem(originalStack, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                Integer fuelTicks = FuelRegistry.INSTANCE.get(originalStack.getItem());
                if (fuelTicks != null && fuelTicks > 0) {
                    if (!this.insertItem(originalStack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        if (!this.insertItem(originalStack, INPUT_START, INPUT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else {
                    if (!this.insertItem(originalStack, INPUT_START, INPUT_END, false)) {
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
