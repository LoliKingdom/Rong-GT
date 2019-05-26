package com.rong.rt.common.entities;

import com.rong.rt.RongTech;
import com.rong.rt.Values;
import com.rong.rt.common.renders.DynamiteRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RTEntities {
	
	public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(Values.MOD_ID, "dynamite"), DynamiteEntity.class, "Dynamite", 1, RongTech.instance, 64, 3, true);
    }

    @SideOnly(Side.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(DynamiteEntity.class, manager -> new DynamiteRenderer(manager, Minecraft.getMinecraft().getRenderItem()));
    }

}
