package net.empire.ewmedieval.block.custom.cropblocks;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

// BuddingTomatoBlock — the seedling stage of the tomato plant (age 0-4).
// At age 4 it transforms into TomatoVineBlock via growPastMaxAge.
public class BuddingTomatoBlock extends BuddingBushBlock implements net.minecraft.block.Fertilizable {

    public BuddingTomatoBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.TOMATO_SEEDS;
    }

    // Can only be planted on farmland (no rich soil since we don't have that block)
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof FarmlandBlock
                || floor.isOf(Blocks.FARMLAND);
    }

    // When neighbor updates push age to 4, transform into TomatoVineBlock
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (state.get(AGE) >= 4) {
            world.setBlockState(pos, ModBlocks.TOMATO_CROP.getDefaultState(), 3);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canGrowPastMaxAge() {
        return true;
    }

    // Past max age — become a tomato vine
    @Override
    public void growPastMaxAge(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, ModBlocks.TOMATO_CROP.getDefaultState());
    }

    // --- Fertilizable ---

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int maxAge = this.getMaxAge();
        int ageGrowth = Math.min(this.getAge(state) + getBonemealAgeIncrease(world), 7);

        if (ageGrowth <= maxAge) {
            world.setBlockState(pos, state.with(AGE, ageGrowth));
        } else {
            // Carry remaining growth into the vine's age
            int remainingGrowth = Math.min(ageGrowth - maxAge - 1,
                    TomatoVineBlock.VINE_AGE.getValues().size() - 1);
            world.setBlockState(pos, ModBlocks.TOMATO_CROP.getDefaultState()
                    .with(TomatoVineBlock.VINE_AGE, remainingGrowth));
        }
    }

    protected int getBonemealAgeIncrease(World world) {
        return MathHelper.nextInt(world.random, 1, 4);
    }
}