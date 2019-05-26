package com.rong.rt.api.block;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.rong.rt.RongTech;
import com.rong.rt.api.block.interfaces.IHorizontal;
import com.rong.rt.api.tiles.IHasWork;
import com.rong.rt.api.tiles.TileEnergySink;

import ic2.api.tile.IWrenchable;
import ic2.core.inventory.base.IHasGui;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachine extends Block implements IWrenchable, IHorizontal {

	private final Class<? extends TileEnergySink> type;

	static final PropertyBool WORKING = PropertyBool.create("working");

	public BlockMachine(Class<? extends TileEnergySink> clazz) {
		super(Material.IRON);
		this.type = clazz;
		this.setDefaultState(this.blockState.getBaseState().withProperty(WORKING, Boolean.FALSE));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, WORKING);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof IHasWork) {
			state = state.withProperty(WORKING, ((IHasWork) tile).isWorking());
		}
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(WORKING) ? 1 : 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(WORKING, meta == 0 ? Boolean.FALSE : Boolean.TRUE);
	}

	// Explicitly override this, forcing us not relying on metadata anymore
	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		try {
			return type.newInstance();
		} catch(Exception ignored) {
			return null;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEnergySink) {
			((TileEnergySink) tileEntity).onBlockDestroyed(worldIn, pos, state);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && IHasGui.class.isAssignableFrom(this.type)) {
			playerIn.openGui(RongTech.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public EnumFacing getFacing(World world, BlockPos blockPos) {
		return EnumFacing.NORTH;
	}

	@Override
	public boolean setFacing(World world, BlockPos blockPos, EnumFacing enumFacing, EntityPlayer entityPlayer) {
		return false;
	}

	@Override
	public boolean wrenchCanRemove(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public List<ItemStack> getWrenchDrops(World world, BlockPos blockPos, IBlockState iBlockState,
			TileEntity tileEntity, EntityPlayer entityPlayer, int i) {
		return Collections.emptyList();
	}
}
