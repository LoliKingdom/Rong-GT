package gregtech.loaders.recipes.processing;

import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_SCREW;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_SPRING;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;

public class PartsRecipeHandler {

    public static void register() {
        OrePrefix.stick.addProcessingHandler(DustMaterial.class, PartsRecipeHandler::processStick);
        OrePrefix.plate.addProcessingHandler(DustMaterial.class, PartsRecipeHandler::processPlate);

        OrePrefix.turbineBlade.addProcessingHandler(IngotMaterial.class, PartsRecipeHandler::processTurbine);
        OrePrefix.rotor.addProcessingHandler(IngotMaterial.class, PartsRecipeHandler::processRotor);
        OrePrefix.screw.addProcessingHandler(IngotMaterial.class, PartsRecipeHandler::processScrew);
        OrePrefix.wireFine.addProcessingHandler(IngotMaterial.class, PartsRecipeHandler::processFineWire);
        OrePrefix.foil.addProcessingHandler(IngotMaterial.class, PartsRecipeHandler::processFoil);
        OrePrefix.lens.addProcessingHandler(GemMaterial.class, PartsRecipeHandler::processLens);

        OrePrefix.gear.addProcessingHandler(SolidMaterial.class, PartsRecipeHandler::processGear);
        OrePrefix.gearSmall.addProcessingHandler(SolidMaterial.class, PartsRecipeHandler::processGear);
        OrePrefix.ring.addProcessingHandler(IngotMaterial.class, PartsRecipeHandler::processRing);
        OrePrefix.spring.addProcessingHandler(SolidMaterial.class, PartsRecipeHandler::processSpring);
    }
    

    public static void processScrew(OrePrefix screwPrefix, IngotMaterial material) {
    	ItemStack stickStack = OreDictUnifier.get(OrePrefix.stick, material);
        ItemStack screwStack = OreDictUnifier.get(screwPrefix, material);
        ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);

        ModHandler.addShapedRecipe(String.format("bolt_file_%s", material.toString()),
            stickStack, "fS", "S ",
            'S', new UnificationEntry(OrePrefix.screw, material));

        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .inputs(stickStack)
            .outputs(screwStack)
            .duration((int) Math.max(1, material.getAverageMass() / 8L)).EUt(4)
            .buildAndRegister();

