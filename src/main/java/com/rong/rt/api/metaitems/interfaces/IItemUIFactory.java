package com.rong.rt.api.metaitems.interfaces;

import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.gui.PlayerInventoryHolder;

import net.minecraft.entity.player.EntityPlayer;

public interface IItemUIFactory {

    /**
     * Creates new UI basing on given holder. Holder contains information
     * about item stack and hand, and also player
     */
    ModularUI createUI(PlayerInventoryHolder holder, EntityPlayer entityPlayer);

}
