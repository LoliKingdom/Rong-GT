package com.rong.rt.api.metaitems.interfaces;

import net.minecraft.item.ItemStack;

public interface IToolItem {
	
	/**
     * Tries to apply given amount of damage to item
     * If simulated, actual damage won't be applied and durability won't be changed
     * <p>
     * DO NOT USE METHODS BELOW TO CHECK IF TOOL CAN RECEIVE SPECIFIED AMOUNT OF DAMAGE,
     * Use this method with simulate = true to check so, because it's can be different for electric items, as example!
     */
    boolean damageItem(ItemStack stack, int damage, boolean simulate);

    /**
     * @return amount of internal damage this item have
     * Item is not forced to modify this value in #damageItem,
     * it's used only for visual needs
     */
    int getItemDamage(ItemStack stack);

    /**
     * @return amount of max internal damage this item can receive
     * Generally internal damage should never exceed this number
     */
    int getMaxItemDamage(ItemStack stack);

}
