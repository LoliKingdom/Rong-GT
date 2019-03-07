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
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		if(event.getLeft().getItem() instanceof ToolMetaItem) {
			
			ItemStack leftStack = event.getLeft();
			ItemStack rightStack = event.getRight();
			ItemStack outputStack = event.getLeft().copy();
			
			ToolMetaItem tool = (ToolMetaItem)event.getLeft().getItem();
			
			Map<Enchantment, Integer> leftEnchants = EnchantmentHelper.getEnchantments(leftStack);
						
			Material leftMaterial = tool.getToolMaterial(leftStack);
			
			if(StringUtils.isBlank(event.getName()) || event.getName() == tool.getItemStackDisplayName(leftStack)) {
				outputStack.setStackDisplayName(tool.getItemStackDisplayName(leftStack));
			}
			else {
				outputStack.setStackDisplayName(event.getName());
			}
			
			if(!(rightStack.isEmpty())) {
				//setInternalDamage taking original durability + calculated to avoid doDamageItem passing negative int and messing around with Unbreaking Enchant
				if(!((rightStack.getItem() instanceof ItemEnchantedBook))) {
					if(tool.getToolMaterial(leftStack) instanceof IngotMaterial && rightStack.isItemEqual(OreDictUnifier.get(OrePrefix.ingot, leftMaterial))) {
						if(tool.getInternalDamage(leftStack) == tool.getMaxInternalDamage(leftStack)) {
							event.setOutput(ItemStack.EMPTY);
							event.setCost(0);
							event.setMaterialCost(0);
						}
						else {
							tool.regainItemDurability(outputStack, 100 * event.getRight().getCount());
							event.setCost(event.getRight().getCount() * 3);
							event.setMaterialCost(event.getRight().getCount());						
							event.setOutput(outputStack);
						}
					}
					else if(tool.getToolMaterial(leftStack) instanceof GemMaterial && rightStack.isItemEqual(OreDictUnifier.get(OrePrefix.gem, leftMaterial))) {
						if(tool.getInternalDamage(leftStack) == tool.getMaxInternalDamage(leftStack)) {
							event.setOutput(ItemStack.EMPTY);
							event.setCost(0);
							event.setMaterialCost(0);
						}
						else {
							tool.regainItemDurability(outputStack, 100 * event.getRight().getCount());
							event.setCost(event.getRight().getCount() * 3);
							event.setMaterialCost(event.getRight().getCount());						
							event.setOutput(outputStack);
						}
					}
					else if(rightStack.isItemEqual(OreDictUnifier.get(OrePrefix.dust, leftMaterial))){
						if(tool.getInternalDamage(leftStack) == tool.getMaxInternalDamage(leftStack)) {
							event.setOutput(ItemStack.EMPTY);
							event.setCost(0);
							event.setMaterialCost(0);
						}
						else {
							tool.regainItemDurability(outputStack, 100 * event.getRight().getCount());
							event.setCost(event.getRight().getCount() * 3);
							event.setMaterialCost(event.getRight().getCount());						
							event.setOutput(outputStack);
						}
					}
				}
				
				else {
					Map<Enchantment, Integer> enchantsOnBook = EnchantmentHelper.getEnchantments(rightStack);
					for(Enchantment bookEnchants : enchantsOnBook.keySet()) {
						if(bookEnchants != null) {
							//temp variable names
							int i = leftEnchants.containsKey(bookEnchants) ? leftEnchants.get(bookEnchants) : 0;
                            int j = enchantsOnBook.get(bookEnchants);
                            j = i == j ? j + 1 : Math.max(j, i);
							boolean canApply = bookEnchants.canApply(leftStack);
							for(Enchantment stackEnchants : leftEnchants.keySet()) {
								canApply = bookEnchants.isCompatibleWith(stackEnchants);
							}
							if(canApply) {
								if (j > bookEnchants.getMaxLevel()) {
                                    j = bookEnchants.getMaxLevel();
                                }
								Map<Enchantment, Integer> outputEnchants = EnchantmentHelper.getEnchantments(outputStack);
								outputEnchants.put(bookEnchants, j);
								int cost = 0;
								switch(bookEnchants.getRarity()) {
									case COMMON:
										cost = 1;
										break;
									case UNCOMMON:
										cost = 2;
										break;
									case RARE:
										cost = 4;
										break;
									case VERY_RARE:
										cost = 8;
								}
								event.setCost(cost * j);
							}
							else {
								event.setOutput(ItemStack.EMPTY);
								event.setCost(0);
								event.setMaterialCost(0);
							}
						}
					}
				}
			}					
		}
	}
	
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
