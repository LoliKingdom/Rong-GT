package gregtech.loaders.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityLoader {
	
	public static void init() {
		
		//Parts
        ModHandler.addShapedRecipe("casing_primitive_bricks", MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.PRIMITIVE_BRICKS, 1), "XX", "XX", 'X', MetaItems.FIRECLAY_BRICK);
        ModHandler.addShapedRecipe("casing_coke_bricks", MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.COKE_BRICKS, 1), "XX", "XX", 'X', MetaItems.COKE_OVEN_BRICK);

        //Multiblock Main Machine
        ModHandler.addShapedRecipe("coke_oven", MetaTileEntities.COKE_OVEN.getStackForm(), "PIP", "IwI", "PIP", 'P', MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.COKE_BRICKS), 'I', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.addShapelessRecipe("coke_oven_hatch", MetaTileEntities.COKE_OVEN_HATCH.getStackForm(), MetaBlocks.METAL_CASING.getItemVariant(MetalCasingType.COKE_BRICKS), MetaTileEntities.BRONZE_TANK.getStackForm());

	}

}
