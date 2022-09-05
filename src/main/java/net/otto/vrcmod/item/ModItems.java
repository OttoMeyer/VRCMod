package net.otto.vrcmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.otto.vrcmod.VRCMod;
import net.otto.vrcmod.item.custom.SimpleCocktail;

public class ModItems {
    public static final Item COCKTAIL_GLASS = registerItem("cocktail_glass",
            new Item(new FabricItemSettings().group(ModItemGroup.COCKTAIL)));
    public static final Item SWEET_JUICE = registerItem("sweet_juice",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.SWEET_JUICE).maxCount(16)));
    public static final Item SWEETEST_JUICE = registerItem("sweetest_juice",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.SWEETEST_JUICE).maxCount(16)));
    public static final Item CANDY_POP = registerItem("candy_pop",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.CANDY_POP).maxCount(16)));
    public static final Item CHOKAK = registerItem("chokak",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.CHOKAK).maxCount(16)));
    public static final Item FUNNY_JACK = registerItem("funny_jack",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.FUNNY_JACK).maxCount(16)));
    public static final Item HEARTLESS = registerItem("heartless",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.HEARTLESS).maxCount(16)));
    public static final Item NOT_CYAN = registerItem("not_cyan",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.NOT_CYAN).maxCount(16)));
    public static final Item RETAW = registerItem("retaw",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.RETAW).maxCount(16)));
    public static final Item TRIPLE_W = registerItem("triple_w",
            new SimpleCocktail(new FabricItemSettings().group(ModItemGroup.COCKTAIL).food(ModFoodComponents.TRIPLE_W).maxCount(16)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(VRCMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        VRCMod.LOGGER.debug("Register Mod Items for " + VRCMod.MOD_ID);
    }
}
