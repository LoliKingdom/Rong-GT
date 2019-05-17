package com.rong.gt;

import com.rong.gt.api.unification.OreDictUnifier;
import com.rong.gt.api.unification.materials.AnnotatedMaterialHandlerLoader;
import com.rong.gt.api.unification.materials.Materials;
import com.rong.gt.api.unification.materials.types.Material;
import com.rong.gt.common.CommonProxy;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Values.MOD_ID, name = Values.MOD_NAME, acceptedMinecraftVersions = "[1.12]",
dependencies = "after:forestry;after:nuclearcraft;after:tconstruct;after:forgemultipartcbe;after:jei@[4.8.6,);after:crafttweaker;")
public class RongTech {
	
	static {
        FluidRegistry.enableUniversalBucket();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            //ResourcePackHook.init();
            //BlockOreFactory.init();
            //BlockCompressedFactory.init();
            //BlockFrameFactory.init();
        }
    }

    @Mod.Instance(Values.MOD_ID)
    public static RongTech instance;

    @SidedProxy(modId = Values.MOD_ID, clientSide = "com.rong.gt.client.ClientProxy", serverSide = "com.rong.gt.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        RTLog.init(event.getModLog());

        //NetworkHandler.init();
        //MetaTileEntityUIFactory.INSTANCE.init();
        //PlayerInventoryUIFactory.INSTANCE.init();
        //SimpleCapabilityManager.init();
        OreDictUnifier.init();
        
        Materials.register();      
        AnnotatedMaterialHandlerLoader.discoverAndLoadAnnotatedMaterialHandlers(event.getAsmData());
        Material.runMaterialHandlers();
        
        if(Loader.isModLoaded("crafttweaker")) {
            RTLog.logger.info("Running early CraftTweaker initialization scripts...");
            runEarlyCraftTweakerScripts();
        }

        //freeze material registry before processing items, blocks and fluids
        Material.freezeRegistry();

        //MetaBlocks.init();
        //MetaItems.init();
        //MetaFluids.init();
        //MetaTileEntities.init();
        //MetaEntities.init();   
        
        //if(Values.isModLoaded("mekanism")) {
        	//MekanismProcessingHandler.preInitGas();
        //}
        
       //if(Values.isModLoaded("tconstruct")) {
        	//TinkersIntegration.preInit();
        	//MinecraftForge.EVENT_BUS.register(new TinkersEvents());
        //}
        
        proxy.onPreLoad();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onLoad();
        /*
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
        }*/
        
        //if(!ConfigHolder.disableRubberTreeGeneration) {
            //GameRegistry.registerWorldGenerator(new RubberTreeGenerator(), 10000);
        //}

        //if(GTValues.isModLoaded("theoneprobe")) {
            //GTLog.logger.info("TheOneProbe found. Enabling integration...");
            //TheOneProbeCompatibility.registerCompatibility();
        //}

        //WorldGenRegistry.INSTANCE.initializeRegistry();

        //DungeonLootLoader.init();
    }

    @Method(modid = "crafttweaker")
    private void runEarlyCraftTweakerScripts() {
        CraftTweakerAPI.tweaker.loadScript(false, "rongtech");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onPostLoad();
        //if(GTValues.isModLoaded("nuclearcraft")) {
        	//GTRadSources.init();
        //}
        //OreDictionaryLoader.postInit();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        //event.registerServerCommand(new GregTechCommand());
    }

}
