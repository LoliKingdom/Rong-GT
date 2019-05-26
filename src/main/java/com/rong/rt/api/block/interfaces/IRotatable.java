package com.rong.rt.api.block.interfaces;

import java.util.Collections;
import java.util.List;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

interface IRotatable extends IWrenchable {

	default void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING_ALL, placer.getHorizontalFacing().getOpposite()));
	}

	@Override
	default EnumFacing getFacing(World world, BlockPos pos) {
		return world.getBlockState(pos).getValue(FACING_ALL);
	}

	@Override
	default boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
		world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING_ALL, newDirection), 2);
		return true;
	}

	@Override
	default boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
		return true;
	}

	@Override
	default List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te,
			EntityPlayer player, int fortune) {
		return Collections.singletonList(new ItemStack(state.getBlock(), 1, state.getBlock().damageDropped(state)));
	}

	PropertyDirection FACING_ALL = BlockDirectional.FACING;
}