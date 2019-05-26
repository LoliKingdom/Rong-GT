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
		initializeChemicalRecipes();
	}

	private static void initializeBendingRecipes() {
	}

	private static void initializeChemicalRecipes() {
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().input(EnumOrePrefix.block, Materials.Iron)
				.fluidInputs(Materials.Air.getFluid(1000)).outputs(MetaItems.BOARD_ADVANCED.getStackForm(2))
				.buildAndRegister();
	}

}
