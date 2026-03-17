package net.empire.ewmedieval.datagen;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.item.ModItems;
import net.empire.ewmedieval.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;


public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> TIN_SMELTABLES = List.of(ModItems.RAW_TIN,
            ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, ModBlocks.SLATE_TIN_ORE);

    private static final List<ItemConvertible> SILVER_SMELTABLES = List.of(ModItems.RAW_SILVER,
            ModBlocks.DEEPSLATE_SILVER_ORE);

    private static final List<ItemConvertible> MITHRIL_SMELTABLES = List.of(ModItems.RAW_MITHRIL);

    private static final List<ItemConvertible> LEAD_SMELTABLES = List.of(ModItems.RAW_LEAD,
                ModBlocks.DEEPSLATE_LEAD_ORE);

    private static final List<ItemConvertible> BRONZE_SMELTABLES = List.of(ModItems.BRONZE_MIXTURE);

    private static final List<ItemConvertible> CRUDE_SMELTABLES = List.of(ModItems.BRONZE_ASH_MIXTURE);

    private static final List<ItemConvertible> ASH_SMELTABLES = List.of(Items.BEEF, Items.PORKCHOP,
            Items.MUTTON, Items.RABBIT, Items.LEATHER, Items.BONE, Items.PUMPKIN, Items.MELON, Items.CACTUS,
            Items.CAKE, Items.PUMPKIN_PIE, Items.BOOK, Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK,
            Items.DRIED_KELP_BLOCK, Items.WHITE_WOOL, Items.LIGHT_GRAY_WOOL,
            Items.GRAY_WOOL, Items.BLACK_WOOL, Items.BROWN_WOOL, Items.RED_WOOL, Items.ORANGE_WOOL, Items.YELLOW_WOOL,
            Items.LIME_WOOL, Items.GREEN_WOOL, Items.CYAN_WOOL, Items.LIGHT_BLUE_WOOL, Items.BLUE_WOOL, Items.PURPLE_WOOL,
            Items.MAGENTA_WOOL, Items.PINK_WOOL, Items.WHITE_BANNER, Items.LIGHT_GRAY_BANNER, Items.GRAY_BANNER,
            Items.BLACK_BANNER, Items.BROWN_BANNER, Items.RED_BANNER, Items.ORANGE_BANNER, Items.YELLOW_BANNER,
            Items.LIME_BANNER, Items.GREEN_BANNER, Items.CYAN_BANNER, Items.LIGHT_BLUE_BANNER, Items.BLUE_BANNER,
            Items.PURPLE_BANNER, Items.MAGENTA_BANNER, Items.PINK_BANNER, Items.WHITE_CARPET, Items.LIGHT_GRAY_CARPET,
            Items.GRAY_CARPET, Items.BLACK_CARPET, Items.BROWN_CARPET, Items.RED_CARPET, Items.ORANGE_CARPET,
            Items.YELLOW_CARPET, Items.LIME_CARPET, Items.GREEN_CARPET, Items.CYAN_CARPET, Items.LIGHT_BLUE_CARPET,
            Items.BLUE_CARPET, Items.PURPLE_CARPET, Items.MAGENTA_CARPET, Items.PINK_CARPET);

    private static final List<ItemConvertible> ASH_PIECE_SMELTABLES = List.of(Items.WHEAT, Items.BEETROOT, Items.CARROT,
            Items.POTATO, Items.POISONOUS_POTATO, Items.APPLE, Items.MELON_SLICE, Items.SWEET_BERRIES, Items.GLOW_BERRIES,
            Items.BROWN_MUSHROOM, Items.RED_MUSHROOM, Items.BAMBOO, Items.SUGAR_CANE, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS,
            Items.MELON_SEEDS, Items.WHEAT_SEEDS, Items.ROTTEN_FLESH, Items.CHICKEN, Items.COD, Items.SALMON, Items.SPIDER_EYE,
            Items.STRING, Items.FEATHER, Items.PAPER, Items.RABBIT_HIDE, Items.BREAD, Items.PUFFERFISH, Items.TROPICAL_FISH,
            Items.DRIED_KELP, Items.COOKIE, ModItems.PEACH);


    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    private void offerSmokingRecipe(
            Consumer<RecipeJsonProvider> exporter,
            ItemConvertible input,
            ItemConvertible output,
            float experience,
            int cookingTime,
            String group,
            String recipeName) {

        CookingRecipeJsonBuilder.createSmoking(
                        Ingredient.ofItems(input),
                        RecipeCategory.FOOD,
                        output,
                        experience,
                        cookingTime)
                .group(group)
                .criterion("has_" + input.asItem().getName().getString().toLowerCase(), conditionsFromItem(input))
                .offerTo(exporter, new Identifier("ewmedieval", recipeName));
    }

    private void offerSmeltingRecipe(
            Consumer<RecipeJsonProvider> exporter,
            ItemConvertible input,
            ItemConvertible output,
            float experience,
            int cookingTime,
            String group,
            String recipeName) {

        CookingRecipeJsonBuilder.createSmelting(
                        Ingredient.ofItems(input),
                        RecipeCategory.FOOD,
                        output,
                        experience,
                        cookingTime)
                .group(group)
                .criterion("has_" + input.asItem().getName().getString().toLowerCase(), conditionsFromItem(input))
                .offerTo(exporter, new Identifier("ewmedieval", recipeName));
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {


// Ash Recipes


        offerSmelting(exporter, ASH_SMELTABLES, RecipeCategory.MISC, ModItems.ASH,
                0.16f, 115, "ash");

        offerSmelting(exporter, ASH_PIECE_SMELTABLES, RecipeCategory.MISC, ModItems.ASH_PIECE,
                0.15f, 100, "ash");



        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ASH_PIECE, 5)
                .input(ModItems.ASH)
                .criterion("has_ash", conditionsFromItem(ModItems.ASH))
                .offerTo(exporter, new Identifier("ewmedieval", "ash_from_ash_piece"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ASH)
                .input(ModItems.ASH_PIECE, 5)
                .criterion("has_ash_piece", conditionsFromItem(ModItems.ASH_PIECE))
                .offerTo(exporter, new Identifier("ewmedieval", "ash_piece_from_ash"));

//TODO RECIPE FIXEN

// Metals/ores
        offerSmelting(exporter, TIN_SMELTABLES, RecipeCategory.MISC, ModItems.TIN_INGOT,
                0.7f, 200, "tin_ingot");
        offerBlasting(exporter, TIN_SMELTABLES, RecipeCategory.MISC, ModItems.TIN_INGOT,
                0.7f, 100, "tin_ingot");
        offerSmelting(exporter, SILVER_SMELTABLES, RecipeCategory.MISC, ModItems.SILVER_INGOT,
                0.9f, 200, "silver_ingot");
        offerBlasting(exporter, SILVER_SMELTABLES, RecipeCategory.MISC, ModItems.SILVER_INGOT,
                0.9f, 100, "silver_ingot");
        offerSmelting(exporter, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT,
                0.9f, 200, "silver_ingot");
        offerBlasting(exporter, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT,
                0.9f, 100, "silver_ingot");
        offerSmelting(exporter, MITHRIL_SMELTABLES, RecipeCategory.MISC, ModItems.MITHRIL_INGOT,
                0.9f, 200, "silver_ingot");
        offerBlasting(exporter, MITHRIL_SMELTABLES, RecipeCategory.MISC, ModItems.MITHRIL_INGOT,
                0.9f, 100, "silver_ingot");
        offerSmelting(exporter, BRONZE_SMELTABLES, RecipeCategory.MISC, ModItems.BRONZE_INGOT,
                0.7f, 200, "bronze_ingot");
        offerBlasting(exporter, BRONZE_SMELTABLES, RecipeCategory.MISC, ModItems.BRONZE_INGOT,
                0.7f, 100, "bronze_ingot");

    offerSmelting(exporter, CRUDE_SMELTABLES, RecipeCategory.MISC, ModItems.CRUDE_INGOT,
                0.7f, 200, "crude_ingot");
        offerBlasting(exporter, CRUDE_SMELTABLES, RecipeCategory.MISC, ModItems.CRUDE_INGOT,
                0.7f, 100, "crude_ingot");

        

// Food
        offerSmokingRecipe(exporter, ModItems.RAW_HORSE, ModItems.COOKED_HORSE, 0.35f, 100, "cooked_horse", "cooked_horse_smoking");
        offerSmeltingRecipe(exporter, ModItems.RAW_HORSE, ModItems.COOKED_HORSE, 0.35f, 200, "cooked_horse", "cooked_horse_smelting");
        offerSmokingRecipe(exporter, ModItems.RAW_SWAN, ModItems.COOKED_SWAN, 0.35f, 100, "cooked_swan", "cooked_swan_smoking");
        offerSmeltingRecipe(exporter, ModItems.RAW_SWAN, ModItems.COOKED_SWAN, 0.35f, 200, "cooked_swan", "cooked_swan_smelting");
        offerSmokingRecipe(exporter, ModItems.RAW_GOAT, ModItems.COOKED_GOAT, 0.35f, 100, "cooked_goat", "cooked_goat_smoking");
        offerSmeltingRecipe(exporter, ModItems.RAW_GOAT, ModItems.COOKED_GOAT, 0.35f, 200, "cooked_goat", "cooked_goat_smelting");
        offerSmokingRecipe(exporter, ModItems.CACTUS_FLESH, ModItems.CACTUS_STEAK, 0.35f, 100, "cactus_steak", "cactus_steak_smoking");
        offerSmeltingRecipe(exporter, ModItems.CACTUS_FLESH, ModItems.CACTUS_STEAK, 0.35f, 200, "cactus_steak", "cactus_steak_smelting");
        offerSmokingRecipe(exporter, ModItems.BAT_WING, ModItems.SMOKED_BAT_WING, 0.35f, 100, "smoked_bat_wing", "smoked_bat_wing_smoking");
        offerSmokingRecipe(exporter, ModItems.PEAR, ModItems.BAKED_PEAR, 0.35f, 100, "baked_pear", "baked_pear_smoking");
        offerSmeltingRecipe(exporter, ModItems.PEAR, ModItems.BAKED_PEAR, 0.35f, 200, "baked_pear", "baked_pear_smelting");
        offerSmokingRecipe(exporter, ModItems.RAW_WOLF, ModItems.COOKED_WOLF, 0.35f, 100, "cooked_wolf", "cooked_wolf_smoking");
        offerSmeltingRecipe(exporter, ModItems.RAW_WOLF, ModItems.COOKED_WOLF, 0.35f, 200, "cooked_wolf", "cooked_wolf_smelting");

// Crafting Blocks
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.TIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_TIN, RecipeCategory.DECORATIONS,
                ModBlocks.RAW_TIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.SILVER_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.SILVER_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_SILVER, RecipeCategory.DECORATIONS,
                ModBlocks.RAW_SILVER_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.KHAZAD_STEEL_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.KHAZAD_STEEL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.BURZUM_STEEL_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.BURZUM_STEEL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.EDHEL_STEEL_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.EDHEL_STEEL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.CRUDE_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.CRUDE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_LEAD, RecipeCategory.DECORATIONS,
                ModBlocks.RAW_LEAD_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.LEAD_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.LEAD_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_MITHRIL, RecipeCategory.DECORATIONS,
                ModBlocks.RAW_MITHRIL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MITHRIL_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.MITHRIL_BLOCK);



// Nugget → Ingot
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.KHAZAD_STEEL_INGOT)
                .input(ModItems.KHAZAD_STEEL_NUGGET, 9)
                .criterion("has_khazad_steel_nugget", conditionsFromItem(ModItems.KHAZAD_STEEL_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "khazad_steel_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURZUM_STEEL_INGOT)
                .input(ModItems.BURZUM_STEEL_NUGGET, 9)
                .criterion("has_burzum_steel_nugget", conditionsFromItem(ModItems.BURZUM_STEEL_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "burzum_steel_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EDHEL_STEEL_INGOT)
                .input(ModItems.EDHEL_STEEL_NUGGET, 9)
                .criterion("has_edhel_steel_nugget", conditionsFromItem(ModItems.EDHEL_STEEL_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "edhel_steel_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRUDE_INGOT)
                .input(ModItems.CRUDE_NUGGET, 9)
                .criterion("has_crude_nugget", conditionsFromItem(ModItems.CRUDE_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "crude_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LEAD_INGOT)
                .input(ModItems.LEAD_NUGGET, 9)
                .criterion("has_lead_nugget", conditionsFromItem(ModItems.LEAD_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "lead_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MITHRIL_INGOT)
                .input(ModItems.MITHRIL_NUGGET, 9)
                .criterion("has_mithril_nugget", conditionsFromItem(ModItems.MITHRIL_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "mithril_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TIN_INGOT)
                .input(ModItems.TIN_NUGGET, 9)
                .criterion("has_tin_nugget", conditionsFromItem(ModItems.TIN_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "tin_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SILVER_INGOT)
                .input(ModItems.SILVER_NUGGET, 9)
                .criterion("has_silver_nugget", conditionsFromItem(ModItems.SILVER_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "silver_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_INGOT)
                .input(ModItems.BRONZE_NUGGET, 9)
                .criterion("has_bronze_nugget", conditionsFromItem(ModItems.BRONZE_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "bronze_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.COPPER_INGOT)
                .input(ModItems.COPPER_NUGGET, 9)
                .criterion("has_copper_nugget", conditionsFromItem(ModItems.COPPER_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "copper_ingot_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_IRON)
                .input(ModItems.RAW_IRON_NUGGET, 9)
                .criterion("has_raw_iron_nugget", conditionsFromItem(ModItems.RAW_IRON_NUGGET))
                .offerTo(exporter, new Identifier("ewmedieval", "raw_iron_from_raw_iron_nuggets"));

// Ingot → Nugget
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.KHAZAD_STEEL_NUGGET, 9)
                .input(ModItems.KHAZAD_STEEL_INGOT)
                .criterion("has_khazad_steel_ingot", conditionsFromItem(ModItems.KHAZAD_STEEL_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "khazad_steel_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BURZUM_STEEL_NUGGET, 9)
                .input(ModItems.BURZUM_STEEL_INGOT)
                .criterion("has_burzum_steel_ingot", conditionsFromItem(ModItems.BURZUM_STEEL_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "burzum_steel_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EDHEL_STEEL_NUGGET, 9)
                .input(ModItems.EDHEL_STEEL_INGOT)
                .criterion("has_edhel_steel_ingot", conditionsFromItem(ModItems.EDHEL_STEEL_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "edhel_steel_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRUDE_NUGGET, 9)
                .input(ModItems.CRUDE_INGOT)
                .criterion("has_crude_ingot", conditionsFromItem(ModItems.CRUDE_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "crude_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LEAD_NUGGET, 9)
                .input(ModItems.LEAD_INGOT)
                .criterion("has_lead_ingot", conditionsFromItem(ModItems.LEAD_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "lead_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MITHRIL_NUGGET, 9)
                .input(ModItems.MITHRIL_INGOT)
                .criterion("has_mithril_ingot", conditionsFromItem(ModItems.MITHRIL_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "mithril_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TIN_NUGGET, 9)
                .input(ModItems.TIN_INGOT)
                .criterion("has_tin_ingot", conditionsFromItem(ModItems.TIN_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "tin_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SILVER_NUGGET, 9)
                .input(ModItems.SILVER_INGOT)
                .criterion("has_silver_ingot", conditionsFromItem(ModItems.SILVER_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "silver_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_NUGGET, 9)
                .input(ModItems.BRONZE_INGOT)
                .criterion("has_bronze_ingot", conditionsFromItem(ModItems.BRONZE_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "bronze_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.COPPER_NUGGET, 9)
                .input(Items.COPPER_INGOT)
                .criterion("has_copper_ingot", conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, new Identifier("ewmedieval", "copper_nugget_from_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_IRON_NUGGET, 9)
                .input(Items.RAW_IRON)
                .criterion("has_raw_iron", conditionsFromItem(Items.RAW_IRON))
                .offerTo(exporter, new Identifier("ewmedieval", "raw_iron_nugget_from_raw_iron"));




        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_MIXTURE, 1)
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .input('#', ModItems.COPPER_NUGGET)
                .input('S', ModItems.TIN_NUGGET)
                .criterion("has_copper_nugget", conditionsFromItem(ModItems.COPPER_NUGGET))
                .criterion("has_tin_nugget", conditionsFromItem(ModItems.TIN_NUGGET))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BRONZE_ASH_MIXTURE, 1)
                .input(ModItems.BRONZE_MIXTURE)
                .input(ModItems.ASH)
                .criterion("has_bronze_mixture", conditionsFromItem(ModItems.BRONZE_MIXTURE))
                .criterion("has_ash", conditionsFromItem(ModItems.ASH))
                .offerTo(exporter);





// Cut recipes
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.CUT_LEAD,
                ModBlocks.LEAD_BLOCK , 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.CUT_SILVER,
                ModBlocks.SILVER_BLOCK , 1);


// Stone version Block recipes
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_SLATE,
                ModBlocks.SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_STAIRS,
                ModBlocks.SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_WALL,
                ModBlocks.SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_BRICKS,
                ModBlocks.POLISHED_SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_TRAPDOOR,
                ModBlocks.SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.COBBLED_SLATE,
                ModBlocks.SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_SLAB,
                ModBlocks.SLATE , 2);


        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_SLATE_STAIRS,
                ModBlocks.POLISHED_SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_SLATE_WALL,
                ModBlocks.POLISHED_SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_SLATE_SLAB,
                ModBlocks.POLISHED_SLATE , 2);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_BRICKS_STAIRS,
                ModBlocks.SLATE_BRICKS , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_BRICKS_WALL,
                ModBlocks.SLATE_BRICKS , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SLATE_BRICKS_SLAB,
                ModBlocks.SLATE_BRICKS , 2);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.COBBLED_SLATE_STAIRS,
                ModBlocks.COBBLED_SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.COBBLED_SLATE_WALL,
                ModBlocks.COBBLED_SLATE , 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.COBBLED_SLATE_SLAB,
                ModBlocks.COBBLED_SLATE , 2);

        createStairsRecipe(ModBlocks.SLATE_STAIRS, Ingredient.ofItems(ModBlocks.SLATE))
                .criterion("has_slate", conditionsFromItem(ModBlocks.SLATE.asItem()))
                .offerTo(exporter);

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_SLAB, Ingredient.ofItems(ModBlocks.SLATE))
                .criterion("has_slate", conditionsFromItem(ModBlocks.SLATE.asItem()))
                .offerTo(exporter);

        createStairsRecipe(ModBlocks.POLISHED_SLATE_STAIRS, Ingredient.ofItems(ModBlocks.POLISHED_SLATE))
                .criterion("has_polished_slate", conditionsFromItem(ModBlocks.POLISHED_SLATE.asItem()))
                .offerTo(exporter);

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SLATE_SLAB, Ingredient.ofItems(ModBlocks.POLISHED_SLATE))
                .criterion("has_polished_slate", conditionsFromItem(ModBlocks.POLISHED_SLATE.asItem()))
                .offerTo(exporter);

        createStairsRecipe(ModBlocks.SLATE_BRICKS_STAIRS, Ingredient.ofItems(ModBlocks.SLATE_BRICKS))
                .criterion("has_slate_bricks", conditionsFromItem(ModBlocks.SLATE_BRICKS.asItem()))
                .offerTo(exporter);

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_BRICKS_SLAB, Ingredient.ofItems(ModBlocks.SLATE_BRICKS))
                .criterion("has_slate_bricks", conditionsFromItem(ModBlocks.SLATE_BRICKS.asItem()))
                .offerTo(exporter);

        createStairsRecipe(ModBlocks.COBBLED_SLATE_STAIRS, Ingredient.ofItems(ModBlocks.COBBLED_SLATE))
                .criterion("has_cobbled_slate", conditionsFromItem(ModBlocks.COBBLED_SLATE.asItem()))
                .offerTo(exporter);

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLED_SLATE_SLAB, Ingredient.ofItems(ModBlocks.COBBLED_SLATE))
                .criterion("has_cobbled_slate", conditionsFromItem(ModBlocks.COBBLED_SLATE.asItem()))
                .offerTo(exporter);

        createPressurePlateRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_PRESSURE_PLATE, Ingredient.ofItems(ModBlocks.SLATE))
                .criterion("has_slate", conditionsFromItem(ModBlocks.SLATE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_WALL, 6)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.SLATE)
                .criterion("has_slate", conditionsFromItem(ModBlocks.SLATE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SLATE_WALL, 6)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.POLISHED_SLATE)
                .criterion("has_polished_slate", conditionsFromItem(ModBlocks.POLISHED_SLATE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_BRICKS_WALL, 6)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.SLATE_BRICKS)
                .criterion("has_slate_bricks", conditionsFromItem(ModBlocks.SLATE_BRICKS.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLED_SLATE_WALL, 6)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.COBBLED_SLATE)
                .criterion("has_cobbled_slate_bricks", conditionsFromItem(ModBlocks.COBBLED_SLATE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_TRAPDOOR, 3)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.SLATE)
                .criterion("has_slate", conditionsFromItem(ModBlocks.SLATE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SLATE, 4)
                .pattern("##")
                .pattern("##")
                .input('#', ModBlocks.SLATE)
                .criterion("has_slate", conditionsFromItem(ModBlocks.SLATE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLATE_BRICKS, 4)
                .pattern("##")
                .pattern("##")
                .input('#', ModBlocks.POLISHED_SLATE)
                .criterion("has_polished_slate", conditionsFromItem(ModBlocks.POLISHED_SLATE.asItem()))
                .offerTo(exporter);

/*
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FORGE, 1)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', )
*/

//tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BRONZE_PICKAXE, 1)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .input('#', ModItems.BRONZE_INGOT)
                .input('S', Items.STICK)
                .criterion("has_bronze_ingot", conditionsFromItem(ModItems.BRONZE_INGOT))
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BRONZE_AXE, 1)
                .pattern("##")
                .pattern("#S")
                .pattern(" S")
                .input('#', ModItems.BRONZE_INGOT)
                .input('S', Items.STICK)
                .criterion("has_bronze_ingot", conditionsFromItem(ModItems.BRONZE_INGOT))
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BRONZE_SHOVEL, 1)
                .pattern("#")
                .pattern("S")
                .pattern("S")
                .input('#', ModItems.BRONZE_INGOT)
                .input('S', Items.STICK)
                .criterion("has_bronze_ingot", conditionsFromItem(ModItems.BRONZE_INGOT))
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BRONZE_HOE, 1)
                .pattern("##")
                .pattern(" S")
                .pattern(" S")
                .input('#', ModItems.BRONZE_INGOT)
                .input('S', Items.STICK)
                .criterion("has_bronze_ingot", conditionsFromItem(ModItems.BRONZE_INGOT))
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BRONZE_SHEARS, 1)
                .pattern(" #")
                .pattern("# ")

                .input('#', ModItems.BRONZE_INGOT)
                .criterion("has_bronze_ingot", conditionsFromItem(ModItems.BRONZE_INGOT))
                .offerTo(exporter);




    }
}
