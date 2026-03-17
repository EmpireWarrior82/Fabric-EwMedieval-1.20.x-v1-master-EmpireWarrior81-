package net.empire.ewmedieval.block.entity;

import net.empire.ewmedieval.block.custom.earlyforge.EarlyForgeBlock;
import net.empire.ewmedieval.gui.earlyforge.EarlyForgeScreenHandler;
import net.empire.ewmedieval.recipe.EarlyForgeRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EarlyForgeBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;
    private static final int OUTPUT_SLOT = 4;
    public static final int FUEL_SLOT = 5;

    private int progress = 0;
    private int maxProgress = 200;
    private int burnTime = 0;
    private int fuelTime = 0;

    protected final PropertyDelegate propertyDelegate;

    public EarlyForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EARLYFORGE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> burnTime;
                    case 3 -> fuelTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                    case 2 -> burnTime = value;
                    case 3 -> fuelTime = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.ewmedieval.earlyforge");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("earlyforge.progress", progress);
        nbt.putInt("earlyforge.burnTime", burnTime);
        nbt.putInt("earlyforge.fuelTime", fuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("earlyforge.progress");
        burnTime = nbt.getInt("earlyforge.burnTime");
        fuelTime = nbt.getInt("earlyforge.fuelTime");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EarlyForgeScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) return;

        boolean wasBurning = isBurning();

        SimpleInventory inv = getInputInventory();

        if (isBurning()) {
            burnTime--;
        }

        if (!isBurning() && hasValidRecipe(inv) && !getStack(FUEL_SLOT).isEmpty()) {
            ItemStack fuelStack = getStack(FUEL_SLOT);
            Integer fuelTicks = FuelRegistry.INSTANCE.get(fuelStack.getItem());
            if (fuelTicks != null && fuelTicks > 0) {
                burnTime = fuelTicks;
                fuelTime = fuelTicks;

                if (fuelStack.getItem() == Items.LAVA_BUCKET) {
                    setStack(FUEL_SLOT, new ItemStack(Items.BUCKET));
                } else {
                    fuelStack.decrement(1);
                }
            }
        }

        boolean hasRecipe = hasValidRecipe(inv);
        boolean canInsert = canInsertResult(inv);
        boolean changed = false;

        if (hasRecipe && canInsert) {
            if (isBurning()) {
                progress++;
                changed = true;
                if (progress >= maxProgress) {
                    craftItem(inv);
                    progress = 0;
                }
            } else {

                if (progress > 0) {
                    progress--;
                    changed = true;
                }
            }
        } else {

            if (progress != 0) {
                progress = 0;
                changed = true;
            }
        }

        if (changed) {
            markDirty(world, pos, state);
        }

        if (wasBurning != isBurning()) {
            world.setBlockState(pos, state.with(EarlyForgeBlock.LIT, isBurning()), Block.NOTIFY_ALL);
        }
    }

    private SimpleInventory getInputInventory() {
        SimpleInventory inv = new SimpleInventory(4);
        inv.setStack(0, getStack(INPUT_SLOT_1));
        inv.setStack(1, getStack(INPUT_SLOT_2));
        inv.setStack(2, getStack(INPUT_SLOT_3));
        inv.setStack(3, getStack(INPUT_SLOT_4));
        return inv;
    }

    private boolean hasValidRecipe(SimpleInventory inv) {
        if (world == null) return false;
        return world.getRecipeManager()
                .getFirstMatch(EarlyForgeRecipe.Type.INSTANCE, inv, world)
                .isPresent();
    }

    private boolean canInsertResult(SimpleInventory inv) {
        if (world == null) return false;

        return world.getRecipeManager()
                .getFirstMatch(EarlyForgeRecipe.Type.INSTANCE, inv, world)
                .map(recipe -> {
                    ItemStack result = recipe.craft(inv, world.getRegistryManager());
                    ItemStack output = getStack(OUTPUT_SLOT);
                    return output.isEmpty() ||
                            (output.getItem() == result.getItem() &&
                                    output.getCount() + result.getCount() <= output.getMaxCount());
                })
                .orElse(false);
    }

    private void craftItem(SimpleInventory inv) {
        if (world == null) return;
        world.getRecipeManager()
                .getFirstMatch(EarlyForgeRecipe.Type.INSTANCE, inv, world)
                .ifPresent(recipe -> {
                    for (int i = 0; i < 4; i++) {
                        if (!getStack(i).isEmpty()) {
                            removeStack(i, 1);
                        }
                    }

                    ItemStack result = recipe.craft(inv, world.getRegistryManager());
                    ItemStack output = getStack(OUTPUT_SLOT);

                    if (output.isEmpty()) {
                        setStack(OUTPUT_SLOT, result.copy());
                    } else if (output.getItem() == result.getItem()) {
                        output.increment(result.getCount());
                    }
                });
    }
}