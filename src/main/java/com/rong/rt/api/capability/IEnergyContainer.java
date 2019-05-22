package com.rong.rt.api.capability;

import net.minecraft.util.EnumFacing;

public interface IEnergyContainer {

    long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage);

    boolean inputsEnergy(EnumFacing side);

    default boolean outputsEnergy(EnumFacing side) {
        return false;
    }

    long changeEnergy(long differenceAmount);

    default long addEnergy(long energyToAdd) {
        return changeEnergy(energyToAdd);
    }

    default long removeEnergy(long energyToRemove) {
        return changeEnergy(-energyToRemove);
    }

    default long getEnergyCanBeInserted() {
        return getEnergyCapacity() - getCurrentEnergyStored();
    }

    /**
     * Gets the stored electric energy
     */
    long getCurrentEnergyStored();

    /**
     * Gets the largest electric energy capacity
     */
    long getEnergyCapacity();

    /**
     * Gets the amount of energy packets per tick.
     */
    default long getOutputAmperage() {
        return 0L;
    }

    /**
     * Gets the output in energy units per energy packet.
     */
    default long getOutputVoltage() {
        return 0L;
    }

    /**
     * Gets the amount of energy packets this machine can receive
     */
    long getInputAmperage();

    /**
     * Gets the maximum voltage this machine can receive in one energy packet.
     * Overflowing this value will explode machine.
     */
    long getInputVoltage();

    default boolean isOneProbeHidden() {
        return false;
    }
}