package gregtech.common;

import gregtech.api.GTValues;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandlers {
	
	@SubscribeEvent
	public static void onLightningStrike(EntityStruckByLightningEvent event) {
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
	@SubscribeEvent
	public static void onPlayerInteraction(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL) {
			if (!event.getWorld().isRemote
                    && !event.getEntityPlayer().capabilities.isCreativeMode
                    && event.getWorld().rand.nextInt(100) >= ConfigHolder.flintChanceToCreateFire) {
				stack.damageItem(1, event.getEntityPlayer());
				if (stack.getItemDamage() >= stack.getMaxDamage()) {
                    stack.shrink(1);
                }
				event.setCanceled(true);
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
