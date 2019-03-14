package gregtech.loaders;

import gregtech.loaders.recipes.AssemblyLineRecipeLoader;
import gregtech.loaders.recipes.BedrockDrillRecipeLoader;
import gregtech.loaders.recipes.ChemicalRecipeLoader;
import gregtech.loaders.recipes.CraftingRecipeLoader;
import gregtech.loaders.recipes.DyeRecipeLoader;
import gregtech.loaders.recipes.MachineRecipeLoader;
import gregtech.loaders.recipes.MatterManipulationRecipeLoader;
import gregtech.loaders.recipes.MetaTileEntityRecipeLoader;
import gregtech.loaders.recipes.RecyclingRecipeLoader;
import gregtech.loaders.recipes.WoodRecipeLoader;
import gregtech.loaders.recipes.processing.DecompositionRecipeHandler;
import gregtech.loaders.recipes.processing.MaterialRecipeHandler;
import gregtech.loaders.recipes.processing.OreRecipeHandler;
import gregtech.loaders.recipes.processing.PartsRecipeHandler;
import gregtech.loaders.recipes.processing.PipeRecipeHandler;
import gregtech.loaders.recipes.processing.PolarizingRecipeHandler;
import gregtech.loaders.recipes.processing.RecyclingRecipeHandler;
import gregtech.loaders.recipes.processing.ToolRecipeHandler;
import gregtech.loaders.recipes.processing.WireRecipeHandler;

public class RecipeLoaders {
	
	public static void init() {
		
		MaterialRecipeHandler.register();
        OreRecipeHandler.register();
        PartsRecipeHandler.register();
        WireRecipeHandler.register();
        PipeRecipeHandler.register();
        ToolRecipeHandler.register();
        PolarizingRecipeHandler.register();
        RecyclingRecipeHandler.register();
        DecompositionRecipeHandler.runRecipeGeneration();
		
		MachineRecipeLoader.init();
		MetaTileEntityRecipeLoader.init();
		CraftingRecipeLoader.init();
		AssemblyLineRecipeLoader.init();
		BedrockDrillRecipeLoader.init();
		MatterManipulationRecipeLoader.init();
		RecyclingRecipeLoader.init();
		DyeRecipeLoader.init();
		ChemicalRecipeLoader.init();
		WoodRecipeLoader.init();
		
		FuelLoader.registerFuels();
	}
}