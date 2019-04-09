package gregtech.integration.tinkers;

import gregtech.api.GTValues;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;

public class TinkersEvents {
	
	@Method(modid = "tconstruct")
	@SubscribeEvent(priority = EventPriority.HIGH)
    public void smeltingRemoval(TinkerRegisterEvent.MeltingRegisterEvent event) {
		if(GTValues.isModLoaded("tconstruct")) {
			for(Material material : Material.MATERIAL_REGISTRY) {
	    		if(material instanceof IngotMaterial && ((IngotMaterial)material).blastFurnaceTemperature > 0 && OreDictUnifier.get(OrePrefix.ore, material).isEmpty()) {
	    			event.setCanceled(true);
	    		}
	    	}
		} 	
    }
}
