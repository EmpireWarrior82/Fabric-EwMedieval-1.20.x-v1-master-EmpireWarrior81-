package net.empire.ewmedieval.item;

import com.google.common.collect.Sets;
import net.empire.ewmedieval.enchantment.ModEnchantments;
import net.empire.ewmedieval.util.ModTags;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Set;

public class KnifeItem extends MiningToolItem implements CustomEnchantingBehaviorItem {

    public KnifeItem(ToolMaterial tier, float attackDamage, float attackSpeed, Settings properties) {
        super(attackDamage, attackSpeed, tier, ModTags.MINEABLE_WITH_KNIFE, properties);
    }

    @Override
    public boolean canMine(BlockState state, World level, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isIn(ModTags.MINEABLE_WITH_KNIFE)) {
            return this.getMaterial().getMiningSpeedMultiplier();
        }
        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (user) -> user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    public static void init() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack toolStack = player.getStackInHand(hand);
            if (!toolStack.isIn(ModTags.KNIVES)) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if (state.isIn(ModTags.DROPS_CAKE_SLICE)) {
                if (!level.isClient) {
                    level.setBlockState(pos, Blocks.CAKE.getDefaultState().with(CakeBlock.BITES, 1), 3);
                    Block.dropStacks(state, level, pos);
                    ItemEntity itemEntity = new ItemEntity(level,
                            pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5,
                            new ItemStack(ModItems.CAKE_SLICE.asItem()));
                    itemEntity.setVelocity(-0.05, 0, 0);
                    level.spawnEntity(itemEntity);
                    level.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
                    return ActionResult.SUCCESS;
                }
            }

            if (block == Blocks.CAKE) {
                int bites = state.get(CakeBlock.BITES);
                if (bites < 6) {
                    level.setBlockState(pos, state.with(CakeBlock.BITES, bites + 1), 3);
                } else {
                    level.removeBlock(pos, false);
                }
                ItemEntity itemEntity = new ItemEntity(level,
                        pos.getX() + (bites * 0.1) + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5,
                        new ItemStack(ModItems.CAKE_SLICE.asItem()));
                itemEntity.setVelocity(-0.05, 0, 0);
                level.spawnEntity(itemEntity);
                level.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
                return ActionResult.success(level.isClient);
            }

            return ActionResult.PASS;
        });
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World level = context.getWorld();
        ItemStack toolStack = context.getStack();
        BlockPos pos = context.getBlockPos();
        BlockState state = level.getBlockState(pos);
        Direction facing = context.getSide();

        if (state.getBlock() == Blocks.PUMPKIN && toolStack.isIn(ModTags.KNIVES)) {
            PlayerEntity player = context.getPlayer();
            if (player != null && !level.isClient) {
                Direction direction = facing.getAxis() == Direction.Axis.Y
                        ? player.getHorizontalFacing().getOpposite()
                        : facing;
                level.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.setBlockState(pos, Blocks.CARVED_PUMPKIN.getDefaultState()
                        .with(CarvedPumpkinBlock.FACING, direction), 11);
                ItemEntity itemEntity = new ItemEntity(level,
                        pos.getX() + 0.5 + direction.getOffsetX() * 0.65,
                        pos.getY() + 0.1,
                        pos.getZ() + 0.5 + direction.getOffsetZ() * 0.65,
                        new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itemEntity.setVelocity(
                        0.05 * direction.getOffsetX() + level.random.nextDouble() * 0.02,
                        0.05,
                        0.05 * direction.getOffsetZ() + level.random.nextDouble() * 0.02);
                level.spawnEntity(itemEntity);
                toolStack.damage(1, player, (playerIn) -> playerIn.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(level.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        Set<Enchantment> ALLOWED_ENCHANTMENTS = Sets.newHashSet(
                Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.LOOTING,
                ModEnchantments.BACKSTABBING);
        if (ALLOWED_ENCHANTMENTS.contains(enchantment)) return true;

        Set<Enchantment> DENIED_ENCHANTMENTS = Sets.newHashSet(Enchantments.FORTUNE);
        if (DENIED_ENCHANTMENTS.contains(enchantment)) return false;

        return enchantment.target.isAcceptableItem(stack.getItem());
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.isIn(ModTags.MINEABLE_WITH_KNIFE)) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

            if (state.getBlock() == Blocks.CAKE && state.get(CakeBlock.BITES) == 0) {
                Block.dropStack(world, pos, new ItemStack(Items.CAKE));
            }
        }
        return true;
    }
}