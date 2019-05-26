package com.rong.rt.api.block;

import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

class SlotRT extends SlotItemHandler {

	private static final Predicate<ItemStack> DEFAULT_VALIDATOR = stack -> true;

	static final Predicate<ItemStack> OUTPUT = stack -> false;

	private final Predicate<ItemStack> validator;

	SlotRT(IItemHandler inv, int index, int x, int y) {
		this(inv, index, x, y, DEFAULT_VALIDATOR);
	}

	SlotRT(IItemHandler inv, int index, int x, int y, Predicate<ItemStack> validator) {
		super(inv, index, x, y);
		this.validator = Objects.requireNonNull(validator);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		return validator.test(stack);
	}
}