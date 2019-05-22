package com.rong.rt.api.recipes.machines;

import java.util.List;

import javax.annotation.Nullable;

import com.rong.rt.api.recipes.ModHandler;
import com.rong.rt.api.recipes.Recipe;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.recipes.builders.SimpleRecipeBuilder;
import com.rong.rt.api.utils.Utility;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeMapFurnace extends RecipeMap<SimpleRecipeBuilder> {

    public RecipeMapFurnace(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, SimpleRecipeBuilder defaultRecipe) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, defaultRecipe);
    }

    @Override
    @Nullable
    public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs, int outputFluidTankCapacity) {
        Recipe normalRecipe = super.findRecipe(voltage, inputs, fluidInputs, outputFluidTankCapacity);
        if (normalRecipe != null || inputs.size() == 0 || inputs.get(0).isEmpty())
            return normalRecipe;
		ItemStack output = ModHandler.getSmeltingOutput(inputs.get(0));
        return output.isEmpty() ? null : this.recipeBuilder()
            .inputs(Utility.copyAmount(1, inputs.get(0)))
            .outputs(output)
            .duration(128).EUt(4)
            .build().getResult();
    }
}
