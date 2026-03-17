package net.empire.ewmedieval.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ForgeRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;

    public ForgeRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        // Tel hoeveel echte items en hoeveel lege plekken dit recept verwacht
        java.util.List<Ingredient> requiredItems = new java.util.ArrayList<>();
        int requiredEmpty = 0;

        for (Ingredient ing : recipeItems) {
            if (ing.isEmpty()) {
                requiredEmpty++;
            } else {
                requiredItems.add(ing);
            }
        }

        // Kopie van de slotten
        java.util.List<ItemStack> slots = new java.util.ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            slots.add(inventory.getStack(i));
        }

        // Eerst: check lege plekken
        int actualEmpty = 0;
        for (ItemStack s : slots) {
            if (s.isEmpty()) {
                actualEmpty++;
            }
        }
        if (actualEmpty < requiredEmpty) return false; // te weinig lege slots

        // Vervolgens: probeer alle vereiste items te matchen
        for (Ingredient ing : requiredItems) {
            boolean matched = false;
            for (int i = 0; i < slots.size(); i++) {
                ItemStack stack = slots.get(i);
                if (!stack.isEmpty() && ing.test(stack)) {
                    slots.set(i, ItemStack.EMPTY); // gebruik die slot
                    matched = true;
                    break;
                }
            }
            if (!matched) return false; // item niet gevonden
        }

        // Tot slot: check dat er geen extra items zijn achtergebleven
        for (ItemStack s : slots) {
            if (!s.isEmpty()) {
                return false; // er zit iets in wat niet in het recept hoort
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
        return true; // maakt niet uit, onze forge heeft vaste slots
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

    public static class Type implements RecipeType<ForgeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "forge";
    }

    public static class Serializer implements RecipeSerializer<ForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = new Identifier("ewmedieval", "forge");

        @Override
        public ForgeRecipe read(Identifier id, JsonObject json) {
            // output
            ItemStack output = net.minecraft.recipe.ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));

            // inputs
            JsonArray ingredientsJson = json.getAsJsonArray("ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(4, Ingredient.EMPTY);

            for (int i = 0; i < ingredientsJson.size(); i++) {
                if (ingredientsJson.get(i).isJsonObject() && ingredientsJson.get(i).getAsJsonObject().entrySet().isEmpty()) {
                    // {} in JSON -> echt leeg slot
                    inputs.set(i, Ingredient.EMPTY);
                } else {
                    inputs.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
                }
            }

            return new ForgeRecipe(id, output, inputs);
        }

        @Override
        public ForgeRecipe read(Identifier id, PacketByteBuf buf) {
            int size = buf.readInt();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                boolean isEmpty = buf.readBoolean(); // nieuwe flag
                if (isEmpty) {
                    inputs.set(i, Ingredient.EMPTY);
                } else {
                    inputs.set(i, Ingredient.fromPacket(buf));
                }
            }

            ItemStack output = buf.readItemStack();
            return new ForgeRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, ForgeRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                if (ing.isEmpty()) {
                    buf.writeBoolean(true); // flag: empty
                } else {
                    buf.writeBoolean(false);
                    ing.write(buf);
                }
            }

            buf.writeItemStack(recipe.output);
        }
    }
}