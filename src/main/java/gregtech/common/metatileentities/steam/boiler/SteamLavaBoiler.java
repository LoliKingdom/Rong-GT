package gregtech.common.metatileentities.steam.boiler;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.recipes.ModHandler;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class SteamLavaBoiler extends SteamBoiler {

    private FluidTank lavaFluidTank;

    public SteamLavaBoiler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, Textures.LAVA_BOILER_OVERLAY, 100);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SteamLavaBoiler(metaTileEntityId);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        FluidTankList superHandler = super.createImportFluidHandler();
        this.lavaFluidTank = new FilteredFluidHandler(16000)
            .setFillPredicate(ModHandler::isLava);
        return new FluidTankList(false, superHandler, lavaFluidTank);
    }

    public static final int LAVA_PER_OPERATION = 100;

    @Override
    protected void tryConsumeNewFuel() {
        if(lavaFluidTank.getFluidAmount() >= LAVA_PER_OPERATION) {
            lavaFluidTank.drain(LAVA_PER_OPERATION, true);
            setFuelMaxBurnTime(LAVA_PER_OPERATION);
        }
    }
    
    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {  
    	int containerCapacity = 1000;
        if(!playerIn.isSneaking()) {
        	if(getWorld() != null && !getWorld().isRemote) {
        		ItemStack stack = playerIn.getActiveItemStack();
        		if(FluidUtil.getFluidHandler(stack) != null) {
        			if(FluidUtil.getFluidContained(stack).getFluid() == null && facing == getFrontFacing().getOpposite()) {        			
            			//ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(stack, 1);
            	        IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(stack);
            	        for(IFluidTankProperties properties : containerFluidHandler.getTankProperties()) {
            	        	containerCapacity = properties.getCapacity();
            	        }
            	        FluidActionResult result = FluidUtil.tryFillContainer(stack, steamFluidTank, containerCapacity, playerIn, false);
            			if(result.isSuccess()) {
            				FluidUtil.tryFillContainer(stack, steamFluidTank, Integer.MAX_VALUE, null, true);
            			}     			                  
                    }
        			else if(FluidUtil.getFluidContained(stack).getFluid() == FluidRegistry.LAVA) {
        				FluidActionResult result = FluidUtil.tryEmptyContainer(stack, lavaFluidTank, Integer.MAX_VALUE, playerIn, false);
        				if(result.isSuccess()) {
            				FluidUtil.tryFillContainer(stack, lavaFluidTank, Integer.MAX_VALUE, null, true);
            			}
        			}
        			else {
        				FluidActionResult result = FluidUtil.tryEmptyContainer(stack, waterFluidTank, Integer.MAX_VALUE, playerIn, false);
            			if(result.isSuccess()) {
            				FluidUtil.tryFillContainer(stack, waterFluidTank, Integer.MAX_VALUE, null, true);
            			}
        			}     			
        	    }
        		else {
        			MetaTileEntityUIFactory.INSTANCE.openUI(getHolder(), (EntityPlayerMP)playerIn);
        		}
             }       		
        }
		return true;       	
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer)
            .widget(new TankWidget(lavaFluidTank, 108, 17, 11, 55)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty")))
            .build(getHolder(), entityPlayer);
    }
}
