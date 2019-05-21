package com.rong.rt.common.blocks;

import static com.rong.rt.client.ClientProxy.COMPRESSED_BLOCK_COLOR;
import static com.rong.rt.client.ClientProxy.COMPRESSED_ITEM_COLOR;
import static com.rong.rt.client.ClientProxy.FRAME_BLOCK_COLOR;
import static com.rong.rt.client.ClientProxy.FRAME_ITEM_COLOR;
import static com.rong.rt.client.ClientProxy.ORE_BLOCK_COLOR;
import static com.rong.rt.client.ClientProxy.ORE_ITEM_COLOR;
import static com.rong.rt.client.ClientProxy.SURFACE_ROCK_COLOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.rong.rt.Values;
import com.rong.rt.api.RongTechAPI;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.OreDictUnifier;
import com.rong.rt.api.unification.materials.Materials;
import com.rong.rt.api.unification.materials.types.DustMaterial;
import com.rong.rt.api.unification.materials.types.Material;
import com.rong.rt.api.unification.materials.types.SolidMaterial;
import com.rong.rt.api.unification.stonetypes.StoneType;
import com.rong.rt.common.blocks.modelfactories.BakedModelHandler;
import com.rong.rt.common.blocks.surfacerock.BlockSurfaceRock;
import com.rong.rt.common.blocks.surfacerock.BlockSurfaceRockFlooded;
import com.rong.rt.common.blocks.BlockMachine;
import com.rong.rt.common.blocks.tiles.electric.TileEntityAutoclave;
import com.rong.rt.common.blocks.tiles.electric.TileEntityBender;

