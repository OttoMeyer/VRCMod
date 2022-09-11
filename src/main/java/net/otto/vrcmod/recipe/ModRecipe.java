package net.otto.vrcmod.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.otto.vrcmod.VRCMod;

public class ModRecipe {
    public static void registerRecipes(){
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VRCMod.MOD_ID, CocktailFrozeRecipe.Serializer.ID),
                CocktailFrozeRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(VRCMod.MOD_ID, CocktailFrozeRecipe.Serializer.ID),
                CocktailFrozeRecipe.Type.INSTANCE);


    }
}
