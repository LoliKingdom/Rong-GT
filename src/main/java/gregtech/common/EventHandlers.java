package gregtech.common;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.AnvilUpdateEvent;
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
