package net.empire.ewmedieval.block.custom.cropblocks;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WildRiceBlock extends TallPlantBlock implements Waterloggable, Fertilizable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WildRiceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(WATERLOGGED, true)
                .with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TallPlantBlock.HALF, WATERLOGGED);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        FluidState fluid = world.getFluidState(pos);
        BlockPos floorPos = pos.down();
        if (state.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER) {
            return super.canPlaceAt(state, world, pos)
                    && this.canPlantOnTop(world.getBlockState(floorPos), world, floorPos)
                    && fluid.isIn(FluidTags.WATER)
                    && fluid.getLevel() == 8;
        }
        return super.canPlaceAt(state, world, pos)
                && world.getBlockState(pos.down()).getBlock() instanceof WildRiceBlock;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.SAND);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        world.setBlockState(pos.up(),
                this.getDefaultState()
                        .with(WATERLOGGED, false)
                        .with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER),
                3);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        BlockState currentState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        DoubleBlockHalf half = state.get(TallPlantBlock.HALF);

        if (!currentState.isAir()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (direction.getAxis() != Direction.Axis.Y
                || half == DoubleBlockHalf.LOWER != (direction == Direction.UP)
                || neighborState.getBlock() instanceof WildRiceBlock
                && neighborState.get(TallPlantBlock.HALF) != half) {
            return half == DoubleBlockHalf.LOWER
                    && direction == Direction.DOWN
                    && !state.canPlaceAt(world, pos)
                    ? Blocks.AIR.getDefaultState()
                    : state;
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos pos = context.getBlockPos();
        FluidState fluid = context.getWorld().getFluidState(pos);
        return pos.getY() < context.getWorld().getTopY() - 1
                && fluid.isIn(FluidTags.WATER)
                && fluid.getLevel() == 8
                && context.getWorld().getBlockState(pos.up()).isAir()
                ? super.getPlacementState(context)
                : null;
    }


    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER
                ? Fluids.WATER.getStill(false)
                : Fluids.EMPTY.getDefaultState();
    }



    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.3F;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Block.dropStack(world, pos, new ItemStack(this));
    }
}