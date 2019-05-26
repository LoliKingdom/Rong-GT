package com.rong.rt.common.renders;

import com.rong.rt.common.entities.DynamiteEntity;
import com.rong.rt.common.items.MetaItems;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DynamiteRenderer extends RenderSnowball<DynamiteEntity> {

	public DynamiteRenderer(RenderManager renderManagerIn, RenderItem itemRendererIn) {
		super(renderManagerIn, MetaItems.DYNAMITE.getMetaItem(), itemRendererIn);
	}

	public ItemStack getStackToRender(DynamiteEntity entityIn) {
		return MetaItems.DYNAMITE.getStackForm();
	}
}
