package com.rong.rt.api.tiles;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public abstract class TileEnergy extends TileBase implements IEnergyTile {

	private boolean isInEnergyNet;

	@Override
	public void invalidate() {
		if(!getWorld().isRemote && isInEnergyNet) {
			isInEnergyNet = false;
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		super.invalidate();
	}

	@Override
	public void onLoad() {
		if(!getWorld().isRemote && !isInEnergyNet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			isInEnergyNet = true;
		}
	}

}
