package net.empire.ewmedieval.block.custom;

import net.empire.ewmedieval.item.RopeItem;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class RopeBlock extends PaneBlock {

    public static final BooleanProperty TIED_TO_BELL = BooleanProperty.of("tied_to_bell");

    protected static final VoxelShape LOWER_SUPPORT_SHAPE =
            Block.createCuboidShape(7, 0, 7, 9, 1, 9);

    public RopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(TIED_TO_BELL, false)
                .with(Properties.WATERLOGGED, false));
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockView world = context.getWorld();
        BlockPos posAbove = context.getBlockPos().up();
        BlockState state = super.getPlacementState(context);
        return state != null
                ? state.with(TIED_TO_BELL, world.getBlockState(posAbove).getBlock() == Blocks.BELL)
                : null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {

        // If holding rope, pass so the UseBlockCallback in RopeItem handles placement
        if (player.getStackInHand(hand).getItem() instanceof RopeItem) {
            return ActionResult.PASS;
        }

        // Client side — tell vanilla whether this block is interactive with empty hand
        if (world.isClient) {
            return player.getStackInHand(hand).isEmpty() ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        // Server side — actual interaction logic
        if (player.getStackInHand(hand).isEmpty()) {
            if (player.isSneaking()) {
                // Sneak + right-click with empty hand: reel up the lowest rope in the chain
                if (player.getAbilities().allowModifyWorld &&
                        (player.getAbilities().creativeMode ||
                                player.getInventory().insertStack(new ItemStack(this.asItem())))) {

                    BlockPos.Mutable reelingPos = pos.mutableCopy().move(Direction.DOWN);
                    int minBuildHeight = world.getBottomY();

                    while (reelingPos.getY() >= minBuildHeight) {
                        BlockState below = world.getBlockState(reelingPos);
                        if (below.isOf(this)) {
                            reelingPos.move(Direction.DOWN);
                        } else {
                            reelingPos.move(Direction.UP);
                            world.breakBlock(reelingPos, false, player);
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            } else {
                // Right-click with empty hand: ring a bell above (searches up to 24 blocks)
                BlockPos.Mutable bellPos = pos.mutableCopy().move(Direction.UP);

                for (int i = 0; i < 24; i++) {
                    BlockState above = world.getBlockState(bellPos);
                    Block blockAbove = above.getBlock();

                    if (blockAbove == Blocks.BELL) {
                        BellBlock bellBlock = (BellBlock) Blocks.BELL;
                        bellBlock.ring(player, world, bellPos, null);
                        return ActionResult.SUCCESS;
                    } else if (blockAbove instanceof RopeBlock) {
                        bellPos.move(Direction.UP);
                    } else {
                        return ActionResult.PASS;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    // No collision — entities walk through rope
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos,
                                        ShapeContext context) {
        return VoxelShapes.empty();
    }

    // Small support at the bottom so items can be placed on top of rope
    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LOWER_SUPPORT_SHAPE;
    }

    // Normal block placement against rope works without sneaking
    // (rope extending downward is handled by RopeItem's UseBlockCallback instead)
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        boolean tiedToBell = state.get(TIED_TO_BELL);
        if (direction == Direction.UP) {
            tiedToBell = neighborState.getBlock() == Blocks.BELL;
        }

        if (direction.getAxis().isHorizontal()) {
            return state
                    .with(TIED_TO_BELL, tiedToBell)
                    .with(FACING_PROPERTIES.get(direction),
                            this.connectsTo(neighborState,
                                    neighborState.isSideSolidFullSquare(world, neighborPos,
                                            direction.getOpposite())));
        } else {
            return super.getStateForNeighborUpdate(
                    state.with(TIED_TO_BELL, tiedToBell),
                    direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, Properties.WATERLOGGED, TIED_TO_BELL);
    }
}