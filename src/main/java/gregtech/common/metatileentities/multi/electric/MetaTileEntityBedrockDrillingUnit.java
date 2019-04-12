package gregtech.common.metatileentities.multi.electric;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.BlockWorldState;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockDrillHead;
import gregtech.common.blocks.BlockDrillHead.DrillHeadType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityMultiFurnace.MultiFurnaceWorkable;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MetaTileEntityBedrockDrillingUnit extends RecipeMapMultiblockController {
	
	private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = {
			MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.EXPORT_ITEMS, MultiblockAbility.INPUT_ENERGY
	};

	public MetaTileEntityBedrockDrillingUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BEDROCK_DRILL_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityElectricBlastFurnace(metaTileEntityId);
    }
    
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
    }
    
    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }
    
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
            .aisle("BBB", "XXX", "CCC", "#Z#", "#Z#")
            .aisle("BBB", "XXX", "CCC", "ZCZ", "ZCZ")
            .aisle("BBB", "XXX", "CSC", "#Z#", "#Z#")
            .setAmountAtLeast('B', 3)
            .where('S', selfPredicate())
            .where('X', drillHeadPredicate())
            .where('C', statePredicate(MetaBlocks.METAL_CASING.getState(MetalCasingType.INVAR_HEATPROOF)).or(abilityPartPredicate(ALLOWED_ABILITIES)))
            .where('#', isAirPredicate())
            .where('Z', blockPredicate(MetaBlocks.FRAMES.get(Materials.Steel)))
            .where('B', blockPredicate(Blocks.BEDROCK))           
            .build();
    }
    
    public static Predicate<BlockWorldState> drillHeadPredicate() {
        return blockWorldState -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if(!(blockState.getBlock() instanceof BlockDrillHead))
                return false;
            BlockDrillHead blockDrillHead = (BlockDrillHead)blockState.getBlock();
            DrillHeadType drillType = blockDrillHead.getState(blockState);
            DrillHeadType currentDrillType = blockWorldState.getMatchContext().getOrPut("DrillHeadType", drillType);
            return currentDrillType.getName().equals(drillType.getName());
        };
    }
    
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.drill"));
        }
        super.addDisplayText(textList);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
    	return Textures.SOLID_STEEL_CASING;
    }
}