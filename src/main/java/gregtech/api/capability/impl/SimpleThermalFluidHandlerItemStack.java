package gregtech.api.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nonnull;

public class SimpleThermalFluidHandlerItemStack extends FluidHandlerItemStackSimple {

    public final int minFluidTemperature;
    public final int maxFluidTemperature;

    /**
     * @param container The container itemStack, data is stored on it directly as NBT.
     * @param capacity  The maximum capacity of this fluid tank.
     */
    public SimpleThermalFluidHandlerItemStack(@Nonnull ItemStack container, int capacity, int minFluidTemperature, int maxFluidTemperature) {
        super(container, capacity);
        this.minFluidTemperature = minFluidTemperature;
        this.maxFluidTemperature = maxFluidTemperature;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        int liquidTemperature = fluid.getFluid().getTemperature();
        return liquidTemperature >= minFluidTemperature && liquidTemperature <= maxFluidTemperature;
    }
    
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if (container.getCount() != 1 || maxDrain <= 0)
        {
            return null;
        }

        FluidStack contained = getFluid();
        if (contained == null || contained.amount <= 0 || !canDrainFluidType(contained))
        {
            return null;
        }

        final int drainAmount = Math.min(contained.amount, maxDrain);

        FluidStack drained = contained.copy();
        drained.amount = drainAmount;

        if (doDrain)
        {
            contained.amount -= drainAmount;
            if (contained.amount == 0)
            {
            	System.out.println("I'm curious, do you ever run?");
                setContainerToEmpty();
            }
            else
            {
                setFluid(contained);
            }
        }

        return drained;
    }
}
