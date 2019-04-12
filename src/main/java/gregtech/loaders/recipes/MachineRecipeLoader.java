package gregtech.loaders.recipes;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.B;
import static gregtech.api.GTValues.M;
import static gregtech.common.items.MetaItems.BATTERY_HULL_HV;
import static gregtech.common.items.MetaItems.BATTERY_HULL_LV;
import static gregtech.common.items.MetaItems.BATTERY_HULL_MV;
import static gregtech.common.items.MetaItems.BATTERY_RE_HV_CADMIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_HV_LITHIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_HV_SODIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_LV_CADMIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_LV_LITHIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_LV_SODIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_MV_CADMIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_MV_LITHIUM;
import static gregtech.common.items.MetaItems.BATTERY_RE_MV_SODIUM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import forestry.core.fluids.Fluids;
import gregtech.api.GTValues;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.CokeOvenRecipeBuilder;
import gregtech.api.recipes.builders.PBFRecipeBuilder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockConcrete.ConcreteVariant;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneBlock;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import gregtech.common.blocks.wood.BlockRubberLog.LogVariant;
import gregtech.common.items.ItemCell;
import gregtech.common.items.MetaItems;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipeLoader {
	
	public static void init() {				
		initializeAssemblingRecipes();
		initializeAlloyingRecipes();
		initializeBatteryRecipes();
		initializeBendingRecipes();
		initializeBlastRecipes();
		initializeCentrifugingRecipes();
		initializeCokeOvenRecipes();
		initializeCompressingRecipes();	
		initializeElectrolyzingRecipes();
		initializeExtractingRecipes();
		initializeExtrudingRecipes();
		initializeForestrySupport();
		initializeFusionRecipes();
		initializeMiscRecipes();
		initializeMixingRecipes();
		initializePBFRecipes();
		initializePlasmaArcRecipes();
		initializePyrolyseRecipes();
		initializeStoneBricksRecipes();
	}
	
	private static void initializeAssemblingRecipes() {
		
		for(CoilType type : CoilType.values()) {
            if(type.getMaterial() != null) {
                ItemStack outputStack = MetaBlocks.WIRE_COIL.getItemVariant(type);
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(8)
                    .input(OrePrefix.wireGtDouble, type.getMaterial(), 8)
                    .outputs(outputStack)
                    .duration(50).EUt(16).buildAndRegister();
            }
		}
		
		/*for (Material m : IngotMaterial.MATERIAL_REGISTRY) {
			MaterialStack[] cableFluids = {
		            						new MaterialStack(Materials.Rubber, 144),
		            						new MaterialStack(Materials.StyreneButadieneRubber, 108),
		            						new MaterialStack(Materials.SiliconeRubber, 72)
										  };
			MaterialStack[] cableDusts =  {
		            						new MaterialStack(Materials.Polydimethylsiloxane, 1),
		            						new MaterialStack(Materials.PolyvinylChloride, 1)
										  };
			if (m instanceof IngotMaterial && !OreDictUnifier.get(OrePrefix.cableGtSingle, m).isEmpty() && m != Materials.RedAlloy && m != Materials.Cobalt && m != Materials.Zinc && m != Materials.SolderingAlloy && m != Materials.Tin && m != Materials.Lead) {
				for (MaterialStack stackFluid : cableFluids) {
					IngotMaterial fluid = (IngotMaterial) stackFluid.material;
					int multiplier = (int) stackFluid.amount;
					if (m == Materials.Tungsten || m == Materials.Osmium || m == Materials.Platinum || m == Materials.TungstenSteel || m == Materials.Graphene || m == Materials.VanadiumGallium || m == Materials.HSSG || m == Materials.YttriumBariumCuprate || m == Materials.NiobiumTitanium || m == Materials.Naquadah || m == Materials.NiobiumTitanium || m == Materials.NaquadahEnriched || m == Materials.NaquadahAlloy) {
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.foil, m)).fluidInputs(fluid.getFluid(multiplier)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.foil, m, 2)).fluidInputs(fluid.getFluid(multiplier * 2)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.foil, m, 4)).fluidInputs(fluid.getFluid(multiplier * 4)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.foil, m, 8)).fluidInputs(fluid.getFluid(multiplier * 8)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.foil, m, 16)).fluidInputs(fluid.getFluid(multiplier * 16)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
						for (MaterialStack stackDust : cableDusts) {
							Material dust = stackDust.material;
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.foil, m), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.foil, m, 2), OreDictUnifier.get(OrePrefix.dustSmall, dust, 2)).fluidInputs(fluid.getFluid(multiplier)).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.foil, m, 4), OreDictUnifier.get(OrePrefix.dustSmall, dust, 4)).fluidInputs(fluid.getFluid(multiplier * 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.foil, m, 8), OreDictUnifier.get(OrePrefix.dustSmall, dust, 8)).fluidInputs(fluid.getFluid(multiplier * 4)).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.foil, m, 16), OreDictUnifier.get(OrePrefix.dustSmall, dust, 16)).fluidInputs(fluid.getFluid(multiplier * 8)).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
						}
					} 
					else {
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m)).fluidInputs(fluid.getFluid(multiplier)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m)).fluidInputs(fluid.getFluid(multiplier * 2)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m)).fluidInputs(fluid.getFluid(multiplier * 4)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m)).fluidInputs(fluid.getFluid(multiplier * 8)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
						RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m)).fluidInputs(fluid.getFluid(multiplier * 16)).circuitMeta(24).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
						for (MaterialStack stackDust : cableDusts) {
							Material dust = stackDust.material;
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtSingle, m), OreDictUnifier.get(OrePrefix.dustSmall, dust)).fluidInputs(fluid.getFluid(multiplier / 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtDouble, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 2)).fluidInputs(fluid.getFluid(multiplier)).outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtQuadruple, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 4)).fluidInputs(fluid.getFluid(multiplier * 2)).outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtOctal, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 8)).fluidInputs(fluid.getFluid(multiplier * 4)).outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, m)).buildAndRegister();
							RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(150).EUt(8).inputs(OreDictUnifier.get(OrePrefix.wireGtHex, m), OreDictUnifier.get(OrePrefix.dustSmall, dust, 16)).fluidInputs(fluid.getFluid(multiplier * 8)).outputs(OreDictUnifier.get(OrePrefix.cableGtHex, m)).buildAndRegister();
						}
					}
				}
			}
		}*/
		
		//Circuit Shit
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(MetaItems.GLASS_TUBE.getStackForm(), OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper, 2), new ItemStack(Items.PAPER, 2)).outputs(MetaItems.CIRCUIT_VACUUM_TUBE_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(120).EUt(8).inputs(MetaItems.GLASS_TUBE.getStackForm(), OreDictUnifier.get(OrePrefix.wireFine, Materials.Copper, 2), new ItemStack(Items.PAPER, 2)).outputs(MetaItems.CIRCUIT_VACUUM_TUBE_LV.getStackForm()).buildAndRegister();
		
        //Automatic Machine Component Recipes
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(480).inputs(CountableIngredient.from(OrePrefix.circuit, Tier.Advanced, 4), CountableIngredient.from(MetaItems.QUANTUM_EYE.getStackForm())).fluidInputs(Materials.Osmium.getFluid(1152)).outputs(MetaItems.FIELD_GENERATOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(1920).inputs(CountableIngredient.from(OrePrefix.circuit, Tier.Elite, 4), CountableIngredient.from(OrePrefix.dust, Materials.NetherStar)).fluidInputs(Materials.Osmium.getFluid(2304)).outputs(MetaItems.FIELD_GENERATOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(7680).inputs(CountableIngredient.from(OrePrefix.circuit, Tier.Master, 4), CountableIngredient.from(MetaItems.QUANTUM_STAR.getStackForm())).fluidInputs(Materials.Osmium.getFluid(4608)).outputs(MetaItems.FIELD_GENERATOR_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(10).inputs(CountableIngredient.from(OrePrefix.cableGtSingle, Materials.Tin, 2), CountableIngredient.from(OrePrefix.stick, Materials.Iron, 2), CountableIngredient.from(OrePrefix.stick, Materials.IronMagnetic)).fluidInputs(Materials.Copper.getFluid(288)).outputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(10).inputs(CountableIngredient.from(OrePrefix.cableGtSingle, Materials.Tin, 2), CountableIngredient.from(OrePrefix.stick, Materials.Steel, 2), CountableIngredient.from(OrePrefix.stick, Materials.SteelMagnetic)).fluidInputs(Materials.Copper.getFluid(288)).outputs(MetaItems.ELECTRIC_MOTOR_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(40).inputs(CountableIngredient.from(OrePrefix.cableGtSingle, Materials.Copper, 2), CountableIngredient.from(OrePrefix.stick, Materials.Aluminium, 2), CountableIngredient.from(OrePrefix.stick, Materials.SteelMagnetic)).fluidInputs(Materials.Copper.getFluid(576)).outputs(MetaItems.ELECTRIC_MOTOR_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(160).inputs(CountableIngredient.from(OrePrefix.cableGtSingle, Materials.Gold, 2), CountableIngredient.from(OrePrefix.stick, Materials.StainlessSteel, 2), CountableIngredient.from(OrePrefix.stick, Materials.SteelMagnetic)).fluidInputs(Materials.Copper.getFluid(1152)).outputs(MetaItems.ELECTRIC_MOTOR_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(640).inputs(CountableIngredient.from(OrePrefix.cableGtSingle, Materials.Aluminium, 2), CountableIngredient.from(OrePrefix.stick, Materials.Titanium, 2), CountableIngredient.from(OrePrefix.stick, Materials.NeodymiumMagnetic)).fluidInputs(Materials.AnnealedCopper.getFluid(2304)).outputs(MetaItems.ELECTRIC_MOTOR_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(2560).inputs(CountableIngredient.from(OrePrefix.cableGtSingle, Materials.Tungsten, 2), CountableIngredient.from(OrePrefix.stick, Materials.TungstenSteel, 2), CountableIngredient.from(OrePrefix.stick, Materials.NeodymiumMagnetic)).fluidInputs(Materials.AnnealedCopper.getFluid(4608)).outputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(15).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin), MetaItems.ELECTRIC_MOTOR_LV.getStackForm(2)).fluidInputs(Materials.Rubber.getFluid(864)).outputs(MetaItems.CONVEYOR_MODULE_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(60).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper), MetaItems.ELECTRIC_MOTOR_MV.getStackForm(2)).fluidInputs(Materials.Rubber.getFluid(864)).outputs(MetaItems.CONVEYOR_MODULE_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(240).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Gold), MetaItems.ELECTRIC_MOTOR_HV.getStackForm(2)).fluidInputs(Materials.Rubber.getFluid(864)).outputs(MetaItems.CONVEYOR_MODULE_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(960).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Aluminium), MetaItems.ELECTRIC_MOTOR_EV.getStackForm(2)).fluidInputs(Materials.Rubber.getFluid(864)).outputs(MetaItems.CONVEYOR_MODULE_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(3840).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tungsten), MetaItems.ELECTRIC_MOTOR_IV.getStackForm(2)).fluidInputs(Materials.Rubber.getFluid(864)).outputs(MetaItems.CONVEYOR_MODULE_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(15).input(OrePrefix.circuit, Tier.Basic, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2), OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite)).fluidInputs(Materials.Brass.getFluid(576)).outputs(MetaItems.EMITTER_LV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(60).input(OrePrefix.circuit, Tier.Intermediate, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper, 2), OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz)).fluidInputs(Materials.Electrum.getFluid(576)).outputs(MetaItems.EMITTER_MV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(240).input(OrePrefix.circuit, Tier.Advanced, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Gold, 2), OreDictUnifier.get(OrePrefix.gem, Materials.Emerald)).fluidInputs(Materials.Chrome.getFluid(576)).outputs(MetaItems.EMITTER_HV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(960).input(OrePrefix.circuit, Tier.Elite, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Aluminium, 2), OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl)).fluidInputs(Materials.Platinum.getFluid(576)).outputs(MetaItems.EMITTER_EV.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(600).EUt(3840).input(OrePrefix.circuit, Tier.Master, 2).inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tungsten, 2), OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye)).fluidInputs(Materials.Osmium.getFluid(576)).outputs(MetaItems.EMITTER_IV.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().input(OrePrefix.plate, Materials.Aluminium, 2).input(OrePrefix.dust, Materials.Redstone).outputs(MetaItems.COVER_MACHINE_CONTROLLER.getStackForm(1)).EUt(16).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_ACTIVITY_DETECTOR.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_FLUID_DETECTOR.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(16).inputs(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Lead, 1).fluidInputs(Materials.SolderingAlloy.getFluid(144 / 2)).outputs(MetaItems.COVER_ITEM_DETECTOR.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(800).EUt(1).inputs(new ItemStack(Blocks.REDSTONE_TORCH, 2, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.dust, Materials.Redstone, 1).fluidInputs(Materials.Concrete.getFluid(144)).outputs(new ItemStack(Items.REPEATER)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.LEAD, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(50)).outputs(new ItemStack(Items.NAME_TAG)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.COMPASS, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 8).outputs(new ItemStack(Items.MAP)).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(2048).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONIC_ORB.getStackForm(8)).input(OrePrefix.plate, Materials.Europium, 4).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32768).EUt(4096).inputs(MetaItems.ENERGY_LAPOTRONIC_ORB2.getStackForm(4)).input(OrePrefix.plate, Materials.Darmstadtium, 4).outputs(MetaItems.ENERGY_LAPOTRONIC_ORB3.getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(400).EUt(1).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.stick, Materials.Wood, 1).fluidInputs(Materials.Creosote.getFluid(1000)).outputs(new ItemStack(Blocks.TORCH, 6)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(4).inputs(new ItemStack(Blocks.PISTON, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Glue.getFluid(100)).circuitMeta(1).outputs(new ItemStack(Blocks.STICKY_PISTON)).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(32).EUt(8).inputs(new ItemStack(Items.LEATHER, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.plate, Materials.Paper, 3).fluidInputs(Materials.Glue.getFluid(20)).outputs(new ItemStack(Items.BOOK)).buildAndRegister();
        
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
	}
	
    private static void initializeCompressingRecipes() {
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.ICE, 2, OreDictionary.WILDCARD_VALUE)).outputs(new ItemStack(Blocks.PACKED_ICE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Ice, 1).outputs(new ItemStack(Blocks.ICE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.QUARTZ, 4)).outputs(new ItemStack(Blocks.QUARTZ_BLOCK)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.WHEAT, 9)).outputs(new ItemStack(Blocks.HAY_BLOCK)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Glowstone, 4).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.NetherQuartz, 4).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.NetherQuartz)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2).input(OrePrefix.dust, Materials.CertusQuartz, 4).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.CertusQuartz)).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Fireclay).outputs(MetaItems.COMPRESSED_FIRECLAY.getStackForm()).duration(100).EUt(2).buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder().inputs(new ItemStack(Items.CLAY_BALL, 2)).outputs(MetaItems.COMPRESSED_CLAY.getStackForm()).buildAndRegister();
    }
    
    private static void initializeBendingRecipes() {
    	 RecipeMaps.BENDER_RECIPES.recipeBuilder()
         .circuitMeta(1)
         .input(OrePrefix.plate, Materials.Iron, 12)
         .outputs(new ItemStack(Items.BUCKET, 4))
         .duration(800).EUt(4)
         .buildAndRegister();

     RecipeMaps.BENDER_RECIPES.recipeBuilder()
         .circuitMeta(1)
         .input(OrePrefix.plate, Materials.WroughtIron, 12)
         .outputs(new ItemStack(Items.BUCKET, 4))
         .duration(800).EUt(4)
         .buildAndRegister();

     RecipeMaps.BENDER_RECIPES.recipeBuilder()
         .circuitMeta(12)
         .input(OrePrefix.plate, Materials.Tin, 2)
         .outputs(new ItemStack(new ItemCell()))
         .duration(200).EUt(30)
         .buildAndRegister();
    }
    
    private static void initializeExtrudingRecipes() {
    	RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(OrePrefix.plate, Materials.Tin, 2)
        .notConsumable(MetaItems.SHAPE_EXTRUDER_CELL)
        .outputs(new ItemStack(new ItemCell()))
        .duration(200).EUt(30)
        .buildAndRegister();
    }
    
    private static void initializePBFRecipes() {
        PBFRecipeBuilder.start().input(OrePrefix.ingot, Materials.Iron).output(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).duration(1500).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.block, Materials.Iron).output(OreDictUnifier.get(OrePrefix.block, Materials.Steel)).duration(12000).fuelAmount(18).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.ingot, Materials.WroughtIron).output(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).duration(600).fuelAmount(2).buildAndRegister();
        PBFRecipeBuilder.start().input(OrePrefix.block, Materials.WroughtIron).output(OreDictUnifier.get(OrePrefix.block, Materials.Steel)).duration(4800).fuelAmount(18).buildAndRegister();
    }
    
    private static void initializeCokeOvenRecipes() {
		CokeOvenRecipeBuilder.start().input(OrePrefix.log, Materials.Wood).output(OreDictUnifier.get(OrePrefix.gem, Materials.Charcoal)).fluidOutput(Materials.Creosote.getFluid(250)).duration(900).buildAndRegister();
		CokeOvenRecipeBuilder.start().input(OrePrefix.gem, Materials.Coal).output(OreDictUnifier.get(OrePrefix.gem, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(500)).duration(900).buildAndRegister();
		CokeOvenRecipeBuilder.start().input(OrePrefix.block, Materials.Coal).output(OreDictUnifier.get(OrePrefix.block, Materials.Coke)).fluidOutput(Materials.Creosote.getFluid(4500)).duration(8000).buildAndRegister();
    }
    
    public static void initializeElectrolyzingRecipes() {
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(480).EUt(120).input(OrePrefix.dust, Materials.Bentonite, 30).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Sodium), OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium, 6), OreDictUnifier.get(OrePrefix.dust, Materials.Silicon, 12)).fluidOutputs(Materials.Hydrogen.getFluid(6000), Materials.Water.getFluid(5000)).buildAndRegister();

        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(1500).EUt(30).fluidInputs(Materials.Water.getFluid(3000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(1500).EUt(30).fluidInputs(Materials.DistilledWater.getFluid(3000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().inputs(new ItemStack(Items.DYE, 3)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Calcium,1)).duration(96).EUt(26).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().inputs(new ItemStack(Blocks.SAND, 8)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.SiliconDioxide,1)).duration(500).EUt(25).buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().input(OrePrefix.dust, Materials.Graphite, 1).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon,4)).duration(100).EUt(26).buildAndRegister();

    }
    
    public static void initializeFuelRecipes() {
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Materials.Creosote.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedEthane.getFluid(1000)).fluidOutputs(Materials.Methane.getFluid(2000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.SteamCrackedEthane.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Carbon, 2)).fluidOutputs(Materials.Methane.getFluid(1500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedEthylene.getFluid(1000)).fluidOutputs(Materials.Ethane.getFluid(1000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.SteamCrackedEthylene.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon)).fluidOutputs(Materials.Methane.getFluid(1000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedPropene.getFluid(1000)).fluidOutputs(Materials.Propane.getFluid(500), Materials.Ethylene.getFluid(500), Materials.Methane.getFluid(500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(180).EUt(120).fluidInputs(Materials.SteamCrackedPropene.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Carbon, 6)).fluidOutputs(Materials.Methane.getFluid(1500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedPropane.getFluid(1000)).fluidOutputs(Materials.Ethane.getFluid(1000), Materials.Methane.getFluid(1000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Materials.SteamCrackedPropane.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Carbon, 3)).fluidOutputs(Materials.Ethylene.getFluid(500), Materials.Methane.getFluid(3500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedLightFuel.getFluid(1000)).fluidOutputs(Materials.Naphtha.getFluid(800), Materials.Butane.getFluid(150), Materials.Propane.getFluid(200), Materials.Ethane.getFluid(125), Materials.Methane.getFluid(125)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.SteamCrackedLightFuel.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Carbon)).fluidOutputs(Materials.HeavyFuel.getFluid(50), Materials.Naphtha.getFluid(100), Materials.Toluene.getFluid(30), Materials.Benzene.getFluid(150), Materials.Butene.getFluid(65), Materials.Butadiene.getFluid(50), Materials.Propane.getFluid(50), Materials.Propene.getFluid(250), Materials.Ethane.getFluid(50), Materials.Ethylene.getFluid(250), Materials.Methane.getFluid(250)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(90).EUt(120).fluidInputs(Materials.HydroCrackedButane.getFluid(750)).fluidOutputs(Materials.Propane.getFluid(500), Materials.Ethane.getFluid(500), Materials.Methane.getFluid(500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Materials.SteamCrackedButane.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Carbon, 9)).fluidOutputs(Materials.Propane.getFluid(250), Materials.Ethane.getFluid(250), Materials.Ethylene.getFluid(250), Materials.Methane.getFluid(4000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedNaphtha.getFluid(1000)).fluidOutputs(Materials.Butane.getFluid(800), Materials.Propane.getFluid(300), Materials.Ethane.getFluid(250), Materials.Methane.getFluid(250)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.SteamCrackedNaphtha.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Carbon, 3)).fluidOutputs(Materials.HeavyFuel.getFluid(25), Materials.LightFuel.getFluid(50), Materials.Toluene.getFluid(20), Materials.Benzene.getFluid(100), Materials.Butene.getFluid(50), Materials.Butadiene.getFluid(50), Materials.Propane.getFluid(15), Materials.Propene.getFluid(300), Materials.Ethane.getFluid(65), Materials.Ethylene.getFluid(500), Materials.Methane.getFluid(500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedHeavyFuel.getFluid(1000)).fluidOutputs(Materials.LightFuel.getFluid(600), Materials.Naphtha.getFluid(100), Materials.Butane.getFluid(100), Materials.Propane.getFluid(100), Materials.Ethane.getFluid(75), Materials.Methane.getFluid(75)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.SteamCrackedHeavyFuel.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Carbon, 3)).fluidOutputs(Materials.LightFuel.getFluid(100), Materials.Naphtha.getFluid(125), Materials.Toluene.getFluid(80), Materials.Benzene.getFluid(400), Materials.Butene.getFluid(80), Materials.Butadiene.getFluid(50), Materials.Propane.getFluid(10), Materials.Propene.getFluid(100), Materials.Ethane.getFluid(15), Materials.Ethylene.getFluid(150), Materials.Methane.getFluid(150)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.HydroCrackedGas.getFluid(1000)).fluidOutputs(Materials.Methane.getFluid(1400), Materials.Hydrogen.getFluid(1340), Materials.Helium.getFluid(20)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(96).EUt(120).fluidInputs(Materials.SteamCrackedGas.getFluid(800)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Carbon)).fluidOutputs(Materials.Propene.getFluid(6), Materials.Ethane.getFluid(6), Materials.Ethylene.getFluid(20), Materials.Methane.getFluid(914), Materials.Helium.getFluid(16)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(90).EUt(120).fluidInputs(Materials.HydroCrackedButene.getFluid(750)).fluidOutputs(Materials.Butane.getFluid(250), Materials.Propene.getFluid(250), Materials.Ethane.getFluid(250), Materials.Methane.getFluid(250)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Materials.SteamCrackedButene.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 3)).fluidOutputs(Materials.Propene.getFluid(250), Materials.Ethylene.getFluid(625), Materials.Methane.getFluid(3000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(90).EUt(120).fluidInputs(Materials.HydroCrackedButadiene.getFluid(750)).fluidOutputs(Materials.Butene.getFluid(500), Materials.Ethylene.getFluid(500)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(240).EUt(120).fluidInputs(Materials.SteamCrackedButadiene.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 2)).fluidOutputs(Materials.Propene.getFluid(250), Materials.Ethylene.getFluid(375), Materials.Methane.getFluid(2250)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(20).EUt(96).fluidInputs(Materials.OilLight.getFluid(150)).fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(10), Materials.SulfuricLightFuel.getFluid(20), Materials.SulfuricNaphtha.getFluid(30), Materials.SulfuricGas.getFluid(240)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(20).EUt(96).fluidInputs(Materials.OilMedium.getFluid(100)).fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(15), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricGas.getFluid(60)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(20).EUt(288).fluidInputs(Materials.OilHeavy.getFluid(150)).fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(250), Materials.SulfuricLightFuel.getFluid(45), Materials.SulfuricNaphtha.getFluid(15), Materials.SulfuricGas.getFluid(600)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(20).EUt(96).fluidInputs(Materials.Oil.getFluid(50)).fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(15), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricGas.getFluid(60)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(600).EUt(64).fluidInputs(Materials.DilutedHydrochloricAcid.getFluid(2000)).fluidOutputs(Materials.Water.getFluid(1000), Materials.HydrochloricAcid.getFluid(1000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(600).EUt(120).fluidInputs(Materials.DilutedSulfuricAcid.getFluid(3000)).fluidOutputs(Materials.SulfuricAcid.getFluid(2000), Materials.Water.getFluid(1000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodTar.getFluid(1000)).fluidOutputs(Materials.Creosote.getFluid(500), Materials.Phenol.getFluid(75), Materials.Benzene.getFluid(350), Materials.Toluene.getFluid(75)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(160).EUt(120).fluidInputs(Materials.Water.getFluid(250)).fluidOutputs(Materials.DistilledWater.getFluid(200)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(80).EUt(480).fluidInputs(Materials.CalciumAcetate.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Quicklime, 3)).fluidOutputs(Materials.Acetone.getFluid(1000), Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(80).EUt(640).fluidInputs(Materials.Acetone.getFluid(1000)).fluidOutputs(Materials.Ethenone.getFluid(1000), Materials.Methane.getFluid(1000)).buildAndRegister();

        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(160).EUt(24).circuitMeta(1).fluidInputs(Materials.Toluene.getFluid(30)).fluidOutputs(Materials.LightFuel.getFluid(30)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).circuitMeta(1).fluidInputs(Materials.HeavyFuel.getFluid(10)).fluidOutputs(Materials.Toluene.getFluid(4)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).circuitMeta(2).fluidInputs(Materials.HeavyFuel.getFluid(10)).fluidOutputs(Materials.Benzene.getFluid(4)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(32).EUt(24).circuitMeta(3).fluidInputs(Materials.HeavyFuel.getFluid(20)).fluidOutputs(Materials.Phenol.getFluid(5)).buildAndRegister();

        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.OilLight.getFluid(300)).circuitMeta(4).fluidOutputs(Materials.Oil.getFluid(100)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.OilMedium.getFluid(200)).circuitMeta(4).fluidOutputs(Materials.Oil.getFluid(100)).buildAndRegister();
        RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(16).EUt(24).fluidInputs(Materials.OilHeavy.getFluid(100)).circuitMeta(4).fluidOutputs(Materials.Oil.getFluid(100)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Ethane.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedEthane.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Ethylene.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedEthylene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Propene.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedPropene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Propane.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedPropane.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.LightFuel.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedLightFuel.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Butane.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedButane.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Naphtha.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedNaphtha.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.HeavyFuel.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedHeavyFuel.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Gas.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedGas.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Butene.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedButene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Hydrogen.getFluid(B), Materials.Butadiene.getFluid(B / 2)).fluidOutputs(Materials.HydroCrackedButadiene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Ethane.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedEthane.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Ethylene.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedEthylene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Propene.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedPropene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Propane.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedPropane.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.LightFuel.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedLightFuel.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Butane.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedButane.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Naphtha.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedNaphtha.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.HeavyFuel.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedHeavyFuel.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Gas.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedGas.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Butene.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedButene.getFluid(B / 2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Steam.getFluid(B), Materials.Butadiene.getFluid(B / 2)).fluidOutputs(Materials.SteamCrackedButadiene.getFluid(B / 2)).buildAndRegister();
        
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Ethane.getFluid(B)).fluidOutputs(Materials.HydroCrackedEthane.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Ethylene.getFluid(B)).fluidOutputs(Materials.HydroCrackedEthylene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Propene.getFluid(B)).fluidOutputs(Materials.HydroCrackedPropene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Propane.getFluid(B)).fluidOutputs(Materials.HydroCrackedPropane.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.LightFuel.getFluid(B)).fluidOutputs(Materials.HydroCrackedLightFuel.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Butane.getFluid(B)).fluidOutputs(Materials.HydroCrackedButane.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Naphtha.getFluid(B)).fluidOutputs(Materials.HydroCrackedNaphtha.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.HeavyFuel.getFluid(B)).fluidOutputs(Materials.HydroCrackedHeavyFuel.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Gas.getFluid(B)).fluidOutputs(Materials.HydroCrackedGas.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Butene.getFluid(B)).fluidOutputs(Materials.HydroCrackedButene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Hydrogen.getFluid(B * 2), Materials.Butadiene.getFluid(B)).fluidOutputs(Materials.HydroCrackedButadiene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Ethane.getFluid(B)).fluidOutputs(Materials.SteamCrackedEthane.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Ethylene.getFluid(B)).fluidOutputs(Materials.SteamCrackedEthylene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Propene.getFluid(B)).fluidOutputs(Materials.SteamCrackedPropene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Propane.getFluid(B)).fluidOutputs(Materials.SteamCrackedPropane.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.LightFuel.getFluid(B)).fluidOutputs(Materials.SteamCrackedLightFuel.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Butane.getFluid(B)).fluidOutputs(Materials.SteamCrackedButane.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Naphtha.getFluid(B)).fluidOutputs(Materials.SteamCrackedNaphtha.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.HeavyFuel.getFluid(B)).fluidOutputs(Materials.SteamCrackedHeavyFuel.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Gas.getFluid(B)).fluidOutputs(Materials.SteamCrackedGas.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Butene.getFluid(B)).fluidOutputs(Materials.SteamCrackedButene.getFluid(B)).buildAndRegister();
        RecipeMaps.CRACKING_RECIPES.recipeBuilder().duration(40).EUt(100).fluidInputs(Materials.Steam.getFluid(B * 2), Materials.Butadiene.getFluid(B)).fluidOutputs(Materials.SteamCrackedButadiene.getFluid(B)).buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(20).EUt(480).fluidInputs(Materials.BioDiesel.getFluid(1000), Materials.Tetranitromethane.getFluid(40)).fluidOutputs(Materials.NitroFuel.getFluid(750)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(20).EUt(480).fluidInputs(Materials.Fuel.getFluid(1000), Materials.Tetranitromethane.getFluid(20)).fluidOutputs(Materials.NitroFuel.getFluid(1000)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(20).EUt(5).fluidInputs(Materials.Butane.getFluid(320)).fluidOutputs(Materials.LPG.getFluid(370)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(20).EUt(5).fluidInputs(Materials.Propane.getFluid(320)).fluidOutputs(Materials.LPG.getFluid(290)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(16).EUt(120).fluidInputs(Materials.LightFuel.getFluid(5000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.Fuel.getFluid(6000)).buildAndRegister();
    }
    
    public static void initializeAlloyingRecipes() {   	
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(600).EUt(2).input(OrePrefix.dust, Materials.Iron, 9).input(OrePrefix.gem, Materials.Coke).outputs(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron)).buildAndRegister();
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(600).EUt(2).input(OrePrefix.block, Materials.Iron).input(OrePrefix.gem, Materials.Coke).outputs(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron)).buildAndRegister();
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(600).EUt(2).input(OrePrefix.ingot, Materials.Iron, 9).input(OrePrefix.gem, Materials.Coke).outputs(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron)).buildAndRegister();
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(300).EUt(2).input(OrePrefix.dust, Materials.Iron, 2).input(OrePrefix.gem, Materials.Coal).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 2)).buildAndRegister();
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(300).EUt(2).input(OrePrefix.ingot, Materials.Iron, 2).input(OrePrefix.gem, Materials.Coal).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 2)).buildAndRegister();
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(300).EUt(2).input(OrePrefix.dust, Materials.Iron, 2).input(OrePrefix.gem, Materials.Charcoal).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 2)).buildAndRegister();
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(300).EUt(2).input(OrePrefix.ingot, Materials.Iron, 2).input(OrePrefix.gem, Materials.Charcoal).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 2)).buildAndRegister();

    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(500).EUt(2).input(OrePrefix.dust, Materials.Obsidian, 4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK).outputs(new ItemStack(Blocks.OBSIDIAN)).buildAndRegister();
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

    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().inputs(MetaItems.FUEL_BINDER.getStackForm(4), MetaItems.ENERGIUM_DUST.getStackForm()).outputs(MetaItems.SUPER_FUEL_BINDER.getStackForm()).duration(1000).EUt(112).buildAndRegister();

    	//Glass Tube
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(160).EUt(8).input(OrePrefix.dust, Materials.Glass).outputs(MetaItems.GLASS_TUBE.getStackForm()).buildAndRegister();
    	
    	RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(300).EUt(8).input(OrePrefix.block, Materials.Glass, 9).notConsumable(MetaItems.SHAPE_MOLD_PLATE).outputs(OreDictUnifier.get(OrePrefix.plate, Materials.Glass)).buildAndRegister();

    	
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
    
    private static void initializeFusionRecipes() {
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Deuterium.getFluid(125), Materials.Tritium.getFluid(125)).fluidOutputs(Materials.Helium.getPlasma(125)).duration(16).EUt(4096).EUToStart(400000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Deuterium.getFluid(125), Materials.Helium3.getFluid(125)).fluidOutputs(Materials.Helium.getPlasma(125)).duration(16).EUt(2048).EUToStart(600000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Carbon.getFluid(125), Materials.Helium3.getFluid(125)).fluidOutputs(Materials.Oxygen.getPlasma(125)).duration(32).EUt(4096).EUToStart(800000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Beryllium.getFluid(16), Materials.Deuterium.getFluid(375)).fluidOutputs(Materials.Nitrogen.getPlasma(175)).duration(16).EUt(2048).EUToStart(200000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Silicon.getFluid(16), Materials.Magnesium.getFluid(16)).fluidOutputs(Materials.Iron.getPlasma(125)).duration(32).EUt(8192).EUToStart(3600000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Potassium.getFluid(16), Materials.Fluorine.getFluid(125)).fluidOutputs(Materials.Nickel.getPlasma(125)).duration(16).EUt(32768).EUToStart(4800000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Beryllium.getFluid(16), Materials.Tungsten.getFluid(16)).fluidOutputs(Materials.Platinum.getFluid(16)).duration(32).EUt(32768).EUToStart(1500000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Neodymium.getFluid(16), Materials.Hydrogen.getFluid(48)).fluidOutputs(Materials.Europium.getFluid(16)).duration(64).EUt(24576).EUToStart(1500000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Lutetium.getFluid(16), Materials.Chrome.getFluid(16)).fluidOutputs(Materials.Americium.getFluid(16)).duration(96).EUt(30405).EUToStart(2000000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Plutonium239.getFluid(16), Materials.Thorium.getFluid(16)).fluidOutputs(Materials.Naquadah.getFluid(16)).duration(64).EUt(32768).EUToStart(3000000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Tungsten.getFluid(16), Materials.Helium.getFluid(16)).fluidOutputs(Materials.Osmium.getFluid(16)).duration(64).EUt(24578).EUToStart(1500000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Manganese.getFluid(16), Materials.Hydrogen.getFluid(16)).fluidOutputs(Materials.Iron.getFluid(16)).duration(64).EUt(8192).EUToStart(1200000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Mercury.getFluid(16), Materials.Magnesium.getFluid(16)).fluidOutputs(Materials.Uranium.getFluid(16)).duration(64).EUt(30405).EUToStart(2400000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Gold.getFluid(16), Materials.Aluminium.getFluid(16)).fluidOutputs(Materials.Uranium.getFluid(16)).duration(64).EUt(30405).EUToStart(2400000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Uranium.getFluid(16), Materials.Helium.getFluid(16)).fluidOutputs(Materials.Plutonium239.getFluid(16)).duration(128).EUt(30405).EUToStart(4800000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Vanadium.getFluid(16), Materials.Hydrogen.getFluid(125)).fluidOutputs(Materials.Chrome.getFluid(16)).duration(64).EUt(24576).EUToStart(1400000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Gallium.getFluid(16), Materials.Radon.getFluid(125)).fluidOutputs(Materials.Duranium.getFluid(16)).duration(64).EUt(16384).EUToStart(1400000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Titanium.getFluid(48), Materials.Duranium.getFluid(32)).fluidOutputs(Materials.Tritanium.getFluid(16)).duration(64).EUt(30405).EUToStart(2000000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Gold.getFluid(16), Materials.Mercury.getFluid(16)).fluidOutputs(Materials.Radon.getFluid(125)).duration(64).EUt(32768).EUToStart(2000000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Silver.getFluid(16), Materials.Lithium.getFluid(16)).fluidOutputs(Materials.Indium.getFluid(16)).duration(32).EUt(24576).EUToStart(3800000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Lithium.getFluid(16), Materials.Tungsten.getFluid(16)).fluidOutputs(Materials.Iridium.getFluid(16)).duration(32).EUt(32768).EUToStart(3000000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Americium.getFluid(16), Materials.Tritanium.getFluid(16)).fluidOutputs(Materials.Darmstadtium.getFluid(16)).duration(32).EUt(32768).EUToStart(1400000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Darmstadtium.getFluid(32), Materials.Adamantine.getFluid(16)).fluidOutputs(Materials.Vibranium.getFluid(32)).duration(125).EUt(85240).EUToStart(3800000).buildAndRegister();
		
		//Fantasy Recipes
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Acrylic.getFluid(32), Materials.Duranium.getFluid(16)).fluidOutputs(Materials.DuraniumFusedAcrylic.getFluid(48)).duration(200).EUt(8964).EUToStart(1200000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Adamantine.getFluid(16), Materials.Iron.getFluid(16)).fluidOutputs(Materials.Tungsten.getFluid(24)).duration(100).EUt(4096).EUToStart(1200000).buildAndRegister();
		RecipeMaps.FUSION_RECIPES.recipeBuilder().fluidInputs(Materials.Silicon.getFluid(16), Materials.Gold.getFluid(16)).fluidOutputs(Materials.Mercury.getFluid(16)).duration(60).EUt(4096).EUToStart(1500000).buildAndRegister();

    }
    
    private static void initializePlasmaArcRecipes() {
    	//Ore materials over @link:OreRecipeHandler.java
    	for(FluidMaterial plasma : new FluidMaterial[] {Materials.Nitrogen, Materials.Argon}) {
    		int plasmaAmount = (int)(plasma.getAverageMass());    		    		
    		RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(240).EUt(480)
    			.fluidInputs(plasma.getPlasma(plasmaAmount))
    			.inputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Adamantine), OreDictUnifier.get(OrePrefix.ingot, Materials.Naquadah))
    			.outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Tritanium))
    			.buildAndRegister(); 
            RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(100).EUt(190).input(OrePrefix.dust, Materials.Silicon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Silicon)).buildAndRegister();
            RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(40).EUt(120).input(OrePrefix.dust, Materials.Iron).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel)).buildAndRegister();
            RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(90).EUt(320).input(OrePrefix.dust, Materials.Titanium).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Titanium)).buildAndRegister();
            RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(90).EUt(760).input(OrePrefix.dust, Materials.Rutile).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Titanium, 2)).buildAndRegister();
    	}  	
        RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(480).EUt(29130).input(OrePrefix.ingot, Materials.Naquadah, 1).input(OrePrefix.ingot, Materials.Osmiridium, 1).fluidInputs(Materials.Argon.getPlasma(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.NaquadahAlloy, 2)).buildAndRegister();
        RecipeMaps.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder().duration(240).EUt(23400).input(OrePrefix.ingot, Materials.NaquadahEnriched, 1).input(OrePrefix.ingot, Materials.Osmiridium, 1).fluidInputs(Materials.Argon.getPlasma(100)).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.NaquadahAlloy, 2)).buildAndRegister();
    }
    
    private static void initializeBlastRecipes() {
    	RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.TungstenSteel.getAverageMass() / 80,1) * Materials.TungstenSteel.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Tungsten, 1).input(OrePrefix.ingot, Materials.Steel, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenSteel,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(Materials.TungstenSteel.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.VanadiumGallium.getAverageMass() / 40,1) * Materials.VanadiumGallium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Vanadium, 3).input(OrePrefix.ingot, Materials.Gallium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.VanadiumGallium,4), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,2)).blastFurnaceTemp(Materials.VanadiumGallium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.NiobiumTitanium.getAverageMass() / 80,1) * Materials.NiobiumTitanium.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Niobium, 1).input(OrePrefix.ingot, Materials.Titanium, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.NiobiumTitanium,2), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(Materials.NiobiumTitanium.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration((int) Math.max(Materials.Nichrome.getAverageMass() / 32,1) * Materials.Nichrome.blastFurnaceTemperature).EUt(480).input(OrePrefix.ingot, Materials.Nickel, 4).input(OrePrefix.ingot, Materials.Chrome, 1).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Nichrome,5), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,2)).blastFurnaceTemp(Materials.Nichrome.blastFurnaceTemperature).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Ruby, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3), OreDictUnifier.get(OrePrefix.dustTiny,Materials.DarkAsh,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(100).input(OrePrefix.dust, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(320).EUt(100).input(OrePrefix.gem, Materials.Sapphire, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Aluminium,3)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(500).input(OrePrefix.dust, Materials.Ilmenite, 1).input(OrePrefix.dust, Materials.Carbon, 1).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Rutile,4)).blastFurnaceTemp(1700).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(800).EUt(480).input(OrePrefix.dust, Materials.Magnesium, 2).fluidInputs(Materials.TitaniumTetrachloride.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot,Materials.Titanium,1), OreDictUnifier.get(OrePrefix.dust,Materials.MagnesiumChloride,2)).blastFurnaceTemp(Materials.Titanium.blastFurnaceTemperature + 200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Galena, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.Silver,4), OreDictUnifier.get(OrePrefix.nugget,Materials.Lead,4)).blastFurnaceTemp(1500).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(400).EUt(500).input(OrePrefix.dust, Materials.Magnetite, 1).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.nugget,Materials.WroughtIron,4), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.ingot, Materials.Iron, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Steel,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(350).EUt(120).input(OrePrefix.ingot, Materials.PigIron, 1).fluidInputs(Materials.Oxygen.getFluid(500)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Steel,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(350).EUt(120).input(OrePrefix.ingot, Materials.WroughtIron, 1).fluidInputs(Materials.Oxygen.getFluid(500)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.Steel,1), OreDictUnifier.get(OrePrefix.dustSmall,Materials.DarkAsh,1)).blastFurnaceTemp(1000).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.dust, Materials.Copper, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.AnnealedCopper,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(120).input(OrePrefix.ingot, Materials.Copper, 1).fluidInputs(Materials.Oxygen.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.AnnealedCopper,1)).blastFurnaceTemp(1200).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(1920).input(OrePrefix.ingot, Materials.Iridium, 3).input(OrePrefix.ingot, Materials.Osmium, 1).fluidInputs(Materials.Helium.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Osmiridium, 4)).blastFurnaceTemp(Materials.Osmiridium.blastFurnaceTemperature).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Galena).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Massicot), OreDictUnifier.get(OrePrefix.nugget, Materials.Lead, 6)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Stibnite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.AntimonyTrioxide), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Sphalerite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Zincite), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Cobaltite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.CobaltOxide), OreDictUnifier.get(OrePrefix.dust, Materials.ArsenicTrioxide)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Tetrahedrite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.CupricOxide), OreDictUnifier.get(OrePrefix.dustTiny, Materials.AntimonyTrioxide, 3)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Chalcopyrite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.CupricOxide), OreDictUnifier.get(OrePrefix.dust, Materials.Ferrosilite)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Pentlandite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Garnierite), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Pyrite).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BandedIron), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Massicot, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Lead, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.AntimonyTrioxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Antimony, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(3000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.CobaltOxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Cobalt, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.ArsenicTrioxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Arsenic, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.CupricOxide, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Garnierite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Nickel, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.BandedIron, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.SiliconDioxide).input(OrePrefix.dust, Materials.Carbon, 2).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Silicon), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash)).fluidOutputs(Materials.CarbonMonoxide.getFluid(2000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Malachite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(3000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Magnetite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(240).EUt(120).blastFurnaceTemp(1200).input(OrePrefix.dust, Materials.Cassiterite, 2).input(OrePrefix.dust, Materials.Carbon).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 2)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(944).EUt(120).input(OrePrefix.dust, Materials.Silicon).notConsumable(new IntCircuitIngredient(1)).blastFurnaceTemp(1687).outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Silicon)).buildAndRegister();
        
        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(100).EUt(6722).input(OrePrefix.dust, Materials.Copper).input(OrePrefix.dust, Materials.Diamond).fluidInputs(Materials.Silver.getFluid(L * 3)).blastFurnaceTemp(Materials.Dymalloy.blastFurnaceTemperature).outputs(OreDictUnifier.get(OrePrefix.ingotHot, Materials.Dymalloy, 5)).buildAndRegister();

        MaterialStack[] ironOres = {
                new MaterialStack(Materials.Pyrite, 1),
                new MaterialStack(Materials.Magnetite, 1),
                new MaterialStack(Materials.Iron, 1),
                new MaterialStack(Materials.Siderite, 1),
                new MaterialStack(Materials.Hematite, 1),
                new MaterialStack(Materials.Geothite, 1),
                //new MaterialStack(Materials.Taconite, 1),
        };
        
        for(MaterialStack ore : ironOres) {
            Material targetMaterial = ore.material;
            if(targetMaterial == Materials.BandedIron) {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 6), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 8), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
            if((targetMaterial == Materials.Magnetite)/* || (targetMaterial == Materials.Taconite)*/) {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 12), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 16), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
            if(targetMaterial == Materials.Geothite) {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 17), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 20), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
            else {
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.ore, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSand, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreSandstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreGravel, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreNetherrack, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dust, Materials.Calcite).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 25), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();
                RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(500).EUt(90).blastFurnaceTemp(1200).input(OrePrefix.oreEndstone, targetMaterial).input(OrePrefix.dustSmall, Materials.Quicklime, 3).outputs(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 32), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh)).buildAndRegister();       
            }
        }
    }
    
    private static void initializeCentrifugingRecipes() {
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(300).EUt(16).inputs(MetaItems.RUBBER_DROP.getStackForm()).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3)).fluidOutputs(Materials.Glue.getFluid(100)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(200).EUt(20).inputs(MetaBlocks.LOG.getItem(LogVariant.RUBBER_WOOD)).chancedOutput(MetaItems.RUBBER_DROP.getStackForm(), 5000).chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon), 2500).chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 2500).fluidOutputs(Materials.Methane.getFluid(60)).buildAndRegister();

    	RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Items.NETHER_WART)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Blocks.BROWN_MUSHROOM)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(new ItemStack(Blocks.RED_MUSHROOM)).fluidOutputs(Materials.Methane.getFluid(18)).buildAndRegister();
        
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(1600).EUt(8).fluidInputs(Materials.Air.getFluid(10000)).fluidOutputs(Materials.Nitrogen.getFluid(3900), Materials.Oxygen.getFluid(1000)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(300).EUt(192).fluidInputs(Materials.LeadZincSolution.getFluid(8000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Lead), OreDictUnifier.get(OrePrefix.dust, Materials.Silver), OreDictUnifier.get(OrePrefix.dust, Materials.Zinc), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 3)).fluidOutputs(Materials.Water.getFluid(2000)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(900).EUt(30).input(OrePrefix.dust, Materials.PlatinumGroupSludge).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Gold), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Platinum)).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Palladium), 8000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Iridium), 6000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Osmium), 6000).buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(250).EUt(6).input(OrePrefix.dust, Materials.DarkAsh).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon)).buildAndRegister();
       
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(40).EUt(220).fluidInputs(Materials.Lava.getFluid(200)).chancedOutput(OreDictUnifier.get(OrePrefix.nugget,Materials.Tantalum, 1), 1000).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Olivine, 1), 2000).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Topaz, 2), 1500).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Tungstate, 1), 400).chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Spessartine, 1), 2000).chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Zircon, 2), 500).buildAndRegister();

        //Methane from foods
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(60).EUt(5).input("listAllmeat", 1).fluidOutputs(Materials.Methane.getFluid(40)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(60).EUt(5).input("listAllmeatcooked", 1).fluidOutputs(Materials.Methane.getFluid(50)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(60).EUt(5).input("listAllfish", 1).fluidOutputs(Materials.Methane.getFluid(40)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(60).EUt(5).input("listAllfishcooked", 1).fluidOutputs(Materials.Methane.getFluid(50)).buildAndRegister();
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(60).EUt(5).input("listAllveggies", 1).fluidOutputs(Materials.Methane.getFluid(25)).buildAndRegister();
        
        for(Item item : ForgeRegistries.ITEMS.getValuesCollection()) {
            if(item instanceof ItemFood) {
                ItemFood itemFood = (ItemFood) item;
                Collection<ItemStack> subItems = ModHandler.getAllSubItems(new ItemStack(item, 1, GTValues.W));
                for(ItemStack itemStack : subItems) {
                    int healAmount = itemFood.getHealAmount(itemStack);
                    float saturationModifier = itemFood.getSaturationModifier(itemStack);
                    if(healAmount > 0) {
                        FluidStack outputStack = Materials.Methane.getFluid(Math.round(9 * healAmount * (1.0f + saturationModifier)));
                        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(144).EUt(5).inputs(itemStack).fluidOutputs(outputStack).buildAndRegister();
                    }
                }
            }
        }
    }
    
    private static void initializeMixingRecipes() {
    	for (OrePrefix prefix : Arrays.asList(OrePrefix.dust, OrePrefix.dustSmall, OrePrefix.dustTiny)) {
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (100 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.EnderPearl).input(prefix, Materials.Blaze).outputs(OreDictUnifier.getDust(Materials.EnderEye, 1 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (200 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Gold).input(prefix, Materials.Silver).outputs(OreDictUnifier.getDust(Materials.Electrum, 2 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 2).input(prefix, Materials.Nickel).outputs(OreDictUnifier.getDust(Materials.Invar, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron, 4).input(prefix, Materials.Invar, 3).input(prefix, Materials.Manganese).input(prefix, Materials.Chrome).outputs(OreDictUnifier.getDust(Materials.StainlessSteel, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Iron).input(prefix, Materials.Aluminium).input(prefix, Materials.Chrome).outputs(OreDictUnifier.getDust(Materials.Kanthal, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (600 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Barium, 2).input(prefix, Materials.Yttrium).outputs(OreDictUnifier.getDust(Materials.YttriumBariumCuprate, 6 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Zinc).outputs(OreDictUnifier.getDust(Materials.Brass, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Tin).outputs(OreDictUnifier.getDust(Materials.Bronze, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (200 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper).input(prefix, Materials.Nickel).outputs(OreDictUnifier.getDust(Materials.Cupronickel, 2 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper).input(prefix, Materials.Gold, 4).outputs(OreDictUnifier.getDust(Materials.RoseGold, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper).input(prefix, Materials.Silver, 4).outputs(OreDictUnifier.getDust(Materials.SterlingSilver, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 5).input(prefix, Materials.Beryllium, 2).input(prefix, Materials.Nickel).outputs(OreDictUnifier.getDust(Materials.BerylliumCopper, 8 * prefix.materialAmount)).buildAndRegister();           
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Copper, 3).input(prefix, Materials.Aluminium).outputs(OreDictUnifier.getDust(Materials.AluminiumCopper, 4 * prefix.materialAmount)).buildAndRegister();           
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (500 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Bismuth).input(prefix, Materials.Brass, 4).outputs(OreDictUnifier.getDust(Materials.BismuthBronze, 5 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (900 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Cobalt, 5).input(prefix, Materials.Chrome, 2).input(prefix, Materials.Nickel).input(prefix, Materials.Molybdenum).outputs(OreDictUnifier.getDust(Materials.Ultimet, 9 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (400 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Saltpeter, 2).input(prefix, Materials.Sulfur).input(prefix, Materials.Coal).outputs(OreDictUnifier.getDust(Materials.Gunpowder, 4 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (300 * prefix.materialAmount / M)).EUt(8).input(prefix, Materials.Saltpeter, 2).input(prefix, Materials.Sulfur).input(prefix, Materials.Charcoal).outputs(OreDictUnifier.getDust(Materials.Gunpowder, 3 * prefix.materialAmount)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration((int) (1000 * prefix.materialAmount / M)).EUt(480).input(prefix, Materials.Copper).input(prefix, Materials.Silver, 3).input(prefix, Materials.Diamond).outputs(OreDictUnifier.getDust(Materials.Dymalloy, 5 * prefix.materialAmount)).buildAndRegister();      
    	}
    	RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(160).EUt(8).input(OrePrefix.dust, Materials.Flint).input(OrePrefix.dust, Materials.Quartzite, 4).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 4)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(60).EUt(30).input(OrePrefix.dust, Materials.Sodium, 2).input(OrePrefix.dust, Materials.Sulfur).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumSulfide)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(140).EUt(53).input(OrePrefix.dust, Materials.Iron).input(OrePrefix.dust, Materials.Aluminium).outputs(MetaItems.THERMITE_DUST.getStackForm(2)).buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
        	.input(OrePrefix.dust, Materials.Stone, 1)
        	.fluidInputs(Materials.Lubricant.getFluid(20), ModHandler.getWater(1000))
        	.fluidOutputs(Materials.DrillingFluid.getFluid(5000))
        	.duration(64).EUt(16)
        	.buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
        	.input(OrePrefix.dust, Materials.Clay, 1)
        	.input(OrePrefix.dust, Materials.Stone, 3)
        	.fluidInputs(Materials.Water.getFluid(500))
        	.fluidOutputs(Materials.Concrete.getFluid(576))
        	.duration(20).EUt(16)
        	.buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
        	.inputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.LIGHT_CONCRETE, ChiselingVariant.NORMAL))
        	.fluidInputs(Materials.Water.getFluid(144))
        	.outputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.DARK_CONCRETE, ChiselingVariant.NORMAL))
        	.duration(12).EUt(4)
        	.buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
        	.duration(64).EUt(16)
        	.fluidInputs(Materials.Water.getFluid(1000))
        	.input("sand", 2)
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
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.SPIDER_EYE)).input(OrePrefix.dust, Materials.Sugar).outputs(new ItemStack(Items.FERMENTED_SPIDER_EYE)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(16).EUt(16).fluidInputs(Materials.LightFuel.getFluid(5000), Materials.HeavyFuel.getFluid(1000)).fluidOutputs(Materials.Fuel.getFluid(6000)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(20).EUt(16).input(OrePrefix.dust, Materials.Clay).input(OrePrefix.dust, Materials.Stone, 3).fluidInputs(Materials.Water.getFluid(500)).fluidOutputs(Materials.Concrete.getFluid(576)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(12).EUt(4).inputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.LIGHT_CONCRETE, ChiselingVariant.NORMAL)).fluidInputs(Materials.Water.getFluid(144)).outputs(MetaBlocks.CONCRETE.getItemVariant(ConcreteVariant.DARK_CONCRETE, ChiselingVariant.NORMAL)).buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(60).EUt(30).input(OrePrefix.dust, Materials.Sodium, 2).input(OrePrefix.dust, Materials.Sulfur).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.SodiumSulfide)).buildAndRegister();

    }
    
    private static void initializeBatteryRecipes() {
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_LV.getStackForm())
        	.input(OrePrefix.dust, Materials.Cadmium, 2)
        	.outputs(BATTERY_RE_LV_CADMIUM.getStackForm())
        	.duration(100)
        	.EUt(2)
        	.buildAndRegister();
        
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_LV.getStackForm())
        	.input(OrePrefix.dust, Materials.Lithium, 2)
        	.outputs(BATTERY_RE_LV_LITHIUM.getStackForm())
        	.duration(100)
        	.EUt(2)
        	.buildAndRegister();
        
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_LV.getStackForm())
        	.input(OrePrefix.dust, Materials.Sodium, 2)
        	.outputs(BATTERY_RE_LV_SODIUM.getStackForm())
        	.duration(100)
        	.EUt(2)
        	.buildAndRegister();

        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_MV.getStackForm())
        	.input(OrePrefix.dust, Materials.Cadmium, 8)
        	.outputs(BATTERY_RE_MV_CADMIUM.getStackForm())
        	.duration(400)
        	.EUt(2)
        	.buildAndRegister();
        
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_MV.getStackForm())
        	.input(OrePrefix.dust, Materials.Lithium, 8)
        	.outputs(BATTERY_RE_MV_LITHIUM.getStackForm())
        	.duration(400)
        	.EUt(2)
        	.buildAndRegister();
        
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_MV.getStackForm())
        	.input(OrePrefix.dust, Materials.Sodium, 8)
        	.outputs(BATTERY_RE_MV_SODIUM.getStackForm())
        	.duration(400)
        	.EUt(2)
        	.buildAndRegister();

        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_HV.getStackForm())
        	.input(OrePrefix.dust, Materials.Cadmium, 16)
        	.outputs(BATTERY_RE_HV_CADMIUM.getStackForm())
        	.duration(1600)
        	.EUt(2)
        	.buildAndRegister();
        
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_HV.getStackForm())
        	.input(OrePrefix.dust, Materials.Lithium, 16)
        	.outputs(BATTERY_RE_HV_LITHIUM.getStackForm())
        	.duration(1600)
        	.EUt(2)
        	.buildAndRegister();
        
        RecipeMaps.CANNER_RECIPES.recipeBuilder()
        	.inputs(BATTERY_HULL_HV.getStackForm())
        	.input(OrePrefix.dust, Materials.Sodium, 16)
        	.outputs(BATTERY_RE_HV_SODIUM.getStackForm())
        	.duration(1600)
        	.EUt(2)
        	.buildAndRegister();
    }
    
    private static void initializePyrolyseRecipes() {
		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.gem, Materials.Coal, 16)
		  	.circuitMeta(0)
		  	.outputs(OreDictUnifier.get(OrePrefix.gem, Materials.Coke, 20))
		  	.fluidOutputs(Materials.Creosote.getFluid(10000))
		  	.duration(440).EUt(96)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(0)
		  	.outputs(new ItemStack(Items.COAL, 20, 1))
		  	.fluidOutputs(Materials.Creosote.getFluid(4000))
		  	.duration(440)
		  	.EUt(64)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(1)
		  	.fluidInputs(Materials.Nitrogen.getFluid(400))
		  	.outputs(new ItemStack(Items.COAL, 20, 1))
		  	.fluidOutputs(Materials.Creosote.getFluid(4000))
		  	.duration(200)
		  	.EUt(96)
		  .buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(2)
		  	.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 4))
		  	.fluidOutputs(Materials.OilHeavy.getFluid(200))
		  	.duration(280)
		  	.EUt(192)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(3)
		  	.outputs(new ItemStack(Items.COAL, 20, 1))
		  	.fluidOutputs(Materials.WoodVinegar.getFluid(3000))
		  	.duration(640)
		  	.EUt(64)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(4)
		  	.fluidInputs(Materials.Nitrogen.getFluid(400))
		  	.outputs(new ItemStack(Items.COAL, 20, 1))
		  	.fluidOutputs(Materials.WoodVinegar.getFluid(3000))
		  	.duration(320)
		  	.EUt(96)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(7)
		  	.outputs(new ItemStack(Items.COAL, 20, 1))
		  	.fluidOutputs(Materials.WoodTar.getFluid(1500))
		  	.duration(640)
		  	.EUt(64)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.input(OrePrefix.log, Materials.Wood, 16)
		  	.circuitMeta(8)
		  	.fluidInputs(Materials.Nitrogen.getFluid(400))
		  	.outputs(new ItemStack(Items.COAL, 20, 1))
		  	.fluidOutputs(Materials.WoodTar.getFluid(1500))
		  	.duration(320)
		  	.EUt(96)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.inputs(new ItemStack(Items.SUGAR, 23))
		  	.circuitMeta(1)
		  	.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal, 12))
		  	.fluidOutputs(Materials.Water.getFluid(1500))
		  	.duration(640)
		  	.EUt(64)
		  	.buildAndRegister();

		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
		  	.inputs(new ItemStack(Items.SUGAR, 23))
		  	.circuitMeta(2)
		  	.fluidInputs(Materials.Nitrogen.getFluid(400))
		  	.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal, 12))
		  	.fluidOutputs(Materials.Water.getFluid(1500))
		  	.duration(320)
		  	.EUt(96)
		  	.buildAndRegister();
    }
    
    private static void initializeExtractingRecipes() {
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Items.COAL, 1, 1)).chancedOutput(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1), 1000).fluidOutputs(Materials.Creosote.getFluid(50)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(32).EUt(4).inputs(new ItemStack(Items.SNOWBALL)).fluidOutputs(Materials.Water.getFluid(250)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(128).EUt(4).inputs(new ItemStack(Blocks.SNOW)).fluidOutputs(Materials.Water.getFluid(1000)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(80).EUt(24).input(OrePrefix.dust, Materials.Redstone).fluidOutputs(Materials.Redstone.getFluid(144)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(80).EUt(24).input(OrePrefix.dust, Materials.Glowstone).fluidOutputs(Materials.Glowstone.getFluid(144)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(600).EUt(28).input(OrePrefix.dust, Materials.Quartzite, 1).fluidOutputs(Materials.Glass.getFluid(144)).buildAndRegister();
   
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(1000).EUt(24).inputs(MetaBlocks.LOG.getItem(LogVariant.RUBBER_WOOD)).chancedOutput(MetaItems.RUBBER_DROP.getStackForm(), 2500).fluidOutputs(Materials.Glue.getFluid(250)).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(1000).EUt(24).inputs(new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE)).chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 3500).buildAndRegister();
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(1000).EUt(24).inputs(new ItemStack(Blocks.SAND)).chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide), 2500).buildAndRegister();
    }
    
    private static void initializeMiscRecipes() {		
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
		
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).fluidInputs(Materials.Water.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(128).EUt(4).fluidInputs(Materials.DistilledWater.getFluid(250)).outputs(new ItemStack(Items.SNOWBALL)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Water.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(512).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.DistilledWater.getFluid(1000)).outputs(new ItemStack(Blocks.SNOW)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(1024).EUt(16).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Lava.getFluid(1000)).outputs(new ItemStack(Blocks.OBSIDIAN)).buildAndRegister();

        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glowstone.getFluid(576)).outputs(new ItemStack(Blocks.GLOWSTONE)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_BLOCK.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(12).EUt(4).notConsumable(MetaItems.SHAPE_MOLD_PLATE.getStackForm()).fluidInputs(Materials.Glass.getFluid(144)).outputs(OreDictUnifier.get(OrePrefix.plate,Materials.Glass,1)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Paper, 1).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.REEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Water.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(200).EUt(4).input(OrePrefix.dust, Materials.Wood, 1).fluidInputs(Materials.DistilledWater.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(4).input(OrePrefix.dust, Materials.Paper, 1).fluidInputs(Materials.DistilledWater.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();

        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(100).EUt(8).inputs(new ItemStack(Items.REEDS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.DistilledWater.getFluid(100)).outputs(new ItemStack(Items.PAPER)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.WOOL)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(25)).outputs(new ItemStack(Blocks.CARPET)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.HARDENED_CLAY)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_GLASS, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(50)).outputs(new ItemStack(Blocks.GLASS)).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(400).EUt(2).inputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.Chlorine.getFluid(20)).outputs(new ItemStack(Blocks.GLASS_PANE)).buildAndRegister();
    	
    	for(int i = 0; i < 16; i++) {
            RecipeMaps.CUTTER_RECIPES.recipeBuilder().duration(50).EUt(8).inputs(new ItemStack(Blocks.STAINED_GLASS, 3, i)).outputs(new ItemStack(Blocks.STAINED_GLASS_PANE, 8, i)).buildAndRegister();
        }
    	
        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(16).EUt(30).circuitMeta(1).fluidInputs(Materials.Acetone.getFluid(100)).fluidOutputs(Materials.Ethenone.getFluid(100)).buildAndRegister();
        RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(16).EUt(30).circuitMeta(1).fluidInputs(Materials.CalciumAcetate.getFluid(200)).fluidOutputs(Materials.Acetone.getFluid(200)).buildAndRegister();

        RecipeMaps.VACUUM_RECIPES.recipeBuilder().duration(50).fluidInputs(Materials.Water.getFluid(1000)).fluidOutputs(Materials.Ice.getFluid(1000)).buildAndRegister();
        RecipeMaps.VACUUM_RECIPES.recipeBuilder().duration(400).fluidInputs(Materials.Air.getFluid(4000)).fluidOutputs(Materials.LiquidAir.getFluid(4000)).buildAndRegister();
        
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder().duration(80).EUt(48).input(OrePrefix.ingot, Materials.Polycaprolactam, 1).outputs(new ItemStack(Items.STRING, 32)).buildAndRegister();

        RecipeMaps.WIREMILL_RECIPES.recipeBuilder().duration(200).EUt(140).input(OrePrefix.dust, Materials.Carbon, 18).outputs(MetaItems.CARBON_FIBRE.getStackForm()).buildAndRegister();
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder().duration(200).EUt(140).input(OrePrefix.dust, Materials.Graphite, 9).outputs(MetaItems.CARBON_FIBRE.getStackForm()).buildAndRegister();

        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder() .inputs(MetaItems.ENERGIUM_DUST.getStackForm(9)).fluidInputs(Materials.Lubricant.getFluid(20)).outputs(MetaItems.ENERGY_CRYSTAL.getStackForm()).duration(2000).EUt(96).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(72000).EUt(480).input(OrePrefix.dust, Materials.NetherStar, 1).fluidInputs(Materials.Lubricant.getFluid(108)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar, 1)).buildAndRegister();
        
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(480).EUt(384).input(OrePrefix.gem, Materials.EnderEye, 2).fluidInputs(Materials.Radon.getFluid(250)).outputs(MetaItems.QUANTUM_EYE.getStackForm()).buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(1920).EUt(384).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Radon.getFluid(1250)).outputs(MetaItems.QUANTUM_STAR.getStackForm()).buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder().duration(480).EUt(7680).input(OrePrefix.gem, Materials.NetherStar, 1).fluidInputs(Materials.Darmstadtium.getFluid(L * 2)).outputs(MetaItems.GRAVI_STAR.getStackForm()).buildAndRegister();

        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(800).EUt(3).input("treeSapling", 1).fluidInputs(Materials.Water.getFluid(100)).fluidOutputs(Materials.Biomass.getFluid(50)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.POTATO)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.CARROT)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Blocks.CACTUS)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.REEDS)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Blocks.BROWN_MUSHROOM)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Blocks.RED_MUSHROOM)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(160).EUt(3).inputs(new ItemStack(Items.BEETROOT)).fluidInputs(Materials.Water.getFluid(20)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        
        for (ItemStack stack : OreDictionary.getOres("listAllveggies")) {
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(800).EUt(3).inputs(GTUtility.copyAmount(1, stack)).fluidInputs(Materials.Water.getFluid(100)).fluidOutputs(Materials.Biomass.getFluid(20)).buildAndRegister();
        }
        
        for (ItemStack stack : OreDictionary.getOres("listAllgrain")) {
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(800).EUt(3).inputs(GTUtility.copyAmount(1, stack)).fluidInputs(Materials.Water.getFluid(100)).fluidOutputs(Materials.Biomass.getFluid(10)).buildAndRegister();
        }
 
    }
    
    private static void initializeForestrySupport() {	
    	if(Loader.isModLoaded("forestry")) {
    		RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Fluids.SEED_OIL.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
            RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodVinegar.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(100), Materials.Water.getFluid(500), Fluids.BIO_ETHANOL.getFluid(10), Materials.Methanol.getFluid(300), Materials.Acetone.getFluid(50), Materials.MethylAcetate.getFluid(10)).buildAndRegister();
            RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(75).EUt(180).fluidInputs(Materials.FermentedBiomass.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(25), Materials.Water.getFluid(375), Fluids.BIO_ETHANOL.getFluid(150), Materials.Methanol.getFluid(150), Materials.Ammonia.getFluid(100), Materials.CarbonDioxide.getFluid(400), Materials.Methane.getFluid(600)).buildAndRegister();
            RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(400).fluidInputs(Materials.Biomass.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2)).fluidOutputs(Fluids.BIO_ETHANOL.getFluid(600), Materials.Water.getFluid(300)).buildAndRegister();
            
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(Fluids.SEED_OIL.getFluid(6000), Materials.Methanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(Fluids.SEED_OIL.getFluid(6000), Fluids.BIO_ETHANOL.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
            
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(20).fluidInputs(Materials.SulfuricAcid.getFluid(1000), Fluids.BIO_ETHANOL.getFluid(1000)).fluidOutputs(Materials.Ethylene.getFluid(1000), Materials.DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();

            RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(32).EUt(2).input("listAllseed", 1).fluidOutputs(Fluids.SEED_OIL.getFluid(10)).buildAndRegister();
               
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(400).EUt(3).input("treeSapling", 1).fluidInputs(Fluids.FOR_HONEY.getFluid(50)).fluidOutputs(Materials.Biomass.getFluid(50)).buildAndRegister();            
            RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(400).EUt(3).input("treeSapling", 1).fluidInputs(Fluids.JUICE.getFluid(50)).fluidOutputs(Materials.Biomass.getFluid(50)).buildAndRegister();                       
    	} 
    	else {
        	RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(16).EUt(96).fluidInputs(Materials.SeedOil.getFluid(24)).fluidOutputs(Materials.Lubricant.getFluid(12)).buildAndRegister();
        	RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(40).EUt(256).fluidInputs(Materials.WoodVinegar.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(100), Materials.Water.getFluid(500), Materials.Ethanol.getFluid(10), Materials.Methanol.getFluid(300), Materials.Acetone.getFluid(50), Materials.MethylAcetate.getFluid(10)).buildAndRegister();
        	RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(75).EUt(180).fluidInputs(Materials.FermentedBiomass.getFluid(1000)).fluidOutputs(Materials.AceticAcid.getFluid(25), Materials.Water.getFluid(375), Materials.Ethanol.getFluid(150), Materials.Methanol.getFluid(150), Materials.Ammonia.getFluid(100), Materials.CarbonDioxide.getFluid(400), Materials.Methane.getFluid(600)).buildAndRegister();
        	RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(32).EUt(400).fluidInputs(Materials.Biomass.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2)).fluidOutputs(Materials.Ethanol.getFluid(600), Materials.Water.getFluid(300)).buildAndRegister();
		
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(Materials.SeedOil.getFluid(6000), Materials.Methanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dustTiny, Materials.SodiumHydroxide).fluidInputs(Materials.SeedOil.getFluid(6000), Materials.Ethanol.getFluid(1000)).fluidOutputs(Materials.Glycerol.getFluid(1000), Materials.BioDiesel.getFluid(6000)).buildAndRegister();
    	
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(20).fluidInputs(Materials.SulfuricAcid.getFluid(1000), Materials.Ethanol.getFluid(1000)).fluidOutputs(Materials.Ethylene.getFluid(1000), Materials.DilutedSulfuricAcid.getFluid(1000)).buildAndRegister();
            
            RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder().duration(32).EUt(2).input("listAllseed", 1).fluidOutputs(Materials.SeedOil.getFluid(10)).buildAndRegister();

            for(ItemStack stack : OreDictionary.getOres("treeSapling")) {
            	RecipeMaps.BREWING_RECIPES.recipeBuilder().duration(800).EUt(3).inputs(stack).fluidInputs(Materials.Water.getFluid(100)).fluidOutputs(Materials.Biomass.getFluid(100)).buildAndRegister();
            }
            
    	}
    	
    	MaterialStack[] lubeDusts = {
                					 new MaterialStack(Materials.Talc, 1),
                					 new MaterialStack(Materials.Soapstone, 1),
                					 new MaterialStack(Materials.Redstone, 1)
                					};
    	
    	for(MaterialStack lubeDust : lubeDusts) {
    		DustMaterial dust = (DustMaterial) lubeDust.material;
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Materials.Oil.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Materials.Creosote.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            if (Loader.isModLoaded("forestry")) {
                RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Fluids.SEED_OIL.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            }
            else {
                RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(128).EUt(4).input(OrePrefix.dust, dust).fluidInputs(Materials.SeedOil.getFluid(750)).fluidOutputs(Materials.Lubricant.getFluid(750)).buildAndRegister();
            }
    	}
	}
    

    
    
    private static void initializeStoneBricksRecipes() {
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.LIGHT_CONCRETE, ConcreteVariant.LIGHT_BRICKS);
        registerBrickRecipe(MetaBlocks.CONCRETE, ConcreteVariant.DARK_CONCRETE, ConcreteVariant.DARK_CONCRETE);

        registerChiselingRecipes(MetaBlocks.CONCRETE);
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
}