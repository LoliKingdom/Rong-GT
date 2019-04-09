package gregtech.common.metatileentities.steam.boiler;

import codechicken.lib.fluid.FluidUtils;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.recipes.ModHandler;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.SimpleSidedCubeRenderer;
import gregtech.api.render.SimpleSidedCubeRenderer.RenderSide;
import gregtech.api.unification.material.Materials;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SteamBoiler extends MetaTileEntity {

    private static final EnumFacing[] STEAM_PUSH_DIRECTIONS = ArrayUtils.add(EnumFacing.HORIZONTALS, EnumFacing.UP);
    public static final int BOILING_CYCLE_LENGTH = 18;

    public final TextureArea BRONZE_BACKGROUND_TEXTURE;
    public final TextureArea BRONZE_SLOT_BACKGROUND_TEXTURE;

    public final TextureArea SLOT_FURNACE_BACKGROUND;

    protected final int baseSteamOutput;
    private final OrientedOverlayRenderer renderer;

    protected FluidTank waterFluidTank;
    protected FluidTank steamFluidTank;

    private int fuelBurnTimeLeft;
    private int fuelMaxBurnTime;
    private int currentTemperature;
    private boolean hasNoWater;
    private int timeBeforeCoolingDown;
    
    private int maxTemperature = 750;

    private boolean isBurning;
    private boolean wasBurningAndNeedsUpdate;
    private ItemStackHandler containerInventory;

    public SteamBoiler(ResourceLocation metaTileEntityId, OrientedOverlayRenderer renderer, int baseSteamOutput) {
        super(metaTileEntityId);
        this.renderer = renderer;
        this.baseSteamOutput = baseSteamOutput;
        BRONZE_BACKGROUND_TEXTURE = getGuiTexture("%s_gui");
        BRONZE_SLOT_BACKGROUND_TEXTURE = getGuiTexture("slot_%s");
        SLOT_FURNACE_BACKGROUND = getGuiTexture("slot_%s_furnace_background");
        this.containerInventory = new ItemStackHandler(2);
    }

    @SideOnly(Side.CLIENT)
    private SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.STEAM_BRICKED_CASING_BRONZE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getBaseRenderer().getSpriteOnSide(RenderSide.TOP);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        renderer.render(renderState, translation, pipeline, getFrontFacing(), isBurning());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("FuelBurnTimeLeft", fuelBurnTimeLeft);
        data.setInteger("FuelMaxBurnTime", fuelMaxBurnTime);
        data.setInteger("CurrentTemperature", currentTemperature);
        data.setBoolean("HasNoWater", hasNoWater);
        data.setTag("ContainerInventory", containerInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.fuelBurnTimeLeft = data.getInteger("FuelBurnTimeLeft");
        this.fuelMaxBurnTime = data.getInteger("FuelMaxBurnTime");
        this.currentTemperature = data.getInteger("CurrentTemperature");
        this.hasNoWater = data.getBoolean("HasNoWater");
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
        this.isBurning = fuelBurnTimeLeft > 0;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isBurning);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isBurning = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == 100) {
            this.isBurning = buf.readBoolean();
            getHolder().scheduleChunkForRenderUpdate();
        }
    }
    
    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {    	
    	boolean debug = true;
    	ItemStack stack = playerIn.getHeldItem(hand);
		ItemStack stackCopy = ItemHandlerHelper.copyStackWithSize(stack, 1);
		IFluidHandlerItem handlerItem = FluidUtil.getFluidHandler(stackCopy);
		boolean canStack = stack.isStackable();
		if(handlerItem != null) {
			if(FluidUtil.getFluidContained(stackCopy) != null) {
				FluidActionResult result = FluidUtil.tryEmptyContainer(stackCopy, waterFluidTank, Integer.MAX_VALUE, playerIn, false);
    			if(result.isSuccess()) {
    				FluidUtil.tryEmptyContainer(stackCopy, waterFluidTank, Integer.MAX_VALUE, playerIn, true);
    				handlerItem.drain(Fluid.BUCKET_VOLUME, true);
    				if(canStack) {
    					stack.setCount(stack.getCount() - 1);
    					//playerIn.setHeldItem(hand, stack);
    					if(playerIn.addItemStackToInventory(handlerItem.getContainer())) {
    						playerIn.addItemStackToInventory(handlerItem.getContainer());
    					}
    					else {
    						playerIn.dropItem(handlerItem.getContainer(), true);
    					}
    				}
    				else {
    					playerIn.setHeldItem(hand, handlerItem.getContainer());
    				}
    			}
    			return true;
			}			
			else if(facing == getFrontFacing().getOpposite()) {
    	        FluidActionResult result = FluidUtil.tryFillContainer(stackCopy, steamFluidTank, Fluid.BUCKET_VOLUME, playerIn, false);
    			if(result.isSuccess()) {
    				FluidUtil.tryFillContainer(stackCopy, steamFluidTank, Fluid.BUCKET_VOLUME, playerIn, true);
    				handlerItem.fill(ModHandler.getSteam(Fluid.BUCKET_VOLUME), true);
    				if(canStack) {
    					stack.setCount(stack.getCount() - 1);
    					//playerIn.setHeldItem(hand, stack);
    					if(playerIn.addItemStackToInventory(handlerItem.getContainer())) {
    						playerIn.addItemStackToInventory(handlerItem.getContainer());
    					}
    					else {
    						playerIn.dropItem(handlerItem.getContainer(), true);
    					}
    				}
    				else {
    					playerIn.setHeldItem(hand, handlerItem.getContainer());
    				}
    			}
    			return true;
			}
		}
		return super.onRightClick(playerIn, hand, facing, hitResult);
    }

    /*@Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {    	
        if(!playerIn.isSneaking()) {
        	if(getWorld() != null && !getWorld().isRemote) {
        		ItemStack stack = playerIn.getActiveItemStack();
        		ItemStack stackCopy = ItemHandlerHelper.copyStackWithSize(stack, 1);
        		if(FluidUtil.getFluidHandler(stackCopy) != null) {
        			if(FluidUtil.getFluidContained(stackCopy).getFluid() == FluidRegistry.WATER) {
        				FluidActionResult result = FluidUtil.tryEmptyContainer(stackCopy, waterFluidTank, Integer.MAX_VALUE, playerIn, false);
            			if(result.isSuccess()) {
            				FluidUtil.tryEmptyContainer(stackCopy, waterFluidTank, Integer.MAX_VALUE, playerIn, true);
            				stack = stackCopy;
            			}
        			}
        			else if(FluidUtil.getFluidContained(stackCopy).getFluid() == null 
            				&& facing == getFrontFacing().getOpposite()) {
            	        FluidActionResult result = FluidUtil.tryFillContainer(stackCopy, steamFluidTank, Integer.MAX_VALUE, playerIn, false);
            			if(result.isSuccess()) {
            				FluidUtil.tryFillContainer(stackCopy, steamFluidTank, Integer.MAX_VALUE, playerIn, true);
            				stack = stackCopy;
            			}
        			}
        		}
        	}
        }
        return super.onRightClick(playerIn, hand, facing, hitResult);
    }*/
    
    public void setFuelMaxBurnTime(int fuelMaxBurnTime) {
        this.fuelMaxBurnTime = fuelMaxBurnTime;
        this.fuelBurnTimeLeft = fuelMaxBurnTime;
        if(!getWorld().isRemote) {
            markDirty();
        }
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            updateCurrentTemperature();
            generateSteam();
            if(getTimer() % 5 == 0) {
                fillInternalTankFromFluidContainer(containerInventory, containerInventory, 0, 1);
                pushFluidsIntoNearbyHandlers(STEAM_PUSH_DIRECTIONS);
            }
            if(fuelMaxBurnTime <= 0) {
                tryConsumeNewFuel();
                if(fuelBurnTimeLeft > 0) {
                    if(wasBurningAndNeedsUpdate) {
                        this.wasBurningAndNeedsUpdate = false;
                    } 
                    else {
                    	setBurning(true);
                    }
                }
            }
            if(wasBurningAndNeedsUpdate) {
                this.wasBurningAndNeedsUpdate = false;
                setBurning(false);
            }
        }
    }

    private void updateCurrentTemperature() {
        if(fuelMaxBurnTime > 0) {
            if(getTimer() % 12 == 0) {
                if(fuelBurnTimeLeft % 2 == 0 && currentTemperature < getMaxTemperate()) {
                    currentTemperature++;
                }
                fuelBurnTimeLeft -= 1;
                if(fuelBurnTimeLeft == 0) {
                    this.fuelMaxBurnTime = 0;
                    this.timeBeforeCoolingDown = 40;
                    //boiler has no fuel now, so queue burning state update
                    this.wasBurningAndNeedsUpdate = true;
                }
            }
        } 
        else if(timeBeforeCoolingDown == 0) {
        	if(currentTemperature > 0) {
                currentTemperature--;
            }
        } 
        else {
        	--timeBeforeCoolingDown;
        }
    }

    private void generateSteam() {
        if(currentTemperature >= 100 && (getTimer() % BOILING_CYCLE_LENGTH == 0)) {
            int fillAmount = (int)(baseSteamOutput * (currentTemperature / (getMaxTemperate() * 1.0)));
            boolean hasDrainedWater = waterFluidTank.drain(1, true) != null;
            int filledSteam = 0;
            if(hasDrainedWater) {
                filledSteam = steamFluidTank.fill(ModHandler.getSteam(fillAmount), true);
            }
            if(this.hasNoWater && hasDrainedWater) {
                getWorld().setBlockToAir(getPos());
                getWorld().createExplosion(null,
                    getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,
                    2.0f, true);
            } 
            else {
            	this.hasNoWater = !hasDrainedWater;
            }
            if(filledSteam == 0 && hasDrainedWater) {
                getWorld().playSound(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,
                    SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                steamFluidTank.drain(4000, true);
            }
        }
    }

    public boolean isBurning() {
        return isBurning;
    }

    public void setBurning(boolean burning) {
        this.isBurning = burning;
        if(!getWorld().isRemote) {
            markDirty();
            writeCustomData(100, buf -> buf.writeBoolean(burning));
        }
    }

    protected abstract void tryConsumeNewFuel();

    public int getMaxTemperate() {
        return 750;
    }

    public double getTemperaturePercent() {
        return currentTemperature / (getMaxTemperate() * 1.0);
    }

    public double getFuelLeftPercent() {
        return fuelMaxBurnTime == 0 ? 0.0 : fuelBurnTimeLeft / (fuelMaxBurnTime * 1.0);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        this.waterFluidTank = new FilteredFluidHandler(16000).setFillPredicate(ModHandler::isWater);
        return new FluidTankList(false, waterFluidTank);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        this.steamFluidTank = new FluidTank(16000);
        return new FluidTankList(false, steamFluidTank);
    }

    protected TextureArea getGuiTexture(String pathTemplate) {
        return TextureArea.fullImage(String.format("textures/gui/steam/%s/%s.png",
            "bronze", pathTemplate.replace("%s", "bronze")));
    }

    public ModularUI.Builder createUITemplate(EntityPlayer player) {
        return ModularUI.builder(BRONZE_BACKGROUND_TEXTURE, 176, 166)
            .widget(new LabelWidget(6, 6, getMetaFullName()))

            .widget(new ProgressWidget(this::getTemperaturePercent, 95, 17, 11, 55)
                .setProgressBar(getGuiTexture("bar_%s_empty"),
                    getGuiTexture("bar_heat"),
                    MoveType.VERTICAL))

            .widget(new TankWidget(waterFluidTank, 82, 17, 11, 55)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty")))
            .widget(new TankWidget(steamFluidTank, 69, 17, 11, 55)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty")))

            .widget(new FluidContainerSlotWidget(containerInventory, 0, 43, 18, true)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getGuiTexture("overlay_%s_in")))
            .widget(new SlotWidget(containerInventory, 1, 43, 54, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getGuiTexture("overlay_%s_out")))
            .widget(new ImageWidget(42, 35, 18, 18)
                .setImage(getGuiTexture("overlay_%s_fluid_container")))

            .bindPlayerInventory(player.inventory, BRONZE_SLOT_BACKGROUND_TEXTURE);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.steam_boiler.tooltip_produces", baseSteamOutput, BOILING_CYCLE_LENGTH));
    }
}
