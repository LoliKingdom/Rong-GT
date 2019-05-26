package com.rong.rt.common;

import com.rong.rt.api.tiles.IHasGUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity aTile = world.getTileEntity(new BlockPos(x, y, z));
		if(aTile instanceof IHasGUI) {
			return ((IHasGUI) aTile).getGuiContainer(world, player);
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity aTile = world.getTileEntity(new BlockPos(x, y, z));
		if(aTile instanceof IHasGUI) {
			return ((IHasGUI) aTile).getGui(world, player);
		}
		else {
			return null;
		}
	}

}
