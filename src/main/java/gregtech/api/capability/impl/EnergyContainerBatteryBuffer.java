package gregtech.api.capability.impl;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandlerModifiable;

public class EnergyContainerBatteryBuffer extends MTETrait implements IEnergyContainer {

    private final int tier;

    public EnergyContainerBatteryBuffer(MetaTileEntity metaTileEntity, int tier) {
        super(metaTileEntity);
        this.tier = tier;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long initialAmperage = amperage;
        if(side == null || inputsEnergy(side)) {
            if (voltage > getInputVoltage()) {
                GTUtility.doOvervoltageExplosion(metaTileEntity, voltage);
                return Math.min(amperage, getInputAmperage());
            }
            IItemHandlerModifiable inventory = getInventory();
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                if(chargeItemWithVoltageExact(electricItem, voltage, getTier(), true)) {
                	chargeItemWithVoltageExact(electricItem, voltage, getTier(), false);
                    inventory.setStackInSlot(i, batteryStack);
                    if(--amperage == 0) break;
                }
            }
        }
        return initialAmperage - amperage;
    }

    private static boolean chargeItemWithVoltageExact(IElectricItem electricItem, long voltage, int tier, boolean simulate) {
    	return electricItem.charge(voltage, tier, false, simulate) == voltage;
    }
    
    private static long chargeItem(IElectricItem electricItem, long amount, int tier, boolean discharge) {
        if (!discharge) {
            return electricItem.charge(amount, tier, false, false);
        } 
        else {
            return electricItem.discharge(-amount, tier, true, true, false);
        }
    }

    @Override
    public void update() {
        if(!metaTileEntity.getWorld().isRemote) {
            EnumFacing outFacing = metaTileEntity.getFrontFacing();
            TileEntity tileEntity = metaTileEntity.getWorld().getTileEntity(metaTileEntity.getPos().offset(outFacing));
            if (tileEntity == null) {
                return;
            }
            IEnergyContainer energyContainer = tileEntity.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, outFacing.getOpposite());
            if (energyContainer == null) {
                return;
            }
            IItemHandlerModifiable inventory = getInventory();
            long voltage = getOutputVoltage();
            long maxAmperage = 0L;
            TIntList slotsList = new TIntArrayList();
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                if(electricItem.discharge(voltage, getTier(), true, true, true) == voltage) {
                    slotsList.add(i);
                    maxAmperage++;
                }
            }
            if(maxAmperage == 0) return;
            long amperageUsed = energyContainer.acceptEnergyFromNetwork(outFacing.getOpposite(), voltage, maxAmperage);
            if(amperageUsed == 0) return;
            for (int i : slotsList.toArray()) {
                ItemStack batteryStack = inventory.getStackInSlot(i);
                IElectricItem electricItem = getBatteryContainer(batteryStack);
                if (electricItem == null) continue;
                electricItem.discharge(voltage, getTier(), true, true, false);
                inventory.setStackInSlot(i, batteryStack);
                if(--amperageUsed == 0) break;
            }
        }
    }

    @Override
    public long getEnergyCapacity() {
        long energyCapacity = 0L;
        IItemHandlerModifiable inventory = getInventory();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            energyCapacity += electricItem.getMaxCharge();
        }
        return energyCapacity;
    }

    @Override
    public long getCurrentEnergyStored() {
        long energyStored = 0L;
        IItemHandlerModifiable inventory = getInventory();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            energyStored += electricItem.getCharge();
        }
        return energyStored;
    }

    @Override
    public long getInputAmperage() {
        long inputAmperage = 0L;
        IItemHandlerModifiable inventory = getInventory();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            inputAmperage++;
        }
        return inputAmperage;
    }

    public IElectricItem getBatteryContainer(ItemStack itemStack) {
        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if(electricItem != null && getTier() >= electricItem.getTier() &&
                electricItem.canProvideChargeExternally())
                return electricItem;
            return null;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        boolean isDischarge = energyToAdd < 0L;
        energyToAdd = Math.abs(energyToAdd);
        long initialEnergyToAdd = energyToAdd;
        IItemHandlerModifiable inventory = getInventory();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack batteryStack = inventory.getStackInSlot(i);
            IElectricItem electricItem = getBatteryContainer(batteryStack);
            if (electricItem == null) continue;
            long charged = chargeItem(electricItem, energyToAdd, getTier(), isDischarge);
            energyToAdd -= charged;
            if(energyToAdd == 0L) break;
        }
        return initialEnergyToAdd - energyToAdd;
    }

    @Override
    public long getInputVoltage() {
        return GTValues.V[getTier()];
    }

    @Override
    public long getOutputVoltage() {
        return getInputVoltage();
    }

    @Override
    public long getOutputAmperage() {
        return getInputAmperage();
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return getMetaTileEntity().getFrontFacing() != side;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return !inputsEnergy(side);
    }

    @Override
    public String getName() {
        return "BatteryEnergyContainer";
    }

    @Override
    public int getNetworkID() {
        return TraitNetworkIds.TRAIT_ID_ENERGY_CONTAINER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if(capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
            return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER.cast(this);
        }
        return null;
    }

    protected IItemHandlerModifiable getInventory() {
        return metaTileEntity.getImportItems();
    }

    protected int getTier() {
        return tier;
    }
}
