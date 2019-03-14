package gregtech.api.items.toolitem;

import java.util.List;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.impl.ElectricItem;
import gregtech.api.items.toolitem.ToolMetaItem.MetaToolValueItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.RoughSolidMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber(modid = GTValues.MODID)
public class ToolMetaItemEventHandler {

    private static final Object DUMMY_OBJECT = new Object();
    private static final ThreadLocal<Object> harvesting = new ThreadLocal<>();
    
    @SubscribeEvent
    public static void onAnvilChange(AnvilUpdateEvent event) {
        ItemStack leftStack = event.getLeft();
        ItemStack rightStack = event.getRight();
        if(!leftStack.isEmpty() && !rightStack.isEmpty() && leftStack.getItem() instanceof ToolMetaItem) {
            ToolMetaItem<?> toolMetaItem = (ToolMetaItem<?>)leftStack.getItem();
            MetaToolValueItem toolValueItem = toolMetaItem.getItem(leftStack);
            UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(rightStack);
            if(toolValueItem == null) {
                return;
            }
            SolidMaterial toolMaterial = ToolMetaItem.getToolMaterial(leftStack);
            OrePrefix prefix = getAvailablePrefix(toolMaterial);
            double toolDamage = toolMetaItem.getInternalDamage(leftStack) / (toolMetaItem.getMaxInternalDamage(leftStack) * 1.0);
            int materialUnitsRequired = Math.min(rightStack.getCount(), (int)Math.ceil(toolDamage));
            int repairCost = Math.max(2, toolMaterial.harvestLevel) * materialUnitsRequired;
            if(toolDamage > 0.0 && materialUnitsRequired > 0 && unificationEntry != null &&
                unificationEntry.material == toolMaterial && unificationEntry.orePrefix == prefix) {
                int durabilityToRegain = materialUnitsRequired * 100;
                ItemStack outputStack = leftStack.copy();
                toolMetaItem.regainItemDurability(outputStack, durabilityToRegain);
                event.setMaterialCost(materialUnitsRequired);
                event.setCost(repairCost);
                event.setOutput(outputStack);
            }
        }
        else {
        	event.setMaterialCost(0);
            event.setCost(0);
            event.setOutput(ItemStack.EMPTY);
        }
    }
    
    private static OrePrefix getAvailablePrefix(SolidMaterial material) {
        if(material instanceof IngotMaterial) {
            return OrePrefix.ingot;
        } 
        else if(material instanceof GemMaterial) {
            return OrePrefix.gem;
        } 
        else if(material instanceof RoughSolidMaterial) {
            return ((RoughSolidMaterial)material).solidFormSupplier.get();
        } 
        else 
        	return null;
    }
    
    @SubscribeEvent
    public static void onXpOrbPickup(PlayerPickupXpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        EntityXPOrb xpOrb = event.getOrb();
        ItemStack itemStack = EnchantmentHelper.getEnchantedItem(Enchantments.MENDING, player);

        if(!itemStack.isEmpty() && itemStack.getItem() instanceof ToolMetaItem) {
            ToolMetaItem<?> toolMetaItem = (ToolMetaItem<?>)itemStack.getItem();
            int maxDurabilityRegain = xpToDurability(xpOrb.xpValue);
            int durabilityRegained = toolMetaItem.regainItemDurability(itemStack, maxDurabilityRegain);
            xpOrb.xpValue -= durabilityToXp(durabilityRegained);
        }
    }

    private static int xpToDurability(int xp) {
        return xp * 2;
    }

    private static int durabilityToXp(int durability) {
        return durability / 2;
    }

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer harvester = event.getHarvester();
        List<ItemStack> drops = event.getDrops();
        if(harvester == null) {
            return;
        }
        ItemStack stackInHand = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if(stackInHand.isEmpty() || !(stackInHand.getItem() instanceof ToolMetaItem<?>)) {
            return;
        }
        ToolMetaItem<? extends MetaToolValueItem> toolMetaItem = (ToolMetaItem<?>) stackInHand.getItem();
        MetaToolValueItem valueItem = toolMetaItem.getItem(stackInHand);
        IToolStats toolStats = valueItem == null ? null : valueItem.getToolStats();
        if(toolStats == null || !toolStats.isMinableBlock(event.getState(), stackInHand)) {
            return;
        }
        boolean isRecursiveCall = harvesting.get() == DUMMY_OBJECT;
        if(isRecursiveCall && !toolStats.allowRecursiveConversion()) {
            return; //do not call recursive if not allowed by tool stats explicitly
        }
        if(!isRecursiveCall) {
            harvesting.set(DUMMY_OBJECT);
        }
        try {
            int damageDealt = toolStats.convertBlockDrops(event.getWorld(), event.getPos(), event.getState(), harvester, drops, isRecursiveCall, stackInHand);
            if(damageDealt > 0) {
                event.setDropChance(1.0f);
                boolean damagedTool = GTUtility.doDamageItem(stackInHand, damageDealt *
                    toolStats.getToolDamagePerDropConversion(stackInHand), false);
                if(!damagedTool) {
                    //if we can't apply entire damage to tool, just break it
                    stackInHand.shrink(1);
                }
            }
        } 
        finally {
            if(!isRecursiveCall) {
                //restore state only if non-recursive
                harvesting.set(null);
            }
        }
    }
}