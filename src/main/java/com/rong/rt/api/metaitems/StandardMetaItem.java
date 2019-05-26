package com.rong.rt.api.metaitems;

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