        if (!screwStack.isEmpty() && !ingotStack.isEmpty()) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_EXTRUDER_SCREW)
                .input(OrePrefix.ingot, material)
                .outputs(GTUtility.copyAmount(4, screwStack))
                .duration(15).EUt(74)
                .buildAndRegister();
        }
    }

    public static void processFoil(OrePrefix foilPrefix, IngotMaterial material) {
        ItemStack foilStack = OreDictUnifier.get(foilPrefix, material);
        RecipeMaps.BENDER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, material)
            .outputs(GTUtility.copyAmount(4, foilStack))
            .duration((int) material.getAverageMass()).EUt(24)
            .circuitMeta(0)
            .buildAndRegister();
    }

    public static void processFineWire(OrePrefix fineWirePrefix, IngotMaterial material) {
        ItemStack fineWireStack = OreDictUnifier.get(fineWirePrefix, material);
        ModHandler.addShapelessRecipe(String.format("fine_wire_%s", material.toString()),
            fineWireStack, 'x', new UnificationEntry(OrePrefix.foil, material));
        if(material.cableProperties != null) {
            RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtSingle, material)
                .outputs(OreDictUnifier.get(OrePrefix.wireFine, material, 4))
                .duration(200).EUt(8)
                .buildAndRegister();
        }
    }


    public static void processGear(OrePrefix gearPrefix, SolidMaterial material) {
        ItemStack stack = OreDictUnifier.get(gearPrefix, material);
        if (gearPrefix == OrePrefix.gear && material instanceof IngotMaterial) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 4)
                .notConsumable(MetaItems.SHAPE_EXTRUDER_GEAR)
                .outputs(OreDictUnifier.get(gearPrefix, material))
                .duration((int) material.getAverageMass() * 5)
                .EUt(74)
                .buildAndRegister();

            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 6)
                .notConsumable(MetaItems.SHAPE_MOLD_GEAR)
                .outputs(OreDictUnifier.get(gearPrefix, material))
                .duration((int) material.getAverageMass() * 10)
                .EUt(24)
                .buildAndRegister();
        }

        if (material.shouldGenerateFluid()) {
            boolean isSmall = gearPrefix == OrePrefix.gearSmall;
            RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(isSmall ? MetaItems.SHAPE_MOLD_GEAR_SMALL : MetaItems.SHAPE_MOLD_GEAR)
                .fluidInputs(material.getFluid(L * (isSmall ? 1 : 4)))
                .outputs(stack)
                .duration(isSmall ? 20 : 100).EUt(8)
                .buildAndRegister();
        }

        if(material.hasFlag(GENERATE_PLATE | GENERATE_ROD)) {
            if (gearPrefix == OrePrefix.gearSmall) {
                ModHandler.addShapedRecipe(String.format("small_gear_%s", material), stack,
                    "h ", " P", 'P', new UnificationEntry(OrePrefix.plate, material));
            } else {
                ModHandler.addShapedRecipe(String.format("gear_%s", material), stack,
                    "RPR", "PdP", "RPR",
                    'P', new UnificationEntry(OrePrefix.plate, material),
                    'R', new UnificationEntry(OrePrefix.stick, material));
            }
        }
    }

    public static void processLens(OrePrefix lensPrefix, GemMaterial material) {
        ItemStack stack = OreDictUnifier.get(lensPrefix, material);

        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, material)
            .outputs(stack, OreDictUnifier.get(OrePrefix.dustSmall, material))
            .duration((int) (material.getAverageMass() / 2L))
            .EUt(16)
            .buildAndRegister();

        EnumDyeColor dyeColor = GTUtility.determineDyeColor(material.materialRGB);
        MarkerMaterial colorMaterial = MarkerMaterials.Color.COLORS.get(dyeColor);
        OreDictUnifier.registerOre(stack, OrePrefix.craftingLens, colorMaterial);
    }

    public static void processPlate(OrePrefix platePrefix, DustMaterial material) {
        if (material.shouldGenerateFluid()) {
            RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                .fluidInputs(material.getFluid(L))
                .outputs(OreDictUnifier.get(platePrefix, material))
                .duration(40)
                .EUt(8)
                .buildAndRegister();
        }

        if (material.hasFlag(MORTAR_GRINDABLE)) {
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
            ModHandler.addShapedRecipe(String.format("plate_to_dust_%s", material),
                dustStack, "X", "m",
                'X', new UnificationEntry(OrePrefix.plate, material));
        }
    }

    public static void processRing(OrePrefix ringPrefix, IngotMaterial material) {
        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, material)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_RING)
            .outputs(OreDictUnifier.get(ringPrefix, material, 4))
            .duration((int) material.getAverageMass() * 2)
            .EUt(74)
            .buildAndRegister();

        if (!material.hasFlag(NO_SMASHING)) {
            ModHandler.addShapedRecipe(String.format("ring_%s", material),
                OreDictUnifier.get(ringPrefix, material),
                "h ", " X",
                'X', new UnificationEntry(OrePrefix.stick, material));
        }
    }

    public static void processRotor(OrePrefix rotorPrefix, SolidMaterial material) {
        ItemStack stack = OreDictUnifier.get(rotorPrefix, material);
        ModHandler.addShapedRecipe(String.format("rotor_%s", material.toString()), stack,
            "PhP", "SRf", "PdP",
            'P', new UnificationEntry(OrePrefix.plate, material),
            'R', new UnificationEntry(OrePrefix.ring, material),
            'S', new UnificationEntry(OrePrefix.screw, material));

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, material, 4).input(OrePrefix.ring, material)
            .outputs(stack)
            .fluidInputs(Materials.SolderingAlloy.getFluid(32))
            .duration(240)
            .EUt(24)
            .buildAndRegister();
    }

    public static void processStick(OrePrefix stickPrefix, DustMaterial material) {

        if (material instanceof IngotMaterial) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material)
                .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                .duration((int) material.getAverageMass() * 2)
                .EUt(74)
                .buildAndRegister();
        }

        if (material instanceof GemMaterial || material instanceof IngotMaterial) {
            RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .inputs(material instanceof GemMaterial ? CountableIngredient.from(OrePrefix.gem, material) :
                    CountableIngredient.from(OrePrefix.ingot, material))
                .outputs(OreDictUnifier.get(stickPrefix, material), OreDictUnifier.get(OrePrefix.dustSmall,
                    ((SolidMaterial) material).macerateInto, 2))
                .duration((int) Math.max(material.getAverageMass() * 5L, 1L))
                .EUt(16)
                .buildAndRegister();
        }

        if (material.hasFlag(GENERATE_SCREW)) {
            ItemStack screwStack = OreDictUnifier.get(OrePrefix.screw, material);
            RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                .input(stickPrefix, material)
                .outputs(GTUtility.copyAmount(4, screwStack))
                .duration((int) Math.max(material.getAverageMass() * 2L, 1L)).EUt(4)
                .buildAndRegister();

            ModHandler.addShapedRecipe(String.format("screw_saw_%s", material.toString()),
                GTUtility.copyAmount(2, screwStack),
                "s ", " X",
                'X', new UnificationEntry(OrePrefix.stick, material));
        }
        if (material.hasFlag(IngotMaterial.MatFlags.GENERATE_FINE_WIRE)) {
            RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, material)
                .outputs(OreDictUnifier.get(OrePrefix.wireFine, material, 4))
                .duration(50).EUt(4)
                .buildAndRegister();
        }
    }

    public static void processSpring(OrePrefix springPrefix, DustMaterial material) {
        if (!material.hasFlag(MatFlags.NO_SMASHING)) {
            if (material.hasFlag(GENERATE_SPRING)) {
                RecipeMaps.BENDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.stick, material, 4)
                    .outputs(OreDictUnifier.get(OrePrefix.spring, material))
                    .circuitMeta(1)
                    .duration(200).EUt(24)
                    .buildAndRegister();
            }
        }
    }

    public static void processTurbine(OrePrefix toolPrefix, SolidMaterial material) {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.turbineBlade, material, 8)
            .input(OrePrefix.stick, Materials.Titanium, 4)
            .outputs(MetaItems.TURBINE.getStackForm(material))
            .duration(320)
            .EUt(400)
            .buildAndRegister();

        ModHandler.addShapedRecipe(String.format("turbine_blade_%s", material),
            OreDictUnifier.get(toolPrefix, material),
            "fPd", "SPS", " P ",
            'P', new UnificationEntry(OrePrefix.plate, material),
            'S', new UnificationEntry(OrePrefix.screw, material));
    }


    private static boolean doesMaterialUseNormalFurnace(Material material) {
        return !(material instanceof IngotMaterial) ||
            ((IngotMaterial) material).blastFurnaceTemperature <= 0;
    }
}
