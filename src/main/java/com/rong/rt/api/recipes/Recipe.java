package com.rong.rt.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.ImmutableList;
import com.rong.rt.api.recipes.input.CountableIngredient;
import com.rong.rt.api.utils.Utility;

import gnu.trove.impl.unmodifiable.TUnmodifiableObjectIntMap;
import gnu.trove.map.TObjectIntMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public class Recipe {

	private final List<CountableIngredient> inputs;
	private final NonNullList<ItemStack> outputs;

	/**
	 * A chance of 10000 equals 100%
	 */
	private final TObjectIntMap<ItemStack> chancedOutputs;

	private final List<FluidStack> fluidInputs;
	private final List<FluidStack> fluidOutputs;

	private final int duration;

	private final int energyPerTick;

	public Recipe(List<CountableIngredient> inputs, List<ItemStack> outputs, TObjectIntMap<ItemStack> chancedOutputs,
			List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int duration, int energyPerTick) {
		this.inputs = NonNullList.create();
		this.inputs.addAll(inputs);
		this.outputs = NonNullList.create();
		this.outputs.addAll(outputs);
		this.chancedOutputs = new TUnmodifiableObjectIntMap<>(chancedOutputs);
		this.fluidInputs = ImmutableList.copyOf(fluidInputs);
		this.fluidOutputs = ImmutableList.copyOf(fluidOutputs);
		this.duration = duration;
		this.energyPerTick = energyPerTick;
		this.inputs.sort(Comparator.comparing(CountableIngredient::getCount).reversed());
	}

	public boolean matches(boolean consumeIfSuccessful, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
		int[] fluidAmountInTank = new int[fluidInputs.size()];
		int[] itemAmountInSlot = new int[inputs.size()];

		for(int i = 0; i < fluidAmountInTank.length; i++) {
			FluidStack fluidInTank = fluidInputs.get(i);
			fluidAmountInTank[i] = fluidInTank == null ? 0 : fluidInTank.amount;
		}
		for(int i = 0; i < itemAmountInSlot.length; i++) {
			ItemStack itemInSlot = inputs.get(i);
			itemAmountInSlot[i] = itemInSlot.isEmpty() ? 0 : itemInSlot.getCount();
		}

		for(FluidStack fluid : this.fluidInputs) {
			int fluidAmount = fluid.amount;
			boolean isNotConsumed = false;
			if(fluidAmount == 0) {
				fluidAmount = 1;
				isNotConsumed = true;
			}
			for(int i = 0; i < fluidInputs.size(); i++) {
				FluidStack tankFluid = fluidInputs.get(i);
				if(tankFluid == null || !tankFluid.isFluidEqual(fluid)) continue;
				int fluidAmountToConsume = Math.min(fluidAmountInTank[i], fluidAmount);
				fluidAmount -= fluidAmountToConsume;
				if(!isNotConsumed) fluidAmountInTank[i] -= fluidAmountToConsume;
				if(fluidAmount == 0) break;
			}
			if(fluidAmount > 0) return false;
		}

		for(CountableIngredient ingredient : this.inputs) {
			int ingredientAmount = ingredient.getCount();
			boolean isNotConsumed = false;
			if(ingredientAmount == 0) {
				ingredientAmount = 1;
				isNotConsumed = true;
			}
			for(int i = 0; i < inputs.size(); i++) {
				ItemStack inputStack = inputs.get(i);
				if(inputStack.isEmpty() || !ingredient.getIngredient().apply(inputStack)) continue;
				int itemAmountToConsume = Math.min(itemAmountInSlot[i], ingredientAmount);
				ingredientAmount -= itemAmountToConsume;
				if(!isNotConsumed) itemAmountInSlot[i] -= itemAmountToConsume;
				if(ingredientAmount == 0) break;
			}
			if(ingredientAmount > 0) return false;
		}

		if(consumeIfSuccessful) {
			for(int i = 0; i < fluidAmountInTank.length; i++) {
				FluidStack fluidStack = fluidInputs.get(i);
				int fluidAmount = fluidAmountInTank[i];
				if(fluidStack == null || fluidStack.amount == fluidAmount) continue;
				fluidStack.amount = fluidAmount;
				if(fluidStack.amount == 0) fluidInputs.set(i, null);
			}
			for(int i = 0; i < itemAmountInSlot.length; i++) {
				ItemStack itemInSlot = inputs.get(i);
				int itemAmount = itemAmountInSlot[i];
				if(itemInSlot.isEmpty() || itemInSlot.getCount() == itemAmount) continue;
				itemInSlot.setCount(itemAmountInSlot[i]);
			}
		}

		return true;
	}

	public List<CountableIngredient> getInputs() {
		return inputs;
	}

	public NonNullList<ItemStack> getOutputs() {
		return outputs;
	}

	public List<ItemStack> getResultItemOutputs(Random random, int byproductChanceMultiplier) {
		ArrayList<ItemStack> outputs = new ArrayList<>(Utility.copyStackList(getOutputs()));
		TObjectIntMap<ItemStack> chancedOutputsMap = getChancedOutputs();
		for(ItemStack chancedOutput : chancedOutputsMap.keySet()) {
			int outputChance = chancedOutputsMap.get(chancedOutput) * byproductChanceMultiplier;
			//10000 = max
			if(random.nextInt(10000) <= outputChance) outputs.add(chancedOutput.copy());
		}
		return outputs;
	}

	public TObjectIntMap<ItemStack> getChancedOutputs() {
		return chancedOutputs;
	}

	public List<FluidStack> getFluidInputs() {
		return fluidInputs;
	}

	public boolean hasInputFluid(FluidStack fluid) {
		for(FluidStack fluidStack : fluidInputs) {
			if(fluidStack.isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public List<FluidStack> getFluidOutputs() {
		return fluidOutputs;
	}

	public int getDuration() {
		return duration;
	}

	public int getEnergyPerTick() {
		return energyPerTick;
	}

	public boolean hasValidInputsForDisplay() {
		boolean hasValidInputs = true;
		for(CountableIngredient ingredient : inputs) {
			ItemStack[] matchingItems = ingredient.getIngredient().getMatchingStacks();
			hasValidInputs &= Arrays.stream(matchingItems).anyMatch(s -> !s.isEmpty());
		}
		return hasValidInputs;
	}

}
