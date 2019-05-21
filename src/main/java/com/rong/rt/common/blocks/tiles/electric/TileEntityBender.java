package com.rong.rt.common.blocks.tiles.electric;

import com.rong.rt.api.gui.GuiTextures;
import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.gui.widgets.ImageWidget;
import com.rong.rt.api.gui.widgets.LabelWidget;
import com.rong.rt.api.gui.widgets.ToggleButtonWidget;
import com.rong.rt.api.recipes.RecipeMap;
import com.rong.rt.api.recipes.handlers.FluidHandlerProxy;
import com.rong.rt.api.recipes.handlers.FluidTankList;
import com.rong.rt.api.recipes.handlers.ItemHandlerProxy;
import com.rong.rt.api.render.OrientedOverlayRenderer;
import com.rong.rt.api.render.Textures;
import com.rong.rt.api.tiles.TileEntityBaseMachine;
import com.rong.rt.common.recipes.RecipeMaps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBender extends TileEntityBaseMachine {
	
	protected IItemHandler outputItemInventory;
	protected IFluidHandler outputFluidInventory;

	public TileEntityBender() {
		super(1, RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY);
	}

	protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
		ModularUI.Builder builder = recipeMap
				.createUITemplate(this::getProgressPercent, importItems, exportItems, importFluids, exportFluids)
				.widget(new LabelWidget(5, 5, recipeMap.getLocalizedName()))
				.widget(new ImageWidget(79, 42, 18, 18, GuiTextures.INDICATOR_NO_ENERGY)
						.setPredicate(this::isHasNotEnoughEnergy))
				.bindPlayerInventory(player.inventory);

		int rightButtonStartX = 176 - 7 - 20;

		builder.widget(new ToggleButtonWidget(rightButtonStartX, 60, 20, 20, GuiTextures.BUTTON_OVERCLOCK,
				this::isOverclockable, this::setOverclockable).setTooltipText("rongtech.gui.overclock"));

		return builder;
	}

	@Override
	public ModularUI createUI(EntityPlayer entityPlayer) {
		return createGuiTemplate(entityPlayer).build(this, entityPlayer);
	}

}
