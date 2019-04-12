package gregtech.integration.tinkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import gregtech.GregTechMod;
import gregtech.api.GTValues;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.common.ConfigHolder;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;

public class TinkersIntegration {
	
	public static void preInit() {
    	GTLog.logger.info("Registering Tinker's Construct Support");
        try {
        	registerTinkerMaterials();
        	registerTinkerAlloys();
        } catch(NoSuchMethodError ex) {
        	GTLog.logger.warn("Failed to load TConstruct module. Are Tinkers tools disabled?");
            ex.printStackTrace();
        } catch(Exception ex) {
        	GTLog.logger.error("Unknown error while loading TConstruct module.");
            ex.printStackTrace();
        }
    }

	private static void registerTinkerMaterials() {
		for(Material m : Material.MATERIAL_REGISTRY) {
			if(!(m instanceof SolidMaterial)) {
				continue;
			}			
			SolidMaterial material = (SolidMaterial)m;
			if(m instanceof IngotMaterial && ((IngotMaterial)m).blastFurnaceTemperature <= 0) {
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.ore, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreSand, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreNetherrack, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreGravel, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
                TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreEndstone, m).toString(), ((IngotMaterial)m).getMaterialFluid(), (int)(144 * ((IngotMaterial)m).smeltingMultiplier * Config.oreToIngotRatio));
			}
			if((m instanceof IngotMaterial || m instanceof GemMaterial) && toGenerate(material)) {
				if(material.toolDurability > 0) {
					slimeknights.tconstruct.library.materials.Material ticonMaterial = 
							new slimeknights.tconstruct.library.materials.Material(m.toString(), m.materialRGB);
					registerMaterialInfo(ticonMaterial, material);
				}
				else {
					//TinkerRegistry.integrate(((FluidMaterial)m).getMaterialFluid(), upperCase(m));
				}
			}
			else if(material instanceof DustMaterial) {
				DustMaterial dust = (DustMaterial)m;
                if(dust.hasFlag(DustMaterial.MatFlags.GENERATE_ORE) && dust.directSmelting != null) {
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.ore, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreSand, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreNetherrack, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreGravel, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                    TinkerRegistry.registerMelting(new UnificationEntry(OrePrefix.oreEndstone, m).toString(), dust.directSmelting.getMaterialFluid(), (int) (144 * dust.smeltingMultiplier * Config.oreToIngotRatio));
                } 
                else if(dust.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID) && m != Materials.Glass && m != Materials.Ice) {
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
    
    private static void registerMaterialInfo(slimeknights.tconstruct.library.materials.Material ticonMaterial, SolidMaterial gtMaterial) {   	
    	ItemStack stack = gtMaterial instanceof IngotMaterial ? 
    				OreDictUnifier.get(OrePrefix.ingot, gtMaterial) : 
    				OreDictUnifier.get(OrePrefix.gem, gtMaterial);
    	ticonMaterial.addItem(stack, 1, ticonMaterial.VALUE_Ingot);
    	ticonMaterial.addCommonItems(upperCase(gtMaterial));
        ticonMaterial.setFluid(gtMaterial.getMaterialFluid());
        ticonMaterial.setRepresentativeItem(gtMaterial instanceof IngotMaterial ? //shall we use string instead of ItemStack?
        			OreDictUnifier.get(OrePrefix.ingot, gtMaterial) :
        			OreDictUnifier.get(OrePrefix.gem, gtMaterial));
        float drawDelay = Math.max(38.4F - 1.4F *  gtMaterial.toolSpeed, 10);
        TinkerRegistry.addMaterialStats(ticonMaterial,
                new HeadMaterialStats((int)(gtMaterial.toolDurability * 0.8D), gtMaterial.toolSpeed + 1F, gtMaterial.toolAttackDamage + 1F, gtMaterial.harvestLevel), //harvestLevel can equal 0, may have unintended side effects
                new HandleMaterialStats(gtMaterial.harvestLevel * 0.824F, gtMaterial.toolDurability < 140 ? -30 : gtMaterial.toolDurability / 7),
                new ExtraMaterialStats(gtMaterial.harvestLevel != 0 ? (int)(gtMaterial.toolDurability * 0.3D) : (int)(gtMaterial.toolDurability * 0.1D)),
        		new BowMaterialStats(20F / drawDelay, (float)(gtMaterial.harvestLevel * 0.74), 0.4F * gtMaterial.toolAttackDamage - 1F));
        addTinkersTrait(ticonMaterial, gtMaterial);
        MaterialIntegration materialIntegration = new MaterialIntegration(ticonMaterial, gtMaterial.getMaterialFluid(), upperCase(gtMaterial));
    	if(OreDictUnifier.get(OrePrefix.block, gtMaterial) != null) {
    		materialIntegration.toolforge();
    		TinkerRegistry.integrate(materialIntegration).preInit();
    	}    	
    	ticonMaterial.setCastable(true);   
    	ticonMaterial.setCraftable(false); 
    }
    
    private static void addTinkersTrait(slimeknights.tconstruct.library.materials.Material ticonMaterial, SolidMaterial gtMaterial) {
    	 if(gtMaterial == Materials.Osmium || gtMaterial == Materials.Osmiridium) {
         	ticonMaterial.addTrait(TraitRadioactive.instance);
         }
         else if(gtMaterial == Materials.Tritanium || gtMaterial == Materials.Naquadah || gtMaterial == Materials.NaquadahAlloy || gtMaterial == Materials.NaquadahEnriched) {
         	ticonMaterial.addTrait(TraitRadioactive.instance2);
         }
         else if(gtMaterial == Materials.Duranium  || gtMaterial == Materials.Vibranium || gtMaterial == Materials.Adamantine) {
         	ticonMaterial.addTrait(TraitRadioactive.instance3);
         }
         else if(gtMaterial == Materials.Darmstadtium) {
         	ticonMaterial.addTrait(TraitRadioactive.instance4);
         }
    }
    
    private static boolean toGenerate(SolidMaterial m) {
    	for(MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
			if(integration != null && integration.material != null) {
				if(integration.material.getIdentifier() == m.toString()) { 
					return false;
				}
			}
		}
    	if(TinkerRegistry.getMaterial(m.toString()) != slimeknights.tconstruct.library.materials.Material.UNKNOWN) {
    		return false;
    	}
    	else if(m == Materials.Emerald || m == Materials.Diamond || m == Materials.PigIron) {
    		return false;
    	}
    	else {
    		return true;
    	}
    	/*boolean plustic = GTValues.isModLoaded("plustic"); 	
    	if(plustic) {
			return m == Materials.Nickel || m == Materials.Invar || m == Materials.Iridium;
    	}
    	else if(plustic && GTValues.isModLoaded("advancedrocketry")) {
    		return m == Materials.Titanium;
    	}
    	else if((plustic || GTValues.isModLoaded("pewter")) && GTValues.isModLoaded("mekanism")) {
    		return m == Materials.Osmium || m == Materials.Osmiridium;
    	}
    	else if(plustic && GTValues.isModLoaded("thermalfoundation")) {
    		return m == Materials.Platinum;
    	}
    	else if(plustic && GTValues.isModLoaded("landcore")) {
    		return m == Materials.Tungsten && m == Materials.Thorium;
    	}
    	else if(GTValues.isModLoaded("nuclearcraft")) {
    		return m == Materials.Uranium && m == Materials.Uranium235 && m == Materials.Thorium;
    	}
    	return m == Materials.Iron || m == Materials.Cobalt || m == Materials.Copper ||
			   m == Materials.Bronze || m == Materials.Lead || m == Materials.Electrum || 
			   m == Materials.Silver || m == Materials.Steel || m == Materials.PigIron;*/
    }
    
    private static String upperCase(Material mat) {
        return mat.toCamelCaseString().substring(0, 1).toUpperCase() + mat.toCamelCaseString().substring(1);
    }
}
