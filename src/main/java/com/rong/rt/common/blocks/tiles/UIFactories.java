package com.rong.rt.common.blocks.tiles;

import java.io.IOException;

import com.rong.rt.Values;
import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.gui.PlayerInventoryHolder;
import com.rong.rt.api.gui.UIFactory;
import com.rong.rt.common.blocks.tiles.electric.TileEntityAutoclave;
import com.rong.rt.common.blocks.tiles.electric.TileEntityBender;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UIFactories {

	public static class PlayerInventoryUIFactory extends UIFactory<PlayerInventoryHolder> {

		public static final PlayerInventoryUIFactory INSTANCE = new PlayerInventoryUIFactory();

		private PlayerInventoryUIFactory() {
		}

		public void init() {
			UIFactory.FACTORY_REGISTRY.register(0, new ResourceLocation(Values.MOD_ID, "player_inventory_factory"),
					this);
		}

		@Override
		protected ModularUI createUITemplate(PlayerInventoryHolder holder, EntityPlayer entityPlayer) {
			return holder.createUI(entityPlayer);
		}

		@Override
		@SideOnly(Side.CLIENT)
		protected PlayerInventoryHolder readHolderFromSyncData(PacketBuffer syncData) {
			EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
			EnumHand enumHand = EnumHand.values()[syncData.readByte()];
			ItemStack itemStack;
			try {
				itemStack = syncData.readItemStack();
			} catch(IOException exception) {
				throw new RuntimeException(exception);
			}
			return new PlayerInventoryHolder(entityPlayer, enumHand, itemStack);
		}

		@Override
		protected void writeHolderToSyncData(PacketBuffer syncData, PlayerInventoryHolder holder) {
			syncData.writeByte(holder.hand.ordinal());
			syncData.writeItemStack(holder.getCurrentItem());
		}
	}

	public static class UIAutoclave extends UIFactory<TileEntityAutoclave> {

		public static final UIAutoclave INSTANCE = new UIAutoclave();

		private UIAutoclave() {
		}

		public void init() {
			UIFactory.FACTORY_REGISTRY.register(1, new ResourceLocation(Values.MOD_ID, "autoclave_ui_factory"),
					this);
		}

		@Override
		protected ModularUI createUITemplate(TileEntityAutoclave holder, EntityPlayer entityPlayer) {
			return holder.createUI(entityPlayer);
		}

		@Override
		@SideOnly(Side.CLIENT)
		protected TileEntityAutoclave readHolderFromSyncData(PacketBuffer syncData) {
			return (TileEntityAutoclave) Minecraft.getMinecraft().world.getTileEntity(syncData.readBlockPos());
		}

		@Override
		protected void writeHolderToSyncData(PacketBuffer syncData, TileEntityAutoclave holder) {
			syncData.writeBlockPos(holder.getPos());
		}

	}

	public static class UIBender extends UIFactory<TileEntityBender> {

		public static final UIBender INSTANCE = new UIBender();

		private UIBender() {
		}

		public void init() {
			UIFactory.FACTORY_REGISTRY.register(2, new ResourceLocation(Values.MOD_ID, "bender_ui_factory"),
					this);
		}

		@Override
		protected ModularUI createUITemplate(TileEntityBender holder, EntityPlayer entityPlayer) {
			return holder.createUI(entityPlayer);
		}

		@Override
		@SideOnly(Side.CLIENT)
		protected TileEntityBender readHolderFromSyncData(PacketBuffer syncData) {
			return (TileEntityBender) Minecraft.getMinecraft().world.getTileEntity(syncData.readBlockPos());
		}

		@Override
		protected void writeHolderToSyncData(PacketBuffer syncData, TileEntityBender holder) {
			syncData.writeBlockPos(holder.getPos());
		}

	}

}
