package gregtech.integration.thaumcraft;

import org.apache.commons.lang3.StringUtils;

import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.ItemCell;
import gregtech.common.items.MetaItems;
import gregtech.loaders.recipes.processing.OreRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional.Method;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.common.lib.utils.Utils;

public class ThaumcraftProcessingHandler {
	
	@Method(modid = "thaumcraft")
	public static void init() {
		//init aspects
		ThaumcraftApi.registerObjectTag(new ItemStack(new ItemCell()), new AspectList().add(Aspect.VOID, 5).add(Aspect.METAL, 24));
		
		for(MetaItem item : MetaItem.getMetaItems()) {
			if(item instanceof ToolMetaItem<?>) {
				ToolMetaItem<?> tool = (ToolMetaItem<?>)item;
				if(tool.getToolMaterial(new ItemStack(tool)).getAverageMass() < 20) {
					ThaumcraftApi.registerObjectTag(new ItemStack(item), new AspectList().add(Aspect.TOOL, 7).add(Aspect.CRAFT, 10).add(Aspect.MECHANISM, 5));
				}
				if(tool.getToolMaterial(new ItemStack(tool)).getAverageMass() > 20 && tool.getToolMaterial(new ItemStack(tool)).getAverageMass() < 100) {
					ThaumcraftApi.registerObjectTag(new ItemStack(item), new AspectList().add(Aspect.TOOL, 14).add(Aspect.CRAFT, 20).add(Aspect.MECHANISM, 10));
				}
				else {
					ThaumcraftApi.registerObjectTag(new ItemStack(item), new AspectList().add(Aspect.TOOL, 18).add(Aspect.CRAFT, 30).add(Aspect.MECHANISM, 20));
				}
			}
		}
		
		for(Material material : Material.MATERIAL_REGISTRY) {			
			if(material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
				DustMaterial m = (DustMaterial)material;
				//init ore aspects
				ThaumcraftApi.registerObjectTag(OrePrefix.oreEndstone + StringUtils.capitalize(m.getUnlocalizedName()), new AspectList().add(Aspect.DARKNESS, 5).add(Aspect.ENTROPY, 2)/*.add(SPACE, 2)*/);
				ThaumcraftApi.registerObjectTag(OrePrefix.oreNetherrack + StringUtils.capitalize(m.getUnlocalizedName()), new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2));
				ThaumcraftApi.registerObjectTag(OrePrefix.oreGravel + StringUtils.capitalize(m.getUnlocalizedName()), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 3));
				ThaumcraftApi.registerObjectTag(OrePrefix.oreSand + StringUtils.capitalize(m.getUnlocalizedName()), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 3));		
				ThaumcraftApi.registerObjectTag(OrePrefix.oreSandstone + StringUtils.capitalize(m.getUnlocalizedName()), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 3).add(Aspect.EARTH, 2));		

				ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(GTValues.MODID, "metal_purification" + m.getLocalizedName().toLowerCase()), new CrucibleRecipe("METALPURIFICATION", OreDictUnifier.get(OrePrefix.cluster, m), "ore" + m.toCamelCaseString(), new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
				ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(GTValues.MODID, "end_metal_purification" + m.getLocalizedName().toLowerCase()), new CrucibleRecipe("METALPURIFICATION", OreDictUnifier.get(OrePrefix.cluster, m), "oreGravel" + m.toCamelCaseString(), new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
				ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(GTValues.MODID, "gravel_metal_purification" + m.getLocalizedName().toLowerCase()), new CrucibleRecipe("METALPURIFICATION", OreDictUnifier.get(OrePrefix.cluster, m, 3), "oreEndstone" + m.toCamelCaseString(), new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5).merge(Aspect.VOID, 2)));
				ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(GTValues.MODID, "sand_metal_purification" + m.getLocalizedName().toLowerCase()), new CrucibleRecipe("METALPURIFICATION", OreDictUnifier.get(OrePrefix.cluster, m), "oreSand" + m.toCamelCaseString(), new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
				ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(GTValues.MODID, "sandstone_metal_purification" + m.getLocalizedName().toLowerCase()), new CrucibleRecipe("METALPURIFICATION", OreDictUnifier.get(OrePrefix.cluster, m), "oreSandstone" + m.toCamelCaseString(), new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
				ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(GTValues.MODID, "nether_metal_purification" + m.getLocalizedName().toLowerCase()), new CrucibleRecipe("METALPURIFICATION", OreDictUnifier.get(OrePrefix.cluster, m, 2), "oreNetherrack" + m.toCamelCaseString(), new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5).merge(Aspect.FIRE, 2)));
			
				Utils.addSpecialMiningResult(OreDictUnifier.get(OrePrefix.ore, m), OreDictUnifier.get(OrePrefix.cluster, m), 1.0F);	
				
				RecipeMaps.ORE_WASHER_RECIPES.recipeBuilder()
      	      		.inputs(OreDictUnifier.get(OrePrefix.cluster, m, 2))
      	      		.fluidInputs(Materials.UUMatter.getFluid(2))
      	      		.outputs(OreDictUnifier.get(OrePrefix.crushedPurified, m, 2), 
      	      				GTValues.isModLoaded("mekanism") ? OreDictUnifier.get(OrePrefix.dustPure, m) : OreDictUnifier.get(OrePrefix.shard, m))
      	      		.chancedOutput(OreDictUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, m, m.oreByProducts, DustMaterial.class)), 1000)
      	      		.EUt(340)
      	      		.duration((int)m.getAverageMass() * 2)
      	      		.buildAndRegister();
				
				OreRecipeHandler.processMetalSmelting(OrePrefix.cluster, m, 9);
			}
		}
	}

}
