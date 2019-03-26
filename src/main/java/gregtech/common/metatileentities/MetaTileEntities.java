package gregtech.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTLog;
import gregtech.common.metatileentities.electric.MetaTileEntityAirCollector;
import gregtech.common.metatileentities.electric.MetaTileEntityBatteryBuffer;
import gregtech.common.metatileentities.electric.MetaTileEntityCharger;
import gregtech.common.metatileentities.electric.MetaTileEntityHull;
import gregtech.common.metatileentities.electric.MetaTileEntityLightningHarvester;
import gregtech.common.metatileentities.electric.MetaTileEntityMacerator;
import gregtech.common.metatileentities.electric.MetaTileEntityMagicEnergyAbsorber;
import gregtech.common.metatileentities.electric.MetaTileEntityPump;
import gregtech.common.metatileentities.electric.MetaTileEntityTeslaCoil;
import gregtech.common.metatileentities.electric.MetaTileEntityTransformer;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityEnergyHatch;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityItemBus;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gregtech.common.metatileentities.multi.MetaTileEntityCokeOven;
import gregtech.common.metatileentities.multi.MetaTileEntityCokeOvenHatch;
import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler;
import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler.BoilerType;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveBlastFurnace;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityAssemblyLine;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityBedrockDrillingUnit;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityCrackingUnit;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityDistillationTower;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityElectricBlastFurnace;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityFusionReactor;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityImplosionCompressor;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityMultiFurnace;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityPyrolyseOven;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityVacuumFreezer;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityDieselEngine;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine.TurbineType;
import gregtech.common.metatileentities.steam.SteamAlloySmelter;
import gregtech.common.metatileentities.steam.SteamCompressor;
import gregtech.common.metatileentities.steam.SteamExtractor;
import gregtech.common.metatileentities.steam.SteamFurnace;
import gregtech.common.metatileentities.steam.SteamHammer;
import gregtech.common.metatileentities.steam.SteamMacerator;
import gregtech.common.metatileentities.steam.boiler.SteamCoalBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamLavaBoiler;
import gregtech.common.metatileentities.storage.MetaTileEntityQuantumChest;
import gregtech.common.metatileentities.storage.MetaTileEntityQuantumTank;
import gregtech.common.metatileentities.storage.MetaTileEntityTank;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("WeakerAccess")
public class MetaTileEntities {

    //HULLS
    public static MetaTileEntityHull[] HULL = new MetaTileEntityHull[GTValues.V.length];
    public static MetaTileEntityTransformer[] TRANSFORMER = new MetaTileEntityTransformer[GTValues.V.length];
    public static MetaTileEntityBatteryBuffer[][] BATTERY_BUFFER = new MetaTileEntityBatteryBuffer[GTValues.V.length][];
    public static MetaTileEntityCharger[] CHARGER = new MetaTileEntityCharger[GTValues.V.length];

    //BRONZE MACHINES SECTION
    public static SteamCoalBoiler STEAM_BOILER_COAL_BRONZE;
    public static SteamCoalBoiler STEAM_BOILER_COAL_STEEL;
    public static SteamLavaBoiler STEAM_BOILER_LAVA_BRONZE;
    public static SteamLavaBoiler STEAM_BOILER_LAVA_STEEL;
    public static SteamExtractor STEAM_EXTRACTOR_BRONZE;
    public static SteamExtractor STEAM_EXTRACTOR_STEEL;
    public static SteamMacerator STEAM_MACERATOR_BRONZE;
    public static SteamMacerator STEAM_MACERATOR_STEEL;
    public static SteamCompressor STEAM_COMPRESSOR_BRONZE;
    public static SteamCompressor STEAM_COMPRESSOR_STEEL;
    public static SteamHammer STEAM_HAMMER_BRONZE;
    public static SteamHammer STEAM_HAMMER_STEEL;
    public static SteamFurnace STEAM_FURNACE_BRONZE;
    public static SteamFurnace STEAM_FURNACE_STEEL;
    public static SteamAlloySmelter STEAM_ALLOY_SMELTER_BRONZE;
    public static SteamAlloySmelter STEAM_ALLOY_SMELTER_STEEL;

