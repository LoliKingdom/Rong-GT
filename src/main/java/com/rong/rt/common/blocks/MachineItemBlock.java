package com.rong.rt.common.blocks;

import ic2.core.item.block.ItemBlockRare;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MachineItemBlock extends ItemBlockRare {

	public MachineItemBlock(Block block) {
		super(block);
	}

	@Override
	public int getMetadata(int damage) {
		return 0;
	}

	@Override
	public int getMetadata(ItemStack stack) {
		return 0;
	}

}
