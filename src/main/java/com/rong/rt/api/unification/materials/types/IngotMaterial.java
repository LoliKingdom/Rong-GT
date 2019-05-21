package com.rong.rt.api.unification.materials.types;

import com.google.common.collect.ImmutableList;
import com.rong.rt.api.unification.EnumElements;
import com.rong.rt.api.unification.materials.MaterialIconSet;
import com.rong.rt.api.unification.stack.MaterialStack;

import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

import static com.rong.rt.api.unification.materials.types.DustMaterial.MatFlags.GENERATE_PLATE;
import static com.rong.rt.api.unification.materials.types.DustMaterial.MatFlags.SMELT_INTO_FLUID;
import static com.rong.rt.api.utils.Utility.createFlag;
import static com.rong.rt.api.unification.materials.types.IngotMaterial.MatFlags.*;
import static com.rong.rt.api.unification.materials.types.SolidMaterial.MatFlags.GENERATE_ROD;

public class IngotMaterial extends SolidMaterial {

    public static final class MatFlags {

        public static final long GENERATE_FOIL = createFlag(25);
        public static final long GENERATE_SCREW = createFlag(26);
        public static final long GENERATE_RING = createFlag(27);
        public static final long GENERATE_SPRING = createFlag(28);
        public static final long GENERATE_FINE_WIRE = createFlag(29);
        public static final long GENERATE_ROTOR = createFlag(30);
        public static final long GENERATE_SMALL_GEAR = createFlag(31);
        public static final long GENERATE_DENSE = createFlag(32);
        public static final long GENERATE_SPRING_SMALL = createFlag(33);

        /**
         * Add this to your Material if you want to have its Ore Calcite heated in a Blast Furnace for more output. Already listed are:
         * Iron, Pyrite, PigIron, WroughtIron.
         */
        public static final long BLAST_FURNACE_CALCITE_DOUBLE = createFlag(35);
        public static final long BLAST_FURNACE_CALCITE_TRIPLE = createFlag(36);

        static {
            Material.MatFlags.registerMaterialFlagsHolder(MatFlags.class, IngotMaterial.class);
        }
    }

    /**
     * Specifies a material into which this material parts turn when heated
     */
    public IngotMaterial smeltInto;

    /**
     * Specifies a material into which this material parts turn when processed in the recycler
     */
    public IngotMaterial recycleTo;

    /**
     * Material which obtained when this material is polarized
     */
    @Nullable
    public IngotMaterial magneticMaterial;

    /**
     * Blast furnace temperature of this material
     * Equal to zero if material doesn't use blast furnace
     * If below 1000C, primitive blast furnace recipes will be also added
     */
    public final int blastFurnaceTemperature;

    public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, EnumElements element, float toolSpeed, float attackDamage, int toolDurability, int blastFurnaceTemperature) {
        super(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, element, toolSpeed, attackDamage, toolDurability);
        this.blastFurnaceTemperature = blastFurnaceTemperature;
        this.smeltInto = this;
        this.recycleTo = this;
        addFlag(SMELT_INTO_FLUID);
    }

    public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, EnumElements element) {
        this(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, element, 0, 0, 0, 0);
    }

    public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, EnumElements element, int blastFurnaceTemperature) {
        this(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, element, 0, 0, 0, blastFurnaceTemperature);
    }

    public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, EnumElements element, float toolSpeed, float attackDamage, int toolDurability) {
        this(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, element, toolSpeed, attackDamage, toolDurability, 0);
    }

    public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, float toolSpeed, float attackDamage, int toolDurability) {
        this(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, null, toolSpeed, attackDamage, toolDurability, 0);
    }

    public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
        this(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, null, 0, 0, 0,0);
    }

    @Override
    protected void initializeMaterial() {
        super.initializeMaterial();
        if(blastFurnaceTemperature > 0) {
            setFluidTemperature(blastFurnaceTemperature);
        }
    }

    @Override
    protected long verifyMaterialBits(long generationBits) {
        if((generationBits & GENERATE_DENSE) > 0) {
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_ROTOR) > 0) {
            generationBits |= GENERATE_SCREW;
            generationBits |= GENERATE_RING;
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_SMALL_GEAR) > 0) {
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_FINE_WIRE) > 0) {
            generationBits |= GENERATE_FOIL;
        }
        if((generationBits & GENERATE_FOIL) > 0) {
            generationBits |= GENERATE_PLATE;
        }
        if((generationBits & GENERATE_RING) > 0) {
            generationBits |= GENERATE_ROD;
        }
        if((generationBits & GENERATE_SCREW) > 0) {
            generationBits |= GENERATE_ROD;
        }
        return super.verifyMaterialBits(generationBits);
    }

    public void setSmeltingInto(IngotMaterial smeltInto) {
        this.smeltInto = smeltInto;
    }

    public void setRecycleTo(IngotMaterial recycleInto) {
        this.recycleTo = recycleInto;
    }
}
