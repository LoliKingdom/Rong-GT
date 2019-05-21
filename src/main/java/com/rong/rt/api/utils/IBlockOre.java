package com.rong.rt.api.utils;

import com.rong.rt.api.unification.stonetypes.StoneType;

import net.minecraft.block.state.IBlockState;

public interface IBlockOre {

    IBlockState getOreBlock(StoneType stoneType);

}
