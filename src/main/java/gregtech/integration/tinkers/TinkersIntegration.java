package gregtech.integration.tinkers;

import java.util.ArrayList;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

@Mod.EventBusSubscriber
public class TinkersIntegration {
	
    @Method(modid = "tconstruct")
	public static void preInit() {
    	registerTinkerMaterials();
    	registerTinkerAlloys();
	}
	
	@Method(modid = "tconstruct")
	public static void init() {
	}
	
	private static void registerTinkerMaterials() {
		for(Material m : Material.MATERIAL_REGISTRY) {		
			if(m instanceof IngotMaterial && ((IngotMaterial)m).blastFurnaceTemperature <= 0) {
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.ore, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreSand, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreNetherrack, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreGravel, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreEndstone, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
			}
			if(!ConfigHolder.overrideTiConStats && m instanceof IngotMaterial &&
					m != Materials.Iron && m != Materials.Cobalt && m != Materials.Copper && 
					m != Materials.Bronze && m != Materials.Lead && m != Materials.Electrum && 
					m != Materials.Silver && m != Materials.Steel && m != Materials.PigIron) {
				if(((SolidMaterial)m).toolDurability > 0) {
					slimeknights.tconstruct.library.materials.Material ticonMaterial = 
							new slimeknights.tconstruct.library.materials.Material(m.toString(), m.materialRGB)
							.setCastable(true).setCraftable(false);
					IngotMaterial gtMaterial = (IngotMaterial)m;
					registerIngotMaterialInfo(ticonMaterial, gtMaterial);
				}
				else {
					TinkerRegistry.integrate(((IngotMaterial)m).getMaterialFluid(), upperCase(m));
				}
			}
			else if(ConfigHolder.overrideTiConStats && m instanceof IngotMaterial){
				if(((SolidMaterial)m).toolDurability > 0) {
					slimeknights.tconstruct.library.materials.Material ticonMaterial = 
							new slimeknights.tconstruct.library.materials.Material(m.toString(), m.materialRGB)
							.setCastable(true).setCraftable(false);
					IngotMaterial gtMaterial = (IngotMaterial)m;
					registerIngotMaterialInfo(ticonMaterial, gtMaterial);
				}
				else {
					TinkerRegistry.integrate(((IngotMaterial)m).getMaterialFluid(), upperCase(m));
				}
			}
			else if(m instanceof GemMaterial) {
				slimeknights.tconstruct.library.materials.Material ticonMaterial = 
						new slimeknights.tconstruct.library.materials.Material(m.toString(), m.materialRGB)
						.setCastable(false).setCraftable(true);
				GemMaterial gtMaterial = (GemMaterial)m;
				
			}
			else if(m instanceof DustMaterial && !(m instanceof IngotMaterial)) {
                DustMaterial dust = (DustMaterial)m;
                if (dust.directSmelting != null) {
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.ore, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreSand, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreNetherrack, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreGravel, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreEndstone, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                } 
                else if (dust.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID) && m != Materials.Glass && m != Materials.Ice) {
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.dust, m).toString(), dust.getMaterialFluid(), 144);
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.dustSmall, m).toString(), dust.getMaterialFluid(), 36);
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.dustTiny, m).toString(), dust.getMaterialFluid(), 16);
                }
            }
		}
	}
	
	private static void registerTinkerAlloys() {		
        TinkerRegistry.registerAlloy(Materials.Brass.getFluid(4), Materials.Copper.getFluid(3), Materials.Zinc.getFluid(1));        
        TinkerRegistry.registerAlloy(Materials.Brass.getFluid(4), Materials.AnnealedCopper.getFluid(3), Materials.Zinc.getFluid(1));
        TinkerRegistry.registerAlloy(Materials.Cupronickel.getFluid(2), Materials.Copper.getFluid(1), Materials.Nickel.getFluid(1));       
        TinkerRegistry.registerAlloy(Materials.Cupronickel.getFluid(2), Materials.AnnealedCopper.getFluid(1), Materials.Nickel.getFluid(1));
        TinkerRegistry.registerAlloy(Materials.RedAlloy.getFluid(1), Materials.Copper.getFluid(1), Materials.Redstone.getFluid(4));
        TinkerRegistry.registerAlloy(Materials.Invar.getFluid(3), Materials.Iron.getFluid(1), Materials.Nickel.getFluid(1));
        TinkerRegistry.registerAlloy(Materials.BatteryAlloy.getFluid(5), Materials.Lead.getFluid(4), Materials.Antimony.getFluid(1));
        TinkerRegistry.registerAlloy(Materials.SolderingAlloy.getFluid(10), Materials.Tin.getFluid(9), Materials.Antimony.getFluid(1));
        TinkerRegistry.registerAlloy(Materials.Magnalium.getFluid(3), Materials.Aluminium.getFluid(2), Materials.Magnesium.getFluid(1));
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
    public static void smeltingRemoval(TinkerRegisterEvent.MeltingRegisterEvent event) {
    	for (Material material : Material.MATERIAL_REGISTRY) {
    		if (material instanceof IngotMaterial && ((IngotMaterial)material).blastFurnaceTemperature > 0 && OreDictUnifier.get(OrePrefix.ore, material).isEmpty()) {
    			event.setCanceled(true);
    		}
    	}
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void alloyRemoval(TinkerRegisterEvent.AlloyRegisterEvent event) {
        if (event.getRecipe().getResult() == Materials.Brass.getFluid(3))
            event.setCanceled(true);
    }
    
    private static void registerGemMaterialInfo(slimeknights.tconstruct.library.materials.Material ticonMaterial, GemMaterial gtMaterial) {
    	ticonMaterial.addCommonItems(upperCase(gtMaterial));
    	ticonMaterial.addItemIngot(new UnificationEntry(OrePrefix.gem, gtMaterial).toString());
    	ticonMaterial.setRepresentativeItem(OreDictUnifier.get(OrePrefix.gem, gtMaterial));
        TinkerRegistry.addMaterial(ticonMaterial);
        TinkerRegistry.addMaterialStats(ticonMaterial,
                new HeadMaterialStats(gtMaterial.toolDurability, gtMaterial.toolSpeed, gtMaterial.toolAttackDamage, gtMaterial.harvestLevel),
                new HandleMaterialStats(gtMaterial.harvestLevel - 0.5f, gtMaterial.toolDurability / 4),
                new ExtraMaterialStats(gtMaterial.toolDurability / 50));
        TinkerRegistry.integrate(ticonMaterial, upperCase(gtMaterial));
    }
    
    private static void registerIngotMaterialInfo(slimeknights.tconstruct.library.materials.Material ticonMaterial, IngotMaterial gtMaterial) {
    	ticonMaterial.addCommonItems(upperCase(gtMaterial));
        ticonMaterial.setFluid(gtMaterial.getMaterialFluid());
        ticonMaterial.addItemIngot(new UnificationEntry(OrePrefix.ingot, gtMaterial).toString());
        ticonMaterial.setRepresentativeItem(OreDictUnifier.get(OrePrefix.ingot, gtMaterial));
        TinkerRegistry.addMaterial(ticonMaterial);
        TinkerRegistry.addMaterialStats(ticonMaterial,
                new HeadMaterialStats((int)(gtMaterial.toolDurability * 0.8), gtMaterial.toolSpeed, gtMaterial.toolAttackDamage, gtMaterial.harvestLevel),
                new HandleMaterialStats((gtMaterial.harvestLevel - 0.5f) / 2, gtMaterial.toolDurability / 3),
                new ExtraMaterialStats(gtMaterial.toolDurability / 4));
        TinkerRegistry.integrate(ticonMaterial, ticonMaterial.getFluid(), upperCase(gtMaterial));
    }
    
    private static String upperCase(Material mat) {
        return mat.toCamelCaseString().substring(0, 1).toUpperCase() + mat.toCamelCaseString().substring(1);
    }
}