import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RTBlocks {

	public static Collection<BlockFluidBase> FLUID_BLOCKS = new HashSet<>();
	public static Map<DustMaterial, BlockCompressed> COMPRESSED = new HashMap<>();
	public static Map<DustMaterial, BlockSurfaceRock> SURFACE_ROCKS = new HashMap<>();
	public static Map<DustMaterial, BlockSurfaceRockFlooded> FLOODED_SURFACE_ROCKS = new HashMap<>();
	public static Map<SolidMaterial, BlockFrame> FRAMES = new HashMap<>();
	public static Collection<BlockOre> ORES = new HashSet<>();

	static final List<Block> toRegister = new ArrayList<>();

	public static final BlockMachine
		blockBender = registerBlock(new BlockMachine("bender")),
		blockAutoclave = registerBlock(new BlockMachine("autoclave"));

	public static void init() {
		registerBlocks();
		StoneType.init();
		registerTileEntity();
		createGeneratedBlock(material -> material instanceof DustMaterial && !EnumOrePrefix.block.isIgnored(material),
				RTBlocks::createCompressedBlock);
		createGeneratedBlock(material -> material instanceof DustMaterial, RTBlocks::createSurfaceRockBlock);
		for(Material material : Material.MATERIAL_REGISTRY) {
			if(material instanceof DustMaterial && material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				createOreBlock((DustMaterial) material);
			}
			if(material instanceof SolidMaterial && material.hasFlag(SolidMaterial.MatFlags.GENERATE_FRAME)) {
				BlockFrame blockFrame = new BlockFrame((SolidMaterial) material);
				blockFrame.setRegistryName("frame_" + material.toString());
				FRAMES.put((SolidMaterial) material, blockFrame);
			}
		}
	}

	public static void registerBlocks() {
		for(Block block : toRegister) {
			IC2.getInstance().createBlock(block, MachineItemBlock.class);
		}
	}

	private static <T extends Block> T registerBlock(T block) {
		toRegister.add(block);
		return block;
	}

	public static void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityAutoclave.class,
				new ResourceLocation(Values.MOD_ID + "tile_autoclave"));
		GameRegistry.registerTileEntity(TileEntityBender.class, new ResourceLocation(Values.MOD_ID + "tile_bender"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerStateMappers() {
		BakedModelHandler modelHandler = new BakedModelHandler();
		MinecraftForge.EVENT_BUS.register(modelHandler);
		FLUID_BLOCKS.forEach(modelHandler::addFluidBlock);
		SURFACE_ROCKS.values().stream().distinct().forEach(block -> modelHandler.addBuiltInBlock(block, "stone"));
		FLOODED_SURFACE_ROCKS.values().stream().distinct()
				.forEach(block -> modelHandler.addBuiltInBlock(block, "stone"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		COMPRESSED.values().stream().distinct().forEach(RTBlocks::registerItemModel);
		FRAMES.values().forEach(it -> registerItemModelWithFilteredProperties(it));
		ORES.stream().distinct().forEach(RTBlocks::registerItemModel);
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors() {
		/*
		 * Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
		 * FOAM_BLOCK_COLOR, FOAM, REINFORCED_FOAM, PETRIFIED_FOAM,
		 * REINFORCED_PETRIFIED_FOAM);
		 */

		COMPRESSED.values().stream().distinct().forEach(block -> {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(COMPRESSED_BLOCK_COLOR, block);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(COMPRESSED_ITEM_COLOR, block);
		});

		FRAMES.values().forEach(block -> {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(FRAME_BLOCK_COLOR, block);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(FRAME_ITEM_COLOR, block);
		});

		ORES.stream().distinct().forEach(block -> {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(ORE_BLOCK_COLOR, block);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ORE_ITEM_COLOR, block);
		});

		SURFACE_ROCKS.values().stream().distinct().forEach(block -> Minecraft.getMinecraft().getBlockColors()
				.registerBlockColorHandler(SURFACE_ROCK_COLOR, block));
		FLOODED_SURFACE_ROCKS.values().stream().distinct().forEach(block -> Minecraft.getMinecraft().getBlockColors()
				.registerBlockColorHandler(SURFACE_ROCK_COLOR, block));
	}

	@SideOnly(Side.CLIENT)
	private static void registerItemModel(Block block) {
		for(IBlockState state : block.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), block.getMetaFromState(state),
					new ModelResourceLocation(block.getRegistryName(), statePropertiesToString(state.getProperties())));
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerItemModelWithFilteredProperties(Block block, IProperty<?>... filteredProperties) {
		for(IBlockState state : block.getBlockState().getValidStates()) {
			HashMap<IProperty<?>, Comparable<?>> stringProperties = new HashMap<>();
			for(IProperty<?> property : filteredProperties) {
				stringProperties.put(property, state.getValue(property));
			}
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), block.getMetaFromState(state),
					new ModelResourceLocation(block.getRegistryName(), statePropertiesToString(stringProperties)));
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerItemModelWithOverride(Block block, Map<IProperty<?>, Comparable<?>> stateOverrides) {
		for(IBlockState state : block.getBlockState().getValidStates()) {
			HashMap<IProperty<?>, Comparable<?>> stringProperties = new HashMap<>(state.getProperties());
			stringProperties.putAll(stateOverrides);
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), block.getMetaFromState(state),
					new ModelResourceLocation(block.getRegistryName(), statePropertiesToString(stringProperties)));
		}
	}

	public static void registerOreDict() {
		// OreDictUnifier.registerOre(new ItemStack(LOG, 1, GTValues.W), OrePrefix.log,
		// Materials.Wood);
		// OreDictUnifier.registerOre(new ItemStack(LEAVES, 1, GTValues.W),
		// "treeLeaves");
		// OreDictUnifier.registerOre(new ItemStack(SAPLING, 1, GTValues.W),
		// "treeSapling");
		for(Entry<DustMaterial, BlockCompressed> entry : COMPRESSED.entrySet()) {
			DustMaterial material = entry.getKey();
			BlockCompressed block = entry.getValue();
			ItemStack itemStack = block.getItem(material);
			OreDictUnifier.registerOre(itemStack, EnumOrePrefix.block, material);
		}

		for(Entry<SolidMaterial, BlockFrame> entry : FRAMES.entrySet()) {
			SolidMaterial material = entry.getKey();
			BlockFrame block = entry.getValue();
			for(int i = 0; i < 16; i++) {
				ItemStack itemStack = new ItemStack(block, 1, i);
				OreDictUnifier.registerOre(itemStack, EnumOrePrefix.frameGt, material);
			}
		}
		for(BlockOre blockOre : ORES) {
			DustMaterial material = blockOre.material;
			for(StoneType stoneType : blockOre.STONE_TYPE.getAllowedValues()) {
				if(stoneType == null) continue;
				ItemStack normalStack = blockOre
						.getItem(blockOre.getDefaultState().withProperty(blockOre.STONE_TYPE, stoneType));
				OreDictUnifier.registerOre(normalStack, EnumOrePrefix.ore, material);
				OreDictUnifier.registerOre(normalStack, stoneType.processingPrefix, material);
			}
		}
	}

	private static int createGeneratedBlock(Predicate<Material> materialPredicate,
			BiConsumer<Material[], Integer> blockGenerator) {
		Material[] materialBuffer = new Material[16];
		Arrays.fill(materialBuffer, Materials._NULL);
		int currentGenerationIndex = 0;
		for(Material material : Material.MATERIAL_REGISTRY) {
			if(materialPredicate.test(material)) {
				if(currentGenerationIndex > 0 && currentGenerationIndex % 16 == 0) {
					blockGenerator.accept(materialBuffer, currentGenerationIndex / 16 - 1);
					Arrays.fill(materialBuffer, Materials._NULL);
				}
				materialBuffer[currentGenerationIndex % 16] = material;
				currentGenerationIndex++;
			}
		}
		if(materialBuffer[0] != Materials._NULL) {
			blockGenerator.accept(materialBuffer, currentGenerationIndex / 16);
		}
		return (currentGenerationIndex / 16) + 1;
	}

	private static void createSurfaceRockBlock(Material[] materials, int index) {
		BlockSurfaceRock block = new BlockSurfaceRock(materials);
		BlockSurfaceRockFlooded floodedBlock = new BlockSurfaceRockFlooded(materials);
		block.setRegistryName("surface_rock_" + index);
		floodedBlock.setRegistryName("surface_rock_flooded_" + index);
		for(Material material : materials) {
			if(material instanceof DustMaterial) {
				SURFACE_ROCKS.put((DustMaterial) material, block);
				FLOODED_SURFACE_ROCKS.put((DustMaterial) material, floodedBlock);
			}
		}
	}

	private static void createCompressedBlock(Material[] materials, int index) {
		BlockCompressed block = new BlockCompressed(materials);
		block.setRegistryName("compressed_" + index);
		for(Material material : materials) {
			if(material instanceof DustMaterial) {
				COMPRESSED.put((DustMaterial) material, block);
			}
		}
	}

	private static void createOreBlock(DustMaterial material) {
		StoneType[] stoneTypeBuffer = new StoneType[16];
		int generationIndex = 0;
		for(StoneType stoneType : StoneType.STONE_TYPE_REGISTRY) {
			int id = StoneType.STONE_TYPE_REGISTRY.getIDForObject(stoneType), index = id / 16;
			if(index > generationIndex) {
				createOreBlock(material, copyNotNull(stoneTypeBuffer), generationIndex);
				Arrays.fill(stoneTypeBuffer, null);
			}
			stoneTypeBuffer[id % 16] = stoneType;
			generationIndex = index;
		}
		createOreBlock(material, copyNotNull(stoneTypeBuffer), generationIndex);
	}

	private static <T> T[] copyNotNull(T[] src) {
		int nullIndex = ArrayUtils.indexOf(src, null);
		return Arrays.copyOfRange(src, 0, nullIndex == -1 ? src.length : nullIndex);
	}

	private static void createOreBlock(DustMaterial material, StoneType[] stoneTypes, int index) {
		BlockOre block = new BlockOre(material, stoneTypes);
		block.setRegistryName("ore_" + material + "_" + index);
		for(StoneType stoneType : stoneTypes) {
			RongTechAPI.oreBlockTable.computeIfAbsent(material, m -> new HashMap<>()).put(stoneType, block);
		}
		ORES.add(block);
	}

	private static String statePropertiesToString(Map<IProperty<?>, Comparable<?>> properties) {
		StringBuilder stringbuilder = new StringBuilder();

		List<Entry<IProperty<?>, Comparable<?>>> entries = properties.entrySet().stream()
				.sorted(Comparator.comparing(c -> c.getKey().getName())).collect(Collectors.toList());

		for(Map.Entry<IProperty<?>, Comparable<?>> entry : entries) {
			if(stringbuilder.length() != 0) {
				stringbuilder.append(",");
			}

			IProperty<?> property = entry.getKey();
			stringbuilder.append(property.getName());
			stringbuilder.append("=");
			stringbuilder.append(getPropertyName(property, entry.getValue()));
		}

		if(stringbuilder.length() == 0) {
			stringbuilder.append("normal");
		}

		return stringbuilder.toString();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
		return property.getName((T) value);
	}
	
}
