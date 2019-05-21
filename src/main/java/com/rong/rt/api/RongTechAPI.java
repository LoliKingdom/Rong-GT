package com.rong.rt.api;

import java.util.HashMap;
import java.util.Map;

import com.rong.rt.Values;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.OreDictUnifier;
import com.rong.rt.api.unification.materials.Materials;
import com.rong.rt.api.unification.materials.types.DustMaterial;
import com.rong.rt.api.unification.stonetypes.StoneType;
import com.rong.rt.api.utils.BaseCreativeTab;
import com.rong.rt.api.utils.IBlockOre;
import com.rong.rt.common.items.MetaItems;

import net.minecraft.util.ResourceLocation;

public class RongTechAPI {
	
    public static final Map<DustMaterial, Map<StoneType, IBlockOre>> oreBlockTable = new HashMap<>();

    public static final BaseCreativeTab TAB_RT_MAIN =
        new BaseCreativeTab(Values.MOD_ID + ".main", () -> MetaItems.WRENCH.getStackForm(), true);
    public static final BaseCreativeTab TAB_RT_MATERIALS =
        new BaseCreativeTab(Values.MOD_ID + ".materials", () -> OreDictUnifier.get(EnumOrePrefix.ingot, Materials.Aluminium), true);
    public static final BaseCreativeTab TAB_RT_ORES =
        new BaseCreativeTab(Values.MOD_ID + ".ores", () -> MetaItems.HARD_HAMMER.getStackForm(), true);

}