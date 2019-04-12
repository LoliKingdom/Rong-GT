package gregtech.integration.nuclearcraft;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import gregtech.common.items.MetaTools;
import nc.radiation.RadSources;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.traits.ITrait;

public class GTRadSources {
	
	public static void init() {		
		
		double VIBRANIUM_RADIATION = 0.000007851D;
		double DARMSTADTIUM_RADIATION = 0.0000524D;
		double TRITANIUM_RADIATION = 0.000012D;
		double NAQUADAH_RADIATION = 0.000000679D;
		double NAQUADAHENRICHED_RADIATION = 0.0000006D;
		double NAQUADAHALLOY_RADIATION = 0.0000006D;
		double DURANIUM_RADIATION = 0.0000811D;
		double ADAMANTINE_RADIATION = 0.00000091D;
		double SCHEELITE_RADIATION = 0.000000000411D;	
		double RARE_EARTH_RADIATION = 0.000000000911D;
		double LUTETIUM_RADIATION = 0.00000000530D;
		double OSMIUM_RADIATION = 0.0000000000034D;	
		double ZIRCONIUM_RADIATION = 0.0000001354D;	
		double ZIRCON_RADIATION = 0.000000554D;	
		
		RadSources.putMaterial(RadSources.URANIUM, Materials.Uraninite.toCamelCaseString());
		RadSources.putMaterial(VIBRANIUM_RADIATION, Materials.Vibranium.toCamelCaseString());
		RadSources.putMaterial(DARMSTADTIUM_RADIATION, Materials.Darmstadtium.toCamelCaseString());
		RadSources.putMaterial(TRITANIUM_RADIATION, Materials.Tritanium.toCamelCaseString());
		RadSources.putMaterial(NAQUADAH_RADIATION, Materials.Naquadah.toCamelCaseString());
		RadSources.putMaterial(NAQUADAHENRICHED_RADIATION, Materials.NaquadahEnriched.toCamelCaseString());
		RadSources.putMaterial(NAQUADAHALLOY_RADIATION, Materials.NaquadahAlloy.toCamelCaseString());
		RadSources.putMaterial(SCHEELITE_RADIATION, Materials.Scheelite.toCamelCaseString());
		RadSources.putMaterial(DURANIUM_RADIATION, Materials.Duranium.toCamelCaseString());
		RadSources.putMaterial(ADAMANTINE_RADIATION, Materials.Adamantine.toCamelCaseString());
		RadSources.putMaterial(RARE_EARTH_RADIATION, Materials.RareEarth.toCamelCaseString());
		RadSources.putMaterial(LUTETIUM_RADIATION, Materials.Lutetium.toCamelCaseString());
		RadSources.putMaterial(OSMIUM_RADIATION, Materials.Osmium.toCamelCaseString());
		RadSources.putMaterial(OSMIUM_RADIATION, Materials.Osmiridium.toCamelCaseString());
		RadSources.putMaterial(ZIRCONIUM_RADIATION, Materials.Zirconium.toCamelCaseString());
		RadSources.putMaterial(ZIRCON_RADIATION, Materials.Zircon.toCamelCaseString());

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.Vibranium), VIBRANIUM_RADIATION * 2);
	
		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.Darmstadtium), DARMSTADTIUM_RADIATION * 2);

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.Tritanium), TRITANIUM_RADIATION * 2);

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.Naquadah), NAQUADAH_RADIATION * 2);

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.NaquadahEnriched), NAQUADAHENRICHED_RADIATION * 2);

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.NaquadahAlloy), NAQUADAHALLOY_RADIATION * 2);

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.Duranium), DURANIUM_RADIATION * 2);

		RadSources.addToStackMap(MetaItems.SAW.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.HARD_HAMMER.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 6);
	    RadSources.addToStackMap(MetaItems.WRENCH.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.FILE.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.WIRE_CUTTER.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_LV.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_MV.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.WRENCH_HV.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 5);
	    RadSources.addToStackMap(MetaItems.BUZZSAW.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 2);
	    RadSources.addToStackMap(MetaItems.SCREWDRIVER_LV.getStackForm(Materials.Adamantine), ADAMANTINE_RADIATION * 2);
	}
}