package com.rong.rt.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rong.rt.api.metaitems.MaterialMetaItem;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.OreDictUnifier;
import com.rong.rt.api.unification.materials.types.Material;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class MetaResources extends MaterialMetaItem {

	public final Map<EnumOrePrefix, EnumOrePrefix> purifyMap = new HashMap<>();

	public MetaResources() {
		super(EnumOrePrefix.dustTiny, EnumOrePrefix.dust, EnumOrePrefix.dustImpure, EnumOrePrefix.dustPure,
				EnumOrePrefix.crushed, EnumOrePrefix.crushedPurified, EnumOrePrefix.crushedCentrifuged,
				EnumOrePrefix.gem, EnumOrePrefix.nugget, EnumOrePrefix.ingot, EnumOrePrefix.ingotHot,
				EnumOrePrefix.plate, EnumOrePrefix.plateDense, EnumOrePrefix.stick, EnumOrePrefix.lens,
				EnumOrePrefix.screw, EnumOrePrefix.ring, EnumOrePrefix.foil, null, null, null, null, null, null, null,
				null, null, null, null, null, null);
		registerPurifyRecipes();
	}

	private void registerPurifyRecipes() {
		purifyMap.put(EnumOrePrefix.dustImpure, EnumOrePrefix.dust);
		purifyMap.put(EnumOrePrefix.dustDirty, EnumOrePrefix.dust);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem itemEntity) {
		int damage = itemEntity.getItem().getMetadata();
		if(damage >= this.metaItemOffset || itemEntity.getEntityWorld().isRemote) return false;
		Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
		EnumOrePrefix prefix = this.prefixes[(damage / 1000)];
		if(!purifyMap.containsKey(prefix)) return false;
		BlockPos blockPos = new BlockPos(itemEntity);
		IBlockState blockState = itemEntity.getEntityWorld().getBlockState(blockPos);
		int waterLevel = blockState.getBlock() instanceof BlockCauldron ? blockState.getValue(BlockCauldron.LEVEL) : 0;
		if(waterLevel == 0) return false;
		itemEntity.getEntityWorld().setBlockState(blockPos,
				blockState.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
		ItemStack replacementStack = OreDictUnifier.get(purifyMap.get(prefix), material,
				itemEntity.getItem().getCount());
		itemEntity.setItem(replacementStack);
		return false;
	}

	@Override
	protected void addMaterialTooltip(ItemStack itemStack, EnumOrePrefix prefix, Material material, List<String> lines,
			ITooltipFlag tooltipFlag) {
		if(prefix == EnumOrePrefix.dustImpure || prefix == EnumOrePrefix.dustPure) {
			lines.add(I18n.format("metaitem.dust.tooltip.purify"));
		}
	}

}
