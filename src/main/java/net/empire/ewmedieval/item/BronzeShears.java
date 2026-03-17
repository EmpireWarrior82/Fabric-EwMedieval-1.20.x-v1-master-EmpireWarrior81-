package net.empire.ewmedieval.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BronzeShears extends ShearsItem {
    public static final int BRONZE_SHEARS_DURABILITY = 180;

    public BronzeShears(Settings settings) {
        super(settings.maxCount(1).maxDamage(BRONZE_SHEARS_DURABILITY));
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return isSuitableFor(state)
                || state.isOf(Blocks.PUMPKIN)
                || state.isOf(Blocks.COBWEB);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        if (state.isOf(Blocks.PUMPKIN)) {
            if (!world.isClient && player != null) {
                world.setBlockState(pos, Blocks.CARVED_PUMPKIN.getDefaultState()
                        .with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite()), 11);
                world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                stack.damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));
                world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(Items.PUMPKIN_SEEDS, 4)));
            }

            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof SheepEntity sheep) {
            if (!entity.getWorld().isClient && sheep.isShearable()) {
                sheep.sheared(SoundCategory.PLAYERS);
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state,
                            BlockPos pos, LivingEntity miner) {
        if (!world.isClient && !state.isIn(BlockTags.FIRE)) {

            boolean shouldDrop = state.isIn(BlockTags.LEAVES)
                    || state.isOf(Blocks.COBWEB)
                    || state.isOf(Blocks.VINE)
                    || state.isOf(Blocks.GLOW_LICHEN);

            if (shouldDrop) {
                stack.damage(1, miner,
                        (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.spawnEntity(new ItemEntity(world,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(state.getBlock().asItem())));

                return true;
            }

            stack.damage(1, miner,
                    (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return isSuitableFor(state)
                || state.isOf(Blocks.COBWEB)
                || state.isOf(Blocks.VINE)
                || state.isOf(Blocks.GLOW_LICHEN)
                || state.isIn(BlockTags.LEAVES);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)
                || state.isOf(Blocks.VINE)
                || state.isOf(Blocks.GLOW_LICHEN)
                || state.isIn(BlockTags.LEAVES)) {
            return 15.0F;
        }
        if (state.isIn(BlockTags.WOOL)) {
            return 5.0F;
        }
        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(BlockTags.WOOL)
                || state.isOf(Blocks.VINE)
                || state.isOf(Blocks.GLOW_LICHEN)
                || state.isIn(BlockTags.LEAVES);
    }
}