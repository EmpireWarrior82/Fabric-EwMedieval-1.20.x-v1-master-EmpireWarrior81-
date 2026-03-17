package net.empire.ewmedieval.client.renderer;

import net.empire.ewmedieval.block.custom.CuttingBoardBlock;
import net.empire.ewmedieval.block.entity.CuttingBoardBlockEntity;
import net.empire.ewmedieval.util.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class CuttingBoardRenderer implements BlockEntityRenderer<CuttingBoardBlockEntity> {

    public CuttingBoardRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(CuttingBoardBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        Direction direction = entity.getCachedState().get(CuttingBoardBlock.FACING).getOpposite();
        ItemStack boardStack = entity.getStoredItem();
        // Use block pos as a unique seed for item model randomness
        int posLong = (int) entity.getPos().asLong();

        if (!boardStack.isEmpty()) {
            matrices.push();

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

            // Check if the item renders as a 3D block model
            matrices.push();
            BakedModel model = itemRenderer.getModel(boardStack, entity.getWorld(), null, 0);
            model.getTransformation().getTransformation(ModelTransformationMode.FIXED).apply(false, matrices);
            boolean isBlockItem = model.hasDepth();
            matrices.pop();

            if (entity.isItemCarvingBoard()) {
                renderItemCarved(matrices, direction, boardStack);
            } else if (isBlockItem && !boardStack.isIn(ModTags.FLAT_ON_CUTTING_BOARD)) {
                renderBlock(matrices, direction);
            } else {
                renderItemLayingDown(matrices, direction);
            }

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    boardStack,
                    ModelTransformationMode.FIXED,
                    light,
                    overlay,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    posLong);

            matrices.pop();
        }
    }

    /**
     * Renders a regular item flat on the cutting board surface.
     */
    private void renderItemLayingDown(MatrixStack matrices, Direction direction) {
        // Center above the board
        matrices.translate(0.5, 0.08, 0.5);
        // Rotate to face the board's front
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-direction.asRotation()));
        // Lay flat
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        // Scale down
        matrices.scale(0.6F, 0.6F, 0.6F);
    }

    /**
     * Renders a block item sitting upright on the cutting board.
     */
    private void renderBlock(MatrixStack matrices, Direction direction) {
        // Center and raise above the board
        matrices.translate(0.5, 0.27, 0.5);
        // Rotate to face the board's front
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-direction.asRotation()));
        // Scale down slightly
        matrices.scale(0.8F, 0.8F, 0.8F);
    }

    /**
     * Renders a tool carved/placed onto the board surface (sneak + right-click placement).
     */
    private void renderItemCarved(MatrixStack matrices, Direction direction, ItemStack itemStack) {
        // Center and raise above the board
        matrices.translate(0.5, 0.23, 0.5);
        // Rotate to face the board's front
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-direction.asRotation() + 180));

        // Angle depends on tool type — pickaxes/hoes sit at a different angle than swords etc.
        Item tool = itemStack.getItem();
        float poseAngle;
        if (tool instanceof PickaxeItem || tool instanceof HoeItem) {
            poseAngle = 225.0F;
        } else if (tool instanceof TridentItem) {
            poseAngle = 135.0F;
        } else {
            poseAngle = 180.0F;
        }
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(poseAngle));
        matrices.scale(0.6F, 0.6F, 0.6F);
    }
}