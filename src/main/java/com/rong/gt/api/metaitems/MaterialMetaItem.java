package com.rong.gt.api.metaitems;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.rong.gt.Values;
import com.rong.gt.api.DamageSources;
import com.rong.gt.api.RongTechAPI;
import com.rong.gt.api.unification.EnumOrePrefix;
import com.rong.gt.api.unification.OreDictUnifier;
import com.rong.gt.api.unification.materials.MaterialIconSet;
import com.rong.gt.api.unification.materials.types.DustMaterial;
import com.rong.gt.api.unification.materials.types.Material;

import gnu.trove.map.hash.TShortObjectHashMap;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialMetaItem extends StandardMetaItem {

    protected EnumOrePrefix[] EnumOrePrefixes;
    private ArrayList<Short> generatedItems = new ArrayList<>();

    public MaterialMetaItem(EnumOrePrefix... EnumOrePrefixes) {
        super((short) (1000 * EnumOrePrefixes.length));
        Preconditions.checkArgument(EnumOrePrefixes.length <= 32, "Max allowed EnumOrePrefix count on MaterialMetaItem is 32.");
        this.EnumOrePrefixes = EnumOrePrefixes;
        for(Material material : Material.MATERIAL_REGISTRY) {
            int i = Material.MATERIAL_REGISTRY.getIDForObject(material);
            for (int j = 0; j < EnumOrePrefixes.length; j++) {
                EnumOrePrefix EnumOrePrefix = EnumOrePrefixes[j];
                if (EnumOrePrefix != null && canGenerate(EnumOrePrefix, material)) {
                    short metadata = (short) (j * 1000 + i);
                    generatedItems.add(metadata);
                }
            }
        }
    }

    public void registerOreDict() {
        for(short metaItem : generatedItems) {
            EnumOrePrefix prefix = this.EnumOrePrefixes[metaItem / 1000];
            Material material = Material.MATERIAL_REGISTRY.getObjectById(metaItem % 1000);
            OreDictUnifier.registerOre(new ItemStack(this, 1, metaItem), prefix, material);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModels() {
        super.registerModels();
        TShortObjectHashMap<ModelResourceLocation> alreadyRegistered = new TShortObjectHashMap<>();
        for (short metaItem : generatedItems) {
            EnumOrePrefix prefix = this.EnumOrePrefixes[metaItem / 1000];
            MaterialIconSet materialIconSet = Material.MATERIAL_REGISTRY.getObjectById(metaItem % 1000).materialIconSet;
            short registrationKey = (short) (metaItem / 1000 * 1000 + materialIconSet.ordinal());
            if (!alreadyRegistered.containsKey(registrationKey)) {
                ResourceLocation resourceLocation = prefix.materialIconType.getItemModelPath(materialIconSet);
                ModelBakery.registerItemVariants(this, resourceLocation);
                alreadyRegistered.put(registrationKey, new ModelResourceLocation(resourceLocation, "inventory"));
            }
            ModelResourceLocation resourceLocation = alreadyRegistered.get(registrationKey);
            metaItemsModels.put(metaItem, resourceLocation);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        if (tintIndex == 0 && stack.getMetadata() < metaItemOffset) {
            Material material = Material.MATERIAL_REGISTRY.getObjectById(stack.getMetadata() % 1000);
            if(material == null) return 0xFFFFFF;
            return material.materialRGB;
        }
        return super.getColorForItemStack(stack, tintIndex);
    }

    protected boolean canGenerate(EnumOrePrefix EnumOrePrefix, Material material) {
        return EnumOrePrefix.doGenerateItem(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack itemStack) {
        if(itemStack.getItemDamage() < metaItemOffset) {
            Material material = Material.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000);
            EnumOrePrefix prefix = EnumOrePrefixes[itemStack.getItemDamage() / 1000];
            if(material == null || prefix == null) return "";
            return prefix.getLocalNameForItem(material);
        }
        return super.getItemStackDisplayName(itemStack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        if(stack.getItemDamage() < metaItemOffset) {
            EnumOrePrefix prefix = EnumOrePrefixes[stack.getItemDamage() / 1000];
            return prefix.maxStackSize;
        }
        return super.getItemStackLimit(stack);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        super.getSubItems(tab, subItems);
        if(tab == RongTechAPI.TAB_RT_MATERIALS || tab == CreativeTabs.SEARCH) {
            for (short metadata : generatedItems) {
                subItems.add(new ItemStack(this, 1, metadata));
            }
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(itemStack.getItemDamage() < metaItemOffset && generatedItems.contains((short) itemStack.getItemDamage()) && entityIn instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            Material material = Material.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000);
            EnumOrePrefix prefix = EnumOrePrefixes[itemStack.getItemDamage() / 1000];
            if(worldIn.getTotalWorldTime() % 20 == 0) {
                if(prefix.heatDamage != 0.0) {
                    if(prefix.heatDamage > 0.0) {
                        entity.attackEntityFrom(DamageSources.getHeatDamage(), prefix.heatDamage);
                    } 
                    else if(prefix.heatDamage < 0.0) {
                        entity.attackEntityFrom(DamageSources.getFrostDamage(), -prefix.heatDamage);
                    }
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);
        int damage = itemStack.getItemDamage();
        if (damage < this.metaItemOffset) {
            Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
            EnumOrePrefix prefix = this.EnumOrePrefixes[(damage / 1000)];
            if(material == null) return;
            addMaterialTooltip(itemStack, prefix, material, lines, tooltipFlag);
        }
    }

    public Material getMaterial(ItemStack itemStack) {
        int damage = itemStack.getItemDamage();
        if (damage < this.metaItemOffset) {
            return Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
        }
        return null;
    }

    public EnumOrePrefix getEnumOrePrefix(ItemStack itemStack) {
        int damage = itemStack.getItemDamage();
        if (damage < this.metaItemOffset) {
            return this.EnumOrePrefixes[(damage / 1000)];
        }
        return null;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        int damage = itemStack.getItemDamage();
        if (damage < this.metaItemOffset) {
            Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
            EnumOrePrefix prefix = this.EnumOrePrefixes[(damage / 1000)];
            if (material instanceof DustMaterial) {
                DustMaterial dustMaterial = (DustMaterial) material;
                return (int) (dustMaterial.burnTime * prefix.materialAmount / Values.M);
            }
        }
        return super.getItemBurnTime(itemStack);

    }

    protected void addMaterialTooltip(ItemStack itemStack, EnumOrePrefix prefix, Material material, List<String> lines, ITooltipFlag tooltipFlag) {}
}
