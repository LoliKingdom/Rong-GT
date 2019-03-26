package gregtech.integration.mekanism;

import java.util.ArrayList;
import java.util.HashMap;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Method;

public class MekanismProcessingHandler {
	
	private static HashMap<Material, OreGas> registeredSlurries = new HashMap<>();
	private static HashMap<Material, OreGas> registeredCleanSlurries = new HashMap<>();
	
	@Method(modid = "mekanism")
	public static void preInitGas() {
		for(Material m : DustMaterial.MATERIAL_REGISTRY) {
			if(m.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				OreGas cleanSlurry = new OreGas("clean_" + m.toString(), "_slurry");
				cleanSlurry.setVisible(false).setTint(m.materialRGB);
				registeredCleanSlurries.put(m, cleanSlurry);
				OreGas dirtySlurry = new OreGas("dirty_" + m.toString(), "_slurry");
				dirtySlurry.setCleanGas(cleanSlurry).setVisible(false).setTint(m.materialRGB);
				registeredSlurries.put(m, dirtySlurry);
			}
		}
	}
	
	@Method(modid = "mekanism")
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
	@Method(modid = "mekanism")
	public static void initRecipes() {
		for(Material material : DustMaterial.MATERIAL_REGISTRY) {
			if(material instanceof DustMaterial && material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				DustMaterial m = (DustMaterial)material;	
				//TIL: Can input Fluid as a GasStack
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.ore, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.oreEndstone, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.oreNetherrack, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.oreGravel, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.oreSand, m), getSlurry(m, false, 1000));
				RecipeHandler.addChemicalDissolutionChamberRecipe(OreDictUnifier.get(OrePrefix.oreSandstone, m), getSlurry(m, false, 1000));
				
				RecipeHandler.addChemicalWasherRecipe(getSlurry(m, false, 20), getSlurry(m, true, 10));
				
				RecipeHandler.addChemicalCrystallizerRecipe(getSlurry(m, true, 250), OreDictUnifier.get(OrePrefix.crystal, m));

				RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
					.fluidInputs(new FluidStack(m.getMaterialCleanSlurry(), 220))
					.outputs(OreDictUnifier.get(OrePrefix.crystal, m))
					.EUt(1480)
					.duration((int)m.getAverageMass() / 2)
					.buildAndRegister();
				
				RecipeHandler.addChemicalInjectionChamberRecipe(OreDictUnifier.get(OrePrefix.crystal, m), MekanismFluids.HydrogenChloride, OreDictUnifier.get(OrePrefix.shard, m));

				RecipeHandler.addPurificationChamberRecipe(OreDictUnifier.get(OrePrefix.shard, m), OreDictUnifier.get(OrePrefix.clump, m));				
				RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().input(OrePrefix.shard, m).fluidInputs(Materials.Oxygen.getFluid(400)).outputs(OreDictUnifier.get(OrePrefix.clump, m)).duration(350).EUt(320).buildAndRegister();
				
				RecipeHandler.addCrusherRecipe(OreDictUnifier.get(OrePrefix.clump, m), OreDictUnifier.get(OrePrefix.dustDirty, m));
				RecipeMaps.MACERATOR_RECIPES.recipeBuilder().input(OrePrefix.clump, m).outputs(OreDictUnifier.get(OrePrefix.dustDirty, m)).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, m), 2500).duration(140).EUt(18).buildAndRegister();
				RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().input(OrePrefix.clump, m).outputs(OreDictUnifier.get(OrePrefix.dustDirty, m)).duration(20).EUt(12).buildAndRegister();

				RecipeHandler.addEnrichmentChamberRecipe(OreDictUnifier.get(OrePrefix.dustDirty, m), OreDictUnifier.get(OrePrefix.dust, m));

		        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
				  	.input(OrePrefix.crushed, m)
				  	.fluidInputs(Materials.SulfuricAcid.getFluid(600))
				  	.fluidOutputs(new FluidStack(m.getMaterialDirtySlurry(), 1000))
				  	.duration(400)
				  	.EUt(96)
				  	.buildAndRegister();
				
		        //TODO: Add chanceOutput and waste output
				RecipeMaps.MIXER_RECIPES.recipeBuilder()
				  	.fluidInputs(new FluidStack(m.getMaterialDirtySlurry(), 20), ModHandler.getWater(50))
				  	.fluidOutputs(new FluidStack(m.getMaterialCleanSlurry(), 10))
				  	.duration(40)
				  	.EUt(24)
				  	.buildAndRegister(); 
			}
		}
	}
	
	private static GasStack getSlurry(Material m, boolean isClean, int amount) {
		if(isClean && registeredCleanSlurries.containsKey(m)) {
			return new GasStack(registeredCleanSlurries.get(m), amount);
		}
		else if (!isClean && registeredSlurries.containsKey(m)){
			return new GasStack(registeredSlurries.get(m), amount);
		}
		//Again, this should not happen
		return null;
	}
}