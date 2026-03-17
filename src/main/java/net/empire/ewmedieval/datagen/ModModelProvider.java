package net.empire.ewmedieval.datagen;

import net.empire.ewmedieval.block.ModBlocks;
import net.empire.ewmedieval.block.custom.VerticalSlabs.VerticalSlabBlock;
import net.empire.ewmedieval.block.custom.VerticalSlabs.VerticalSlabShape;
import net.empire.ewmedieval.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.math.Direction;
import java.util.List;
import java.util.Optional;

import net.minecraft.data.client.VariantSettings;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public static final Model VERTICAL_SLAB = new Model(
            Optional.of(new Identifier("minecraft", "block/block")), // parent = simpel blockmodel
            Optional.empty(), // suffix optioneel
            TextureKey.ALL
    );


    record CustomBlockTextures(Block block, String side, String top) {}


    private void registerVerticalSlab(BlockStateModelGenerator generator, Block verticalSlab, Block baseBlock) {
        Identifier baseTexture = TextureMap.getId(baseBlock);
        Identifier fullBlockModel = ModelIds.getBlockModelId(baseBlock);

        Identifier straightModel = new Identifier("ewmedieval:block/vertical_slab_straight");
        Identifier innerLeftModel = new Identifier("ewmedieval:block/vertical_slab_inner");
        Identifier innerRightModel = new Identifier("ewmedieval:block/vertical_slab_inner");
        Identifier outerLeftModel = new Identifier("ewmedieval:block/vertical_slab_outer");
        Identifier outerRightModel = new Identifier("ewmedieval:block/vertical_slab_outer");

        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(verticalSlab)
                .coordinate(BlockStateVariantMap.create(VerticalSlabBlock.FACING, VerticalSlabBlock.SHAPE, VerticalSlabBlock.DOUBLE)
                        .register(Direction.NORTH, VerticalSlabShape.STRAIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, straightModel).put(VariantSettings.Y, VariantSettings.Rotation.R0))
                        .register(Direction.EAST, VerticalSlabShape.STRAIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, straightModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, VerticalSlabShape.STRAIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, straightModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, VerticalSlabShape.STRAIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, straightModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))

                        .register(Direction.NORTH, VerticalSlabShape.INNER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R0))
                        .register(Direction.EAST, VerticalSlabShape.INNER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, VerticalSlabShape.INNER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, VerticalSlabShape.INNER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))

                        .register(Direction.NORTH, VerticalSlabShape.INNER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R0))
                        .register(Direction.EAST, VerticalSlabShape.INNER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, VerticalSlabShape.INNER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, VerticalSlabShape.INNER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, innerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))

                        .register(Direction.NORTH, VerticalSlabShape.OUTER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R0))
                        .register(Direction.EAST, VerticalSlabShape.OUTER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, VerticalSlabShape.OUTER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, VerticalSlabShape.OUTER_LEFT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerLeftModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))

                        .register(Direction.NORTH, VerticalSlabShape.OUTER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R0))
                        .register(Direction.EAST, VerticalSlabShape.OUTER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, VerticalSlabShape.OUTER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, VerticalSlabShape.OUTER_RIGHT, false, BlockStateVariant.create().put(VariantSettings.MODEL, outerRightModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))

                        .register(Direction.NORTH, VerticalSlabShape.STRAIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.EAST, VerticalSlabShape.STRAIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.SOUTH, VerticalSlabShape.STRAIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.WEST, VerticalSlabShape.STRAIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))

                        // DOUBLE (alle vormen)
                        .register(Direction.NORTH, VerticalSlabShape.INNER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.NORTH, VerticalSlabShape.INNER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.NORTH, VerticalSlabShape.OUTER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.NORTH, VerticalSlabShape.OUTER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.EAST,  VerticalSlabShape.INNER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.EAST,  VerticalSlabShape.INNER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.EAST,  VerticalSlabShape.OUTER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.EAST,  VerticalSlabShape.OUTER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.SOUTH, VerticalSlabShape.INNER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.SOUTH, VerticalSlabShape.INNER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.SOUTH, VerticalSlabShape.OUTER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.SOUTH, VerticalSlabShape.OUTER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.WEST,  VerticalSlabShape.INNER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.WEST,  VerticalSlabShape.INNER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.WEST,  VerticalSlabShape.OUTER_LEFT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))
                        .register(Direction.WEST,  VerticalSlabShape.OUTER_RIGHT, true, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockModel))

                ));


    }


    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TIN_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_TIN_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TIN_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_TIN_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SILVER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CUT_SILVER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_SILVER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_SILVER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.KHAZAD_STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BURZUM_STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.EDHEL_STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRUDE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LEAD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CUT_LEAD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_LEAD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_LEAD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MITHRIL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_MITHRIL_BLOCK);

        // Polished Stone
        BlockStateModelGenerator.BlockTexturePool polishedStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_STONE);
        polishedStonePool.slab(ModBlocks.POLISHED_STONE_SLAB);
        polishedStonePool.stairs(ModBlocks.POLISHED_STONE_STAIRS);
        polishedStonePool.wall(ModBlocks.POLISHED_STONE_WALL);

// Mossy Polished Stone
        BlockStateModelGenerator.BlockTexturePool mossyPolishedStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_POLISHED_STONE);
        mossyPolishedStonePool.slab(ModBlocks.MOSSY_POLISHED_STONE_SLAB);
        mossyPolishedStonePool.stairs(ModBlocks.MOSSY_POLISHED_STONE_STAIRS);
        mossyPolishedStonePool.wall(ModBlocks.MOSSY_POLISHED_STONE_WALL);

