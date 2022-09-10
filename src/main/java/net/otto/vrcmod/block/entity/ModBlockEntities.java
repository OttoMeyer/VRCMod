package net.otto.vrcmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.otto.vrcmod.VRCMod;
import net.otto.vrcmod.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<CocktailFrozeBlockEntity> COCKTAIL_FROZE_BARREL;

    public static void registerBlockEntities(){
        COCKTAIL_FROZE_BARREL = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(VRCMod.MOD_ID, "gem_infusing_station"),
                FabricBlockEntityTypeBuilder.create(CocktailFrozeBlockEntity::new, ModBlocks.COCKTAIL_FROZE_BARREL).build(null));
    }
}
