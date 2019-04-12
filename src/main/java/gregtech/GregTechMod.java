package gregtech;

import codechicken.lib.CodeChickenLib;
import crafttweaker.CraftTweakerAPI;
import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import gregtech.api.cover.CoverBehaviorUIFactory;
import gregtech.api.items.gui.PlayerInventoryUIFactory;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.net.NetworkHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.api.util.AnnotatedMaterialHandlerLoader;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.CommonProxy;
import gregtech.common.ConfigHolder;
import gregtech.common.MetaEntities;
import gregtech.common.MetaFluids;
import gregtech.common.RubberTreeGenerator;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.modelfactories.BlockCompressedFactory;
import gregtech.common.blocks.modelfactories.BlockFrameFactory;
import gregtech.common.blocks.modelfactories.BlockOreFactory;
import gregtech.common.command.GregTechCommand;
import gregtech.common.covers.CoverBehaviors;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.multipart.GTMultipartFactory;
import gregtech.integration.mekanism.MekanismProcessingHandler;
import gregtech.integration.nuclearcraft.GTRadSources;
import gregtech.integration.theoneprobe.TheOneProbeCompatibility;
import gregtech.integration.tinkers.TinkersEvents;
import gregtech.integration.tinkers.TinkersIntegration;
import gregtech.loaders.OreDictionaryLoader;
import gregtech.loaders.dungeon.DungeonLootLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GTValues.MODID,
     name = "GregTech",
     acceptedMinecraftVersions = "[1.12,1.13)",
     dependencies = "required:forge@[14.23.5.2806,);" + CodeChickenLib.MOD_VERSION_DEP + "after:forestry;after:nuclearcraft;after:tconstruct;after:forgemultipartcbe;after:jei@[4.8.6,);after:crafttweaker;")
public class GregTechMod {

    static {
        FluidRegistry.enableUniversalBucket();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            ResourcePackHook.init();
            BlockOreFactory.init();
            BlockCompressedFactory.init();
            BlockFrameFactory.init();
        }
    }

    @Mod.Instance(GTValues.MODID)
    public static GregTechMod instance;

    @SidedProxy(modId = GTValues.MODID, clientSide = "gregtech.common.ClientProxy", serverSide = "gregtech.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        GTLog.init(event.getModLog());

        NetworkHandler.init();
        MetaTileEntityUIFactory.INSTANCE.init();
        PlayerInventoryUIFactory.INSTANCE.init();
        CoverBehaviorUIFactory.INSTANCE.init();
        SimpleCapabilityManager.init();
        OreDictUnifier.init();
        
        Materials.register();      
        AnnotatedMaterialHandlerLoader.discoverAndLoadAnnotatedMaterialHandlers(event.getAsmData());
        Material.runMaterialHandlers();
        
        if(Loader.isModLoaded("crafttweaker")) {
            GTLog.logger.info("Running early CraftTweaker initialization scripts...");
            runEarlyCraftTweakerScripts();
        }

        //freeze material registry before processing items, blocks and fluids
        Material.freezeRegistry();

        MetaBlocks.init();
        MetaItems.init();
        MetaFluids.init();
        MetaTileEntities.init();
        MetaEntities.init();   
        
        if(GTValues.isModLoaded("mekanism")) {
        	MekanismProcessingHandler.preInitGas();
        }
        
        if(GTValues.isModLoaded("tconstruct")) {
        	TinkersIntegration.preInit();
        	MinecraftForge.EVENT_BUS.register(new TinkersEvents());
        }
        
        proxy.onPreLoad();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onLoad();

        if (RecipeMap.isFoundInvalidRecipe()) {
            GTLog.logger.fatal("Seems like invalid recipe was found.");
            //crash if config setting is set to false, or we are in deobfuscated environment
            if(!ConfigHolder.ignoreErrorOrInvalidRecipes || !FMLForgePlugin.RUNTIME_DEOBF) {
                GTLog.logger.fatal("Loading cannot continue. Either fix or report invalid recipes, or enable ignoreErrorOrInvalidRecipes in the config as a temporary solution");
                throw new LoaderException("Found at least one invalid recipe. Please read the log above for more details.");
            } else {
                GTLog.logger.fatal("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                GTLog.logger.fatal("Ignoring invalid recipes and continuing loading!");
                GTLog.logger.fatal("Some things may lack recipes or have invalid ones, proceed at your own risk");
                GTLog.logger.fatal("Report to GregTech: Chill Edition's Github/Curse page to get help and fix the problem");
                GTLog.logger.fatal("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        
        if(!ConfigHolder.disableRubberTreeGeneration) {
            GameRegistry.registerWorldGenerator(new RubberTreeGenerator(), 10000);
        }

        if(GTValues.isModLoaded("forgemultipartcbe")) {
            GTLog.logger.info("ForgeMultiPart found. Enabling integration...");
            registerForgeMultipartCompat();
        }

        if(GTValues.isModLoaded("theoneprobe")) {
            GTLog.logger.info("TheOneProbe found. Enabling integration...");
            TheOneProbeCompatibility.registerCompatibility();
        }

        WorldGenRegistry.INSTANCE.initializeRegistry();
        
        CoverBehaviors.init();
        DungeonLootLoader.init();
    }

    @Method(modid = "forgemultipartcbe")
    private void registerForgeMultipartCompat() {
        GTMultipartFactory.INSTANCE.registerFactory();
    }

    @Method(modid = "crafttweaker")
    private void runEarlyCraftTweakerScripts() {
        CraftTweakerAPI.tweaker.loadScript(false, "gregtech");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onPostLoad();
        if(GTValues.isModLoaded("nuclearcraft")) {
        	GTRadSources.init();
        }
        //OreDictionaryLoader.postInit();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new GregTechCommand());
    }
}