    //SIMPLE MACHINES SECTION
    public static SimpleMachineMetaTileEntity[] ELECTRIC_FURNACE = new SimpleMachineMetaTileEntity[4];
    public static MetaTileEntityMacerator[] MACERATOR = new MetaTileEntityMacerator[7];
    public static SimpleMachineMetaTileEntity[] ALLOY_SMELTER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] ASSEMBLER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] AUTOCLAVE = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] BENDER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] BREWERY = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] CANNER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] CENTRIFUGE = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] CHEMICAL_BATH = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] CHEMICAL_REACTOR = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] COMPRESSOR = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] CUTTER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] DISTILLERY = new SimpleMachineMetaTileEntity[3];
    public static SimpleMachineMetaTileEntity[] ELECTROLYZER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] EXTRACTOR = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] EXTRUDER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] FLUID_HEATER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] FLUID_SOLIDIFIER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] FORGE_HAMMER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] PRESS = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] LATHE = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] MIXER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] ORE_WASHER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] PLASMA_ARC_FURNACE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] POLARIZER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] LASER_ENGRAVER = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] THERMAL_CENTRIFUGE = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] WIREMILL = new SimpleMachineMetaTileEntity[7];
    public static SimpleMachineMetaTileEntity[] RECYCLER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] MASS_FABRICATOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] REPLICATOR = new SimpleMachineMetaTileEntity[4];

    //GENERATORS SECTION
    public static SimpleGeneratorMetaTileEntity[] DIESEL_GENERATOR = new SimpleGeneratorMetaTileEntity[4];
    public static SimpleGeneratorMetaTileEntity[] STEAM_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static SimpleGeneratorMetaTileEntity[] GAS_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static MetaTileEntityMagicEnergyAbsorber[] MAGIC_ENERGY_ABSORBER = new MetaTileEntityMagicEnergyAbsorber[4]; //HV-LuV
    public static MetaTileEntityLightningHarvester[] LIGHTNING_HARVESTER = new MetaTileEntityLightningHarvester[5]; //HV-UV

    //MULTIBLOCK PARTS SECTION
    public static MetaTileEntityItemBus[] ITEM_IMPORT_BUS = new MetaTileEntityItemBus[GTValues.V.length];
    public static MetaTileEntityItemBus[] ITEM_EXPORT_BUS = new MetaTileEntityItemBus[GTValues.V.length];
    public static MetaTileEntityFluidHatch[] FLUID_IMPORT_HATCH = new MetaTileEntityFluidHatch[GTValues.V.length];
    public static MetaTileEntityFluidHatch[] FLUID_EXPORT_HATCH = new MetaTileEntityFluidHatch[GTValues.V.length];
    public static MetaTileEntityEnergyHatch[] ENERGY_INPUT_HATCH = new MetaTileEntityEnergyHatch[GTValues.V.length];
    public static MetaTileEntityEnergyHatch[] ENERGY_OUTPUT_HATCH = new MetaTileEntityEnergyHatch[GTValues.V.length];
    public static MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[3]; //HV, IV, UV
    
    public static MetaTileEntityCokeOvenHatch COKE_OVEN_HATCH;

    //MULTIBLOCKS SECTION
    public static MetaTileEntityPrimitiveBlastFurnace PRIMITIVE_BLAST_FURNACE;
    public static MetaTileEntityElectricBlastFurnace ELECTRIC_BLAST_FURNACE;
    public static MetaTileEntityVacuumFreezer VACUUM_FREEZER;
    public static MetaTileEntityImplosionCompressor IMPLOSION_COMPRESSOR;
    public static MetaTileEntityPyrolyseOven PYROLYSE_OVEN;
    public static MetaTileEntityDistillationTower DISTILLATION_TOWER;
    public static MetaTileEntityCrackingUnit CRACKER;
    public static MetaTileEntityMultiFurnace MULTI_FURNACE;
    public static MetaTileEntityDieselEngine DIESEL_ENGINE;
    
    public static MetaTileEntityCokeOven COKE_OVEN;
    
    public static MetaTileEntityBedrockDrillingUnit BEDROCK_DRILL;
    public static MetaTileEntityAssemblyLine ASSEMBLY_LINE;
    
    public static MetaTileEntityFusionReactor[] FUSION_REACTOR = new MetaTileEntityFusionReactor[2];

    public static MetaTileEntityLargeTurbine LARGE_STEAM_TURBINE;
    public static MetaTileEntityLargeTurbine LARGE_GAS_TURBINE;
    public static MetaTileEntityLargeTurbine LARGE_PLASMA_TURBINE;

    public static MetaTileEntityLargeBoiler LARGE_BRONZE_BOILER;
    public static MetaTileEntityLargeBoiler LARGE_STEEL_BOILER;
    public static MetaTileEntityLargeBoiler LARGE_TITANIUM_BOILER;
    public static MetaTileEntityLargeBoiler LARGE_TUNGSTENSTEEL_BOILER;

    //STORAGE SECTION 
    public static MetaTileEntityQuantumChest[] QUANTUM_CHEST = new MetaTileEntityQuantumChest[4];
    public static MetaTileEntityQuantumTank[] QUANTUM_TANK = new MetaTileEntityQuantumTank[4];
    
    public static MetaTileEntityTank WOODEN_TANK;
    public static MetaTileEntityTank BRONZE_TANK;
    public static MetaTileEntityTank STEEL_TANK;
    public static MetaTileEntityTank STAINLESS_STEEL_TANK;
    public static MetaTileEntityTank TITANIUM_TANK;
    public static MetaTileEntityTank TUNGSTENSTEEL_TANK;
    public static MetaTileEntityTank IRIDIUM_TANK;
    public static MetaTileEntityTank ADAMANTINE_TANK;

    //MISC MACHINES SECTION
    public static MetaTileEntityPump[] PUMP = new MetaTileEntityPump[7];
    public static MetaTileEntityAirCollector[] AIR_COLLECTOR = new MetaTileEntityAirCollector[7];
    public static MetaTileEntityTeslaCoil TESLA_COIL;
    

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        STEAM_BOILER_COAL_BRONZE = GregTechAPI.registerMetaTileEntity(1, new SteamCoalBoiler(gregtechId("steam_boiler_coal_bronze"), false));
        STEAM_BOILER_COAL_STEEL = GregTechAPI.registerMetaTileEntity(2, new SteamCoalBoiler(gregtechId("steam_boiler_coal_steel"), true));

        STEAM_BOILER_LAVA_BRONZE = GregTechAPI.registerMetaTileEntity(5, new SteamLavaBoiler(gregtechId("steam_boiler_lava_bronze"), false));
        STEAM_BOILER_LAVA_STEEL = GregTechAPI.registerMetaTileEntity(6, new SteamLavaBoiler(gregtechId("steam_boiler_lava_steel"), true));

        STEAM_EXTRACTOR_BRONZE = GregTechAPI.registerMetaTileEntity(7, new SteamExtractor(gregtechId("steam_extractor_bronze"), false));
        STEAM_EXTRACTOR_STEEL = GregTechAPI.registerMetaTileEntity(8, new SteamExtractor(gregtechId("steam_extractor_steel"), true));

        STEAM_MACERATOR_BRONZE = GregTechAPI.registerMetaTileEntity(9, new SteamMacerator(gregtechId("steam_macerator_bronze"), false));
        STEAM_MACERATOR_STEEL = GregTechAPI.registerMetaTileEntity(10, new SteamMacerator(gregtechId("steam_macerator_steel"), true));

        STEAM_COMPRESSOR_BRONZE = GregTechAPI.registerMetaTileEntity(11, new SteamCompressor(gregtechId("steam_compressor_bronze"), false));
        STEAM_COMPRESSOR_STEEL = GregTechAPI.registerMetaTileEntity(12, new SteamCompressor(gregtechId("steam_compressor_steel"), true));

        STEAM_HAMMER_BRONZE = GregTechAPI.registerMetaTileEntity(13, new SteamHammer(gregtechId("steam_hammer_bronze"), false));
        STEAM_HAMMER_STEEL = GregTechAPI.registerMetaTileEntity(14, new SteamHammer(gregtechId("steam_hammer_steel"), true));

        STEAM_FURNACE_BRONZE = GregTechAPI.registerMetaTileEntity(15, new SteamFurnace(gregtechId("steam_furnace_bronze"), false));
        STEAM_FURNACE_STEEL = GregTechAPI.registerMetaTileEntity(16, new SteamFurnace(gregtechId("steam_furnace_steel"), true));

        STEAM_ALLOY_SMELTER_BRONZE = GregTechAPI.registerMetaTileEntity(17, new SteamAlloySmelter(gregtechId("steam_alloy_smelter_bronze"), false));
        STEAM_ALLOY_SMELTER_STEEL = GregTechAPI.registerMetaTileEntity(18, new SteamAlloySmelter(gregtechId("steam_alloy_smelter_steel"), true));

        ELECTRIC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(50, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.lv"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 0));
        ELECTRIC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(51, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.mv"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 1));
        ELECTRIC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(52, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.hv"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 2));
        ELECTRIC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(53, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.ev"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 3));

        MACERATOR[0] = GregTechAPI.registerMetaTileEntity(60, new MetaTileEntityMacerator(gregtechId("macerator.lv"), RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 0));
        MACERATOR[1] = GregTechAPI.registerMetaTileEntity(61, new MetaTileEntityMacerator(gregtechId("macerator.mv"), RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 1));
        MACERATOR[2] = GregTechAPI.registerMetaTileEntity(62, new MetaTileEntityMacerator(gregtechId("macerator.hv"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 2));
        MACERATOR[3] = GregTechAPI.registerMetaTileEntity(63, new MetaTileEntityMacerator(gregtechId("macerator.ev"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 3));
        MACERATOR[4] = GregTechAPI.registerMetaTileEntity(64, new MetaTileEntityMacerator(gregtechId("macerator.iv"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 4));
        MACERATOR[5] = GregTechAPI.registerMetaTileEntity(65, new MetaTileEntityMacerator(gregtechId("macerator.luv"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 5));
        MACERATOR[6] = GregTechAPI.registerMetaTileEntity(66, new MetaTileEntityMacerator(gregtechId("macerator.uv"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 6));

        ALLOY_SMELTER[0] = GregTechAPI.registerMetaTileEntity(70, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.lv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 0));
        ALLOY_SMELTER[1] = GregTechAPI.registerMetaTileEntity(71, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.mv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        ALLOY_SMELTER[2] = GregTechAPI.registerMetaTileEntity(72, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.hv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        ALLOY_SMELTER[3] = GregTechAPI.registerMetaTileEntity(73, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.ev"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        ALLOY_SMELTER[4] = GregTechAPI.registerMetaTileEntity(74, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.iv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        ALLOY_SMELTER[5] = GregTechAPI.registerMetaTileEntity(75, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.luv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        ALLOY_SMELTER[6] = GregTechAPI.registerMetaTileEntity(76, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.uv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));

        ASSEMBLER[0] = GregTechAPI.registerMetaTileEntity(100, new SimpleMachineMetaTileEntity(gregtechId("assembler.lv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 0));
        ASSEMBLER[1] = GregTechAPI.registerMetaTileEntity(101, new SimpleMachineMetaTileEntity(gregtechId("assembler.mv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 1));
        ASSEMBLER[2] = GregTechAPI.registerMetaTileEntity(102, new SimpleMachineMetaTileEntity(gregtechId("assembler.hv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 2));
        ASSEMBLER[3] = GregTechAPI.registerMetaTileEntity(103, new SimpleMachineMetaTileEntity(gregtechId("assembler.ev"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 3));
        ASSEMBLER[4] = GregTechAPI.registerMetaTileEntity(104, new SimpleMachineMetaTileEntity(gregtechId("assembler.iv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 4));
        ASSEMBLER[5] = GregTechAPI.registerMetaTileEntity(105, new SimpleMachineMetaTileEntity(gregtechId("assembler.luv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 5));
        ASSEMBLER[6] = GregTechAPI.registerMetaTileEntity(106, new SimpleMachineMetaTileEntity(gregtechId("assembler.uv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 6));

        AUTOCLAVE[0] = GregTechAPI.registerMetaTileEntity(110, new SimpleMachineMetaTileEntity(gregtechId("autoclave.lv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 0, false));
        AUTOCLAVE[1] = GregTechAPI.registerMetaTileEntity(111, new SimpleMachineMetaTileEntity(gregtechId("autoclave.mv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 1, false));
        AUTOCLAVE[2] = GregTechAPI.registerMetaTileEntity(112, new SimpleMachineMetaTileEntity(gregtechId("autoclave.hv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 2, false));
        AUTOCLAVE[3] = GregTechAPI.registerMetaTileEntity(113, new SimpleMachineMetaTileEntity(gregtechId("autoclave.ev"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 3, false));
        AUTOCLAVE[4] = GregTechAPI.registerMetaTileEntity(114, new SimpleMachineMetaTileEntity(gregtechId("autoclave.iv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 4, false));
        AUTOCLAVE[5] = GregTechAPI.registerMetaTileEntity(115, new SimpleMachineMetaTileEntity(gregtechId("autoclave.luv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 5, false));
        AUTOCLAVE[6] = GregTechAPI.registerMetaTileEntity(116, new SimpleMachineMetaTileEntity(gregtechId("autoclave.uv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 6, false));

        BENDER[0] = GregTechAPI.registerMetaTileEntity(120, new SimpleMachineMetaTileEntity(gregtechId("bender.lv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 0));
        BENDER[1] = GregTechAPI.registerMetaTileEntity(121, new SimpleMachineMetaTileEntity(gregtechId("bender.mv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 1));
        BENDER[2] = GregTechAPI.registerMetaTileEntity(122, new SimpleMachineMetaTileEntity(gregtechId("bender.hv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 2));
        BENDER[3] = GregTechAPI.registerMetaTileEntity(123, new SimpleMachineMetaTileEntity(gregtechId("bender.ev"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 3));
        BENDER[4] = GregTechAPI.registerMetaTileEntity(124, new SimpleMachineMetaTileEntity(gregtechId("bender.iv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 4));
        BENDER[5] = GregTechAPI.registerMetaTileEntity(125, new SimpleMachineMetaTileEntity(gregtechId("bender.luv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 5));
        BENDER[6] = GregTechAPI.registerMetaTileEntity(126, new SimpleMachineMetaTileEntity(gregtechId("bender.uv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 6));

        BREWERY[0] = GregTechAPI.registerMetaTileEntity(130, new SimpleMachineMetaTileEntity(gregtechId("brewery.lv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 0));
        BREWERY[1] = GregTechAPI.registerMetaTileEntity(131, new SimpleMachineMetaTileEntity(gregtechId("brewery.mv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 1));
        BREWERY[2] = GregTechAPI.registerMetaTileEntity(132, new SimpleMachineMetaTileEntity(gregtechId("brewery.hv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 2));
        BREWERY[3] = GregTechAPI.registerMetaTileEntity(133, new SimpleMachineMetaTileEntity(gregtechId("brewery.ev"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 3));
        BREWERY[4] = GregTechAPI.registerMetaTileEntity(134, new SimpleMachineMetaTileEntity(gregtechId("brewery.iv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 4));
        BREWERY[5] = GregTechAPI.registerMetaTileEntity(135, new SimpleMachineMetaTileEntity(gregtechId("brewery.luv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 5));
        BREWERY[6] = GregTechAPI.registerMetaTileEntity(136, new SimpleMachineMetaTileEntity(gregtechId("brewery.uv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 6));

        CANNER[0] = GregTechAPI.registerMetaTileEntity(140, new SimpleMachineMetaTileEntity(gregtechId("canner.lv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 0));
        CANNER[1] = GregTechAPI.registerMetaTileEntity(141, new SimpleMachineMetaTileEntity(gregtechId("canner.mv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 1));
        CANNER[2] = GregTechAPI.registerMetaTileEntity(142, new SimpleMachineMetaTileEntity(gregtechId("canner.hv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 2));
        CANNER[3] = GregTechAPI.registerMetaTileEntity(143, new SimpleMachineMetaTileEntity(gregtechId("canner.ev"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 3));
        CANNER[4] = GregTechAPI.registerMetaTileEntity(144, new SimpleMachineMetaTileEntity(gregtechId("canner.iv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 4));
        CANNER[5] = GregTechAPI.registerMetaTileEntity(145, new SimpleMachineMetaTileEntity(gregtechId("canner.luv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 5));
        CANNER[6] = GregTechAPI.registerMetaTileEntity(146, new SimpleMachineMetaTileEntity(gregtechId("canner.uv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 6));

        CENTRIFUGE[0] = GregTechAPI.registerMetaTileEntity(150, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.lv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 0, false));
        CENTRIFUGE[1] = GregTechAPI.registerMetaTileEntity(151, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.mv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 1, false));
        CENTRIFUGE[2] = GregTechAPI.registerMetaTileEntity(152, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.hv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 2, false));
        CENTRIFUGE[3] = GregTechAPI.registerMetaTileEntity(153, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.ev"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 3, false));
        CENTRIFUGE[4] = GregTechAPI.registerMetaTileEntity(154, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.iv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 4, false));
        CENTRIFUGE[5] = GregTechAPI.registerMetaTileEntity(155, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.luv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 5, false));
        CENTRIFUGE[6] = GregTechAPI.registerMetaTileEntity(156, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.uv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 6, false));

        CHEMICAL_BATH[0] = GregTechAPI.registerMetaTileEntity(180, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.lv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 0));
        CHEMICAL_BATH[1] = GregTechAPI.registerMetaTileEntity(181, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.mv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 1));
        CHEMICAL_BATH[2] = GregTechAPI.registerMetaTileEntity(182, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.hv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 2));
        CHEMICAL_BATH[3] = GregTechAPI.registerMetaTileEntity(183, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.ev"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 3));
        CHEMICAL_BATH[4] = GregTechAPI.registerMetaTileEntity(184, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.iv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 4));
        CHEMICAL_BATH[5] = GregTechAPI.registerMetaTileEntity(185, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.luv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 5));
        CHEMICAL_BATH[6] = GregTechAPI.registerMetaTileEntity(186, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.uv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 6));

        CHEMICAL_REACTOR[0] = GregTechAPI.registerMetaTileEntity(190, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.lv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 0));
        CHEMICAL_REACTOR[1] = GregTechAPI.registerMetaTileEntity(191, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.mv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 1));
        CHEMICAL_REACTOR[2] = GregTechAPI.registerMetaTileEntity(192, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.hv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2));
        CHEMICAL_REACTOR[3] = GregTechAPI.registerMetaTileEntity(193, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.ev"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3));
        CHEMICAL_REACTOR[4] = GregTechAPI.registerMetaTileEntity(194, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.iv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4));
        CHEMICAL_REACTOR[5] = GregTechAPI.registerMetaTileEntity(195, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.luv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 5));
        CHEMICAL_REACTOR[6] = GregTechAPI.registerMetaTileEntity(196, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.uv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 6));

        COMPRESSOR[0] = GregTechAPI.registerMetaTileEntity(210, new SimpleMachineMetaTileEntity(gregtechId("compressor.lv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 0));
        COMPRESSOR[1] = GregTechAPI.registerMetaTileEntity(211, new SimpleMachineMetaTileEntity(gregtechId("compressor.mv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 1));
        COMPRESSOR[2] = GregTechAPI.registerMetaTileEntity(212, new SimpleMachineMetaTileEntity(gregtechId("compressor.hv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 2));
        COMPRESSOR[3] = GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity(gregtechId("compressor.ev"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 3));
        COMPRESSOR[4] = GregTechAPI.registerMetaTileEntity(214, new SimpleMachineMetaTileEntity(gregtechId("compressor.iv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 4));
        COMPRESSOR[5] = GregTechAPI.registerMetaTileEntity(215, new SimpleMachineMetaTileEntity(gregtechId("compressor.luv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 5));
        COMPRESSOR[6] = GregTechAPI.registerMetaTileEntity(216, new SimpleMachineMetaTileEntity(gregtechId("compressor.uv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 6));

        CUTTER[0] = GregTechAPI.registerMetaTileEntity(220, new SimpleMachineMetaTileEntity(gregtechId("cutter.lv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 0));
        CUTTER[1] = GregTechAPI.registerMetaTileEntity(221, new SimpleMachineMetaTileEntity(gregtechId("cutter.mv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 1));
        CUTTER[2] = GregTechAPI.registerMetaTileEntity(222, new SimpleMachineMetaTileEntity(gregtechId("cutter.hv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 2));
        CUTTER[3] = GregTechAPI.registerMetaTileEntity(223, new SimpleMachineMetaTileEntity(gregtechId("cutter.ev"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 3));
        CUTTER[4] = GregTechAPI.registerMetaTileEntity(224, new SimpleMachineMetaTileEntity(gregtechId("cutter.iv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 4));
        CUTTER[5] = GregTechAPI.registerMetaTileEntity(225, new SimpleMachineMetaTileEntity(gregtechId("cutter.luv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 5));
        CUTTER[6] = GregTechAPI.registerMetaTileEntity(226, new SimpleMachineMetaTileEntity(gregtechId("cutter.uv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 6));

        DISTILLERY[0] = GregTechAPI.registerMetaTileEntity(230, new SimpleMachineMetaTileEntity(gregtechId("distillery.lv"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 0));
        DISTILLERY[1] = GregTechAPI.registerMetaTileEntity(231, new SimpleMachineMetaTileEntity(gregtechId("distillery.mv"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 1));
        DISTILLERY[2] = GregTechAPI.registerMetaTileEntity(232, new SimpleMachineMetaTileEntity(gregtechId("distillery.hv"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 2));

        ELECTROLYZER[0] = GregTechAPI.registerMetaTileEntity(240, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.lv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 0, false));
        ELECTROLYZER[1] = GregTechAPI.registerMetaTileEntity(241, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.mv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 1, false));
        ELECTROLYZER[2] = GregTechAPI.registerMetaTileEntity(242, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.hv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 2, false));
        ELECTROLYZER[3] = GregTechAPI.registerMetaTileEntity(243, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.ev"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 3, false));
        ELECTROLYZER[4] = GregTechAPI.registerMetaTileEntity(244, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.iv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 4, false));
        ELECTROLYZER[5] = GregTechAPI.registerMetaTileEntity(245, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.luv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 5, false));
        ELECTROLYZER[6] = GregTechAPI.registerMetaTileEntity(246, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.uv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 6, false));

        EXTRACTOR[0] = GregTechAPI.registerMetaTileEntity(260, new SimpleMachineMetaTileEntity(gregtechId("extractor.lv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 0));
        EXTRACTOR[1] = GregTechAPI.registerMetaTileEntity(261, new SimpleMachineMetaTileEntity(gregtechId("extractor.mv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 1));
        EXTRACTOR[2] = GregTechAPI.registerMetaTileEntity(262, new SimpleMachineMetaTileEntity(gregtechId("extractor.hv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 2));
        EXTRACTOR[3] = GregTechAPI.registerMetaTileEntity(263, new SimpleMachineMetaTileEntity(gregtechId("extractor.ev"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 3));
        EXTRACTOR[4] = GregTechAPI.registerMetaTileEntity(264, new SimpleMachineMetaTileEntity(gregtechId("extractor.iv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 4));
        EXTRACTOR[5] = GregTechAPI.registerMetaTileEntity(265, new SimpleMachineMetaTileEntity(gregtechId("extractor.luv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 5));
        EXTRACTOR[6] = GregTechAPI.registerMetaTileEntity(266, new SimpleMachineMetaTileEntity(gregtechId("extractor.uv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 6));
        
        EXTRUDER[0] = GregTechAPI.registerMetaTileEntity(270, new SimpleMachineMetaTileEntity(gregtechId("extruder.lv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 0));
        EXTRUDER[1] = GregTechAPI.registerMetaTileEntity(271, new SimpleMachineMetaTileEntity(gregtechId("extruder.mv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 1));
        EXTRUDER[2] = GregTechAPI.registerMetaTileEntity(272, new SimpleMachineMetaTileEntity(gregtechId("extruder.hv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 2));
        EXTRUDER[3] = GregTechAPI.registerMetaTileEntity(273, new SimpleMachineMetaTileEntity(gregtechId("extruder.ev"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 3));
        EXTRUDER[4] = GregTechAPI.registerMetaTileEntity(274, new SimpleMachineMetaTileEntity(gregtechId("extruder.iv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 4));
        EXTRUDER[5] = GregTechAPI.registerMetaTileEntity(275, new SimpleMachineMetaTileEntity(gregtechId("extruder.luv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 5));
        EXTRUDER[6] = GregTechAPI.registerMetaTileEntity(276, new SimpleMachineMetaTileEntity(gregtechId("extruder.uv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 6));

        FLUID_HEATER[0] = GregTechAPI.registerMetaTileEntity(310, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.lv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 0));
        FLUID_HEATER[1] = GregTechAPI.registerMetaTileEntity(311, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.mv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 1));
        FLUID_HEATER[2] = GregTechAPI.registerMetaTileEntity(312, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.hv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 2));
        FLUID_HEATER[3] = GregTechAPI.registerMetaTileEntity(313, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.ev"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 3));
        FLUID_HEATER[4] = GregTechAPI.registerMetaTileEntity(314, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.iv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 4));
        FLUID_HEATER[5] = GregTechAPI.registerMetaTileEntity(315, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.luv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 5));
        FLUID_HEATER[6] = GregTechAPI.registerMetaTileEntity(316, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.uv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 6));

        FLUID_SOLIDIFIER[0] = GregTechAPI.registerMetaTileEntity(320, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.lv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 0));
        FLUID_SOLIDIFIER[1] = GregTechAPI.registerMetaTileEntity(321, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.mv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 1));
        FLUID_SOLIDIFIER[2] = GregTechAPI.registerMetaTileEntity(322, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.hv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 2));
        FLUID_SOLIDIFIER[3] = GregTechAPI.registerMetaTileEntity(323, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.ev"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 3));
        FLUID_SOLIDIFIER[4] = GregTechAPI.registerMetaTileEntity(324, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.iv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 4));
        FLUID_SOLIDIFIER[5] = GregTechAPI.registerMetaTileEntity(325, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.luv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 5));
        FLUID_SOLIDIFIER[6] = GregTechAPI.registerMetaTileEntity(326, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.uv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 6));

        FORGE_HAMMER[0] = GregTechAPI.registerMetaTileEntity(330, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.lv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 0));
        FORGE_HAMMER[1] = GregTechAPI.registerMetaTileEntity(331, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.mv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 1));
        FORGE_HAMMER[2] = GregTechAPI.registerMetaTileEntity(332, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.hv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 2));
        FORGE_HAMMER[3] = GregTechAPI.registerMetaTileEntity(333, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.ev"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 3));
        FORGE_HAMMER[4] = GregTechAPI.registerMetaTileEntity(334, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.iv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 4));
        FORGE_HAMMER[5] = GregTechAPI.registerMetaTileEntity(335, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.luv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 5));
        FORGE_HAMMER[6] = GregTechAPI.registerMetaTileEntity(336, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.uv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 6));

        PRESS[0] = GregTechAPI.registerMetaTileEntity(340, new SimpleMachineMetaTileEntity(gregtechId("press.lv"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 0));
        PRESS[1] = GregTechAPI.registerMetaTileEntity(341, new SimpleMachineMetaTileEntity(gregtechId("press.mv"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 1));
        PRESS[2] = GregTechAPI.registerMetaTileEntity(342, new SimpleMachineMetaTileEntity(gregtechId("press.hv"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 2));
        PRESS[3] = GregTechAPI.registerMetaTileEntity(343, new SimpleMachineMetaTileEntity(gregtechId("press.ev"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 3));
        PRESS[4] = GregTechAPI.registerMetaTileEntity(344, new SimpleMachineMetaTileEntity(gregtechId("press.iv"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 4));
        PRESS[5] = GregTechAPI.registerMetaTileEntity(345, new SimpleMachineMetaTileEntity(gregtechId("press.luv"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 5));
        PRESS[6] = GregTechAPI.registerMetaTileEntity(346, new SimpleMachineMetaTileEntity(gregtechId("press.uv"), RecipeMaps.PRESS_RECIPES, Textures.PRESS_OVERLAY, 6));

        LATHE[0] = GregTechAPI.registerMetaTileEntity(350, new SimpleMachineMetaTileEntity(gregtechId("lathe.lv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 0));
        LATHE[1] = GregTechAPI.registerMetaTileEntity(351, new SimpleMachineMetaTileEntity(gregtechId("lathe.mv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 1));
        LATHE[2] = GregTechAPI.registerMetaTileEntity(352, new SimpleMachineMetaTileEntity(gregtechId("lathe.hv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 2));
        LATHE[3] = GregTechAPI.registerMetaTileEntity(353, new SimpleMachineMetaTileEntity(gregtechId("lathe.ev"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 3));
        LATHE[4] = GregTechAPI.registerMetaTileEntity(354, new SimpleMachineMetaTileEntity(gregtechId("lathe.iv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 4));
        LATHE[5] = GregTechAPI.registerMetaTileEntity(355, new SimpleMachineMetaTileEntity(gregtechId("lathe.luv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 5));
        LATHE[6] = GregTechAPI.registerMetaTileEntity(356, new SimpleMachineMetaTileEntity(gregtechId("lathe.uv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 6));

        MIXER[0] = GregTechAPI.registerMetaTileEntity(370, new SimpleMachineMetaTileEntity(gregtechId("mixer.lv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 0, false));
        MIXER[1] = GregTechAPI.registerMetaTileEntity(371, new SimpleMachineMetaTileEntity(gregtechId("mixer.mv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 1, false));
        MIXER[2] = GregTechAPI.registerMetaTileEntity(372, new SimpleMachineMetaTileEntity(gregtechId("mixer.hv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 2, false));
        MIXER[3] = GregTechAPI.registerMetaTileEntity(373, new SimpleMachineMetaTileEntity(gregtechId("mixer.ev"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 3, false));
        MIXER[4] = GregTechAPI.registerMetaTileEntity(374, new SimpleMachineMetaTileEntity(gregtechId("mixer.iv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 4, false));
        MIXER[5] = GregTechAPI.registerMetaTileEntity(375, new SimpleMachineMetaTileEntity(gregtechId("mixer.luv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 5, false));
        MIXER[6] = GregTechAPI.registerMetaTileEntity(376, new SimpleMachineMetaTileEntity(gregtechId("mixer.uv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 6, false));

        ORE_WASHER[0] = GregTechAPI.registerMetaTileEntity(380, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.lv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 0));
        ORE_WASHER[1] = GregTechAPI.registerMetaTileEntity(381, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.mv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 1));
        ORE_WASHER[2] = GregTechAPI.registerMetaTileEntity(382, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.hv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 2));
        ORE_WASHER[3] = GregTechAPI.registerMetaTileEntity(383, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.ev"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 3));
        ORE_WASHER[4] = GregTechAPI.registerMetaTileEntity(384, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.iv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 4));
        ORE_WASHER[5] = GregTechAPI.registerMetaTileEntity(385, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.luv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 5));
        ORE_WASHER[6] = GregTechAPI.registerMetaTileEntity(386, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.uv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 6));

        PLASMA_ARC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(390, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.hv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 2, false));
        PLASMA_ARC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(391, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.ev"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 3, false));
        PLASMA_ARC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(392, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.iv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 4, false));
        PLASMA_ARC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(393, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.luv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 5, false));
        PLASMA_ARC_FURNACE[4] = GregTechAPI.registerMetaTileEntity(394, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.uv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 6, false));

        POLARIZER[0] = GregTechAPI.registerMetaTileEntity(420, new SimpleMachineMetaTileEntity(gregtechId("polarizer.lv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 0));
        POLARIZER[1] = GregTechAPI.registerMetaTileEntity(421, new SimpleMachineMetaTileEntity(gregtechId("polarizer.mv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 1));
        POLARIZER[2] = GregTechAPI.registerMetaTileEntity(422, new SimpleMachineMetaTileEntity(gregtechId("polarizer.hv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 2));
        POLARIZER[3] = GregTechAPI.registerMetaTileEntity(423, new SimpleMachineMetaTileEntity(gregtechId("polarizer.ev"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 3));
        POLARIZER[4] = GregTechAPI.registerMetaTileEntity(424, new SimpleMachineMetaTileEntity(gregtechId("polarizer.iv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 4));
        POLARIZER[5] = GregTechAPI.registerMetaTileEntity(425, new SimpleMachineMetaTileEntity(gregtechId("polarizer.luv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 5));
        POLARIZER[6] = GregTechAPI.registerMetaTileEntity(426, new SimpleMachineMetaTileEntity(gregtechId("polarizer.uv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 6));

        LASER_ENGRAVER[0] = GregTechAPI.registerMetaTileEntity(430, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.lv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 0));
        LASER_ENGRAVER[1] = GregTechAPI.registerMetaTileEntity(431, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.mv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 1));
        LASER_ENGRAVER[2] = GregTechAPI.registerMetaTileEntity(432, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.hv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 2));
        LASER_ENGRAVER[3] = GregTechAPI.registerMetaTileEntity(433, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.ev"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 3));
        LASER_ENGRAVER[4] = GregTechAPI.registerMetaTileEntity(434, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.iv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 4));
        LASER_ENGRAVER[5] = GregTechAPI.registerMetaTileEntity(435, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.luv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 5));
        LASER_ENGRAVER[6] = GregTechAPI.registerMetaTileEntity(436, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.uv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 6));

        THERMAL_CENTRIFUGE[0] = GregTechAPI.registerMetaTileEntity(450, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.lv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 0));
        THERMAL_CENTRIFUGE[1] = GregTechAPI.registerMetaTileEntity(451, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.mv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 1));
        THERMAL_CENTRIFUGE[2] = GregTechAPI.registerMetaTileEntity(452, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.hv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 2));
        THERMAL_CENTRIFUGE[3] = GregTechAPI.registerMetaTileEntity(453, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.ev"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 3));
        THERMAL_CENTRIFUGE[4] = GregTechAPI.registerMetaTileEntity(454, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.iv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 4));
        THERMAL_CENTRIFUGE[5] = GregTechAPI.registerMetaTileEntity(455, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.luv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 5));
        THERMAL_CENTRIFUGE[6] = GregTechAPI.registerMetaTileEntity(456, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.uv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 6));

        WIREMILL[0] = GregTechAPI.registerMetaTileEntity(460, new SimpleMachineMetaTileEntity(gregtechId("wiremill.lv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 0));
        WIREMILL[1] = GregTechAPI.registerMetaTileEntity(461, new SimpleMachineMetaTileEntity(gregtechId("wiremill.mv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 1));
        WIREMILL[2] = GregTechAPI.registerMetaTileEntity(462, new SimpleMachineMetaTileEntity(gregtechId("wiremill.hv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 2));
        WIREMILL[3] = GregTechAPI.registerMetaTileEntity(463, new SimpleMachineMetaTileEntity(gregtechId("wiremill.ev"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 3));
        WIREMILL[4] = GregTechAPI.registerMetaTileEntity(464, new SimpleMachineMetaTileEntity(gregtechId("wiremill.iv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 4));
        WIREMILL[5] = GregTechAPI.registerMetaTileEntity(465, new SimpleMachineMetaTileEntity(gregtechId("wiremill.luv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 5));
        WIREMILL[6] = GregTechAPI.registerMetaTileEntity(466, new SimpleMachineMetaTileEntity(gregtechId("wiremill.uv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 6));

        DIESEL_GENERATOR[0] = GregTechAPI.registerMetaTileEntity(470, new SimpleGeneratorMetaTileEntity(gregtechId("diesel_generator.lv"), RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 0));
        DIESEL_GENERATOR[1] = GregTechAPI.registerMetaTileEntity(471, new SimpleGeneratorMetaTileEntity(gregtechId("diesel_generator.mv"), RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 1));
        DIESEL_GENERATOR[2] = GregTechAPI.registerMetaTileEntity(472, new SimpleGeneratorMetaTileEntity(gregtechId("diesel_generator.hv"), RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 2));

        STEAM_TURBINE[0] = GregTechAPI.registerMetaTileEntity(473, new SimpleGeneratorMetaTileEntity(gregtechId("steam_turbine.lv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 0));
        STEAM_TURBINE[1] = GregTechAPI.registerMetaTileEntity(474, new SimpleGeneratorMetaTileEntity(gregtechId("steam_turbine.mv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 1));
        STEAM_TURBINE[2] = GregTechAPI.registerMetaTileEntity(475, new SimpleGeneratorMetaTileEntity(gregtechId("steam_turbine.hv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 2));

        GAS_TURBINE[0] = GregTechAPI.registerMetaTileEntity(476, new SimpleGeneratorMetaTileEntity(gregtechId("gas_turbine.lv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 0));
        GAS_TURBINE[1] = GregTechAPI.registerMetaTileEntity(477, new SimpleGeneratorMetaTileEntity(gregtechId("gas_turbine.mv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 1));
        GAS_TURBINE[2] = GregTechAPI.registerMetaTileEntity(478, new SimpleGeneratorMetaTileEntity(gregtechId("gas_turbine.hv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 2));

        MAGIC_ENERGY_ABSORBER[0] = GregTechAPI.registerMetaTileEntity(480, new MetaTileEntityMagicEnergyAbsorber(gregtechId("magic_energy_absorber.hv"), 2));
        MAGIC_ENERGY_ABSORBER[1] = GregTechAPI.registerMetaTileEntity(481, new MetaTileEntityMagicEnergyAbsorber(gregtechId("magic_energy_absorber.ev"), 3));
        MAGIC_ENERGY_ABSORBER[2] = GregTechAPI.registerMetaTileEntity(482, new MetaTileEntityMagicEnergyAbsorber(gregtechId("magic_energy_absorber.iv"), 4));
        MAGIC_ENERGY_ABSORBER[3] = GregTechAPI.registerMetaTileEntity(483, new MetaTileEntityMagicEnergyAbsorber(gregtechId("magic_energy_absorber.luv"), 5));
        
        LIGHTNING_HARVESTER[0] = GregTechAPI.registerMetaTileEntity(485, new MetaTileEntityLightningHarvester(gregtechId("lightning_harvester.hv"), 2));
        LIGHTNING_HARVESTER[1] = GregTechAPI.registerMetaTileEntity(486, new MetaTileEntityLightningHarvester(gregtechId("lightning_harvester.ev"), 3));
        LIGHTNING_HARVESTER[2] = GregTechAPI.registerMetaTileEntity(487, new MetaTileEntityLightningHarvester(gregtechId("lightning_harvester.iv"), 4));
        LIGHTNING_HARVESTER[3] = GregTechAPI.registerMetaTileEntity(488, new MetaTileEntityLightningHarvester(gregtechId("lightning_harvester.luv"), 5));
        LIGHTNING_HARVESTER[4] = GregTechAPI.registerMetaTileEntity(489, new MetaTileEntityLightningHarvester(gregtechId("lightning_harvester.uv"), 6));

        RECYCLER[0] = GregTechAPI.registerMetaTileEntity(490, new SimpleMachineMetaTileEntity(gregtechId("recycler.hv"), RecipeMaps.RECYCLING_RECIPES, Textures.RECYCLER_OVERLAY, 2));
        RECYCLER[1] = GregTechAPI.registerMetaTileEntity(491, new SimpleMachineMetaTileEntity(gregtechId("recycler.ev"), RecipeMaps.RECYCLING_RECIPES, Textures.RECYCLER_OVERLAY, 3));
        RECYCLER[2] = GregTechAPI.registerMetaTileEntity(492, new SimpleMachineMetaTileEntity(gregtechId("recycler.iv"), RecipeMaps.RECYCLING_RECIPES, Textures.RECYCLER_OVERLAY, 4));
        RECYCLER[3] = GregTechAPI.registerMetaTileEntity(493, new SimpleMachineMetaTileEntity(gregtechId("recycler.luv"), RecipeMaps.RECYCLING_RECIPES, Textures.RECYCLER_OVERLAY, 5));
        RECYCLER[4] = GregTechAPI.registerMetaTileEntity(494, new SimpleMachineMetaTileEntity(gregtechId("recycler.uv"), RecipeMaps.RECYCLING_RECIPES, Textures.RECYCLER_OVERLAY, 6));

        MASS_FABRICATOR[0] = GregTechAPI.registerMetaTileEntity(495, new SimpleMachineMetaTileEntity(gregtechId("mass_fabricator.ev"), RecipeMaps.MASS_FABRICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 3));
        MASS_FABRICATOR[1] = GregTechAPI.registerMetaTileEntity(496, new SimpleMachineMetaTileEntity(gregtechId("mass_fabricator.iv"), RecipeMaps.MASS_FABRICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 4));
        MASS_FABRICATOR[2] = GregTechAPI.registerMetaTileEntity(497, new SimpleMachineMetaTileEntity(gregtechId("mass_fabricator.luv"), RecipeMaps.MASS_FABRICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 5));
        MASS_FABRICATOR[3] = GregTechAPI.registerMetaTileEntity(498, new SimpleMachineMetaTileEntity(gregtechId("mass_fabricator.uv"), RecipeMaps.MASS_FABRICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 6));

        REPLICATOR[0] = GregTechAPI.registerMetaTileEntity(501, new SimpleMachineMetaTileEntity(gregtechId("replicator.ev"), RecipeMaps.REPLICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 3));
        REPLICATOR[1] = GregTechAPI.registerMetaTileEntity(502, new SimpleMachineMetaTileEntity(gregtechId("replicator.iv"), RecipeMaps.REPLICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 4));
        REPLICATOR[2] = GregTechAPI.registerMetaTileEntity(503, new SimpleMachineMetaTileEntity(gregtechId("replicator.luv"), RecipeMaps.REPLICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 5));
        REPLICATOR[3] = GregTechAPI.registerMetaTileEntity(504, new SimpleMachineMetaTileEntity(gregtechId("replicator.uv"), RecipeMaps.REPLICATOR_RECIPES, Textures.MASS_FABRICATOR_OVERLAY, 6));

        
        for(int i = 0; i < HULL.length; i++) {
            MetaTileEntityHull metaTileEntity = new MetaTileEntityHull(gregtechId("hull." + GTValues.VN[i].toLowerCase()), i);
            GregTechAPI.registerMetaTileEntity(540 + i, metaTileEntity);
            HULL[i] = metaTileEntity;
        }

        PRIMITIVE_BLAST_FURNACE = GregTechAPI.registerMetaTileEntity(529, new MetaTileEntityPrimitiveBlastFurnace(gregtechId("primitive_blast_furnace.bronze")));
        ELECTRIC_BLAST_FURNACE = GregTechAPI.registerMetaTileEntity(511, new MetaTileEntityElectricBlastFurnace(gregtechId("electric_blast_furnace")));
        VACUUM_FREEZER = GregTechAPI.registerMetaTileEntity(512, new MetaTileEntityVacuumFreezer(gregtechId("vacuum_freezer")));
        IMPLOSION_COMPRESSOR = GregTechAPI.registerMetaTileEntity(513, new MetaTileEntityImplosionCompressor(gregtechId("implosion_compressor")));
        PYROLYSE_OVEN = GregTechAPI.registerMetaTileEntity(514, new MetaTileEntityPyrolyseOven(gregtechId("pyrolyse_oven")));
        DISTILLATION_TOWER = GregTechAPI.registerMetaTileEntity(515, new MetaTileEntityDistillationTower(gregtechId("distillation_tower")));
        MULTI_FURNACE = GregTechAPI.registerMetaTileEntity(516, new MetaTileEntityMultiFurnace(gregtechId("multi_furnace")));
        DIESEL_ENGINE = GregTechAPI.registerMetaTileEntity(517, new MetaTileEntityDieselEngine(gregtechId("diesel_engine")));
        CRACKER = GregTechAPI.registerMetaTileEntity(528, new MetaTileEntityCrackingUnit(gregtechId("cracker")));

        LARGE_STEAM_TURBINE = GregTechAPI.registerMetaTileEntity(518, new MetaTileEntityLargeTurbine(gregtechId("large_turbine.steam"), TurbineType.STEAM));
        LARGE_GAS_TURBINE = GregTechAPI.registerMetaTileEntity(519, new MetaTileEntityLargeTurbine(gregtechId("large_turbine.gas"), TurbineType.GAS));
        LARGE_PLASMA_TURBINE = GregTechAPI.registerMetaTileEntity(520, new MetaTileEntityLargeTurbine(gregtechId("large_turbine.plasma"), TurbineType.PLASMA));

        LARGE_BRONZE_BOILER = GregTechAPI.registerMetaTileEntity(521, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.bronze"), BoilerType.BRONZE));
        LARGE_STEEL_BOILER = GregTechAPI.registerMetaTileEntity(522, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.steel"), BoilerType.STEEL));
        LARGE_TITANIUM_BOILER = GregTechAPI.registerMetaTileEntity(523, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.titanium"), BoilerType.TITANIUM));
        LARGE_TUNGSTENSTEEL_BOILER = GregTechAPI.registerMetaTileEntity(524, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.tungstensteel"), BoilerType.TUNGSTENSTEEL));

        BEDROCK_DRILL = GregTechAPI.registerMetaTileEntity(525, new MetaTileEntityBedrockDrillingUnit(gregtechId("bedrock_drill")));
        
        FUSION_REACTOR[0] = GregTechAPI.registerMetaTileEntity(526, new MetaTileEntityFusionReactor(gregtechId("fusion_reactor.luv"), 5));
        FUSION_REACTOR[1] = GregTechAPI.registerMetaTileEntity(527, new MetaTileEntityFusionReactor(gregtechId("fusion_reactor.uv"), 6));
        
        COKE_OVEN = GregTechAPI.registerMetaTileEntity(536, new MetaTileEntityCokeOven(gregtechId("coke_oven")));
        COKE_OVEN_HATCH = GregTechAPI.registerMetaTileEntity(537, new MetaTileEntityCokeOvenHatch(gregtechId("coke_oven_hatch")));
        
        ASSEMBLY_LINE = GregTechAPI.registerMetaTileEntity(550, new MetaTileEntityAssemblyLine(gregtechId("assembly_line")));

        for(int i = 1; i < GTValues.V.length; i++) {
        	MetaTileEntityTransformer transformer = new MetaTileEntityTransformer(gregtechId("transformer." + GTValues.VN[i].toLowerCase()), i);
        	TRANSFORMER[i] = GregTechAPI.registerMetaTileEntity(600 + (i), transformer);
        }

        for(int i = 0; i < GTValues.V.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            ITEM_IMPORT_BUS[i] = new MetaTileEntityItemBus(gregtechId("item_bus.import." + voltageName), i, false);
            ITEM_EXPORT_BUS[i] = new MetaTileEntityItemBus(gregtechId("item_bus.export." + voltageName), i, true);
            FLUID_IMPORT_HATCH[i] = new MetaTileEntityFluidHatch(gregtechId("fluid_hatch.import." + voltageName), i, false);
            FLUID_EXPORT_HATCH[i] = new MetaTileEntityFluidHatch(gregtechId("fluid_hatch.export." + voltageName), i, true);
            ENERGY_INPUT_HATCH[i] = new MetaTileEntityEnergyHatch(gregtechId("energy_hatch.input." + voltageName), i, false);
            ENERGY_OUTPUT_HATCH[i] = new MetaTileEntityEnergyHatch(gregtechId("energy_hatch.output." + voltageName), i, true);

            GregTechAPI.registerMetaTileEntity(700 + 10 * i, ITEM_IMPORT_BUS[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 1, ITEM_EXPORT_BUS[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 2, FLUID_IMPORT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 3, FLUID_EXPORT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 4, ENERGY_INPUT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 5, ENERGY_OUTPUT_HATCH[i]);
            
            int[] batteryBufferSlots = new int[] {1, 4, 9, 16};
            BATTERY_BUFFER[i] = new MetaTileEntityBatteryBuffer[batteryBufferSlots.length];
            for(int slot = 0; slot < batteryBufferSlots.length; slot++) {
                String transformerId = "battery_buffer." + GTValues.VN[i].toLowerCase() + "." + batteryBufferSlots[slot];
                MetaTileEntityBatteryBuffer batteryBuffer = new MetaTileEntityBatteryBuffer(gregtechId(transformerId), i, batteryBufferSlots[slot]);
                BATTERY_BUFFER[i][slot] = GregTechAPI.registerMetaTileEntity(620 + batteryBufferSlots.length * i + slot, batteryBuffer);
            }
            String chargerId = "charger." + GTValues.VN[i].toLowerCase();
            MetaTileEntityCharger charger = new MetaTileEntityCharger(gregtechId(chargerId), i, 4);
            CHARGER[i] = GregTechAPI.registerMetaTileEntity(680 + i, charger);
        }
        
        WOODEN_TANK = GregTechAPI.registerMetaTileEntity(811, new MetaTileEntityTank(gregtechId("wooden_tank"), Materials.Wood, 4000));
        BRONZE_TANK = GregTechAPI.registerMetaTileEntity(812, new MetaTileEntityTank(gregtechId("bronze_tank"), Materials.Bronze,8000));
        STEEL_TANK = GregTechAPI.registerMetaTileEntity(813, new MetaTileEntityTank(gregtechId("steel_tank"), Materials.Steel, 16000));
        STAINLESS_STEEL_TANK = GregTechAPI.registerMetaTileEntity(814, new MetaTileEntityTank(gregtechId("stainless_steel_tank"), Materials.StainlessSteel, 32000));
        TITANIUM_TANK = GregTechAPI.registerMetaTileEntity(815, new MetaTileEntityTank(gregtechId("titanium_tank"), Materials.Titanium, 48000));
        TUNGSTENSTEEL_TANK = GregTechAPI.registerMetaTileEntity(816, new MetaTileEntityTank(gregtechId("tungstensteel_tank"), Materials.TungstenSteel, 64000));
        IRIDIUM_TANK = GregTechAPI.registerMetaTileEntity(817, new MetaTileEntityTank(gregtechId("iridium_tank"), Materials.Iridium, 80000));
        ADAMANTINE_TANK = GregTechAPI.registerMetaTileEntity(818, new MetaTileEntityTank(gregtechId("adamantine_tank"), Materials.Adamantine, 128000));

        ROTOR_HOLDER[0] = GregTechAPI.registerMetaTileEntity(819, new MetaTileEntityRotorHolder(gregtechId("rotor_holder.hv"), GTValues.HV, 1.0f));
        ROTOR_HOLDER[1] = GregTechAPI.registerMetaTileEntity(820, new MetaTileEntityRotorHolder(gregtechId("rotor_holder.iv"), GTValues.IV, 1.20f));
        ROTOR_HOLDER[2] = GregTechAPI.registerMetaTileEntity(821, new MetaTileEntityRotorHolder(gregtechId("rotor_holder.uv"), GTValues.UV, 1.40f));

        for(int i = 0; i < 7; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            PUMP[i] = new MetaTileEntityPump(gregtechId("pump." + voltageName), i);
            AIR_COLLECTOR[i] = new MetaTileEntityAirCollector(gregtechId("air_collector." + voltageName), i);
            GregTechAPI.registerMetaTileEntity(822 + (i), PUMP[i]);
            GregTechAPI.registerMetaTileEntity(829 + (i), AIR_COLLECTOR[i]);
        }

        TESLA_COIL = new MetaTileEntityTeslaCoil(gregtechId("tesla_coil"));
        GregTechAPI.registerMetaTileEntity(850, TESLA_COIL);

        for(int i = 3; i < GTValues.V.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            QUANTUM_CHEST[i - 3] = new MetaTileEntityQuantumChest(gregtechId("quantum_chest." + voltageName), i, 64 * 64000 * (i));
            QUANTUM_TANK[i - 3] = new MetaTileEntityQuantumTank(gregtechId("quantum_tank." + voltageName), i, 1000 * 64000 * (i));
            GregTechAPI.registerMetaTileEntity(851 + (i - 3), QUANTUM_CHEST[i - 3]);
            GregTechAPI.registerMetaTileEntity(870 + (i - 3), QUANTUM_TANK[i - 3]);
        }
    }

    private static ResourceLocation gregtechId(String name) {
        return new ResourceLocation(GTValues.MODID, name);
    }
}