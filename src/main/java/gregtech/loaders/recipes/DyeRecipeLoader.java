package gregtech.loaders.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import gregtech.api.GTValues;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class DyeRecipeLoader {
	
	//Copied from TGILibrary, really can't be arsed with cleaning it up	
	/*
	 * TODO:
	 * 1. AE2 CABLES
	 * 2. IF CONVEYOR BELTS
	 * 3. FUSED QUARTZ
	 * 4. THAUMCRAFT NITOR
	 * 5. (DONE)THERMAL FOUNDATION ROCKWOOL
	 * 6. ITEMFRAMES
	 * 7. CANDLES
	 * 8. STAINED PLANKS
	 * 9. ENDERIO SHIT
	 * 10. OC FLOPPY DISKS
	 * 11. QUARK ITEMS
	 * 
	 */
	
	public static void init() {
		
		List<String> itemDyes = Arrays.asList("dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite");
		List<String> waterDyes = Arrays.asList("water_dye_black", "water_dye_red", "water_dye_green", "water_dye_brown", "water_dye_blue", "water_dye_purple", "water_dye_cyan", "water_dye_lightgray", "water_dye_gray", "water_dye_pink", "water_dye_lime", "water_dye_yellow", "water_dye_lightblue", "water_dye_magenta", "water_dye_orange", "water_dye_white");
		List<String> chemicalDyes = Arrays.asList("chemical_dye_black", "chemical_dye_red", "chemical_dye_green", "chemical_dye_brown", "chemical_dye_blue", "chemical_dye_purple", "chemical_dye_cyan", "chemical_dye_lightgray", "chemical_dye_gray", "chemical_dye_pink", "chemical_dye_lime", "chemical_dye_yellow", "chemical_dye_lightblue", "chemical_dye_magenta", "chemical_dye_orange", "chemical_dye_white");
		
		ArrayList<Integer> dyeMetas = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));		
		ArrayList<Integer> woolMetas = new ArrayList<Integer>(Arrays.asList(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		Iterator<Integer> dyeIterator = dyeMetas.iterator();
		Iterator<Integer> woolIterator = woolMetas.iterator();
				
		while(dyeIterator.hasNext() && woolIterator.hasNext()) {
			int dye = dyeIterator.next();
			int meta = woolIterator.next();
			if(dye != 15 && meta != 0) {
				RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(18).input(itemDyes.get(dye), 4).inputs(new ItemStack(Blocks.WOOL, 1, 0)).outputs(new ItemStack(Blocks.WOOL, 1, meta)).buildAndRegister();
			
				RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(240).EUt(8).fluidInputs(ModHandler.getWater(500)).input(itemDyes.get(dye), 1).fluidOutputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).buildAndRegister();
				RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(30).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500), Materials.SulfuricAcid.getFluid(500)).input(OrePrefix.dust, Materials.Salt).fluidOutputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 1000)).buildAndRegister();
						
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.WOOL, 1, 0)).outputs(new ItemStack(Blocks.WOOL, 1, meta)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.HARDENED_CLAY)).outputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, meta)).buildAndRegister();			
				
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.WOOL, 1, 0)).outputs(new ItemStack(Blocks.WOOL, 1, meta)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(120).EUt(2).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.HARDENED_CLAY)).outputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, meta)).buildAndRegister();			
						
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.CARPET, 1, 0)).outputs(new ItemStack(Blocks.CARPET, 1, meta)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.CARPET, 1, 0)).outputs(new ItemStack(Blocks.CARPET, 1, meta)).buildAndRegister();		
			}
			
			if(dye != 7 && GTValues.isModLoaded("thermalfoundation")) {
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, 7)).outputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, dye)).buildAndRegister();
				RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, 7)).outputs(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, dye)).buildAndRegister();			
			}
			
			RecipeMaps.CANNER_RECIPES.recipeBuilder().duration(80).EUt(12).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 5000)).inputs(MetaItems.SPRAY_EMPTY.getStackForm()).outputs(MetaItems.SPRAY_CAN_DYES[meta].getStackForm()).buildAndRegister();
			
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.GLASS, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS, 1, meta)).buildAndRegister();
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.GLASS, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS, 1, meta)).buildAndRegister();
		
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(chemicalDyes.get(dye), 100)).inputs(new ItemStack(Blocks.GLASS_PANE, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, meta)).buildAndRegister();
			RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(16).fluidInputs(FluidRegistry.getFluidStack(waterDyes.get(dye), 500)).inputs(new ItemStack(Blocks.GLASS_PANE, 1, 0)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, meta)).buildAndRegister();		

			ModHandler.removeRecipes(new ItemStack(Blocks.WOOL, 1, dye));
			ModHandler.removeRecipes(new ItemStack(Blocks.STAINED_GLASS, 8, dye));
			ModHandler.removeRecipes(new ItemStack(Blocks.STAINED_GLASS_PANE, 16, dye));
			ModHandler.removeRecipes(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 8, dye));
			if(GTValues.isModLoaded("thermalfoundation")) {
				ModHandler.removeRecipes(new ItemStack(Block.getBlockFromName("thermalfoundation:rockwool"), 1, dye));		
			}				
		}
	}
}