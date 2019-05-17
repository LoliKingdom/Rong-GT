package com.rong.gt.api.block;

import java.util.Arrays;
import java.util.List;

import com.rong.gt.Values;
import com.rong.gt.api.RongTechAPI;

import ic2.core.block.base.tile.TileEntityBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBaseMachine extends BlockMultiIDBase {

	String name;
	String texture;
	int size = 0;

	public BlockBaseMachine(String name) {
		super(Material.IRON);
		this.name = name;
		this.size = 1;
		setRegistryName(this.name.toLowerCase());
		setUnlocalizedName(Values.MOD_ID + "." + this.name.toLowerCase());
		setCreativeTab(RongTechAPI.TAB_RT_MAIN);
		setBlockUnbreakable();
		setResistance(20.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
	}

	public BlockBaseMachine(String name, int additionalInfo) {
		super(Material.IRON);
		this.name = name;
		this.size = additionalInfo + 1;
		setRegistryName(this.name.toLowerCase());
		setUnlocalizedName(Values.MOD_ID + "." + this.name.toLowerCase());
		setCreativeTab(RongTechAPI.TAB_RT_MAIN);
		setBlockUnbreakable();
		setResistance(20.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		for (int i = 0; i < this.size; i++) {
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
	public List<Integer> getValidMetas() {
		return Arrays.asList(0);
	}

	@Override
	public List<IBlockState> getValidStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TileEntityBlock createNewTileEntity(World arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextureAtlasSprite[] getIconSheet(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBlockState> getValidStateList() {
		// TODO Auto-generated method stub
		return null;
	}

}
