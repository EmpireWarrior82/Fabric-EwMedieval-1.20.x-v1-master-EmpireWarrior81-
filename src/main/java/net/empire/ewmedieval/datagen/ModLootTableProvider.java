package net.empire.ewmedieval.datagen;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.TIN_BLOCK);
        addDrop(ModBlocks.RAW_TIN_BLOCK);
        addDrop(ModBlocks.SILVER_BLOCK);
        addDrop(ModBlocks.CUT_SILVER);
        addDrop(ModBlocks.RAW_SILVER_BLOCK);
        addDrop(ModBlocks.KHAZAD_STEEL_BLOCK);
        addDrop(ModBlocks.BURZUM_STEEL_BLOCK);
        addDrop(ModBlocks.EDHEL_STEEL_BLOCK);
        addDrop(ModBlocks.CRUDE_BLOCK);
        addDrop(ModBlocks.LEAD_BLOCK);
        addDrop(ModBlocks.CUT_LEAD);
        addDrop(ModBlocks.RAW_LEAD_BLOCK);
        addDrop(ModBlocks.MITHRIL_BLOCK);
        addDrop(ModBlocks.RAW_MITHRIL_BLOCK);

        addDrop(ModBlocks.TIN_ORE, copperLikeOreDrops(ModBlocks.TIN_ORE, ModItems.RAW_TIN));
        addDrop(ModBlocks.DEEPSLATE_TIN_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_TIN_ORE, ModItems.RAW_TIN));
        addDrop(ModBlocks.DEEPSLATE_SILVER_ORE, ironLikeOreDrops(ModBlocks.DEEPSLATE_SILVER_ORE, ModItems.RAW_SILVER));
        addDrop(ModBlocks.DEEPSLATE_LEAD_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_LEAD_ORE, ModItems.RAW_LEAD));
        addDrop(ModBlocks.SLATE_COPPER_ORE, copperLikeOreDrops(ModBlocks.SLATE_COPPER_ORE, Items.RAW_COPPER));
        addDrop(ModBlocks.SLATE_TIN_ORE, copperLikeOreDrops(ModBlocks.SLATE_TIN_ORE, ModItems.RAW_TIN));
        addDrop(ModBlocks.SLATE_COAL_ORE, coalLikeOreDrops(ModBlocks.SLATE_COAL_ORE, Items.COAL));

        addDrop(ModBlocks.SLATE, drops(ModBlocks.COBBLED_SLATE));
        addDrop(ModBlocks.COBBLED_SLATE);
        addDrop(ModBlocks.POLISHED_SLATE);
        addDrop(ModBlocks.SLATE_BRICKS);

        addDrop(ModBlocks.SLATE_STAIRS);
        addDrop(ModBlocks.SLATE_BUTTON);
        addDrop(ModBlocks.SLATE_PRESSURE_PLATE);
        addDrop(ModBlocks.SLATE_WALL);
        addDrop(ModBlocks.SLATE_TRAPDOOR);
        addDrop(ModBlocks.SLATE_SLAB, slabDrops(ModBlocks.SLATE_SLAB));

        addDrop(ModBlocks.POLISHED_SLATE_SLAB, slabDrops(ModBlocks.POLISHED_SLATE_SLAB));
        addDrop(ModBlocks.POLISHED_SLATE_WALL);
        addDrop(ModBlocks.POLISHED_SLATE_STAIRS);
        addDrop(ModBlocks.SLATE_BRICKS_WALL);
        addDrop(ModBlocks.SLATE_BRICKS_STAIRS);
        addDrop(ModBlocks.SLATE_BRICKS_SLAB, slabDrops(ModBlocks.SLATE_BRICKS_SLAB));
        addDrop(ModBlocks.COBBLED_SLATE_SLAB, slabDrops(ModBlocks.COBBLED_SLATE_SLAB));
        addDrop(ModBlocks.COBBLED_SLATE_WALL);
        addDrop(ModBlocks.COBBLED_SLATE_STAIRS);

        addDrop(ModBlocks.STONE_PILLAR);

        addDrop(ModBlocks.OLD_DEEPSLATE);
        addDrop(ModBlocks.OLD_DEEPSLATE_SLAB, slabDrops(ModBlocks.OLD_DEEPSLATE_SLAB));
        addDrop(ModBlocks.OLD_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.OLD_DEEPSLATE_WALL);

        addDrop(ModBlocks.SMOOTH_DEEPSLATE);
        addDrop(ModBlocks.SMOOTH_DEEPSLATE_SLAB, slabDrops(ModBlocks.SMOOTH_DEEPSLATE_SLAB));
        addDrop(ModBlocks.SMOOTH_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.SMOOTH_DEEPSLATE_WALL);

        addDrop(ModBlocks.MOSSY_SMOOTH_DEEPSLATE);
        addDrop(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_SLAB, slabDrops(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_SLAB));
        addDrop(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_WALL);

        addDrop(ModBlocks.CRACKED_SMOOTH_DEEPSLATE);
        addDrop(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_SLAB, slabDrops(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_SLAB));
        addDrop(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_WALL);

        addDrop(ModBlocks.MOSSY_COBBLED_DEEPSLATE);
        addDrop(ModBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB, slabDrops(ModBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB));
        addDrop(ModBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);

        addDrop(ModBlocks.DEEPSLATE_PILLAR);
        addDrop(ModBlocks.MOSSY_DEEPSLATE_PILLAR);
        addDrop(ModBlocks.CRACKED_DEEPSLATE_PILLAR);

        addDrop(ModBlocks.MOSSY_DEEPSLATE_BRICKS);
        addDrop(ModBlocks.MOSSY_DEEPSLATE_BRICKS_SLAB, slabDrops(ModBlocks.MOSSY_DEEPSLATE_BRICKS_SLAB));
        addDrop(ModBlocks.MOSSY_DEEPSLATE_BRICKS_STAIRS);
        addDrop(ModBlocks.MOSSY_DEEPSLATE_BRICKS_WALL);

        addDrop(ModBlocks.MOSSY_DEEPSLATE_TILES);
        addDrop(ModBlocks.MOSSY_DEEPSLATE_TILES_SLAB, slabDrops(ModBlocks.MOSSY_DEEPSLATE_TILES_SLAB));
        addDrop(ModBlocks.MOSSY_DEEPSLATE_TILES_STAIRS);
        addDrop(ModBlocks.MOSSY_DEEPSLATE_TILES_WALL);

        addDrop(ModBlocks.MOSSY_POLISHED_DEEPSLATE);
        addDrop(ModBlocks.MOSSY_POLISHED_DEEPSLATE_SLAB, slabDrops(ModBlocks.MOSSY_POLISHED_DEEPSLATE_SLAB));
        addDrop(ModBlocks.MOSSY_POLISHED_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.MOSSY_POLISHED_DEEPSLATE_WALL);

        addDrop(ModBlocks.CRACKED_POLISHED_DEEPSLATE);
        addDrop(ModBlocks.CRACKED_POLISHED_DEEPSLATE_SLAB, slabDrops(ModBlocks.CRACKED_POLISHED_DEEPSLATE_SLAB));
        addDrop(ModBlocks.CRACKED_POLISHED_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.CRACKED_POLISHED_DEEPSLATE_WALL);

        addDrop(ModBlocks.CHISELED_POLISHED_DEEPSLATE);
        addDrop(ModBlocks.CHISELED_DEEPSLATE_BRICKS);
        addDrop(ModBlocks.CHISELED_DEEPSLATE_TILES);
        addDrop(ModBlocks.CHISELED_SMOOTH_DEEPSLATE);

        addDrop(ModBlocks.DEEPSLATE_BRICKWORK);
        addDrop(ModBlocks.DEEPSLATE_BRICKWORK_SLAB, slabDrops(ModBlocks.DEEPSLATE_BRICKWORK_SLAB));
        addDrop(ModBlocks.DEEPSLATE_BRICKWORK_STAIRS);
        addDrop(ModBlocks.DEEPSLATE_BRICKWORK_WALL);

        addDrop(ModBlocks.DEEPSLATE_PRESSURE_PLATE);
        addDrop(ModBlocks.DEEPSLATE_BUTTON);
        addDrop(ModBlocks.DEEPSLATE_TRAPDOOR);

        addDrop(ModBlocks.TUFF_TRAPDOOR);
        addDrop(ModBlocks.TUFF_BRICKS);
        addDrop(ModBlocks.TUFF_BRICK_SLAB, slabDrops(ModBlocks.TUFF_BRICK_SLAB));
        addDrop(ModBlocks.TUFF_BRICK_STAIRS);
        addDrop(ModBlocks.POLISHED_TUFF);
        addDrop(ModBlocks.POLISHED_TUFF_SLAB, slabDrops(ModBlocks.POLISHED_TUFF_SLAB));
        addDrop(ModBlocks.TUFF_SLAB, slabDrops(ModBlocks.TUFF_SLAB));
        addDrop(ModBlocks.POLISHED_TUFF_STAIRS);
        addDrop(ModBlocks.TUFF_STAIRS);
        addDrop(ModBlocks.POLISHED_TUFF_WALL);
        addDrop(ModBlocks.TUFF_WALL);
        addDrop(ModBlocks.CHISELED_TUFF);
        addDrop(ModBlocks.CHISELED_TUFF_BRICKS);

        addDropWithSilkTouch(ModBlocks.TUFF_CARVED_WINDOW);
        addDropWithSilkTouch(ModBlocks.TUFF_CARVED_WINDOW_PANE);


    }
    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );

    }
    public LootTable.Builder coalLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>) this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }
    public LootTable.Builder ironLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>) this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }

}
