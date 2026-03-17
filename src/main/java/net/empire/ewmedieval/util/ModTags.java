package net.empire.ewmedieval.util;

import net.empire.ewmedieval.EwMedieval;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

 //   public static TagKey<Item> DYEABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(EwMedieval.MOD_ID, "dyeable"));

    public static final TagKey<Block> MINEABLE_WITH_KNIFE = TagKey.of(RegistryKeys.BLOCK, new Identifier("ewmedieval", "mineable/knife"));
    public static final TagKey<Block> DROPS_CAKE_SLICE = TagKey.of(RegistryKeys.BLOCK, new Identifier("ewmedieval", "drops_cake_slice"));

    public static final TagKey<Item> KNIVES = TagKey.of(RegistryKeys.ITEM, new Identifier("ewmedieval", "knives"));

    public static final TagKey<Item> AXES = TagKey.of(RegistryKeys.ITEM, new Identifier("ewmedieval", "axes"));

    public static final TagKey<Item> OFFHAND_EQUIPMENT = TagKey.of(RegistryKeys.ITEM, new Identifier("ewmedieval", "offhand_equipment"));


    public static final TagKey<Item> CUTTING_TOOLS = TagKey.of(RegistryKeys.ITEM, new Identifier("ewmedieval", "cutting_tools"));

    public static final TagKey<Item> FLAT_ON_CUTTING_BOARD = TagKey.of(RegistryKeys.ITEM, new Identifier("ewmedieval", "flat_on_cutting_board"));

    public static final TagKey<EntityType<?>> DOG_FOOD_USERS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("ewmedieval", "dog_food_users"));

    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(EwMedieval.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(EwMedieval.MOD_ID, name));
        }
    }
}