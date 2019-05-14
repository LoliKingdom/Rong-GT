package gregtech.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

public class BedrockDrillRecipeLoader {
	
	public static void init() {
		
		//TODO: Better recipes		
		//MV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 200)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Ilmenite), 1500)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Gold), 2500)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Cobaltite), 3000)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Carbon), 4000)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.Obsidian), 1000)
				  .EUt(74)
				  .buildAndRegister();
				  
		//HV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 500)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Diamond), 800)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Emerald), 800)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Rutile), 2400)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Neodymium), 1400)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Bedrock), 4000)
				  .EUt(280)
				  .buildAndRegister();
		
		//EV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 6500)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.Adamantine), 2000)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Cobaltite), 2000)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Xenotime), 3500)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Platinum), 4000)	  
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Bedrock), 5000)
				  .EUt(1048)
				  .buildAndRegister();
		
		//IV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
		  		  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.Lutetium), 200)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.RareEarth), 1000)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.Adamantine), 3500)
				  .EUt(6074)
				  .buildAndRegister();
		
		//LuV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.Lutetium), 2000)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dust, Materials.RareEarth, 2), 1500)
				  .chancedOutputTierless(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Iridium), 4000)
				  .EUt(12105)
				  .buildAndRegister();			  
	}
}
