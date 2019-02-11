package gregtech.loaders.recipes.processing;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.toolitem.ToolMetaItem.MetaToolValueItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_BOLT_SCREW;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;

public class ToolRecipeHandler {

    public static void register() {
        OrePrefix.stick.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processStick);

        OrePrefix.toolHeadSaw.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processSawHead);

        OrePrefix.toolHeadWrench.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processWrenchHead);
        OrePrefix.toolHeadBuzzSaw.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processBuzzSawHead);
        OrePrefix.toolHeadFile.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processFileHead);
        OrePrefix.toolHeadScrewdriver.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processScrewdriverHead);
        OrePrefix.toolHeadHammer.addProcessingHandler(SolidMaterial.class, ToolRecipeHandler::processHammerHead);
    }

    public static MetaValueItem[] motorItems;
    public static SolidMaterial[] baseMaterials;
    public static MetaValueItem[][] batteryItems;

    public static void initializeMetaItems() {
        motorItems = new MetaValueItem[]{MetaItems.ELECTRIC_MOTOR_LV, MetaItems.ELECTRIC_MOTOR_MV, MetaItems.ELECTRIC_MOTOR_HV};
        baseMaterials = new SolidMaterial[]{Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};
        batteryItems = new MetaValueItem[][]{
            {MetaItems.BATTERY_RE_LV_LITHIUM, MetaItems.BATTERY_RE_LV_CADMIUM, MetaItems.BATTERY_RE_LV_SODIUM},
            {MetaItems.BATTERY_RE_MV_LITHIUM, MetaItems.BATTERY_RE_MV_CADMIUM, MetaItems.BATTERY_RE_MV_SODIUM},
            {MetaItems.BATTERY_RE_HV_LITHIUM, MetaItems.BATTERY_RE_HV_CADMIUM, MetaItems.BATTERY_RE_HV_SODIUM}};
    }

    public static void processSimpleElectricToolHead(OrePrefix toolPrefix, SolidMaterial solidMaterial, MetaToolValueItem[] toolItems) {
        for(int i = 0; i < toolItems.length; i++) {
            for(MetaValueItem battery : batteryItems[i]) {
                ItemStack batteryStack = battery.getStackForm();

                @SuppressWarnings("ConstantConditions")
                long maxCharge = batteryStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null).getMaxCharge();
                ItemStack drillStack = toolItems[i].getMaxChargeOverrideStack(
                    solidMaterial, maxCharge);
                String recipeName = String.format("%s_%s_%s",
                    toolItems[i].unlocalizedName, battery.unlocalizedName, solidMaterial);

                ModHandler.addShapedIngredientAwareRecipe(recipeName, drillStack,
                    "SXd", "GMG", "PBP",
                    'X', new UnificationEntry(toolPrefix, solidMaterial),
                    'M', motorItems[i].getStackForm(),
                    'S', new UnificationEntry(OrePrefix.screw, baseMaterials[i]),
                    'P', new UnificationEntry(OrePrefix.plate, baseMaterials[i]),
                    'G', new UnificationEntry(OrePrefix.gearSmall, baseMaterials[i]),
                    'B', batteryStack);
            }
        }
    }

    public static void processSimpleToolHead(OrePrefix toolPrefix, SolidMaterial solidMaterial, MetaToolValueItem toolItem, Object... recipe) {
        Material handleMaterial = Materials.Wood;

        ModHandler.addShapelessRecipe(String.format("%s_%s_%s", toolPrefix.name(), solidMaterial, handleMaterial),
            toolItem.getStackForm(solidMaterial),
            new UnificationEntry(toolPrefix, solidMaterial),
            new UnificationEntry(OrePrefix.stick, handleMaterial));

        if (solidMaterial instanceof IngotMaterial && solidMaterial.hasFlag(GENERATE_PLATE)) {
            addSimpleToolRecipe(toolPrefix, solidMaterial, toolItem,
                new UnificationEntry(OrePrefix.plate, solidMaterial),
                new UnificationEntry(OrePrefix.ingot, solidMaterial), recipe);
        }
        if (solidMaterial instanceof GemMaterial) {
            addSimpleToolRecipe(toolPrefix, solidMaterial, toolItem,
                new UnificationEntry(OrePrefix.gem, solidMaterial),
                new UnificationEntry(OrePrefix.gem, solidMaterial), recipe);
        }
    }

    public static void processStick(OrePrefix stickPrefix, SolidMaterial material) {
        if (material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
            return;
        }

        if (material instanceof IngotMaterial && material.toolDurability > 0) {
            ModHandler.addShapedRecipe(String.format("plunger_%s", material),
                MetaItems.PLUNGER.getStackForm(material),
                "xRR", " SR", "S f",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));
        }

        SolidMaterial handleMaterial = Materials.Wood;
        if (material.hasFlag(GENERATE_ROD) && material.toolDurability > 0) {
            ModHandler.addShapedRecipe(String.format("screwdriver_%s_%s", material.toString(), handleMaterial.toString()),
                MetaItems.SCREWDRIVER.getStackForm(material),
                " fS", " Sh", "W  ",
                'S', new UnificationEntry(OrePrefix.stick, material),
                'W', new UnificationEntry(OrePrefix.stick, handleMaterial));
        }
        
        if (material.hasFlag(GENERATE_PLATE | GENERATE_ROD | GENERATE_BOLT_SCREW) && material.toolDurability > 0) {
            for (MetaValueItem batteryItem : batteryItems[0]) {
                ItemStack batteryStack = batteryItem.getStackForm();
                long maxCharge = batteryStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null).getMaxCharge();
                ModHandler.addShapedIngredientAwareRecipe(String.format("soldering_iron_lv_%s_%s", material.toString(), batteryItem.unlocalizedName),
                    MetaItems.SOLDERING_IRON_LV.getMaxChargeOverrideStack(material, maxCharge),
                    "LBf", "Sd ", "P  ",
                    'B', new UnificationEntry(OrePrefix.screw, material),
                    'P', new UnificationEntry(OrePrefix.plate, material),
                    'S', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                    'L', batteryStack);
            }

            ModHandler.addShapedRecipe(String.format("wire_cutter_%s", material.toString()),
                MetaItems.WIRE_CUTTER.getStackForm(material),
                "PfP", "hPd", "STS",
                'S', new UnificationEntry(stickPrefix, material),
                'P', new UnificationEntry(OrePrefix.plate, material),
                'T', new UnificationEntry(OrePrefix.screw, material));
        }
    }

    public static void processWrenchHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[]{MetaItems.WRENCH_LV, MetaItems.WRENCH_MV, MetaItems.WRENCH_HV});
        ModHandler.addShapedRecipe(String.format("wrench_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadWrench, solidMaterial),
            "hXW", "XRX", "WXd",
            'X', new UnificationEntry(OrePrefix.plate, solidMaterial),
            'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
            'W', new UnificationEntry(OrePrefix.screw, Materials.Steel));
    }

    public static void processBuzzSawHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[] {MetaItems.BUZZSAW});
        ModHandler.addShapedRecipe(String.format("buzzsaw_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, solidMaterial),
            "wXh", "X X", "fXx",
            'X', new UnificationEntry(OrePrefix.plate, solidMaterial));
    }

    public static void processScrewdriverHead(OrePrefix toolPrefix, Material material) {
        if(!(material instanceof SolidMaterial)) return;
        SolidMaterial solidMaterial = (SolidMaterial) material;
        processSimpleElectricToolHead(toolPrefix, solidMaterial, new MetaToolValueItem[] {MetaItems.SCREWDRIVER_LV});
        ModHandler.addShapedRecipe(String.format("screwdriver_head_%s", solidMaterial.toString()),
            OreDictUnifier.get(OrePrefix.toolHeadScrewdriver, solidMaterial),
            "fX", "Xh",
            'X', new UnificationEntry(OrePrefix.stick, solidMaterial));
    }

    public static void addSimpleToolRecipe(OrePrefix toolPrefix, SolidMaterial solidMaterial, MetaToolValueItem toolItem, UnificationEntry plate, UnificationEntry ingot, Object[] recipe) {
        ArrayList<Character> usedChars = new ArrayList<>();
        for(Object object : recipe) {
            if(!(object instanceof String))
                continue;
            char[] chars = ((String) object).toCharArray();
            for(char character : chars)
                usedChars.add(character);
        }

        if(usedChars.contains('P')) {
            recipe = ArrayUtils.addAll(recipe, 'P', plate);
        }
        if(usedChars.contains('I')) {
            recipe = ArrayUtils.addAll(recipe, 'I', ingot);
        }

        ModHandler.addShapedRecipe(
            String.format("head_%s_%s", toolPrefix.name(), solidMaterial.toString()),
            OreDictUnifier.get(toolPrefix, solidMaterial), recipe);
    }

    public static void processSawHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.SAW, "PP", "fh");

        int voltageMultiplier = getVoltageMultiplier(solidMaterial);

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, solidMaterial, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_SAW)
            .outputs(OreDictUnifier.get(OrePrefix.toolHeadSaw, solidMaterial))
            .duration((int) solidMaterial.getAverageMass() * 2)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();
    }

    public static void processHammerHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        if(!solidMaterial.hasFlag(NO_WORKING)) {
            processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.HARD_HAMMER, "II ", "IIh", "II ");
        }
        if(solidMaterial instanceof IngotMaterial) {
            SolidMaterial handleMaterial = Materials.Wood;
            if(!solidMaterial.hasFlag(NO_WORKING)) {
                ModHandler.addShapedRecipe(String.format("hammer_%s", solidMaterial.toString()),
                    MetaItems.HARD_HAMMER.getStackForm(solidMaterial),
                    "XX ", "XXS", "XX ",
                    'X', new UnificationEntry(OrePrefix.ingot, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, handleMaterial));
            }
        }

        if (!solidMaterial.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
            int voltageMultiplier = getVoltageMultiplier(solidMaterial);

            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, solidMaterial, 6)
                .notConsumable(MetaItems.SHAPE_EXTRUDER_HAMMER)
                .outputs(OreDictUnifier.get(toolPrefix, solidMaterial))
                .duration((int) solidMaterial.getAverageMass() * 6)
                .EUt(8 * voltageMultiplier)
                .buildAndRegister();
        }
    }

    public static void processFileHead(OrePrefix toolPrefix, SolidMaterial solidMaterial) {
        processSimpleToolHead(toolPrefix, solidMaterial, MetaItems.FILE, " I ", " I ", " fh");
        if(solidMaterial instanceof IngotMaterial) {
            SolidMaterial handleMaterial = Materials.Wood;
            ModHandler.addShapedRecipe(String.format("file_%s", solidMaterial),
                MetaItems.FILE.getStackForm(solidMaterial),
                "P", "P", "S",
                'P', new UnificationEntry(OrePrefix.plate, solidMaterial),
                'S', new UnificationEntry(OrePrefix.stick, handleMaterial));
        }

        int voltageMultiplier = getVoltageMultiplier(solidMaterial);

        RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
            .input(OrePrefix.ingot, solidMaterial, 2)
            .notConsumable(MetaItems.SHAPE_EXTRUDER_FILE)
            .outputs(OreDictUnifier.get(toolPrefix, solidMaterial))
            .duration((int) solidMaterial.getAverageMass() * 2)
            .EUt(8 * voltageMultiplier)
            .buildAndRegister();

    }

    private static int getVoltageMultiplier(Material material) {
        return material instanceof IngotMaterial && ((IngotMaterial) material)
            .blastFurnaceTemperature >= 2800 ? 32 : 8;
    }

}
