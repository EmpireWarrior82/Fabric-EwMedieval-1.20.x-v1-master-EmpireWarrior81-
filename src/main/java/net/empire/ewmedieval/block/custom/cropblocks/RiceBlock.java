package net.empire.ewmedieval.block.custom.cropblocks;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RiceBlock extends PlantBlock implements Fertilizable, Waterloggable {

    public static final IntProperty AGE = Properties.AGE_3;

    public static final BooleanProperty SUPPORTING = BooleanProperty.of("supporting");

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
            Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)
    };

    public RiceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(AGE, 0)
                .with(SUPPORTING, false));
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    public int getMaxAge() {
        return 3;
    }

    public boolean isMaxAge(BlockState state) {
        return state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), age);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if (!world.isChunkLoaded(pos)) return;

        // Only grow in sufficient light
        if (world.getLightLevel(pos.up(), 0) >= 6) {
            int age = this.getAge(state);
            if (age <= this.getMaxAge()) {
                float chance = 10;
                if (random.nextInt((int) (25.0F / chance) + 1) == 0) {
                    if (age == this.getMaxAge()) {
                        // Fully grown stalk — try to place the panicle on top
                        RicePaniclesBlock riceUpper = (RicePaniclesBlock) ModBlocks.RICE_CROP_PANICLES;
                        if (riceUpper.getDefaultState().canPlaceAt(world, pos.up())
                                && world.isAir(pos.up())) {
                            world.setBlockState(pos.up(), riceUpper.getDefaultState());
                        }
                    } else {
                        world.setBlockState(pos, this.withAge(age + 1), 2);
                    }
                }
            }
        }
    }

    // --- Shape ---

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

    // --- Placement conditions ---

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        FluidState fluid = world.getFluidState(pos);
        // Must be submerged in source water
        return super.canPlaceAt(state, world, pos)
                && fluid.isIn(FluidTags.WATER)
                && fluid.getLevel() == 8;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isIn(BlockTags.DIRT);
    }

    // Returns the rice item when middle-clicked in creative
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.RICE);
    }

    // --- State definition ---

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, SUPPORTING);
    }

    // --- Neighbor updates ---

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        BlockState current = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (!current.isAir()) {
            // Keep the block waterlogged
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            if (direction == Direction.UP) {
                // Update SUPPORTING flag based on whether panicles are above
                return current.with(SUPPORTING, isSupportingRiceUpper(neighborState));
            }
        }
        return current;
    }

    public boolean isSupportingRiceUpper(BlockState topState) {
        return topState.getBlock() instanceof RicePaniclesBlock;
    }

    // --- Placement ---

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState fluid = context.getWorld().getFluidState(context.getBlockPos());
        // Can only be placed in source water
        return fluid.isIn(FluidTags.WATER) && fluid.getLevel() == 8
                ? super.getPlacementState(context)
                : null;
    }

    // --- Waterloggable ---
    // Rice is always waterlogged — it cannot exist outside of water

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false; // already always waterlogged
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    // --- Fertilizable (bonemeal) ---

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockState upper = world.getBlockState(pos.up());
        if (upper.getBlock() instanceof RicePaniclesBlock panicles) {
            // Can bonemeal if the panicle isn't fully grown yet
            return !panicles.isMaxAge(upper);
        }
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    protected int getBonemealAgeIncrease(World world) {
        return MathHelper.nextInt(world.random, 1, 4);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(world), 7);

        if (ageGrowth <= this.getMaxAge()) {
            // Still growing the stalk
            world.setBlockState(pos, state.with(AGE, ageGrowth));
        } else {
            BlockState top = world.getBlockState(pos.up());
            if (top.getBlock() instanceof RicePaniclesBlock growable) {
                // Panicle exists — bonemeal it if it can still grow
                if (growable.isFertilizable(world, pos.up(), top, false)) {
                    growable.grow(world, world.random, pos.up(), top);
                }
            } else {
                // No panicle yet — place one and apply remaining growth
                RicePaniclesBlock riceUpper = (RicePaniclesBlock) ModBlocks.RICE_CROP_PANICLES;
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (riceUpper.getDefaultState().canPlaceAt(world, pos.up())
                        && world.isAir(pos.up())) {
                    world.setBlockState(pos, state.with(AGE, this.getMaxAge()));
                    world.setBlockState(pos.up(),
                            riceUpper.getDefaultState().with(RicePaniclesBlock.RICE_AGE, remainingGrowth),
                            2);
                }
            }
        }
    }
}