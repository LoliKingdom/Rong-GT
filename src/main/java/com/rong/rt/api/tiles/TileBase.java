package com.rong.rt.api.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class TileBase extends TileEntity {

	protected final void syncToTrackingClients() {
		if(!this.world.isRemote) {
			SPacketUpdateTileEntity packet = this.getUpdatePacket();
			PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap()
					.getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
			if(trackingEntry != null) {
				for(EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
					player.connection.sendPacket(packet);
				}
			}
		}
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, -1, this.writePacketData(new NBTTagCompound()));
	}

	@Override
	public final void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		this.readPacketData(packet.getNbtCompound());
	}

	protected void readPacketData(NBTTagCompound data) {
		if(this.world.isRemote) {
			this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		}
	}

	protected NBTTagCompound writePacketData(NBTTagCompound data) {
		return data;
	}

	/**
	 * Called when {@link Block#breakBlock(World, BlockPos, IBlockState)} is called
	 *
	 * @param worldIn
	 *            the World instance
	 * @param pos
	 *            the position
	 * @param state
	 *            the current BlockState, use this one instead of querying via
	 *            {@link TileEntity#getWorld()}
	 */
	public abstract void onBlockDestroyed(World worldIn, BlockPos pos, IBlockState state);
}
