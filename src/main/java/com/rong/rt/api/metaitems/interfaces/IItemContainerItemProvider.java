package com.rong.rt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemContainerItemProvider extends IMetaItemStats {

    ItemStack getContainerItem(ItemStack itemStack);
}