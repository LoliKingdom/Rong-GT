package com.rong.rt.api.metaitems;

import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.gui.PlayerInventoryHolder;

import net.minecraft.entity.player.EntityPlayer;

public class StandardMetaItem extends MetaItem<MetaItem<?>.MetaValueItem> {

    public StandardMetaItem(short metaItemOffset) {
        super(metaItemOffset);
    }

    @Override
    protected MetaValueItem constructMetaValueItem(short metaValue, String unlocalizedName) {
        return new MetaValueItem(metaValue, unlocalizedName);
    }
}
