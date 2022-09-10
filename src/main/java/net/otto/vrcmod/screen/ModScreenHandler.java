package net.otto.vrcmod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandler {
    public static ScreenHandlerType<CocktailFrozeScreenHandler> COCKTAIL_FROZE_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        COCKTAIL_FROZE_SCREEN_HANDLER = new ScreenHandlerType<>(CocktailFrozeScreenHandler::new);
    }
}

