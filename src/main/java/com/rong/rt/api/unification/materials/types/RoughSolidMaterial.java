package com.rong.rt.api.unification.materials.types;

import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.MaterialIconSet;
import com.rong.rt.api.unification.stack.MaterialStack;

public class RoughSolidMaterial extends SolidMaterial {

    //use Supplier to avoid directly referencing and resolving OrePrefix too early,
    //which leads to class initialization deadlock and probably crash during OrePrefix initialization
    //instead, we use lazy-computing OrePrefix solid form supplier
    public final Supplier<EnumOrePrefix> solidFormSupplier;

    public RoughSolidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, float toolSpeed, float attackDamage, int toolDurability, Supplier<EnumOrePrefix> solidFormSupplier) {
        super(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, null, toolSpeed, attackDamage, toolDurability);
        this.solidFormSupplier = solidFormSupplier;
    }

    public RoughSolidMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Supplier<EnumOrePrefix> solidFormSupplier) {
        super(metaItemSubId, name, materialRGB, materialIconSet, harvestLevel, materialComponents, materialGenerationFlags, null, 0, 0, 0);
        this.solidFormSupplier = solidFormSupplier;
    }

}
