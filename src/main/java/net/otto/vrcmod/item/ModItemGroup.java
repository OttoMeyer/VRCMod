package net.otto.vrcmod.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.otto.vrcmod.VRCMod;

public class ModItemGroup {
    public static final ItemGroup COCKTAIL = FabricItemGroupBuilder.build(
            new Identifier(VRCMod.MOD_ID, "cocktail"), () -> new ItemStack(ModItems.SWEET_JUICE));
}
