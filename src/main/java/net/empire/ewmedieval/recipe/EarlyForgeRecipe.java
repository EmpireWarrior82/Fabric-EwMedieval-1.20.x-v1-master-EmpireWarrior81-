package net.empire.ewmedieval.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EarlyForgeRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;

    public EarlyForgeRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        List<Ingredient> requiredItems = new ArrayList<>();
        int requiredEmpty = 0;

        for (Ingredient ing : recipeItems) {
            if (ing.isEmpty()) {
                requiredEmpty++;
            } else {
                requiredItems.add(ing);
            }
        }

        List<ItemStack> slots = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            slots.add(inventory.getStack(i));
        }

        int actualEmpty = 0;
        for (ItemStack s : slots) {
            if (s.isEmpty()) {
                actualEmpty++;
            }
        }
        if (actualEmpty < requiredEmpty) return false;

        for (Ingredient ing : requiredItems) {
            boolean matched = false;
            for (int i = 0; i < slots.size(); i++) {
                ItemStack stack = slots.get(i);
                if (!stack.isEmpty() && ing.test(stack)) {
                    slots.set(i, ItemStack.EMPTY);
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }

        for (ItemStack s : slots) {
            if (!s.isEmpty()) {
                return false;
            }
        }

        return true;
    }


    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true; // not a grid recipe, always fits
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Type implements RecipeType<EarlyForgeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "earlyforge";
    }

    public static class Serializer implements RecipeSerializer<EarlyForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = new Identifier("ewmedieval", "earlyforge");

        @Override
        public EarlyForgeRecipe read(Identifier id, JsonObject json) {

            ItemStack output = net.minecraft.recipe.ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));

            JsonArray ingredientsJson = json.getAsJsonArray("ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(4, Ingredient.EMPTY);

            for (int i = 0; i < ingredientsJson.size(); i++) {
                if (ingredientsJson.get(i).isJsonObject() && ingredientsJson.get(i).getAsJsonObject().entrySet().isEmpty()) {
                    inputs.set(i, Ingredient.EMPTY);
                } else {
                    inputs.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
                }
            }

            return new EarlyForgeRecipe(id, output, inputs);
        }

        @Override
        public EarlyForgeRecipe read(Identifier id, PacketByteBuf buf) {
            int size = buf.readInt();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                boolean isEmpty = buf.readBoolean();
                if (isEmpty) {
                    inputs.set(i, Ingredient.EMPTY);
                } else {
                    inputs.set(i, Ingredient.fromPacket(buf));
                }
            }

            ItemStack output = buf.readItemStack();
            return new EarlyForgeRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, EarlyForgeRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                if (ing.isEmpty()) {
                    buf.writeBoolean(true);
                } else {
                    buf.writeBoolean(false);
                    ing.write(buf);
                }
            }

            buf.writeItemStack(recipe.output);
        }
    }
}