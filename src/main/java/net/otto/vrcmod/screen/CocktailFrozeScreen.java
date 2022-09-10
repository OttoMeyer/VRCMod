package net.otto.vrcmod.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.otto.vrcmod.VRCMod;

public class CocktailFrozeScreen extends HandledScreen<CocktailFrozeScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(VRCMod.MOD_ID "textures/gui/gem_infusing_station_gui.png")

    public CocktailFrozeScreen(CocktailFrozeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }
}
