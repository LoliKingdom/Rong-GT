package com.rong.rt.api.block;

import java.util.ArrayList;
import java.util.List;

import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

import com.rong.rt.api.tiles.TileBase;

public final class ContainerTileRT extends Container {

	private final int tileInvCount;

	private ContainerTileRT(final int customSlotCount) {
		this.tileInvCount = customSlotCount;
	}

	// This is overridden to ensure that the Builder::addSlotToContainer call below
	// has access to this method; otherwise, it will fail at runtime even if it can
	// be compiled.
	@Override
	protected Slot addSlotToContainer(Slot slot) {
		return super.addSlotToContainer(slot);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	// TODO Fix the weird stack transfer
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(index < this.tileInvCount) {
				if(!this.mergeItemStack(itemstack1, this.tileInvCount, this.tileInvCount + 36, true)) {
					return ItemStack.EMPTY;
				}
			}
			else if(index >= this.tileInvCount && index < this.tileInvCount + 27) {
				if(!this.mergeItemStack(itemstack1, this.tileInvCount + 27, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			}
			else {
				if(!this.mergeItemStack(itemstack1, this.tileInvCount, this.tileInvCount + 27, false)) {
					return ItemStack.EMPTY;
				}
			}

			if(itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}

			if(itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	private void registerPlayerInventory(InventoryPlayer playerInv) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}

	public static final class Builder {

		public static Builder from(TileBase tile) {
			// That parameter is legacy remain.
			return new Builder();
		}

		private InventoryPlayer inventoryPlayer;
		private List<Slot> slots = new ArrayList<>(16); // Consider about the fact that A.C.R. has 13 slots
		private int nonPlayerSlotCounter = 0;

		private Builder() {
			// No-op
		}

		public Builder withPlayerInventory(InventoryPlayer inv) {
			this.inventoryPlayer = inv;
			return this;
		}

		public Builder withStandardSlot(IItemHandlerModifiable itemHandler, int index, int x, int y) {
			nonPlayerSlotCounter++;
			slots.add(new SlotRT(itemHandler, index, x, y));
			return this;
		}

		public Builder withOutputSlot(IItemHandlerModifiable itemHandler, int index, int x, int y) {
			nonPlayerSlotCounter++;
			slots.add(new SlotRT(itemHandler, index, x, y, SlotRT.OUTPUT));
			return this;
		}

		public Builder withChargerSlot(IItemHandlerModifiable itemHandler, int index, int x, int y) {
			nonPlayerSlotCounter++;
			slots.add(new SlotRT(itemHandler, index, x, y, stack -> !stack.isEmpty()
					&& ElectricItem.manager.charge(stack, Double.MAX_VALUE, Integer.MAX_VALUE, true, true) > 0));
			return this;
		}

		public Builder withDischargeSlot(IItemHandlerModifiable itemHandler, int index, int x, int y) {
			nonPlayerSlotCounter++;
			slots.add(new SlotRT(itemHandler, index, x, y, stack -> !stack.isEmpty() && ElectricItem.manager
					.discharge(stack, Double.MAX_VALUE, Integer.MAX_VALUE, true, true, true) > 0));
			return this;
		}

		public ContainerTileRT build() {
			final ContainerTileRT container = new ContainerTileRT(this.nonPlayerSlotCounter);
			slots.forEach(container::addSlotToContainer);
			if(inventoryPlayer != null) {
				container.registerPlayerInventory(inventoryPlayer);
			}
			slots = null;
			return container;
		}

	}

}