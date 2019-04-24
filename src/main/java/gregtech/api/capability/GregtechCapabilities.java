package gregtech.api.capability;

import gregtech.api.capability.tool.IScrewdriverItem;
import gregtech.api.capability.tool.ISoftHammerItem;
import gregtech.api.capability.tool.IWrenchItem;
import gregtech.api.cover.ICoverable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class GregtechCapabilities {

    @CapabilityInject(IWorkable.class)
    public static Capability<IWorkable> CAPABILITY_WORKABLE = null;

    @CapabilityInject(IEnergyContainer.class)
    public static Capability<IEnergyContainer> CAPABILITY_ENERGY_CONTAINER = null;

    @CapabilityInject(IElectricItem.class)
    public static Capability<IElectricItem> CAPABILITY_ELECTRIC_ITEM = null;

    @CapabilityInject(ICoverable.class)
    public static Capability<ICoverable> CAPABILITY_COVERABLE = null;
    
    @CapabilityInject(IWrenchItem.class)
    public static Capability<IWrenchItem> CAPABILITY_WRENCH = null;

    @CapabilityInject(IScrewdriverItem.class)
    public static Capability<IScrewdriverItem> CAPABILITY_SCREWDRIVER = null;

    @CapabilityInject(ISoftHammerItem.class)
    public static Capability<ISoftHammerItem> CAPABILITY_MALLET = null;
    
    @CapabilityInject(IControllable.class)
    public static Capability<IControllable> CAPABILITY_CONTROLLABLE = null;
}