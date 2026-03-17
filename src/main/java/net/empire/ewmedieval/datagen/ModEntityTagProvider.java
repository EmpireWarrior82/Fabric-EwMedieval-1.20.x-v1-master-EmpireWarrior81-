package net.empire.ewmedieval.datagen;

import net.empire.ewmedieval.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagProvider<EntityType<?>> {
    public ModEntityTagProvider(FabricDataOutput output,
                                CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(output, net.minecraft.registry.RegistryKeys.ENTITY_TYPE, future);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.DOG_FOOD_USERS)
                .add(EntityType.WOLF);
    }
}