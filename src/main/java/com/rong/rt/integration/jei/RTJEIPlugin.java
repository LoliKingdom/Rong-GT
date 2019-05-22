package com.rong.rt.integration.jei;

import java.util.List;
import java.util.stream.Collectors;

import com.rong.rt.Values;
import com.rong.rt.api.gui.impl.ModularUIGuiHandler;
import com.rong.rt.api.metaitems.MetaItem;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.common.blocks.RTBlocks;
import com.rong.rt.common.items.MetaItems;
import com.rong.rt.common.loaders.recipes.CustomItemReturnShapedOreRecipe;
import com.rong.rt.common.recipes.RecipeMaps;
import com.rong.rt.integration.jei.recipe.RTRecipeWrapper;
import com.rong.rt.integration.jei.recipe.RecipeMapCategory;
import com.rong.rt.integration.jei.utils.CustomItemReturnRecipeWrapper;
import com.rong.rt.integration.jei.utils.MetadataAwareFluidHandlerSubtype;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class RTJEIPlugin implements IModPlugin {

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		MetadataAwareFluidHandlerSubtype subtype = new MetadataAwareFluidHandlerSubtype();
		for(MetaItem<?> metaItem : MetaItems.ITEMS) {
			subtypeRegistry.registerSubtypeInterpreter(metaItem, subtype);
		}
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		for(RecipeMap<?> recipeMap : RecipeMap.getRecipeMaps()) {
			registry.addRecipeCategories(new RecipeMapCategory(recipeMap, registry.getJeiHelpers().getGuiHelper()));
		}
	}

	@Override
	public void register(IModRegistry registry) {

		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		registry.handleRecipes(CustomItemReturnShapedOreRecipe.class,
				recipe -> new CustomItemReturnRecipeWrapper(jeiHelpers, recipe), VanillaRecipeCategoryUid.CRAFTING);

		ModularUIGuiHandler modularUIGuiHandler = new ModularUIGuiHandler();
		registry.addAdvancedGuiHandlers(modularUIGuiHandler);
		registry.addGhostIngredientHandler(modularUIGuiHandler.getGuiContainerClass(), modularUIGuiHandler);

		for(RecipeMap<?> recipeMap : RecipeMap.getRecipeMaps()) {
			List<RTRecipeWrapper> recipesList = recipeMap.getRecipeList().stream()
					.filter(recipe -> !recipe.isHidden() && recipe.hasValidInputsForDisplay())
					.map(r -> new RTRecipeWrapper(recipeMap, r)).collect(Collectors.toList());
			registry.addRecipes(recipesList, Values.MOD_ID + ":" + recipeMap.unlocalizedName);
		}

	}

}
