package com.rong.rt.common.items.behaviour;

import java.util.List;

import com.rong.rt.api.metaitems.interfaces.IItemBehaviour;
import com.rong.rt.api.utils.Utility;

import ic2.api.classic.audio.PositionSpec;
import ic2.api.classic.tile.ISpecialWrenchable;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.base.util.info.misc.IWrench;
import ic2.core.platform.registry.Ic2Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WrenchBehaviour implements IItemBehaviour, IWrench {

	private final int cost;

	public WrenchBehaviour(int cost) {
		this.cost = cost;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	public boolean canOverrideLossChance(ItemStack stack) {
		return false;
	}

	public void onLossPrevented(EntityPlayer player, ItemStack stack) {
	}

	public boolean hasBigCost(ItemStack stack) {
		return true;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(!Utility.doDamageItem(stack, cost, true)) {
			return EnumActionResult.PASS;
		}
		IBlockState state = world.getBlockState(pos);
		if(!(state.getBlock() instanceof IWrenchable)) {
			return EnumActionResult.PASS;
		}
		boolean server = IC2.platform.isSimulating();
		IWrenchable wrenchBlock = (IWrenchable) state.getBlock();
		EnumFacing facing = wrenchBlock.getFacing(world, pos);
		if(facing != null) {
			EnumFacing newSide = side;
			if(IC2.keyboard.isAltKeyDown(player)) {
				if(player.isSneaking()) {
					newSide = EnumFacing.getFront(facing.getIndex() + 5);
				}
				else {
					newSide = EnumFacing.getFront(facing.getIndex() + 1);
				}
			}
			else if(player.isSneaking()) {
				newSide = side.getOpposite();
			}
			if((newSide != facing) && (wrenchBlock.setFacing(world, pos, newSide, player))) {
				if(server) {
					Utility.doDamageItem(stack, cost, false);
				}
				else {
					IC2.audioManager.playOnce(player, PositionSpec.Hand, Ic2Sounds.wrenchUse, true,
							IC2.audioManager.defaultVolume);
				}
				return server ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
			}
		}
		Vec3d vec = new Vec3d(hitX, hitY, hitZ);
		ISpecialWrenchable special = (wrenchBlock instanceof ISpecialWrenchable) ? (ISpecialWrenchable) wrenchBlock
				: null;
		if((special != null) && (special.hasSpecialAction(world, pos, side, player, vec))
				&& (Utility.doDamageItem(stack, cost * 2, true))) {
			EnumActionResult result = special.onSpecialAction(world, pos, side, player, vec);
			if(result == EnumActionResult.FAIL) {
				return EnumActionResult.PASS;
			}
			if(result == EnumActionResult.SUCCESS) {
				Utility.doDamageItem(stack, cost * 2, false);
				return result;
			}
		}
		if(Utility.doDamageItem(stack, cost * 5, false) && (wrenchBlock.wrenchCanRemove(world, pos, player))) {
			if(server) {
				double chance = special != null ? special.getWrenchSuccessRate(world, pos) : 0.85D;
				chance *= applyFortune(stack, 1.0D);
				boolean originalDrop = world.rand.nextFloat() <= chance;
				Utility.doDamageItem(stack, cost * 5, true);
				if((!originalDrop) && (canOverrideLossChance(stack))) {
					originalDrop = true;
					onLossPrevented(player, stack);
					if(hasBigCost(stack)) {
						Utility.doDamageItem(stack, cost * 20, false);
					}
				}
				List<ItemStack> drops;
				if(originalDrop) {
					drops = wrenchBlock.getWrenchDrops(world, pos, state, world.getTileEntity(pos), player, 0);
					IC2.achievements.issueStat(player, "machineSaver");
				}
				else {
					drops = state.getBlock().getDrops(world, pos, state, 0);
					IC2.achievements.issueStat(player, "machineLoser");
				}
				for(ItemStack item : drops) {
					Block.spawnAsEntity(world, pos, item);
				}
				world.setBlockToAir(pos);
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
	}

	public double applyFortune(ItemStack stack, double base) {
		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
		for(int i = 0; i < fortune; i++) {
			base += base * 0.03D;
		}
		return base;
	}

	@Override
	public boolean isWrench(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack itemStack, List<String> lines) {
		lines.add(I18n.format("behaviour.wrench"));
	}

}
