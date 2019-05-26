package com.rong.rt.api.tiles;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileHandling {

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

}
