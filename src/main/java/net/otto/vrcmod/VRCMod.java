package net.otto.vrcmod;

import net.fabricmc.api.ModInitializer;
import net.otto.vrcmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VRCMod implements ModInitializer {

	public static final String MOD_ID = "vrcmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}