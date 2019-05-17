package com.rong.gt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemContainerItemProvider extends IMetaItemStats {

    ItemStack getContainerItem(ItemStack itemStack);
}