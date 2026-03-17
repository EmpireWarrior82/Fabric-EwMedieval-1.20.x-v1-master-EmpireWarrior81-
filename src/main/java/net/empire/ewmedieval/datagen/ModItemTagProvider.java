package net.empire.ewmedieval.datagen;

import net.empire.ewmedieval.item.ModItems;
import net.empire.ewmedieval.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "tin_ingots")))
                .add(ModItems.TIN_INGOT);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "silver_ingots")))
                .add(ModItems.SILVER_INGOT);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "bronze_ingots")))
                .add(ModItems.BRONZE_INGOT);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "steel_ingots")))
                .add(ModItems.STEEL_INGOT);

        getOrCreateTagBuilder(ModTags.KNIVES)
                .add(ModItems.BRONZE_KNIFE)
                .add(ModItems.IRON_KNIFE)
                .add(ModItems.GOLDEN_KNIFE)
                .add(ModItems.DIAMOND_KNIFE)
                .add(ModItems.NETHERITE_KNIFE)
                .add(ModItems.FLINT_KNIFE)
                .add(ModItems.STONE_KNIFE);

        getOrCreateTagBuilder(ModTags.AXES)
                .add(ModItems.BRONZE_AXE);

        getOrCreateTagBuilder(ModTags.CUTTING_TOOLS)
                .addTag(ModTags.KNIVES)
                .addTag(ModTags.AXES)
                .forceAddTag(ItemTags.AXES)
                .forceAddTag(ItemTags.SWORDS)
                .add(Items.SHEARS)
                .add(ModItems.BRONZE_SHEARS);

        getOrCreateTagBuilder(ModTags.FLAT_ON_CUTTING_BOARD);
    }

}



