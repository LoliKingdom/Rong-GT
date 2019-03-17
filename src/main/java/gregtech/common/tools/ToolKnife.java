package gregtech.common.tools;

import gregtech.api.items.toolitem.IToolStats;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ToolKnife implements IToolStats {

	@Override
	public int getToolDamagePerBlockBreak(ItemStack stack) {
		return 5;
	}

	@Override
	public int getToolDamagePerContainerCraft(ItemStack stack) {
		return 10;
	}

	@Override
	public int getToolDamagePerEntityAttack(ItemStack stack) {
		return 1;
	}

	@Override
    public float getBaseDamage(ItemStack stack) {
        return 4.5F;
    }
	
	@Override
	public ResourceLocation getUseSound(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
		return enchantment.type.canEnchantItem(Items.IRON_SWORD);
	}

	@Override
	public boolean isMinableBlock(IBlockState block, ItemStack stack) {
		return false;
	}
}