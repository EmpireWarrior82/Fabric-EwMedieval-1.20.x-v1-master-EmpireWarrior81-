package net.empire.ewmedieval.gui.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.empire.ewmedieval.EwMedieval;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ForgeScreen extends HandledScreen<ForgeScreenHandler> {
    // verwijst naar je texture in resources/assets/ewmedieval/textures/gui/forge_gui.png
    private static final Identifier TEXTURE = new Identifier(EwMedieval.MOD_ID, "textures/gui/forge_gui.png");

    public ForgeScreen(ForgeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // titels uitzetten (zet ze buiten beeld)
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // achtergrond tekenen
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // vlam (burn-time indicator)
        renderFuelIndicator(context, x, y);

        // pijltje (craft progress)
        renderProgressArrow(context, x, y);
    }

    private void renderFuelIndicator(DrawContext context, int x, int y) {
        if (handler.isBurning()) {
            int fuelHeight = handler.getFuelScaled(); // max 14 px hoog
            context.drawTexture(TEXTURE,
                    x + 42, y + 36 + (14 - fuelHeight), // GUI-coords
                    176, 14 - fuelHeight,               // texture-coords (begin flame sprite)
                    14, fuelHeight);                    // breedte=14, hoogte=variabel
        }
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        int progress = handler.getScaledProgress(); // max 26 px breed
        if (progress > 0) {
            context.drawTexture(TEXTURE,
                    x + 87, y + 15,   // GUI-coords
                    176, 14,          // texture start van arrow
                    progress, 17);    // breedte variabel, hoogte=17
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
