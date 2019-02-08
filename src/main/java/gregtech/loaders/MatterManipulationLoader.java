package gregtech.loaders;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;

public class MatterManipulationLoader {
    public static void init() {
    	for (Material m : FluidMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() > 0 && m.getNeutrons() > 0 && m.getMass() != 98 && m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty()) {
            	RecipeMaps.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).fluidInputs(((FluidMaterial) m).getFluid(1000)).fluidOutputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons())).buildAndRegister();
            }
        }
        for (Material m : DustMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() >= 1 && m.getNeutrons() >= 0 && m.getMass() != 98 && m instanceof DustMaterial && m != Materials.Sphalerite && m != Materials.Ash && m != Materials.DarkAsh) {
            	RecipeMaps.MASS_FABRICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).inputs((OreDictUnifier.get(OrePrefix.dust, m))).fluidOutputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons())).buildAndRegister();
            }
        }
       for (Material m : FluidMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() > 0 && m.getNeutrons() > 0 && m.getMass() != 98 && m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty() && m != Materials.Air && m != Materials.LiquidAir) {
            	RecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).fluidOutputs(((FluidMaterial) m).getFluid(1000)).fluidInputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons()), ((FluidMaterial)m).getFluid(1000)).buildAndRegister();
            }
        }
        for (Material m : DustMaterial.MATERIAL_REGISTRY) {
            if (m.getProtons() >= 1 && m.getNeutrons() >= 0 && m.getMass() != 98 && m instanceof DustMaterial && m != Materials.Sphalerite && m != Materials.Ash && m != Materials.DarkAsh) {
            	RecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int)(m.getMass() * 100)).EUt(32).notConsumable(OreDictUnifier.get(OrePrefix.dust, m)).outputs((OreDictUnifier.get(OrePrefix.dust, m))).fluidInputs(Materials.PositiveMatter.getFluid((int) m.getProtons()), Materials.NeutralMatter.getFluid((int) m.getNeutrons())).buildAndRegister();
            }
        }
    }
}