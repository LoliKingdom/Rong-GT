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
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Ilmenite), 1500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Gold), 2500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Cobaltite), 3000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Carbon), 4000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Obsidian), 4000)
				  .EUt(74)
				  .buildAndRegister();
				  
		//HV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Diamond), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Emerald), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Rutile), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Bedrock), 4000)
				  .EUt(280)
				  .buildAndRegister();
		
		//EV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth), 1500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Adamantine), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Cobaltite), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Xenotime), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Platinum), 4000)	  
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Bedrock), 5000)
				  .EUt(1048)
				  .buildAndRegister();
		
		//IV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
		  		  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Lutetium), 200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Adamantine), 3500)
				  .EUt(6074)
				  .buildAndRegister();
		
		//LuV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Lutetium), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth, 2), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Iridium), 4000)
				  .EUt(12105)
				  .buildAndRegister();			  
	}
}
