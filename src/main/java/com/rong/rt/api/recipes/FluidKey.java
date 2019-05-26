package com.rong.rt.api.recipes;

import java.util.Objects;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FluidKey {

	public final String fluid;
	public final NBTTagCompound tag;

	public FluidKey(FluidStack fluidStack) {
		this.fluid = fluidStack.getFluid().getName();
		this.tag = fluidStack.tag;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof FluidKey)) return false;
		FluidKey fluidKey = (FluidKey) o;
		return Objects.equals(fluid, fluidKey.fluid) && Objects.equals(tag, fluidKey.tag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fluid, tag);
	}

	@Override
	public String toString() {
		return "FluidKey{" + "fluid=" + fluid + ", tag=" + tag + '}';
	}

}
