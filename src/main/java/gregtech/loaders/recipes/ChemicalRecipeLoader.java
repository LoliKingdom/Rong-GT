package gregtech.loaders.recipes;

import gregtech.api.GTValues;
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
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(24).inputs(MetaItems.GELLED_TOLUENE.getStackForm(4)).fluidInputs(Materials.SulfuricAcid.getFluid(250)).outputs(new ItemStack(Blocks.TNT)).buildAndRegister();
		
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

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(140).EUt(24).inputs(new ItemStack(Items.SUGAR)).input(OrePrefix.dustTiny, Materials.Plastic, 1).fluidInputs(Materials.Toluene.getFluid(133)).outputs(MetaItems.GELLED_TOLUENE.getStackForm(2)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(320).fluidInputs(Materials.HydrogenSulfide.getFluid(2000), ModHandler.getWater(2000)).fluidOutputs(Materials.SulfuricAcid.getFluid(3000)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(640).input(OrePrefix.dust, Materials.Saltpeter, 1).fluidInputs(Materials.Naphtha.getFluid(576)).outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Potassium, 1)).fluidOutputs(Materials.Polycaprolactam.getFluid(1296)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(96).input(OrePrefix.dust, Materials.Silicon, 1).fluidInputs(Materials.Epichlorhydrin.getFluid(144)).fluidOutputs(Materials.Silicone.getFluid(144)).buildAndRegister();
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(30).fluidInputs(Materials.Epichlorhydrin.getFluid(144), Materials.Naphtha.getFluid(3000), Materials.NitrogenDioxide.getFluid(1000)).fluidOutputs(Materials.Epoxid.getFluid(GTValues.L * 2)).buildAndRegister();

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
		
        //Glue
        RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(8).fluidInputs(Materials.PolyvinylAcetate.getFluid(1000), Materials.Acetone.getFluid(1500)).fluidOutputs(Materials.Glue.getFluid(2500)).buildAndRegister();
		RecipeMaps.MIXER_RECIPES.recipeBuilder().duration(50).EUt(8).fluidInputs(Materials.PolyvinylAcetate.getFluid(1000), Materials.MethylAcetate.getFluid(1500)).fluidOutputs(Materials.Glue.getFluid(2500)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Air.getFluid(1000), Materials.VinylAcetate.getFluid(144)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(144)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(160).EUt(30).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.VinylAcetate.getFluid(144)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(216)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Air.getFluid(7500), Materials.VinylAcetate.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(3240)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(800).EUt(30).notConsumable(new IntCircuitIngredient(1)).fluidInputs(Materials.Oxygen.getFluid(7500), Materials.VinylAcetate.getFluid(2160), Materials.TitaniumTetrachloride.getFluid(100)).fluidOutputs(Materials.PolyvinylAcetate.getFluid(4320)).buildAndRegister();
		RecipeMaps.CHEMICAL_RECIPES.recipeBuilder().duration(180).EUt(30).notConsumable(new IntCircuitIngredient(3)).fluidInputs(Materials.Oxygen.getFluid(1000), Materials.AceticAcid.getFluid(1000), Materials.Ethylene.getFluid(1000)).fluidOutputs(Materials.Water.getFluid(1000), Materials.VinylAcetate.getFluid(1000)).buildAndRegister();
	}
}
