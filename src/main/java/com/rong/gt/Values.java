package com.rong.gt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class Values {

	public static final String MOD_ID = "rongtech";
	public static final String MOD_NAME = "RongTech";
	
	/**
     * <p/>
     * This is worth exactly one normal Item.
     * This Constant can be divided by many commonly used Numbers such as
     * 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 24, ... 64 or 81
     * without loosing precision and is for that reason used as Unit of Amount.
     * But it is also small enough to be multiplied with larger Numbers.
     * <p/>
     * This is used to determine the amount of Material contained inside a prefixed Ore.
     * For example Nugget = M / 9 as it contains out of 1/9 of an Ingot.
     */
    public static final long M = 3628800;

    /**
     * Renamed from "FLUID_MATERIAL_UNIT" to just "L"
     * <p/>
     * Fluid per Material Unit (Prime Factors: 3 * 3 * 2 * 2 * 2 * 2)
     */
    public static final int L = 144;
    
    /**
     * For non-ingot/dust fluids, fluid per material unit
     */
    public static final int B = 1000;

    /**
     * The Item WildCard Tag. Even shorter than the "-1" of the past
     */
    public static final short W = OreDictionary.WILDCARD_VALUE;

    /**
     * The Voltage Tiers. Use this Array instead of the old named Voltage Variables
     */
    public static final long[] V = new long[]{32, 128, 512, 2048, 8192, 32768, 131072};

    public static final int LV = 0;
    public static final int MV = 1;
    public static final int HV = 2;
    public static final int EV = 3;
    public static final int IV = 4;
    public static final int LuV = 5;
    public static final int UV = 6;

    /**
     * The short Names for the Voltages
     */
    public static final String[] VN = new String[]{"LV", "MV", "HV", "EV", "IV", "LuV", "UV"};
    
    /**
     * Color values for the voltages
     */
    public static final int[] VC = new int[]{0xDCDCDC, 0xFF6400, 0xFFFF1E, 0x808080, 0xF0F0F5, 0xF0F0F5, 0xF0F0F5};

    /**
     * The long Names for the Voltages
     */
    public static final String[] VOLTAGE_NAMES = new String[]{"Low Voltage", "Medium Voltage", "High Voltage", "Extreme Voltage", "Insane Voltage", "Ludicrous Voltage", "Ultimate Voltage"};

    private static final ConcurrentMap<String, Boolean> isModLoadedCache = new ConcurrentHashMap<>();

    public static boolean isModLoaded(String modid) {
        if(isModLoadedCache.containsKey(modid)) {
            return isModLoadedCache.get(modid);
        }
        boolean isLoaded = Loader.instance().getIndexedModList().containsKey(modid);
        isModLoadedCache.put(modid, isLoaded);
        return isLoaded;
    }
	
}
