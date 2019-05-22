package com.rong.rt.api.metatileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Preconditions;
import com.rong.rt.api.RongTechAPI;
import com.rong.rt.api.gui.ModularUI;
import com.rong.rt.api.recipes.handlers.FluidHandlerProxy;
import com.rong.rt.api.recipes.handlers.FluidTankList;
import com.rong.rt.api.recipes.handlers.ItemHandlerProxy;
import com.rong.rt.api.render.Textures;
import com.rong.rt.api.utils.Utility;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public abstract class MetaTileEntity {

	public static final int DEFAULT_PAINTING_COLOR = 0xFFFFFF;
	public static final IndexedCuboid6 FULL_CUBE_COLLISION = new IndexedCuboid6(null, Cuboid6.full);
	public final ResourceLocation metaTileEntityId;
	MetaTileEntityHolder holder;

	protected IItemHandlerModifiable importItems;
	protected IItemHandlerModifiable exportItems;

	protected IItemHandler itemInventory;

	protected FluidTankList importFluids;
	protected FluidTankList exportFluids;

	protected IFluidHandler fluidInventory;

	protected List<MTETrait> mteTraits = new ArrayList<>();

	protected EnumFacing frontFacing = EnumFacing.NORTH;
	protected int paintingColor = DEFAULT_PAINTING_COLOR;

	private int[] sidedRedstoneOutput = new int[6];
	private int[] sidedRedstoneInput = new int[6];
	private int cachedComparatorValue;

	public MetaTileEntity(ResourceLocation metaTileEntityId) {
		this.metaTileEntityId = metaTileEntityId;
		initializeInventory();
	}

	protected void initializeInventory() {
		this.importItems = createImportItemHandler();
		this.exportItems = createExportItemHandler();
		this.itemInventory = new ItemHandlerProxy(importItems, exportItems);

		this.importFluids = createImportFluidHandler();
		this.exportFluids = createExportFluidHandler();
		this.fluidInventory = new FluidHandlerProxy(importFluids, exportFluids);
	}

	public MetaTileEntityHolder getHolder() {
		return holder;
	}

	public abstract MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder);

	public World getWorld() {
		return holder == null ? null : holder.getWorld();
	}

	public BlockPos getPos() {
		return holder == null ? null : holder.getPos();
	}

	public void markDirty() {
		if(holder != null) {
			holder.markDirty();
		}
	}

	public long getTimer() {
		return holder == null ? 0L : holder.getTimer();
	}

	public void writeCustomData(int discriminator, Consumer<PacketBuffer> dataWriter) {
		if(holder != null) {
			holder.writeCustomData(discriminator, dataWriter);
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getParticleTexture() {
		return TextureUtils.getMissingSprite();
	}

	/**
	 * ItemStack currently being rendered by this meta tile entity Use this to
	 * obtain itemstack-specific data like contained fluid, painting color Generally
	 * useful in combination with
	 * {@link #writeItemStackData(net.minecraft.nbt.NBTTagCompound)}
	 */
	@SideOnly(Side.CLIENT)
	protected ItemStack renderContextStack;

	@SideOnly(Side.CLIENT)
	public void setRenderContextStack(ItemStack itemStack) {
		this.renderContextStack = itemStack;
	}

	/**
	 * Renders this meta tile entity Note that you shouldn't refer to world-related
	 * information in this method, because it will be called on ItemStacks too
	 *
	 * @param renderState
	 *            render state (either chunk batched or item)
	 * @param pipeline
	 *            default set of pipeline transformations
	 */
	@SideOnly(Side.CLIENT)
	public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
		TextureAtlasSprite atlasSprite = TextureUtils.getMissingSprite();
		IVertexOperation[] renderPipeline = ArrayUtils.add(pipeline,
				new ColourMultiplier(Utility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
		for(EnumFacing face : EnumFacing.VALUES) {
			Textures.renderFace(renderState, translation, renderPipeline, face, Cuboid6.full, atlasSprite);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getPaintingColorForRendering() {
		if(getWorld() == null && renderContextStack != null) {
			NBTTagCompound tagCompound = renderContextStack.getTagCompound();
			if(tagCompound != null && tagCompound.hasKey("PaintingColor", NBT.TAG_INT)) {
				return tagCompound.getInteger("PaintingColor");
			}
		}
		return paintingColor;
	}

	/**
	 * Called from ItemBlock to initialize this MTE with data contained in ItemStack
	 *
	 * @param itemStack
	 *            itemstack of itemblock
	 */
	public void initFromItemStackData(NBTTagCompound itemStack) {
		if(itemStack.hasKey("PaintingColor", NBT.TAG_INT)) {
			setPaintingColor(itemStack.getInteger("PaintingColor"));
		}
	}

	/**
	 * Called to write MTE specific data when it is destroyed to save it's state
	 * into itemblock, which can be placed later to get
	 * {@link #initFromItemStackData} called
	 *
	 * @param itemStack
	 *            itemstack from which this MTE is being placed
	 */
	public void writeItemStackData(NBTTagCompound itemStack) {
		if(this.paintingColor != DEFAULT_PAINTING_COLOR) { // for machines to stack
			itemStack.setInteger("PaintingColor", this.paintingColor);
		}
	}

	public ICapabilityProvider initItemStackCapabilities(ItemStack itemStack) {
		return null;
	}

	public final String getMetaName() {
		return String.format("%s.machine.%s", metaTileEntityId.getResourceDomain(), metaTileEntityId.getResourcePath());
	}

	public final String getMetaFullName() {
		return getMetaName() + ".name";
	}

	/**
	 * Adds a trait to this meta tile entity traits are objects linked with meta
	 * tile entity and performing certain actions. usually traits implement
	 * capabilities there can be only one trait for given name
	 *
	 * @param trait
	 *            trait object to add
	 */
	void addMetaTileEntityTrait(MTETrait trait) {
		mteTraits.removeIf(otherTrait -> {
			if(trait.getName().equals(otherTrait.getName())) {
				return true;
			}
			if(otherTrait.getNetworkID() == trait.getNetworkID()) {
				String message = "Trait %s is incompatible with trait %s, as they both use same network id %d";
				throw new IllegalArgumentException(String.format(message, trait, otherTrait, trait.getNetworkID()));
			}
			return false;
		});
		this.mteTraits.add(trait);
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

	/**
	 * Creates a UI instance for player opening inventory of this meta tile entity
	 *
	 * @param entityPlayer
	 *            player opening inventory
	 * @return freshly created UI instance
	 */
	protected abstract ModularUI createUI(EntityPlayer entityPlayer);

	/**
	 * Called when player clicks on specific side of this meta tile entity
	 *
	 * @return true if something happened, so animation will be played
	 */
	public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
			CuboidRayTraceResult hitResult) {
		if(!playerIn.isSneaking() && openGUIOnRightClick()) {
			if(getWorld() != null && !getWorld().isRemote) {
				MetaTileEntityUIFactory.INSTANCE.openUI(getHolder(), (EntityPlayerMP) playerIn);
			}
			return true;
		}
		return false;
	}

	/**
	 * Called when player clicks wrench on specific side of this meta tile entity
	 *
	 * @return true if something happened, so wrench will get damaged and animation
	 *         will be played
	 */
	public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide,
			CuboidRayTraceResult hitResult) {
		if(playerIn.isSneaking()) {
			if(wrenchSide == getFrontFacing() || !isValidFrontFacing(wrenchSide) || !hasFrontFacing()) {
				return false;
			}
			if(wrenchSide != null && !getWorld().isRemote) {
				setFrontFacing(wrenchSide);
			}
			return true;
		}
		return false;
	}

	/**
	 * Called when player clicks screwdriver on specific side of this meta tile
	 * entity
	 *
	 * @return true if something happened, so screwdriver will get damaged and
	 *         animation will be played
	 */
	public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
			CuboidRayTraceResult hitResult) {
		return false;
	}

	public void onLoad() {
		this.cachedComparatorValue = getActualComparatorValue();
		for(EnumFacing side : EnumFacing.VALUES) {
			this.sidedRedstoneInput[side.getIndex()] = Utility.getRedstonePower(getWorld(), getPos(), side);
		}
	}

	public void onUnload() {
	}

	public final boolean canConnectRedstone(@Nullable EnumFacing side) {
		// so far null side means either upwards or downwards redstone wire connection
		// so check both top cover and bottom cover
		if(side == null) {
			return canConnectRedstone(EnumFacing.UP) || canConnectRedstone(EnumFacing.DOWN);
		}
		return canMachineConnectRedstone(side);
	}

	protected boolean canMachineConnectRedstone(EnumFacing side) {
		return false;
	}

	public final boolean isBlockRedstonePowered() {
		return false;
	}

	public void updateInputRedstoneSignals() {
		for(EnumFacing side : EnumFacing.VALUES) {
			int redstoneValue = Utility.getRedstonePower(getWorld(), getPos(), side);
			int currentValue = sidedRedstoneInput[side.getIndex()];
			if(redstoneValue != currentValue) {
				this.sidedRedstoneInput[side.getIndex()] = redstoneValue;
			}
		}
	}

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

	public void update() {
		for(MTETrait mteTrait : this.mteTraits) {
			if(shouldUpdate(mteTrait)) {
				mteTrait.update();
			}
		}
	}

	protected boolean shouldUpdate(MTETrait trait) {
		return true;
	}

	public final ItemStack getStackForm(int amount) {
		int metaTileEntityIntId = RongTechAPI.META_TILE_ENTITY_REGISTRY.getIdByObjectName(metaTileEntityId);
		return new ItemStack(RongTechAPI.MACHINE, amount, metaTileEntityIntId);
	}

	public final ItemStack getStackForm() {
		return getStackForm(1);
	}

	/**
	 * Add special drops which this meta tile entity contains here Meta tile entity
	 * item is ALREADY added into this list Do NOT add inventory contents in this
	 * list - it will be dropped automatically when breakBlock is called This will
	 * only be called if meta tile entity is broken with proper tool (i.e wrench)
	 *
	 * @param dropsList
	 *            list of meta tile entity drops
	 * @param harvester
	 *            harvester of this meta tile entity, or null
	 */
	public void getDrops(NonNullList<ItemStack> dropsList, @Nullable EntityPlayer harvester) {
	}

	public ItemStack getPickItem(CuboidRayTraceResult result, EntityPlayer player) {
		return getStackForm();
	}

	/**
	 * Whether this tile entity represents completely opaque cube
	 *
	 * @return true if machine is opaque
	 */
	public boolean isOpaqueCube() {
		return true;
	}

	public int getLightValue() {
		return 0;
	}

	public int getLightOpacity() {
		return 255;
	}

	/**
	 * Called to obtain list of AxisAlignedBB used for collision testing, highlight
	 * rendering and ray tracing this meta tile entity's block in world
	 */
	public void addCollisionBoundingBox(List<IndexedCuboid6> collisionList) {
		collisionList.add(FULL_CUBE_COLLISION);
	}

	/**
	 * Retrieves face shape on the current side of this meta tile entity
	 */
	public BlockFaceShape getFaceShape(EnumFacing side) {
		return isOpaqueCube() ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	/**
	 * @return tool required to dismantle this meta tile entity properly
	 */
	public String getHarvestTool() {
		return "wrench";
	}

	/**
	 * @return minimal level of tool required to dismantle this meta tile entity
	 *         properly
	 */
	public int getHarvestLevel() {
		return 1;
	}

	public void writeInitialSyncData(PacketBuffer buf) {
		buf.writeByte(this.frontFacing.getIndex());
		buf.writeInt(this.paintingColor);
		buf.writeShort(mteTraits.size());
		for(MTETrait trait : mteTraits) {
			buf.writeVarInt(trait.getNetworkID());
			trait.writeInitialData(buf);
		}
	}

	public void receiveInitialSyncData(PacketBuffer buf) {
		this.frontFacing = EnumFacing.VALUES[buf.readByte()];
		this.paintingColor = buf.readInt();
		int amountOfTraits = buf.readShort();
		for(int i = 0; i < amountOfTraits; i++) {
			int traitNetworkId = buf.readVarInt();
			MTETrait trait = mteTraits.stream().filter(otherTrait -> otherTrait.getNetworkID() == traitNetworkId)
					.findAny().get();
			trait.receiveInitialData(buf);
		}
	}

	public void writeTraitData(MTETrait trait, int internalId, Consumer<PacketBuffer> dataWriter) {
		writeCustomData(-4, buffer -> {
			buffer.writeVarInt(trait.getNetworkID());
			buffer.writeVarInt(internalId);
			dataWriter.accept(buffer);
		});
	}

	public void receiveCustomData(int dataId, PacketBuffer buf) {
		if(dataId == -2) {
			this.frontFacing = EnumFacing.VALUES[buf.readByte()];
			getHolder().scheduleChunkForRenderUpdate();
		}
		else if(dataId == -3) {
			this.paintingColor = buf.readInt();
			getHolder().scheduleChunkForRenderUpdate();
		}
		else if(dataId == -4) {
			int traitNetworkId = buf.readVarInt();
			MTETrait trait = mteTraits.stream().filter(otherTrait -> otherTrait.getNetworkID() == traitNetworkId)
					.findAny().get();
			int internalId = buf.readVarInt();
			trait.receiveCustomData(internalId, buf);
		}
	}

	public BlockFaceShape getCoverFaceShape(EnumFacing side) {
		return getFaceShape(side);
	}

	public <T> T getCapability(Capability<T> capability, EnumFacing side) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
				&& getFluidInventory().getTankProperties().length > 0) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidInventory());
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getItemInventory().getSlots() > 0) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemInventory());
		}
		for(MTETrait mteTrait : this.mteTraits) {
			T capabilityResult = mteTrait.getCapability(capability);
			if(capabilityResult != null) {
				return capabilityResult;
			}
		}
		return null;
	}

	public boolean fillInternalTankFromFluidContainer(IItemHandlerModifiable importItems,
			IItemHandlerModifiable exportItems, int inputSlot, int outputSlot) {
		ItemStack inputContainerStack = importItems.extractItem(inputSlot, 1, true);
		FluidActionResult result = FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE,
				null, false);
		if(result.isSuccess()) {
			ItemStack remainingItem = result.getResult();
			if(ItemStack.areItemStacksEqual(inputContainerStack, remainingItem)) return false; // do not fill if item
																								// stacks match
			if(!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
				return false; // do not fill if can't put remaining item
			FluidUtil.tryEmptyContainer(inputContainerStack, importFluids, Integer.MAX_VALUE, null, true);
			importItems.extractItem(inputSlot, 1, false);
			exportItems.insertItem(outputSlot, remainingItem, false);
			return true;
		}
		return false;
	}

	public boolean fillContainerFromInternalTank(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
			int inputSlot, int outputSlot) {
		ItemStack emptyContainer = importItems.extractItem(inputSlot, 1, true);
		FluidActionResult result = FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null,
				false);
		if(result.isSuccess()) {
			ItemStack remainingItem = result.getResult();
			if(!remainingItem.isEmpty() && !exportItems.insertItem(outputSlot, remainingItem, true).isEmpty())
				return false;
			FluidUtil.tryFillContainer(emptyContainer, exportFluids, Integer.MAX_VALUE, null, true);
			importItems.extractItem(inputSlot, 1, false);
			exportItems.insertItem(outputSlot, remainingItem, false);
			return true;
		}
		return false;
	}

	public static boolean isItemHandlerEmpty(IItemHandler handler) {
		for(int i = 0; i < handler.getSlots(); i++) {
			if(!handler.getStackInSlot(i).isEmpty()) return false;
		}
		return true;
	}

	public static boolean addItemsToItemHandler(IItemHandler handler, boolean simulate, NonNullList<ItemStack> items) {
		boolean insertedAll = true;
		for(ItemStack stack : items) {
			insertedAll &= ItemHandlerHelper.insertItemStacked(handler, stack, simulate).isEmpty();
			if(!insertedAll && simulate) return false;
		}
		return insertedAll;
	}

	public static boolean addFluidsToFluidHandler(IFluidHandler handler, boolean simulate, List<FluidStack> items) {
		boolean filledAll = true;
		for(FluidStack stack : items) {
			int filled = handler.fill(stack, !simulate);
			filledAll &= filled == stack.amount;
			if(!filledAll && simulate) return false;
		}
		return filledAll;
	}

	public final void setOutputRedstoneSignal(EnumFacing side, int strength) {
		Preconditions.checkNotNull(side, "side");
		this.sidedRedstoneOutput[side.getIndex()] = strength;
		if(getWorld() != null && !getWorld().isRemote) {
			notifyBlockUpdate();
			markDirty();
		}
	}

	public void notifyBlockUpdate() {
		getHolder().notifyBlockUpdate();
	}

	public void scheduleRenderUpdate() {
		getHolder().scheduleChunkForRenderUpdate();
	}

	public void setFrontFacing(EnumFacing frontFacing) {
		Preconditions.checkNotNull(frontFacing, "frontFacing");
		this.frontFacing = frontFacing;
		if(getWorld() != null && !getWorld().isRemote) {
			getHolder().notifyBlockUpdate();
			markDirty();
			writeCustomData(-2, buf -> buf.writeByte(frontFacing.getIndex()));
			mteTraits.forEach(trait -> trait.onFrontFacingSet(frontFacing));
		}
	}

	public void setPaintingColor(int paintingColor) {
		this.paintingColor = paintingColor;
		if(getWorld() != null && !getWorld().isRemote) {
			getHolder().notifyBlockUpdate();
			markDirty();
			writeCustomData(-3, buf -> buf.writeInt(paintingColor));
		}
	}

	public boolean isValidFrontFacing(EnumFacing facing) {
		return facing != EnumFacing.UP && facing != EnumFacing.DOWN;
	}

	public boolean hasFrontFacing() {
		return true;
	}

	/**
	 * @return true if this meta tile entity should serialize it's export and import
	 *         inventories Useful when you use your own unified inventory and don't
	 *         need these dummies to be saved
	 */
	protected boolean shouldSerializeInventories() {
		return true;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		data.setInteger("FrontFacing", frontFacing.getIndex());
		data.setInteger("PaintingColor", paintingColor);

		if(shouldSerializeInventories()) {
			Utility.writeItems(importItems, "ImportInventory", data);
			Utility.writeItems(exportItems, "ExportInventory", data);

			data.setTag("ImportFluidInventory", importFluids.serializeNBT());
			data.setTag("ExportFluidInventory", exportFluids.serializeNBT());
		}

		for(MTETrait mteTrait : this.mteTraits) {
			data.setTag(mteTrait.getName(), mteTrait.serializeNBT());
		}
		return data;
	}

	public void readFromNBT(NBTTagCompound data) {
		this.frontFacing = EnumFacing.VALUES[data.getInteger("FrontFacing")];
		this.paintingColor = data.getInteger("PaintingColor");

		if(shouldSerializeInventories()) {
			Utility.readItems(importItems, "ImportInventory", data);
			Utility.readItems(exportItems, "ExportInventory", data);

			importFluids.deserializeNBT(data.getCompoundTag("ImportFluidInventory"));
			exportFluids.deserializeNBT(data.getCompoundTag("ExportFluidInventory"));
		}

		for(MTETrait mteTrait : this.mteTraits) {
			NBTTagCompound traitCompound = data.getCompoundTag(mteTrait.getName());
			mteTrait.deserializeNBT(traitCompound);
		}
	}

	public boolean isValid() {
		return getHolder() != null && getHolder().isValid();
	}

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

	/**
	 * Called from breakBlock right before meta tile entity destruction at this
	 * stage tile entity inventory is already dropped on ground, but drops aren't
	 * fetched yet tile entity will still get getDrops called after this, if player
	 * broke block
	 */
	public void onRemoval() {
	}

	public EnumFacing getFrontFacing() {
		return frontFacing;
	}

	public int getPaintingColor() {
		return paintingColor;
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

}