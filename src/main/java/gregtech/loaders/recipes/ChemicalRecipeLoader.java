package gregtech.loaders.recipes;

import static gregtech.api.GTValues.L;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ChemicalRecipeLoader {
	
	public static void init() {
		
		ModHandler.removeRecipes(new ItemStack(Items.GOLDEN_APPLE));
		ModHandler.removeRecipes(new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		ModHandler.removeRecipes(Items.SPECKLED_MELON);
		ModHandler.removeRecipes(Items.GOLDEN_CARROT);
		ModHandler.removeRecipes(new ItemStack(Blocks.TNT));
		
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.MELON, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.nugget, Materials.Gold, 8).outputs(new ItemStack(Items.SPECKLED_MELON)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.CARROT, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.nugget, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_CARROT)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.ingot, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).inputs(new ItemStack(Items.APPLE, 1, OreDictionary.WILDCARD_VALUE)).input(OrePrefix.block, Materials.Gold, 8).outputs(new ItemStack(Items.GOLDEN_APPLE, 1, 1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(24).inputs(new ItemStack(Items.SUGAR, 4, OreDictionary.WILDCARD_VALUE)).fluidInputs(Materials.SulfuricAcid.getFluid(250), Materials.NitricAcid.getFluid(250)).outputs(new ItemStack(Blocks.TNT)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(190).fluidInputs(Materials.SulfuricAcid.getFluid(250), Materials.NitricAcid.getFluid(250), Materials.Toluene.getFluid(250)).outputs(new ItemStack(Blocks.TNT, 4)).buildAndRegister();
		
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(950).EUt(30).fluidInputs(Materials.Water.getFluid(2000), Materials.NitrogenDioxide.getFluid(4000), Materials.Oxygen.getFluid(1000)).fluidOutputs(Materials.NitricAcid.getFluid(4000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Ethylene.getFluid(1000), Materials.AceticAcid.getFluid(2000), Materials.Methanol.getFluid(1000)).fluidOutputs(Materials.Glue.getFluid(4000)).buildAndRegister();
        
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(30).input(OrePrefix.dust, Materials.Quicklime).fluidInputs(Materials.CarbonDioxide.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).input(OrePrefix.dust, Materials.Calcite).notConsumable(new IntCircuitIngredient(1)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Quicklime)).fluidOutputs(Materials.CarbonDioxide.getFluid(1000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Isoprene.getFluid(144), Materials.Air.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).fluidInputs(Materials.Isoprene.getFluid(144), Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(240).fluidInputs(Materials.Butadiene.getFluid(108), Materials.Styrene.getFluid(36), Materials.Air.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawStyreneButadieneRubber)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(240).fluidInputs(Materials.Butadiene.getFluid(108), Materials.Styrene.getFluid(36), Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawStyreneButadieneRubber, 3)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Materials.Propene.getFluid(2000)).fluidOutputs(Materials.Methane.getFluid(1000), Materials.Isoprene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(3500).EUt(30).input(OrePrefix.dust, Materials.Carbon).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Hydrogen.getFluid(4000)).fluidOutputs(Materials.Methane.getFluid(5000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(120).EUt(30).fluidInputs(Materials.Ethylene.getFluid(1000), Materials.Propene.getFluid(1000)).fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Isoprene.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(30).input(OrePrefix.dust, Materials.RawStyreneButadieneRubber, 9).input(OrePrefix.dust, Materials.Sulfur).fluidOutputs(Materials.StyreneButadieneRubber.getFluid(1296)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(8000).input(OrePrefix.dust, Materials.Sulfur, 1).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Oxygen.getFluid(4000)).fluidOutputs(Materials.SodiumPersulfate.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(2700).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.Water.getFluid(2000), Materials.Nitrogen.getFluid(1000)).fluidOutputs(Materials.Glyceryl.getFluid(4000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).fluidInputs(Materials.HydrogenSulfide.getFluid(2000), ModHandler.getWater(2000)).fluidOutputs(Materials.SulfuricAcid.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).input(OrePrefix.dust, Materials.Saltpeter, 1).fluidInputs(Materials.Naphtha.getFluid(576)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Potassium, 1)).fluidOutputs(Materials.Polycaprolactam.getFluid(1296)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(96).input(OrePrefix.dust, Materials.Silicon, 1).fluidInputs(Materials.Epichlorhydrin.getFluid(144)).fluidOutputs(Materials.Silicone.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Epichlorhydrin.getFluid(144), Materials.Naphtha.getFluid(3000), Materials.NitrogenDioxide.getFluid(1000)).fluidOutputs(Materials.Epoxid.getFluid(L * 2)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.NetherQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.CertusQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.CertusQuartz, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.Quartzite, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Water.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite,3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.NetherQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.DistilledWater.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.CertusQuartz, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.DistilledWater.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.CertusQuartz, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.Quartzite, 3).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.DistilledWater.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite, 3)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).input(OrePrefix.dust, Materials.Uraninite, 1).input(OrePrefix.dust, Materials.Aluminium, 1).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Uranium, 1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).input(OrePrefix.dust, Materials.Uraninite, 1).input(OrePrefix.dust, Materials.Magnesium, 1).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Uranium, 1)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).input(OrePrefix.dust, Materials.Calcium, 1).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.Oxygen.getFluid(3000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 5)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1150).input(OrePrefix.dust, Materials.Sulfur, 1).fluidInputs(Materials.Water.getFluid(2000)).fluidOutputs(Materials.SulfuricAcid.getFluid(3000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).EUt(30).input(OrePrefix.crushedPurified, Materials.Chalcopyrite).fluidInputs(Materials.NitricAcid.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.PlatinumGroupSludge)).fluidOutputs(Materials.BlueVitriolSolution.getFluid(9000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(50).EUt(30).input(OrePrefix.crushedPurified, Materials.Pentlandite).fluidInputs(Materials.NitricAcid.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.PlatinumGroupSludge)).fluidOutputs(Materials.NickelSulfateSolution.getFluid(9000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(8000).input(OrePrefix.dust, Materials.Sulfur, 1).input(OrePrefix.dust, Materials.Sodium, 1).fluidInputs(Materials.Oxygen.getFluid(4000)).fluidOutputs(Materials.SodiumPersulfate.getFluid(6000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(2700).input(OrePrefix.dust, Materials.Carbon, 1).fluidInputs(Materials.Water.getFluid(2000), Materials.Nitrogen.getFluid(1000)).fluidOutputs(Materials.Glyceryl.getFluid(4000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(480).input(OrePrefix.dust, Materials.Rutile, 1).input(OrePrefix.dust, Materials.Carbon, 3).fluidInputs(Materials.Chlorine.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Ash,1)).fluidOutputs(Materials.TitaniumTetrachloride.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(300).EUt(240).input(OrePrefix.dust, Materials.Sodium, 1).input(OrePrefix.dust, Materials.MagnesiumChloride, 2).outputs(OreDictUnifier.get(OrePrefix.dustSmall,Materials.Magnesium,6)).fluidOutputs(Materials.Chlorine.getFluid(1500)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(600).EUt(16).input(OrePrefix.dust, Materials.RawRubber, 9).input(OrePrefix.dust, Materials.Sulfur, 1).fluidOutputs(Materials.Rubber.getFluid(1296)).buildAndRegister();
	
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(280).EUt(96).input(OrePrefix.dust, Materials.Sulfur).fluidInputs(Materials.NitricAcid.getFluid(1000)).fluidOutputs(Materials.SulfurDioxide.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(140).EUt(24).fluidInputs(Materials.SulfurDioxide.getFluid(1000), Materials.HydrogenSulfide.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 3)).fluidOutputs(ModHandler.getWater(2000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(140).EUt(24).fluidInputs(Materials.SulfurDioxide.getFluid(2000), ModHandler.getWater(2000)).fluidOutputs(Materials.Oxygen.getFluid(1000), Materials.SulfuricAcid.getFluid(2000)).buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(60000).EUt(8).input(OrePrefix.ingot, Materials.Plutonium239, 3).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Plutonium239, 3)).fluidOutputs(Materials.Radon.getFluid(50)).buildAndRegister();
        
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Air.getFluid(1000), Materials.Tetrafluoroethylene.getFluid(144)).fluidOutputs(Materials.Polytetrafluoroethylene.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.Tetrafluoroethylene.getFluid(144)).fluidOutputs(Materials.Polytetrafluoroethylene.getFluid(216)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Air.getFluid(7500), Materials.Tetrafluoroethylene.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.Polytetrafluoroethylene.getFluid(3240)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(7500), Materials.Tetrafluoroethylene.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.Polytetrafluoroethylene.getFluid(4320)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(30).input(OrePrefix.dust, Materials.SodiumHydroxide).fluidInputs(Materials.Epichlorhydrin.getFluid(1000), Materials.BisphenolA.getFluid(1000)).fluidOutputs(Materials.Epoxid.getFluid(1000), Materials.SaltWater.getFluid(1000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(500).EUt(480).input(OrePrefix.dust, Materials.Carbon, 2).input(OrePrefix.dust, Materials.Rutile).fluidInputs(Materials.Chlorine.getFluid(4000)).fluidOutputs(Materials.CarbonMonoxide.getFluid(2000), Materials.TitaniumTetrachloride.getFluid(1000)).buildAndRegister();
		
        //Glue
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(8).fluidInputs(Materials.PolyvinylAcetate.getFluid(1000), Materials.Acetone.getFluid(1500)).fluidOutputs(Materials.Glue.getFluid(2500)).buildAndRegister();
		RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(8).fluidInputs(Materials.PolyvinylAcetate.getFluid(1000), Materials.MethylAcetate.getFluid(1500)).fluidOutputs(Materials.Glue.getFluid(2500)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Air.getFluid(1000), Materials.VinylAcetate.getFluid(144)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(144)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.VinylAcetate.getFluid(144)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(216)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Air.getFluid(7500), Materials.VinylAcetate.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(3240)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(7500), Materials.VinylAcetate.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(4320)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(180).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.AceticAcid.getFluid(1000), Materials.Ethylene.getFluid(1000)).fluidOutputs(Materials.Water.getFluid(1000), Materials.VinylAcetate.getFluid(1000)).buildAndRegister();	
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(120).input(OrePrefix.dust, Materials.Barite).input(OrePrefix.gem, Materials.Coke, 2).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BariumSulfide)).fluidOutputs(Materials.CarbonDioxide.getFluid(2000)).buildAndRegister();
		RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(400).EUt(2).fluidInputs(Materials.CarbonDioxide.getFluid(1000)).input(OrePrefix.dust, Materials.BariumSulfide).fluidOutputs(Materials.Witherite.getFluid(1000)).buildAndRegister();
		ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Witherite), OreDictUnifier.get(OrePrefix.dust, Materials.BariumOxide));
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(120).input(OrePrefix.dust, Materials.BariumOxide, 2).fluidInputs(Materials.Oxygen.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.BariumPeroxide)).buildAndRegister();

		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(14).fluidInputs(Materials.SulfuricAcid.getFluid(1000), Materials.Ammonia.getFluid(2000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumSulfate)).buildAndRegister();
		RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder().duration(700).EUt(55).fluidInputs(Materials.SulfuricAcid.getFluid(1000), Materials.AmmoniumSulfate.getFluid(1000)).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.AmmoniumPersulfate)).buildAndRegister();
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(74).fluidInputs(Materials.Chloroform.getFluid(1000), Materials.HydrofluoricAcid.getFluid(2000)).fluidOutputs(Materials.Chlorodifluoromethane.getFluid(1000) ,Materials.HydrochloricAcid.getFluid(2000)).buildAndRegister();
		RecipeMaps.PYROLYSE_RECIPES.recipeBuilder().input(OrePrefix.log, Materials.Wood, 4).fluidInputs(Materials.Chlorodifluoromethane.getFluid(2000)).outputs(new ItemStack(Items.COAL, 5, 1)).fluidOutputs(Materials.Tetrafluoroethylene.getFluid(1000)).duration(320).EUt(96).buildAndRegister();
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(14).fluidInputs(Materials.Chlorine.getFluid(6000)).input(OrePrefix.dust, Materials.Iron, 2).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.FerricChloride, 2)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(28).fluidInputs(Materials.HydrochloricAcid.getFluid(2000)).input(OrePrefix.dust, Materials.Iron, 2).outputs(OreDictUnifier.get(OrePrefix.dust, Materials.FerrousChloride)).fluidOutputs(Materials.Hydrogen.getFluid(2000)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(28).fluidInputs(Materials.HydrochloricAcid.getFluid(8000)).input(OrePrefix.dust, Materials.Magnetite).fluidOutputs(Materials.FerrousChloride.getFluid(1000), Materials.FerricChloride.getFluid(2000), Materials.Water.getFluid(4000)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(55).EUt(8).fluidInputs(Materials.FerrousChloride.getFluid(2000), Materials.Chlorine.getFluid(2000)).fluidOutputs(Materials.FerricChloride.getFluid(2000)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(55).EUt(8).fluidInputs(Materials.FerrousChloride.getFluid(4000), Materials.Oxygen.getFluid(2000), Materials.HydrochloricAcid.getFluid(4000)).fluidOutputs(Materials.FerricChloride.getFluid(4000), Materials.Water.getFluid(2000)).buildAndRegister();
		RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(300).EUt(55).fluidInputs(Materials.Chlorine.getFluid(2000), Materials.Ethylene.getFluid(1000)).input(OrePrefix.dust, Materials.FerricChloride).fluidOutputs(Materials.EthyleneDichloride.getFluid(1000)).buildAndRegister();
		RecipeMaps.FLUID_HEATER_RECIPES.recipeBuilder().duration(400).EUt(88).fluidInputs(Materials.Chlorine.getFluid(6000), Materials.EthyleneDichloride.getFluid(1000)).fluidOutputs(Materials.Tetrachloroethylene.getFluid(1000), Materials.HydrochloricAcid.getFluid(4000)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(80).EUt(320).fluidInputs(Materials.Tetrachloroethylene.getFluid(2000), Materials.HydrofluoricAcid.getFluid(1000), Materials.Chloroform.getFluid(500)).fluidOutputs(Materials.DichloroTrifluoroethane.getFluid(1500), Materials.Trichlorotrifluoroethane.getFluid(2000)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(180).fluidInputs(Materials.Methanol.getFluid(2000), Materials.Trichlorotrifluoroethane.getFluid(2000), Materials.Zinc.getFluid(288)).fluidOutputs(Materials.Chlorotrifluoroethylene.getFluid(2000), Materials.Chlorine.getFluid(2000)).buildAndRegister();
		
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1000).EUt(96).fluidInputs(Materials.Propene.getFluid(2000), Materials.Ammonia.getFluid(2000), Materials.Air.getFluid(3000)).fluidOutputs(Materials.SOHIOMixture.getFluid(1000), Materials.Water.getFluid(6000)).buildAndRegister();
		RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(30).EUt(120).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.SOHIOMixture.getFluid(1000)).fluidOutputs(Materials.Acrylonitrile.getFluid(500)).buildAndRegister();
		RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(30).EUt(120).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.SOHIOMixture.getFluid(1000)).fluidOutputs(Materials.Acetonitrile.getFluid(200)).buildAndRegister();
		RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(30).EUt(120).notConsumable(new IntCircuitIngredient(2)).fluidInputs(Materials.SOHIOMixture.getFluid(1000)).fluidOutputs(Materials.HydrocyanicAcid.getFluid(200)).buildAndRegister();
		RecipeMaps.DISTILLERY_RECIPES.recipeBuilder().duration(30).EUt(120).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Materials.SOHIOMixture.getFluid(1000)).fluidOutputs(Materials.AmmoniumSulfate.getFluid(100)).buildAndRegister();
		RecipeMaps.DISTILLATION_RECIPES.recipeBuilder().duration(120).EUt(120).fluidInputs(Materials.SOHIOMixture.getFluid(1000)).fluidOutputs(Materials.Acrylonitrile.getFluid(500), Materials.Acetonitrile.getFluid(200), Materials.HydrocyanicAcid.getFluid(200), Materials.AmmoniumSulfate.getFluid(100)).buildAndRegister();
		
		//Polymerization
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1400).EUt(96).fluidInputs(Materials.Acrylonitrile.getFluid(500), Materials.Butadiene.getFluid(500), Materials.Styrene.getFluid(500), Materials.Air.getFluid(1500)).fluidOutputs(Materials.AcrylonitrileButadieneStyrene.getFluid(L * 3)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(1200).EUt(27421).fluidInputs(Materials.Chlorotrifluoroethylene.getFluid(1000), Materials.BariumPeroxide.getFluid(5000)).fluidOutputs(Materials.Polychlorotrifluoroethylene.getFluid(L * 6)).buildAndRegister();

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
}
