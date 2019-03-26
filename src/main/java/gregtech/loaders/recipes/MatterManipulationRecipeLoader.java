package gregtech.loaders.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class MatterManipulationRecipeLoader {
	
    public static void init() {
    	RecipeMaps.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int) (Materials.Hydrogen.getAverageMass() * 20)).EUt(32).fluidInputs((Materials.Hydrogen.getFluid(1000))).fluidOutputs(Materials.PositiveMatter.getFluid((1))).buildAndRegister();
    	RecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int) (Materials.Hydrogen.getAverageMass() * 20)).EUt(32).inputs(GTUtility.getFilledCell(Materials.Hydrogen.getMaterialFluid(), 1)).fluidOutputs((Materials.Hydrogen.getFluid(2000))).fluidInputs(Materials.PositiveMatter.getFluid(1)).buildAndRegister();
    	for(Material m : Material.MATERIAL_REGISTRY) {
    		if(m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty() && m.getProtons() > 0 && m.getNeutrons() > 0) {
    			FluidMaterial material = (FluidMaterial)m;
            	RecipeMaps.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int)(m.getAverageMass() * 20)).EUt(32).inputs(GTUtility.getFilledCell(material.getMaterialFluid(), 1)).fluidOutputs(Materials.PositiveMatter.getFluid((int)m.getProtons()), Materials.NeutralMatter.getFluid((int)m.getAverageNeutrons())).buildAndRegister();
            	RecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int)(m.getAverageMass() * 20)).EUt(1920).inputs(GTUtility.getFilledCell(material.getMaterialFluid(), 1)).fluidInputs(Materials.PositiveMatter.getFluid((int)m.getProtons()), Materials.NeutralMatter.getFluid((int)m.getAverageNeutrons())).fluidOutputs(material.getFluid(2000)).buildAndRegister();
    		}
    		if(m instanceof DustMaterial && m.getProtons() > 0 && m.getNeutrons() > 0) {
    			DustMaterial material = (DustMaterial)m;
            	RecipeMaps.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int)(m.getAverageMass() * 20)).EUt(32).input(OrePrefix.dust, m).fluidOutputs(Materials.PositiveMatter.getFluid((int)m.getProtons()), Materials.NeutralMatter.getFluid((int)m.getAverageNeutrons())).buildAndRegister();
            	RecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int)(m.getAverageMass() * 20)).EUt(1920).input(OrePrefix.dust, m).fluidInputs(Materials.PositiveMatter.getFluid((int)m.getProtons()), Materials.NeutralMatter.getFluid((int)m.getAverageNeutrons())).outputs(OreDictUnifier.get(OrePrefix.dust, m, 2)).buildAndRegister();
    		}
    	}
    }
}