package com.rong.rt.common.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rong.rt.Values;
import com.rong.rt.api.RongTechAPI;
import com.rong.rt.common.blocks.tiles.UIFactories;
import com.rong.rt.common.blocks.tiles.electric.TileEntityAutoclave;
import com.rong.rt.common.blocks.tiles.electric.TileEntityBender;

import ic2.core.block.base.tile.TileEntityBlock;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.util.helpers.BlockStateContainerIC2;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachine extends ExtendedBlockMultiID {

	// TODO: Redstone integrations!
	// TODO: Right click for ModularUI!

	String name;
	String texture;
	int size = 0;

	public BlockMachine(String name) {
		super(Material.IRON);
		this.name = name;
		this.size = 1;
		setRegistryName(this.name.toLowerCase());
		setUnlocalizedName(Values.MOD_ID + "." + this.name.toLowerCase());
		setCreativeTab(RongTechAPI.TAB_RT_MAIN);
		//setBlockUnbreakable();
		setResistance(20.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile == null) return false;
		if(world.isRemote) return false;
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		for(int i = 0; i < this.size; i++) {
			tooltip.add(I18n.format(this.getUnlocalizedName().replace("tile", "tooltip") + i));
		}
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
	public TileEntityBlock createNewTileEntity(World worldIn, int meta) {
		if(this == RTBlocks.blockBender) {
			return new TileEntityBender();
		}
		else if(this == RTBlocks.blockAutoclave) {
			return new TileEntityAutoclave();
		}
		return new TileEntityBlock();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite[] getIconSheet(int meta) {
		return Ic2Icons.getTextures(this.name);
	}

	@Override
	public int getMaxSheetSize(int meta) {
		return 1;
	}

	@Override
	public List<IBlockState> getValidStateList() {
		IBlockState def = getDefaultState();
		List<IBlockState> states = new ArrayList<>();
		for(EnumFacing side : EnumFacing.VALUES) {
			states.add(def.withProperty(allFacings, side).withProperty(active, false));
			states.add(def.withProperty(allFacings, side).withProperty(active, true));
		}
		return states;
	}

	@Override
	public List<IBlockState> getValidStates() {
		return getBlockState().getValidStates();
	}

}
