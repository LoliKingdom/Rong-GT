package com.rong.rt.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import com.rong.rt.api.recipes.Recipe;
import com.rong.rt.api.recipes.RecipeBuilder;
import com.rong.rt.api.recipes.RecipeMap;
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
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, needsEmptyOutput));
    }
}
