package net.empire.ewmedieval.util;

import net.empire.ewmedieval.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.enchantment.Enchantments;


public class ModLootTableModifiers {
    private static final Identifier HORSE_ID =
            new Identifier("minecraft", "entities/horse");

    private static final Identifier GOAT_ID =
            new Identifier("minecraft", "entities/goat");

    private static final Identifier IRON_ORE_ID =
            new Identifier("minecraft", "blocks/iron_ore");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(HORSE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModItems.RAW_HORSE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(GOAT_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.85f))
                        .with(ItemEntry.builder(ModItems.RAW_GOAT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }


    public static void replaceLootTables() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
                if(IRON_ORE_ID.equals(id)) {
                    return LootTable.builder()
                            .pool(LootPool.builder()
                                    .rolls(ConstantLootNumberProvider.create(1))
                                    .with(ItemEntry.builder(ModItems.RAW_IRON_NUGGET)
                                            .apply(SetCountLootFunction.builder(
                                                    UniformLootNumberProvider.create(1.0f, 3.0f)))
                                                    .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))

                                            )
                            )
                            .build();
                }
                return null;
            });





        }
}

