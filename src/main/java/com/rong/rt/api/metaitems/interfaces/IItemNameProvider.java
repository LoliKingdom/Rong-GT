package com.rong.rt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemNameProvider extends IMetaItemStats {

    String getItemStackDisplayName(ItemStack itemStack, String unlocalizedName);

}