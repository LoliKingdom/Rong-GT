package com.rong.rt.api.gui.widgets;

import com.rong.rt.api.capability.IElectricItem;
import com.rong.rt.api.capability.RTCapabilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class DischargerSlotWidget extends SlotWidget {

    public DischargerSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition, true, true);
    }

    @Override
    public boolean canPutStack(ItemStack stack) {
        IElectricItem capability = stack.getCapability(RTCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        return capability != null && capability.canProvideChargeExternally();
    }
}