package com.rong.gt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IItemCapabilityProvider extends IMetaItemStats {

    ICapabilityProvider createProvider(ItemStack itemStack);

}
