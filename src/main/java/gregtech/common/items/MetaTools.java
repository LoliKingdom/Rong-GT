package gregtech.common.items;

import static gregtech.common.items.MetaItems.BUZZSAW;
import static gregtech.common.items.MetaItems.FILE;
import static gregtech.common.items.MetaItems.HARD_HAMMER;
import static gregtech.common.items.MetaItems.MORTAR;
import static gregtech.common.items.MetaItems.SAW;
import static gregtech.common.items.MetaItems.SCREWDRIVER;
import static gregtech.common.items.MetaItems.SCREWDRIVER_LV;
import static gregtech.common.items.MetaItems.SOFT_HAMMER;
import static gregtech.common.items.MetaItems.SOLDERING_IRON_LV;
import static gregtech.common.items.MetaItems.TURBINE;
import static gregtech.common.items.MetaItems.WIRE_CUTTER;
import static gregtech.common.items.MetaItems.WRENCH;
import static gregtech.common.items.MetaItems.WRENCH_HV;
import static gregtech.common.items.MetaItems.WRENCH_LV;
import static gregtech.common.items.MetaItems.WRENCH_MV;

import gregtech.api.GregTechAPI;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.tools.ToolBuzzSaw;
import gregtech.common.tools.ToolFile;
import gregtech.common.tools.ToolHardHammer;
import gregtech.common.tools.ToolMortar;
import gregtech.common.tools.ToolSaw;
import gregtech.common.tools.ToolScrewdriver;
import gregtech.common.tools.ToolScrewdriverLV;
import gregtech.common.tools.ToolSoftHammer;
import gregtech.common.tools.ToolSolderingIron;
import gregtech.common.tools.ToolTurbineRotor;
import gregtech.common.tools.ToolWireCutter;
import gregtech.common.tools.ToolWrench;
import gregtech.common.tools.ToolWrenchHV;
import gregtech.common.tools.ToolWrenchLV;
import gregtech.common.tools.ToolWrenchMV;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MetaTools extends ToolMetaItem<ToolMetaItem<?>.MetaToolValueItem> {

    public MetaTools() {
        super();
    }

    @Override
    public void registerSubItems() {
        SAW = addItem(5, "tool.saw").setToolStats(new ToolSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        HARD_HAMMER = addItem(6, "tool.hard_hammer").setToolStats(new ToolHardHammer()).addOreDict(ToolDictNames.craftingToolHardHammer).addToList(GregTechAPI.hardHammerList);
        SOFT_HAMMER = addItem(7, "tool.soft_hammer").setToolStats(new ToolSoftHammer()).addOreDict(ToolDictNames.craftingToolSoftHammer).addToList(GregTechAPI.softHammerList);
        WRENCH = addItem(8, "tool.wrench").setToolStats(new ToolWrench()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        FILE = addItem(9, "tool.file").setToolStats(new ToolFile()).addOreDict(ToolDictNames.craftingToolFile);
        SCREWDRIVER = addItem(10, "tool.screwdriver").setToolStats(new ToolScrewdriver()).addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList);
        MORTAR = addItem(11, "tool.mortar").setToolStats(new ToolMortar()).addOreDict(ToolDictNames.craftingToolMortar);
        WIRE_CUTTER = addItem(12, "tool.wire_cutter").setToolStats(new ToolWireCutter()).addOreDict(ToolDictNames.craftingToolWireCutter);

        WRENCH_LV = addItem(20, "tool.wrench.lv").setToolStats(new ToolWrenchLV())
            .addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        WRENCH_MV = addItem(21, "tool.wrench.mv").setToolStats(new ToolWrenchMV())
            .addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList)
            .addStats(ElectricStats.createElectricItem(400000L, 2L));
        WRENCH_HV = addItem(22, "tool.wrench.hv").setToolStats(new ToolWrenchHV())
            .addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList)
            .addStats(ElectricStats.createElectricItem(1600000L, 3L));

        SCREWDRIVER_LV = addItem(23, "tool.screwdriver.lv").setToolStats(new ToolScrewdriverLV())
            .addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));
        SOLDERING_IRON_LV = addItem(24, "tool.soldering_iron.lv").setToolStats(new ToolSolderingIron())
            .addOreDict(ToolDictNames.craftingToolSolderingIron)
            .addToList(GregTechAPI.solderingToolList)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));

        BUZZSAW = addItem(25, "tool.buzzsaw").setToolStats(new ToolBuzzSaw())
            .addOreDict(ToolDictNames.craftingToolSaw)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));

        TURBINE = addItem(50, "tool.turbine").setToolStats(new ToolTurbineRotor());
    }

    public void registerRecipes() {
        ModHandler.addShapedRecipe("mortar_flint", MORTAR.getStackForm(Materials.Flint),
            " I ", "SIS", "SSS",
            'I', new ItemStack(Items.FLINT, 1),
            'S', OrePrefix.stone);

        IngotMaterial[] mortarMaterials = new IngotMaterial[] {Materials.BismuthBronze, Materials.DamascusSteel, Materials.VanadiumSteel};

        for (IngotMaterial material : mortarMaterials) {
            ModHandler.addShapedRecipe("mortar_" + material.toString(),
                MORTAR.getStackForm(material),
                " I ", "SIS", "SSS",
                'I', new UnificationEntry(OrePrefix.ingot, material),
                'S', OrePrefix.stone);
        }
        
        SolidMaterial[] softHammerMaterials = new SolidMaterial[] {
                Materials.Wood, Materials.Rubber, Materials.Plastic, Materials.Polytetrafluoroethylene
            };
            for(int i = 0; i < softHammerMaterials.length; i++) {
                SolidMaterial solidMaterial = softHammerMaterials[i];
                ItemStack itemStack = MetaItems.SOFT_HAMMER.getStackForm();
                MetaItems.SOFT_HAMMER.setToolData(itemStack, solidMaterial, 128 * (1 << i), 1, 4.0f, 1.0f);
                ModHandler.addShapedRecipe(String.format("soft_hammer_%s", solidMaterial.toString()), itemStack,
                    "XX ", "XXS", "XX ",
                    'X', new UnificationEntry(OrePrefix.ingot, solidMaterial),
                    'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));
            }

        ModHandler.addShapelessRecipe("clay_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Clay, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Blocks.CLAY, 1));

        ModHandler.addShapelessRecipe("wheat_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Items.WHEAT, 1));

        ModHandler.addShapelessRecipe("gravel_to_flint", new ItemStack(Items.FLINT, 1),
            ToolDictNames.craftingToolMortar,
            new ItemStack(Blocks.GRAVEL, 1));
    }
}