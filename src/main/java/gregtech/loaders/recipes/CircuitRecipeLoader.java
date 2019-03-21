package gregtech.loaders.recipes;

import static gregtech.api.GTValues.L;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CircuitRecipeLoader {
	
	public static void init() {		
		//Board
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(400).EUt(12)
			.input(OrePrefix.dust, Materials.SiliconDioxide, 32)
			.input(OrePrefix.ring, Materials.Copper, 2)
			.outputs(MetaItems.BOARD_BASIC.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(200).EUt(12)
			.input(OrePrefix.dust, Materials.SiliconDioxide, 4)
			.input(OrePrefix.ring, Materials.Copper, 1)
			.input(OrePrefix.dustTiny, Materials.Silicon, 2)
			.outputs(MetaItems.BOARD_BASIC.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(80).EUt(12)
			.fluidInputs(Materials.Phenol.getFluid(1000))
			.notConsumable(MetaItems.SHAPE_MOLD_PLATE)
			.outputs(MetaItems.PHENOLIC_BOARD.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(250).EUt(12)
			.inputs(MetaItems.PHENOLIC_BOARD.getStackForm())
			.input(OrePrefix.ring, Materials.Brass, 2)
			.input(OrePrefix.dustTiny, Materials.Silicon, 10)
			.outputs(MetaItems.BOARD_INTERMEDIATE.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(270).EUt(12)
			.input(OrePrefix.plate, Materials.ReinforcedEpoxyResin)
			.input(OrePrefix.ring, Materials.Cupronickel, 2)
			.input(OrePrefix.dustTiny, Materials.Silicon, 10)
			.outputs(MetaItems.BOARD_ADVANCED.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(290).EUt(12)
			.input(OrePrefix.plate, Materials.Polyethylene, 2)
			.input(OrePrefix.ring, Materials.BerylliumCopper, 2)
			.input(OrePrefix.dustSmall, Materials.Silicon, 5)
			.outputs(MetaItems.BOARD_ELITE.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(310).EUt(12)
			.input(OrePrefix.plate, Materials.Epoxid, 2)
			.input(OrePrefix.ring, Materials.RoseGold, 2)
			.input(OrePrefix.dustSmall, Materials.Silicon, 6)
			.outputs(MetaItems.BOARD_MASTER.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(330).EUt(12)
			.input(OrePrefix.plate, Materials.Fiberglass, 4)
			.input(OrePrefix.ring, Materials.AluminiumCopper, 2)
			.input(OrePrefix.dustSmall, Materials.Silicon, 6)
			.outputs(MetaItems.BOARD_EXPERT.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.PRESS_RECIPES.recipeBuilder().duration(350).EUt(12)
			.input(OrePrefix.plate, Materials.DuraniumFusedAcrylic, 2)
			.input(OrePrefix.ring, Materials.Dymalloy, 2)
			.input(OrePrefix.dustSmall, Materials.Silicon, 6)
			.outputs(MetaItems.BOARD_ULTIMATE.getStackForm())
			.buildAndRegister();
		
		//SOC		
		ModHandler.addShapelessRecipe("topaz_soc", MetaItems.SOC_BASIC.getStackForm(), "h", new UnificationEntry(OrePrefix.gem, Materials.Topaz), new UnificationEntry(OrePrefix.gem, Materials.Topaz));
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(80).EUt(4)
			.input(OrePrefix.plate, Materials.Topaz, 2)
			.input(OrePrefix.dust, Materials.Redstone, 4)
			.outputs(MetaItems.SOC_BASIC.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(100).EUt(24)
			.input(OrePrefix.plate, Materials.EnderPearl, 8)
			.input(OrePrefix.dust, Materials.Redstone, 8)
			.outputs(MetaItems.SOC_INTERMEDIATE.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(120).EUt(48)
			.input(OrePrefix.plate, Materials.NetherQuartz, 8)
			.input(OrePrefix.dust, Materials.Redstone, 10)
			.outputs(MetaItems.SOC_ADVANCED.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(140).EUt(110)
			.input(OrePrefix.plate, Materials.Lapis, 8)
			.input(OrePrefix.dust, Materials.Redstone, 12)
			.outputs(MetaItems.SOC_ELITE.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(160).EUt(110)
			.input(OrePrefix.plate, Materials.Olivine, 2)
			.input(OrePrefix.dust, Materials.Redstone, 14)
			.outputs(MetaItems.SOC_MASTER.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(200).EUt(500)
			.input(OrePrefix.plate, Materials.Emerald, 2)
			.input(OrePrefix.dust, Materials.Redstone, 20)
			.outputs(MetaItems.SOC_EXPERT.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(300).EUt(3096)
			.input(OrePrefix.plate, Materials.Diamond, 2)
			.input(OrePrefix.dust, Materials.Redstone, 32)
			.outputs(MetaItems.SOC_ULTIMATE.getStackForm())
			.buildAndRegister();		
		
		//Wiring
		ModHandler.addShapedRecipe("red_alloy_circuit_wiring", MetaItems.WIRING_BASIC.getStackForm(), "CCC", "CxC", "CCC", 'C', new UnificationEntry(OrePrefix.wireGtSingle, Materials.RedAlloy));
		//ModHandler.addShapedRecipe("copper_circuit_wiring", MetaItems.WIRING_INTERMEDIATE.getStackForm(), "WWW", "WxW", "WWW", 'W', new UnificationEntry(OrePrefix.wireFine, Materials.Copper));
		//ModHandler.addShapedRecipe("annealed_copper_circuit_wiring", MetaItems.WIRING_INTERMEDIATE.getStackForm(), "WWW", "WxW", "WWW", 'W', new UnificationEntry(OrePrefix.wireFine, Materials.AnnealedCopper));

		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(180).EUt(4)
			.input(OrePrefix.foil, Materials.RedAlloy).input(OrePrefix.craftingLens, MarkerMaterials.Color.Red)
			.outputs(MetaItems.WIRING_BASIC.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(210).EUt(24)
			.input(OrePrefix.foil, Materials.Copper).input(OrePrefix.craftingLens, MarkerMaterials.Color.Orange)
			.outputs(MetaItems.WIRING_INTERMEDIATE.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(240).EUt(74)
			.input(OrePrefix.foil, Materials.Electrum).input(OrePrefix.craftingLens, MarkerMaterials.Color.Yellow)
			.outputs(MetaItems.WIRING_ADVANCED.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(270).EUt(240)
			.input(OrePrefix.foil, Materials.AluminiumCopper).input(OrePrefix.craftingLens, MarkerMaterials.Color.Cyan)
			.outputs(MetaItems.WIRING_ELITE.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(300).EUt(340)
			.input(OrePrefix.foil, Materials.Graphene).input(OrePrefix.craftingLens, MarkerMaterials.Color.Black)
			.outputs(MetaItems.WIRING_MASTER.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(330).EUt(512)
			.input(OrePrefix.foil, Materials.YttriumBariumCuprate).input(OrePrefix.craftingLens, MarkerMaterials.Color.Purple)
			.outputs(MetaItems.WIRING_EXPERT.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder().duration(330).EUt(1024)
			.input(OrePrefix.foil, Materials.Tritanium).input(OrePrefix.craftingLens, MarkerMaterials.Color.Blue)
			.outputs(MetaItems.WIRING_ULTIMATE.getStackForm())
			.buildAndRegister();
		
		//Circuits
        ModHandler.addShapelessRecipe("basic_to_configurable_circuit", MetaItems.INTEGRATED_CIRCUIT.getStackForm(), new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic));

        ModHandler.addShapedRecipe("vacuum_tube", MetaItems.CIRCUIT_VACUUM_TUBE_LV.getStackForm(), "PTP", "STS", "WWW", 'P', new ItemStack(Items.PAPER), 'T', MetaItems.GLASS_TUBE.getStackForm(), 'S', new ItemStack(Items.SLIME_BALL), 'W', OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper));
        ModHandler.addShapedRecipe("vacuum_tube_fine", MetaItems.CIRCUIT_VACUUM_TUBE_LV.getStackForm(), "PTP", "STS", "WWW", 'P', new ItemStack(Items.PAPER), 'T', MetaItems.GLASS_TUBE.getStackForm(), 'S', new ItemStack(Items.SLIME_BALL), 'W', OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper));

		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(8)
			.inputs(MetaItems.BOARD_BASIC.getStackForm(), MetaItems.SOC_BASIC.getStackForm(), MetaItems.WIRING_BASIC.getStackForm(2))
			.fluidInputs(Materials.Glue.getFluid(25), Materials.SolderingAlloy.getFluid(L))
			.outputs(MetaItems.CIRCUIT_BASIC_LV.getStackForm(4))
			.buildAndRegister();
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(16)
			.inputs(MetaItems.BOARD_BASIC.getStackForm(), MetaItems.SOC_BASIC.getStackForm(), 
					MetaItems.WIRING_BASIC.getStackForm(2), new ItemStack(Items.SLIME_BALL, 2))
			.outputs(MetaItems.CIRCUIT_BASIC_LV.getStackForm(4))
			.buildAndRegister();
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(24)
			.inputs(MetaItems.BOARD_INTERMEDIATE.getStackForm(), MetaItems.SOC_INTERMEDIATE.getStackForm(), MetaItems.WIRING_INTERMEDIATE.getStackForm(2))
			.fluidInputs(Materials.Glue.getFluid(10))
			.outputs(MetaItems.CIRCUIT_INTERMEDIATE_MV.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(24)
			.inputs(MetaItems.BOARD_ADVANCED.getStackForm(), MetaItems.SOC_ADVANCED.getStackForm(), 
					MetaItems.WIRING_ADVANCED.getStackForm(2), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 2))
			.fluidInputs(Materials.Glue.getFluid(50), Materials.SolderingAlloy.getFluid(L * 2))
			.outputs(MetaItems.CIRCUIT_ADVANCED_HV.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(24)
			.inputs(MetaItems.BOARD_ELITE.getStackForm(), MetaItems.SOC_ELITE.getStackForm(), 
					MetaItems.WIRING_ELITE.getStackForm(2), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 4))
			.fluidInputs(Materials.BisphenolA.getFluid(200), Materials.SolderingAlloy.getFluid(L * 3))
			.outputs(MetaItems.CIRCUIT_ELITE_EV.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(24)
			.inputs(MetaItems.BOARD_ELITE.getStackForm(), MetaItems.SOC_ELITE.getStackForm(), 
					MetaItems.WIRING_ELITE.getStackForm(2), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 4))
			.fluidInputs(Materials.PolyvinylAcetate.getFluid(20), Materials.SolderingAlloy.getFluid(L * 3))
			.outputs(MetaItems.CIRCUIT_ELITE_EV.getStackForm())
			.buildAndRegister();
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(24)
			.inputs(MetaItems.BOARD_MASTER.getStackForm(), MetaItems.SOC_MASTER.getStackForm(2), 
					MetaItems.WIRING_MASTER.getStackForm(4), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 4))
			.fluidInputs(Materials.BisphenolA.getFluid(400), Materials.SolderingAlloy.getFluid(L * 3))
			.outputs(MetaItems.CIRCUIT_MASTER_IV.getStackForm())
			.buildAndRegister();	
		
		RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(24)
			.inputs(MetaItems.BOARD_MASTER.getStackForm(), MetaItems.SOC_MASTER.getStackForm(2), 
					MetaItems.WIRING_MASTER.getStackForm(4), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 4))
			.fluidInputs(Materials.PolyvinylAcetate.getFluid(100), Materials.SolderingAlloy.getFluid(L * 3))
			.outputs(MetaItems.CIRCUIT_MASTER_IV.getStackForm())
			.buildAndRegister();	
		
		RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
        	.inputs(MetaItems.BOARD_EXPERT.getStackForm(2), MetaItems.SOC_EXPERT.getStackForm(4), 
        			MetaItems.WIRING_EXPERT.getStackForm(8), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 8))
        	.fluidInputs(Materials.SolderingAlloy.getFluid(L * 4), Materials.PolyvinylAcetate.getFluid(250))
        	.outputs(MetaItems.CIRCUIT_EXPERT_LuV.getStackForm())
        	.duration(1000)
        	.EUt(14440)
        	.buildAndRegister();

		RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
		.inputs(MetaItems.BOARD_ULTIMATE.getStackForm(4), MetaItems.SOC_ULTIMATE.getStackForm(8), 
				MetaItems.WIRING_ULTIMATE.getStackForm(10), OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate, 8))
        	.fluidInputs(Materials.SolderingAlloy.getFluid(L * 9), Materials.PolyvinylAcetate.getFluid(1000))
        	.outputs(MetaItems.CIRCUIT_ULTIMATE_UV.getStackForm())
        	.duration(1000)
        	.EUt(57770)
        	.buildAndRegister();
	}
}