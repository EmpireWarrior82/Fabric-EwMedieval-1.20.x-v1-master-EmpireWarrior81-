package net.empire.ewmedieval.block.custom.cropblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

// BuddingBushBlock — a bush-like crop that grows through 5 stages (0-4).
// At max age (3) it can optionally "grow past" and transform into a different block.
// Subclasses override canGrowPastMaxAge() and growPastMaxAge() to define transformation.
@SuppressWarnings("deprecation")
public abstract class BuddingBushBlock extends PlantBlock {

    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = IntProperty.of("age", 0, 4);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public BuddingBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_BY_AGE[state.get(getAgeProperty())];
    }

    // Can only be planted on farmland
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof FarmlandBlock;
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    protected int getAge(BlockState state) {
        return state.get(getAgeProperty());
    }

    public BlockState getStateForAge(int age) {
        return this.getDefaultState().with(getAgeProperty(), age);
    }

    public boolean isMaxAge(BlockState state) {
        return state.get(getAgeProperty()) >= getMaxAge();
    }

    // Keep ticking if it can grow past max age, or if it hasn't reached max age yet
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return canGrowPastMaxAge() || !isMaxAge(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isChunkLoaded(pos)) return;
        if (world.getLightLevel(pos, 0) >= 9) {
            int age = getAge(state);
            if (age <= getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, world, pos);
                if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
                    if (isMaxAge(state)) {
                        growPastMaxAge(state, world, pos, random);
                    } else {
                        world.setBlockState(pos, getStateForAge(age + 1));
                    }
                }
            }
        }
    }

    // Whether this bush keeps ticking at max age and can transform
    public boolean canGrowPastMaxAge() {
        return false;
    }

    // Called when growth goes past max age — subclasses define what it becomes
    public void growPastMaxAge(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    }

    // Growth speed calculation — same logic as vanilla CropBlock
    protected static float getGrowthSpeed(Block block, BlockView world, BlockPos pos) {
        float speed = 1.0F;
        BlockPos posBelow = pos.down();

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                float bonus = 1.0F;
                BlockState below = world.getBlockState(posBelow.add(x, 0, z));
                if (below.getBlock() instanceof FarmlandBlock
                        && below.get(FarmlandBlock.MOISTURE) > 0) {
                    bonus = 3.0F;
                }
                if (x != 0 || z != 0) {
                    bonus /= 4.0F;
                }
                speed += bonus;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean ewRow = world.getBlockState(west).isOf(block) || world.getBlockState(east).isOf(block);
        boolean nsRow = world.getBlockState(north).isOf(block) || world.getBlockState(south).isOf(block);

        if (ewRow && nsRow) {
            speed /= 2.0F;
        } else {
            boolean diagonal = world.getBlockState(west.north()).isOf(block)
                    || world.getBlockState(east.north()).isOf(block)
                    || world.getBlockState(east.south()).isOf(block)
                    || world.getBlockState(west.south()).isOf(block);
            if (diagonal) {
                speed /= 2.0F;
            }
        }

        return speed;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                && super.canPlaceAt(state, world, pos);
    }

    // Ravagers destroy crops when mob griefing is on
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity
                && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    // Returns the seed item for middle-click in creative
    protected abstract ItemConvertible getSeedsItem();

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(getSeedsItem());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}