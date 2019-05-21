package com.rong.rt.common.loaders.recipes;

import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.Materials;
import com.rong.rt.common.items.MetaItems;
import com.rong.rt.common.recipes.RecipeMaps;

import ic2.core.platform.registry.Ic2Items;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MachineRecipeLoader {
	
	public static void init() {
		initializeBendingRecipes();
	}

	private static void initializeBendingRecipes() {
		RecipeMaps.BENDER_RECIPES.recipeBuilder().circuitMeta(1).input(EnumOrePrefix.plate, Materials.Iron, 12)
				.outputs(new ItemStack(Items.BUCKET, 4)).duration(800).EUt(4).buildAndRegister();

		RecipeMaps.BENDER_RECIPES.recipeBuilder().circuitMeta(1).input(EnumOrePrefix.plate, Materials.WroughtIron, 12)
				.outputs(new ItemStack(Items.BUCKET, 4)).duration(800).EUt(4).buildAndRegister();

		RecipeMaps.BENDER_RECIPES.recipeBuilder().input(EnumOrePrefix.plate, Materials.Tin, 2)
				.outputs(Ic2Items.emptyCell).duration(200).EUt(8).buildAndRegister();
	}

}
