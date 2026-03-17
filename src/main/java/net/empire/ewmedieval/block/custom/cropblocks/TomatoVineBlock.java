package net.empire.ewmedieval.block.custom.cropblocks;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.block.custom.RopeBlock;
import net.empire.ewmedieval.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import static net.empire.ewmedieval.block.custom.cropblocks.BuddingBushBlock.getGrowthSpeed;

@SuppressWarnings("deprecation")
public class TomatoVineBlock extends CropBlock {

    // 4 growth stages (0-3), stage 3 = mature with harvestable tomatoes
    public static final IntProperty VINE_AGE = Properties.AGE_3;

    // Whether this vine block is growing on a rope above the base plant
    public static final BooleanProperty ROPELOGGED = BooleanProperty.of("ropelogged");

    private static final VoxelShape SHAPE =
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public TomatoVineBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(getAgeProperty(), 0)
                .with(ROPELOGGED, false));
    }

    @Override
    public IntProperty getAgeProperty() {
        return VINE_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.TOMATO_SEEDS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    // Right-click mature vine to pick tomatoes, resetting age to 0
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(getAgeProperty());
        boolean isMature = age == getMaxAge();

        // Don't intercept bonemeal on immature vines
        if (!isMature && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        }

        if (isMature) {
            if (!world.isClient) {
                int quantity = 1 + world.random.nextInt(2);
                Block.dropStack(world, pos, new ItemStack(ModItems.TOMATO, quantity));

                // Uncomment when rotten tomato item is added:
                // if (world.random.nextFloat() < 0.05F) {
                //     Block.dropStack(world, pos, new ItemStack(ModItems.ROTTEN_TOMATO));
                // }

                world.playSound(null, pos,
                        SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES,
                        SoundCategory.BLOCKS, 1.0F,
                        0.8F + world.random.nextFloat() * 0.4F);
                world.setBlockState(pos, state.with(getAgeProperty(), 0), 2);
            }
            return ActionResult.success(world.isClient);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    // Always tick — vine needs to grow and attempt rope climbing
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isChunkLoaded(pos)) return;
        if (world.getLightLevel(pos, 0) >= 9) {
            int age = state.get(getAgeProperty());
            if (age < this.getMaxAge()) {
                float speed = getGrowthSpeed(this, world, pos);
                if (random.nextInt((int) (25.0F / speed) + 1) == 0) {
                    world.setBlockState(pos, state.with(getAgeProperty(), age + 1), 2);
                }
            }
            attemptRopeClimb(world, pos, random);
        }
    }

    // 30% chance per tick to grow upward onto a rope block
    public void attemptRopeClimb(ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.3F) {
            BlockPos posAbove = pos.up();
            BlockState stateAbove = world.getBlockState(posAbove);
            boolean canClimb = stateAbove.getBlock() instanceof RopeBlock;

            if (canClimb) {
                int vineHeight = 1;
                while (world.getBlockState(pos.down(vineHeight)).isOf(this)) {
                    vineHeight++;
                }
                // Max vine height of 3 blocks
                if (vineHeight < 3) {
                    world.setBlockState(posAbove, this.getDefaultState().with(ROPELOGGED, true));
                }
            }
        }
    }

    // --- Survival ---

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos belowPos = pos.down();
        BlockState belowState = world.getBlockState(belowPos);

        if (state.get(ROPELOGGED)) {
            // Ropelogged vine must be above another tomato vine block with enough light
            return belowState.getBlock() instanceof TomatoVineBlock
                    && hasGoodLightConditions(world, pos);
        }

        return super.canPlaceAt(state, world, pos);
    }

    public boolean hasGoodLightConditions(WorldView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos);
    }

    // When a ropelogged vine is broken by a player, put the rope back
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean isRopelogged = state.get(ROPELOGGED);
        super.onBreak(world, pos, state, player);
        if (isRopelogged && !world.isClient) {
            world.setBlockState(pos, ModBlocks.ROPE.getDefaultState());
        }
    }

    // Schedule a tick to re-check survival when neighbors change
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    // Destroy vine on scheduled tick if it can no longer survive
    // Ropelogged vines leave the rope behind
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            if (state.get(ROPELOGGED)) {
                world.setBlockState(pos, ModBlocks.ROPE.getDefaultState());
            } else {
                world.breakBlock(pos, true);
            }
        }
    }

    // --- Bonemeal ---

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int newAge = Math.min(state.get(getAgeProperty()) + getBonemealAgeIncrease(world),
                this.getMaxAge());
        world.setBlockState(pos, state.with(getAgeProperty(), newAge));
        attemptRopeClimb(world, pos, random);
    }

    private int getBonemealAgeIncrease(World world) {
        // Slower than normal — roughly half the vanilla increase
        return Math.max(1, MathHelper.nextInt(world.random, 1, 3) / 2);
    }

    // --- State definition ---

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VINE_AGE, ROPELOGGED);
    }
}
