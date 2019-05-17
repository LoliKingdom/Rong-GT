package com.rong.gt.api.tiles;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import com.rong.gt.api.gui.ModularUI;

import ic2.api.classic.network.adv.NetworkField;
import ic2.api.classic.tile.IInfoTile;
import ic2.api.classic.tile.INotifyMachine;
import ic2.api.classic.tile.machine.IEUStorage;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.Direction;
import ic2.core.block.base.util.info.EnergyInfo;
import ic2.core.block.base.util.info.MaxInputInfo;
import ic2.core.block.base.util.info.NotifyInfo;
import ic2.core.block.base.util.info.SinkTierInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;

public abstract class TileEntityMachine extends TileEntityEUBase implements IEUStorage, IEnergySink {

	@NetworkField(index = 3, override = true)
	public int energy;
	// private int fuelslot;
	@NetworkField(index = 4, override = true)
	public int maxEnergy;
	@NetworkField(index = 5)
	public int maxInputEnergy;
	@NetworkField(index = 6, compression = NetworkField.BitLevel.Bit8)
	public int tier;
	public int baseTier;

	public boolean addedToEnergyNet;

	private Set<EnumFacing> notiIterator = EnumSet.noneOf(EnumFacing.class);
	private INotifyMachine[] machines = new INotifyMachine[6];

	public TileEntityMachine(int maxInputEnergy) {
		super();
		this.energy = 0;
		// this.fuelslot = -1;
		this.maxEnergy = 0;
		this.tier = EnergyNet.instance.getTierFromPower(maxInputEnergy);
		this.baseTier = this.tier;
		this.maxInputEnergy = maxInputEnergy;
		if(supportsNotify()) {
			addInfos(new IInfoTile.InfoComponent[] { new NotifyInfo() });
		}
		addInfos(new IInfoTile.InfoComponent[] { new EnergyInfo(this), new SinkTierInfo(this), new MaxInputInfo(this) });
	}

	public boolean canInteractWith(EntityPlayer player) {
		return !isInvalid();
	}

	public abstract boolean supportsNotify();

	public void notifyNeighbors() {
		Iterator<EnumFacing> iter = this.notiIterator.iterator();
		while(iter.hasNext()) {
			int index = ((EnumFacing)iter.next()).getIndex();
			INotifyMachine mach = this.machines[index];
			if(mach != null) {
				if(!mach.isValid()) {
					iter.remove();
					this.machines[index] = null;
				} 
				else {
					mach.onNotify();
				}
			}
		}
	}

	private void updateNeighborChanges() {
		for(Direction direction : Direction.directions) {
			EnumFacing facing = direction.toFacing();
			TileEntity tile = direction.applyToTileEntity(this);
			if((tile instanceof INotifyMachine)) {
				this.notiIterator.add(facing);
				this.machines[facing.getIndex()] = ((INotifyMachine)tile);
			}
			else {
				this.notiIterator.remove(facing);
		        this.machines[facing.getIndex()] = null;
			}
		}
	}
	
	public void updateNeighbors() {
		if(this.getWorld().getTotalWorldTime() % 80L == 0L && supportsNotify()) {
			updateNeighborChanges();
		}
	}
	
	public void setMaxEnergy(int max) {
		this.maxEnergy = max;
	}
	
	public void setMaxInput(int inputEnergy) {
	    this.maxInputEnergy = inputEnergy;
	    this.tier = EnergyNet.instance.getTierFromPower(inputEnergy);
	}
	
	public void setTier(int newTier) {
	    this.tier = newTier;
	    this.maxInputEnergy = ((int)EnergyNet.instance.getPowerFromTier(newTier));
	}

	public boolean hasEnergy(int needed) {
		return this.energy >= needed;
	}
	
	public void useEnergy(int use) {
		this.energy -= use;
		if(this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.energy = nbt.getInteger("StoredEnergy");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("StoredEnergy", this.energy);
		return nbt;
	}
	
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing facing) {
		return true;
	}
	
	@Override
	public double getDemandedEnergy() {
		return this.maxEnergy - this.energy;
	}
	
	@Override
	public int getSinkTier() {
		return tier;
	}
	
	@Override
	public void onLoaded() {
		super.onLoaded();
		if(isSimulating()) {
	      if(!this.addedToEnergyNet) {
	    	  MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	    	  this.addedToEnergyNet = true;
	      }
	      if(supportsNotify()) {
	    	  updateNeighborChanges();
	      }
	    }
	}
	  
	public void onUnloaded() {
		if (this.addedToEnergyNet && isSimulating()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			this.addedToEnergyNet = false;
	    }
		super.onUnloaded();
	}
	
	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		if(amount > this.maxInputEnergy || amount <= 0.0D) {
			return 0.0D;
		}
		this.energy = (int)(this.energy + amount);
		int left = 0;
		if(this.energy >= this.maxEnergy) {
			left = this.energy - this.maxEnergy;
		}
		return left;
	}
	
	public int getTier() {
		return this.tier;
	}
	
	@Override
	public int getMaxEU() {
		return this.maxEnergy;
	}

	@Override
	public int getStoredEU() {
		return this.energy;
	}
	
	@Override
	public boolean canSetFacing(EntityPlayer player, EnumFacing facing) {
	    return getFacing() != facing;
	}
	
	@Override
	public double getWrenchDropRate() {
		return 0.85D;
	}
	
	@Override
	protected ModularUI createUI(EntityPlayer entityPlayer) {
		return null;
	}

}
