package com.rong.rt.common.items.behaviour;

import java.util.List;

import com.rong.rt.api.gui.GuiTextures;
import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.gui.PlayerInventoryHolder;
import com.rong.rt.api.gui.widgets.ClickButtonWidget;
import com.rong.rt.api.gui.widgets.DynamicLabelWidget;
import com.rong.rt.api.metaitems.interfaces.IItemBehaviour;
import com.rong.rt.api.metaitems.interfaces.IItemUIFactory;
import com.rong.rt.api.recipes.ingredients.IntCircuitIngredient;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class IntCircuitBehaviour implements IItemBehaviour, IItemUIFactory {

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        int configuration = IntCircuitIngredient.getCircuitConfiguration(itemStack);
        lines.add(I18n.format("metaitem.int_circuit.configuration", configuration));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!world.isRemote) {
            PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
            holder.openUI();
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder holder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND_SMALL, 176, 60)
            .label(9, 8, "metaitem.circuit.integrated.gui")
            .widget(new DynamicLabelWidget(82, 30, () -> Integer.toString(IntCircuitIngredient.getCircuitConfiguration(holder.getCurrentItem())), 0x404040))
            .widget(new ClickButtonWidget(15, 24, 20, 20, "-5", data -> adjustConfiguration(holder, -5)))
            .widget(new ClickButtonWidget(50, 24, 20, 20, "-1", data -> adjustConfiguration(holder, -1)))
            .widget(new ClickButtonWidget(104, 24, 20, 20, "+1", data -> adjustConfiguration(holder, +1)))
            .widget(new ClickButtonWidget(141, 24, 20, 20, "+5", data -> adjustConfiguration(holder, +5)))
            .build(holder, entityPlayer);
    }

    private static void adjustConfiguration(PlayerInventoryHolder holder, int amount) {
        ItemStack stack = holder.getCurrentItem();
        int configuration = IntCircuitIngredient.getCircuitConfiguration(stack);
        configuration += amount;
        configuration = MathHelper.clamp(configuration, 0, 32);
        IntCircuitIngredient.setCircuitConfiguration(stack, configuration);
        holder.markAsDirty();
    }
}
