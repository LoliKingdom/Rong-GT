package com.rong.rt.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.rong.rt.RTLog;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.types.Material;
import com.rong.rt.api.utils.Utility;
import com.rong.rt.api.utils.ValidationResult;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

public class RecipeMap<R extends RecipeBuilder<R>> {

	private static final List<RecipeMap<?>> RECIPE_MAPS = new ArrayList<>();

	public final String unlocalizedName;

	private final R recipeBuilderSample;
	private final int minInputs, maxInputs;
	private final int minOutputs, maxOutputs;
	private final int minFluidInputs, maxFluidInputs;
	private final int minFluidOutputs, maxFluidOutputs;

	private final Map<FluidKey, Collection<Recipe>> recipeFluidMap = new HashMap<>();
	private final Collection<Recipe> recipeList = new ArrayList<>();

	public RecipeMap(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs,
			int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, R defaultRecipe) {
		this.unlocalizedName = unlocalizedName;

		this.minInputs = minInputs;
		this.minFluidInputs = minFluidInputs;
		this.minOutputs = minOutputs;
		this.minFluidOutputs = minFluidOutputs;

		this.maxInputs = maxInputs;
		this.maxFluidInputs = maxFluidInputs;
		this.maxOutputs = maxOutputs;
		this.maxFluidOutputs = maxFluidOutputs;

		defaultRecipe.setRecipeMap(this);
		this.recipeBuilderSample = defaultRecipe;
		RECIPE_MAPS.add(this);
	}

	@ZenMethod
	public static List<RecipeMap<?>> getRecipeMaps() {
		return Collections.unmodifiableList(RECIPE_MAPS);
	}

	@ZenMethod
	public static RecipeMap<?> getByName(String unlocalizedName) {
		return RECIPE_MAPS.stream().filter(map -> map.unlocalizedName.equals(unlocalizedName)).findFirst().orElse(null);
	}

	public static boolean isFoundInvalidRecipe() {
		return foundInvalidRecipe;
	}

	public static void setFoundInvalidRecipe(boolean foundInvalidRecipe) {
		RecipeMap.foundInvalidRecipe |= foundInvalidRecipe;
		EnumOrePrefix currentOrePrefix = EnumOrePrefix.getCurrentProcessingPrefix();
		if(currentOrePrefix != null) {
			Material currentMaterial = EnumOrePrefix.getCurrentMaterial();
			RTLog.logger.error(
					"Error happened during processing ore registration of prefix {} and material {}. "
							+ "Seems like cross-mod compatibility issue. Report to RongTech's Github!",
					currentOrePrefix, currentMaterial);
		}
	}

	public boolean canInputFluidForce(Fluid fluid) {
		return false;
	}

	public Collection<Recipe> getRecipesForFluid(FluidStack fluid) {
		return recipeFluidMap.getOrDefault(new FluidKey(fluid), Collections.emptySet());
	}

	private static boolean foundInvalidRecipe = false;

	// internal usage only, use buildAndRegister()
	public void addRecipe(ValidationResult<Recipe> validationResult) {
		switch(validationResult.getType()) {
			case SKIP:
				return;
			case INVALID:
				setFoundInvalidRecipe(true);
				return;
		}
		Recipe recipe = validationResult.getResult();
		recipeList.add(recipe);

		for(FluidStack fluid : recipe.getFluidInputs()) {
			recipeFluidMap.computeIfAbsent(new FluidKey(fluid), k -> new HashSet<>(1)).add(recipe);
		}
	}

	public boolean removeRecipe(Recipe recipe) {
		// if we actually removed this recipe
		if(recipeList.remove(recipe)) {
			// also iterate trough fluid mappings and remove recipe from them
			recipeFluidMap.values().forEach(fluidMap -> fluidMap.removeIf(fluidRecipe -> fluidRecipe == recipe));
			return true;
		}
		return false;
	}

