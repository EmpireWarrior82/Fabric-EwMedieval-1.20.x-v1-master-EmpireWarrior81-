package net.empire.ewmedieval.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Recipe for the Cutting Board.
 *
 * JSON format example:
 * {
 *   "type": "ewmedieval:cutting",
 *   "group": "",
 *   "ingredients": [ { "item": "minecraft:porkchop" } ],
 *   "tool": { "tag": "c:knives" },
 *   "result": [
 *     { "item": "minecraft:porkchop", "count": 2, "chance": 0.75 },
 *     { "item": "minecraft:leather",  "count": 1, "chance": 0.1  }
 *   ],
 *   "sound": "minecraft:block.wood.break"   // optional
 * }
 */
public class CuttingBoardRecipe implements Recipe<SimpleInventory> {

    public static final int MAX_RESULTS = 4;

    private final Identifier id;
    private final String group;
    private final Ingredient input;
    private final Ingredient tool;
    private final List<ChanceResult> results;
    /** Optional sound event ID string, e.g. "minecraft:entity.sheep.shear" */
    private final String soundEventId;

    public CuttingBoardRecipe(Identifier id, String group, Ingredient input,
                              Ingredient tool, List<ChanceResult> results,
                              String soundEventId) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.tool = tool;
        this.results = results;
        this.soundEventId = soundEventId;
    }

    // -------------------------------------------------------------------------
    // Recipe interface
    // -------------------------------------------------------------------------

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true; // Cutting board recipes don't show in the recipe book
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(input);
        return list;
    }

    /**
     * Returns the tool ingredient used to match against the player's held item.
     */
    public Ingredient getTool() {
        return tool;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (inventory.isEmpty()) return false;
        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return results.isEmpty() ? ItemStack.EMPTY : results.get(0).getStack().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return results.isEmpty() ? ItemStack.EMPTY : results.get(0).getStack();
    }

    // -------------------------------------------------------------------------
    // Result helpers
    // -------------------------------------------------------------------------

    public List<ChanceResult> getRollableResults() {
        return results;
    }

    /** Returns just the ItemStacks without rolling for chance. */
    public List<ItemStack> getResults() {
        return results.stream().map(ChanceResult::getStack).collect(Collectors.toList());
    }

    /**
     * Rolls each result against its chance (with fortune bonus) and returns
     * the list of stacks that were successfully generated.
     */
    public List<ItemStack> rollResults(Random random, int fortuneLevel) {
        List<ItemStack> rolled = new ArrayList<>();
        for (ChanceResult output : results) {
            ItemStack stack = output.rollOutput(random, fortuneLevel);
            if (!stack.isEmpty()) {
                rolled.add(stack);
            }
        }
        return rolled;
    }

    @Nullable
    public String getSoundEventId() {
        return soundEventId;
    }

    // -------------------------------------------------------------------------
    // Registry references
    // -------------------------------------------------------------------------

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CUTTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CUTTING_TYPE;
    }

    // -------------------------------------------------------------------------
    // equals / hashCode
    // -------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuttingBoardRecipe that = (CuttingBoardRecipe) o;
        return Objects.equals(id, that.id)
                && Objects.equals(group, that.group)
                && Objects.equals(getResults(), that.getResults())
                && Objects.equals(soundEventId, that.soundEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, getResults(), soundEventId);
    }

    // -------------------------------------------------------------------------
    // Serializer (inner class)
    // -------------------------------------------------------------------------

    public static class Serializer implements RecipeSerializer<CuttingBoardRecipe> {

        public static final Identifier ID = new Identifier("ewmedieval", "cutting");

        @Override
        public CuttingBoardRecipe read(Identifier recipeId, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");

            // --- Ingredient ---
            JsonArray ingredientsArray = JsonHelper.getArray(json, "ingredients");
            if (ingredientsArray.size() == 0) {
                throw new JsonParseException("No ingredients for cutting recipe");
            }
            if (ingredientsArray.size() > 1) {
                throw new JsonParseException("Too many ingredients for cutting recipe! Only one ingredient is allowed.");
            }
            Ingredient input = Ingredient.fromJson(ingredientsArray.get(0));

            // --- Tool ---
            Ingredient tool;
            JsonElement toolElement = json.get("tool");
            if (toolElement == null) {
                throw new JsonParseException("No tool specified for cutting recipe");
            } else if (toolElement.isJsonArray()) {
                tool = Ingredient.fromJson(toolElement);
            } else if (toolElement.isJsonObject()) {
                tool = Ingredient.fromJson(toolElement.getAsJsonObject());
            } else {
                throw new JsonParseException("Tool must be a JSON object or array");
            }
            if (tool.isEmpty()) {
                throw new JsonParseException("Tool ingredient is empty");
            }

            // --- Results ---
            JsonArray resultArray = JsonHelper.getArray(json, "result");
            if (resultArray.size() > MAX_RESULTS) {
                throw new JsonParseException("Too many results for cutting recipe! Max is " + MAX_RESULTS);
            }
            List<ChanceResult> results = new ArrayList<>();
            for (JsonElement element : resultArray) {
                results.add(ChanceResult.deserialize(element));
            }

            // --- Optional sound ---
            String sound = JsonHelper.getString(json, "sound", "");

            return new CuttingBoardRecipe(recipeId, group, input, tool, results, sound);
        }

        @Nullable
        @Override
        public CuttingBoardRecipe read(Identifier recipeId, PacketByteBuf buf) {
            String group = buf.readString(32767);
            Ingredient input = Ingredient.fromPacket(buf);
            Ingredient tool = Ingredient.fromPacket(buf);

            int resultCount = buf.readVarInt();
            List<ChanceResult> results = new ArrayList<>(resultCount);
            for (int i = 0; i < resultCount; i++) {
                results.add(ChanceResult.read(buf));
            }
            String sound = buf.readString();

            return new CuttingBoardRecipe(recipeId, group, input, tool, results, sound);
        }

        @Override
        public void write(PacketByteBuf buf, CuttingBoardRecipe recipe) {
            buf.writeString(recipe.group);
            recipe.input.write(buf);
            recipe.tool.write(buf);
            buf.writeVarInt(recipe.results.size());
            for (ChanceResult result : recipe.results) {
                result.write(buf);
            }
            buf.writeString(recipe.soundEventId != null ? recipe.soundEventId : "");
        }
    }
}