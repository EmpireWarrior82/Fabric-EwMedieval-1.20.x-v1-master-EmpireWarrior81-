package net.empire.ewmedieval.item;

import net.empire.ewmedieval.EwMedieval;
import net.empire.ewmedieval.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.empire.ewmedieval.EwMedieval.MOD_ID;


public class ModItems {


    public static final Item ARKENSTONE = registerItem("arkenstone", new Item(new FabricItemSettings()));
    public static final Item RAW_TIN = registerItem("raw_tin", new Item(new FabricItemSettings()));
    public static final Item TIN_INGOT = registerItem("tin_ingot", new Item(new FabricItemSettings()));
    public static final Item TIN_NUGGET = registerItem("tin_nugget", new Item(new FabricItemSettings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot", new Item(new FabricItemSettings()));
    public static final Item SILVER_NUGGET = registerItem("silver_nugget", new Item(new FabricItemSettings()));
    public static final Item RAW_SILVER = registerItem("raw_silver", new Item(new FabricItemSettings()));
    public static final Item KHAZAD_STEEL_INGOT = registerItem("khazad_steel_ingot", new Item(new FabricItemSettings()));
    public static final Item KHAZAD_STEEL_NUGGET = registerItem("khazad_steel_nugget", new Item(new FabricItemSettings()));
    public static final Item BURZUM_STEEL_INGOT = registerItem("burzum_steel_ingot", new Item(new FabricItemSettings()));
    public static final Item BURZUM_STEEL_NUGGET = registerItem("burzum_steel_nugget", new Item(new FabricItemSettings()));
    public static final Item CRUDE_INGOT = registerItem("crude_ingot", new Item(new FabricItemSettings()));
    public static final Item CRUDE_NUGGET = registerItem("crude_nugget", new Item(new FabricItemSettings()));
    public static final Item EDHEL_STEEL_INGOT = registerItem("edhel_steel_ingot", new Item(new FabricItemSettings()));
    public static final Item EDHEL_STEEL_NUGGET = registerItem("edhel_steel_nugget", new Item(new FabricItemSettings()));
    public static final Item LEAD_NUGGET = registerItem("lead_nugget", new Item(new FabricItemSettings()));
    public static final Item RAW_LEAD = registerItem("raw_lead", new Item(new FabricItemSettings()));
    public static final Item LEAD_INGOT = registerItem("lead_ingot", new Item(new FabricItemSettings()));
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot", new Item(new FabricItemSettings()));
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget", new Item(new FabricItemSettings()));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril", new Item(new FabricItemSettings()));
    public static final Item BRONZE_MIXTURE = registerItem("bronze_mixture", new Item(new FabricItemSettings()));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", new Item(new FabricItemSettings()));
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget", new Item(new FabricItemSettings()));
    public static final Item COPPER_NUGGET = registerItem("copper_nugget", new Item(new FabricItemSettings()));
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));
    public static final Item STEEL_NUGGET = registerItem("steel_nugget", new Item(new FabricItemSettings()));

    public static final Item BRONZE_ASH_MIXTURE = registerItem("bronze_ash_mixture", new Item(new FabricItemSettings()));

    public static final Item RAW_IRON_NUGGET = registerItem("raw_iron_nugget", new Item(new FabricItemSettings()));

    public static final Item ASH = registerItem("ash", new Item(new FabricItemSettings()));
    public static final Item ASH_PIECE = registerItem("ash_piece", new Item(new FabricItemSettings()));
    public static final Item FUR = registerItem("fur", new Item(new FabricItemSettings()));

    public static final Item STONE_PEBBLE = registerItem("stone_pebble", new Item(new FabricItemSettings()));

    public static final Item ROPE = registerItem("rope", new RopeItem(ModBlocks.ROPE, new FabricItemSettings()));

    public static final Item RAW_HORSE = registerItem("raw_horse", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_HORSE)));
    public static final Item RAW_SWAN = registerItem("raw_swan", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_SWAN)));
    public static final Item RAW_GOAT = registerItem("raw_goat", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_GOAT)));
    public static final Item COOKED_HORSE = registerItem("cooked_horse", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_HORSE)));
    public static final Item COOKED_SWAN = registerItem("cooked_swan", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_SWAN)));
    public static final Item COOKED_GOAT = registerItem("cooked_goat", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_GOAT)));
    public static final Item BAT_WING = registerItem("bat_wing", new Item(new FabricItemSettings().food(ModFoodComponents.BAT_WING)));
    public static final Item SMOKED_BAT_WING = registerItem("smoked_bat_wing", new Item(new FabricItemSettings().food(ModFoodComponents.SMOKED_BAT_WING)));
    public static final Item RAW_WOLF = registerItem("raw_wolf", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_WOLF)));
    public static final Item COOKED_WOLF = registerItem("cooked_wolf", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_WOLF)));

    public static final Item CABBAGE = registerItem("cabbage", new Item(new FabricItemSettings().food(ModFoodComponents.CABBAGE)));
    public static final Item TOMATO = registerItem("tomato", new Item(new FabricItemSettings().food(ModFoodComponents.TOMATO)));
    public static final Item ONION = registerItem("onion", new AliasedBlockItem(ModBlocks.ONION_CROP, new FabricItemSettings().food(ModFoodComponents.ONION)));


    public static final Item CRANBERRY = registerItem("cranberry", new Item(new FabricItemSettings().food(ModFoodComponents.CRANBERRY)));
    public static final Item FIG = registerItem("fig", new Item(new FabricItemSettings().food(ModFoodComponents.FIG)));
    public static final Item KIWI = registerItem("kiwi", new Item(new FabricItemSettings().food(ModFoodComponents.KIWI)));
    public static final Item LEMON = registerItem("lemon", new Item(new FabricItemSettings().food(ModFoodComponents.LEMON)));
    public static final Item MANGO = registerItem("mango", new Item(new FabricItemSettings().food(ModFoodComponents.MANGO)));
    public static final Item ORANGE = registerItem("orange", new Item(new FabricItemSettings().food(ModFoodComponents.ORANGE)));
    public static final Item PEACH = registerItem("peach", new Item(new FabricItemSettings().food(ModFoodComponents.PEACH)));
    public static final Item PEAR = registerItem("pear", new Item(new FabricItemSettings().food(ModFoodComponents.PEAR)));
    public static final Item BAKED_PEAR = registerItem("baked_pear", new Item(new FabricItemSettings().food(ModFoodComponents.BAKED_PEAR)));


    public static final Item FRIED_EGG = registerItem("fried_egg", new Item(new FabricItemSettings().food(ModFoodComponents.FRIED_EGG)));
    public static final Item TOMATO_SAUCE = registerItem("tomato_sauce", new Item(new FabricItemSettings().food(ModFoodComponents.TOMATO_SAUCE).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item WHEAT_DOUGH = registerItem("wheat_dough", new Item(new FabricItemSettings().food(ModFoodComponents.WHEAT_DOUGH)));
    public static final Item RAW_PASTA = registerItem("raw_pasta", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_PASTA)));
    public static final Item PIE_CRUST = registerItem("pie_crust", new Item(new FabricItemSettings().food(ModFoodComponents.PIE_CRUST)));
    public static final Item CAKE_SLICE = registerItem("cake_slice", new Item(new FabricItemSettings().food(ModFoodComponents.CAKE_SLICE)));
    public static final Item PUMPKIN_SLICE = registerItem("pumpkin_slice", new Item(new FabricItemSettings().food(ModFoodComponents.PUMPKIN_SLICE)));
    public static final Item CABBAGE_LEAF = registerItem("cabbage_leaf", new Item(new FabricItemSettings().food(ModFoodComponents.CABBAGE_LEAF)));
    public static final Item MINCED_BEEF = registerItem("minced_beef", new Item(new FabricItemSettings().food(ModFoodComponents.MINCED_BEEF)));
    public static final Item BEEF_PATTY = registerItem("beef_patty", new Item(new FabricItemSettings().food(ModFoodComponents.BEEF_PATTY)));
    public static final Item CHICKEN_CUTS = registerItem("chicken_cuts", new Item(new FabricItemSettings().food(ModFoodComponents.CHICKEN_CUTS)));
    public static final Item COOKED_CHICKEN_CUTS = registerItem("cooked_chicken_cuts", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_CHICKEN_CUTS)));
    public static final Item BACON = registerItem("bacon", new Item(new FabricItemSettings().food(ModFoodComponents.BACON)));
    public static final Item COOKED_BACON = registerItem("cooked_bacon", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_BACON)));
    public static final Item COD_SLICE = registerItem("cod_slice", new Item(new FabricItemSettings().food(ModFoodComponents.COD_SLICE)));
    public static final Item COOKED_COD_SLICE = registerItem("cooked_cod_slice", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_COD_SLICE)));
    public static final Item SALMON_SLICE = registerItem("salmon_slice", new Item(new FabricItemSettings().food(ModFoodComponents.SALMON_SLICE)));
    public static final Item COOKED_SALMON_SLICE = registerItem("cooked_salmon_slice", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_SALMON_SLICE)));
    public static final Item MUTTON_CHOPS = registerItem("mutton_chops", new Item(new FabricItemSettings().food(ModFoodComponents.MUTTON_CHOPS)));
    public static final Item COOKED_MUTTON_CHOPS = registerItem("cooked_mutton_chops", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_MUTTON_CHOPS)));
    public static final Item HAM = registerItem("ham", new Item(new FabricItemSettings().food(ModFoodComponents.HAM)));
    public static final Item SMOKED_HAM = registerItem("smoked_ham", new Item(new FabricItemSettings().food(ModFoodComponents.SMOKED_HAM)));

    public static final Item FRUIT_SALAD = registerItem("fruit_salad", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.FRUIT_SALAD).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item GLOW_BERRY_CUSTARD = registerItem("glow_berry_custard",
            new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.GLOW_BERRY_CUSTARD).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16)));


    public static final Item MIXED_SALAD = registerItem("mixed_salad", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.MIXED_SALAD).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item NETHER_SALAD = registerItem("nether_salad", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.NETHER_SALAD).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item BARBECUE_STICK = registerItem("barbecue_stick", new Item(new FabricItemSettings().food(ModFoodComponents.BARBECUE_STICK)));
    public static final Item EGG_SANDWICH = registerItem("egg_sandwich", new Item(new FabricItemSettings().food(ModFoodComponents.EGG_SANDWICH)));
    public static final Item CHICKEN_SANDWICH = registerItem("chicken_sandwich", new Item(new FabricItemSettings().food(ModFoodComponents.CHICKEN_SANDWICH)));
    public static final Item HAMBURGER = registerItem("hamburger", new Item(new FabricItemSettings().food(ModFoodComponents.HAMBURGER)));
    public static final Item BACON_SANDWICH = registerItem("bacon_sandwich", new Item(new FabricItemSettings().food(ModFoodComponents.BACON_SANDWICH)));
    public static final Item MUTTON_WRAP = registerItem("mutton_wrap", new Item(new FabricItemSettings().food(ModFoodComponents.MUTTON_WRAP)));
    public static final Item DUMPLINGS = registerItem("dumplings", new Item(new FabricItemSettings().food(ModFoodComponents.DUMPLINGS)));
    public static final Item STUFFED_POTATO = registerItem("stuffed_potato", new Item(new FabricItemSettings().food(ModFoodComponents.STUFFED_POTATO)));
    public static final Item CABBAGE_ROLLS = registerItem("cabbage_rolls", new Item(new FabricItemSettings().food(ModFoodComponents.CABBAGE_ROLLS)));
    public static final Item SALMON_ROLL = registerItem("salmon_roll", new Item(new FabricItemSettings().food(ModFoodComponents.SALMON_ROLL)));
    public static final Item COD_ROLL = registerItem("cod_roll", new Item(new FabricItemSettings().food(ModFoodComponents.COD_ROLL)));
    public static final Item KELP_ROLL = registerItem("kelp_roll", new Item(new FabricItemSettings().food(ModFoodComponents.KELP_ROLL)));
    public static final Item KELP_ROLL_SLICE = registerItem("kelp_roll_slice", new Item(new FabricItemSettings().food(ModFoodComponents.KELP_ROLL_SLICE)));


    public static final Item COOKED_RICE = registerItem("cooked_rice", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.COOKED_RICE).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item BONE_BROTH = registerItem("bone_broth", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.BONE_BROTH).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item BEEF_STEW = registerItem("beef_stew", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.BEEF_STEW).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item VEGETABLE_SOUP = registerItem("vegetable_soup", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.VEGETABLE_SOUP).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item FISH_STEW = registerItem("fish_stew", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.FISH_STEW).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item CHICKEN_SOUP = registerItem("chicken_soup", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.CHICKEN_SOUP).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item FRIED_RICE = registerItem("fried_rice", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.FRIED_RICE).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item PUMPKIN_SOUP = registerItem("pumpkin_soup", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.PUMPKIN_SOUP).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item BAKED_COD_STEW = registerItem("baked_cod_stew", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.BAKED_COD_STEW).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item NOODLE_SOUP = registerItem("noodle_soup", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.NOODLE_SOUP).recipeRemainder(Items.BOWL).maxCount(16)));


    public static final Item BACON_AND_EGGS = registerItem("bacon_and_eggs", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.BACON_AND_EGGS).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item RATATOUILLE = registerItem("ratatouille", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.RATATOUILLE).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item STEAK_AND_POTATOES = registerItem("steak_and_potatoes", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.STEAK_AND_POTATOES).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item PASTA_WITH_MEATBALLS = registerItem("pasta_with_meatballs", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.PASTA_WITH_MEATBALLS).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item PASTA_WITH_MUTTON_CHOP = registerItem("pasta_with_mutton_chop", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.PASTA_WITH_MUTTON_CHOP).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item MUSHROOM_RICE = registerItem("mushroom_rice", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.MUSHROOM_RICE).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item ROASTED_MUTTON_CHOPS = registerItem("roasted_mutton_chops", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.ROASTED_MUTTON_CHOPS).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item VEGETABLE_NOODLES = registerItem("vegetable_noodles", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.VEGETABLE_NOODLES).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item SQUID_INK_PASTA = registerItem("squid_ink_pasta", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.SQUID_INK_PASTA).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item GRILLED_SALMON = registerItem("grilled_salmon", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.GRILLED_SALMON).recipeRemainder(Items.BOWL).maxCount(16)));


    public static final Item STUFFED_PUMPKIN = registerItem("stuffed_pumpkin", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.STUFFED_PUMPKIN).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item ROAST_CHICKEN = registerItem("roast_chicken", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.ROAST_CHICKEN).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item HONEY_GLAZED_HAM = registerItem("honey_glazed_ham", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.HONEY_GLAZED_HAM).recipeRemainder(Items.BOWL).maxCount(16)));
    public static final Item SHEPHERDS_PIE = registerItem("shepherds_pie", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.SHEPHERDS_PIE).recipeRemainder(Items.BOWL).maxCount(16)));


    public static final Item APPLE_CIDER = registerItem("apple_cider", new ConsumableItem(new FabricItemSettings().food(ModFoodComponents.APPLE_CIDER).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16)));


    public static final Item LEMBAS = registerItem("lembas", new Item(new FabricItemSettings().food(ModFoodComponents.LEMBAS)));
    public static final Item CACTUS_FLESH = registerItem("cactus_flesh", new Item(new FabricItemSettings().food(ModFoodComponents.CACTUS_FLESH)));
    public static final Item CACTUS_STEAK = registerItem("cactus_steak", new Item(new FabricItemSettings().food(ModFoodComponents.CACTUS_STEAK)));

    public static final Item CABBAGE_SEEDS = registerItem("cabbage_seeds",
            new AliasedBlockItem(ModBlocks.CABBAGE_CROP, new FabricItemSettings()));
    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds",
            new AliasedBlockItem(ModBlocks.BUDDING_TOMATO_CROP, new FabricItemSettings()));

    public static final Item RICE_PANICLE = registerItem("rice_panicle",
            new Item(new FabricItemSettings()));
    public static final Item RICE = registerItem("rice",
            new RiceItem(ModBlocks.RICE_CROP, new FabricItemSettings()));

    public static final Item STRAW = registerItem("straw",
            new Item(new FabricItemSettings()));

    public static final Item DOG_FOOD = registerItem("dog_food",
            new DogFoodItem(new FabricItemSettings()
                    .food(ModFoodComponents.DOG_FOOD)
                    .recipeRemainder(Items.BOWL)
                    .maxCount(16)));


    public static final Item CRUDE_PICKAXE = registerItem("crude_pickaxe",
            new PickaxeItem(ModToolMaterial.CRUDE, 1, -2.8f, new FabricItemSettings()));
    public static final Item CRUDE_AXE = registerItem("crude_axe",
            new AxeItem(ModToolMaterial.CRUDE, 6, -3.0f, new FabricItemSettings()));
    public static final Item CRUDE_SHOVEL = registerItem("crude_shovel",
            new ShovelItem(ModToolMaterial.CRUDE, 1, -3.0f, new FabricItemSettings()));
    public static final Item CRUDE_HOE = registerItem("crude_hoe",
            new HoeItem(ModToolMaterial.CRUDE, -1, -1.0f, new FabricItemSettings()));

    public static final Item BRONZE_SHEARS = registerItem("bronze_shears",
            new BronzeShears(new Item.Settings().maxCount(1)));

    public static final Item BRONZE_PICKAXE = registerItem("bronze_pickaxe",
            new PickaxeItem(ModToolMaterial.BRONZE, 1, -2.8f, new FabricItemSettings()));
    public static final Item BRONZE_AXE = registerItem("bronze_axe",
            new AxeItem(ModToolMaterial.BRONZE, 5.5f, -3.0f, new FabricItemSettings()));
    public static final Item BRONZE_SHOVEL = registerItem("bronze_shovel",
            new ShovelItem(ModToolMaterial.BRONZE, 1, -3.0f, new FabricItemSettings()));
    public static final Item BRONZE_HOE = registerItem("bronze_hoe",
            new HoeItem(ModToolMaterial.BRONZE, -1, -1.0f, new FabricItemSettings()));

    public static final Item BRONZE_KNIFE = registerItem("bronze_knife",
            new KnifeItem(ModToolMaterial.BRONZE, 0.5f, -2.0f, new FabricItemSettings()));
    public static final Item IRON_KNIFE = registerItem("iron_knife",
            new KnifeItem(ToolMaterials.IRON, 0.5f, -2.0f, new FabricItemSettings()));
    public static final Item GOLDEN_KNIFE = registerItem("golden_knife",
            new KnifeItem(ToolMaterials.GOLD, 0.5f, -2.0f, new FabricItemSettings()));
    public static final Item DIAMOND_KNIFE = registerItem("diamond_knife",
            new KnifeItem(ToolMaterials.DIAMOND, 0.5f, -2.0f, new FabricItemSettings()));
    public static final Item NETHERITE_KNIFE = registerItem("netherite_knife",
            new KnifeItem(ToolMaterials.NETHERITE, 0.5f, -2.0f, new FabricItemSettings()));
    public static final Item FLINT_KNIFE = registerItem("flint_knife",
            new KnifeItem(ModToolMaterial.FLINT, 0.5f, -2.0f, new FabricItemSettings()));
    public static final Item STONE_KNIFE = registerItem("stone_knife",
            new KnifeItem(ToolMaterials.STONE, 0.5f, -2.0f, new FabricItemSettings()));





    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RAW_TIN);
        entries.add(TIN_INGOT);
        entries.add(SILVER_INGOT);
        entries.add(RAW_SILVER);
        entries.add(KHAZAD_STEEL_INGOT);
        entries.add(KHAZAD_STEEL_NUGGET);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {
        EwMedieval.LOGGER.info("Registering ModItems for EwMedieval " + MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
