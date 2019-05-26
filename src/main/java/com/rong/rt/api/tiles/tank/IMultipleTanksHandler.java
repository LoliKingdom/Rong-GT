package com.rong.rt.api.tiles.tank;

import java.util.List;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IMultipleTanksHandler extends IFluidHandler, Iterable<IFluidTank> {

	List<IFluidTank> getFluidTanks();

	int getTanks();

	IFluidTank getTankAt(int index);
}