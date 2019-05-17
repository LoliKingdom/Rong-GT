package com.rong.gt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemColorProvider extends IMetaItemStats {

    int getItemStackColor(ItemStack itemStack, int tintIndex);
}
