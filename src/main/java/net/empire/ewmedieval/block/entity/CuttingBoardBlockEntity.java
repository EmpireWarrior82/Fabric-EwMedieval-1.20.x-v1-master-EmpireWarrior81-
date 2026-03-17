package net.empire.ewmedieval.block.entity;

import net.empire.ewmedieval.block.custom.CuttingBoardBlock;
import net.empire.ewmedieval.recipe.CuttingBoardRecipe;
import net.empire.ewmedieval.recipe.ModRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CuttingBoardBlockEntity extends BlockEntity implements Inventory {

    // Single-slot inventory stored as a DefaultedList for easy NBT handling
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    // True when the item on the board is a tool placed for display/carving,
    // not an ingredient to be processed
    private boolean isItemCarvingBoard = false;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUTTING_BOARD_BLOCK_ENTITY, pos, state);
    }

    // -------------------------------------------------------------------------
    // NBT save / load
    // -------------------------------------------------------------------------

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        isItemCarvingBoard = nbt.getBoolean("IsItemCarved");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putBoolean("IsItemCarved", isItemCarvingBoard);
    }

    // -------------------------------------------------------------------------
    // Syncing with client (so the item model renders correctly)
    // -------------------------------------------------------------------------

    /**
     * Called when the chunk first loads on the client — sends full NBT.
     */
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    /**
     * Creates the update packet sent to the client whenever markDirty() is called.
     */
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /**
     * Call this whenever the inventory changes so the client re-renders the item.
     */
    private void inventoryChanged() {
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    // -------------------------------------------------------------------------
    // Inventory interface (single slot)
    // -------------------------------------------------------------------------

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return inventory.get(0).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) inventoryChanged();
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        inventoryChanged();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    // -------------------------------------------------------------------------
    // Convenience helpers used by CuttingBoardBlock
    // -------------------------------------------------------------------------

    /**
     * Returns the item currently sitting on the board.
     */
    public ItemStack getStoredItem() {
        return inventory.get(0);
    }

    /**
     * Places one item from the given stack onto the board as an ingredient.
     */
    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            // Take exactly one item
            inventory.set(0, itemStack.split(1));
            isItemCarvingBoard = false;
            inventoryChanged();
            return true;
        }
        return false;
    }

    /**
     * Places a tool onto the board for display/decoration (sneak + right-click).
     */
    public boolean carveToolOnBoard(ItemStack tool) {
        if (addItem(tool)) {
            isItemCarvingBoard = true;
            return true;
        }
        return false;
    }

    /**
     * Removes and returns the stored item, resetting the carving flag.
     */
    public ItemStack removeItem() {
        if (!isEmpty()) {
            isItemCarvingBoard = false;
            ItemStack item = removeStack(0);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public boolean isItemCarvingBoard() {
        return isItemCarvingBoard;
    }

    // -------------------------------------------------------------------------
    // Recipe processing
    // -------------------------------------------------------------------------

    /**
     * Attempts to process the stored item using the provided tool.
     * Returns true if a matching recipe was found and executed.
     */
    public boolean processStoredItemUsingTool(ItemStack toolStack, @Nullable PlayerEntity player) {
        if (world == null || isItemCarvingBoard) return false;

        // Wrap current inventory for recipe matching
        SimpleInventory recipeInventory = new SimpleInventory(getStoredItem());

        Optional<CuttingBoardRecipe> matchingRecipe = getMatchingRecipe(recipeInventory, toolStack, player);

        matchingRecipe.ifPresent(recipe -> {
            // Get fortune level on the tool, then roll the recipe's result list
            int fortuneLevel = EnchantmentHelper.getLevel(Enchantments.FORTUNE, toolStack);
            List<ItemStack> results = recipe.rollResults(world.random, fortuneLevel);

            // Spawn each result item next to the board, offset by the facing direction
            Direction facing = getCachedState().get(CuttingBoardBlock.FACING).rotateYCounterclockwise();
            for (ItemStack result : results) {
                spawnResultItem(result, facing);
            }

            // Damage the tool by 1 (or destroy it if it runs out of durability)
            if (!world.isClient) {
                if (player != null) {
                    toolStack.damage(1, player, p -> p.sendEquipmentBreakStatus(net.minecraft.entity.EquipmentSlot.MAINHAND));
                } else {
                    if (toolStack.damage(1, world.random, null)) {
                        toolStack.setCount(0);
                    }
                }
            }

            // Play the appropriate processing sound
            playProcessingSound(recipe.getSoundEventId(), toolStack, getStoredItem());

            removeItem();
        });

        return matchingRecipe.isPresent();
    }

    private Optional<CuttingBoardRecipe> getMatchingRecipe(SimpleInventory recipeInventory,
                                                           ItemStack toolStack,
                                                           @Nullable PlayerEntity player) {
        if (world == null) return Optional.empty();

        // Get all cutting board recipes that match the current ingredient
        List<CuttingBoardRecipe> candidates = world.getRecipeManager()
                .getAllMatches(ModRecipes.CUTTING_TYPE, recipeInventory, world);

        if (candidates.isEmpty()) {
            if (player != null) {
                player.sendMessage(
                        Text.translatable("block.ewmedieval.cutting_board.invalid_item"), true);
            }
            return Optional.empty();
        }

        // Among matching recipes, find one whose tool ingredient accepts the held tool
        Optional<CuttingBoardRecipe> match = candidates.stream()
                .filter(r -> r.getTool().test(toolStack))
                .findFirst();

        if (match.isEmpty() && player != null) {
            player.sendMessage(
                    Text.translatable("block.ewmedieval.cutting_board.invalid_tool"), true);
        }

        return match;
    }

    /**
     * Spawns a result ItemStack as an item entity slightly offset from the board.
     */
    private void spawnResultItem(ItemStack stack, Direction spawnDirection) {
        if (world == null || stack.isEmpty()) return;

        double x = pos.getX() + 0.5 + (spawnDirection.getOffsetX() * 0.2);
        double y = pos.getY() + 0.2;
        double z = pos.getZ() + 0.5 + (spawnDirection.getOffsetZ() * 0.2);

        ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack.copy());
        itemEntity.setVelocity(
                spawnDirection.getOffsetX() * 0.2F,
                0.0F,
                spawnDirection.getOffsetZ() * 0.2F);
        world.spawnEntity(itemEntity);
    }

    // -------------------------------------------------------------------------
    // Sounds
    // -------------------------------------------------------------------------

    /**
     * Plays the appropriate sound when a recipe is processed.
     * Tries the recipe's specified sound first, then falls back to sensible defaults.
     */
    public void playProcessingSound(@Nullable String soundEventId, ItemStack tool, ItemStack boardItem) {
        SoundEvent sound = null;

        // 1. Use the sound specified in the recipe JSON, if any
        if (soundEventId != null && !soundEventId.isEmpty()) {
            sound = Registries.SOUND_EVENT.get(new Identifier(soundEventId));
        }

        // 2. Fall back based on tool/item type
        if (sound == null) {
            if (tool.getItem() instanceof net.minecraft.item.ShearsItem) {
                sound = SoundEvents.ENTITY_SHEEP_SHEAR;
            } else if (tool.isIn(net.empire.ewmedieval.util.ModTags.KNIVES)) {
                // Your custom knife sound — picks knife1.ogg or knife2.ogg randomly
                sound = net.empire.ewmedieval.sound.ModSounds.BLOCK_CUTTING_BOARD_KNIFE;
            } else if (boardItem.getItem() instanceof BlockItem blockItem) {
                BlockSoundGroup soundType = blockItem.getBlock().getDefaultState().getSoundGroup();
                sound = soundType.getBreakSound();
            } else {
                sound = SoundEvents.BLOCK_WOOD_BREAK;
            }
        }

        playSound(sound, 0.8F, 1.0F);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        if (world != null) {
            world.playSound(
                    null,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    sound, SoundCategory.BLOCKS, volume, pitch);
        }
    }
}