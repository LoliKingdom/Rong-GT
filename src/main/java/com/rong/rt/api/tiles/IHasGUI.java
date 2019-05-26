package com.rong.rt.api.tiles;

import com.rong.rt.api.block.ContainerTileRT;
import com.rong.rt.api.gui.GuiTileRT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasGUI {

	ContainerTileRT getGuiContainer(World world, EntityPlayer player);

	@SideOnly(Side.CLIENT)
	GuiTileRT<? extends TileBase> getGui(World world, EntityPlayer player);

}
