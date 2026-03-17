package net.empire.ewmedieval.block.custom;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import net.empire.ewmedieval.block.entity.CuttingBoardBlockEntity;
import net.empire.ewmedieval.util.ModTags;
import net.empire.ewmedieval.block.entity.ModBlockEntities;

@SuppressWarnings("deprecation")

public class CuttingBoardBlock extends BlockWithEntity implements Waterloggable {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

    public CuttingBoardBlock(Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    /**
     * Call this in your ModInitializer to register the sneak+right-click tool placement event.
     */
    public static void init() {
        UseBlockCallback.EVENT.register(ToolCarvingEvent::onSneakPlaceTool);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof CuttingBoardBlockEntity cuttingBoard)) {
            return ActionResult.PASS;
        }

        ItemStack heldStack = player.getStackInHand(hand);
        ItemStack offhandStack = player.getOffHandStack();

        if (cuttingBoard.isEmpty()) {
            if (!offhandStack.isEmpty()) {
                if (hand == Hand.MAIN_HAND
                        && !offhandStack.isIn(ModTags.OFFHAND_EQUIPMENT)
                        && !(heldStack.getItem() instanceof BlockItem)) {
                    return ActionResult.PASS;
                }
                if (hand == Hand.OFF_HAND && offhandStack.isIn(ModTags.OFFHAND_EQUIPMENT)) {
                    return ActionResult.PASS;
                }
            }

            if (heldStack.isEmpty()) {
                return ActionResult.PASS;
            }

            ItemStack toPlace = player.getAbilities().creativeMode ? heldStack.copy() : heldStack;
            if (cuttingBoard.addItem(toPlace)) {
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
                return ActionResult.SUCCESS;
            }

        } else if (!heldStack.isEmpty()) {
            ItemStack boardStack = cuttingBoard.getStoredItem().copy();
            if (cuttingBoard.processStoredItemUsingTool(heldStack, player)) {
                spawnCuttingParticles(world, pos, boardStack, 5);
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;

        } else if (hand == Hand.MAIN_HAND) {
            if (!player.getAbilities().creativeMode) {
                ItemStack removed = cuttingBoard.removeItem();
                if (!player.getInventory().insertStack(removed)) {
                    player.dropItem(removed, false);
                }
            } else {
                cuttingBoard.removeItem();
            }
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.25F, 0.5F);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos,
                                BlockState newState, boolean moved) {
        if (state.getBlock() == newState.getBlock()) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CuttingBoardBlockEntity cuttingBoard) {
            ItemScatterer.spawn(world, pos, new SimpleInventory(cuttingBoard.getStoredItem()));
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState fluid = context.getWorld().getFluidState(context.getBlockPos());
        return this.getDefaultState()
                .with(FACING, context.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos below = pos.down();
        return world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CuttingBoardBlockEntity board) {
            return !board.isEmpty() ? 15 : 0;
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.CUTTING_BOARD_BLOCK_ENTITY.instantiate(pos, state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public static void spawnCuttingParticles(World world, BlockPos pos, ItemStack stack, int count) {
        for (int i = 0; i < count; i++) {
            Vec3d velocity = new Vec3d(
                    (world.random.nextFloat() - 0.5D) * 0.1D,
                    Math.random() * 0.1D + 0.1D,
                    (world.random.nextFloat() - 0.5D) * 0.1D);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        new ItemStackParticleEffect(ParticleTypes.ITEM, stack),
                        pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5,
                        1, velocity.x, velocity.y + 0.05, velocity.z, 0.0);
            } else {
                world.addParticle(
                        new ItemStackParticleEffect(ParticleTypes.ITEM, stack),
                        pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5,
                        velocity.x, velocity.y + 0.05, velocity.z);
            }
        }
    }

    public static class ToolCarvingEvent {
        public static ActionResult onSneakPlaceTool(PlayerEntity player, World world,
                                                    Hand hand, BlockHitResult hit) {
            BlockPos pos = hit.getBlockPos();
            ItemStack heldStack = player.getMainHandStack();

            if (!(world.getBlockEntity(pos) instanceof CuttingBoardBlockEntity board)) {
                return ActionResult.PASS;
            }

            if (player.isSneaking() && !heldStack.isEmpty()) {
                Item item = heldStack.getItem();
                boolean isTool = item instanceof ToolItem
                        || item instanceof TridentItem
                        || item instanceof ShearsItem;

                if (isTool) {
                    ItemStack toPlace = player.getAbilities().creativeMode
                            ? heldStack.copy()
                            : heldStack;
                    if (board.carveToolOnBoard(toPlace)) {
                        world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE,
                                SoundCategory.BLOCKS, 1.0F, 0.8F);
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        }
    }
}