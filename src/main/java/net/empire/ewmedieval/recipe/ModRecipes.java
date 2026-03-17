package net.empire.ewmedieval.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static RecipeType<CuttingBoardRecipe> CUTTING_TYPE;
    public static CuttingBoardRecipe.Serializer CUTTING_SERIALIZER;

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_TYPE,
                new Identifier("ewmedieval", ForgeRecipe.Type.ID), ForgeRecipe.Type.INSTANCE);
        
        Registry.register(Registries.RECIPE_SERIALIZER,
                ForgeRecipe.Serializer.ID, ForgeRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                EarlyForgeRecipe.Serializer.ID, EarlyForgeRecipe.Serializer.INSTANCE);


        CUTTING_TYPE = Registry.register(
                Registries.RECIPE_TYPE,
                new Identifier("ewmedieval", "cutting"),
                new RecipeType<CuttingBoardRecipe>() {
                    @Override
                    public String toString() { return "ewmedieval:cutting"; }
                });

        CUTTING_SERIALIZER = Registry.register(
                Registries.RECIPE_SERIALIZER,
                new Identifier("ewmedieval", "cutting"),
                new CuttingBoardRecipe.Serializer());
    }
}
