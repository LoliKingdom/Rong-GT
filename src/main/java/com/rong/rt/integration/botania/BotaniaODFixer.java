package com.rong.rt.integration.botania;

import com.rong.rt.api.unification.OreDictUnifier;

import net.minecraftforge.oredict.OreDictionary;

public class BotaniaODFixer {
	
	public static void init() {
		OreDictUnifier.registerOre(OreDictionary.getOres("livingwood").get(0), "blockLivingwood");
		OreDictUnifier.registerOre(OreDictionary.getOres("livingrock").get(0), "blockLivingrock");
		OreDictUnifier.registerOre(OreDictionary.getOres("manaPearl").get(0), "gemManaPearl");
		OreDictUnifier.registerOre(OreDictionary.getOres("manaDiamond").get(0), "blockLivingwood");
		OreDictUnifier.registerOre(OreDictionary.getOres("gaiaIngot").get(0), "gemManaDiamond");
	}
}
