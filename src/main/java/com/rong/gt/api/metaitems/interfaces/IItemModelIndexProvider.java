package com.rong.gt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemModelIndexProvider extends IMetaItemStats {

    int getModelIndex(ItemStack itemStack);
}
