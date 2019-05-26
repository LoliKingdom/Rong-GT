package com.rong.rt.api.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.rong.rt.ConfigHolder;
import com.rong.rt.Values;
import com.rong.rt.api.recipes.Recipe;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.tiles.tank.IMultipleTanksHandler;
import com.rong.rt.api.utils.Utility;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class TileEnergySinkWithRecipe extends TileEnergySink implements ITickable {

	public final RecipeMap<?> recipeMap;

	protected boolean forceRecipeRecheck;
	protected ItemStack[] lastItemInputs;
	protected FluidStack[] lastFluidInputs;
	protected Recipe previousRecipe;

	protected int recipeEnergyPerTick;
	protected List<FluidStack> fluidOutputs;
	protected NonNullList<ItemStack> itemOutputs;
	protected final Random random = new Random();

	protected int progress;
	protected int progressMax;
	protected boolean wasActiveAndNeedsUpdate = true;
	protected boolean working = false;
	protected boolean hasNotEnoughEnergy;
	private boolean allowOverclocking = true;

	public TileEnergySinkWithRecipe(int sinkTier, int maxEnergy, RecipeMap<?> recipeMap) {
		super(sinkTier, maxEnergy);
		this.recipeMap = recipeMap;
	}

	protected void updateRecipeProgress() {
		boolean drawEnergy = drawEnergy(recipeEnergyPerTick);
		if(drawEnergy || recipeEnergyPerTick < 0) {
			if(++progress >= progressMax) {
				completeRecipe();
			}
		}
		else if(recipeEnergyPerTick > 0) {
			this.hasNotEnoughEnergy = true;
			// if current progress value is greater than 2, decrement it by 2
			if(progress >= 2) {
				if(ConfigHolder.insufficientEnergySupplyWipesRecipeProgress) {
					this.progress = 1;
				}
				else {
					this.progress = Math.max(1, progress - 2);
				}
			}
		}
	}

	protected void trySearchNewRecipe(IItemHandlerModifiable importInventory, IMultipleTanksHandler importFluids) {
		long maxVoltage = getMaxVoltage();
		Recipe currentRecipe = null;
		if(previousRecipe != null && previousRecipe.matches(false, Utility.itemHandlerToList(importInventory),
				Utility.fluidHandlerToList(importFluids))) {
			// if previous recipe still matches inputs, try to use it
			currentRecipe = previousRecipe;
		}
		else {
			boolean dirty = checkRecipeInputsDirty(importInventory, importFluids);
			if(dirty || forceRecipeRecheck) {
				this.forceRecipeRecheck = false;
				// else, try searching new recipe for given inputs
				currentRecipe = findRecipe(maxVoltage, importInventory, importFluids);
				if(currentRecipe != null) {
					this.previousRecipe = currentRecipe;
				}
			}
		}
		if(currentRecipe != null && setupAndConsumeRecipeInputs(currentRecipe)) {
			setupRecipe(currentRecipe);
		}
	}

	protected abstract void setupRecipe(Recipe recipe);

	protected boolean checkRecipeInputsDirty(IItemHandler inputs, IMultipleTanksHandler fluidInputs) {
		boolean shouldRecheckRecipe = false;
		if(lastItemInputs == null || lastItemInputs.length != inputs.getSlots()) {
			this.lastItemInputs = new ItemStack[inputs.getSlots()];
			Arrays.fill(lastItemInputs, ItemStack.EMPTY);
		}
		if(lastFluidInputs == null || lastFluidInputs.length != fluidInputs.getTanks()) {
			this.lastFluidInputs = new FluidStack[fluidInputs.getTanks()];
		}
		for(int i = 0; i < lastItemInputs.length; i++) {
			ItemStack currentStack = inputs.getStackInSlot(i);
			ItemStack lastStack = lastItemInputs[i];
			if(!ItemStack.areItemsEqual(currentStack, lastStack)
					|| !ItemStack.areItemStackTagsEqual(currentStack, lastStack)) {
				this.lastItemInputs[i] = currentStack.isEmpty() ? ItemStack.EMPTY : currentStack.copy();
				shouldRecheckRecipe = true;
			}
			else if(currentStack.getCount() != lastStack.getCount()) {
				lastStack.setCount(currentStack.getCount());
				shouldRecheckRecipe = true;
			}
		}
		for(int i = 0; i < lastFluidInputs.length; i++) {
			FluidStack currentStack = fluidInputs.getTankAt(i).getFluid();
			FluidStack lastStack = lastFluidInputs[i];
			if((currentStack == null && lastStack != null)
					|| (currentStack != null && !currentStack.isFluidEqual(lastStack))) {
				this.lastFluidInputs[i] = currentStack == null ? null : currentStack.copy();
				shouldRecheckRecipe = true;
			}
			else if(currentStack != null && lastStack != null && currentStack.amount != lastStack.amount) {
				lastStack.amount = currentStack.amount;
				shouldRecheckRecipe = true;
			}
		}
		return shouldRecheckRecipe;
	}

	protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTanksHandler fluidInputs) {
		return findRecipe(maxVoltage, inputs, fluidInputs, null);
	}

	protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTanksHandler fluidInputs,
			IFluidTank outputTank) {
		return recipeMap.findRecipe(maxVoltage, Utility.itemHandlerToList(inputs),
				Utility.fluidHandlerToList(fluidInputs), outputTank.getCapacity());
	}

	public void forceRecipeRecheck() {
		this.forceRecipeRecheck = true;
	}

	protected abstract boolean setupAndConsumeRecipeInputs(Recipe recipe);

	protected int getByproductChanceMultiplier(Recipe recipe) {
		int byproductChanceMultiplier = 1;
		int tier = Utility.getTierByVoltage(getMaxVoltage());
		int recipeTier = Utility.getTierByVoltage(recipe.getEnergyPerTick());
		if(tier > Values.LV && tier > recipeTier) {
			byproductChanceMultiplier = 1 << (tier - recipeTier);
		}
		return byproductChanceMultiplier;
	}

	protected int[] calculateOverclock(int EUt, long voltage, int duration) {
		if(!allowOverclocking) {
			return new int[] { EUt, duration };
		}
		boolean negativeEU = EUt < 0;
		int tier = getOverclockingTier(voltage);
		if(Values.V[tier] <= EUt || tier == 0) return new int[] { EUt, duration };
		if(negativeEU) EUt = -EUt;
		if(EUt <= 16) {
			int multiplier = EUt <= 8 ? tier : tier - 1;
			int resultEUt = EUt * (1 << multiplier) * (1 << multiplier);
			int resultDuration = duration / (1 << multiplier);
			return new int[] { negativeEU ? -resultEUt : resultEUt, resultDuration };
		}
		else {
			int resultEUt = EUt;
			double resultDuration = duration;
			while(resultDuration >= 3 && resultEUt <= Values.V[tier - 1]) {
				resultEUt *= 4;
				resultDuration /= 2.7;
			}
			return new int[] { negativeEU ? -resultEUt : resultEUt, (int) Math.ceil(resultDuration) };
		}
	}

	protected int getOverclockingTier(long voltage) {
		return Utility.getTierByVoltage(voltage);
	}

	protected abstract void completeRecipe();

	protected long getMaxVoltage() {
		return Values.V[Utility.getTierByVoltage(recipeEnergyPerTick)];
	}

	private boolean drawEnergy(int recipeEnergyPerTick) {
		if((charge - recipeEnergyPerTick) >= 0) {
			this.charge -= recipeEnergyPerTick;
			return true;
		}
		else {
			return false;
		}
	}

	public double getProgressPercent() {
		return getMaxProgress() == 0 ? 0.0 : getProgress() / (getMaxProgress() * 1.0);
	}

	public int getTicksTimeLeft() {
		return progressMax == 0 ? 0 : (progressMax - progress);
	}

	public int getProgress() {
		return progress;
	}

	public int getMaxProgress() {
		return progressMax;
	}

	public int getRecipeEnergyPerTick() {
		return recipeEnergyPerTick;
	}

	public boolean allowOverclocking() {
		return allowOverclocking;
	}

	public void setMaxProgress(int maxProgress) {
		this.progressMax = maxProgress;
		markDirty();
	}

	public boolean isWorking() {
		return this.working;
	}

	public void setWorking(boolean workingEnabled) {
		this.working = workingEnabled;
		markDirty();
	}

	public void setAllowOverclocking(boolean allowOverclocking) {
		this.allowOverclocking = allowOverclocking;
		markDirty();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("working", working);
		compound.setBoolean("allowOverclocking", allowOverclocking);
		if(progress > 0) {
			compound.setInteger("progress", progress);
			compound.setInteger("progressMax", progressMax);
			compound.setInteger("recipeEnergyPerTick", recipeEnergyPerTick);
			NBTTagList itemOutputsList = new NBTTagList();
			for(ItemStack itemOutput : itemOutputs) {
				itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
			}
			NBTTagList fluidOutputsList = new NBTTagList();
			for(FluidStack fluidOutput : fluidOutputs) {
				fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
			}
			compound.setTag("itemOutputs", itemOutputsList);
			compound.setTag("fluidOutputs", fluidOutputsList);
		}
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		this.working = compound.getBoolean("working");
		this.progress = compound.getInteger("progress");
		if(compound.hasKey("allowOverclocking")) {
			this.allowOverclocking = compound.getBoolean("allowOverclocking");
		}
		this.working = false;
		if(progress > 0) {
			this.working = true;
			this.progressMax = compound.getInteger("progressMax");
			this.recipeEnergyPerTick = compound.getInteger("recipeEnergyPerTick");
			NBTTagList itemOutputsList = compound.getTagList("itemOutputs", Constants.NBT.TAG_COMPOUND);
			this.itemOutputs = NonNullList.create();
			for(int i = 0; i < itemOutputsList.tagCount(); i++) {
				this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
			}
			NBTTagList fluidOutputsList = compound.getTagList("fluidOutputs", Constants.NBT.TAG_COMPOUND);
			this.fluidOutputs = new ArrayList<>();
			for(int i = 0; i < fluidOutputsList.tagCount(); i++) {
				this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
			}
		}
	}

	@Override
	public void readPacketData(NBTTagCompound data) {
		super.readPacketData(data);
		this.progress = data.getInteger("process");
		this.progressMax = data.getInteger("processMax");
		this.working = data.getBoolean("working");
	}

	@Override
	public NBTTagCompound writePacketData(NBTTagCompound data) {
		data.setInteger("process", this.progress);
		data.setInteger("processMax", this.progressMax);
		data.setBoolean("working", this.working);
		return super.writePacketData(data);
	}

}
