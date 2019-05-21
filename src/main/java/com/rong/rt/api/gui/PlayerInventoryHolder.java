package com.rong.rt.api.gui;

import com.google.common.base.Preconditions;
import com.rong.rt.api.metaitems.interfaces.IItemUIFactory;
import com.rong.rt.common.blocks.tiles.UIFactories;
import com.rong.rt.common.blocks.tiles.UIFactories.PlayerInventoryUIFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerInventoryHolder implements IUIHolder {

    public final EntityPlayer player;
    /*package-local*/ public final EnumHand hand;
    /*package-local*/ public ItemStack sampleItem;

    @SideOnly(Side.CLIENT)
	public
        /*package-local*/ PlayerInventoryHolder(EntityPlayer player, EnumHand hand, ItemStack sampleItem) {
        this.player = player;
        this.hand = hand;
        this.sampleItem = sampleItem;
    }

    public PlayerInventoryHolder(EntityPlayer entityPlayer, EnumHand hand) {
        this.player = entityPlayer;
        this.hand = hand;
        this.sampleItem = player.getHeldItem(hand);
        Preconditions.checkArgument(sampleItem.getItem() instanceof IItemUIFactory,
            "Current Item should implement ItemUIFactory");
    }

    public static void openHandItemUI(EntityPlayer player, EnumHand hand) {
        PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
        holder.openUI();
    }

    /*package-local*/ public ModularUI createUI(EntityPlayer entityPlayer) {
        IItemUIFactory uiFactory = (IItemUIFactory) sampleItem.getItem();
        return uiFactory.createUI(this, entityPlayer);
    }

    public void openUI() {
        UIFactories.PlayerInventoryUIFactory.INSTANCE.openUI(this, (EntityPlayerMP) player);
    }

    @Override
    public boolean isValid() {
        ItemStack itemStack = player.getHeldItem(hand);
        return ItemStack.areItemsEqual(sampleItem, itemStack);
    }

    @Override
    public boolean isRemote() {
        return player.getEntityWorld().isRemote;
    }

    public ItemStack getCurrentItem() {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!ItemStack.areItemsEqual(sampleItem, itemStack))
            return null;
        return itemStack;
    }

    /**
     * Will replace current item in hand with the given one
     * will also update sample item to this item
     */
    public void setCurrentItem(ItemStack item) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!ItemStack.areItemsEqual(sampleItem, itemStack))
            return;
        Preconditions.checkArgument(item.getItem() instanceof IItemUIFactory,
            "Current Item should implement ItemUIFactory");
        this.sampleItem = item;
        player.setHeldItem(hand, item);
    }

    @Override
    public void markAsDirty() {
        player.inventory.markDirty();
        player.inventoryContainer.detectAndSendChanges();
    }
}
