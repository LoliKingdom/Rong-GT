package gregtech.common.items;

import static gregtech.common.items.MetaItems.BUZZSAW;
import static gregtech.common.items.MetaItems.FILE;
import static gregtech.common.items.MetaItems.HARD_HAMMER;
import static gregtech.common.items.MetaItems.MORTAR;
import static gregtech.common.items.MetaItems.SAW;
import static gregtech.common.items.MetaItems.SCREWDRIVER;
import static gregtech.common.items.MetaItems.SCREWDRIVER_LV;
import static gregtech.common.items.MetaItems.WIRE_CUTTER;
import static gregtech.common.items.MetaItems.WRENCH;
import static gregtech.common.items.MetaItems.WRENCH_HV;
import static gregtech.common.items.MetaItems.WRENCH_LV;
import static gregtech.common.items.MetaItems.WRENCH_MV;
import static gregtech.common.items.MetaItems.PLUNGER;
import static gregtech.common.items.MetaItems.SOFT_HAMMER;

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
import gregtech.common.ConfigHolder;
import gregtech.common.tools.ToolBuzzSaw;
import gregtech.common.tools.ToolFile;
import gregtech.common.tools.ToolHardHammer;
import gregtech.common.tools.ToolMortar;
import gregtech.common.tools.ToolPlunger;
import gregtech.common.tools.ToolSaw;
import gregtech.common.tools.ToolScrewdriver;
import gregtech.common.tools.ToolScrewdriverLV;
import gregtech.common.tools.ToolSoftHammer;
import gregtech.common.tools.ToolSolderingIron;
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
        SAW = addItem(1, "tool.saw").setToolStats(new ToolSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        HARD_HAMMER = addItem(2, "tool.hard_hammer").setToolStats(new ToolHardHammer()).addOreDict(ToolDictNames.craftingToolHardHammer).addToList(GregTechAPI.hardHammerList);
        SOFT_HAMMER = addItem(3, "tool.soft_hammer").setToolStats(new ToolSoftHammer()).addOreDict(ToolDictNames.craftingToolSoftHammer).addToList(GregTechAPI.softHammerList);
        WRENCH = addItem(4, "tool.wrench").setToolStats(new ToolWrench()).addOreDict(ToolDictNames.craftingToolWrench).addToList(GregTechAPI.wrenchList);
        FILE = addItem(5, "tool.file").setToolStats(new ToolFile()).addOreDict(ToolDictNames.craftingToolFile);
        SCREWDRIVER = addItem(6, "tool.screwdriver").setToolStats(new ToolScrewdriver()).addOreDict(ToolDictNames.craftingToolScrewdriver).addToList(GregTechAPI.screwdriverList);
        MORTAR = addItem(7, "tool.mortar").setToolStats(new ToolMortar()).addOreDict(ToolDictNames.craftingToolMortar);
        WIRE_CUTTER = addItem(8, "tool.wire_cutter").setToolStats(new ToolWireCutter()).addOreDict(ToolDictNames.craftingToolWireCutter);
        
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

        BUZZSAW = addItem(24, "tool.buzzsaw").setToolStats(new ToolBuzzSaw())
            .addOreDict(ToolDictNames.craftingToolSaw)
            .addStats(ElectricStats.createElectricItem(100000L, 1L));

        PLUNGER = addItem(25, "tool.plunger").setToolStats(new ToolPlunger()).addOreDict(ToolDictNames.craftingToolPlunger);
    }
}