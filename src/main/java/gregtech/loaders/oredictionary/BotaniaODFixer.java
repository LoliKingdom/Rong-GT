package gregtech.loaders.oredictionary;

import gregtech.api.recipes.CountableIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraftforge.oredict.OreDictionary;

public class BotaniaODFixer {
	
	public static void init() {
		OreDictUnifier.registerOre(OreDictionary.getOres("livingwood").get(0), OrePrefix.block, Materials.Livingwood);
		OreDictUnifier.registerOre(OreDictionary.getOres("livingrock").get(0), OrePrefix.block, Materials.Livingrock);
		OreDictUnifier.registerOre(OreDictionary.getOres("manaPearl").get(0), OrePrefix.gem, Materials.ManaPearl);
		OreDictUnifier.registerOre(OreDictionary.getOres("manaDiamond").get(0), OrePrefix.gem, Materials.ManaDiamond);
		OreDictUnifier.registerOre(OreDictionary.getOres("gaiaIngot").get(0), OrePrefix.ingot, Materials.Gaia);
	}
}