// Cracked Polished Stone
        BlockStateModelGenerator.BlockTexturePool crackedPolishedStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_POLISHED_STONE);
        crackedPolishedStonePool.slab(ModBlocks.CRACKED_POLISHED_STONE_SLAB);
        crackedPolishedStonePool.stairs(ModBlocks.CRACKED_POLISHED_STONE_STAIRS);
        crackedPolishedStonePool.wall(ModBlocks.CRACKED_POLISHED_STONE_WALL);

// Stone Tiles
        BlockStateModelGenerator.BlockTexturePool stoneTilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.STONE_TILES);
        stoneTilesPool.slab(ModBlocks.STONE_TILES_SLAB);
        stoneTilesPool.stairs(ModBlocks.STONE_TILES_STAIRS);
        stoneTilesPool.wall(ModBlocks.STONE_TILES_WALL);

// Mossy Stone Tiles
        BlockStateModelGenerator.BlockTexturePool mossyStoneTilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_STONE_TILES);
        mossyStoneTilesPool.slab(ModBlocks.MOSSY_STONE_TILES_SLAB);
        mossyStoneTilesPool.stairs(ModBlocks.MOSSY_STONE_TILES_STAIRS);
        mossyStoneTilesPool.wall(ModBlocks.MOSSY_STONE_TILES_WALL);

// Cracked Stone Tiles
        BlockStateModelGenerator.BlockTexturePool crackedStoneTilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_STONE_TILES);
        crackedStoneTilesPool.slab(ModBlocks.CRACKED_STONE_TILES_SLAB);
        crackedStoneTilesPool.stairs(ModBlocks.CRACKED_STONE_TILES_STAIRS);
        crackedStoneTilesPool.wall(ModBlocks.CRACKED_STONE_TILES_WALL);

// Mossy Smooth Stone
        BlockStateModelGenerator.BlockTexturePool mossySmoothStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_SMOOTH_STONE);
        mossySmoothStonePool.slab(ModBlocks.MOSSY_SMOOTH_STONE_SLAB);
        mossySmoothStonePool.stairs(ModBlocks.MOSSY_SMOOTH_STONE_STAIRS);
        mossySmoothStonePool.wall(ModBlocks.MOSSY_SMOOTH_STONE_WALL);

// Cracked Smooth Stone
        BlockStateModelGenerator.BlockTexturePool crackedSmoothStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_SMOOTH_STONE);
        crackedSmoothStonePool.slab(ModBlocks.CRACKED_SMOOTH_STONE_SLAB);
        crackedSmoothStonePool.stairs(ModBlocks.CRACKED_SMOOTH_STONE_STAIRS);
        crackedSmoothStonePool.wall(ModBlocks.CRACKED_SMOOTH_STONE_WALL);

// Old Stone
        BlockStateModelGenerator.BlockTexturePool oldStonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.OLD_STONE);
        oldStonePool.slab(ModBlocks.OLD_STONE_SLAB);
        oldStonePool.stairs(ModBlocks.OLD_STONE_STAIRS);
        oldStonePool.wall(ModBlocks.OLD_STONE_WALL);

// Stone Brickwork
        BlockStateModelGenerator.BlockTexturePool stoneBrickworkPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.STONE_BRICKWORK);
        stoneBrickworkPool.slab(ModBlocks.STONE_BRICKWORK_SLAB);
        stoneBrickworkPool.stairs(ModBlocks.STONE_BRICKWORK_STAIRS);
        stoneBrickworkPool.wall(ModBlocks.STONE_BRICKWORK_WALL);

// Slate
        BlockStateModelGenerator.BlockTexturePool slatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SLATE);
        BlockStateModelGenerator.BlockTexturePool cobbledslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COBBLED_SLATE);
        BlockStateModelGenerator.BlockTexturePool polishedslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_SLATE);
        BlockStateModelGenerator.BlockTexturePool slatebricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SLATE_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SLATE_TIN_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SLATE_COPPER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SLATE_COAL_ORE);

        slatePool.stairs(ModBlocks.SLATE_STAIRS);
        slatePool.slab(ModBlocks.SLATE_SLAB);
        slatePool.button(ModBlocks.SLATE_BUTTON);
        slatePool.pressurePlate(ModBlocks.SLATE_PRESSURE_PLATE);
        slatePool.wall(ModBlocks.SLATE_WALL);

        polishedslatePool.stairs(ModBlocks.POLISHED_SLATE_STAIRS);
        polishedslatePool.slab(ModBlocks.POLISHED_SLATE_SLAB);
        polishedslatePool.wall(ModBlocks.POLISHED_SLATE_WALL);

        slatebricksPool.stairs(ModBlocks.SLATE_BRICKS_STAIRS);
        slatebricksPool.slab(ModBlocks.SLATE_BRICKS_SLAB);
        slatebricksPool.wall(ModBlocks.SLATE_BRICKS_WALL);

        cobbledslatePool.stairs(ModBlocks.COBBLED_SLATE_STAIRS);
        cobbledslatePool.slab(ModBlocks.COBBLED_SLATE_SLAB);
        cobbledslatePool.wall(ModBlocks.COBBLED_SLATE_WALL);

        blockStateModelGenerator.registerTrapdoor(ModBlocks.SLATE_TRAPDOOR);

        BlockStateModelGenerator.BlockTexturePool tuffbricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TUFF_BRICKS);
        tuffbricksPool.stairs(ModBlocks.TUFF_BRICK_STAIRS);
        tuffbricksPool.slab(ModBlocks.TUFF_BRICK_SLAB);
        tuffbricksPool.wall(ModBlocks.TUFF_BRICK_WALL);

        BlockStateModelGenerator.BlockTexturePool polishedtuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_TUFF);
        polishedtuffPool.stairs(ModBlocks.POLISHED_TUFF_STAIRS);
        polishedtuffPool.slab(ModBlocks.POLISHED_TUFF_SLAB);
        polishedtuffPool.wall(ModBlocks.POLISHED_TUFF_WALL);

        blockStateModelGenerator.registerTrapdoor(ModBlocks.TUFF_TRAPDOOR);

        blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.TUFF)
                .slab(ModBlocks.TUFF_SLAB)
                .stairs(ModBlocks.TUFF_STAIRS)
                .wall(ModBlocks.TUFF_WALL);

