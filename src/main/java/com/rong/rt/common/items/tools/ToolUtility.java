package com.rong.rt.common.items.tools;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.rong.rt.api.unification.OreDictUnifier;

import codechicken.lib.raytracer.RayTracer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class ToolUtility {

    public static EnumFacing getSideHit(World world, BlockPos blockPos, EntityPlayer harvester) {
        RayTraceResult result = RayTracer.retraceBlock(world, harvester, blockPos);
        return result == null ? harvester.getHorizontalFacing() : result.sideHit;
    }
    
    private static void dropListOfItems(World world, BlockPos pos, List<ItemStack> drops) {
        Random rand = new Random();
        for (ItemStack stack : drops) {
            float f = 0.7F;
            double offX = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
            double offY = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
            double offZ = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
            EntityItem entityItem = new EntityItem(world, pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ, stack);
            entityItem.setDefaultPickupDelay();
            world.spawnEntity(entityItem);
        }
    }

    public static boolean applyShearBehavior(ItemStack itemStack, BlockPos pos, EntityPlayer player) {
        Block block = player.world.getBlockState(pos).getBlock();
        if (block instanceof IShearable) {
            IShearable target = (IShearable) block;
            if (target.isShearable(itemStack, player.world, pos)) {
                int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
                List<ItemStack> drops = target.onSheared(itemStack, player.world, pos, fortuneLevel);
                dropListOfItems(player.world, pos, drops);
                player.addStat(StatList.getBlockStats(block));
                player.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                return true;
            }
        }
        return false;
    }
    
    /*public static void applyHammerDrops(Random random, IBlockState blockState, List<ItemStack> drops, int fortuneLevel) {
        ItemStack itemStack = new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState));
        Recipe recipe = RecipeMaps.FORGE_HAMMER_RECIPES.findRecipe(Long.MAX_VALUE, Collections.singletonList(itemStack), Collections.emptyList(), 0);
        if (recipe != null && !recipe.getOutputs().isEmpty()) {
            drops.clear();
            for (ItemStack outputStack : recipe.getResultItemOutputs(random, 1)) {
                outputStack = outputStack.copy();
                if (OreDictUnifier.getPrefix(outputStack) == OrePrefix.crushed) {
                    int growAmount = Math.round(outputStack.getCount() * random.nextFloat());
                    if (fortuneLevel > 0) {
                        int i = Math.max(0, random.nextInt(fortuneLevel + 2) - 1);
                        growAmount += outputStack.getCount() * i;
                    }
                    outputStack.grow(growAmount);
                }
                drops.add(outputStack);
            }
        }
    }*/
}