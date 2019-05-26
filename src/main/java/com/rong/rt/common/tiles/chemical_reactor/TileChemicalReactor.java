package com.rong.rt.common.tiles.chemical_reactor;

import javax.annotation.Nullable;

import com.rong.rt.api.block.ContainerTileRT;
import com.rong.rt.api.gui.GuiTileRT;
import com.rong.rt.api.recipes.Recipe;
import com.rong.rt.api.tiles.IHasGUI;
import com.rong.rt.api.tiles.TileBase;
import com.rong.rt.api.tiles.TileEnergySinkWithRecipe;
import com.rong.rt.api.tiles.TileHandling;
import com.rong.rt.api.tiles.tank.FluidTankList;
import com.rong.rt.api.tiles.tank.RongFluidTank;
import com.rong.rt.api.utils.Utility;
import com.rong.rt.client.gui.GUIChemicalReactor;
import com.rong.rt.common.recipes.RecipeMaps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileChemicalReactor extends TileEnergySinkWithRecipe implements IHasGUI {

	private final ItemStackHandler input = new ItemStackHandler(3);
	private final ItemStackHandler output = new ItemStackHandler(2);
	// private final ItemStackHandler fluidInput = new ItemStackHandler(3);
	// private final ItemStackHandler fluidOutput = new ItemStackHandler(2);
	// private final MultiTypeStorage input = new MultiTypeStorage(6);
	// private final MultiTypeStorage output = new MultiTypeStorage(4);
	private final RongFluidTank inputFluidTank1 = new RongFluidTank(10000, "inputFluidTank1");
	private final RongFluidTank inputFluidTank2 = new RongFluidTank(10000, "inputFluidTank2");
	private final RongFluidTank inputFluidTank3 = new RongFluidTank(10000, "inputFluidTank3");
	private final RongFluidTank outputFluidTank1 = new RongFluidTank(10000, "outputFluidTank1");
	private final RongFluidTank outputFluidTank2 = new RongFluidTank(10000, "outputFluidTank2");
	private final FluidTankList inputFluidTanks = new FluidTankList(false, inputFluidTank1, inputFluidTank2,
			inputFluidTank3);
	private final FluidTankList outputFluidTanks = new FluidTankList(false, outputFluidTank1, outputFluidTank2);

	protected TileChemicalReactor() {
		super(1, 50000, RecipeMaps.CHEMICAL_RECIPES);
	}

	@Override
	public void update() {
		if(!getWorld().isRemote) {
			if(!working) {
				if(progress > 0) {
					updateRecipeProgress();
					// world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos),
					// this.world.getBlockState(this.pos), 1 | 2);
				}
				if(progress == 0) {
					trySearchNewRecipe(input, inputFluidTanks);
					world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos),
							this.world.getBlockState(this.pos), 1 | 2);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		// working = tag.getBoolean("working");
		// progress = tag.getInteger("progress");
		// progressMax = tag.getInteger("progressMax");
		input.deserializeNBT(tag.getCompoundTag("input"));
		output.deserializeNBT(tag.getCompoundTag("output"));
		inputFluidTank1.readFromNBT(tag);
		inputFluidTank2.readFromNBT(tag);
		inputFluidTank3.readFromNBT(tag);
		outputFluidTank1.readFromNBT(tag);
		outputFluidTank2.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		// tag.setBoolean("working", this.working);
		// tag.setInteger("progress", this.progress);
		// tag.setInteger("progressMax", this.progressMax);
		tag.setTag("input", this.input.serializeNBT());
		tag.setTag("output", this.output.serializeNBT());
		inputFluidTank1.writeToNBT(tag);
		inputFluidTank2.writeToNBT(tag);
		inputFluidTank3.writeToNBT(tag);
		outputFluidTank1.writeToNBT(tag);
		outputFluidTank2.writeToNBT(tag);
		return super.writeToNBT(tag);
	}

	@Override
	public ContainerTileRT getGuiContainer(World world, EntityPlayer player) {
		return ContainerTileRT.Builder.from(this).withStandardSlot(input, 0, 40, 22).withStandardSlot(input, 1, 60, 22)
				.withStandardSlot(input, 2, 80, 22).withOutputSlot(output, 0, 40, 52).withOutputSlot(output, 1, 60, 52)
				.withOutputSlot(output, 2, 80, 52).withPlayerInventory(player.inventory).build();
	}

	@Override
	public GuiTileRT<? extends TileBase> getGui(World world, EntityPlayer player) {
		return new GUIChemicalReactor(getGuiContainer(world, player), this);
	}

	@Override
	protected void setupRecipe(Recipe recipe) {
		int[] resultOverclock = calculateOverclock(recipe.getEnergyPerTick(), getMaxVoltage(), recipe.getDuration());
		this.progress = 1;
		this.setMaxProgress(resultOverclock[1]);
		this.recipeEnergyPerTick = resultOverclock[0];
		this.fluidOutputs = Utility.copyFluidList(recipe.getFluidOutputs());
		int byproductChanceMultiplier = getByproductChanceMultiplier(recipe);
		this.itemOutputs = Utility.copyStackList(recipe.getResultItemOutputs(random, byproductChanceMultiplier));
		if(this.wasActiveAndNeedsUpdate) {
			this.wasActiveAndNeedsUpdate = false;
		}
		else {
			this.setWorking(true);
		}
	}

	@Override
	protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
		int[] resultOverclock = calculateOverclock(recipe.getEnergyPerTick(), getMaxVoltage(), recipe.getDuration());
		int totalEUt = resultOverclock[0] * resultOverclock[1];
		return (totalEUt >= 0 ? charge >= (totalEUt > maxCharge / 2 ? resultOverclock[0] : totalEUt)
				: (charge - resultOverclock[0] <= maxCharge)) && TileHandling.isItemHandlerEmpty(input)
				&& TileHandling.addItemsToItemHandler(output, true, recipe.getOutputs())
				&& TileHandling.addFluidsToFluidHandler(outputFluidTanks, true, recipe.getFluidOutputs())
				&& recipe.matches(true, Utility.itemHandlerToList(input), Utility.fluidHandlerToList(inputFluidTanks));
	}

	@Override
	protected void completeRecipe() {
		TileHandling.addItemsToItemHandler(output, false, itemOutputs);
		TileHandling.addFluidsToFluidHandler(outputFluidTanks, false, fluidOutputs);
		this.progress = 0;
		setMaxProgress(0);
		this.recipeEnergyPerTick = 0;
		this.fluidOutputs = null;
		this.itemOutputs = null;
		this.hasNotEnoughEnergy = false;
		this.wasActiveAndNeedsUpdate = true;
		// force recipe recheck because inputs could have changed since last time
		// we checked them before starting our recipe, especially if recipe took long
		// time
		this.forceRecipeRecheck = true;
		this.syncToTrackingClients();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null) {
			switch(facing) {
				case UP:
				case NORTH:
				case EAST:
					return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(input);
				case DOWN:
				case SOUTH:
				case WEST:
					return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(output);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void onBlockDestroyed(World world, BlockPos pos, IBlockState state) {
		Utility.dropInventoryItems(world, pos, input, output);
	}

}
