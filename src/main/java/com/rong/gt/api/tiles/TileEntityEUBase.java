package com.rong.gt.api.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.rong.gt.Values;
import com.rong.gt.api.ControlledRegistry;
import com.rong.gt.api.gui.IUIHolder;
import com.rong.gt.api.gui.ModularUI;
import com.rong.gt.api.gui.UIFactory;
import com.rong.gt.api.gui.Widget;
import com.rong.gt.api.gui.impl.ModularUIContainer;
import com.rong.gt.api.gui.impl.ModularUIGui;
import com.rong.gt.api.net.NetworkHandler;
import com.rong.gt.api.net.PacketUIOpen;
import com.rong.gt.api.net.PacketUIWidgetUpdate;
import com.rong.gt.api.render.Textures;
import com.rong.gt.api.tiles.handlers.FluidTankList;
import com.rong.gt.api.tiles.handlers.WrenchInfo;
import com.rong.gt.api.utils.Utility;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ArrayUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import ic2.api.classic.network.adv.NetworkField;
import ic2.api.classic.tile.IInfoTile;
import ic2.api.classic.tile.machine.IEUStorage;
import ic2.api.classic.util.IWorldTickCallback;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.IC2;
import ic2.core.block.base.util.info.misc.IEnergyUser;
import ic2.core.inventory.base.IRotatingInventory;
import ic2.core.network.NetworkManager;
import ic2.core.platform.registry.Ic2Capabilities;
import ic2.core.util.helpers.ChunkUpdater;
import ic2.core.util.helpers.FilteredList;
import ic2.core.util.obj.IWrenchableTile;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityEUBase extends TileEntity 
	implements ITickable, IEnergyUser, IRotatingInventory, IWrenchableTile, IInfoTile {

	private final NetworkManager network;
	
	@NetworkField(index = 0)
	public boolean isActive;
	private boolean prevActive;
	
	@NetworkField(index = 1, compression = NetworkField.BitLevel.Bit8)
	public int facing = 0;
	private int prevFacing = 0;
	
	//private int fuelslot;
	private List<String> networkFields = new FilteredList();
	private List<IInfoTile.InfoComponent> infos = new FilteredList();

	private int timer = 0;
	
	private boolean loaded = false;
	private final boolean isServer;
	
	private boolean redstone;
	private boolean needsRedstoneUpdate;
	private boolean needsInitialRedstoneUpdate;
	
	protected EnumFacing frontFacing = EnumFacing.NORTH;
	
	private int[] sidedRedstoneOutput = new int[6];
    private int[] sidedRedstoneInput = new int[6];
    private int cachedComparatorValue;

    protected IItemHandlerModifiable importItems;
    protected IItemHandlerModifiable exportItems;

    protected IItemHandler itemInventory;

    protected FluidTankList importFluids;
    protected FluidTankList exportFluids;

    protected IFluidHandler fluidInventory;
    
    public TileEntityEUBase() {
    	this.isServer = IC2.platform.isSimulating();
    	this.network = ((NetworkManager)IC2.network.get(this.isServer));
    	addNetworkFields(new String[] { "facing", "isActive" });
    	addInfos(new IInfoTile.InfoComponent[] { new WrenchInfo(this) });
    }
    
    public void init() {
        FACTORY_REGISTRY.register(0, new ResourceLocation(Values.MOD_ID, "tile_entity_factory"), this);
    }

    @Override
    public void update() {
    	//if(!canUpdate()) return;
        if(timer == 0) {
            onFirstTick();
        }
        if(needsInitialRedstoneUpdate()) {
        	handleInitialRedstone();
        }
        timer++;
    }
    
    protected void onFirstTick() {}
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {}

    public void setActive(boolean active) {
    	this.isActive = active;
    	if(active != this.prevActive) {
    		getNetwork().updateTileEntityField(this, "isActive");
    	}
    	this.prevActive = active;
    }
    
    public void setActiveWithoutNotify(boolean active) {
    	this.isActive = active;
    	this.prevActive = active;
    }
    
    public boolean isActive() {
    	return this.isActive;
    }
    
    public boolean isActiveChanged() {
    	return this.isActive != this.prevActive;
    }
    
    @Override
    public void onLoad() {
    	if(!this.canUpdate()) {
    		this.world.tickableTileEntities.remove(this);
    	}
    	if(!this.loaded) {
    		if(isSimulating()) {
    			IC2.callbacks.addCallback(this.getWorld(), new IWorldTickCallback() {
    				public ActionResult<Integer> tickCallback(World world) {
    					TileEntityEUBase.this.onLoaded();
    		            return ActionResult.newResult(EnumActionResult.SUCCESS, Integer.valueOf(0));
    		        }
    			});
    			this.cachedComparatorValue = getActualComparatorValue();
    	        for (EnumFacing side : EnumFacing.VALUES) {
    	            this.sidedRedstoneInput[side.getIndex()] = Utility.getRedstonePower(getWorld(), getPos(), side);
    	        }
    		}
    	}
    }

    public void onLoaded() {
    	if(!this.isRendering()) {
    		ChunkUpdater.addWatcher(getWorld(), getPos());
    	}
    	this.loaded = true;
    }
    
    public void onUnloaded() {
    	this.loaded = false;
    	if(this.isSimulating()) {
    		ChunkUpdater.removeWatcher(getWorld(), getPos());
    	}
    }
    
    @Override
    public void invalidate() {
    	if(this.loaded) {
    		onUnloaded();
    	}
    	super.invalidate();
    }
    
    @Override
    public void onChunkUnload() {
    	if(this.loaded) {
    		onUnloaded();
    	}
    	super.onChunkUnload();
    }
    
    public void onNetworkUpdate(String field) {
    	if(field.equals("isActive") || field.equals("facing")) {
    		this.prevActive = this.isActive;
    		this.prevFacing = this.facing;
    		this.world.markBlockRangeForRenderUpdate(getPos(), getPos());
    	}
    }
    
    @SideOnly(Side.CLIENT)
    protected ItemStack renderContextStack;

    @SideOnly(Side.CLIENT)
    public void setRenderContextStack(ItemStack itemStack) {
        this.renderContextStack = itemStack;
    }
  
    @SideOnly(Side.CLIENT)
    public void renderTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        TextureAtlasSprite atlasSprite = TextureUtils.getMissingSprite();
        for(EnumFacing face : EnumFacing.VALUES) {
            Textures.renderFace(renderState, translation, pipeline, face, Cuboid6.full, atlasSprite);
        }
    }
    
    public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
        return null;
    }
    
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if(!playerIn.isSneaking() && openGUIOnRightClick()) {
            if(getWorld() != null && !getWorld().isRemote) {
                this.openUI((EntityPlayerMP)playerIn);
            }
            return true;
        }
        return false;
    }
    
    public void onLeftClick(EntityPlayer player, EnumFacing facing, CuboidRayTraceResult hitResult) {}
    
    public int getActualComparatorValue() {
        return 0;
    }

    public final int getComparatorValue() {
        return cachedComparatorValue;
    }

    public void updateComparatorValue() {
        int newComparatorValue = getActualComparatorValue();
        if(cachedComparatorValue != newComparatorValue) {
            this.cachedComparatorValue = newComparatorValue;
            if(getWorld() != null && !getWorld().isRemote) {
                notifyBlockUpdate();
            }
        }
    }

	protected void addInfos(IInfoTile.InfoComponent... infoArray) {
	    this.infos.addAll(Arrays.asList(infoArray));
	}
	
	protected void addNetworkFields(String... fields){
	    this.networkFields.addAll(Arrays.asList(fields));
    }
	
	@Override
	public EnumFacing getFacing() {
		return this.getFacing();
	}

	@Override
	public boolean supportsRotation() {
		return true;
	}
	
	@Override
	public boolean canRemoveBlock(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canSetFacing(EntityPlayer player, EnumFacing facing) {
		return true;
	}

	@Override
	public EnumActionResult doSpecialAction(EntityPlayer player, EnumFacing facing, Vec3d vec) {
		return EnumActionResult.PASS;
	}

	@Override
	public double getWrenchDropRate() {
		return 1.0D;
	}

	@Override
	public boolean hasSpecialAction(EntityPlayer player, EnumFacing facing, Vec3d vec) {
		return false;
	}
	
	public EnumFacing getFrontFacing() {
        return this.frontFacing;
	}

	@Override
	public void setFacing(EnumFacing face) {
		if(face == null) return;
		this.facing = face.getIndex();
		if(this.facing != this.prevFacing) {
			getNetwork().updateTileEntityField(this, "facing");
		}
		this.prevFacing = this.facing;
	}

	public void setFacingWithoutNotify(EnumFacing face) {
		if(face == null) return;
		this.facing = face.getIndex();
		this.prevFacing = face.getIndex();
	}
	
	public List<String> getNetworkedFields() {
	    return this.networkFields;
	}
	  
	@Override
	public List<IInfoTile.InfoComponent> getComponents() {
		return this.infos;
	}

	@Override
	public int getEnergyUsage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isSimulating() {
		return this.isServer;
	}
	  
	public boolean isRendering() {
		return !this.isServer;
	}
	
	public boolean canRenderBreaking() {
		return false;
	}
	
	public boolean canUpdate(){
	    return isSimulating();
	}
	
	public NetworkManager getNetwork() {
		return this.network;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		if(shouldSerializeInventories()) {
			Utility.writeItems(importItems, "ImportInventory", nbt);
            Utility.writeItems(exportItems, "ExportInventory", nbt);

            nbt.setTag("ImportFluidInventory", importFluids.serializeNBT());
            nbt.setTag("ExportFluidInventory", exportFluids.serializeNBT());
		}
		super.readFromNBT(nbt);
	}
	  
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    super.writeToNBT(nbt);
	    nbt.setByte("Facing", (byte)this.facing);
	    if(shouldSerializeInventories()) {
            Utility.writeItems(importItems, "ImportInventory", nbt);
            Utility.writeItems(exportItems, "ExportInventory", nbt);

            nbt.setTag("ImportFluidInventory", importFluids.serializeNBT());
            nbt.setTag("ExportFluidInventory", exportFluids.serializeNBT());
	    }
	    return nbt;
	}
	  
	public NBTTagCompound getUpdateTag() {
	    NBTTagCompound nbt = new NBTTagCompound();
	    nbt.setInteger("x", this.pos.getX());
	    nbt.setInteger("y", this.pos.getY());
	    nbt.setInteger("z", this.pos.getZ());
	    return nbt;
	}
	
	public NBTTagCompound getTag(NBTTagCompound nbt, String tag) {
		if(!nbt.hasKey(tag)) {
			nbt.setTag(tag, new NBTTagCompound());
	    }
		return nbt.getCompoundTag(tag);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == Ic2Capabilities.SinkLimiterCapability) {
			return super.hasCapability(capability, facing);
	    }
	    return false;
	}
	 
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == Ic2Capabilities.SinkLimiterCapability) {
			return (T)super.getCapability(capability, facing);
	    }
		return null;
	  }
	
	public void onBlockUpdate(Block block) {
	    this.needsRedstoneUpdate = true;
	}
	
	public void handleRedstone() {
		if(this.needsRedstoneUpdate) {
			this.redstone = this.world.isBlockPowered(this.getPos());
		    this.needsRedstoneUpdate = false;
		}
	}
	
	public boolean needsInitialRedstoneUpdate() {
		return false;
	}
	
	public void handleInitialRedstone() {
		this.redstone = this.world.isBlockPowered(this.getPos());
	}
	
	public void scheduleChunkForRenderUpdate() {
        BlockPos pos = getPos();
        getWorld().markBlockRangeForRenderUpdate(
            pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1,
            pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    public void notifyBlockUpdate() {
        getWorld().notifyNeighborsOfStateChange(pos, getBlockType(), false);
    }
	
	public long getTimer() {
        return timer;
    }
	
	protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(0);
    }

    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(0);
    }

    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false);
    }

    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false);
    }

    protected boolean openGUIOnRightClick() {
        return true;
    }

    protected abstract ModularUI createUI(EntityPlayer entityPlayer);
    
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        clearInventory(itemBuffer, importItems);
        clearInventory(itemBuffer, exportItems);
    }

    public static void clearInventory(NonNullList<ItemStack> itemBuffer, IItemHandlerModifiable inventory) {
        for(int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if(!stackInSlot.isEmpty()) {
                inventory.setStackInSlot(i, ItemStack.EMPTY);
                itemBuffer.add(stackInSlot);
            }
        }
    }
    
    public boolean fillInternalTankFromFluidContainer(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, int inputSlot, int outputSlot) {
        ItemStack inputContainerStack = importItems.extractItem(inputSlot, 1, true);
        FluidActionResult result = FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE, null, false);
        if (result.isSuccess()) {
            ItemStack remainingItem = result.getResult();
            if (ItemStack.areItemStacksEqual(inputContainerStack, remainingItem))
                return false; //do not fill if item stacks match
            if (!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
                return false; //do not fill if can't put remaining item
            FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE, null, true);
            importItems.extractItem(inputSlot, 1, false);
            exportItems.insertItem(outputSlot, remainingItem, false);
            return true;
        }
        return false;
    }
    
    public boolean fillContainerFromInternalTank(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, int inputSlot, int outputSlot) {
        ItemStack emptyContainer = importItems.extractItem(inputSlot, 1, true);
        FluidActionResult result = FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null, false);
        if (result.isSuccess()) {
            ItemStack remainingItem = result.getResult();
            if (!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
                return false;
            FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null, true);
            importItems.extractItem(inputSlot, 1, false);
            exportItems.insertItem(outputSlot, remainingItem, false);
            return true;
        }
        return false;
    }
    
    public static boolean isItemHandlerEmpty(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    public static boolean addItemsToItemHandler(IItemHandler handler, boolean simulate, NonNullList<ItemStack> items) {
        boolean insertedAll = true;
        for(ItemStack stack : items) {
            insertedAll &= ItemHandlerHelper.insertItemStacked(handler, stack, simulate).isEmpty();
            if (!insertedAll && simulate) return false;
        }
        return insertedAll;
    }

    public static boolean addFluidsToFluidHandler(IFluidHandler handler, boolean simulate, List<FluidStack> items) {
        boolean filledAll = true;
        for(FluidStack stack : items) {
            int filled = handler.fill(stack, !simulate);
            filledAll &= filled == stack.amount;
            if (!filledAll && simulate) return false;
        }
        return filledAll;
    }

    public final int getOutputRedstoneSignal(@Nullable EnumFacing side) {
        if(side == null) {
            return getHighestOutputRedstoneSignal();
        }
        return sidedRedstoneOutput[side.getIndex()];
    }

    public final int getHighestOutputRedstoneSignal() {
        int highestSignal = 0;
        for(EnumFacing side : EnumFacing.VALUES) {
            highestSignal = Math.max(highestSignal, sidedRedstoneOutput[side.getIndex()]);
        }
        return highestSignal;
    }

    public final void setOutputRedstoneSignal(EnumFacing side, int strength) {
        Preconditions.checkNotNull(side, "side");
        this.sidedRedstoneOutput[side.getIndex()] = strength;
        if (getWorld() != null && !getWorld().isRemote) {
            notifyBlockUpdate();
            markDirty();
        }
    }

    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != EnumFacing.UP && facing != EnumFacing.DOWN;
    }

    public boolean isFrontFacing() {
        return true;
    }	
    
    protected boolean shouldSerializeInventories() {
        return true;
    }
    
    public IItemHandler getItemInventory() {
        return itemInventory;
    }

    public IFluidHandler getFluidInventory() {
        return fluidInventory;
    }

    public IItemHandlerModifiable getImportItems() {
        return importItems;
    }

    public IItemHandlerModifiable getExportItems() {
        return exportItems;
    }

    public FluidTankList getImportFluids() {
        return importFluids;
    }

    public FluidTankList getExportFluids() {
        return exportFluids;
    }
    
    public static final ControlledRegistry<ResourceLocation, TileEntityEUBase> FACTORY_REGISTRY = new ControlledRegistry<>(Short.MAX_VALUE);

    public final void openUI(EntityPlayerMP player) {
        if (player instanceof FakePlayer) {
            return;
        }
        ModularUI uiTemplate = createUITemplate(player);
        uiTemplate.initWidgets();

        player.getNextWindowId();
        player.closeContainer();
        int currentWindowId = player.currentWindowId;

        PacketBuffer serializedHolder = new PacketBuffer(Unpooled.buffer());
        writeHolderToSyncData(serializedHolder);
        int uiFactoryId = FACTORY_REGISTRY.getIDForObject(this);

        ModularUIContainer container = new ModularUIContainer(uiTemplate);
        container.windowId = currentWindowId;
        //accumulate all initial updates of widgets in open packet
        container.accumulateWidgetUpdateData = true;
        uiTemplate.guiWidgets.values().forEach(Widget::detectAndSendChanges);
        container.accumulateWidgetUpdateData = false;
        ArrayList<PacketUIWidgetUpdate> updateData = new ArrayList<>(container.accumulatedUpdates);
        container.accumulatedUpdates.clear();

        PacketUIOpen packet = new PacketUIOpen(uiFactoryId, serializedHolder, currentWindowId, updateData);
        NetworkHandler.channel.sendTo(NetworkHandler.packet2proxy(packet), player);

        container.addListener(player);
        player.openContainer = container;

        //and fire forge event only in the end
        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, container));
    }

    @SideOnly(Side.CLIENT)
    public final void initClientUI(PacketBuffer serializedHolder, int windowId, List<PacketUIWidgetUpdate> initialWidgetUpdates) {
    	TileEntityEUBase holder = readHolderFromSyncData(serializedHolder);
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP entityPlayer = minecraft.player;

        ModularUI uiTemplate = createUITemplate(entityPlayer);
        uiTemplate.initWidgets();
        ModularUIGui modularUIGui = new ModularUIGui(uiTemplate);
        modularUIGui.inventorySlots.windowId = windowId;
        for(PacketUIWidgetUpdate packet : initialWidgetUpdates) {
            modularUIGui.handleWidgetUpdate(packet);
        }
        minecraft.addScheduledTask(() -> {
            minecraft.displayGuiScreen(modularUIGui);
            minecraft.player.openContainer.windowId = windowId;
        });
    }

    protected ModularUI createUITemplate(EntityPlayer entityPlayer) {
    	return this.createUI(entityPlayer);
    }

    @SideOnly(Side.CLIENT)
    protected TileEntityEUBase readHolderFromSyncData(PacketBuffer syncData) {
        return (TileEntityEUBase)Minecraft.getMinecraft().world.getTileEntity(syncData.readBlockPos());
    }

    protected void writeHolderToSyncData(PacketBuffer syncData) {
        syncData.writeBlockPos(this.getPos());
    }

}
