package net.empire.ewmedieval.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;   // 1 minute
    public static final int MEDIUM_DURATION = 3600;  // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes


    public static final FoodComponent RAW_HORSE = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).meat().build();
    public static final FoodComponent RAW_SWAN = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).meat().build();
    public static final FoodComponent RAW_GOAT = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).meat().build();
    public static final FoodComponent COOKED_HORSE = new FoodComponent.Builder().hunger(8).saturationModifier(0.8f).meat().build();
    public static final FoodComponent COOKED_SWAN = new FoodComponent.Builder().hunger(7).saturationModifier(0.8f).meat().build();
    public static final FoodComponent COOKED_GOAT = new FoodComponent.Builder().hunger(6).saturationModifier(0.7f).meat().build();
    public static final FoodComponent BAT_WING = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).meat().build();
    public static final FoodComponent SMOKED_BAT_WING = new FoodComponent.Builder().hunger(6).saturationModifier(0.7f).meat().build();
    public static final FoodComponent RAW_WOLF = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).meat().build();
    public static final FoodComponent COOKED_WOLF = new FoodComponent.Builder().hunger(6).saturationModifier(0.7f).meat().build();

    public static final FoodComponent CABBAGE = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.4f).build();
    public static final FoodComponent TOMATO = new FoodComponent.Builder()
            .hunger(1).saturationModifier(0.3f).build();
    public static final FoodComponent ONION = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.4f).build();

    public static final FoodComponent FRIED_EGG = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.4f).build();
    public static final FoodComponent TOMATO_SAUCE = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.4f).build();
    public static final FoodComponent WHEAT_DOUGH = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.3f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).build();
    public static final FoodComponent RAW_PASTA = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.3f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).build();
    public static final FoodComponent PIE_CRUST = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.2f).build();
    public static final FoodComponent PUMPKIN_SLICE = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.3f).build();
    public static final FoodComponent CABBAGE_LEAF = new FoodComponent.Builder()
            .hunger(1).saturationModifier(0.4f).snack().build();
    public static final FoodComponent MINCED_BEEF = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.3f).meat().snack().build();
    public static final FoodComponent BEEF_PATTY = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.8f).meat().snack().build();
    public static final FoodComponent CHICKEN_CUTS = new FoodComponent.Builder()
            .hunger(1).saturationModifier(0.3f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F)
            .meat().snack().build();
    public static final FoodComponent COOKED_CHICKEN_CUTS = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.6f).meat().snack().build();
    public static final FoodComponent BACON = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.3f).meat().snack().build();
    public static final FoodComponent COOKED_BACON = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.8f).meat().snack().build();
    public static final FoodComponent COD_SLICE = new FoodComponent.Builder()
            .hunger(1).saturationModifier(0.1f).snack().build();
    public static final FoodComponent COOKED_COD_SLICE = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.5f).snack().build();
    public static final FoodComponent SALMON_SLICE = new FoodComponent.Builder()
            .hunger(1).saturationModifier(0.1f).snack().build();
    public static final FoodComponent COOKED_SALMON_SLICE = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.8f).snack().build();
    public static final FoodComponent MUTTON_CHOPS = new FoodComponent.Builder()
            .hunger(1).saturationModifier(0.3f).meat().snack().build();
    public static final FoodComponent COOKED_MUTTON_CHOPS = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.8f).meat().snack().build();
    public static final FoodComponent HAM = new FoodComponent.Builder()
            .hunger(5).saturationModifier(0.3f).meat().build();
    public static final FoodComponent SMOKED_HAM = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.8f).meat().build();

    public static final FoodComponent POPSICLE = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.2f).snack().alwaysEdible().build();
    public static final FoodComponent COOKIES = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.1f).snack().build();
    public static final FoodComponent CAKE_SLICE = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.1f).snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 0, false, false), 1.0F).build();
    public static final FoodComponent PIE_SLICE = new FoodComponent.Builder()
            .hunger(3).saturationModifier(0.3f).snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 0, false, false), 1.0F).build();
    public static final FoodComponent FRUIT_SALAD = new FoodComponent.Builder()
            .hunger(6).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1.0F).build();
    public static final FoodComponent GLOW_BERRY_CUSTARD = new FoodComponent.Builder()
            .hunger(7).saturationModifier(0.6f).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0), 1.0F).build();

    public static final FoodComponent MIXED_SALAD = new FoodComponent.Builder()
            .hunger(6).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1.0F).build();
    public static final FoodComponent NETHER_SALAD = new FoodComponent.Builder()
            .hunger(5).saturationModifier(0.4f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 240, 0), 0.3F).build();
    public static final FoodComponent BARBECUE_STICK = new FoodComponent.Builder()
            .hunger(8).saturationModifier(0.9f).build();
    public static final FoodComponent EGG_SANDWICH = new FoodComponent.Builder()
            .hunger(8).saturationModifier(0.8f).build();
    public static final FoodComponent CHICKEN_SANDWICH = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.8f).build();
    public static final FoodComponent HAMBURGER = new FoodComponent.Builder()
            .hunger(11).saturationModifier(0.8f).build();
    public static final FoodComponent BACON_SANDWICH = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.8f).build();
    public static final FoodComponent MUTTON_WRAP = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.8f).build();
    public static final FoodComponent DUMPLINGS = new FoodComponent.Builder()
            .hunger(8).saturationModifier(0.8f).build();
    public static final FoodComponent STUFFED_POTATO = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.7f).build();
    public static final FoodComponent CABBAGE_ROLLS = new FoodComponent.Builder()
            .hunger(5).saturationModifier(0.5f).build();
    public static final FoodComponent SALMON_ROLL = new FoodComponent.Builder()
            .hunger(7).saturationModifier(0.6f).build();
    public static final FoodComponent COD_ROLL = new FoodComponent.Builder()
            .hunger(7).saturationModifier(0.6f).build();
    public static final FoodComponent KELP_ROLL = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.6f).build();
    public static final FoodComponent KELP_ROLL_SLICE = new FoodComponent.Builder()
            .hunger(6).saturationModifier(0.5f).snack().build();


    public static final FoodComponent COOKED_RICE = new FoodComponent.Builder()
            .hunger(6).saturationModifier(0.4f).build();
    public static final FoodComponent BONE_BROTH = new FoodComponent.Builder()
            .hunger(8).saturationModifier(0.7f).build();
    public static final FoodComponent BEEF_STEW = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent VEGETABLE_SOUP = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent FISH_STEW = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent CHICKEN_SOUP = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent FRIED_RICE = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent PUMPKIN_SOUP = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent BAKED_COD_STEW = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent NOODLE_SOUP = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();


    public static final FoodComponent BACON_AND_EGGS = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.6f).build();
    public static final FoodComponent RATATOUILLE = new FoodComponent.Builder()
            .hunger(10).saturationModifier(0.6f).build();
    public static final FoodComponent STEAK_AND_POTATOES = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent PASTA_WITH_MEATBALLS = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent PASTA_WITH_MUTTON_CHOP = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent MUSHROOM_RICE = new FoodComponent.Builder()
            .hunger(12).saturationModifier(0.8f).build();
    public static final FoodComponent ROASTED_MUTTON_CHOPS = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent VEGETABLE_NOODLES = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent SQUID_INK_PASTA = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent GRILLED_SALMON = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();


    public static final FoodComponent ROAST_CHICKEN = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent STUFFED_PUMPKIN = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent HONEY_GLAZED_HAM = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();
    public static final FoodComponent SHEPHERDS_PIE = new FoodComponent.Builder()
            .hunger(14).saturationModifier(0.75f).build();


    public static final FoodComponent APPLE_CIDER = new FoodComponent.Builder()
            .alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200, 0), 1.0F).build();

    public static final FoodComponent DOG_FOOD = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.2f).meat().build();


    public static final FoodComponent LEMBAS = new FoodComponent.Builder().hunger(20).saturationModifier(1f).build();

    public static final FoodComponent CACTUS_FLESH = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).build();
    public static final FoodComponent CACTUS_STEAK = new FoodComponent.Builder().hunger(7).saturationModifier(0.8f).build();
    public static final FoodComponent CRANBERRY = new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build();
    public static final FoodComponent FIG = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).build();
    public static final FoodComponent KIWI = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).build();
    public static final FoodComponent LEMON = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build();
    public static final FoodComponent MANGO = new FoodComponent.Builder().hunger(4).saturationModifier(0.6f).build();
    public static final FoodComponent ORANGE = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent PEACH = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent PEAR = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent BAKED_PEAR = new FoodComponent.Builder().hunger(5).saturationModifier(0.7f).build();


}
