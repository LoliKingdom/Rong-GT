package com.rong.rt.common;

import static com.rong.rt.common.blocks.RTBlocks.COMPRESSED;
import static com.rong.rt.common.blocks.RTBlocks.FLOODED_SURFACE_ROCKS;
import static com.rong.rt.common.blocks.RTBlocks.FLUID_BLOCKS;
import static com.rong.rt.common.blocks.RTBlocks.FRAMES;
import static com.rong.rt.common.blocks.RTBlocks.ORES;
import static com.rong.rt.common.blocks.RTBlocks.SURFACE_ROCKS;

import java.util.function.Function;

import com.rong.rt.RTLog;
import com.rong.rt.Values;
import com.rong.rt.api.metaitems.MetaItem;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.common.blocks.CompressedItemBlock;
import com.rong.rt.common.blocks.FrameItemBlock;
import com.rong.rt.common.blocks.OreItemBlock;
import com.rong.rt.common.blocks.RTBlocks;
import com.rong.rt.common.items.MetaItems;
import com.rong.rt.common.loaders.MaterialInfoLoader;
import com.rong.rt.common.loaders.OreDictionaryLoader;
import com.rong.rt.common.loaders.RecipesLoader;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Values.MOD_ID)
public class CommonProxy {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		RTLog.logger.info("Registering Blocks...");
		IForgeRegistry<Block> registry = event.getRegistry();

		COMPRESSED.values().stream().distinct().forEach(registry::register);
		SURFACE_ROCKS.values().stream().distinct().forEach(registry::register);
		FLOODED_SURFACE_ROCKS.values().stream().distinct().forEach(registry::register);
		FRAMES.values().stream().distinct().forEach(registry::register);
		ORES.forEach(registry::register);
		FLUID_BLOCKS.forEach(registry::register);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		RTLog.logger.info("Registering Items...");
		IForgeRegistry<Item> registry = event.getRegistry();

		for(MetaItem<?> item : MetaItems.ITEMS) {
			registry.register(item);
			item.registerSubItems();
		}
		
		COMPRESSED.values().stream().distinct().map(block -> createItemBlock(block, CompressedItemBlock::new))
				.forEach(registry::register);
		FRAMES.values().stream().distinct().map(block -> createItemBlock(block, FrameItemBlock::new))
				.forEach(registry::register);
		ORES.stream().map(block -> createItemBlock(block, OreItemBlock::new)).forEach(registry::register);
	}

	@SubscribeEvent
	public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(Values.MOD_ID)) {
			ConfigManager.sync(Values.MOD_ID, Type.INSTANCE);
		}
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		RTLog.logger.info("Registering ore dictionary...");

		MetaItems.registerOreDict();
		RTBlocks.registerOreDict();
		OreDictionaryLoader.init();
		MaterialInfoLoader.init();

		if(Loader.isModLoaded("botania")) {
			// BotaniaODFixer.init();
		}

		RTLog.logger.info("Registering recipes...");

		// MetaItems.registerRecipes();
		RecipesLoader.init();
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void runEarlyMaterialHandlers(RegistryEvent.Register<IRecipe> event) {
		RTLog.logger.info("Running early material handlers...");
		// Checks
		EnumOrePrefix.runMaterialHandlers();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
		RTLog.logger.info("Running late material handlers...");
		EnumOrePrefix.runMaterialHandlers();
		// DecompositionRecipeHandler.runRecipeGeneration();
		if(Loader.isModLoaded("mekanism")) {
			// MekanismProcessingHandler.removeRecipes();
			// MekanismProcessingHandler.initRecipes();
		}
		if(Loader.isModLoaded("thaumcraft")) {
			// ThaumcraftProcessingHandler.init();
		}
	}
	
	@SuppressWarnings("deprecation")
    private static <T extends Block> ItemBlock createMultiTexItemBlock(T block, Function<IBlockState, String> nameProducer) {
        ItemBlock itemBlock = new ItemMultiTexture(block, block, stack -> {
            IBlockState blockState = block.getStateFromMeta(stack.getMetadata());
            return nameProducer.apply(blockState);
        });
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
    
    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }

	public void onPreLoad() {
		// TODO Auto-generated method stub
	}

	public void onLoad() {
		// TODO Auto-generated method stub
	}

	public void onPostLoad() {
		// TODO Auto-generated method stub
	}

}
