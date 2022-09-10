package net.otto.vrcmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.otto.vrcmod.VRCMod;
import net.otto.vrcmod.block.custom.FrozeBarrel;
import net.otto.vrcmod.item.ModItemGroup;

public class ModBlocks {

    public static final Block COCKTAIL_FROZE_BARREL = registerBlock("cocktail_froze_barrel",
            new FrozeBarrel(FabricBlockSettings.of(Material.WOOD).strength(4f).requiresTool()), ModItemGroup.COCKTAIL);

    private static Block registerBlock(String name, Block block, ItemGroup tab){
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(VRCMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab){
        return Registry.register(Registry.ITEM, new Identifier(VRCMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));

    }

    public static void registerModBlocks(){
        VRCMod.LOGGER.debug("Register ModBlocks for " + VRCMod.MOD_ID);
    }
}
