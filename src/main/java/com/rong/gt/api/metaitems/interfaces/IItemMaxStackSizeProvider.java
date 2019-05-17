package com.rong.gt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemMaxStackSizeProvider extends IMetaItemStats {

    int getMaxStackSize(ItemStack itemStack, int defaultValue);

}
