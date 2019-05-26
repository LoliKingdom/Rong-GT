package com.rong.rt.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RTItemBlock extends ItemBlock {

	public RTItemBlock(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return this.block.getLocalizedName();
	}

}
