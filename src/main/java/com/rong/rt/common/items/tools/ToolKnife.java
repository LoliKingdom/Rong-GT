package com.rong.rt.common.items.tools;

import com.rong.rt.api.metaitems.interfaces.IToolStats;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ToolKnife implements IToolStats {

	@Override
	public int getToolDamagePerBlockBreak(ItemStack stack) {
		return 5;
	}

	@Override
	public int getToolDamagePerContainerCraft(ItemStack stack) {
		return 10;
	}

	@Override
	public int getToolDamagePerEntityAttack(ItemStack stack) {
		return 1;
	}

	@Override
    public float getBaseDamage(ItemStack stack) {
        return 4.5F;
    }

	@Override
	public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
		return enchantment.type.canEnchantItem(Items.IRON_SWORD);
	}

	@Override
	public boolean canMineBlock(IBlockState block, ItemStack stack) {
		return false;
	}
}