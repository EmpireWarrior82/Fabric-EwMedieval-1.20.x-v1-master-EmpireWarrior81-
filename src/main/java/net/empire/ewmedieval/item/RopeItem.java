package net.empire.ewmedieval.item;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RopeItem extends BlockItem {

    public RopeItem(Block block, Settings settings) {
        super(block, settings);
    }

    // Call this in onInitialize() — handles extending rope downward
    // when right-clicking an existing rope block with rope in hand
    public static void init() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack held = player.getStackInHand(hand);
            if (!(held.getItem() instanceof RopeItem ropeItem)) return ActionResult.PASS;

            BlockPos pos = hit.getBlockPos();
            Block block = ropeItem.getBlock();

            // Only trigger when clicking on an existing rope block
            if (!world.getBlockState(pos).isOf(block)) return ActionResult.PASS;

            // Walk downward to find the lowest available empty position
            BlockPos.Mutable mutablePos = pos.mutableCopy().move(net.minecraft.util.math.Direction.DOWN);

            for (int i = 0; i < 256; i++) {
                BlockState stateAt = world.getBlockState(mutablePos);

                // Keep moving down through existing rope
                if (stateAt.isOf(block)) {
                    mutablePos.move(net.minecraft.util.math.Direction.DOWN);
                    continue;
                }

                FluidState fluid = world.getFluidState(mutablePos);

                // Can't place in lava or other non-water fluids
                if (!fluid.isIn(FluidTags.WATER) && !fluid.isEmpty()) {
                    return ActionResult.PASS;
                }

                // Found a valid spot — place the rope
                if (stateAt.isAir() || fluid.isIn(FluidTags.WATER)) {
                    if (!world.isClient) {
                        world.setBlockState(mutablePos, block.getDefaultState());
                        if (!player.getAbilities().creativeMode) {
                            held.decrement(1);
                        }
                        world.playSound(null, mutablePos,
                                block.getDefaultState().getSoundGroup().getPlaceSound(),
                                SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    return ActionResult.success(world.isClient);
                }
                break;
            }

            return ActionResult.PASS;
        });
    }

    // Rope can be placed even if floating in air
    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return true;
    }
}