package gregtech.api.recipes.builders;

import com.google.common.collect.ImmutableMap;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.util.ValidationResult;

public class RecyclerRecipeBuilder extends RecipeBuilder<RecyclerRecipeBuilder> {

    public RecyclerRecipeBuilder() {
    }

    public RecyclerRecipeBuilder(Recipe recipe, RecipeMap<RecyclerRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public RecyclerRecipeBuilder(RecipeBuilder<RecyclerRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public RecyclerRecipeBuilder copy() {
        return new RecyclerRecipeBuilder(this);
    }

    @Override
    public void buildAndRegister() {
        if (fluidInputs.isEmpty()) {
            fluidInputs(Materials.Oxygen.getFluid(this.duration));
            int plasmaAmount = (int) Math.max(1L, this.duration / (Materials.Nitrogen.getAverageMass() * 16L));
            RecyclerRecipeBuilder builder = RecipeMaps.RECYCLING_RECIPES.recipeBuilder()
            		.inputsIngredients(this.inputs)
                    .outputs(this.outputs)
                    .duration(Math.max(1, this.duration / 16))
                    .EUt(this.EUt / 3)
                    .fluidInputs(Materials.Nitrogen.getPlasma(plasmaAmount))
                    .fluidOutputs(Materials.Nitrogen.getFluid(plasmaAmount));
             builder.buildAndRegister();
        }
        super.buildAndRegister();
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, needsEmptyOutput));
    }
}