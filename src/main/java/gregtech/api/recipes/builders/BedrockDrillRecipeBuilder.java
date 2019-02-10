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

    @Override
    public boolean applyProperty(String key, Object value) {
        if(key.equals("level")) {
            this.level(((Number)value).longValue());
            return true;
        }
        if(key.equals("modifier")) {
            this.modifier(((Number)value).intValue());
            return true;
        }
        return true;
    }

    public BedrockDrillRecipeBuilder level(long level) {
        if (level <= 0) {
            GTLog.logger.error("Level cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.level = level;
        return this;
    }
    
    public BedrockDrillRecipeBuilder modifier(int modifier) {
        if (modifier <= 0) {
            GTLog.logger.error("Modifier degree cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.modifier = modifier;
        return this;
    }

    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of("level", level, "modifier", modifier),
                duration, EUt, hidden, canBeBuffered, needsEmptyOutput));
    }
    
    @Override
    public void buildAndRegister() {
    	if(fluidInputs.isEmpty()) {
    		recipeMap.addRecipe(this.copy()
    				 .fluidInputs(Materials.DrillingFluid.getFluid(50))
    				 .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Bedrock, 2))
    				 .duration(320 - modifier)
    				 .EUt(((int)level - modifier))
    				 .build());
    	}
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("level", level)
            .append("modifier", modifier)
            .toString();
    }
}
