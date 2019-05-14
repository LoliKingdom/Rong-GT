package gregtech.integration.jei.multiblock.infos;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class BedrockDrillUnitInfo extends MultiblockInfoPage {
	
	@Override
    public MultiblockControllerBase getController() {
        return MetaTileEntities.BEDROCK_DRILL;
    }

	@Override
	public List<MultiblockShapeInfo> getMatchingShapes() {
		ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
		shapeInfo.add(MultiblockShapeInfo.builder()
				.aisle("BBB", "XXX", "ECE", "#Z#", "#Z#")
				.aisle("BBB", "XXX", "CCC", "ZCZ", "ZCZ")
		        .aisle("BBB", "XXX", "OSF", "#Z#", "#Z#")		        
		        .where('S', MetaTileEntities.BEDROCK_DRILL, EnumFacing.SOUTH)
	            .where('X', Block.getBlockFromItem(OreDictUnifier.get(OrePrefix.block, Materials.Steel).getItem()).getDefaultState())
	            .where('C', MetaBlocks.METAL_CASING.getState(MetalCasingType.INVAR_HEATPROOF))
	            .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.SOUTH)
	            .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
	            .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MV], EnumFacing.NORTH)
	            .where('#', Blocks.AIR.getDefaultState())
	            .where('Z', Block.getBlockFromItem(OreDictUnifier.get(OrePrefix.frameGt, Materials.Steel).getItem()).getDefaultState())
	            .where('B', Blocks.BEDROCK.getDefaultState())                 
	            .build());
		return shapeInfo;
	}

	@Override
	public String[] getDescription() {
		return new String[] {I18n.format("gregtech.multiblock.bedrock_drilling_unit.description")};
	}
}
