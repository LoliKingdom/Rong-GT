package gregtech.loaders.recipes.processing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;

public class OreRecipeHandler {

    public static void register() {
        OrePrefix.ore.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);
        OrePrefix.oreEndstone.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);
        OrePrefix.oreGravel.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);
        OrePrefix.oreNetherrack.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);
        OrePrefix.oreSand.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);
        OrePrefix.oreSandstone.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);
        OrePrefix.oreRedSandstone.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processOre);

        OrePrefix.crushed.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processCrushedOre);
        OrePrefix.crushedPurified.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processCrushedPurified);
        OrePrefix.crushedCentrifuged.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processCrushedCentrifuged);
        OrePrefix.dustImpure.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processImpureDust);
        OrePrefix.dustPure.addProcessingHandler(DustMaterial.class, OreRecipeHandler::processPureDust);
    }

    public static void processOre(OrePrefix orePrefix, DustMaterial material) {
        DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);
        ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dust, byproductMaterial);
        ItemStack crushedStack = OreDictUnifier.get(OrePrefix.crushed, material);
        ItemStack ingotStack;
        DustMaterial smeltingMaterial = material;
        if(material.directSmelting != null) {
            smeltingMaterial = material.directSmelting;
        }
        if(smeltingMaterial instanceof IngotMaterial) {
            ingotStack = OreDictUnifier.get(OrePrefix.ingot, smeltingMaterial);
        } else if(smeltingMaterial instanceof GemMaterial) {
            ingotStack = OreDictUnifier.get(OrePrefix.gem, smeltingMaterial);
        } else {
            ingotStack = OreDictUnifier.get(OrePrefix.dust, smeltingMaterial);
        }
        ingotStack.setCount(material.smeltingMultiplier);
        crushedStack.setCount(material.oreMultiplier);

        if(!crushedStack.isEmpty()) {
            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                .input(orePrefix, material)
                .outputs(crushedStack)
                .duration(60).EUt(8)
                .buildAndRegister();

            RecipeBuilder<?> builder = RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .input(orePrefix, material)
                .outputs(GTUtility.copyAmount(crushedStack.getCount() * 2, crushedStack))
                .chancedOutput(byproductStack, 1500)
                .duration(280).EUt(18);
            for(MaterialStack secondaryMaterial : orePrefix.secondaryMaterials) {
                if(secondaryMaterial.material instanceof DustMaterial) {
                    ItemStack dustStack = OreDictUnifier.getDust(secondaryMaterial);
                    builder.chancedOutput(dustStack, 5000);
                }
            }
            builder.buildAndRegister();
        }
    }

    public static void processCrushedOre(OrePrefix crushedPrefix, DustMaterial material) {
        ItemStack impureDustStack = OreDictUnifier.get(OrePrefix.dustImpure, material);
        DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material, material.oreByProducts, DustMaterial.class);
        
        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
            .input(crushedPrefix, material)
            .outputs(impureDustStack)
            .duration(20)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(crushedPrefix, material)
            .outputs(impureDustStack)
            .duration(100)
            .EUt(12)
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial, material.byProductMultiplier), 1000)
            .buildAndRegister();

        ItemStack crushedPurifiedOre = GTUtility.copy(
            OreDictUnifier.get(OrePrefix.crushedPurified, material),
            OreDictUnifier.get(OrePrefix.dust, material));
        ItemStack crushedCentrifugedOre = GTUtility.copy(
            OreDictUnifier.get(OrePrefix.crushedCentrifuged, material),
            OreDictUnifier.get(OrePrefix.dust, material));

        RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
            .input(crushedPrefix, material)
            .fluidInputs(ModHandler.getWater(1000))
            .outputs(crushedPurifiedOre,
                OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial, material.byProductMultiplier))
            .buildAndRegister();

        RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
            .input(crushedPrefix, material)
            .fluidInputs(Materials.DistilledWater.getFluid(500))
            .outputs(crushedPurifiedOre,
                OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial, material.byProductMultiplier))
            .duration(300)
            .buildAndRegister();

        RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
            .input(crushedPrefix, material)
            .duration((int) material.getMass() * 2)
            .outputs(crushedCentrifugedOre,
                OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial, material.byProductMultiplier),
                OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
            .buildAndRegister();

        if (material.washedIn != null) {
            DustMaterial washingByproduct = GTUtility.selectItemInList(3, material, material.oreByProducts, DustMaterial.class);
            RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(crushedPrefix, material)
                .fluidInputs(material.washedIn.getFluid(1000))
                .outputs(crushedPurifiedOre)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, washingByproduct, material.byProductMultiplier), 7000)
                .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 4000)
                .duration(800)
                .EUt(8)
                .buildAndRegister();
        }

        ModHandler.addShapelessRecipe(String.format("crushed_ore_to_dust_%s", material),
            impureDustStack, 'h', new UnificationEntry(crushedPrefix, material));

        processMetalSmelting(crushedPrefix, material, 9);
    }

    public static void processCrushedCentrifuged(OrePrefix centrifugedPrefix, DustMaterial material) {
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
        ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(2,
            material, material.oreByProducts, DustMaterial.class), 1);

        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
            .input(centrifugedPrefix, material)
            .outputs(dustStack)
            .duration(20).EUt(8)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(centrifugedPrefix, material)
            .outputs(dustStack)
            .chancedOutput(byproductStack, 1000)
            .duration(40).EUt(18)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(String.format("centrifuged_ore_to_dust_%s", material), dustStack,
            'h', new UnificationEntry(centrifugedPrefix, material));

        processMetalSmelting(centrifugedPrefix, material, 10);
    }

    public static void processCrushedPurified(OrePrefix purifiedPrefix, DustMaterial material) {
        ItemStack crushedCentrifugedStack = OreDictUnifier.get(OrePrefix.crushedCentrifuged, material);
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
        ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(1, material, material.oreByProducts, DustMaterial.class));

        RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
            .input(purifiedPrefix, material)
            .outputs(dustStack)
            .duration(20)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(purifiedPrefix, material)
            .outputs(dustStack)
            .chancedOutput(byproductStack, 1000)
            .duration(120)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(String.format("purified_ore_to_dust_%s", material), dustStack,
            'h', new UnificationEntry(purifiedPrefix, material));

        if (!crushedCentrifugedStack.isEmpty()) {
            RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                .input(purifiedPrefix, material)
                .outputs(crushedCentrifugedStack, byproductStack)
                .duration((int) (material.getMass() * 2))
                .EUt(48)
                .buildAndRegister();
        }
        processMetalSmelting(purifiedPrefix, material, 9);
    }

    public static void processImpureDust(OrePrefix dustPrefix, DustMaterial material) {
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);
        /*if (dustPrefix == OrePrefix.dustPure && material.separatedOnto != null) {
            ItemStack separatedStack = OreDictUnifier.get(OrePrefix.dustSmall, material.separatedOnto);
            RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(dustPrefix, material)
                .outputs(dustStack)
                .chancedOutput(separatedStack, 4000)
                .duration((int) material.separatedOnto.getMass()).EUt(24)
                .buildAndRegister();
        }*/

        FluidMaterial byproduct = GTUtility.selectItemInList(0, material, material.oreByProducts, FluidMaterial.class);

        RecipeBuilder builder = RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
            .input(dustPrefix, material)
            .outputs(dustStack)
            .duration((int) (material.getMass() / 2)).EUt(24);

        if (byproduct instanceof DustMaterial) {
            builder.outputs(OreDictUnifier.get(OrePrefix.dustTiny, byproduct));
        } else {
            builder.fluidOutputs(byproduct.getFluid(GTValues.L / 9));
        }

        builder.buildAndRegister();

        //dust gains same amount of material as normal dust
        processMetalSmelting(dustPrefix, material, 8);
    }

    public static void processPureDust(OrePrefix purePrefix, DustMaterial material) {
        DustMaterial byproductMaterial = GTUtility.selectItemInList(1, material, material.oreByProducts, DustMaterial.class);
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
            .input(purePrefix, material)
            .outputs(dustStack, OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial))
            .duration((int)(material.getMass() / 2))
            .EUt(5)
            .buildAndRegister();

        processMetalSmelting(purePrefix, material, 9);
    }

    public static void processMetalSmelting(OrePrefix prefix, DustMaterial material, int amount) {
        int smeltingNuggetsAmount = 0;
        IngotMaterial smeltingMaterial = null;
        if(material.directSmelting instanceof IngotMaterial) {
            smeltingMaterial = (IngotMaterial) material.directSmelting;
            smeltingNuggetsAmount = amount;
        } else if(material instanceof IngotMaterial) {
            smeltingMaterial = (IngotMaterial) material;
            smeltingNuggetsAmount = amount;
        }
        if(smeltingMaterial != null && doesMaterialUseNormalFurnace(smeltingMaterial)) {
            ItemStack smeltingStack = smeltingNuggetsAmount % 9 == 0 ?
                OreDictUnifier.get(OrePrefix.ingot, smeltingMaterial, smeltingNuggetsAmount / 9) :
                OreDictUnifier.get(OrePrefix.nugget, smeltingMaterial, smeltingNuggetsAmount);
            if(!smeltingStack.isEmpty()) {
                ModHandler.addSmeltingRecipe(new UnificationEntry(prefix, material), smeltingStack);
            }
        }
    }
    private static boolean doesMaterialUseNormalFurnace(Material material) {
        return !(material instanceof IngotMaterial) ||
            ((IngotMaterial) material).blastFurnaceTemperature <= 0;
    }
}