	/**
	 * Finds a Recipe matching the Fluid and ItemStack Inputs.
	 *
	 * @param voltage
	 *            Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param inputs
	 *            the Item Inputs
	 * @param fluidInputs
	 *            the Fluid Inputs
	 * @param outputFluidTankCapacity
	 *            minimal capacity of output fluid tank, used for fluid canner
	 *            recipes for example
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	@Nullable
	public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs,
			int outputFluidTankCapacity) {
		if(recipeList.isEmpty()) return null;
		if(minFluidInputs > 0 && Utility.amountOfNonNullElements(fluidInputs) < minFluidInputs) {
			return null;
		}
		if(minInputs > 0 && Utility.amountOfNonEmptyStacks(inputs) < minInputs) {
			return null;
		}
		if(maxInputs > 0) {
			return findByInputs(voltage, inputs, fluidInputs);
		}
		else {
			return findByFluidInputs(voltage, inputs, fluidInputs);
		}
	}

	@Nullable
	private Recipe findByFluidInputs(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
		for(FluidStack fluid : fluidInputs) {
			if(fluid == null) continue;
			Collection<Recipe> recipes = recipeFluidMap.get(new FluidKey(fluid));
			if(recipes == null) continue;
			for(Recipe tmpRecipe : recipes) {
				if(tmpRecipe.matches(false, inputs, fluidInputs)) {
					return voltage >= tmpRecipe.getEnergyPerTick() ? tmpRecipe : null;
				}
			}
		}
		return null;
	}

	@Nullable
	private Recipe findByInputs(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
		for(Recipe recipe : recipeList) {
			if(recipe.matches(false, inputs, fluidInputs)) {
				return voltage >= recipe.getEnergyPerTick() ? recipe : null;
			}
		}
		return null;
	}

	public Collection<Recipe> getRecipeList() {
		return Collections.unmodifiableCollection(recipeList);
	}
	/*
	 * @ZenMethod("findRecipe")
	 * 
	 * @Method(modid = "crafttweaker")
	 * 
	 * @Nullable public CTRecipe ctFindRecipe(long maxVoltage, IItemStack[]
	 * itemInputs, ILiquidStack[] fluidInputs,
	 * 
	 * @Optional(valueLong = Integer.MAX_VALUE) int outputFluidTankCapacity) {
	 * List<ItemStack> mcItemInputs = itemInputs == null ? Collections.emptyList() :
	 * Arrays.stream(itemInputs).map(CraftTweakerMC::getItemStack).collect(
	 * Collectors.toList()); List<FluidStack> mcFluidInputs = fluidInputs == null ?
	 * Collections.emptyList() :
	 * Arrays.stream(fluidInputs).map(CraftTweakerMC::getLiquidStack).collect(
	 * Collectors.toList()); Recipe backingRecipe = findRecipe(maxVoltage,
	 * mcItemInputs, mcFluidInputs, outputFluidTankCapacity); return backingRecipe
	 * == null ? null : new CTRecipe(this, backingRecipe); }
	 * 
	 * @ZenGetter("recipes")
	 * 
	 * @Method(modid = "crafttweaker") public List<CTRecipe> ccGetRecipeList() {
	 * return getRecipeList().stream().map(recipe -> new CTRecipe(this,
	 * recipe)).collect(Collectors.toList()); }
	 */

	@SideOnly(Side.CLIENT)
	@ZenGetter("localizedName")
	public String getLocalizedName() {
		return I18n.format("recipemap." + unlocalizedName + ".name");
	}

	@ZenGetter("unlocalizedName")
	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public R recipeBuilder() {
		return recipeBuilderSample.copy();
	}
	/*
	 * @ZenMethod("recipeBuilder")
	 * 
	 * @Method(modid = "crafttweaker") public CTRecipeBuilder ctRecipeBuilder() {
	 * return new CTRecipeBuilder(recipeBuilder()); }
	 */

	@ZenGetter("minInputs")
	public int getMinInputs() {
		return minInputs;
	}

	@ZenGetter("maxInputs")
	public int getMaxInputs() {
		return maxInputs;
	}

	@ZenGetter("minOutputs")
	public int getMinOutputs() {
		return minOutputs;
	}

	@ZenGetter("maxOutputs")
	public int getMaxOutputs() {
		return maxOutputs;
	}

	@ZenGetter("minFluidInputs")
	public int getMinFluidInputs() {
		return minFluidInputs;
	}

	@ZenGetter("maxFluidInputs")
	public int getMaxFluidInputs() {
		return maxFluidInputs;
	}

	@ZenGetter("minFluidOutputs")
	public int getMinFluidOutputs() {
		return minFluidOutputs;
	}

	@ZenGetter("maxFluidOutputs")
	public int getMaxFluidOutputs() {
		return maxFluidOutputs;
	}

	@Override
	@ZenMethod
	public String toString() {
		return "RecipeMap{" + "unlocalizedName='" + unlocalizedName + '\'' + '}';
	}
}
