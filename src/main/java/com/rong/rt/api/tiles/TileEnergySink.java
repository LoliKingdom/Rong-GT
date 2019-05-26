package com.rong.rt.api.tiles;

import javax.annotation.Nonnull;

import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEnergySink extends TileEnergy implements IEnergySink {

	public int charge;
	public int maxCharge;
	private final int sinkTier;

	protected TileEnergySink(int sinkTier, int maxEnergy) {
		this.sinkTier = sinkTier;
		this.maxCharge = maxEnergy;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.charge = tag.getInteger("charge");
		this.maxCharge = tag.getInteger("maxCharge");
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setInteger("charge", this.charge);
		tag.setInteger("maxCharge", maxCharge);
		return super.writeToNBT(tag);
	}

	@Override
	public void readPacketData(NBTTagCompound data) {
		super.readPacketData(data);
		this.charge = data.getInteger("charge");
		this.maxCharge = data.getInteger("maxCharge");
	}

	@Override
	public NBTTagCompound writePacketData(NBTTagCompound data) {
		data.setInteger("charge", this.charge);
		data.setInteger("maxCharge", this.maxCharge);
		return super.writePacketData(data);
	}

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
		return true;
	}

	@Override
	public double getDemandedEnergy() {
		return maxCharge - charge;
	}

	@Override
	public int getSinkTier() {
		return sinkTier;
	}

	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		this.charge += amount;
		if(charge >= maxCharge) charge = maxCharge;
		return 0;
	}

}