package net.empire.ewmedieval.block.custom.cropblocks;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class RicePaniclesBlock extends CropBlock {

    // Uses AGE_3 — 4 stages (0-3), representing the ripening panicle on top of the rice stalk
    public static final IntProperty RICE_AGE = Properties.AGE_3;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
            Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)
    };

    public RicePaniclesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public IntProperty getAgeProperty() {
        return RICE_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    public boolean isMaxAge(BlockState state) {
        return state.get(RICE_AGE) >= this.getMaxAge();
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.RICE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RICE_AGE);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int newAge = Math.min(state.get(RICE_AGE) + 1, this.getMaxAge());
        world.setBlockState(pos, state.with(RICE_AGE, newAge), 2);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof RiceBlock;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                && this.canPlantOnTop(world.getBlockState(pos.down()), world, pos);
    }
}