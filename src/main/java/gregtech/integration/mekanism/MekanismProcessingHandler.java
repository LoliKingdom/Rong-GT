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
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import mekanism.common.MekanismBlocks;
import mekanism.common.MekanismFluids;
import mekanism.common.MekanismItems;
import mekanism.common.Resource;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.recipe.machines.CrystallizerRecipe;
import mekanism.common.recipe.machines.DissolutionRecipe;
import mekanism.common.recipe.machines.MachineRecipe;
import mekanism.common.recipe.machines.WasherRecipe;
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
	
	public static void removeRecipes() {
		for(Gas gas : GasRegistry.getRegisteredGasses()) {
			if(gas instanceof OreGas && !((OreGas)gas).isClean()) {
				OreGas oreGas = (OreGas)gas;
				RecipeHandler.removeRecipe(Recipe.CHEMICAL_WASHER, new WasherRecipe(new GasStack(oreGas, 1), new GasStack(oreGas.getCleanGas(), 1)));
				Resource resource = Resource.getFromName(oreGas.getName());
				if(resource != null) {
					RecipeHandler.removeRecipe(Recipe.CHEMICAL_DISSOLUTION_CHAMBER, 
							new DissolutionRecipe(new ItemStack(MekanismBlocks.OreBlock, 1, resource.ordinal()), new GasStack(oreGas, 1)));
					RecipeHandler.removeRecipe(Recipe.CHEMICAL_CRYSTALLIZER, 
							new CrystallizerRecipe(new GasStack(oreGas.getCleanGas(), 200), new ItemStack(MekanismItems.Crystal, 1, resource.ordinal())));
							
				}				
			}
		}
	}

	//Still needs revision
	public static void initRecipes() {
		for(Material m : DustMaterial.MATERIAL_REGISTRY) {
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {		
				//TIL: Can input Fluid as a GasStack
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				
				RecipeHandler.addChemicalWasherRecipe(getSlurry(m, false, 5), getSlurry(m, true, 5));
				
				RecipeHandler.addChemicalCrystallizerRecipe(getSlurry(m, true, 250), OreDictUnifier.get(OrePrefix.crystal, m));
				RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
					.fluidInputs(FluidRegistry.getFluidStack("clean_slurry." + m.toString(), 200))
					.outputs(OreDictUnifier.get(OrePrefix.crystal, m))
					.EUt(320)
					.duration((int)m.getAverageMass() / 2)
					.buildAndRegister();
				
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crystal, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m));

				RecipeHandler.addPurificationChamberRecipe(OreDictUnifier.get(OrePrefix.shard, m), OreDictUnifier.get(OrePrefix.clump, m));				
				RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().input(OrePrefix.shard, m).fluidInputs(Materials.Oxygen.getFluid(400)).outputs(OreDictUnifier.get(OrePrefix.clump, m)).duration(350).EUt(480).buildAndRegister();
				
				RecipeHandler.addCrusherRecipe(OreDictUnifier.get(OrePrefix.clump, m), OreDictUnifier.get(OrePrefix.dustDirty, m));
				RecipeMaps.MACERATOR_RECIPES.recipeBuilder().input(OrePrefix.clump, m).outputs(OreDictUnifier.get(OrePrefix.dustDirty, m)).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, m), 2500).duration(140).EUt(18).buildAndRegister();
				RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().input(OrePrefix.clump, m).outputs(OreDictUnifier.get(OrePrefix.dustDirty, m)).duration(20).EUt(12).buildAndRegister();

				RecipeHandler.addEnrichmentChamberRecipe(OreDictUnifier.get(OrePrefix.dustDirty, m), OreDictUnifier.get(OrePrefix.dust, m));
				
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.dustImpure, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m, 4));
				
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