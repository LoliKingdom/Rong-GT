package gregtech.loaders.recipes;

import org.apache.commons.lang3.tuple.Pair;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneBlock;
import gregtech.common.blocks.BlockConcrete.ConcreteVariant;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;

public class WoodRecipeLoader {
	
	public static void init() {
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
            .input(OrePrefix.log, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 5))
            .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Wood), 8000)
            .buildAndRegister();
        
        ItemStack woodStack = OreDictUnifier.get(OrePrefix.log, Materials.Wood);
        ItemStack smeltingOutput = ModHandler.getSmeltingOutput(woodStack);
        if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
            int coalAmount = smeltingOutput.getCount();
            RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(16, woodStack))
                .circuitMeta(0)
                .outputs(new ItemStack(Items.COAL, 24 * coalAmount, 1))
                .fluidOutputs(Materials.Creosote.getFluid(5000 * coalAmount))
                .duration(440)
                .EUt(64)
                .buildAndRegister();

            RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(16, woodStack))
                .circuitMeta(1)
                .fluidInputs(Materials.Nitrogen.getFluid(400))
                .outputs(new ItemStack(Items.COAL, 24, 1))
                .fluidOutputs(Materials.Creosote.getFluid(4000))
                .duration(200)
                .EUt(96)
                .buildAndRegister();

            RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
                .inputs(GTUtility.copyAmount(16, woodStack))
                .circuitMeta(2)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 5))
                .fluidOutputs(Materials.OilHeavy.getFluid(500 * coalAmount))
                .duration(280)
                .EUt(192)
                .buildAndRegister();
            
            RecipeMaps.PYROLYSE_RECIPES.recipeBuilder()
            	.inputs(GTUtility.copyAmount(12, woodStack))
            	.circuitMeta(3)
            	.outputs(new ItemStack(Items.COAL, 8 * coalAmount, 1))
            	.fluidOutputs(Materials.Phenol.getFluid(200 * coalAmount))
            	.duration(720)
            	.EUt(44)
            	.buildAndRegister();
        }
        
        Pair<IRecipe, ItemStack> outputPair = ModHandler.getRecipeOutput(null, woodStack);
        ItemStack output = outputPair.getValue();
        int originalOutput = output.getCount();
        if (!output.isEmpty()) {
            IRecipe outputRecipe = outputPair.getKey();
            GTLog.logger.info("Nerfing planks crafting recipe {} -> {}", woodStack, output);
            if(originalOutput / 2 > 0) {
            	ModHandler.addShapelessRecipe(outputRecipe.getRegistryName().toString(),
                GTUtility.copyAmount(originalOutput / 2, output), woodStack);
            	} else {
            		ModHandler.removeRecipeByName(outputRecipe.getRegistryName());
                }
            RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                .inputs(woodStack)
                .fluidInputs(Materials.Lubricant.getFluid(1))
                .outputs(GTUtility.copyAmount((int) (originalOutput * 3), output),
                    OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
                .duration(200).EUt(8)
                .buildAndRegister();

            ModHandler.addShapedRecipe(outputRecipe.getRegistryName().getResourcePath() + "_saw",
                GTUtility.copyAmount(originalOutput, output), "s", "L", 'L', woodStack);
        }
        
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood)
            .outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 4))
            .duration(20)
            .EUt(8)
            .buildAndRegister();
        
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
        	.input(OrePrefix.log, Materials.Wood)
        	.outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 16))
        	.duration(40)
        	.EUt(12)
        	.buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .input(OrePrefix.dust, Materials.Redstone)
            .outputs(new ItemStack(Blocks.NOTEBLOCK, 1))
            .duration(200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .input(OrePrefix.gem, Materials.Diamond)
            .outputs(new ItemStack(Blocks.JUKEBOX, 2))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 3)
            .inputs(new ItemStack(Items.BOOK, 3))
            .outputs(new ItemStack(Blocks.BOOKSHELF, 3))
            .duration(400)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood)
            .circuitMeta(1)
            .outputs(new ItemStack(Blocks.WOODEN_BUTTON, 2))
            .duration(100)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 2)
            .circuitMeta(2)
            .outputs(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 2))
            .duration(200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 4)
            .circuitMeta(3)
            .outputs(new ItemStack(Blocks.TRAPDOOR, 4))
            .duration(300)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 4)
            .circuitMeta(4)
            .outputs(new ItemStack(Blocks.CRAFTING_TABLE))
            .duration(400).EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plank, Materials.Wood, 8)
            .outputs(new ItemStack(Blocks.CHEST, 1))
            .duration(800).EUt(4).circuitMeta(8)
            .buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        .inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.OAK.getMetadata()))
        .outputs(new ItemStack(Items.OAK_DOOR, 3))
        .duration(600).EUt(4).circuitMeta(6)
        .buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.SPRUCE.getMetadata()))
        	.outputs(new ItemStack(Items.SPRUCE_DOOR, 3))
        	.duration(600).EUt(4).circuitMeta(6)
        	.buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.BIRCH.getMetadata()))
        	.outputs(new ItemStack(Items.BIRCH_DOOR, 3))
        	.duration(600).EUt(4).circuitMeta(6)
        	.buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.JUNGLE.getMetadata()))
        	.outputs(new ItemStack(Items.JUNGLE_DOOR, 3))
        	.duration(600).EUt(4).circuitMeta(6)
        	.buildAndRegister();
    
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.ACACIA.getMetadata()))
        	.outputs(new ItemStack(Items.ACACIA_DOOR, 3))
        	.duration(600).EUt(4).circuitMeta(6)
        	.buildAndRegister();
    
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        	.inputs(new ItemStack(Blocks.PLANKS, 6, EnumType.DARK_OAK.getMetadata()))
        	.outputs(new ItemStack(Items.DARK_OAK_DOOR, 3))
        	.duration(600).EUt(4).circuitMeta(6)
        	.buildAndRegister();
    }
}