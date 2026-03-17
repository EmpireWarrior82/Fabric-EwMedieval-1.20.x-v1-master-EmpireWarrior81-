package net.empire.ewmedieval.block.entity;

import net.empire.ewmedieval.EwMedieval;
import net.empire.ewmedieval.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<ForgeBlockEntity> FORGE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(EwMedieval.MOD_ID, "forge_be"),
                    FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new,
                            ModBlocks.FORGE).build());


    public static final BlockEntityType<EarlyForgeBlockEntity> EARLYFORGE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(EwMedieval.MOD_ID, "earlyforge_be"),
                    FabricBlockEntityTypeBuilder.create(EarlyForgeBlockEntity::new,
                            ModBlocks.EARLY_FORGE).build());

    public static final BlockEntityType<CuttingBoardBlockEntity> CUTTING_BOARD_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(EwMedieval.MOD_ID, "cutting_board_be"),
                    FabricBlockEntityTypeBuilder.create(CuttingBoardBlockEntity::new,
                            ModBlocks.CUTTING_BOARD).build());

    public static void registerBlockEntities() {
        EwMedieval.LOGGER.info("Registering Block Entities for " + EwMedieval.MOD_ID);
    }
}
