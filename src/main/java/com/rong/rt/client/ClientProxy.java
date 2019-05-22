package com.rong.rt.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rong.rt.api.render.ToolRenderHandler;
import com.rong.rt.api.unification.OreDictUnifier;
import com.rong.rt.api.unification.materials.types.Material;
import com.rong.rt.api.unification.stack.UnificationEntry;
import com.rong.rt.common.CommonProxy;
import com.rong.rt.common.blocks.BlockCompressed;
import com.rong.rt.common.blocks.BlockFrame;
import com.rong.rt.common.blocks.BlockOre;
import com.rong.rt.common.blocks.FrameItemBlock;
import com.rong.rt.common.blocks.MetaFluids;
import com.rong.rt.common.blocks.RTBlocks;
import com.rong.rt.common.blocks.renders.StoneRenderer;
import com.rong.rt.common.blocks.surfacerock.BlockSurfaceRock;
import com.rong.rt.common.blocks.surfacerock.BlockSurfaceRockFlooded;
import com.rong.rt.common.items.MetaItems;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ResourceUtils;
import ic2.core.platform.textures.Ic2Icons.SpriteReloadEvent;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onIconLoad(SpriteReloadEvent event) {
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		RTBlocks.registerStateMappers();
		RTBlocks.registerItemModels();
		MetaItems.registerModels();
	}

	@SubscribeEvent
	public static void addMaterialFormulaHandler(ItemTooltipEvent event) {
		ItemStack itemStack = event.getItemStack();
		if(!(itemStack.getItem() instanceof ItemBlock)) {
			UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(itemStack);
			if(unificationEntry != null && unificationEntry.material != null) {
				String formula = unificationEntry.material.chemicalFormula;
				if(formula != null && !formula.isEmpty() && !formula.equals("?")) {
					event.getToolTip().add(1,
							ChatFormatting.GRAY.toString() + unificationEntry.material.chemicalFormula);
				}
			}
		}
	}

	public static final IBlockColor COMPRESSED_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos,
			int tintIndex) -> state.getValue(((BlockCompressed) state.getBlock()).variantProperty).materialRGB;

	public static final IItemColor COMPRESSED_ITEM_COLOR = (stack, tintIndex) -> {
		BlockCompressed block = (BlockCompressed) ((ItemBlock) stack.getItem()).getBlock();
		IBlockState state = block.getStateFromMeta(stack.getItemDamage());
		return state.getValue(block.variantProperty).materialRGB;
	};

	public static final IBlockColor FRAME_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos,
			int tintIndex) -> {
		Material material = ((BlockFrame) state.getBlock()).frameMaterial;
		EnumDyeColor dyeColor = state.getValue(BlockColored.COLOR);
		return dyeColor == EnumDyeColor.WHITE ? material.materialRGB : dyeColor.colorValue;
	};

	public static final IItemColor FRAME_ITEM_COLOR = (stack, tintIndex) -> {
		IBlockState frameState = ((FrameItemBlock) stack.getItem()).getBlockState(stack);
		BlockFrame block = (BlockFrame) frameState.getBlock();
		EnumDyeColor dyeColor = frameState.getValue(BlockColored.COLOR);
		return dyeColor == EnumDyeColor.WHITE ? block.frameMaterial.materialRGB : dyeColor.colorValue;
	};

	public static final IBlockColor ORE_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos,
			int tintIndex) -> tintIndex == 1 ? ((BlockOre) state.getBlock()).material.materialRGB : 0xFFFFFF;

	public static final IItemColor ORE_ITEM_COLOR = (stack, tintIndex) -> tintIndex == 1
			? ((BlockOre) ((ItemBlock) stack.getItem()).getBlock()).material.materialRGB
			: 0xFFFFFF;

	public static final IBlockColor FOAM_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos,
			int tintIndex) -> state.getValue(BlockColored.COLOR).colorValue;

	public static final IBlockColor SURFACE_ROCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos,
			int tintIndex) -> {
		if(tintIndex == 1) {
			if(state.getBlock() instanceof BlockSurfaceRock) {
				BlockSurfaceRock surfaceRock = (BlockSurfaceRock) state.getBlock();
				return state.getValue(surfaceRock.materialProperty).materialRGB;
			}
			else if(state.getBlock() instanceof BlockSurfaceRockFlooded) {
				BlockSurfaceRockFlooded surfaceRock = (BlockSurfaceRockFlooded) state.getBlock();
				return state.getValue(surfaceRock.materialProperty).materialRGB;
			}
			else return 0xFFFFFF;
		}
		else {
			// flooded surface rock water variant
			return BiomeColorHelper.getWaterColorAtPos(worldIn, pos);
		}
	};

	public void onPreLoad() {
		super.onPreLoad();
		StoneRenderer.preInit();
		// MetaEntities.initRenderers();
		TextureUtils.addIconRegister(MetaFluids::registerSprites);
		MinecraftForge.EVENT_BUS.register(ToolRenderHandler.INSTANCE);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		registerColors();
	}

	@Override
	public void onPostLoad() {
		super.onPostLoad();
		ResourceUtils.registerReloadListener(ToolRenderHandler.INSTANCE);
	}

	public void registerColors() {
		RTBlocks.registerColors();
		MetaItems.registerColors();
	}

}