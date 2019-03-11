package gregtech.common.metatileentities.multi.electric.generator;

import java.util.function.Supplier;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FuelRecipeMapWorkableHandler;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.common.MetaFluids;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine.TurbineType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;

public class LargeTurbineWorkableHandler extends FuelRecipeMapWorkableHandler {

	private static final int CYCLE_LENGTH = 80;
    private static final int BASE_ROTOR_DAMAGE = 11;
    private static final int BASE_EU_OUTPUT = 8192;
    private static final int EU_OUTPUT_BONUS = 8192;

    private MetaTileEntityLargeTurbine largeTurbine;
    private int rotorCycleLength = CYCLE_LENGTH;

    public LargeTurbineWorkableHandler(MetaTileEntityLargeTurbine metaTileEntity, FuelRecipeMap recipeMap, Supplier<IEnergyContainer> energyContainer, Supplier<IMultipleTankHandler> fluidTank, long maxVoltage) {
        super(metaTileEntity, recipeMap, energyContainer, fluidTank, maxVoltage);
        this.largeTurbine = metaTileEntity;
    }

    @Override
    public void update() {
        super.update();
        long totalEnergyOutput = getRecipeOutputVoltage();
        if(totalEnergyOutput > 0) {
            energyContainer.get().addEnergy(totalEnergyOutput);
        }
    }

    public FluidStack getFuelStack() {
        if(previousRecipe == null)
            return null;
        FluidStack fuelStack = previousRecipe.getRecipeFluid();
        return fluidTank.get().drain(new FluidStack(fuelStack.getFluid(), Integer.MAX_VALUE), false);
    }

    @Override
    public boolean checkRecipe(FuelRecipe recipe) {
        MetaTileEntityRotorHolder rotorHolder = largeTurbine.getAbilities(MetaTileEntityLargeTurbine.ABILITY_ROTOR_HOLDER).get(0);
        if(++rotorCycleLength >= CYCLE_LENGTH) {
        	int damageToBeApplied = (int) Math.round(BASE_ROTOR_DAMAGE * rotorHolder.getRelativeRotorSpeed()) + 1;
            if(rotorHolder.applyDamageToRotor(damageToBeApplied, false)) {
                this.rotorCycleLength = 0;
                return true;
            } 
            else {
            	return false;
            }
        }
        return true;
    }

    @Override
    protected int calculateFuelAmount(FuelRecipe currentRecipe) {
        MetaTileEntityRotorHolder rotorHolder = largeTurbine.getAbilities(MetaTileEntityLargeTurbine.ABILITY_ROTOR_HOLDER).get(0);
        double relativeRotorSpeed = rotorHolder.getRelativeRotorSpeed();
        return (int) Math.floor(super.calculateFuelAmount(currentRecipe) * (relativeRotorSpeed * relativeRotorSpeed));
    }

    @Override
    protected long startRecipe(FuelRecipe currentRecipe, int fuelAmountUsed, int recipeDuration) {
        addOutputFluids(currentRecipe, fuelAmountUsed);
        return 0L; //energy is added each tick while the rotor speed is >0 RPM
    }

    private void addOutputFluids(FuelRecipe currentRecipe, int fuelAmountUsed) {
        if(largeTurbine.turbineType == TurbineType.STEAM) {
            int waterFluidAmount = fuelAmountUsed / 15;
            if(waterFluidAmount > 0) {
                FluidStack waterStack = Materials.Water.getFluid(waterFluidAmount);
                largeTurbine.exportFluidHandler.fill(waterStack, true);
            }
        } else if(largeTurbine.turbineType == TurbineType.PLASMA) {
            FluidMaterial material = MetaFluids.getMaterialFromFluid(currentRecipe.getRecipeFluid().getFluid());
            if(material != null) {
                largeTurbine.exportFluidHandler.fill(material.getFluid(fuelAmountUsed), true);
            }
        }
    }
    
    @Override
    public long getRecipeOutputVoltage() {
        MetaTileEntityRotorHolder rotorHolder = largeTurbine.getAbilities(MetaTileEntityLargeTurbine.ABILITY_ROTOR_HOLDER).get(0);
        double relativeRotorSpeed = rotorHolder.getRelativeRotorSpeed();
        if (rotorHolder.getCurrentRotorSpeed() > 0 && rotorHolder.hasRotorInInventory()) {
            double rotorEfficiency = rotorHolder.getRotorEfficiency();
            double totalEnergyOutput = (BASE_EU_OUTPUT + EU_OUTPUT_BONUS * rotorEfficiency) * (relativeRotorSpeed * relativeRotorSpeed);
            return MathHelper.ceil(totalEnergyOutput);
        }
        return 0L;
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = super.serializeNBT();
        tagCompound.setInteger("CycleLength", rotorCycleLength);
        return tagCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.rotorCycleLength = compound.getInteger("CycleLength");
    }

    @Override
    protected boolean shouldVoidExcessiveEnergy() {
        return true;
    }
}
