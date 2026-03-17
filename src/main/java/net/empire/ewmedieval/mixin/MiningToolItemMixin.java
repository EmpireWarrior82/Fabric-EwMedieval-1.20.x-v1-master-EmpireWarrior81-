package net.empire.ewmedieval.mixin;

import net.empire.ewmedieval.datagen.ModBlockTagProvider;
import net.empire.ewmedieval.item.ModToolMaterial;
import net.minecraft.block.BlockState;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin extends ToolItem {
    public MiningToolItemMixin(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Inject(method = "isSuitableFor", at = @At("HEAD"), cancellable = true)
    private void injectCustomSuitability(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        ToolMaterial mat = this.getMaterial();


        if (state.isIn(ModBlockTagProvider.NEEDS_BRONZE_TOOL)) {

            if (mat == ModToolMaterial.BRONZE ||
                    mat == ToolMaterials.IRON ||
                    mat == ToolMaterials.DIAMOND ||
                    mat == ToolMaterials.NETHERITE) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
            return;
        }


        if (mat == ModToolMaterial.BRONZE) {

            if (state.isIn(BlockTags.NEEDS_IRON_TOOL) || state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
                cir.setReturnValue(false);
            }

        }
    }
}