// Old Deepslate
        BlockStateModelGenerator.BlockTexturePool oldDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.OLD_DEEPSLATE);
        oldDeepslatePool.stairs(ModBlocks.OLD_DEEPSLATE_STAIRS);
        oldDeepslatePool.slab(ModBlocks.OLD_DEEPSLATE_SLAB);
        oldDeepslatePool.wall(ModBlocks.OLD_DEEPSLATE_WALL);

// Smooth Deepslate
        BlockStateModelGenerator.BlockTexturePool smoothDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SMOOTH_DEEPSLATE);
        smoothDeepslatePool.stairs(ModBlocks.SMOOTH_DEEPSLATE_STAIRS);
        smoothDeepslatePool.slab(ModBlocks.SMOOTH_DEEPSLATE_SLAB);
        smoothDeepslatePool.wall(ModBlocks.SMOOTH_DEEPSLATE_WALL);

// Mossy Smooth Deepslate
        BlockStateModelGenerator.BlockTexturePool mossySmoothDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_SMOOTH_DEEPSLATE);
        mossySmoothDeepslatePool.stairs(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_STAIRS);
        mossySmoothDeepslatePool.slab(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_SLAB);
        mossySmoothDeepslatePool.wall(ModBlocks.MOSSY_SMOOTH_DEEPSLATE_WALL);

// Cracked Smooth Deepslate
        BlockStateModelGenerator.BlockTexturePool crackedSmoothDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_SMOOTH_DEEPSLATE);
        crackedSmoothDeepslatePool.stairs(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_STAIRS);
        crackedSmoothDeepslatePool.slab(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_SLAB);
        crackedSmoothDeepslatePool.wall(ModBlocks.CRACKED_SMOOTH_DEEPSLATE_WALL);

// Mossy Cobbled Deepslate
        BlockStateModelGenerator.BlockTexturePool mossyCobbledDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_COBBLED_DEEPSLATE);
        mossyCobbledDeepslatePool.stairs(ModBlocks.MOSSY_COBBLED_DEEPSLATE_STAIRS);
        mossyCobbledDeepslatePool.slab(ModBlocks.MOSSY_COBBLED_DEEPSLATE_SLAB);
        mossyCobbledDeepslatePool.wall(ModBlocks.MOSSY_COBBLED_DEEPSLATE_WALL);


// Mossy Deepslate Bricks
        BlockStateModelGenerator.BlockTexturePool mossyDeepslateBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_DEEPSLATE_BRICKS);
        mossyDeepslateBricksPool.stairs(ModBlocks.MOSSY_DEEPSLATE_BRICKS_STAIRS);
        mossyDeepslateBricksPool.slab(ModBlocks.MOSSY_DEEPSLATE_BRICKS_SLAB);
        mossyDeepslateBricksPool.wall(ModBlocks.MOSSY_DEEPSLATE_BRICKS_WALL);

// Mossy Deepslate Tiles
        BlockStateModelGenerator.BlockTexturePool mossyDeepslateTilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_DEEPSLATE_TILES);
        mossyDeepslateTilesPool.stairs(ModBlocks.MOSSY_DEEPSLATE_TILES_STAIRS);
        mossyDeepslateTilesPool.slab(ModBlocks.MOSSY_DEEPSLATE_TILES_SLAB);
        mossyDeepslateTilesPool.wall(ModBlocks.MOSSY_DEEPSLATE_TILES_WALL);

// Mossy Polished Deepslate
        BlockStateModelGenerator.BlockTexturePool mossyPolishedDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_POLISHED_DEEPSLATE);
        mossyPolishedDeepslatePool.stairs(ModBlocks.MOSSY_POLISHED_DEEPSLATE_STAIRS);
        mossyPolishedDeepslatePool.slab(ModBlocks.MOSSY_POLISHED_DEEPSLATE_SLAB);
        mossyPolishedDeepslatePool.wall(ModBlocks.MOSSY_POLISHED_DEEPSLATE_WALL);

// Cracked Polished Deepslate
        BlockStateModelGenerator.BlockTexturePool crackedPolishedDeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_POLISHED_DEEPSLATE);
        crackedPolishedDeepslatePool.stairs(ModBlocks.CRACKED_POLISHED_DEEPSLATE_STAIRS);
        crackedPolishedDeepslatePool.slab(ModBlocks.CRACKED_POLISHED_DEEPSLATE_SLAB);
        crackedPolishedDeepslatePool.wall(ModBlocks.CRACKED_POLISHED_DEEPSLATE_WALL);

