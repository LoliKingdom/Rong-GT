package com.rong.rt.api.unification;

import com.rong.rt.api.unification.materials.types.Material;

@FunctionalInterface
public interface IOreRegistrationHandler {

    void processMaterial(EnumOrePrefix orePrefix, Material material);

}