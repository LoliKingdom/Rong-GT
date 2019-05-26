package com.rong.rt.api.recipes.input;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public class RecipeInputItemStack implements IRecipeInput {

	private final ItemStack stack;

	public RecipeInputItemStack(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean matches(IRecipeInput input) {
		return input.matches(stack);
	}

	@Override
	public boolean matches(Object actualInput) {
		return actualInput instanceof ItemStack && stack.isItemEqual((ItemStack) actualInput);
	}

	@Nonnull
	@Override
	public <T> List<T> getActualInputs(Class<T> type) {
		return type == ItemStack.class ? Collections.singletonList(type.cast(stack)) : Collections.emptyList();
	}

	@Override
	public int getSize() {
		return stack.getCount();
	}

	@Nullable
	@Override
	public <I> I accepts(Class<I> type, I actualInput) {
		if(type == ItemStack.class) {
			ItemStack casted = (ItemStack) actualInput;
			if(casted.isEmpty()) {
				return type.cast(ItemStack.EMPTY);
			}
			else {
				casted.shrink(stack.getCount());
				return actualInput;
			}
		}
		return actualInput; // If type mismatch, don't touch it.
	}

	@Override
	public boolean equals(Object o) {
		return o == this || o instanceof RecipeInputItemStack && ((RecipeInputItemStack) o).stack.equals(this.stack);
	}

}
