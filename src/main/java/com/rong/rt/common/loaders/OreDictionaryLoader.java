package com.rong.rt.common.loaders;

import static com.rong.rt.Values.M;
import static com.rong.rt.Values.W;

import com.rong.rt.RTLog;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.OreDictUnifier;
import com.rong.rt.api.unification.materials.MarkerMaterials.Color;
import com.rong.rt.api.unification.materials.Materials;
import com.rong.rt.api.unification.materials.types.Material;
import com.rong.rt.api.unification.stack.ItemMaterialInfo;
import com.rong.rt.api.unification.stack.MaterialStack;
import com.rong.rt.api.unification.stack.UnificationEntry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {

	public static void init() {
		RTLog.logger.info("Registering OreDict entries.");

		for(Material material : new Material[] { Materials.Lapis, Materials.Lazurite, Materials.Sodalite }) {
			OreDictUnifier.registerOre(OreDictUnifier.get(EnumOrePrefix.gem, material), EnumOrePrefix.dye, Color.Blue);
			OreDictUnifier.registerOre(OreDictUnifier.get(EnumOrePrefix.dust, material), EnumOrePrefix.dye, Color.Blue);
		}

		OreDictUnifier.registerOre(new ItemStack(Items.CLAY_BALL, 1), EnumOrePrefix.ingot, Materials.Clay);
		OreDictUnifier.registerOre(new ItemStack(Items.FLINT, 1), EnumOrePrefix.gem, Materials.Flint);
		OreDictUnifier.registerOre(new ItemStack(Blocks.HARDENED_CLAY, 1, W),
				new ItemMaterialInfo(new MaterialStack(Materials.Clay, M * 4)));
		OreDictUnifier.registerOre(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, W),
				new ItemMaterialInfo(new MaterialStack(Materials.Clay, M * 4)));

		OreDictionary.registerOre("fuelCoke", OreDictUnifier.get(EnumOrePrefix.gem, Materials.Coke));
		OreDictionary.registerOre("blockFuelCoke", OreDictUnifier.get(EnumOrePrefix.block, Materials.Coke));
		OreDictionary.registerOre("blockCoke", OreDictUnifier.get(EnumOrePrefix.block, Materials.Coke));

		OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_ORE), EnumOrePrefix.ore, Materials.Coal);
		OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_ORE), EnumOrePrefix.ore, Materials.Iron);
		OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_ORE), EnumOrePrefix.ore, Materials.Lapis);
		OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_ORE), EnumOrePrefix.ore, Materials.Redstone);
		OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_ORE), EnumOrePrefix.ore, Materials.Gold);
		OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_ORE), EnumOrePrefix.ore, Materials.Diamond);
		OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_ORE), EnumOrePrefix.ore, Materials.Emerald);
		OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_ORE), EnumOrePrefix.ore, Materials.NetherQuartz);
		OreDictUnifier.registerOre(new ItemStack(Items.DYE, 1, 4), EnumOrePrefix.gem, Materials.Lapis);
		OreDictUnifier.registerOre(new ItemStack(Items.ENDER_EYE), EnumOrePrefix.gem, Materials.EnderEye);
		OreDictUnifier.registerOre(new ItemStack(Items.ENDER_PEARL), EnumOrePrefix.gem, Materials.EnderPearl);
		OreDictUnifier.registerOre(new ItemStack(Items.DIAMOND), EnumOrePrefix.gem, Materials.Diamond);
		OreDictUnifier.registerOre(new ItemStack(Items.EMERALD), EnumOrePrefix.gem, Materials.Emerald);
		OreDictUnifier.registerOre(new ItemStack(Items.COAL), EnumOrePrefix.gem, Materials.Coal);
		OreDictUnifier.registerOre(new ItemStack(Items.COAL, 1, 1), EnumOrePrefix.gem, Materials.Charcoal);
		OreDictUnifier.registerOre(new ItemStack(Items.QUARTZ), EnumOrePrefix.gem, Materials.NetherQuartz);
		OreDictUnifier.registerOre(new ItemStack(Items.NETHER_STAR), EnumOrePrefix.gem, Materials.NetherStar);
		OreDictUnifier.registerOre(new ItemStack(Items.GOLD_NUGGET), EnumOrePrefix.nugget, Materials.Gold);
		OreDictUnifier.registerOre(new ItemStack(Items.GOLD_INGOT), EnumOrePrefix.ingot, Materials.Gold);
		OreDictUnifier.registerOre(new ItemStack(Items.IRON_INGOT), EnumOrePrefix.ingot, Materials.Iron);
		OreDictUnifier.registerOre(new ItemStack(Items.PAPER), EnumOrePrefix.plate, Materials.Paper);
		OreDictUnifier.registerOre(new ItemStack(Items.SUGAR), EnumOrePrefix.dust, Materials.Sugar);
		OreDictUnifier.registerOre(new ItemStack(Items.STICK), EnumOrePrefix.stick, Materials.Wood);
		OreDictUnifier.registerOre(new ItemStack(Items.REDSTONE), EnumOrePrefix.dust, Materials.Redstone);
		OreDictUnifier.registerOre(new ItemStack(Items.GUNPOWDER), EnumOrePrefix.dust, Materials.Gunpowder);
		OreDictUnifier.registerOre(new ItemStack(Items.GLOWSTONE_DUST), EnumOrePrefix.dust, Materials.Glowstone);
		OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_POWDER), EnumOrePrefix.dust, Materials.Blaze);
		OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_ROD), EnumOrePrefix.stick, Materials.Blaze);
		OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_BLOCK), EnumOrePrefix.block, Materials.Iron);
		OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_BLOCK), EnumOrePrefix.block, Materials.Gold);
		OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_BLOCK), EnumOrePrefix.block, Materials.Diamond);
		OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_BLOCK), EnumOrePrefix.block, Materials.Emerald);
		OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_BLOCK), EnumOrePrefix.block, Materials.Lapis);
		OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_BLOCK), EnumOrePrefix.block, Materials.Coal);
		OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_BLOCK), EnumOrePrefix.block, Materials.Redstone);
		OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_BLOCK), EnumOrePrefix.block, Materials.NetherQuartz);
		OreDictUnifier.registerOre(new ItemStack(Blocks.BONE_BLOCK), EnumOrePrefix.block, Materials.Bone);
		OreDictUnifier.registerOre(new ItemStack(Blocks.ICE), EnumOrePrefix.block, Materials.Ice);
		OreDictUnifier.registerOre(new ItemStack(Blocks.OBSIDIAN), EnumOrePrefix.block, Materials.Obsidian);
		OreDictUnifier.registerOre(new ItemStack(Blocks.GLASS), EnumOrePrefix.block, Materials.Glass);

		// OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 5),
		// EnumOrePrefix.stone, Materials.Andesite);
		// OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 6),
		// EnumOrePrefix.stone, Materials.Andesite);
		// OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 3),
		// EnumOrePrefix.stone, Materials.Diorite);
		// OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 4),
		// EnumOrePrefix.stone, Materials.Diorite);

		OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL), "craftingAnvil");
		OreDictUnifier.registerOre(new ItemStack(Blocks.OBSIDIAN, 1, W), EnumOrePrefix.stone, Materials.Obsidian);
		OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneMossy");
		OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneCobble");
		OreDictUnifier.registerOre(new ItemStack(Blocks.COBBLESTONE, 1, W), "stoneCobble");
		OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, W), "stoneSmooth");
		OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, W), "stoneBricks");
		OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 1), "stoneMossy");
		OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 2), "stoneCracked");
		OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 3), "stoneChiseled");
		OreDictUnifier.registerOre(new ItemStack(Blocks.NETHERRACK, 1, W), EnumOrePrefix.stone, Materials.Netherrack);
		OreDictUnifier.registerOre(new ItemStack(Blocks.END_STONE, 1, W), EnumOrePrefix.stone, Materials.Endstone);

		OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_TORCH, 1, W), "craftingRedstoneTorch");

		OreDictUnifier.registerOre(new ItemStack(Blocks.PISTON, 1, W), "craftingPiston");
		OreDictUnifier.registerOre(new ItemStack(Blocks.STICKY_PISTON, 1, W), "craftingPiston");

		OreDictUnifier.registerOre(new ItemStack(Blocks.CHEST, 1, W), "chestWood");
		OreDictUnifier.registerOre(new ItemStack(Blocks.TRAPPED_CHEST, 1, W), "chestWood");

		OreDictUnifier.registerOre(new ItemStack(Blocks.FURNACE, 1, W), "craftingFurnace");

		OreDictUnifier.registerOre(new ItemStack(Items.FEATHER, 1, W), "craftingFeather");

		OreDictUnifier.registerOre(new ItemStack(Items.WHEAT, 1, W), "itemWheat");
		OreDictUnifier.registerOre(new ItemStack(Items.PAPER, 1, W), "paperEmpty");
		OreDictUnifier.registerOre(new ItemStack(Items.MAP, 1, W), "paperMap");
		OreDictUnifier.registerOre(new ItemStack(Items.FILLED_MAP, 1, W), "paperMap");
		OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "bookEmpty");
		OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "bookWritable");
		OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "bookWritten");
		OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "bookEnchanted");
		OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "craftingBook");
		OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "craftingBook");
		OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "craftingBook");
		OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "craftingBook");

		for(ItemStack woodPlateStack : OreDictUnifier.getAll(new UnificationEntry(EnumOrePrefix.plate, Materials.Wood))) {
			OreDictUnifier.registerOre(woodPlateStack, EnumOrePrefix.plank, Materials.Wood);
		}
	}
}