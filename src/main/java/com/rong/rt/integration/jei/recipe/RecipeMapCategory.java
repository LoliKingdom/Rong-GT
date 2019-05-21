package com.rong.rt.integration.jei.recipe;

import java.util.List;

import com.rong.rt.Values;
import com.rong.rt.api.gui.BlankUIHolder;
import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.gui.Widget;
import com.rong.rt.api.gui.widgets.SlotWidget;
import com.rong.rt.api.gui.widgets.TankWidget;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.recipes.handlers.FluidTankList;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class RecipeMapCategory implements IRecipeCategory<RTRecipeWrapper> {

	private final RecipeMap<?> recipeMap;
	private final ModularUI modularUI;
	private ItemStackHandler importItems, exportItems;
	private FluidTankList importFluids, exportFluids;
	private final IDrawable backgroundDrawable;

	public RecipeMapCategory(RecipeMap<?> recipeMap, IGuiHelper guiHelper) {
		this.recipeMap = recipeMap;
		FluidTank[] importFluidTanks = new FluidTank[recipeMap.getMaxFluidInputs()];
		for(int i = 0; i < importFluidTanks.length; i++)
			importFluidTanks[i] = new FluidTank(16000);
		FluidTank[] exportFluidTanks = new FluidTank[recipeMap.getMaxFluidOutputs()];
		for(int i = 0; i < exportFluidTanks.length; i++)
			exportFluidTanks[i] = new FluidTank(16000);
		this.modularUI = recipeMap
				.createJeiUITemplate((importItems = new ItemStackHandler(recipeMap.getMaxInputs())),
						(exportItems = new ItemStackHandler(recipeMap.getMaxOutputs())),
						(importFluids = new FluidTankList(false, importFluidTanks)),
						(exportFluids = new FluidTankList(false, exportFluidTanks)))
				.build(new BlankUIHolder(), Minecraft.getMinecraft().player);
		this.modularUI.initWidgets();
		this.backgroundDrawable = guiHelper.createBlankDrawable(modularUI.getWidth(), modularUI.getHeight() * 2 / 3);
	}

	@Override
	public String getUid() {
		return Values.MOD_ID + ":" + recipeMap.unlocalizedName;
	}

	@Override
	public String getTitle() {
		return recipeMap.getLocalizedName();
	}

	@Override
	public String getModName() {
		return Values.MOD_ID;
	}

	@Override
	public IDrawable getBackground() {
		return backgroundDrawable;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RTRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
		for(Widget uiWidget : modularUI.guiWidgets.values()) {

			if(uiWidget instanceof SlotWidget) {
				SlotWidget slotWidget = (SlotWidget) uiWidget;
				if(slotWidget.getHandle().getItemHandler() == importItems) {
					// this is input item stack slot widget, so add it to item group
					itemStackGroup.init(slotWidget.getHandle().getSlotIndex(), true, slotWidget.getXPosition() - 1,
							slotWidget.getYPosition() - 1);
				}
				else if(slotWidget.getHandle().getItemHandler() == exportItems) {
					// this is output item stack slot widget, so add it to item group
					itemStackGroup.init(importItems.getSlots() + slotWidget.getHandle().getSlotIndex(), false,
							slotWidget.getXPosition() - 1, slotWidget.getYPosition() - 1);
				}

			}
			else if(uiWidget instanceof TankWidget) {
				TankWidget tankWidget = (TankWidget) uiWidget;
				if(importFluids.getFluidTanks().contains(tankWidget.fluidTank)) {
					int importIndex = importFluids.getFluidTanks().indexOf(tankWidget.fluidTank);
					List<List<FluidStack>> inputsList = ingredients.getInputs(FluidStack.class);
					int fluidAmount = 0;
					if(inputsList.size() > importIndex && !inputsList.get(importIndex).isEmpty())
						fluidAmount = inputsList.get(importIndex).get(0).amount;
					// this is input tank widget, so add it to fluid group
					fluidStackGroup.init(importIndex, true, tankWidget.getXPosition() + tankWidget.fluidRenderOffset,
							tankWidget.getYPosition() + tankWidget.fluidRenderOffset,
							tankWidget.getWidth() - tankWidget.fluidRenderOffset,
							tankWidget.getHeight() - tankWidget.fluidRenderOffset, fluidAmount, false, null);

				}
				else if(exportFluids.getFluidTanks().contains(tankWidget.fluidTank)) {
					int exportIndex = exportFluids.getFluidTanks().indexOf(tankWidget.fluidTank);
					List<List<FluidStack>> inputsList = ingredients.getOutputs(FluidStack.class);
					int fluidAmount = 0;
					if(inputsList.size() > exportIndex && !inputsList.get(exportIndex).isEmpty())
						fluidAmount = inputsList.get(exportIndex).get(0).amount;
					// this is output tank widget, so add it to fluid group
					fluidStackGroup.init(importFluids.getFluidTanks().size() + exportIndex, false,
							tankWidget.getXPosition() + tankWidget.fluidRenderOffset,
							tankWidget.getYPosition() + tankWidget.fluidRenderOffset,
							tankWidget.getWidth() - tankWidget.fluidRenderOffset,
							tankWidget.getHeight() - tankWidget.fluidRenderOffset, fluidAmount, false, null);

				}
			}
		}
		itemStackGroup.addTooltipCallback(recipeWrapper::addTooltip);
		fluidStackGroup.addTooltipCallback(recipeWrapper::addTooltip);
		itemStackGroup.set(ingredients);
		fluidStackGroup.set(ingredients);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		for(Widget widget : modularUI.guiWidgets.values()) {
			widget.drawInBackground(0, 0);
			widget.drawInForeground(0, 0);
		}
	}
}
