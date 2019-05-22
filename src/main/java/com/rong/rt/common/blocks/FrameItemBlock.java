package com.rong.rt.common.blocks;

import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.types.Material;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FrameItemBlock extends ItemBlock {

    private BlockFrame frameBlock;

    public FrameItemBlock(BlockFrame block) {
        super(block);
        this.frameBlock = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SuppressWarnings("deprecation")
    public IBlockState getBlockState(ItemStack stack) {
        return frameBlock.getStateFromMeta(getMetadata(stack.getItemDamage()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        Material material = frameBlock.frameMaterial;
        return EnumOrePrefix.frameGt.getLocalNameForItem(material);
    }

}
