package gregtech.common;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import gregtech.api.GTValues;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.gui.impl.ModularUIGui;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.behaviors.PlungerBehaviour;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandlers {
	
	@SubscribeEvent
	public static void onLightningStrike(EntityStruckByLightningEvent event) {
		if(GTValues.isModLoaded("appliedenergistics2")) {
			World world = event.getLightning().getEntityWorld();
			BlockPos pos = event.getEntity().getPosition();
			if(event.getEntity() instanceof EntityItem) {
				EntityItem item = (EntityItem)event.getEntity();
				if(item.getItem().isItemEqual(OreDictUnifier.get(OrePrefix.crystal, Materials.CertusQuartz))) {
					item.setDead();
					event.getLightning().getEntityWorld();
					world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), OreDictUnifier.get(OrePrefix.crystal, Materials.ChargedCertusQuartz)));
				}
			}
		}		
	}
	
	@SubscribeEvent
	public static void onPlayerInteraction(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		if(!stack.isEmpty()) {
			if(stack.getItem() == Items.FLINT_AND_STEEL) {
				if(!world.isRemote 
						&& !player.capabilities.isCreativeMode
	                    && world.rand.nextInt(100) >= ConfigHolder.flintChanceToCreateFire) {
					stack.damageItem(1, player);
					if(stack.getItemDamage() >= stack.getMaxDamage()) {
	                    stack.shrink(1);
	                }
					event.setCanceled(true);
				}
			}
			if(stack.getItem() instanceof ToolMetaItem<?>) {
				if(!world.isRemote && !(world.getBlockState(pos).getBlock() instanceof BlockMachine)) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if(tileEntity != null) {
						IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, event.getFace());
			        	if(fluidHandler != null) {
			        		ItemStack toolStack = player.getHeldItem(event.getHand());
			                boolean isShiftClick = player.isSneaking();
			                IFluidHandler handlerToRemoveFrom = isShiftClick ?
			                        (fluidHandler instanceof FluidHandlerProxy ? ((FluidHandlerProxy)fluidHandler).input : null) :
			                        (fluidHandler instanceof FluidHandlerProxy ? ((FluidHandlerProxy)fluidHandler).output : fluidHandler);
			                if(handlerToRemoveFrom != null && GTUtility.doDamageItem(stack, 10, false)) {
			                	FluidStack drainStack = handlerToRemoveFrom.drain(250, true);
			                	int amount = drainStack == null ? 0 : drainStack.amount;
			                    if(amount > 0) {
			                        player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0f, amount / 250.0f);
			                    }
			                    event.setCancellationResult(EnumActionResult.SUCCESS);
			                }
			                else {
			                	event.setCanceled(true);
			                	event.setCancellationResult(EnumActionResult.PASS);
			                }
			        	}
					}
				}
			}
		}
	}

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(GTValues.MODID)) {
            ConfigManager.sync(GTValues.MODID, Config.Type.INSTANCE);
        }
    }
}
