package com.rong.rt.api.block.interfaces;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHorizontal extends IRotatable {

	@Override
	default void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING_HORIZONTAL, placer.getHorizontalFacing().getOpposite()));
	}

	@Override
	default EnumFacing getFacing(World world, BlockPos pos) {
		return world.getBlockState(pos).getValue(FACING_HORIZONTAL);
	}

	@Override
	default boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
		if(newDirection.getAxis() != EnumFacing.Axis.Y) { // Only UP and DOWN have EnumFacing.Axis.Y
			world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING_HORIZONTAL, newDirection), 2);
			return true;
		}
		else {
			return false;
		}
	}

	PropertyDirection FACING_HORIZONTAL = BlockHorizontal.FACING;

}