// Deepslate Brickwork
        BlockStateModelGenerator.BlockTexturePool deepslateBrickworkPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DEEPSLATE_BRICKWORK);
        deepslateBrickworkPool.stairs(ModBlocks.DEEPSLATE_BRICKWORK_STAIRS);
        deepslateBrickworkPool.slab(ModBlocks.DEEPSLATE_BRICKWORK_SLAB);
        deepslateBrickworkPool.wall(ModBlocks.DEEPSLATE_BRICKWORK_WALL);

        BlockStateModelGenerator.BlockTexturePool deepslatePool =
                blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DEEPSLATE);
        deepslatePool.button(ModBlocks.DEEPSLATE_BUTTON);
        deepslatePool.pressurePlate(ModBlocks.DEEPSLATE_PRESSURE_PLATE);

        blockStateModelGenerator.registerTrapdoor(ModBlocks.DEEPSLATE_TRAPDOOR);

        BlockStateModelGenerator.BlockTexturePool cobbled_blue_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COBBLED_BLUE_TUFF);
        cobbled_blue_tuffPool.stairs(ModBlocks.COBBLED_BLUE_TUFF_STAIRS);
        cobbled_blue_tuffPool.slab(ModBlocks.COBBLED_BLUE_TUFF_SLAB);
        cobbled_blue_tuffPool.wall(ModBlocks.COBBLED_BLUE_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool mossy_cobbled_blue_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_COBBLED_BLUE_TUFF);
        mossy_cobbled_blue_tuffPool.stairs(ModBlocks.MOSSY_COBBLED_BLUE_TUFF_STAIRS);
        mossy_cobbled_blue_tuffPool.slab(ModBlocks.MOSSY_COBBLED_BLUE_TUFF_SLAB);
        mossy_cobbled_blue_tuffPool.wall(ModBlocks.MOSSY_COBBLED_BLUE_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool blue_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BLUE_TUFF);
        blue_tuffPool.stairs(ModBlocks.BLUE_TUFF_STAIRS);
        blue_tuffPool.slab(ModBlocks.BLUE_TUFF_SLAB);
        blue_tuffPool.wall(ModBlocks.BLUE_TUFF_WALL);
        blue_tuffPool.button(ModBlocks.BLUE_TUFF_BUTTON);
        blue_tuffPool.pressurePlate(ModBlocks.BLUE_TUFF_PRESSURE_PLATE);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.BLUE_TUFF_TRAPDOOR);
        BlockStateModelGenerator.BlockTexturePool blue_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BLUE_TUFF_BRICKS);
        blue_tuff_bricksPool.stairs(ModBlocks.BLUE_TUFF_BRICKS_STAIRS);
        blue_tuff_bricksPool.slab(ModBlocks.BLUE_TUFF_BRICKS_SLAB);
        blue_tuff_bricksPool.wall(ModBlocks.BLUE_TUFF_BRICKS_WALL);
        BlockStateModelGenerator.BlockTexturePool mossy_blue_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_BLUE_TUFF_BRICKS);
        mossy_blue_tuff_bricksPool.stairs(ModBlocks.MOSSY_BLUE_TUFF_BRICKS_STAIRS);
        mossy_blue_tuff_bricksPool.slab(ModBlocks.MOSSY_BLUE_TUFF_BRICKS_SLAB);
        mossy_blue_tuff_bricksPool.wall(ModBlocks.MOSSY_BLUE_TUFF_BRICKS_WALL);
        BlockStateModelGenerator.BlockTexturePool cracked_blue_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_BLUE_TUFF_BRICKS);
        cracked_blue_tuff_bricksPool.stairs(ModBlocks.CRACKED_BLUE_TUFF_BRICKS_STAIRS);
        cracked_blue_tuff_bricksPool.slab(ModBlocks.CRACKED_BLUE_TUFF_BRICKS_SLAB);
        cracked_blue_tuff_bricksPool.wall(ModBlocks.CRACKED_BLUE_TUFF_BRICKS_WALL);
        BlockStateModelGenerator.BlockTexturePool blue_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BLUE_TUFF_TILES);
        blue_tuff_tilesPool.stairs(ModBlocks.BLUE_TUFF_TILES_STAIRS);
        blue_tuff_tilesPool.slab(ModBlocks.BLUE_TUFF_TILES_SLAB);
        blue_tuff_tilesPool.wall(ModBlocks.BLUE_TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_blue_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_BLUE_TUFF_TILES);
        mossy_blue_tuff_tilesPool.stairs(ModBlocks.MOSSY_BLUE_TUFF_TILES_STAIRS);
        mossy_blue_tuff_tilesPool.slab(ModBlocks.MOSSY_BLUE_TUFF_TILES_SLAB);
        mossy_blue_tuff_tilesPool.wall(ModBlocks.MOSSY_BLUE_TUFF_TILES_WALL);
        BlockStateModelGenerator.BlockTexturePool cracked_blue_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_BLUE_TUFF_TILES);
        cracked_blue_tuff_tilesPool.stairs(ModBlocks.CRACKED_BLUE_TUFF_TILES_STAIRS);
        cracked_blue_tuff_tilesPool.slab(ModBlocks.CRACKED_BLUE_TUFF_TILES_SLAB);
        cracked_blue_tuff_tilesPool.wall(ModBlocks.CRACKED_BLUE_TUFF_TILES_WALL);
        BlockStateModelGenerator.BlockTexturePool polished_blue_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_BLUE_TUFF);
        polished_blue_tuffPool.stairs(ModBlocks.POLISHED_BLUE_TUFF_STAIRS);
        polished_blue_tuffPool.slab(ModBlocks.POLISHED_BLUE_TUFF_SLAB);
        polished_blue_tuffPool.wall(ModBlocks.POLISHED_BLUE_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool mossy_polished_blue_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_POLISHED_BLUE_TUFF);
        mossy_polished_blue_tuffPool.stairs(ModBlocks.MOSSY_POLISHED_BLUE_TUFF_STAIRS);
        mossy_polished_blue_tuffPool.slab(ModBlocks.MOSSY_POLISHED_BLUE_TUFF_SLAB);
        mossy_polished_blue_tuffPool.wall(ModBlocks.MOSSY_POLISHED_BLUE_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool cracked_polished_blue_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_POLISHED_BLUE_TUFF);
        cracked_polished_blue_tuffPool.stairs(ModBlocks.CRACKED_POLISHED_BLUE_TUFF_STAIRS);
        cracked_polished_blue_tuffPool.slab(ModBlocks.CRACKED_POLISHED_BLUE_TUFF_SLAB);
        cracked_polished_blue_tuffPool.wall(ModBlocks.CRACKED_POLISHED_BLUE_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool cobbled_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COBBLED_GREEN_TUFF);
        cobbled_green_tuffPool.stairs(ModBlocks.COBBLED_GREEN_TUFF_STAIRS);
        cobbled_green_tuffPool.slab(ModBlocks.COBBLED_GREEN_TUFF_SLAB);
        cobbled_green_tuffPool.wall(ModBlocks.COBBLED_GREEN_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool smooth_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SMOOTH_GREEN_TUFF);
        smooth_green_tuffPool.stairs(ModBlocks.SMOOTH_GREEN_TUFF_STAIRS);
        smooth_green_tuffPool.slab(ModBlocks.SMOOTH_GREEN_TUFF_SLAB);
        smooth_green_tuffPool.wall(ModBlocks.SMOOTH_GREEN_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_smooth_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_SMOOTH_GREEN_TUFF);
        cracked_smooth_green_tuffPool.stairs(ModBlocks.CRACKED_SMOOTH_GREEN_TUFF_STAIRS);
        cracked_smooth_green_tuffPool.slab(ModBlocks.CRACKED_SMOOTH_GREEN_TUFF_SLAB);
        cracked_smooth_green_tuffPool.wall(ModBlocks.CRACKED_SMOOTH_GREEN_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool mossy_cobbled_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_COBBLED_GREEN_TUFF);
        mossy_cobbled_green_tuffPool.stairs(ModBlocks.MOSSY_COBBLED_GREEN_TUFF_STAIRS);
        mossy_cobbled_green_tuffPool.slab(ModBlocks.MOSSY_COBBLED_GREEN_TUFF_SLAB);
        mossy_cobbled_green_tuffPool.wall(ModBlocks.MOSSY_COBBLED_GREEN_TUFF_WALL);
        BlockStateModelGenerator.BlockTexturePool green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GREEN_TUFF);
        green_tuffPool.stairs(ModBlocks.GREEN_TUFF_STAIRS);
        green_tuffPool.slab(ModBlocks.GREEN_TUFF_SLAB);
        green_tuffPool.wall(ModBlocks.GREEN_TUFF_WALL);
        green_tuffPool.button(ModBlocks.GREEN_TUFF_BUTTON);
        green_tuffPool.pressurePlate(ModBlocks.GREEN_TUFF_PRESSURE_PLATE);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.GREEN_TUFF_TRAPDOOR);

        BlockStateModelGenerator.BlockTexturePool green_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GREEN_TUFF_BRICKS);
        green_tuff_bricksPool.stairs(ModBlocks.GREEN_TUFF_BRICKS_STAIRS);
        green_tuff_bricksPool.slab(ModBlocks.GREEN_TUFF_BRICKS_SLAB);
        green_tuff_bricksPool.wall(ModBlocks.GREEN_TUFF_BRICKS_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_green_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_GREEN_TUFF_BRICKS);
        mossy_green_tuff_bricksPool.stairs(ModBlocks.MOSSY_GREEN_TUFF_BRICKS_STAIRS);
        mossy_green_tuff_bricksPool.slab(ModBlocks.MOSSY_GREEN_TUFF_BRICKS_SLAB);
        mossy_green_tuff_bricksPool.wall(ModBlocks.MOSSY_GREEN_TUFF_BRICKS_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_green_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_GREEN_TUFF_BRICKS);
        cracked_green_tuff_bricksPool.stairs(ModBlocks.CRACKED_GREEN_TUFF_BRICKS_STAIRS);
        cracked_green_tuff_bricksPool.slab(ModBlocks.CRACKED_GREEN_TUFF_BRICKS_SLAB);
        cracked_green_tuff_bricksPool.wall(ModBlocks.CRACKED_GREEN_TUFF_BRICKS_WALL);

        BlockStateModelGenerator.BlockTexturePool green_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GREEN_TUFF_TILES);
        green_tuff_tilesPool.stairs(ModBlocks.GREEN_TUFF_TILES_STAIRS);
        green_tuff_tilesPool.slab(ModBlocks.GREEN_TUFF_TILES_SLAB);
        green_tuff_tilesPool.wall(ModBlocks.GREEN_TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_green_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_GREEN_TUFF_TILES);
        mossy_green_tuff_tilesPool.stairs(ModBlocks.MOSSY_GREEN_TUFF_TILES_STAIRS);
        mossy_green_tuff_tilesPool.slab(ModBlocks.MOSSY_GREEN_TUFF_TILES_SLAB);
        mossy_green_tuff_tilesPool.wall(ModBlocks.MOSSY_GREEN_TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_green_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_GREEN_TUFF_TILES);
        cracked_green_tuff_tilesPool.stairs(ModBlocks.CRACKED_GREEN_TUFF_TILES_STAIRS);
        cracked_green_tuff_tilesPool.slab(ModBlocks.CRACKED_GREEN_TUFF_TILES_SLAB);
        cracked_green_tuff_tilesPool.wall(ModBlocks.CRACKED_GREEN_TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool polished_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_GREEN_TUFF);
        polished_green_tuffPool.stairs(ModBlocks.POLISHED_GREEN_TUFF_STAIRS);
        polished_green_tuffPool.slab(ModBlocks.POLISHED_GREEN_TUFF_SLAB);
        polished_green_tuffPool.wall(ModBlocks.POLISHED_GREEN_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_polished_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_POLISHED_GREEN_TUFF);
        mossy_polished_green_tuffPool.stairs(ModBlocks.MOSSY_POLISHED_GREEN_TUFF_STAIRS);
        mossy_polished_green_tuffPool.slab(ModBlocks.MOSSY_POLISHED_GREEN_TUFF_SLAB);
        mossy_polished_green_tuffPool.wall(ModBlocks.MOSSY_POLISHED_GREEN_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_polished_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_POLISHED_GREEN_TUFF);
        cracked_polished_green_tuffPool.stairs(ModBlocks.CRACKED_POLISHED_GREEN_TUFF_STAIRS);
        cracked_polished_green_tuffPool.slab(ModBlocks.CRACKED_POLISHED_GREEN_TUFF_SLAB);
        cracked_polished_green_tuffPool.wall(ModBlocks.CRACKED_POLISHED_GREEN_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool gilded_green_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GILDED_GREEN_TUFF);
        gilded_green_tuffPool.stairs(ModBlocks.GILDED_GREEN_TUFF_STAIRS);
        gilded_green_tuffPool.slab(ModBlocks.GILDED_GREEN_TUFF_SLAB);
        gilded_green_tuffPool.wall(ModBlocks.GILDED_GREEN_TUFF_WALL);
        gilded_green_tuffPool.button(ModBlocks.GILDED_GREEN_TUFF_BUTTON);
        gilded_green_tuffPool.pressurePlate(ModBlocks.GILDED_GREEN_TUFF_PRESSURE_PLATE);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.GILDED_GREEN_TUFF_TRAPDOOR);

        BlockStateModelGenerator.BlockTexturePool old_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.OLD_TUFF);
        old_tuffPool.stairs(ModBlocks.OLD_TUFF_STAIRS);
        old_tuffPool.slab(ModBlocks.OLD_TUFF_SLAB);
        old_tuffPool.wall(ModBlocks.OLD_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool smooth_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SMOOTH_TUFF);
        smooth_tuffPool.stairs(ModBlocks.SMOOTH_TUFF_STAIRS);
        smooth_tuffPool.slab(ModBlocks.SMOOTH_TUFF_SLAB);
        smooth_tuffPool.wall(ModBlocks.SMOOTH_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_smooth_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_SMOOTH_TUFF);
        mossy_smooth_tuffPool.stairs(ModBlocks.MOSSY_SMOOTH_TUFF_STAIRS);
        mossy_smooth_tuffPool.slab(ModBlocks.MOSSY_SMOOTH_TUFF_SLAB);
        mossy_smooth_tuffPool.wall(ModBlocks.MOSSY_SMOOTH_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_smooth_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_SMOOTH_TUFF);
        cracked_smooth_tuffPool.stairs(ModBlocks.CRACKED_SMOOTH_TUFF_STAIRS);
        cracked_smooth_tuffPool.slab(ModBlocks.CRACKED_SMOOTH_TUFF_SLAB);
        cracked_smooth_tuffPool.wall(ModBlocks.CRACKED_SMOOTH_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool cobbled_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COBBLED_TUFF);
        cobbled_tuffPool.stairs(ModBlocks.COBBLED_TUFF_STAIRS);
        cobbled_tuffPool.slab(ModBlocks.COBBLED_TUFF_SLAB);
        cobbled_tuffPool.wall(ModBlocks.COBBLED_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_cobbled_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_COBBLED_TUFF);
        mossy_cobbled_tuffPool.stairs(ModBlocks.MOSSY_COBBLED_TUFF_STAIRS);
        mossy_cobbled_tuffPool.slab(ModBlocks.MOSSY_COBBLED_TUFF_SLAB);
        mossy_cobbled_tuffPool.wall(ModBlocks.MOSSY_COBBLED_TUFF_WALL);


        BlockStateModelGenerator.BlockTexturePool mossy_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_TUFF_BRICKS);
        mossy_tuff_bricksPool.stairs(ModBlocks.MOSSY_TUFF_BRICKS_STAIRS);
        mossy_tuff_bricksPool.slab(ModBlocks.MOSSY_TUFF_BRICKS_SLAB);
        mossy_tuff_bricksPool.wall(ModBlocks.MOSSY_TUFF_BRICKS_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_tuff_bricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_TUFF_BRICKS);
        cracked_tuff_bricksPool.stairs(ModBlocks.CRACKED_TUFF_BRICKS_STAIRS);
        cracked_tuff_bricksPool.slab(ModBlocks.CRACKED_TUFF_BRICKS_SLAB);
        cracked_tuff_bricksPool.wall(ModBlocks.CRACKED_TUFF_BRICKS_WALL);

        BlockStateModelGenerator.BlockTexturePool tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TUFF_TILES);
        tuff_tilesPool.stairs(ModBlocks.TUFF_TILES_STAIRS);
        tuff_tilesPool.slab(ModBlocks.TUFF_TILES_SLAB);
        tuff_tilesPool.wall(ModBlocks.TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_TUFF_TILES);
        mossy_tuff_tilesPool.stairs(ModBlocks.MOSSY_TUFF_TILES_STAIRS);
        mossy_tuff_tilesPool.slab(ModBlocks.MOSSY_TUFF_TILES_SLAB);
        mossy_tuff_tilesPool.wall(ModBlocks.MOSSY_TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_tuff_tilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_TUFF_TILES);
        cracked_tuff_tilesPool.stairs(ModBlocks.CRACKED_TUFF_TILES_STAIRS);
        cracked_tuff_tilesPool.slab(ModBlocks.CRACKED_TUFF_TILES_SLAB);
        cracked_tuff_tilesPool.wall(ModBlocks.CRACKED_TUFF_TILES_WALL);

        BlockStateModelGenerator.BlockTexturePool mossy_polished_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MOSSY_POLISHED_TUFF);
        mossy_polished_tuffPool.stairs(ModBlocks.MOSSY_POLISHED_TUFF_STAIRS);
        mossy_polished_tuffPool.slab(ModBlocks.MOSSY_POLISHED_TUFF_SLAB);
        mossy_polished_tuffPool.wall(ModBlocks.MOSSY_POLISHED_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool cracked_polished_tuffPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRACKED_POLISHED_TUFF);
        cracked_polished_tuffPool.stairs(ModBlocks.CRACKED_POLISHED_TUFF_STAIRS);
        cracked_polished_tuffPool.slab(ModBlocks.CRACKED_POLISHED_TUFF_SLAB);
        cracked_polished_tuffPool.wall(ModBlocks.CRACKED_POLISHED_TUFF_WALL);

        BlockStateModelGenerator.BlockTexturePool tuff_brickworkPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TUFF_BRICKWORK);
        tuff_brickworkPool.stairs(ModBlocks.TUFF_BRICKWORK_STAIRS);
        tuff_brickworkPool.slab(ModBlocks.TUFF_BRICKWORK_SLAB);
        tuff_brickworkPool.wall(ModBlocks.TUFF_BRICKWORK_WALL);


        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TUFF_CARVED_WINDOW);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TUFF_CARVED_WINDOW_PANE);


        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_CABBAGES, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_ONIONS, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_TOMATOES, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_CARROTS, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_POTATOES, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILD_BEETROOTS, BlockStateModelGenerator.TintType.NOT_TINTED);


        registerVerticalSlab(blockStateModelGenerator, ModBlocks.BLUE_TUFF_VERTICAL_SLAB, ModBlocks.BLUE_TUFF);



