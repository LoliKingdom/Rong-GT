package gregtech.integration.mekanism;

import java.util.Iterator;
import java.util.Map;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.MetaFluids;
import gregtech.common.items.MetaItems;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import mekanism.common.MekanismFluids;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.recipe.machines.MachineRecipe;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;

public class MekanismProcessingHandler {
	
	/*public static void initGas() {
		for(Material m : DustMaterial.MATERIAL_REGISTRY) {
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				OreGas cleanSlurry = new OreGas("clean_" + m.toString(), "_slurry");
				cleanSlurry.setVisible(false).setTint(m.materialRGB);
				OreGas dirtySlurry = new OreGas("dirty_" + m.toString(), "_slurry");
				dirtySlurry.setCleanGas(cleanSlurry).setVisible(false).setTint(m.materialRGB);
			}
		}
	}*/

	public static void initRecipes() {
		for(Material m : DustMaterial.MATERIAL_REGISTRY) {
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {		
				
				//TIL: Can input Fluid as a GasStack
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				
				RecipeHandler.addChemicalWasherRecipe(getSlurry(m, false, 10), getSlurry(m, true, 10));
				
				RecipeHandler.addChemicalCrystallizerRecipe(getSlurry(m, true, 200), OreDictUnifier.get(OrePrefix.crystal, m));
				
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crystal, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m));

				
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crushed, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m, 4));
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crystal, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m));
				
				RecipeHandler.addPurificationChamberRecipe(OreDictUnifier.get(OrePrefix.shard, m), OreDictUnifier.get(OrePrefix.clump, m));
				RecipeHandler.addCrusherRecipe(OreDictUnifier.get(OrePrefix.clump, m), OreDictUnifier.get(OrePrefix.dust, m));

		        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
				  	.input(OrePrefix.crushed, m)
				  	.fluidInputs(Materials.SulfuricAcid.getFluid(600))
				  	.fluidOutputs(FluidRegistry.getFluidStack("slurry." + m.toString(), 600))
				  	.duration(400)
				  	.EUt(96)
				  	.buildAndRegister();
				
				RecipeMaps.MIXER_RECIPES.recipeBuilder()
				  	.fluidInputs(FluidRegistry.getFluidStack("slurry." + m.toString(), 600), Materials.Chlorine.getFluid(1000))
				  	.fluidOutputs(FluidRegistry.getFluidStack("clean_slurry." + m.toString(), 500))
				  	.duration(200)
				  	.EUt(24)
				  	.buildAndRegister();
				
				RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
			  		.fluidInputs(FluidRegistry.getFluidStack("slurry." + m.toString(), 2000))
			  		.inputs(MetaItems.THERMITE_DUST.getStackForm())
			  		.outputs(OreDictUnifier.get(OrePrefix.dustDirty, m, 3))
			  		.chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, m), 1000)
			  		.duration(80)
			  		.EUt(96)
			  		.buildAndRegister();	
				
				RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
				  	.fluidInputs(FluidRegistry.getFluidStack("clean_slurry." + m.toString(), 2000))
				  	.inputs(MetaItems.THERMITE_DUST.getStackForm())
				  	.outputs(OreDictUnifier.get(OrePrefix.crystal, m, 5))
				  	.duration(80)
				  	.EUt(480)
				  	.buildAndRegister();			 
			}
		}
	}
	
	private static GasStack getSlurry(Material m, boolean isClean, int amount) {
		if(isClean) {
			return new GasStack(GasRegistry.getGas(FluidRegistry.getFluid("clean_slurry." + m.toString())), amount);	
		}
		else {
			return new GasStack(GasRegistry.getGas(FluidRegistry.getFluid("slurry." + m.toString())), amount);
		}
	}
}