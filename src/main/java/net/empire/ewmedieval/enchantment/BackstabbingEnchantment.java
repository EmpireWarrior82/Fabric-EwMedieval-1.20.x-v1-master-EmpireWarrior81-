package net.empire.ewmedieval.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BackstabbingEnchantment extends Enchantment
{
    public BackstabbingEnchantment(Rarity rarity, EnchantmentTarget category, EquipmentSlot... applicableSlots) {
        super(rarity, category, applicableSlots);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int enchantmentLevel) {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxPower(int enchantmentLevel) {
        return super.getMinPower(enchantmentLevel) + 50;
    }

    /**
     * Determines whether the attacker is within a 90-180 degree cone behind the target's looking direction.
     */

    public static boolean isLookingBehindTarget(LivingEntity target, Vec3d attackerLocation) {
        if (attackerLocation != null) {
            Vec3d lookingVector = target.getRotationVec(1.0F);
            Vec3d attackAngleVector = attackerLocation.subtract(target.getPos()).normalize();
            attackAngleVector = new Vec3d(attackAngleVector.x, 0.0, attackAngleVector.z);
            return attackAngleVector.dotProduct(lookingVector) < -0.5;
        }
        return false;
    }

    public static float getBackstabbingDamagePerLevel(float amount, int level) {
        float multiplier = (level * 0.2F) + 1.2F;
        return amount * multiplier;
    }

    public static float onKnifeBackstab(LivingEntity entity, Entity attacker, float amount) {
        if (attacker instanceof PlayerEntity player) {
            ItemStack weapon = player.getMainHandStack();
            int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.BACKSTABBING, weapon);
            if (enchantmentLevel > 0 && isLookingBehindTarget(entity, attacker.getPos())) {
                World world = entity.getWorld();
                if (!world.isClient) {
                    amount = getBackstabbingDamagePerLevel(amount, enchantmentLevel);
                    world.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
        return amount;
    }
}