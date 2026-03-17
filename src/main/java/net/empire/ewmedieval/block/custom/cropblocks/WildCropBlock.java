package net.empire.ewmedieval.block.custom.cropblocks;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class WildCropBlock extends FlowerBlock implements Fertilizable {

    protected static final VoxelShape SHAPE =
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public WildCropBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);

        FlammableBlockRegistry.getDefaultInstance().add(this, 100, 60);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }


    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.8F;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int wildCropLimit = 10;

        for (BlockPos nearbyPos : BlockPos.iterate(
                pos.add(-4, -1, -4),
                pos.add(4, 1, 4))) {
            if (world.getBlockState(nearbyPos).isOf(this)) {
                --wildCropLimit;
                if (wildCropLimit <= 0) {
                    return;
                }
            }
        }

        BlockPos randomPos = pos.add(
                random.nextInt(3) - 1,
                random.nextInt(2) - random.nextInt(2),
                random.nextInt(3) - 1);

        for (int k = 0; k < 4; ++k) {
            if (world.isAir(randomPos) && state.canPlaceAt(world, randomPos)) {
                pos = randomPos;
            }
            randomPos = pos.add(
                    random.nextInt(3) - 1,
                    random.nextInt(2) - random.nextInt(2),
                    random.nextInt(3) - 1);
        }

        if (world.isAir(randomPos) && state.canPlaceAt(world, randomPos)) {
            world.setBlockState(randomPos, state, 2);
        }
    }
}
