package net.empire.ewmedieval.mixin;

import net.empire.ewmedieval.item.CustomEnchantingBehaviorItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantementMixin {

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void onIsAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof CustomEnchantingBehaviorItem custom) {
            cir.setReturnValue(custom.canApplyAtEnchantingTable(stack, (Enchantment)(Object)this));
        }
    }


}
