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
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 100)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Rutile), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Ilmenite), 1200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Gold), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Carbon), 5000)
				  .EUt(96)
				  .buildAndRegister();
				  
		//HV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Diamond), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Emerald), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Rutile), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .EUt(320)
				  .buildAndRegister();
		
		//EV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Gold), 1500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Adamantium), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Cobaltite), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Platinum), 4000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .EUt(1440)
				  .buildAndRegister();
		
		//IV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
		  		  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Lutetium), 200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Adamantium), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Tungstate), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .EUt(4096)
				  .buildAndRegister();
		
		//LuV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Lutetium), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth, 2), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Iridium), 4000)
				  .EUt(12105)
				  .buildAndRegister();			  
	}
}
