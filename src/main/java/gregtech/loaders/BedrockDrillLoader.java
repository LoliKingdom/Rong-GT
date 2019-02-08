package gregtech.loaders;

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
				  
				  .level(GTValues.V[GTValues.MV])
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
				  .buildAndRegister();
		
		//EV
		RecipeMaps.BEDROCK_DRILL_RECIPES.recipeBuilder()
				  .chancedOutput((OreDictUnifier.get(OrePrefix.dustImpure, Materials.Lutetium)), 200)
				  .chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bedrock), 5000)
				  .level(GTValues.V[GTValues.EV])
				  .buildAndRegister();
		
	}

}
