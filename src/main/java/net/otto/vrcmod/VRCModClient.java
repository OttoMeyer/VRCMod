package net.otto.vrcmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.otto.vrcmod.screen.CocktailFrozeScreen;
import net.otto.vrcmod.screen.ModScreenHandler;

public class VRCModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(){

        HandledScreens.register(ModScreenHandler.COCKTAIL_FROZE_SCREEN_HANDLER, CocktailFrozeScreen::new);

    }
}
