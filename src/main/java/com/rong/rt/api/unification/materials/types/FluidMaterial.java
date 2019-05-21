package com.rong.rt.api.unification.materials.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.rong.rt.api.unification.EnumElements;
import com.rong.rt.api.unification.materials.MaterialIconSet;
import com.rong.rt.api.unification.stack.MaterialStack;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.rong.rt.api.utils.Utility.createFlag;

@ZenClass("mods.rongtech.material.FluidMaterial")
@ZenRegister
public class FluidMaterial extends Material {

    public static final class MatFlags {
        /**
         * Whenever system should generate fluid block for this fluid material
         * Renamed to GENERATE_FLUID_BLOCK to avoid confusion with dust blocks
         */
        public static final long GENERATE_FLUID_BLOCK = createFlag(44);

        /**
         * Add this flag to enable plasma generation for this material
         */
        public static final long GENERATE_PLASMA = createFlag(9);

        /**
         * Marks material state as gas
         * Examples: Air, Argon, Refinery Gas, Oxygen, Hydrogen
         */
        public static final long STATE_GAS = createFlag(10);

        static {
            Material.MatFlags.registerMaterialFlagsHolder(MatFlags.class, FluidMaterial.class);
        }

    }

    /**
     * Internal material fluid field
     */
    @Nullable
    private Fluid materialFluid;

    /**
     * Internal material plasma fluid field
     */
    @Nullable
    private Fluid materialPlasma;

    private int fluidTemperature = 295;

    public FluidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, EnumElements element) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element);
    }

    public FluidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, null);
    }

    @ZenGetter("hasFluid")
    public boolean shouldGenerateFluid() {
        return true;
    }

    @ZenGetter("hasPlasma")
    public boolean shouldGeneratePlasma() {
        return shouldGenerateFluid() && hasFlag(MatFlags.GENERATE_PLASMA);
    }

    @ZenGetter("isGaseous")
    public boolean isGas() {
        return hasFlag(MatFlags.STATE_GAS);
    }

    /**
     * internal usage only
     */
    public final void setMaterialFluid(@Nonnull Fluid materialFluid) {
        Preconditions.checkNotNull(materialFluid);
        this.materialFluid = materialFluid;
    }

    /**
     * internal usage only
     */
    public final void setMaterialPlasma(@Nonnull Fluid materialPlasma) {
        Preconditions.checkNotNull(materialPlasma);
        this.materialPlasma = materialPlasma;
    }

    public final @Nullable Fluid getMaterialFluid() {
        return materialFluid;
    }

    public final @Nullable Fluid getMaterialPlasma() {
        return materialPlasma;
    }

    public final @Nullable FluidStack getFluid(int amount) {
        return materialFluid == null ? null : new FluidStack(materialFluid, amount);
    }

    public final @Nullable FluidStack getPlasma(int amount) {
        return materialPlasma == null ? null : new FluidStack(materialPlasma, amount);
    }

    @ZenSetter("setFluidTemperature")
    public FluidMaterial setFluidTemperature(int fluidTemperature) {
        Preconditions.checkArgument(fluidTemperature > 0, "Invalid temperature");
        this.fluidTemperature = fluidTemperature;
        return this;
    }

    @ZenGetter("fluidTemperature")
    public int getFluidTemperature() {
        return fluidTemperature;
    }

    @ZenGetter("fluid")
    @Method(modid = "crafttweaker")
    @Nullable
    public ILiquidDefinition ctGetFluid() {
        Fluid materialFluid = getMaterialFluid();
        return materialFluid == null ? null : CraftTweakerMC.getILiquidDefinition(materialFluid);
    }

    @ZenGetter("plasma")
    @Method(modid = "crafttweaker")
    @Nullable
    public ILiquidDefinition ctGetPlasma() {
        Fluid materialFluid = getMaterialPlasma();
        return materialFluid == null ? null : CraftTweakerMC.getILiquidDefinition(materialFluid);
    }

}
