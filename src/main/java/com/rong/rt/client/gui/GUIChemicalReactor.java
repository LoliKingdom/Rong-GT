package com.rong.rt.client.gui;

import java.util.Collections;

import com.rong.rt.api.block.ContainerTileRT;
import com.rong.rt.api.gui.GuiTileRT;
import com.rong.rt.common.tiles.chemical_reactor.TileChemicalReactor;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIChemicalReactor extends GuiTileRT<TileChemicalReactor> {

	public GUIChemicalReactor(ContainerTileRT container, TileChemicalReactor tile) {
		super(container, tile, "chemical_reactor.png");
	}
	
	/*
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}*/

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		if (tile.isWorking() && tile.getMaxProgress() != 0) {
		}
	}

}
