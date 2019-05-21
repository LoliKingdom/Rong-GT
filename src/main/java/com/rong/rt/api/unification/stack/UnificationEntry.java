package com.rong.rt.api.unification.stack;

import javax.annotation.Nullable;

import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.types.Material;

public class UnificationEntry {

    public final EnumOrePrefix orePrefix;
    @Nullable
    public final Material material;

    public UnificationEntry(EnumOrePrefix orePrefix, @Nullable Material material) {
        this.orePrefix = orePrefix;
        this.material = material;
    }

    public UnificationEntry(EnumOrePrefix orePrefix) {
        this.orePrefix = orePrefix;
        this.material = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnificationEntry that = (UnificationEntry) o;

        if (orePrefix != that.orePrefix) return false;
        return material != null ? material.equals(that.material) : that.material == null;
    }

    @Override
    public int hashCode() {
        int result = orePrefix.hashCode();
        result = 31 * result + (material != null ? material.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return orePrefix.name() + (material != null ? material.toCamelCaseString() : "");
    }

}
