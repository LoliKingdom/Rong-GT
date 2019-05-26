package com.rong.rt.api.recipes;

import com.google.common.collect.ImmutableMap;
import com.rong.rt.api.utils.ValidationResult;

public class SimpleRecipeBuilder extends RecipeBuilder<SimpleRecipeBuilder> {

	public SimpleRecipeBuilder() {
	}

	public SimpleRecipeBuilder(Recipe recipe, RecipeMap<SimpleRecipeBuilder> recipeMap) {
		super(recipe, recipeMap);
	}

	public SimpleRecipeBuilder(RecipeBuilder<SimpleRecipeBuilder> recipeBuilder) {
		super(recipeBuilder);
	}

	@Override
	public SimpleRecipeBuilder copy() {
		return new SimpleRecipeBuilder(this);
	}

	public ValidationResult<Recipe> build() {
		return ValidationResult.newResult(finalizeAndValidate(),
				new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs, duration, energyPerTick));
	}
}