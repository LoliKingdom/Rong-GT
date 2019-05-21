package com.rong.rt.common.recipes;

import com.rong.rt.api.gui.GuiTextures;
import com.rong.rt.api.gui.widgets.ProgressWidget.MoveType;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.recipes.builders.IntCircuitRecipeBuilder;
import com.rong.rt.api.recipes.builders.SimpleRecipeBuilder;

import stanhebben.zenscript.annotations.ZenProperty;

public class RecipeMaps {

	@ZenProperty
	public static final RecipeMap<IntCircuitRecipeBuilder> BENDER_RECIPES = new RecipeMap<>("bender", 1, 2, 1, 1, 0, 0,
			0, 0, new IntCircuitRecipeBuilder()).setSlotOverlay(false, false, false, GuiTextures.BENDER_OVERLAY)
					.setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, MoveType.HORIZONTAL);
	
	@ZenProperty public static final RecipeMap<SimpleRecipeBuilder> AUTOCLAVE_RECIPES = new RecipeMap<>("autoclave", 1, 1, 1, 2, 1, 1, 0, 0, new SimpleRecipeBuilder())
	        .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
	        .setSlotOverlay(true, false, GuiTextures.CRYSTAL_OVERLAY)
	        .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, MoveType.HORIZONTAL);


}
