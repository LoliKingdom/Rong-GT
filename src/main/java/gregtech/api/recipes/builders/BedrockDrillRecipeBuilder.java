package gregtech.api.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.ImmutableMap;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;

public class BedrockDrillRecipeBuilder extends RecipeBuilder<BedrockDrillRecipeBuilder> {
	
	private long level;
	private int modifier;

	public BedrockDrillRecipeBuilder() {
		
    }

    public BedrockDrillRecipeBuilder(Recipe recipe, RecipeMap<BedrockDrillRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
        this.level = recipe.getLongProperty("level");
        this.modifier = recipe.getIntegerProperty("modifier");
    }

    public BedrockDrillRecipeBuilder(RecipeBuilder<BedrockDrillRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public BedrockDrillRecipeBuilder copy() {
        return new BedrockDrillRecipeBuilder(this);
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
    
    @Override
    public void buildAndRegister() {
    	if(fluidInputs.isEmpty()) {
    		recipeMap.addRecipe(this.copy()
    				 .fluidInputs(Materials.DrillingFluid.getFluid(50 * GTUtility.getTierByVoltage(this.EUt)))
    				 //.outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Bedrock, 2))
    				 .duration(2200 - (GTUtility.getTierByVoltage(this.EUt) * 10))
    				 .EUt(this.EUt)
    				 .build());
    	}
    }
}