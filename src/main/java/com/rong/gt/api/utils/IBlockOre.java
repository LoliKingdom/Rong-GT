package com.rong.gt.api.utils;

import com.rong.gt.api.unification.stonetypes.StoneType;

import net.minecraft.block.state.IBlockState;

public interface IBlockOre {

    IBlockState getOreBlock(StoneType stoneType);

}
