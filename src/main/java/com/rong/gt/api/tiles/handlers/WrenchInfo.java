package com.rong.gt.api.tiles.handlers;

import ic2.api.classic.tile.IInfoTile;
import ic2.api.classic.tile.IInfoTile.InfoType;
import ic2.core.block.base.tile.TileEntityBlock;
import ic2.core.block.base.util.info.misc.IProbeComponent;
import ic2.core.item.tool.ItemToolWrench;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import java.awt.Color;

import com.rong.gt.api.tiles.TileEntityEUBase;

import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Optional.Method;

//Custom implementation, it only takes TileEntityBlock in IC2 ? ? ?
public class WrenchInfo implements IProbeComponent {
	
	TileEntityEUBase block;

	public WrenchInfo(TileEntityEUBase tile) {
		this.block = tile;
	}

	public String getDisplayText(EntityPlayer player) {
		//canRemoveBlockProbe not implemented
		if (this.block.canRemoveBlock(player)) {
			int rate = MathHelper.clamp((int)(getWrenchLoss(player) * 100.0D), 0, 100);
			return Ic2InfoLang.lossChance.getLocalizedFormatted(new Object[] { rate + "%" });
		}
		return "";
	}

	public boolean canShow(EntityPlayer player) {
		return PlayerHandler.getClientPlayerHandler().hasWrench();
	}

	public double getWrenchLoss(EntityPlayer player) {
		double chance = this.block.getWrenchDropRate();
		ItemStack stack = player.getHeldItemMainhand();
		if(stack == null) {
			stack = player.getHeldItemOffhand();
		}
		if((stack != null) && ((stack.getItem() instanceof ItemToolWrench))) {
			ItemToolWrench wrench = (ItemToolWrench)stack.getItem();
			if (wrench.canOverrideLossChance(stack)) {
				chance = 1.0D;
			} else {
				chance *= wrench.applyFortune(stack, wrench.getModifier(stack));
			}
		}
		return chance;
	}

	public IInfoTile.InfoType getType() {
		return IInfoTile.InfoType.Custom;
	}

    @Method(modid = "theoneprobe")
	public boolean isModeValid(ProbeMode mode, PlayerHandler handler) {
		return (mode != ProbeMode.NORMAL) && (handler.hasWrench());
	}

	@Method(modid = "theoneprobe")
	public void applyInfo(IProbeInfo info, EntityPlayer player, EnumFacing side) {
		if(this.block.canRemoveBlock(player)) {
			info.progress(MathHelper.clamp((int)(getWrenchLoss(player) * 100.0D), 0, 100), 100,
					info.defaultProgressStyle().filledColor(Color.RED.getRGB()).prefix("Drop Chance: "));
		}
	}
}
