package gregtech.integration.botania;

import gregtech.api.recipes.CountableIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraftforge.oredict.OreDictionary;

public class BotaniaODFixer {
	
	public static void init() {
		OreDictionary.registerOre("blockLivingwood", OreDictionary.getOres("livingwood").get(0));
		OreDictionary.registerOre("blockLivingrock", OreDictionary.getOres("livingrock").get(0));
		OreDictionary.registerOre("gemManaPearl", OreDictionary.getOres("manaPearl").get(0));
		OreDictionary.registerOre("gemManaDiamond", OreDictionary.getOres("manaDiamond").get(0));
		OreDictionary.registerOre("ingotGaia", OreDictionary.getOres("gaiaIngot").get(0));
	}
}
