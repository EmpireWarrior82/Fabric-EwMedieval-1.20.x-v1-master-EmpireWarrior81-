package net.empire.ewmedieval.block.custom.feastblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class FeastBlock extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, 4);

    public final Supplier<Item> servingItem;
    public final boolean hasLeftovers;

    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D),
    };

    /**
     * A block that provides up to 4 servings of food to players who interact with it.
     * If hasLeftovers is true the block lingers at 0 servings and is destroyed on
     * the next right-click. If false, it vanishes immediately when servings run out.
     *
     * @param settings     Block settings.
     * @param servingItem  The item given to the player per interaction.
     * @param hasLeftovers Whether the empty dish remains after all servings are taken.
     */
    public FeastBlock(Settings settings, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(settings);
        this.servingItem = servingItem;
        this.hasLeftovers = hasLeftovers;
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(getServingsProperty(), getMaxServings()));
    }

    public IntProperty getServingsProperty() {
        return SERVINGS;
    }

    public int getMaxServings() {
        return 4;
    }

    public ItemStack getServingItem(BlockState state) {
        return new ItemStack(this.servingItem.get());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(SERVINGS)];
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            if (this.takeServing(world, pos, state, player, hand).isAccepted()) {
                return ActionResult.SUCCESS;
            }
        }
        return this.takeServing(world, pos, state, player, hand);
    }

    protected ActionResult takeServing(World world, BlockPos pos, BlockState state,
                                       PlayerEntity player, Hand hand) {
        int servings = state.get(getServingsProperty());

        if (servings == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
            world.breakBlock(pos, true);
            return ActionResult.SUCCESS;
        }

        ItemStack serving = this.getServingItem(state);
        ItemStack heldStack = player.getStackInHand(hand);

        Item remainder = serving.getItem().getRecipeRemainder();
        boolean requiresContainer = remainder != null;
        boolean hasContainer = !requiresContainer || heldStack.isOf(remainder);

        if (hasContainer) {
            world.setBlockState(pos, state.with(getServingsProperty(), servings - 1), 3);

            if (requiresContainer && !player.getAbilities().creativeMode) {
                heldStack.decrement(1);
            }

            if (!player.getInventory().insertStack(serving)) {
                player.dropItem(serving, false);
            }

            if (world.getBlockState(pos).get(getServingsProperty()) == 0 && !this.hasLeftovers) {
                world.removeBlock(pos, false);
            }

            world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else {
            player.sendMessage(
                    Text.translatable("block.ewmedieval.feast.use_container",
                            new ItemStack(remainder).getName()),
                    true
            );
        }

        return ActionResult.PASS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SERVINGS);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(getServingsProperty());
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}