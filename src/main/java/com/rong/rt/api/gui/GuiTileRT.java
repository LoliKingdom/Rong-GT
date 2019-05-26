package com.rong.rt.api.gui;

import java.util.Arrays;
import java.util.Collections;

import com.rong.rt.Values;
import com.rong.rt.api.block.ContainerTileRT;
import com.rong.rt.api.tiles.TileBase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiTileRT<T extends TileBase> extends GuiContainer {

	public static final int GRAY_40 = 0x404040;
	public static final int GREEN_3E = 0x20EB3E;

	protected final T tile;
	final ResourceLocation guiBackground;

	protected GuiTileRT(ContainerTileRT inventorySlotsIn, T tile, String textureName) {
		super(inventorySlotsIn);
		this.tile = tile;
		this.guiBackground = new ResourceLocation(Values.MOD_ID, "textures/gui/" + textureName);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTick);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiBackground);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	/**
	 * Draw a fluid tank with given parameters. If the given tank is null, it will
	 * does nothing.
	 * 
	 * @param tank
	 *            The {@link IFluidTank} instance, will keep unmodified
	 * @param x
	 *            The starting x-coordinate of tank
	 * @param y
	 *            The starting y-coordinate of tank
	 * @param tankWidth
	 *            The fluid tank full width in the GUI texture
	 * @param tankHeight
	 *            The fluid tank full height in the GUI texture
	 */
	void renderFluidTank(final IFluidTank tank, final int x, final int y, final int tankWidth, final int tankHeight) {
		if(tank == null || tank.getFluid() == null) return;

		TextureAtlasSprite fluidSprite = this.mc.getTextureMapBlocks()
				.getAtlasSprite(tank.getFluid().getFluid().getStill().toString());
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		int scaledHeight = tankHeight * tank.getFluidAmount() / tank.getCapacity();
		this.drawTexturedModalRect(x, y + tankHeight - scaledHeight, fluidSprite, tankWidth, scaledHeight);
	}

	/**
	 * Draw fluid tooltip on given coordinate
	 * @param tank The {@link IFluidTank} instance, will keep unmodified
	 * @param x The {@link GuiContainer#drawGuiContainerForegroundLayer mouseX}
	 * @param y The {@link GuiContainer#drawGuiContainerForegroundLayer mouseY}
	 */
	void renderFluidTankTooltip(final IFluidTank tank, final int x, final int y) {
		FluidStack stack = tank.getFluid();
		if (stack != null) {
			String name = stack.getLocalizedName();
			int amount = stack.amount;
			String[] info = new String[] {I18n.format("gui.fluid.name", name), I18n.format("gui.fluid.amount", amount)};
			this.drawHoveringText(Arrays.asList(info), x - guiLeft, y - guiTop);
		} else {
			this.drawHoveringText(Collections.singletonList(I18n.format("gui.fluid.null")), x - guiLeft, y - guiTop);
		}
	}
}
