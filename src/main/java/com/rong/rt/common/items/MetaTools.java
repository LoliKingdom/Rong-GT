package com.rong.rt.common.items;

import static com.rong.rt.common.items.MetaItems.FILE;
import static com.rong.rt.common.items.MetaItems.HARD_HAMMER;
import static com.rong.rt.common.items.MetaItems.KNIFE;
import static com.rong.rt.common.items.MetaItems.MORTAR;
import static com.rong.rt.common.items.MetaItems.PLUNGER;
import static com.rong.rt.common.items.MetaItems.SAW;
import static com.rong.rt.common.items.MetaItems.SCREWDRIVER;
import static com.rong.rt.common.items.MetaItems.SOFT_HAMMER;
import static com.rong.rt.common.items.MetaItems.WIRE_CUTTER;
import static com.rong.rt.common.items.MetaItems.WRENCH;

import com.rong.rt.api.ToolDictNames;
import com.rong.rt.api.metaitems.ToolMetaItem;
import com.rong.rt.common.items.tools.ToolFile;
import com.rong.rt.common.items.tools.ToolHardHammer;
import com.rong.rt.common.items.tools.ToolKnife;
import com.rong.rt.common.items.tools.ToolMortar;
import com.rong.rt.common.items.tools.ToolPlunger;
import com.rong.rt.common.items.tools.ToolSaw;
import com.rong.rt.common.items.tools.ToolScrewdriver;
import com.rong.rt.common.items.tools.ToolWireCutter;
import com.rong.rt.common.items.tools.ToolWrench;

public class MetaTools extends ToolMetaItem<ToolMetaItem<?>.MetaToolValueItem> {
	
	public MetaTools() {
        super();
    }

    @Override
    public void registerSubItems() {
        SAW = addItem(1, "saw").setToolStats(new ToolSaw()).addOreDict(ToolDictNames.craftingToolSaw);
        HARD_HAMMER = addItem(2, "hard_hammer").setToolStats(new ToolHardHammer()).addOreDict(ToolDictNames.craftingToolHardHammer);
        WRENCH = addItem(4, "wrench").setToolStats(new ToolWrench()).addOreDict(ToolDictNames.craftingToolWrench);
        FILE = addItem(5, "file").setToolStats(new ToolFile()).addOreDict(ToolDictNames.craftingToolFile);
        SCREWDRIVER = addItem(6, "screwdriver").setToolStats(new ToolScrewdriver()).addOreDict(ToolDictNames.craftingToolScrewdriver);
        MORTAR = addItem(7, "mortar").setToolStats(new ToolMortar()).addOreDict(ToolDictNames.craftingToolMortar);
        WIRE_CUTTER = addItem(8, "wire_cutter").setToolStats(new ToolWireCutter()).addOreDict(ToolDictNames.craftingToolWireCutter);
        KNIFE = addItem(9, "knife").setToolStats(new ToolKnife()).addOreDict(ToolDictNames.craftingToolKnife);
        PLUNGER = addItem(15, "plunger").setToolStats(new ToolPlunger()).addOreDict(ToolDictNames.craftingToolPlunger);
    }

}
