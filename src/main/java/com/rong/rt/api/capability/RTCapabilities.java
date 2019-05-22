package com.rong.rt.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class RTCapabilities {

    @CapabilityInject(IWorkable.class)
    public static Capability<IWorkable> CAPABILITY_WORKABLE = null;
    
    @CapabilityInject(IControllable.class)
    public static Capability<IControllable> CAPABILITY_CONTROLLABLE = null;

    @CapabilityInject(IEnergyContainer.class)
    public static Capability<IEnergyContainer> CAPABILITY_ENERGY_CONTAINER = null;
    
    @CapabilityInject(IElectricItem.class)
    public static Capability<IElectricItem> CAPABILITY_ELECTRIC_ITEM = null;
    
    @CapabilityInject(IWrenchItem.class)
    public static Capability<IWrenchItem> CAPABILITY_WRENCH = null;


}