package com.rong.rt.api.recipes.input;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class RecipeInputFluid implements IRecipeInput {

	private final FluidStack stack;

	public RecipeInputFluid(FluidStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean matches(IRecipeInput input) {
		return this.equals(input);
	}

	@Override
	public boolean matches(Object actualInput) {
		return actualInput instanceof FluidStack ? stack.isFluidEqual((FluidStack) actualInput)
				: actualInput instanceof ItemStack && stack.isFluidEqual((ItemStack) actualInput);
	}

	@Nonnull
	@Override
	public <T> List<T> getActualInputs(Class<T> type) {
		return type == FluidStack.class ? Collections.singletonList(type.cast(stack)) : Collections.emptyList();
	}

	@Override
	public int getSize() {
		return stack.amount;
	}

	@Nullable
	@Override
	public <I> I accepts(Class<I> type, I actualInput) {
		if(type == FluidStack.class) {
			FluidStack casted = (FluidStack) actualInput;
			casted.amount -= stack.amount;
			return casted.amount > 0 ? actualInput : null;
		}
		else if(type == ItemStack.class) {
			ItemStack casted = (ItemStack) actualInput;
			IFluidHandlerItem handler = FluidUtil.getFluidHandler(casted);
			if(handler != null) {
				handler.drain(stack.amount, true);
			}
			return actualInput;
		}
		else {
			return actualInput; // No-op when type mismatch
		}
	}

	@Override
	public boolean equals(Object o) {
		return o == this || o instanceof RecipeInputFluid && ((RecipeInputFluid)o).stack.isFluidEqual(this.stack);
	}
}
