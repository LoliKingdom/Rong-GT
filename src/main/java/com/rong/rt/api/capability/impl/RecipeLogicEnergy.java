package com.rong.rt.api.capability.impl;

import java.util.function.Supplier;

import com.rong.rt.api.capability.IEnergyContainer;
import com.rong.rt.api.metatileentity.MetaTileEntity;
import com.rong.rt.api.recipes.RecipeMap;

public class RecipeLogicEnergy extends AbstractRecipeLogic {

    private final Supplier<IEnergyContainer> energyContainer;

    public RecipeLogicEnergy(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, Supplier<IEnergyContainer> energyContainer) {
        super(tileEntity, recipeMap);
        this.energyContainer = energyContainer;
    }

    @Override
    protected long getEnergyStored() {
        return energyContainer.get().getCurrentEnergyStored();
    }

    @Override
    protected long getEnergyCapacity() {
        return energyContainer.get().getEnergyCapacity();
    }

    @Override
    protected boolean drawEnergy(int recipeEUt) {
        long resultEnergy = getEnergyStored() - recipeEUt;
        if (resultEnergy >= 0L && resultEnergy <= getEnergyCapacity()) {
            energyContainer.get().changeEnergy(-recipeEUt);
            return true;
        } else return false;
    }

    @Override
    protected long getMaxVoltage() {
        return Math.max(energyContainer.get().getInputVoltage(),
            energyContainer.get().getOutputVoltage());
    }

}