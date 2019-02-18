package gregtech.loaders.recipes;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.CokeOvenRecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockConcrete.ConcreteVariant;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneBlock;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import gregtech.common.items.MetaItems;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Tuple;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipeLoader {
	
	public static void init() {
		
		//TODO: PBF RECIPES
		
		initializeWoodRecipes();
		initializeArcRecyclingRecipes();
		initializeAlloySmeltingRecipes();
		
		/**
		 * Alloy Smelting Recipes
		 */
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(100).EUt(16)
        		  .input(OrePrefix.ingot, Materials.Rubber, 2)
        		  .input(OrePrefix.wireGtSingle, Materials.Copper, 1)
        		  .outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper,1))
        		  .buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(320).EUt(96)
		          .input(OrePrefix.dust, Materials.Ruby, 9)
		          .input(OrePrefix.dust, Materials.Redstone, 9)
		          .outputs(MetaItems.ENERGIUM_DUST.getStackForm(9))
		          .buildAndRegister();

		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().inputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Diamond, 5), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 5)).outputs(MetaItems.ENERGIUM_DUST.getStackForm(9)).EUt(460).duration(60).buildAndRegister();
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().inputs(MetaItems.FUEL_BINDER.getStackForm(4), MetaItems.ENERGIUM_DUST.getStackForm()).outputs(MetaItems.SUPER_FUEL_BINDER.getStackForm()).duration(1000).EUt(112).buildAndRegister();

		/**
		 * Assembler Recipes
		 */
		
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LEVER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_CONTROLLER.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_ACTIVITY_DETECTOR.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_FLUID_DETECTOR.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_ITEM_DETECTOR.getStackForm()).buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(30).input(OrePrefix.dust, Materials.EnderPearl, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Basic, 4).fluidInputs(Materials.Osmium.getFluid(L * 2)).outputs(MetaItems.FIELD_GENERATOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(120).input(OrePrefix.dust, Materials.EnderEye, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Good, 4).fluidInputs(Materials.Osmium.getFluid(576)).outputs(MetaItems.FIELD_GENERATOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(480).inputs(MetaItems.QUANTUM_EYE.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 4).fluidInputs(Materials.Osmium.getFluid(1152)).outputs(MetaItems.FIELD_GENERATOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(1920).input(OrePrefix.dust, Materials.NetherStar, 1).input(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 4).fluidInputs(Materials.Osmium.getFluid(2304)).outputs(MetaItems.FIELD_GENERATOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(1800).EUt(7680).inputs(MetaItems.QUANTUM_STAR.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Master, 4).fluidInputs(Materials.Osmium.getFluid(4608)).outputs(MetaItems.FIELD_GENERATOR_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 2, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.dust, Materials.Redstone, 1).fluidInputs(Materials.Concrete.getFluid(144)).outputs(new ItemStack(Items.REPEATER)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.LEAD, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(50)).outputs(new ItemStack(Items.NAME_TAG)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.COMPASS, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 8).outputs(new ItemStack(Items.MAP)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2048).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm(8)).input(OrePrefix.plate, Materials.Europium, 4).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32768).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm(8)).input(OrePrefix.plate, Materials.Darmstadtium, 16).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB3.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 1).fluidInputs(Materials.Creosote.getFluid(1000)).outputs(new ItemStack(Blocks.TORCH, 6)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(100)).circuitMeta(1).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 3).fluidInputs(Materials.Glue.getFluid(20)).outputs(new ItemStack(Items.BOOK)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        		  .input(OrePrefix.plate, Materials.Aluminium, 2)
        		  .input(OrePrefix.foil, Materials.Zinc, 16)
        		  .fluidInputs(Materials.Plastic.getFluid(144))
        		  .outputs(MetaItems.ITEM_FILTER.getStackForm())
        		  .duration(100).EUt(32)
        		  .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        		  .input(OrePrefix.plate, Materials.Emerald, 2)
        		  .input(OrePrefix.foil, Materials.Zinc, 16)
        		  .fluidInputs(Materials.Plastic.getFluid(144))
        		  .outputs(MetaItems.ORE_DICTIONARY_FILTER.getStackForm())
        		  .duration(100).EUt(32)
        		  .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        		  .input(OrePrefix.plate, Materials.Lapis, 2)
        		  .input(OrePrefix.foil, Materials.Zinc, 16)
        		  .fluidInputs(Materials.Plastic.getFluid(144))
        		  .outputs(MetaItems.FLUID_FILTER.getStackForm())
        		  .duration(100).EUt(32)
        		  .buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        		  .input(OrePrefix.plate, Materials.Rubber, 2)
        		  .input(OrePrefix.plate, Materials.Aluminium, 2)
        		  .outputs(MetaItems.COVER_SHUTTER.getStackForm(4))
        		  .EUt(16).duration(800)
        		  .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(480).EUt(240).input(OrePrefix.dust, Materials.Graphite, 8).input(OrePrefix.foil, Materials.Silicon, 1).fluidInputs(Materials.Glue.getFluid(250)).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Graphene, 2)).buildAndRegister();

        for(IngotMaterial cableMaterial : new IngotMaterial[] {Materials.YttriumBariumCuprate, Materials.NiobiumTitanium, Materials.VanadiumGallium}) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtSingle, cableMaterial, 3)
                .input(OrePrefix.plate, Materials.TungstenSteel, 3)
                .inputs(MetaItems.ELECTRIC_PUMP_LV.getStackForm())
                .fluidInputs(Materials.Nitrogen.getFluid(2000))
                .outputs(OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 3))
                .duration(20).EUt(512)
                .buildAndRegister();
        }
        
        /**
         * Canner Recipes
         */
        
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(MetaItems.BATTERY_HULL_LV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(1000)).outputs(MetaItems.BATTERY_SU_LV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(MetaItems.BATTERY_HULL_MV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(4000)).outputs(MetaItems.BATTERY_SU_MV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(MetaItems.BATTERY_HULL_HV.getStackForm()).fluidInputs(Materials.Mercury.getFluid(16000)).outputs(MetaItems.BATTERY_SU_HV_MERCURY.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(100).EUt(30).inputs(MetaItems.BATTERY_HULL_LV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(1000)).outputs(MetaItems.BATTERY_SU_LV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(200).EUt(30).inputs(MetaItems.BATTERY_HULL_MV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(4000)).outputs(MetaItems.BATTERY_SU_MV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder().duration(400).EUt(30).inputs(MetaItems.BATTERY_HULL_HV.getStackForm()).fluidInputs(Materials.SulfuricAcid.getFluid(16000)).outputs(MetaItems.BATTERY_SU_HV_SULFURIC_ACID.getChargedStack(Long.MAX_VALUE)).buildAndRegister();
     
        /**
         * Centrifuging Recipes
         */
        
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(1600).EUt(8).fluidInputs(Materials.Air.getFluid(10000)).fluidOutputs(Materials.Nitrogen.getFluid(3900), Materials.Oxygen.getFluid(1000)).buildAndRegister();

        /**
         * Chemical Reactor Recipes
         */
        
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(950).EUt(30).fluidInputs(Materials.Water.getFluid(2000), Materials.NitrogenDioxide.getFluid(4000), Materials.Oxygen.getFluid(1000)).fluidOutputs(Materials.NitricAcid.getFluid(4000)).buildAndRegister();

        /**
         * Chemical Bath Recipes
         */
        
        //Misc
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Paper, 1).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.REEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).fluidInputs(ModHandler.getDistilledWater(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Paper, 1).fluidInputs(ModHandler.getDistilledWater(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.REEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(ModHandler.getDistilledWater(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.WOOL)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(25)).outputs(new ItemStack(Blocks.CARPET)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.HARDENED_CLAY)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_GLASS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(20)).outputs(new ItemStack(Blocks.GLASS_PANE)).buildAndRegister();

		/**
		 * Coke Oven Recipes
		 */
		CokeOvenRecipeBuilder.start().input(OrePrefix.log, Materials.Wood).output(OreDictUnifier.get(OrePrefix.gem, Materials.Charcoal)).fluidOutput(Materials.Creosote.getFluid(250)).duration(900).buildAndRegister();
		CokeOvenRecipeBuilder.start().input(OrePrefix.gem, Materials.Coal).output(OreDictUnifier.get(OrePrefix.gem, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(500)).duration(900).buildAndRegister();
		CokeOvenRecipeBuilder.start().input(OrePrefix.block, Materials.Coal).output(OreDictUnifier.get(OrePrefix.block, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(4500)).duration(8000).buildAndRegister();

        /**
         * Compressor Recipes
         */
		
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.ICE, 2, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.PACKED_ICE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Ice, 1).outputs(new ItemStack(Blocks.ICE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.QUARTZ, 4)).outputs(new ItemStack(Blocks.QUARTZ_BLOCK)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.WHEAT, 9)).outputs(new ItemStack(Blocks.HAY_BLOCK)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
        		  .input(OrePrefix.dust, Materials.Fireclay)
        		  .outputs(MetaItems.COMPRESSED_FIRECLAY.getStackForm())
        		  .duration(100).EUt(2)
        		  .buildAndRegister();
        
        /**
         * Cutter Recipes
         */
        
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
        		  .inputs(MetaItems.ENERGIUM_DUST.getStackForm(9))
        		  .fluidInputs(Materials.Lubricant.getFluid(20))
        		  .outputs(MetaItems.ENERGY_CRYSTAL.getStackForm())
        		  .duration(2000).EUt(96)
        		  .buildAndRegister();
        
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(72000).EUt(480).input(OrePrefix.dust, Materials.NetherStar, 1).fluidInputs(Materials.Lubricant.getFluid(200)).chancedOutput(OreDictUnifier.get(OrePrefix.gem,Materials.NetherStar, 1), 5000).buildAndRegister();

        /**
         * Extraction Recipes
         */
        

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Items.COAL, 1, 1)).chancedOutput(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1), 1000).fluidOutputs(Materials.Creosote.getFluid(100)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(4).inputs(new ItemStack(Items.SNOWBALL)).fluidOutputs(Materials.Water.getFluid(250)).buildAndRegister();
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Blocks.SNOW)).fluidOutputs(Materials.Water.getFluid(1000)).buildAndRegister();

        
		/**
		 * Mixer Recipes
		 */
        
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
        		  .duration(64).EUt(16)
        		  .fluidInputs(Materials.Water.getFluid(1000))
        		  .input("sand", 4)
        		  .input(OrePrefix.dust, Materials.Stone, 6)
        		  .input(OrePrefix.dust, Materials.Flint)
        		  .fluidOutputs(Materials.ConstructionFoam.getFluid(1000))
        		  .buildAndRegister();
		
		//Fuel Binders
		RecipeMaps.MIXER_RECIPES.recipeBuilder().inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 8), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur)).fluidInputs(Materials.Creosote.getFluid(1000)).outputs(MetaItems.FUEL_BINDER.getStackForm(4)).duration(1400).EUt(16).buildAndRegister();
		RecipeMaps.MIXER_RECIPES.recipeBuilder().inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 8), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur)).fluidInputs(Materials.Creosote.getFluid(1000)).outputs(MetaItems.FUEL_BINDER.getStackForm(4)).duration(1400).EUt(16).buildAndRegister();
		RecipeMaps.MIXER_RECIPES.recipeBuilder().inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 8), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur)).fluidInputs(Materials.Lubricant.getFluid(300)).outputs(MetaItems.FUEL_BINDER.getStackForm(4)).duration(800).EUt(16).buildAndRegister();
		RecipeMaps.MIXER_RECIPES.recipeBuilder().inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 8), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur)).fluidInputs(Materials.Lubricant.getFluid(300)).outputs(MetaItems.FUEL_BINDER.getStackForm(4)).duration(800).EUt(16).buildAndRegister();
		
		//Drilling Fluid
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(48).EUt(16).input(OrePrefix.dust, Materials.Barite, 2).fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(2000)).fluidOutputs(Materials.DrillingFluid.getFluid(2500)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(110).EUt(16).input(OrePrefix.dust, Materials.Hematite, 4).fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(2000)).fluidOutputs(Materials.DrillingFluid.getFluid(2500)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(110).EUt(16).input(OrePrefix.dust, Materials.Calcite, 2).fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(2000)).fluidOutputs(Materials.DrillingFluid.getFluid(2500)).buildAndRegister();

		//Misc
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.SPIDER_EYE)).input(OrePrefix.dust, Materials.Sugar, 1).outputs(new ItemStack(Items.FERMENTED_SPIDER_EYE)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(16).EUt(16).fluidInputs(Materials.LightFuel.getFluid(5000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.Fuel.getFluid(6000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(20).EUt(16).input(OrePrefix.dust, Materials.Clay, 1).input(OrePrefix.dust, Materials.Stone, 3).fluidInputs(Materials.Water.getFluid(500)).fluidOutputs(Materials.Concrete.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(12).EUt(4).inputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.LIGHT_CONCRETE, ChiselingVariant.NORMAL)).fluidInputs(Materials.Water.getFluid(144)).outputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.DARK_CONCRETE, ChiselingVariant.NORMAL)).buildAndRegister();

		/**
		 * Fluid Extractor Recipes 
		 */
		
		List<Tuple<ItemStack, Integer>> seedEntries = GTUtility.getGrassSeedEntries();
        for(Tuple<ItemStack, Integer> seedEntry : seedEntries) {
            RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                	  .duration(32).EUt(2)
                	  .inputs(seedEntry.getFirst())
                	  .fluidOutputs(Materials.SeedOil.getFluid(10))
                	  .buildAndRegister();
        }
        
        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2)
        	      .inputs(new ItemStack(Items.MELON_SEEDS, 1, OreDictionary.WILDCARD_VALUE))
        	      .fluidOutputs(Materials.SeedOil.getFluid(12)).buildAndRegister();

        RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder().duration(32).EUt(2)
        	  	  .inputs(new ItemStack(Items.PUMPKIN_SEEDS, 1, OreDictionary.WILDCARD_VALUE))
        	  	  .fluidOutputs(Materials.SeedOil.getFluid(15)).buildAndRegister();
		
		/**
		 * Pyrolyse Oven Recipes
		 */
		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
        	.input(OrePrefix.gem, Materials.Coal, 16)
        	.circuitMeta(0)
        	.outputs(OreDictUnifier.get(OrePrefix.gem, Materials.Coke, 20))
        	.fluidOutputs(Materials.Creosote.getFluid(10000))
        	.duration(440).EUt(96)
        	.buildAndRegister();
		
		/**
		 * Laser Engraver Recipes
		 */
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
				  .inputs(new ItemStack(Blocks.SANDSTONE, 1, 2))
				  .notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
        		  .outputs(new ItemStack(Blocks.SANDSTONE, 1, 1))
        		  .duration(20).EUt(16)
        		  .buildAndRegister();

		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
		 		   .inputs(CountableIngredient.from("stone"))
		 		   .notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
                  .outputs(new ItemStack(Blocks.STONEBRICK, 1, 3))
                  .duration(50).EUt(16)
                  .buildAndRegister();

		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
				  .inputs(new ItemStack(Blocks.QUARTZ_BLOCK))
				  .notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
                  .outputs(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1))
                  .duration(50).EUt(16)
                  .buildAndRegister();
		
		/**
		 * Solidifying Recipes
		 */
		
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(Materials.Water.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BALL.getStackForm()).fluidInputs(ModHandler.getDistilledWater(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Water.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(ModHandler.getDistilledWater(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(1024).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Lava.getFluid(1000)).outputs(new ItemStack(Blocks.OBSIDIAN)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glowstone.getFluid(576)).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_PLATE.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(OreDictUnifier.get(OrePrefix.plate,Materials.Glass,1)).buildAndRegister();

		
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.LIGHT_CONCRETE, ConcreteVariant.LIGHT_BRICKS);
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.DARK_CONCRETE, ConcreteVariant.DARK_CONCRETE);
        registerChiselingRecipes(MetaBlocks.CONCRETE);
	}
	
	public static void initializeArcRecyclingRecipes() {
        for(Entry<ItemStack, ItemMaterialInfo> entry : OreDictUnifier.getAllItemInfos()) {
            ItemStack itemStack = entry.getKey();
            ItemMaterialInfo materialInfo = entry.getValue();
            ArrayList<MaterialStack> materialStacks = new ArrayList<>();
            materialStacks.add(materialInfo.material);
            materialStacks.addAll(materialInfo.additionalComponents);
            registerArcRecyclingRecipe(b -> b.inputs(itemStack), materialStacks, false);
        }
    }

    public static void registerArcRecyclingRecipe(Consumer<RecipeBuilder<?>> inputSupplier, List<MaterialStack> components, boolean ignoreArcSmelting) {
        List<MaterialStack> dustMaterials = components.stream()
            .filter(stack -> stack.material instanceof DustMaterial)
            .filter(stack -> stack.amount >= M / 9) //do only materials which have at least one nugget
            .collect(Collectors.toList());
        if(dustMaterials.isEmpty()) return;
        MaterialStack firstStack = dustMaterials.get(0);
        DustMaterial dustMaterial = (DustMaterial) firstStack.material;
        int voltageMultiplier = 1;
        if(dustMaterial instanceof IngotMaterial) {
            int blastFurnaceTemperature = ((IngotMaterial) dustMaterial).blastFurnaceTemperature;
            voltageMultiplier = blastFurnaceTemperature == 0 ? 1 : blastFurnaceTemperature > 2000 ? 16 : 4;
        } else {
            //do not apply arc smelting for gems, solid materials and dust materials
            //only generate recipes for ingot materials
            ignoreArcSmelting = true;
        }

        RecipeBuilder<?> maceratorRecipeBuilder = RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .outputs(dustMaterials.stream().map(OreDictUnifier::getDust).collect(Collectors.toList()))
            .duration((int) Math.max(1L, firstStack.amount * 30 / M))
            .EUt(8 * voltageMultiplier);
        inputSupplier.accept(maceratorRecipeBuilder);
        maceratorRecipeBuilder.buildAndRegister();

        if(dustMaterial.shouldGenerateFluid()) {
            RecipeBuilder<?> fluidExtractorRecipeBuilder = RecipeMaps.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                .fluidOutputs(dustMaterial.getFluid((int) (firstStack.amount * L / M)))
                .duration((int) Math.max(1L, firstStack.amount * 80 / M))
                .EUt(32 * voltageMultiplier);
            inputSupplier.accept(fluidExtractorRecipeBuilder);
            fluidExtractorRecipeBuilder.buildAndRegister();
        }

        if(!ignoreArcSmelting) {
            List<ItemStack> resultList = dustMaterials.stream().map(MachineRecipeLoader::getArcSmeltingResult).collect(Collectors.toList());
            resultList.removeIf(ItemStack::isEmpty);
            if(resultList.isEmpty()) return;
            RecipeBuilder<?> arcFurnaceRecipeBuilder = RecipeMaps.ARC_FURNACE_RECIPES.recipeBuilder()
                .outputs(resultList)
                .duration((int) Math.max(1L, firstStack.amount * 60 / M))
                .EUt(32 * voltageMultiplier);
            inputSupplier.accept(arcFurnaceRecipeBuilder);
            arcFurnaceRecipeBuilder.buildAndRegister();
        }
    }

    private static ItemStack getArcSmeltingResult(MaterialStack materialStack) {
        DustMaterial material = (DustMaterial) materialStack.material;
        long materialAmount = materialStack.amount;
        if(material.hasFlag(MatFlags.FLAMMABLE)) {
            return OreDictUnifier.getDust(Materials.Ash, materialAmount);
        } else if(material instanceof GemMaterial) {
            if(materialStack.material.materialComponents.stream()
                .anyMatch(stack -> stack.material == Materials.Oxygen)) {
                return OreDictUnifier.getDust(Materials.Ash, materialAmount);
            }
            if(materialStack.material.materialComponents.stream()
                .anyMatch(stack -> stack.material == Materials.Carbon)) {
                return OreDictUnifier.getDust(Materials.Carbon, materialAmount);
            }
            return OreDictUnifier.getDust(Materials.DarkAsh, materialAmount);
        } else if(material instanceof IngotMaterial) {
            IngotMaterial ingotMaterial = (IngotMaterial) material;
            if(ingotMaterial.arcSmeltInto != null)
                ingotMaterial = ingotMaterial.arcSmeltInto;
            return OreDictUnifier.getIngot(ingotMaterial, materialAmount);
        } else {
            return OreDictUnifier.getDust(material, materialAmount);
        }
    }

    public static void initializeWoodRecipes() {
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.log, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 6))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
            .buildAndRegister();

        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .input(OrePrefix.log, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.stickLong, Materials.Wood, 4),
                OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
            .duration(160).EUt(8)
            .buildAndRegister();

        List<ItemStack> allWoodLogs =  OreDictionary.getOres("logWood").stream()
            .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
            .collect(Collectors.toList());

        //Wood processing
        for (ItemStack stack : allWoodLogs) {
            ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
            if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
                int coalAmount = smeltingOutput.getCount();
                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(0)
                    .outputs(new ItemStack(Items.COAL, 24 * coalAmount, 1))
                    .fluidOutputs(Materials.Creosote.getFluid(5000 * coalAmount))
                    .duration(440)
                    .EUt(64)
                    .buildAndRegister();

                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(1)
                    .fluidInputs(Materials.Nitrogen.getFluid(400))
                    .outputs(new ItemStack(Items.COAL, 24, 1))
                    .fluidOutputs(Materials.Creosote.getFluid(4000))
                    .duration(200)
                    .EUt(96)
                    .buildAndRegister();

                RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(16, stack))
                    .circuitMeta(2)
                    .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 5))
                    .fluidOutputs(Materials.OilHeavy.getFluid(500 * coalAmount))
                    .duration(280)
                    .EUt(192)
                    .buildAndRegister();
            }

            Pair<IRecipe, ItemStack> outputPair = ModHandler.getRecipeOutput(null, stack);
            ItemStack output = outputPair.getValue();
            int originalOutput = output.getCount();
            if (!output.isEmpty()) {
                IRecipe outputRecipe = outputPair.getKey();
                GTLog.logger.info("Nerfing planks crafting recipe {} -> {}", stack, output);
                if(originalOutput / 2 > 0) {
                	ModHandler.addShapelessRecipe(outputRecipe.getRegistryName().toString(),
                    GTUtility.copyAmount(originalOutput / 2, output), stack);
                	} else {
                		ModHandler.removeRecipeByName(outputRecipe.getRegistryName());
                    }
                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .fluidInputs(Materials.Lubricant.getFluid(1))
                    .outputs(GTUtility.copyAmount((int) (originalOutput * 1.5), output),
                        OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                    .duration(200).EUt(8)
                    .buildAndRegister();

                ModHandler.addShapedRecipe(outputRecipe.getRegistryName().getResourcePath() + "_saw",
                    GTUtility.copyAmount(originalOutput, output), "s", "L", 'L', stack);
            }
        }
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2))
            .duration(10)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .input(OrePrefix.dust, Materials.Redstone)
            .outputs(new ItemStack(Blocks.NOTEBLOCK, 1))
            .duration(200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .input(OrePrefix.gem, Materials.Diamond)
            .outputs(new ItemStack(Blocks.JUKEBOX, 1))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 6)
            .inputs(new ItemStack(Items.BOOK, 3))
            .outputs(new ItemStack(Blocks.BOOKSHELF, 1))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood)
            .circuitMeta(1)
            .outputs(new ItemStack(Blocks.WOODEN_BUTTON, 1))
            .duration(100)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 2)
            .circuitMeta(2)
            .outputs(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE))
            .duration(200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 3)
            .circuitMeta(3)
            .outputs(new ItemStack(Blocks.TRAPDOOR, 2))
            .duration(300)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 4)
            .circuitMeta(4)
            .outputs(new ItemStack(Blocks.CRAFTING_TABLE))
            .duration(400).EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .outputs(new ItemStack(Blocks.CHEST, 1))
            .duration(800).EUt(4).circuitMeta(8)
            .buildAndRegister();

        List<ItemStack> allPlanksStacks = OreDictionary.getOres("plankWood").stream()
            .flatMap(stack -> ModHandler.getAllSubItems(stack).stream())
            .collect(Collectors.toList());
        HashSet<String> alreadyProcessedPlanks = new HashSet<>();
        for (ItemStack stack : allPlanksStacks) {
            ItemStack output = ModHandler.getRecipeOutput(null, stack, stack, stack).getValue();
            if (!output.isEmpty() && output.getCount() >= 3) {
                String recipeName = String.format("slab_%s_%s", stack.getUnlocalizedName(), stack.getMetadata());
                if(alreadyProcessedPlanks.contains(recipeName)) {
                    continue;
                }
                alreadyProcessedPlanks.add(recipeName);

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(GTUtility.copyAmount(output.getCount() / 3, output))
                    .duration(25).EUt(4)
                    .buildAndRegister();
                ModHandler.addShapedRecipe(recipeName,
                    GTUtility.copyAmount(output.getCount() / 3, output),
                    "sP", 'P', stack);
            }
        }
        registerPlanksCuttingRecipes();
    }
    
  //TODO move more stuff here and do it automatically
    private static void registerPlanksCuttingRecipes() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.OAK.getMetadata()))
            .outputs(new ItemStack(Items.OAK_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.SPRUCE.getMetadata()))
            .outputs(new ItemStack(Items.SPRUCE_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.BIRCH.getMetadata()))
            .outputs(new ItemStack(Items.BIRCH_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.JUNGLE.getMetadata()))
            .outputs(new ItemStack(Items.JUNGLE_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.ACACIA.getMetadata()))
            .outputs(new ItemStack(Items.ACACIA_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.DARK_OAK.getMetadata()))
            .outputs(new ItemStack(Items.DARK_OAK_DOOR, 3))
            .duration(600).EUt(4).circuitMeta(6)
            .buildAndRegister();
    }
    
    private static <T extends Enum<T> & IStringSerializable> void registerBrickRecipe(StoneBlock<T> stoneBlock, T normalVariant, T brickVariant) {
        ModHandler.addShapedRecipe(stoneBlock.getRegistryName().getResourceDomain() + "_" + normalVariant + "_bricks",
            stoneBlock.getItemVariant(brickVariant, ChiselingVariant.NORMAL, 4),
            "XX", "XX", 'X',
            stoneBlock.getItemVariant(normalVariant, ChiselingVariant.NORMAL));
    }

    private static <T extends Enum<T> & IStringSerializable> void registerChiselingRecipes(StoneBlock<T> stoneBlock) {
        for(T variant : stoneBlock.getVariantValues()) {
            boolean isBricksVariant = variant.getName().endsWith("_bricks");
            if(!isBricksVariant) {
                ModHandler.addSmeltingRecipe(stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED), stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL));
                RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder().duration(12).EUt(4)
                    .inputs(stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL))
                    .outputs(stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED))
                    .buildAndRegister();
            } else {
                ModHandler.addSmeltingRecipe(stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL), stoneBlock.getItemVariant(variant, ChiselingVariant.CRACKED));
            }
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(12).EUt(4)
                .inputs(stoneBlock.getItemVariant(variant, !isBricksVariant ? ChiselingVariant.CRACKED : ChiselingVariant.NORMAL))
                .fluidInputs(Materials.Water.getFluid(144))
                .outputs(stoneBlock.getItemVariant(variant, ChiselingVariant.MOSSY))
                .buildAndRegister();
            ModHandler.addShapelessRecipe(stoneBlock.getRegistryName().getResourcePath() + "_chiseling_" + variant,
                stoneBlock.getItemVariant(variant, ChiselingVariant.CHISELED),
                stoneBlock.getItemVariant(variant, ChiselingVariant.NORMAL));
        }
    }
    
    public static void initializeAlloySmeltingRecipes() {
    	
    	MaterialStack[][] alloySmelterList = {
    	        {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 4L)},
    	        {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 4L)},
    	        {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Cupronickel, 2L)},
    	        {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Redstone, 4L), new MaterialStack(Materials.RedAlloy, 1)},
    	        {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 4L)},
    	        {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 4L)},
    	        {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Cupronickel, 2L)},
    	        {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Redstone, 4L), new MaterialStack(Materials.RedAlloy, 1)},
    	        {new MaterialStack(Materials.Iron, 2L), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Invar, 3L)},
    	        {new MaterialStack(Materials.WroughtIron, 2L), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Invar, 3L)},
    	        {new MaterialStack(Materials.Tin, 9L), new MaterialStack(Materials.Antimony, 1), new MaterialStack(Materials.SolderingAlloy, 10L)},
    	        {new MaterialStack(Materials.Lead, 4L), new MaterialStack(Materials.Antimony, 1), new MaterialStack(Materials.BatteryAlloy, 5L)},
    	        {new MaterialStack(Materials.Gold, 1), new MaterialStack(Materials.Silver, 1), new MaterialStack(Materials.Electrum, 2L)},
    	{new MaterialStack(Materials.Magnesium, 1), new MaterialStack(Materials.Aluminium, 2L), new MaterialStack(Materials.Magnalium, 3L)}};
    	
    	for (OrePrefix prefix : Arrays.asList(OrePrefix.dust, OrePrefix.dustSmall, OrePrefix.dustTiny)) {
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (100 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.EnderPearl, 1).input(prefix, Materials.Blaze, 1).outputs(OreDictUnifier.getDust(Materials.EnderEye, 1 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (200 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Gold, 1).input(prefix, Materials.Silver, 1).outputs(OreDictUnifier.getDust(Materials.Electrum, 2 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 2).input(prefix, Materials.Nickel, 1).outputs(OreDictUnifier.getDust(Materials.Invar, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 4).input(prefix, Materials.Invar, 3).input(prefix, Materials.Manganese, 1).input(prefix, Materials.Chrome, 1).outputs(OreDictUnifier.getDust(Materials.StainlessSteel, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 1).input(prefix, Materials.Aluminium, 1).input(prefix, Materials.Chrome, 1).outputs(OreDictUnifier.getDust(Materials.Kanthal, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (600 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Barium, 2).input(prefix, Materials.Yttrium, 1).outputs(OreDictUnifier.getDust(Materials.YttriumBariumCuprate, 6 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Zinc, 1).outputs(OreDictUnifier.getDust(Materials.Brass, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Tin, 1).outputs(OreDictUnifier.getDust(Materials.Bronze, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (200 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 1).input(prefix, Materials.Nickel, 1).outputs(OreDictUnifier.getDust(Materials.Cupronickel, 2 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 1).input(prefix, Materials.Gold, 4).outputs(OreDictUnifier.getDust(Materials.RoseGold, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 1).input(prefix, Materials.Silver, 4).outputs(OreDictUnifier.getDust(Materials.SterlingSilver, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Bismuth, 1).input(prefix, Materials.Brass, 4).outputs(OreDictUnifier.getDust(Materials.BismuthBronze, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Cobalt, 5).input(prefix, Materials.Chrome, 2).input(prefix, Materials.Nickel, 1).input(prefix, Materials.Molybdenum, 1).outputs(OreDictUnifier.getDust(Materials.Ultimet, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Saltpeter, 2).input(prefix, Materials.Sulfur, 1).input(prefix, Materials.Coal, 1).outputs(OreDictUnifier.getDust(Materials.Gunpowder, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Saltpeter, 2).input(prefix, Materials.Sulfur, 1).input(prefix, Materials.Charcoal, 1).outputs(OreDictUnifier.getDust(Materials.Gunpowder, 3 * prefix.materialAmount)).buildAndRegister();
        }

        for (MaterialStack[] stack : alloySmelterList) {
            if (stack[0].material instanceof IngotMaterial) {
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration((int) stack[2].amount * 50).EUt(16)
                    .input(OrePrefix.ingot, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.dust, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
            }
            if (stack[1].material instanceof IngotMaterial) {
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration((int) stack[2].amount * 50).EUt(16)
                    .input(OrePrefix.dust, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.ingot, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
            }
            if (stack[0].material instanceof IngotMaterial && stack[1].material instanceof IngotMaterial) {
                RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration((int) stack[2].amount * 50).EUt(16)
                    .input(OrePrefix.ingot, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.ingot, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
            }
            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .duration((int) stack[2].amount * 50).EUt(16)
                .input(OrePrefix.dust, stack[0].material, (int) stack[0].amount)
                .input(OrePrefix.dust, stack[1].material, (int) stack[1].amount)
                .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                .buildAndRegister();
        }
    }
}