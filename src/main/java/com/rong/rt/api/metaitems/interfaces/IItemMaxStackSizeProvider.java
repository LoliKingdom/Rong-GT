package com.rong.rt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemMaxStackSizeProvider extends IMetaItemStats {

    int getMaxStackSize(ItemStack itemStack, int defaultValue);

}
