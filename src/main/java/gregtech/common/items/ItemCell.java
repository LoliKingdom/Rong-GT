package gregtech.common.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Materials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.BlockWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemCell extends Item {
	
	private final ItemStack EMPTY_STACK = new ItemStack(this);
	
	public ItemCell() {
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setMaxStackSize(1);
        setUnlocalizedName("fluid_cell");
        setRegistryName("fluid_cell");
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DispenseFluidContainer.getInstance());
	}
	
	@Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidHandlerCell(stack, Fluid.BUCKET_VOLUME);
    }

	@Override
    public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(!this.isInCreativeTab(tab)) {
            return;
        }
        subItems.add(EMPTY_STACK);
        FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
        ItemStack stack = new ItemStack(this);
        IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(fluidHandler != null) {
        	int fluidFillAmount = fluidHandler.fill(fluidStack, true);
            if(fluidFillAmount == fluidStack.amount) {
                ItemStack filledStack = fluidHandler.getContainer();
                subItems.add(filledStack);
            }
        }
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String unlocalizedName = getUnlocalizedNameInefficiently(stack);
	    IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(stack);
	    FluidStack fluidStack = fluidHandler.getTankProperties()[0].getContents();
	    FluidStack fluidInside = fluidHandler.drain(Integer.MAX_VALUE, false);
		String name = fluidStack == null || fluidStack.amount <= 0 ? "fluid_cell.empty" : fluidInside.getUnlocalizedName();
		return I18n.format(unlocalizedName, I18n.format(name));
	}
	
	public static ItemStack empty(ItemStack stack) {
        if(stack.getItem() instanceof ItemCell) {
        	FluidStack fluidStack = FluidUtil.getFluidContained(stack);
            if (fluidStack != null) {
                fluidStack.amount = 0;
            }
        }
        return stack;
	}
	
	@Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World parWorld, @Nonnull EntityPlayer parPlayer, @Nonnull EnumHand parHand) {
		ItemStack itemStack = parPlayer.getHeldItem(parHand);
        RayTraceResult mop = rayTrace(parWorld, parPlayer, true);
        if(mop == null || mop.typeOfHit != RayTraceResult.Type.BLOCK) {
        	return ActionResult.newResult(EnumActionResult.PASS, itemStack);
        }
        BlockPos clickPos = mop.getBlockPos();
        FluidStack fluidStack = getFluidStack(itemStack);
        if (parWorld.isBlockModifiable(parPlayer, clickPos)) {
        	BlockPos targetPos = clickPos.offset(mop.sideHit);
            if (parPlayer.canPlayerEdit(targetPos, mop.sideHit, itemStack)) {
            	if (fluidStack == null || fluidStack.amount <= 0) {
            		return tryFillAlt(parWorld, parPlayer, mop, itemStack);
                }
                else {
                	return tryPlaceAlt(parWorld, parPlayer, targetPos, itemStack);
                }
            }
            else {
                return ActionResult.newResult(EnumActionResult.FAIL, itemStack);
            }
        }
        else {
            return ActionResult.newResult(EnumActionResult.FAIL, itemStack);
        }
    }

    public ActionResult<ItemStack> tryPlaceAlt(World parWorld, EntityPlayer parPlayer, BlockPos parPos, ItemStack parStack) {
        ActionResult<ItemStack> resultPass = new ActionResult<ItemStack>(EnumActionResult.PASS, parStack);
        ActionResult<ItemStack> resultFail = new ActionResult<ItemStack>(EnumActionResult.FAIL, parStack);
        if(parWorld == null || parPos == null) {
            return resultFail;
        }
        else {
        	System.out.println("Valid location to place at");
            IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(parStack);
            if(containerFluidHandler == null) {
            	return resultFail;
            }
            else {
            	FluidStack containerFluidStack = getFluidStack(parStack);
                if(containerFluidStack == null || containerFluidStack.amount <= 0) {
                    return resultFail;
                }
                else {
                    Fluid fluid = containerFluidStack.getFluid();
                    if(fluid == null) {
                        return resultFail;
                    }
                    else {
                    	if(!fluid.canBePlacedInWorld()) {
                            return resultFail;
                        }
                        else {
                            IBlockState destBlockState = parWorld.getBlockState(parPos);
                            if(!parWorld.isAirBlock(parPos) && destBlockState.getMaterial().isSolid() 
                            		&& !destBlockState.getBlock().isReplaceable(parWorld, parPos)) {
                                return resultFail; // Non-air, solid, unreplacable block. We can't put fluid here.
                            }
                            else {
                                if(parWorld.provider.doesWaterVaporize() && fluid.doesVaporize(containerFluidStack)) {
                                    fluid.vaporize(parPlayer, parWorld, parPos, containerFluidStack);
                                    return ActionResult.newResult(EnumActionResult.SUCCESS, parStack);
                                }
                                else {
                                    Block blockToPlace = fluid.getBlock();
                                    IFluidHandler blockFluidHandler;
                                    if(blockToPlace instanceof IFluidBlock) {
                                        blockFluidHandler = new FluidBlockWrapper((IFluidBlock) blockToPlace, parWorld, parPos);
                                    }
                                    else if(blockToPlace instanceof BlockLiquid) {
                                        blockFluidHandler = new BlockLiquidWrapper((BlockLiquid) blockToPlace, parWorld, parPos);
                                    }
                                    else {
                                        blockFluidHandler = new BlockWrapper(blockToPlace, parWorld, parPos);
                                    }
                                    int blockCapacity = blockFluidHandler.getTankProperties()[0].getCapacity();
                                    int amountInContainer = containerFluidStack.amount;
                                    FluidStack blockFluidStack = blockFluidHandler.getTankProperties()[0].getContents();
                                    if(blockFluidStack == null) {
                                    	return resultFail;
                                    }
                                    else {
                                        if(amountInContainer > blockCapacity) {
                                            containerFluidStack.amount -= blockCapacity;
                                            blockFluidStack.amount = blockCapacity;
                                        }
                                        else {
                                            blockFluidStack.amount = amountInContainer;
                                            containerFluidStack.amount = 0;
                                        }
                                        SoundEvent soundevent = fluid.getEmptySound(containerFluidStack);
                                        parWorld.playSound(parPlayer, parPos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        if(!parPlayer.capabilities.isCreativeMode) {
                                            parPlayer.addStat(StatList.getObjectUseStats(this));
                                            if(containerFluidStack.amount <= 0) {
                                            	containerFluidStack.amount = 0;
                                            }
                                            updateFluidNBT(parStack, containerFluidStack);
                                            sendUpdatePacketToClient(parPlayer);
                                            parWorld.setBlockState(parPos, blockToPlace.getDefaultState());
                                            return ActionResult.newResult(EnumActionResult.SUCCESS, containerFluidHandler.getContainer());
                                        }
                                        else {
                                            containerFluidStack.amount = amountInContainer;
                                            parWorld.setBlockState(parPos, blockToPlace.getDefaultState());
                                            return resultPass;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void sendUpdatePacketToClient(EntityPlayer parPlayer) {
    	if(parPlayer instanceof EntityPlayerMP) {
    		((EntityPlayerMP)parPlayer).connection.sendPacket(new SPacketHeldItemChange(parPlayer.inventory.currentItem));
        }
    }

    private void updateFluidNBT(ItemStack parItemStack, FluidStack parFluidStack) {
    	if(!parItemStack.hasTagCompound()) {
    		parItemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound fluidTag = new NBTTagCompound();
        parFluidStack.writeToNBT(fluidTag);
        parItemStack.getTagCompound().setTag(FluidHandlerItemStack.FLUID_NBT_KEY, fluidTag);
    }

    @Nullable
    public FluidStack getMatchingFluidStack(IFluidHandler sourceHandler, Fluid parFluid) {
        IFluidTankProperties[] tankProperties = sourceHandler.getTankProperties();
        FluidStack result = null;
        for(int i = 0; i < tankProperties.length; i++) {
            if(tankProperties[i].getContents().getFluid() == parFluid) {
                result = tankProperties[i].getContents();
            }
        }
        return result;
    }

    @Nullable
    public FluidStack getFluidStack(ItemStack container) {
        if(container.hasTagCompound() && container.getTagCompound().hasKey(FluidHandlerItemStack.FLUID_NBT_KEY)) {
            return FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
        }
        return null;
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     *
     * @return the share tag
     */
    @Override
    public boolean getShareTag()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.item.Item#getNBTShareTag(net.minecraft.item.ItemStack)
     */
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        return stack.getTagCompound();
    }

    public ActionResult<ItemStack> tryFillAlt(World parWorld, EntityPlayer parPlayer, RayTraceResult mop, ItemStack parContainerStack) {
        ActionResult<ItemStack> resultPass = new ActionResult<ItemStack>(EnumActionResult.PASS, parContainerStack);
        ActionResult<ItemStack> resultFail = new ActionResult<ItemStack>(EnumActionResult.FAIL, parContainerStack);
        BlockPos blockPos = mop.getBlockPos();
        if (parWorld == null || blockPos == null || parContainerStack.isEmpty()) {
            return resultPass;
        }
        else {
            Block block = parWorld.getBlockState(blockPos).getBlock();
            if(block instanceof IFluidBlock || block instanceof BlockLiquid) {
                IFluidHandler sourceFluidHandler = FluidUtil.getFluidHandler(parWorld, blockPos, mop.sideHit);
                if(sourceFluidHandler != null) {
                    IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(parContainerStack);
                    if(containerFluidHandler != null) {
                        FluidStack containerFluidStack = getFluidStack(parContainerStack);
                        if(containerFluidStack == null) {
                            containerFluidStack = new FluidStack(FluidRegistry.WATER, 0);
                        }
                        int amountRoomInContainer = Fluid.BUCKET_VOLUME - containerFluidStack.amount;
                        if(amountRoomInContainer <= 0) {
                        	return resultPass;
                        }
                        else {
                            FluidStack sourceFluidStack = getMatchingFluidStack(sourceFluidHandler, FluidRegistry.WATER);
                            if (sourceFluidStack != null) {
                                int amountInSource = sourceFluidStack.amount;
                                if (amountInSource <= 0) {
                                	return resultPass;
                                }
                                else {
                                    if (sourceFluidStack.amount > amountRoomInContainer) {
                                    	containerFluidStack.amount = Fluid.BUCKET_VOLUME;
                                        sourceFluidStack.amount -= amountRoomInContainer;
                                    }
                                    else {
                                        containerFluidStack.amount = sourceFluidStack.amount;
                                        sourceFluidStack.amount = 0; // used all source amount
                                        parWorld.setBlockToAir(blockPos);
                                    }
                                    SoundEvent soundevent = containerFluidStack.getFluid().getFillSound(containerFluidStack);
                                    parPlayer.playSound(soundevent, 1f, 1f);
                                    updateFluidNBT(parContainerStack, containerFluidStack);
                                    sendUpdatePacketToClient(parPlayer);
                                    return ActionResult.newResult(EnumActionResult.SUCCESS, containerFluidHandler.getContainer());
                                }
                            }
                            else {
                                return resultPass;
                            }
                        }
                    }
                    else {
                        return resultFail;
                    }
                }
                else {
                    return resultFail;
                }
            }
            else {
                return resultPass;
            }
        }
    }
    
    public class FluidHandlerCell extends FluidHandlerItemStack {
        protected final FluidStack EMPTY = new FluidStack(FluidRegistry.WATER, 0);

        public FluidHandlerCell(ItemStack parContainerStack, int parCapacity) {
        	super(parContainerStack, parCapacity);
        	if(getFluid() == null) {
        		setContainerToEmpty();
        	}
        }

        @Override
        protected void setContainerToEmpty() {
            setFluid(EMPTY.copy());
            container.getTagCompound().removeTag(FLUID_NBT_KEY);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {
        	Fluid fluid = fluidStack.getFluid();
        	return fluid == FluidRegistry.WATER || fluid == FluidRegistry.LAVA || fluid == Materials.Milk.getMaterialFluid() || FluidRegistry.getBucketFluids().contains(fluid);
        }
    }
}