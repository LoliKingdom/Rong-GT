package com.rong.rt.common.recipes;

import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.recipes.SimpleRecipeBuilder;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.rongtech.recipe.RecipeMaps")
@ZenRegister
public class RecipeMaps {
	
	@ZenProperty
	public static final RecipeMap<SimpleRecipeBuilder> CHEMICAL_RECIPES = new RecipeMap<>("chemical_reactor", 0, 3, 0, 2, 0, 3, 0, 2, new SimpleRecipeBuilder());

}
