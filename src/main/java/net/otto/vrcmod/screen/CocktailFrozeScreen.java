package net.otto.vrcmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.otto.vrcmod.VRCMod;

public class CocktailFrozeScreen extends HandledScreen<CocktailFrozeScreenHandler> {

    private static final Identifier TEXTURE =
            new Identifier(VRCMod.MOD_ID, "textures/gui/cocktail_froze_barrel_gui.png");

    public CocktailFrozeScreen(CocktailFrozeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundHeight - textRenderer.getWidth(title)) / 2;
    }


    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(matrices, x, y);
        renderFuelArrow(matrices, x, y);

    }

    private void renderProgressArrow(MatrixStack matrices, int x, int y) {
        if(handler.isCrafting()) {
            drawTexture(matrices, x + 85, y + 34, 176, 0, 7, handler.getScaledProgress());
        }
    }

    private void renderFuelArrow(MatrixStack matrices, int x, int y) {
        if(handler.isFrozing()) {
            drawTexture(matrices, x + 60, y + 38 + 16 - handler.getScaledFuel(), 176,
                    23 + 16 - handler.getScaledFuel(), 17, handler.getScaledFuel());
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
