package com.rong.gt.api;

import java.util.HashMap;
import java.util.Map;

import com.rong.gt.Values;
import com.rong.gt.api.unification.EnumOrePrefix;
import com.rong.gt.api.unification.OreDictUnifier;
import com.rong.gt.api.unification.materials.Materials;
import com.rong.gt.api.unification.materials.types.DustMaterial;
import com.rong.gt.api.unification.stonetypes.StoneType;
import com.rong.gt.api.utils.BaseCreativeTab;
import com.rong.gt.api.utils.IBlockOre;

import net.minecraft.util.ResourceLocation;

public class RongTechAPI {
	
	//public static BlockMetaMachine MACHINE;
	
    public static final Map<DustMaterial, Map<StoneType, IBlockOre>> oreBlockTable = new HashMap<>();

    public static final BaseCreativeTab TAB_RT_MAIN =
        new BaseCreativeTab(Values.MOD_ID + ".main", () -> MetaItems.BATTERY_HULL_HV.getStackForm(), true);
    public static final BaseCreativeTab TAB_RT_MATERIALS =
        new BaseCreativeTab(Values.MOD_ID + ".materials", () -> OreDictUnifier.get(EnumOrePrefix.ingot, Materials.Aluminium), true);
    public static final BaseCreativeTab TAB_RT_ORES =
        new BaseCreativeTab(Values.MOD_ID + ".ores", () -> MetaItems.HARD_HAMMER.getStackForm(), true);

    //public static final ControlledRegistry<ResourceLocation, MetaTileEntity> META_TILE_ENTITY_REGISTRY = new ControlledRegistry<>(Short.MAX_VALUE);
    
    //public static <T extends MetaTileEntity> T registerMetaTileEntity(int id, T sampleMetaTileEntity) {
        //META_TILE_ENTITY_REGISTRY.register(id, sampleMetaTileEntity.metaTileEntityId, sampleMetaTileEntity);
        //return sampleMetaTileEntity;
}