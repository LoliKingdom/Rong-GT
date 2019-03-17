package gregtech.common.items.behaviors;

import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Arrays;
import java.util.List;

public class PlungerBehaviour implements IItemBehaviour {

    public final int cost;

    public PlungerBehaviour(int cost) {
        this.cost = cost;
    }
    
    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.addAll(Arrays.asList(I18n.format("behaviour.plunger.description").split("/n")));
    }
}