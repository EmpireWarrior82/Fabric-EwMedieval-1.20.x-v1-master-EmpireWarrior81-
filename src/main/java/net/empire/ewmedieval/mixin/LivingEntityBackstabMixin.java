package net.empire.ewmedieval.mixin;

import net.empire.ewmedieval.enchantment.BackstabbingEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class LivingEntityBackstabMixin {

    @ModifyArg(
            method = "damage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"),
            index = 1
    )
    private float onDamage(DamageSource source, float amount) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (source.getAttacker() != null) {
            amount = BackstabbingEnchantment.onKnifeBackstab(self, source.getAttacker(), amount);
        }
        return amount;
    }
}