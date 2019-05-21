package com.rong.rt.common.blocks;

import com.rong.rt.Values;
import com.rong.rt.api.RongTechAPI;
import com.rong.rt.common.blocks.tiles.UIFactories;
import com.rong.rt.common.blocks.tiles.electric.TileEntityAutoclave;
import com.rong.rt.common.blocks.tiles.electric.TileEntityBender;

import ic2.core.block.base.tile.TileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachineBase extends Block implements ITileEntityProvider {

	public static String name;

	public BlockMachineBase(String name) {
		super(Material.IRON);
		this.name = name;
		setRegistryName(this.name.toLowerCase());
		setUnlocalizedName(Values.MOD_ID + "." + this.name.toLowerCase());
		setCreativeTab(RongTechAPI.TAB_RT_MAIN);
		// setBlockUnbreakable();
		setHardness(6.0F);
		setResistance(6.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile == null) return false;
		if(world.isRemote) return false;
		if(player.isSneaking()) return false;
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		if(tile instanceof TileEntityAutoclave) {
			TileEntityAutoclave autoclave = (TileEntityAutoclave) tile;
			UIFactories.UIAutoclave.INSTANCE.openUI(autoclave, playerMP);
			return true;
		}
		if(tile instanceof TileEntityBender) {
			TileEntityBender bender = (TileEntityBender) tile;
			UIFactories.UIBender.INSTANCE.openUI(bender, playerMP);
			return true;
		}
		// super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY,
		// hitZ);
		return false;
	}

	@Override
	@Deprecated
	public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
		return false;
	}

	@Override
	@Deprecated
	public boolean canProvidePower(IBlockState state) {
		int meta = this.getMetaFromState(state);
		return meta >= 0 && meta <= 2 ? true : super.canProvidePower(state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this == RTBlocks.blockBender) {
			return new TileEntityBender();
		}
		else if(this == RTBlocks.blockAutoclave) {
			return new TileEntityAutoclave();
		}
		return new TileEntityBlock();
	}

}
