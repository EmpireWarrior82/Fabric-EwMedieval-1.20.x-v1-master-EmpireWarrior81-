package net.empire.ewmedieval.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;

/**
 * A recipe output that has an optional chance and fortune bonus.
 *
 * JSON format:
 * {
 *   "item": "minecraft:apple",
 *   "count": 2,           // optional, default 1
 *   "chance": 0.5,        // optional, default 1.0 (always drop)
 *   "max_chance": 1.0     // optional, used with fortune scaling
 * }
 */
public class ChanceResult {

    public static final ChanceResult EMPTY = new ChanceResult(ItemStack.EMPTY, 1.0f, 0.0f);

    private final ItemStack stack;
    /** Base drop chance (0.0 – 1.0). 1.0 means always drop. */
    private final float chance;
    /**
     * How much each fortune level raises the chance toward 1.0.
     * E.g. 0.1 means +10% per fortune level.
     */
    private final float fortuneChanceBonus;

    public ChanceResult(ItemStack stack, float chance, float fortuneChanceBonus) {
        this.stack = stack;
        this.chance = chance;
        this.fortuneChanceBonus = fortuneChanceBonus;
    }

    /** Returns the raw ItemStack (ignores chance). */
    public ItemStack getStack() {
        return stack;
    }

    /**
     * Rolls against the chance (plus fortune bonus) and returns the stack if
     * successful, or ItemStack.EMPTY if the roll failed.
     */
    public ItemStack rollOutput(Random random, int fortuneLevel) {
        float effectiveChance = Math.min(1.0f, chance + fortuneChanceBonus * fortuneLevel);
        if (effectiveChance >= 1.0f || random.nextFloat() < effectiveChance) {
            return stack.copy();
        }
        return ItemStack.EMPTY;
    }

    // -------------------------------------------------------------------------
    // JSON serialization
    // -------------------------------------------------------------------------

    public static ChanceResult deserialize(JsonElement element) {
        JsonObject json = element.getAsJsonObject();

        // Parse the item + count using Minecraft's built-in helper
        ItemStack stack = parseItemStack(json);
        float chance = JsonHelper.getFloat(json, "chance", 1.0f);
        float fortuneBonus = JsonHelper.getFloat(json, "fortune_chance_bonus", 0.0f);

        return new ChanceResult(stack, chance, fortuneBonus);
    }

    private static ItemStack parseItemStack(JsonObject json) {
        // Reuse Ingredient to resolve item, then apply count
        Ingredient ingredient = Ingredient.fromJson(json);
        ItemStack[] stacks = ingredient.getMatchingStacks();
        if (stacks.length == 0) return ItemStack.EMPTY;

        ItemStack result = stacks[0].copy();
        if (json.has("count")) {
            result.setCount(JsonHelper.getInt(json, "count", 1));
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Network (de)serialization
    // -------------------------------------------------------------------------

    public static ChanceResult read(PacketByteBuf buf) {
        ItemStack stack = buf.readItemStack();
        float chance = buf.readFloat();
        float fortuneBonus = buf.readFloat();
        return new ChanceResult(stack, chance, fortuneBonus);
    }

    public void write(PacketByteBuf buf) {
        buf.writeItemStack(stack);
        buf.writeFloat(chance);
        buf.writeFloat(fortuneChanceBonus);
    }
}