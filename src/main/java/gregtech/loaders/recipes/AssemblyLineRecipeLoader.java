package gregtech.loaders.recipes;

import static gregtech.api.GTValues.L;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

public class AssemblyLineRecipeLoader {

    public static void init() {
        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.stick, Materials.NeodymiumMagnetic, 4),
            		OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 8),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(L), Materials.Lubricant.getFluid(250))
            .outputs(MetaItems.ELECTRIC_MOTOR_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ELECTRIC_MOTOR_LuV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.block, Materials.NeodymiumMagnetic, 1),
            		OreDictUnifier.get(OrePrefix.stick, Materials.HSSE, 12),
            		OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4),
            		OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 16),
            		OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Platinum, 64),
            		OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 64),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(9 * L), Materials.Lubricant.getFluid(2000))
            .outputs(MetaItems.ELECTRIC_MOTOR_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.pipeSmall, Materials.Ultimet, 2),
            		OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
            		OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 8),
            		OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 4),
            		OreDictUnifier.get(OrePrefix.rotor, Materials.HSSG, 2),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(L), Materials.Lubricant.getFluid(250))
            .outputs(MetaItems.ELECTRIC_PUMP_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ELECTRIC_MOTOR_LuV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.pipeMedium, MarkerMaterials.Tier.Superconductor, 2),
            		OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2),
            		OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 8),
            		OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16),
            		OreDictUnifier.get(OrePrefix.rotor, Materials.HSSE, 2),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
            .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L), Materials.Lubricant.getFluid(750))
            .outputs(MetaItems.ELECTRIC_PUMP_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.CONVEYOR_MODULE_IV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2),
            		OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
            		OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 32),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 2))
            .fluidInputs(Materials.Rubber.getFluid(10 * L), Materials.Lubricant.getFluid(250))
            .outputs(MetaItems.CONVEYOR_MODULE_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.CONVEYOR_MODULE_LuV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 2),
            		OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
            		OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 32),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 2))
            .fluidInputs(Materials.Rubber.getFluid(L * 20), Materials.Lubricant.getFluid(750))
            .outputs(MetaItems.CONVEYOR_MODULE_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ELECTRIC_PISTON_IV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
            		OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4),
            		OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 32),
            		OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 4),
            		OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
            		OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 2),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 4))
            .fluidInputs(Materials.SolderingAlloy.getFluid(2 * L), Materials.Lubricant.getFluid(250))
            .outputs(MetaItems.ELECTRIC_PISTON_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(MetaItems.ELECTRIC_PISTON_LuV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6),
            		OreDictUnifier.get(OrePrefix.ring, Materials.Darmstadtium, 4),
            		OreDictUnifier.get(OrePrefix.screw, Materials.Darmstadtium, 32),
            		OreDictUnifier.get(OrePrefix.stick, Materials.HSSE, 4),
            		OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
            		OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 2),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 4))
            .fluidInputs(Materials.SolderingAlloy.getFluid(9 * L), Materials.Lubricant.getFluid(2000))
            .outputs(MetaItems.ELECTRIC_PISTON_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 12),
            		OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
            		OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 3),
            		MetaItems.ELECTRIC_MOTOR_LuV.getStackForm(2),
            		MetaItems.ELECTRIC_PISTON_LuV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 2),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 2),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 4),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 6))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L), Materials.Lubricant.getFluid(250))
            .outputs(MetaItems.ROBOT_ARM_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.stick, Materials.Darmstadtium, 8),
            		OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
            		OreDictUnifier.get(OrePrefix.gearSmall, Materials.Darmstadtium, 3),
            		MetaItems.ELECTRIC_MOTOR_UV.getStackForm(2),
            		MetaItems.ELECTRIC_PISTON_UV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Expert, 16),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 6))
            .fluidInputs(Materials.SolderingAlloy.getFluid(12 * L), Materials.Lubricant.getFluid(2000))
            .outputs(MetaItems.ROBOT_ARM_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
            		MetaItems.EMITTER_IV.getStackForm(),
            		MetaItems.EMITTER_EV.getStackForm(2),
            		MetaItems.EMITTER_HV.getStackForm(2),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 7),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(5 * L))
            .outputs(MetaItems.EMITTER_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
            		MetaItems.EMITTER_LuV.getStackForm(2),
            		MetaItems.EMITTER_EV.getStackForm(2),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 7),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .outputs(MetaItems.EMITTER_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
            		MetaItems.SENSOR_IV.getStackForm(),
            		MetaItems.SENSOR_EV.getStackForm(2),
            		MetaItems.SENSOR_HV.getStackForm(2),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 6),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .outputs(MetaItems.SENSOR_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
            		MetaItems.SENSOR_LuV.getStackForm(2),
            		MetaItems.SENSOR_IV.getStackForm(),
            		MetaItems.SENSOR_EV.getStackForm(4),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Expert, 6),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64),
            		OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 7))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .outputs(MetaItems.SENSOR_UV.getStackForm())
            .duration(600)
            .EUt(49500)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
            		OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6),
            		MetaItems.QUANTUM_STAR.getStackForm(2),
            		MetaItems.EMITTER_LuV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 8),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.Iridium, 64),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
            		OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.YttriumBariumCuprate, 8))
            .fluidInputs(Materials.SolderingAlloy.getFluid(4 * L))
            .outputs(MetaItems.FIELD_GENERATOR_LuV.getStackForm())
            .duration(600)
            .EUt(10240)
            .buildAndRegister();

        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Darmstadtium, 1),
            		OreDictUnifier.get(OrePrefix.plate, Materials.Darmstadtium, 6),
            		MetaItems.QUANTUM_STAR.getStackForm(4),
            		MetaItems.EMITTER_UV.getStackForm(),
            		OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Expert, 14),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.Iridium, 64),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.Iridium, 64),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
            		OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64),
            		OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.NiobiumTitanium, 8))
            .fluidInputs(Materials.SolderingAlloy.getFluid(12 * L))
            .outputs(MetaItems.FIELD_GENERATOR_UV.getStackForm())
            .duration(600)
            .EUt(61440)
            .buildAndRegister();
    }
}