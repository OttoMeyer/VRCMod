package net.otto.vrcmod;

import net.fabricmc.api.ModInitializer;
import net.otto.vrcmod.block.ModBlocks;
import net.otto.vrcmod.block.entity.ModBlockEntities;
import net.otto.vrcmod.item.ModItems;
import net.otto.vrcmod.recipe.ModRecipe;
import net.otto.vrcmod.screen.ModScreenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VRCMod implements ModInitializer {

	public static final String MOD_ID = "vrcmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandler.registerAllScreenHandlers();

		ModRecipe.registerRecipes();
	}
}