// Chiseled Blokken
        List<CustomBlockTextures> customBlocks = List.of(
                new CustomBlockTextures(ModBlocks.CHISELED_DEEPSLATE_BRICKS,
                        "block/chiseled_deepslate_bricks",
                        "block/chiseled_deepslate_bricks_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_DEEPSLATE_TILES,
                        "block/chiseled_deepslate_tiles",
                        "block/chiseled_deepslate_tiles_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_POLISHED_DEEPSLATE,
                        "block/chiseled_polished_deepslate",
                        "block/chiseled_polished_deepslate_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_SMOOTH_DEEPSLATE,
                        "block/chiseled_smooth_deepslate",
                        "block/chiseled_smooth_deepslate_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_TUFF,
                        "block/chiseled_tuff",
                        "block/chiseled_tuff_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_TUFF_BRICKS,
                        "block/chiseled_tuff_bricks",
                        "block/chiseled_tuff_bricks_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_SMOOTH_STONE,
                        "block/chiseled_smooth_stone",
                        "block/chiseled_smooth_stone_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_STONE,
                        "block/chiseled_stone",
                        "block/chiseled_stone_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_POLISHED_STONE,
                        "block/chiseled_polished_stone",
                        "block/chiseled_polished_stone_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_STONE_TILES,
                        "block/chiseled_stone_tiles",
                        "block/chiseled_stone_tiles_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_POLISHED_BLUE_TUFF,
                        "block/chiseled_polished_blue_tuff",
                        "block/chiseled_polished_blue_tuff_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_BLUE_TUFF_BRICKS,
                        "block/chiseled_blue_tuff_bricks",
                        "block/chiseled_blue_tuff_bricks_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_POLISHED_GREEN_TUFF,
                        "block/chiseled_polished_green_tuff",
                        "block/chiseled_polished_green_tuff_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_GREEN_TUFF,
                        "block/chiseled_green_tuff",
                        "block/chiseled_green_tuff_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_GREEN_TUFF_BRICKS,
                        "block/chiseled_green_tuff_bricks",
                        "block/chiseled_green_tuff_bricks_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_GREEN_TUFF_TILES,
                        "block/chiseled_green_tuff_tiles",
                        "block/chiseled_green_tuff_tiles_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_SMOOTH_GREEN_TUFF,
                        "block/chiseled_smooth_green_tuff",
                        "block/chiseled_smooth_green_tuff_top"),
                new CustomBlockTextures(ModBlocks.GILDED_CHISELED_POLISHED_GREEN_TUFF,
                        "block/gilded_chiseled_polished_green_tuff",
                        "block/gilded_chiseled_polished_green_tuff_top"),
                new CustomBlockTextures(ModBlocks.GILDED_CHISELED_GREEN_TUFF,
                        "block/gilded_chiseled_green_tuff",
                        "block/gilded_chiseled_green_tuff_top"),
                new CustomBlockTextures(ModBlocks.GILDED_CHISELED_GREEN_TUFF_BRICKS,
                        "block/gilded_chiseled_green_tuff_bricks",
                        "block/gilded_chiseled_green_tuff_bricks_top"),
                new CustomBlockTextures(ModBlocks.GILDED_CHISELED_GREEN_TUFF_TILES,
                        "block/gilded_chiseled_green_tuff_tiles",
                        "block/gilded_chiseled_green_tuff_tiles_top"),
                new CustomBlockTextures(ModBlocks.GILDED_CHISELED_SMOOTH_GREEN_TUFF,
                        "block/gilded_chiseled_smooth_green_tuff",
                        "block/gilded_chiseled_smooth_green_tuff_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_POLISHED_TUFF,
                        "block/chiseled_polished_tuff",
                        "block/chiseled_polished_tuff_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_TUFF_TILES,
                        "block/chiseled_tuff_tiles",
                        "block/chiseled_tuff_tiles_top"),
                new CustomBlockTextures(ModBlocks.CHISELED_SMOOTH_TUFF,
                        "block/chiseled_smooth_tuff",
                        "block/chiseled_smooth_tuff_top")

                );

        for (CustomBlockTextures data : customBlocks) {
            blockStateModelGenerator.registerCubeWithCustomTextures(
                    data.block(),
                    data.block(),
                    (b1, b2) -> TextureMap.of(
                            TextureKey.SIDE, new Identifier("ewmedieval", data.side()))
                            .put(TextureKey.UP,    new Identifier("ewmedieval", data.top()))
                            .put(TextureKey.DOWN, new Identifier("ewmedieval", data.top()))
                            .put(TextureKey.PARTICLE,new Identifier("ewmedieval", data.side()))
            );
        }


        List<CustomBlockTextures> pillarBlocks = List.of(
                new CustomBlockTextures(ModBlocks.DEEPSLATE_PILLAR, "block/deepslate_pillar", "block/deepslate_pillar_top"),
                new CustomBlockTextures(ModBlocks.MOSSY_DEEPSLATE_PILLAR, "block/mossy_deepslate_pillar", "block/mossy_deepslate_pillar_top"),
                new CustomBlockTextures(ModBlocks.CRACKED_DEEPSLATE_PILLAR, "block/cracked_deepslate_pillar", "block/cracked_deepslate_pillar_top"),
                new CustomBlockTextures(ModBlocks.STONE_PILLAR, "block/stone_pillar" , "block/stone_pillar_top"),
                new CustomBlockTextures(ModBlocks.MOSSY_STONE_PILLAR, "block/mossy_stone_pillar" , "block/mossy_stone_pillar_top"),
                new CustomBlockTextures(ModBlocks.CRACKED_STONE_PILLAR, "block/cracked_stone_pillar" , "block/cracked_stone_pillar_top"),
                new CustomBlockTextures(ModBlocks.BLUE_TUFF_PILLAR, "block/blue_tuff_pillar", "block/blue_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.MOSSY_BLUE_TUFF_PILLAR, "block/mossy_blue_tuff_pillar", "block/mossy_blue_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.CRACKED_BLUE_TUFF_PILLAR, "block/cracked_blue_tuff_pillar", "block/cracked_blue_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.GREEN_TUFF_PILLAR, "block/green_tuff_pillar", "block/green_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.MOSSY_GREEN_TUFF_PILLAR, "block/mossy_green_tuff_pillar", "block/mossy_green_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.CRACKED_GREEN_TUFF_PILLAR, "block/cracked_green_tuff_pillar", "block/cracked_green_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.TUFF_PILLAR, "block/tuff_pillar", "block/tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.MOSSY_TUFF_PILLAR, "block/mossy_tuff_pillar", "block/mossy_tuff_pillar_top"),
                new CustomBlockTextures(ModBlocks.CRACKED_TUFF_PILLAR, "block/cracked_tuff_pillar", "block/cracked_tuff_pillar_top")
                );

        for (CustomBlockTextures data : pillarBlocks) {
            Identifier side = new Identifier("ewmedieval", data.side()); // zijtexture
            Identifier end  = new Identifier("ewmedieval", data.top());  // uiteinden (top + bottom)

            blockStateModelGenerator.registerAxisRotated(
                    data.block(),
                    TexturedModel.CUBE_COLUMN.andThen(tex -> tex
                            .put(TextureKey.SIDE, side)
                            .put(TextureKey.END,  end)
                    )
            );
}

        }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.TIN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_TIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_SILVER, Models.GENERATED);
        itemModelGenerator.register(ModItems.KHAZAD_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.KHAZAD_STEEL_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURZUM_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURZUM_STEEL_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.EDHEL_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.EDHEL_STEEL_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRUDE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRUDE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_LEAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_MITHRIL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_MIXTURE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_IRON_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_ASH_MIXTURE, Models.GENERATED);

        itemModelGenerator.register(ModItems.RAW_HORSE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_SWAN, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_GOAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_HORSE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_SWAN, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_GOAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAT_WING, Models.GENERATED);
        itemModelGenerator.register(ModItems.SMOKED_BAT_WING, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_WOLF, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_WOLF, Models.GENERATED);

        itemModelGenerator.register(ModItems.LEMBAS, Models.GENERATED);

        itemModelGenerator.register(ModItems.CAKE_SLICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUMPKIN_SLICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STUFFED_PUMPKIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROAST_CHICKEN, Models.GENERATED);

        itemModelGenerator.register(ModItems.CACTUS_FLESH, Models.GENERATED);
        itemModelGenerator.register(ModItems.CACTUS_STEAK, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRANBERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIG, Models.GENERATED);
        itemModelGenerator.register(ModItems.KIWI, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEMON, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGO, Models.GENERATED);
        itemModelGenerator.register(ModItems.ORANGE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PEACH, Models.GENERATED);
        itemModelGenerator.register(ModItems.PEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAKED_PEAR, Models.GENERATED);

        itemModelGenerator.register(ModItems.ARKENSTONE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FUR, Models.GENERATED);
        itemModelGenerator.register(ModItems.ASH, Models.GENERATED);
        itemModelGenerator.register(ModItems.ASH_PIECE, Models.GENERATED);

        itemModelGenerator.register(ModItems.STONE_PEBBLE, Models.GENERATED);

        itemModelGenerator.register(ModItems.CRUDE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CRUDE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CRUDE_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CRUDE_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModItems.BRONZE_SHEARS, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_KNIFE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.BRONZE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModItems.IRON_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GOLDEN_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DIAMOND_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.NETHERITE_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STONE_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_KNIFE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.BACON, Models.GENERATED);
        itemModelGenerator.register(ModItems.MINCED_BEEF, Models.GENERATED);
        itemModelGenerator.register(ModItems.TOMATO, Models.GENERATED);
        itemModelGenerator.register(ModItems.CABBAGE_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CABBAGE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ONION, Models.GENERATED);
        itemModelGenerator.register(ModItems.RICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RICE_PANICLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHICKEN_CUTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUTTON_CHOPS, Models.GENERATED);

        itemModelGenerator.register(ModItems.TOMATO_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.COD_SLICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COD_ROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.CABBAGE_LEAF, Models.GENERATED);
        itemModelGenerator.register(ModItems.HAM, Models.GENERATED);


        itemModelGenerator.register(ModItems.ROPE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DOG_FOOD, Models.GENERATED);




    }
}
