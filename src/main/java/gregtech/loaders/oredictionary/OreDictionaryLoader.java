package gregtech.loaders.oredictionary;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {
	
	public static void init() {
		
		OreDictionary.registerOre("fuelCoke", OreDictUnifier.get(OrePrefix.gem, Materials.Coke));
        OreDictionary.registerOre("blockFuelCoke", OreDictUnifier.get(OrePrefix.block, Materials.Coke));

        OreDictUnifier.registerOre(new ItemStack(Blocks.CLAY), OrePrefix.block, Materials.Clay);
        OreDictUnifier.registerOre(new ItemStack(Blocks.BRICK_BLOCK), OrePrefix.block, Materials.Brick);
	}

}
