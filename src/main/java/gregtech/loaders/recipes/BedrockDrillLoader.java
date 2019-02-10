package gregtech.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

public class BedrockDrillLoader {
	
	public static void init() {
		
		//MV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 100)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Rutile), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Ilmenite), 1200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Sylvanite), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Carbon), 5000)
				  .level(GTValues.V[GTValues.MV])
				  .modifier(0)
				  .buildAndRegister();
				  
		//HV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.RareEarth), 500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Diamond), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Emerald), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Rutile), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium), 2400)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .level(GTValues.V[GTValues.HV])
				  .modifier(20)
				  .buildAndRegister();
		
		//EV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth), 800)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Gold), 1500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Adamantium), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Cobaltite), 2000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Platinum), 4000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .level(GTValues.V[GTValues.EV])
				  .modifier(50)
				  .buildAndRegister();
		
		//IV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
		  		  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Lutetium), 200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Adamantium), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Tungstate), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .level(GTValues.V[GTValues.IV])
				  .modifier(80)
				  .buildAndRegister();
		
		//LuV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Lutetium), 1000)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth, 2), 3500)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustImpure, Materials.Iridium), 4000)
				  .level(GTValues.V[GTValues.LuV])
				  .modifier(120)
				  .buildAndRegister();
				  
	}

}
