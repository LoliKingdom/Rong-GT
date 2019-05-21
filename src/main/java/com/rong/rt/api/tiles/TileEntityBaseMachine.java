package com.rong.rt.api.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.rong.rt.ConfigHolder;
import com.rong.rt.Values;
import com.rong.rt.api.gui.IUIHolder;
import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.recipes.Recipe;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.recipes.handlers.FilteredFluidHandler;
import com.rong.rt.api.recipes.handlers.FluidHandlerProxy;
import com.rong.rt.api.recipes.handlers.FluidTankList;
import com.rong.rt.api.recipes.handlers.IMultipleTankHandler;
import com.rong.rt.api.recipes.handlers.ItemHandlerProxy;
import com.rong.rt.api.render.OrientedOverlayRenderer;
import com.rong.rt.api.utils.Utility;

import ic2.api.classic.audio.PositionSpec;
import ic2.api.classic.network.adv.NetworkField;
import ic2.api.classic.tile.IInfoTile;
import ic2.api.classic.tile.INotifyMachine;
import ic2.api.classic.tile.machine.IEUStorage;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.Direction;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.block.base.tile.TileEntityBlock;
import ic2.core.block.base.util.info.EnergyInfo;
import ic2.core.block.base.util.info.EnergyUsageInfo;
import ic2.core.block.base.util.info.MaxInputInfo;
import ic2.core.block.base.util.info.NotifyInfo;
import ic2.core.block.base.util.info.SinkTierInfo;
import ic2.core.block.base.util.info.misc.IEnergyUser;
import ic2.core.inventory.base.IRotatingInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityBaseMachine extends TileEntityBlock implements IRotatingInventory, IEnergySink,
		IEUStorage, ITickable, IUIHolder, INetworkTileEntityEventListener, IEnergyUser {

	protected IItemHandlerModifiable importItems;
	protected IItemHandlerModifiable exportItems;

	protected IItemHandler itemInventory;

	protected FluidTankList importFluids;
	protected FluidTankList exportFluids;

	protected IFluidHandler fluidInventory;

	public final RecipeMap<?> recipeMap;

	protected boolean forceRecipeRecheck;
	protected ItemStack[] lastItemInputs;
	protected FluidStack[] lastFluidInputs;
	protected Recipe previousRecipe;

	protected int progressTime;
	protected int maxProgressTime;
	protected int recipeEUt;
	protected List<FluidStack> fluidOutputs;
	protected NonNullList<ItemStack> itemOutputs;
	protected final Random random = new Random();

	private boolean hasNotEnoughEnergy;
	private boolean wasActiveAndNeedsUpdate;

	protected final OrientedOverlayRenderer renderer;

	private boolean overclockable;

	public AudioSource audioSource;

	@NetworkField(index = 3, override = true)
	public int energy;
	@NetworkField(index = 4, override = true)
	public int maxEnergy;
	// @NetworkField(index = 5)
	// public int maxInput;
	@NetworkField(index = 6, compression = NetworkField.BitLevel.Bit8)
	public int tier;
	// public int baseTier;
	public boolean addedToEnergyNet;
	private Set<EnumFacing> notiIterator = EnumSet.noneOf(EnumFacing.class);
	private INotifyMachine[] machines = new INotifyMachine[6];
	@NetworkField(index = 8)
	public float soundLevel = 1.0F;
	@NetworkField(index = 11)
	public boolean redstoneInverted;
	@NetworkField(index = 12)
	public boolean redstoneSensitive;

	public TileEntityBaseMachine(int tier, RecipeMap<?> recipeMap, OrientedOverlayRenderer renderer) {
		super();
		this.energy = 0;
		this.maxEnergy = 0;
		this.tier = tier;
		this.renderer = renderer;
		this.recipeMap = recipeMap;
		// this.baseTier = this.tier;
		addNetworkFields(new String[] { "soundLevel", "redstoneInverted", "redstoneSensitive" });
		addGuiFields(new String[] { "energy", "maxEnergy", "tier" });
		if(supportsNotify()) {
			addInfos(new IInfoTile.InfoComponent[] { new NotifyInfo() });
		}
		addInfos(new IInfoTile.InfoComponent[] { new EnergyUsageInfo(this), new EnergyInfo(this),
				new SinkTierInfo(this), new MaxInputInfo(this) });
		initializeInventory();
	}

	@Override
	public void update() {
		if(!getWorld().isRemote) {
			if(energy <= 0) return;
			if(progressTime > 0) {
				getNetwork().initiateTileEntityEvent(this, 1, false);
				updateRecipeProgress();
			}
			if(progressTime == 0) {
				getNetwork().initiateTileEntityEvent(this, 2, false);
				notifyNeighbors();
				trySearchNewRecipe();
			}
			if(wasActiveAndNeedsUpdate) {
				notifyNeighbors();
				this.wasActiveAndNeedsUpdate = false;
				setActive(false);
			}
			updateComparators();
		}
	}

	private void updateRecipeProgress() {
		boolean drawEnergy = (energy - recipeEUt) < 0;
		if(drawEnergy || (recipeEUt < 0)) {
			if(++progressTime >= maxProgressTime) {
				completeRecipe();
			}
		}
		else if(recipeEUt > 0) {
			// only set hasNotEnoughEnergy if this recipe is consuming recipe
			// generators always have enough energy
			this.hasNotEnoughEnergy = true;
			// if current progress value is greater than 2, decrement it by 2
			if(progressTime >= 2) {
				if(ConfigHolder.insufficientEnergySupplyWipesRecipeProgress) {
					this.progressTime = 1;
				}
				else {
					this.progressTime = Math.max(1, progressTime - 2);
				}
			}
		}
	}

	protected void trySearchNewRecipe() {
		double maxVoltage = getMaxVoltage();
		Recipe currentRecipe = null;
		IItemHandlerModifiable importInventory = getImportInventory();
		IMultipleTankHandler importFluids = getImportFluidInventory();
		if(previousRecipe != null && previousRecipe.matches(false, importInventory, importFluids)) {
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

	private double getMaxVoltage() {
		return EnergyNet.instance.getPowerFromTier(tier);
	}

	public void forceRecipeRecheck() {
		this.forceRecipeRecheck = true;
	}

	protected int getMinTankCapacity(IMultipleTankHandler tanks) {
		if(tanks.getTanks() == 0) {
			return 0;
		}
		int result = Integer.MAX_VALUE;
		for(IFluidTank fluidTank : tanks.getFluidTanks()) {
			result = Math.min(fluidTank.getCapacity(), result);
		}
		return result;
	}

	protected Recipe findRecipe(double maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
		return recipeMap.findRecipe(maxVoltage, inputs, fluidInputs, getMinTankCapacity(getExportFluidInventory()));
	}

	protected boolean checkRecipeInputsDirty(IItemHandler inputs, IMultipleTankHandler fluidInputs) {
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

	protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
		int[] resultOverclock = calculateOverclock(recipe.getEUt(), getMaxVoltage(), recipe.getDuration());
		int totalEUt = resultOverclock[0] * resultOverclock[1];
		IItemHandlerModifiable importInventory = getImportInventory();
		IItemHandlerModifiable exportInventory = getExportInventory();
		IMultipleTankHandler importFluids = getImportFluidInventory();
		IMultipleTankHandler exportFluids = getExportFluidInventory();
		return (totalEUt >= 0 ? energy >= (totalEUt > maxEnergy / 2 ? resultOverclock[0] : totalEUt)
				: (energy - resultOverclock[0] <= maxEnergy))
				&& (!recipe.needsEmptyOutput() || isItemHandlerEmpty(exportInventory))
				&& addItemsToItemHandler(exportInventory, true, recipe.getOutputs())
				&& addFluidsToFluidHandler(exportFluids, true, recipe.getFluidOutputs())
				&& recipe.matches(true, importInventory, importFluids);
	}

	protected int[] calculateOverclock(int EUt, double d, int duration) {
		/*
		 * if(!allowOverclocking) { return new int[] { EUt, duration }; }
		 */
		boolean negativeEU = EUt < 0;
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
			// do not overclock further if duration is already too small
			while(resultDuration >= 3 && resultEUt <= Values.V[tier - 1]) {
				resultEUt *= 4;
				resultDuration /= 2.7;
			}
			return new int[] { negativeEU ? -resultEUt : resultEUt, (int) Math.ceil(resultDuration) };
		}
	}

	protected void setupRecipe(Recipe recipe) {
		int[] resultOverclock = calculateOverclock(recipe.getEUt(), getMaxVoltage(), recipe.getDuration());
		this.progressTime = 1;
		setMaxProgress(resultOverclock[1]);
		this.recipeEUt = resultOverclock[0];
		this.fluidOutputs = Utility.copyFluidList(recipe.getFluidOutputs());
		int byproductChanceMultiplier = getByproductChanceMultiplier(recipe);
		this.itemOutputs = Utility.copyStackList(recipe.getResultItemOutputs(random, byproductChanceMultiplier));
		if(this.wasActiveAndNeedsUpdate) {
			this.wasActiveAndNeedsUpdate = false;
		}
		else {
			setActive(true);
		}
	}

	protected int getByproductChanceMultiplier(Recipe recipe) {
		int byproductChanceMultiplier = 1;
		int recipeTier = EnergyNet.instance.getTierFromPower((recipe.getEUt()));
		if(tier > Values.LV && tier > recipeTier) {
			byproductChanceMultiplier = 1 << (tier - recipeTier);
		}
		return byproductChanceMultiplier;
	}

	protected void completeRecipe() {
		addItemsToItemHandler(getExportInventory(), false, itemOutputs);
		addFluidsToFluidHandler(getExportFluidInventory(), false, fluidOutputs);
		this.progressTime = 0;
		setMaxProgress(0);
		this.recipeEUt = 0;
		this.fluidOutputs = null;
		this.itemOutputs = null;
		this.hasNotEnoughEnergy = false;
		this.wasActiveAndNeedsUpdate = true;
		// force recipe recheck because inputs could have changed since last time
		// we checked them before starting our recipe, especially if recipe took long
		// time
		this.forceRecipeRecheck = true;
	}

	public double getProgressPercent() {
		return getMaxProgress() == 0 ? 0.0 : getProgress() / (getMaxProgress() * 1.0);
	}

	public int getTicksTimeLeft() {
		return maxProgressTime == 0 ? 0 : (maxProgressTime - progressTime);
	}

	public int getProgress() {
		return progressTime;
	}

	public int getMaxProgress() {
		return maxProgressTime;
	}

	public int getRecipeEUt() {
		return recipeEUt;
	}

	public void setMaxProgress(int maxProgress) {
		this.maxProgressTime = maxProgress;
		markDirty();
	}

	public boolean fillInternalTankFromFluidContainer(IItemHandlerModifiable importItems,
			IItemHandlerModifiable exportItems, int inputSlot, int outputSlot) {
		ItemStack inputContainerStack = importItems.extractItem(inputSlot, 1, true);
		FluidActionResult result = FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE,
				null, false);
		if(result.isSuccess()) {
			ItemStack remainingItem = result.getResult();
			if(ItemStack.areItemStacksEqual(inputContainerStack, remainingItem)) return false; // do not fill if item
																								// stacks match
			if(!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
				return false; // do not fill if can't put remaining item
			FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE, null, true);
			importItems.extractItem(inputSlot, 1, false);
			exportItems.insertItem(outputSlot, remainingItem, false);
			return true;
		}
		return false;
	}

	public boolean fillContainerFromInternalTank(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
			int inputSlot, int outputSlot) {
		ItemStack emptyContainer = importItems.extractItem(inputSlot, 1, true);
		FluidActionResult result = FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null,
				false);
		if(result.isSuccess()) {
			ItemStack remainingItem = result.getResult();
			if(!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
				return false;
			FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null, true);
			importItems.extractItem(inputSlot, 1, false);
			exportItems.insertItem(outputSlot, remainingItem, false);
			return true;
		}
		return false;
	}

	public static boolean isItemHandlerEmpty(IItemHandler handler) {
		for(int i = 0; i < handler.getSlots(); i++) {
			if(!handler.getStackInSlot(i).isEmpty()) return false;
		}
		return true;
	}

	public static boolean addItemsToItemHandler(IItemHandler handler, boolean simulate, NonNullList<ItemStack> items) {
		boolean insertedAll = true;
		for(ItemStack stack : items) {
			insertedAll &= ItemHandlerHelper.insertItemStacked(handler, stack, simulate).isEmpty();
			if(!insertedAll && simulate) return false;
		}
		return insertedAll;
	}

	public static boolean addFluidsToFluidHandler(IFluidHandler handler, boolean simulate, List<FluidStack> items) {
		boolean filledAll = true;
		for(FluidStack stack : items) {
			int filled = handler.fill(stack, !simulate);
			filledAll &= filled == stack.amount;
			if(!filledAll && simulate) return false;
		}
		return filledAll;
	}

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing facing) {
		return true;
	}

	public void notifyNeighbors() {
		Iterator<EnumFacing> iter = this.notiIterator.iterator();
		while(iter.hasNext()) {
			int index = ((EnumFacing) iter.next()).getIndex();
			INotifyMachine mach = this.machines[index];
			if(mach != null) {
				if(!mach.isValid()) {
					iter.remove();
					this.machines[index] = null;
				}
				else {
					mach.onNotify();
				}
			}
		}
	}

	private void updateNeighborChanges() {
		for(Direction dir : Direction.directions) {
			EnumFacing facing = dir.toFacing();
			TileEntity tile = dir.applyToTileEntity(this);
			if((tile instanceof INotifyMachine)) {
				this.notiIterator.add(facing);
				this.machines[facing.getIndex()] = ((INotifyMachine) tile);
			}
			else {
				this.notiIterator.remove(facing);
				this.machines[facing.getIndex()] = null;
			}
		}
	}

	public void updateNeighbors() {
		if((this.world.getTotalWorldTime() % 80L == 0L) && (supportsNotify())) {
			updateNeighborChanges();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.energy = nbt.getInteger("StoredEnergy");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("StoredEnergy", this.energy);
		return nbt;
	}

	@Override
	public void onLoaded() {
		super.onLoaded();
		if(isSimulating()) {
			getNetwork().updateTileEntityField(this, "redstoneInverted");
			getNetwork().updateTileEntityField(this, "redstoneSensitive");
			getNetwork().updateTileEntityField(this, "soundLevel");
			//getNetwork().updateTileGuiField(this, "maxInput");
			//getNetwork().updateTileGuiField(this, "energy");
			if(!this.addedToEnergyNet) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				this.addedToEnergyNet = true;
			}
			if(supportsNotify()) {
				updateNeighborChanges();
			}
		}
	}

	@Override
	public void onUnloaded() {
		if((isRendering()) && (this.audioSource != null)) {
			IC2.audioManager.removeSources(this);
			this.audioSource.remove();
			this.audioSource = null;
		}
		if(this.addedToEnergyNet && isSimulating()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			this.addedToEnergyNet = false;
		}
		super.onUnloaded();
	}

	@Override
	public void onNetworkEvent(int event) {
		if(this.audioSource != null && this.audioSource.isRemoved()) {
			this.audioSource = null;
		}
		if(this.audioSource == null && getStartSoundFile() != null) {
			this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getStartSoundFile(), true,
					false, IC2.audioManager.defaultVolume * this.soundLevel);
		}
		if(event == 0) {
			if(this.audioSource != null) {
				this.audioSource.play();
			}
		}
		else if(event == 1) {
			if(this.audioSource != null) {
				this.audioSource.stop();
				if(getInterruptSoundFile() != null) {
					IC2.audioManager.playOnce(this, PositionSpec.Center, getInterruptSoundFile(), false,
							IC2.audioManager.defaultVolume * this.soundLevel);
				}
			}
		}
		else if(event == 2) {
			if(this.audioSource != null) {
				this.audioSource.stop();
			}
		}
	}

	@Override
	public void onNetworkUpdate(String field) {
		if(field.equals("isActive")) {
			if(getActive()) {
				onNetworkEvent(0);
			}
		}
		super.onNetworkUpdate(field);
		if(field.equals("soundLevel")) {
			if(this.audioSource != null) {
				this.audioSource.setVolume(IC2.audioManager.defaultVolume * this.soundLevel);
			}
		}
	}

	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		if((amount > EnergyNet.instance.getPowerFromTier(tier)) || (amount <= 0.0D)) {
			return 0.0D;
		}
		this.energy = ((int) (this.energy + amount));
		int left = 0;
		if(this.energy >= this.maxEnergy) {
			left = this.energy - this.maxEnergy;
			this.energy = this.maxEnergy;
		}
		getNetwork().updateTileGuiField(this, "energy");
		return left;
	}

	@Override
	public int getEnergyUsage() {
		return getMaxProgress() * getRecipeEUt();
	}

	@Override
	public double getDemandedEnergy() {
		return maxEnergy - energy;
	}

	@Override
	public int getSinkTier() {
		return tier;
	}

	@Override
	public int getMaxEU() {
		return maxEnergy;
	}

	@Override
	public int getStoredEU() {
		return energy;
	}

	public boolean isHasNotEnoughEnergy() {
		return hasNotEnoughEnergy;
	}

	public boolean isOverclockable() {
		return this.overclockable;
	}

	public void setOverclockable(boolean overclockable) {
		this.overclockable = overclockable;
		markDirty();
	}

	@Override
	public boolean canRemoveBlock(EntityPlayer player) {
		return true;
	}

	@Override
	public double getWrenchDropRate() {
		return 1.0D;
	}

	public boolean supportsNotify() {
		return true;
	}

	@Override
	public boolean supportsRotation() {
		return true;
	}

	@Override
	public boolean canSetFacing(EntityPlayer player, EnumFacing facing) {
		return getFacing() != facing && facing.getAxis().isHorizontal();
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing side) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
				&& getFluidInventory().getTankProperties().length > 0) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidInventory());
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getItemInventory().getSlots() > 0) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemInventory());
		}
		return null;
	}

	protected void initializeInventory() {
		this.importItems = createImportItemHandler();
		this.exportItems = createExportItemHandler();
		this.itemInventory = new ItemHandlerProxy(importItems, exportItems);

		this.importFluids = createImportFluidHandler();
		this.exportFluids = createExportFluidHandler();
		this.fluidInventory = new FluidHandlerProxy(importFluids, exportFluids);
	}

	protected IItemHandlerModifiable createImportItemHandler() {
		return new ItemStackHandler(recipeMap.getMaxInputs());
	}

	protected IItemHandlerModifiable createExportItemHandler() {
		return new ItemStackHandler(recipeMap.getMaxOutputs());
	}

	protected FluidTankList createImportFluidHandler() {
		FilteredFluidHandler[] fluidImports = new FilteredFluidHandler[recipeMap.getMaxFluidInputs()];
		for(int i = 0; i < fluidImports.length; i++) {
			FilteredFluidHandler filteredFluidHandler = new FilteredFluidHandler(getInputTankCapacity(i));
			filteredFluidHandler.setFillPredicate(this::canInputFluid);
			fluidImports[i] = filteredFluidHandler;
		}
		return new FluidTankList(false, fluidImports);
	}

	protected FluidTankList createExportFluidHandler() {
		FluidTank[] fluidExports = new FluidTank[recipeMap.getMaxFluidOutputs()];
		for(int i = 0; i < fluidExports.length; i++) {
			fluidExports[i] = new FluidTank(getOutputTankCapacity(i));
		}
		return new FluidTankList(false, fluidExports);
	}

	protected boolean canInputFluid(FluidStack inputFluid) {
		if(recipeMap.canInputFluidForce(inputFluid.getFluid())) return true; // if recipe map forces input of given
																				// fluid, return true
		Set<Recipe> matchingRecipes = null;
		for(IFluidTank fluidTank : importFluids) {
			FluidStack fluidInTank = fluidTank.getFluid();
			if(fluidInTank != null) {
				if(matchingRecipes == null) {
					// if we didn't have a list of recipes with any fluids, obtain it from first
					// tank with fluid
					matchingRecipes = new HashSet<>(recipeMap.getRecipesForFluid(fluidInTank));
				}
				else {
					// else, remove recipes that don't contain fluid in this tank from list
					matchingRecipes.removeIf(recipe -> !recipe.hasInputFluid(fluidInTank));
				}
			}
		}
		if(matchingRecipes == null) {
			// if all tanks are empty, generally fluid can be inserted if there are recipes
			// for it
			return !recipeMap.getRecipesForFluid(inputFluid).isEmpty();
		}
		else {
			// otherwise, we can insert fluid only if one of recipes accept it as input
			return matchingRecipes.stream().anyMatch(recipe -> recipe.hasInputFluid(inputFluid));
		}
	}

	protected int getInputTankCapacity(int index) {
		return 64000;
	}

	protected int getOutputTankCapacity(int index) {
		return 64000;
	}

	public IItemHandler getItemInventory() {
		return itemInventory;
	}

	public IFluidHandler getFluidInventory() {
		return fluidInventory;
	}

	public IItemHandlerModifiable getImportInventory() {
		return importItems;
	}

	public IItemHandlerModifiable getExportInventory() {
		return exportItems;
	}

	public FluidTankList getImportFluidInventory() {
		return importFluids;
	}

	public FluidTankList getExportFluidInventory() {
		return exportFluids;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		if(progressTime > 0) {
			compound.setInteger("Progress", progressTime);
			compound.setInteger("MaxProgress", maxProgressTime);
			compound.setInteger("RecipeEUt", recipeEUt);
			NBTTagList itemOutputsList = new NBTTagList();
			for(ItemStack itemOutput : itemOutputs) {
				itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
			}
			NBTTagList fluidOutputsList = new NBTTagList();
			for(FluidStack fluidOutput : fluidOutputs) {
				fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
			}
			compound.setTag("ItemOutputs", itemOutputsList);
			compound.setTag("FluidOutputs", fluidOutputsList);
		}
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		this.progressTime = compound.getInteger("Progress");
		this.isActive = false;
		if(progressTime > 0) {
			this.isActive = true;
			this.maxProgressTime = compound.getInteger("MaxProgress");
			this.recipeEUt = compound.getInteger("RecipeEUt");
			NBTTagList itemOutputsList = compound.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
			this.itemOutputs = NonNullList.create();
			for(int i = 0; i < itemOutputsList.tagCount(); i++) {
				this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
			}
			NBTTagList fluidOutputsList = compound.getTagList("FluidOutputs", Constants.NBT.TAG_COMPOUND);
			this.fluidOutputs = new ArrayList<>();
			for(int i = 0; i < fluidOutputsList.tagCount(); i++) {
				this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
			}
		}
	}

	@Override
	public boolean isValid() {
		return !isInvalid();
	}

	@Override
	public boolean isRemote() {
		if(getWorld() != null && getWorld().isRemote) {
			return getWorld().isRemote;
		}
		else {
			return false;
		}
	}

	@Override
	public void markAsDirty() {
		markDirty();
	}

	public ResourceLocation getStartSoundFile() {
		return null;
	}

	public ResourceLocation getInterruptSoundFile() {
		return null;
	}

	public abstract ModularUI createUI(EntityPlayer entityPlayer);

}
