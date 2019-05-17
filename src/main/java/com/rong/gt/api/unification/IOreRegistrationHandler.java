package com.rong.gt.api.unification;

import com.rong.gt.api.unification.materials.types.Material;

@FunctionalInterface
public interface IOreRegistrationHandler {

    void processMaterial(EnumOrePrefix orePrefix, Material material);

}