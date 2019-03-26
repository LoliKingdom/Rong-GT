package gregtech.loaders.recipes.processing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;

public class MaterialRecipeHandler {

    public static void register() {
        OrePrefix.ingot.addProcessingHandler(IngotMaterial.class, MaterialRecipeHandler::processIngot);
        OrePrefix.nugget.addProcessingHandler(SolidMaterial.class, MaterialRecipeHandler::processNugget);

        OrePrefix.block.addProcessingHandler(DustMaterial.class, MaterialRecipeHandler::processBlock);
        OrePrefix.frameGt.addProcessingHandler(SolidMaterial.class, MaterialRecipeHandler::processFrame);

        OrePrefix.dust.addProcessingHandler(DustMaterial.class, MaterialRecipeHandler::processDust);
        OrePrefix.dustSmall.addProcessingHandler(DustMaterial.class, MaterialRecipeHandler::processSmallDust);
        OrePrefix.dustTiny.addProcessingHandler(DustMaterial.class, MaterialRecipeHandler::processTinyDust);
        
        OrePrefix.gem.addProcessingHandler(GemMaterial.class, MaterialRecipeHandler::processGem);
    }

    public static void processDust(OrePrefix dustPrefix, DustMaterial material) {
        if(material instanceof GemMaterial) {
            ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
            ItemStack tinyDarkAshStack = OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh);
            if(material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {

                RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                    .input(dustPrefix, material)
                    .fluidInputs(Materials.Water.getFluid(200))
                    .chancedOutput(gemStack, 7000)
                    .duration(2000)
                    .EUt(24)
                    .buildAndRegister();

                RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                    .input(dustPrefix, material)
                    .fluidInputs(Materials.DistilledWater.getFluid(200))
                    .chancedOutput(gemStack, 9000)
                    .duration(1500)
                    .EUt(24)
                    .buildAndRegister();
            } 
            else if (!material.hasFlag(Material.MatFlags.EXPLOSIVE) && !material.hasFlag(Material.MatFlags.FLAMMABLE)) {
                RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                    .input(dustPrefix, material, 4)
                    .outputs(GTUtility.copyAmount(3, gemStack), GTUtility.copyAmount(2, tinyDarkAshStack))
                    .explosivesAmount(4)
                    .buildAndRegister();
            }
        } 
        else if (material instanceof IngotMaterial) {
            IngotMaterial metalMaterial = (IngotMaterial) material;
            if(!material.hasFlag(Material.MatFlags.FLAMMABLE | MatFlags.NO_SMELTING)) {

                boolean hasHotIngot = OrePrefix.ingotHot.doGenerateItem(metalMaterial);
                ItemStack ingotStack = OreDictUnifier.get(hasHotIngot ? OrePrefix.ingotHot : OrePrefix.ingot, metalMaterial);
                ItemStack nuggetStack = OreDictUnifier.get(OrePrefix.nugget, metalMaterial);

                if(metalMaterial.blastFurnaceTemperature <= 0) {
                    ModHandler.addSmeltingRecipe(new UnificationEntry(dustPrefix, metalMaterial), ingotStack);
                    ModHandler.addSmeltingRecipe(new UnificationEntry(OrePrefix.dustTiny, metalMaterial), nuggetStack);
                } 
                else {
                    int duration = Math.max(1, (int) (material.getAverageMass() * metalMaterial.blastFurnaceTemperature / 50L));
                    ModHandler.removeFurnaceSmelting(new UnificationEntry(OrePrefix.ingot, metalMaterial));
                    RecipeMaps.BLAST_RECIPES.recipeBuilder()
                        .input(dustPrefix, material)
                        .outputs(ingotStack)
                        .duration(duration).EUt(120)
                        .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                        .buildAndRegister();
                    if(!hasHotIngot) {
                        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                            .input(OrePrefix.dustTiny, material)
                            .outputs(nuggetStack)
                            .duration(Math.max(1, duration / 9)).EUt(120)
                            .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                            .buildAndRegister();
                    }
                    if(hasHotIngot) {
                        RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                        	.fluidInputs(metalMaterial.blastFurnaceTemperature >= 4000 ? 
                        			Materials.Chlorotrifluoroethylene.getFluid(20) : Materials.Chlorotrifluoroethylene.getFluid(10))
                            .input(OrePrefix.ingotHot, metalMaterial)
                            .outputs(OreDictUnifier.get(OrePrefix.ingot, metalMaterial))
                            .duration(metalMaterial.blastFurnaceTemperature / 16)
                            .buildAndRegister();
                    }
                }
            }
        } 
        else if (material.hasFlag(MatFlags.GENERATE_PLATE) && !material.hasFlag(EXCLUDE_PLATE_COMPRESSOR_RECIPE)) {
            RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .input(dustPrefix, material)
                .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                .buildAndRegister();
        }
    }

    public static void processSmallDust(OrePrefix orePrefix,DustMaterial material) {
        ItemStack smallDustStack = OreDictUnifier.get(orePrefix, material);
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

        ModHandler.addShapedRecipe(String.format("small_dust_disassembling_%s", material.toString()),
            GTUtility.copyAmount(4, smallDustStack), "  ", " X", 'X', new UnificationEntry(OrePrefix.dust, material));
        ModHandler.addShapedRecipe(String.format("small_dust_assembling_%s", material.toString()),
            dustStack, "XX", "XX", 'X', new UnificationEntry(orePrefix, material));
    }

    public static void processTinyDust(OrePrefix orePrefix, DustMaterial material) {
        ItemStack tinyDustStack = OreDictUnifier.get(orePrefix, material);
        ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

        ModHandler.addShapedRecipe(String.format("tiny_dust_disassembling_%s", material.toString()),
            GTUtility.copyAmount(9, tinyDustStack), "X ", "  ", 'X', new UnificationEntry(OrePrefix.dust, material));
        ModHandler.addShapedRecipe(String.format("tiny_dust_assembling_%s", material.toString()),
            dustStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(orePrefix, material));
    }

    public static void processIngot(OrePrefix ingotPrefix, IngotMaterial material) {
        if (material.hasFlag(MORTAR_GRINDABLE)) {
            ModHandler.addShapedRecipe(String.format("mortar_grind_%s", material.toString()),
                OreDictUnifier.get(OrePrefix.dust, material), "X", "m", 'X', new UnificationEntry(ingotPrefix, material));
        }

        if (!material.hasFlag(MatFlags.NO_SMASHING) && material.toolDurability > 0) {
            ModHandler.addShapedRecipe(String.format("wrench_%s", material.toString()),
                MetaItems.WRENCH.getStackForm(material),
                "IhI", "III", " I ", 'I', new UnificationEntry(ingotPrefix, material));
        }

        if(material.hasFlag(GENERATE_ROD)) {
            ModHandler.addShapedRecipe(String.format("stick_%s", material.toString()),
                OreDictUnifier.get(OrePrefix.stick, material, 1),
                "f ", " X",
                'X', new UnificationEntry(ingotPrefix, material));
            if(!material.hasFlag(NO_SMASHING)) {
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(ingotPrefix, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                    .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                    .duration((int) material.getAverageMass() * 2)
                    .EUt(74)
                    .buildAndRegister();
            }
        }

        if(material.shouldGenerateFluid()) {
            RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                .fluidInputs(material.getFluid(L))
                .outputs(OreDictUnifier.get(ingotPrefix, material))
                .duration(20).EUt(8)
                .buildAndRegister();
        }
        if (material.hasFlag(MatFlags.GENERATE_PLATE) && !material.hasFlag(NO_SMASHING)) {
            ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
            RecipeMaps.BENDER_RECIPES.recipeBuilder()
                .circuitMeta(0)
                .input(ingotPrefix, material)
                .outputs(plateStack)
                .EUt(24).duration((int) (material.getAverageMass()))
                .buildAndRegister();

            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                .input(ingotPrefix, material, 3)
                .outputs(GTUtility.copyAmount(2, plateStack))
                .EUt(16).duration((int) (material.getAverageMass() * 2))
                .buildAndRegister();

            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                .input(ingotPrefix, material)
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                .duration((int) material.getAverageMass())
                .EUt(74)
                .buildAndRegister();

            RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(ingotPrefix, material, 2)
                .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                .duration((int) material.getAverageMass() * 2)
                .EUt(24)
                .buildAndRegister();

            ModHandler.addShapedRecipe(String.format("plate_%s", material.toString()),
                plateStack, "h", "I", "I", 'I', new UnificationEntry(ingotPrefix, material));
        }

    }

    public static void processGem(OrePrefix gemPrefix, GemMaterial material) {
        long materialAmount = gemPrefix.materialAmount;
        ItemStack crushedStack = OreDictUnifier.getDust(material, materialAmount);

        if (material.hasFlag(MORTAR_GRINDABLE)) {
            ModHandler.addShapedRecipe(String.format("gem_to_dust_%s_%s", material, gemPrefix), crushedStack,
                "X", "m", 'X', new UnificationEntry(gemPrefix, material));
        } else if (materialAmount >= M && material.hasFlag(SolidMaterial.MatFlags.GENERATE_ROD)) {
            ItemStack gemStick = OreDictUnifier.get(OrePrefix.stick, material, (int) (materialAmount / M));
            ItemStack gemDust = OreDictUnifier.getDust(material, materialAmount % M);
            if (!gemStick.isEmpty() && !gemDust.isEmpty()) {
                RecipeMaps.LATHE_RECIPES.recipeBuilder()
                    .input(gemPrefix, material)
                    .outputs(gemStick, gemDust)
                    .duration((int) material.getAverageMass())
                    .EUt(16)
                    .buildAndRegister();
            }
        }
    }

    public static void processNugget(OrePrefix orePrefix, SolidMaterial material) {
        ItemStack nuggetStack = OreDictUnifier.get(orePrefix, material);
        if (material instanceof IngotMaterial) {
            ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);

            ModHandler.addShapelessRecipe(String.format("nugget_disassembling_%s", material.toString()),
                GTUtility.copyAmount(9, nuggetStack), new UnificationEntry(OrePrefix.ingot, material));
            ModHandler.addShapedRecipe(String.format("nugget_assembling_%s", material.toString()),
                ingotStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(orePrefix, material));

            if(material.shouldGenerateFluid()) {
                RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_NUGGET)
                    .fluidInputs(material.getFluid(L))
                    .outputs(OreDictUnifier.get(orePrefix, material, 9))
                    .duration((int) material.getAverageMass())
                    .EUt(8)
                    .buildAndRegister();
            }


        } else if (material instanceof GemMaterial) {
            ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);

            ModHandler.addShapelessRecipe(String.format("nugget_disassembling_%s", material.toString()),
                GTUtility.copyAmount(9, nuggetStack), new UnificationEntry(OrePrefix.gem, material));
            ModHandler.addShapedRecipe(String.format("nugget_assembling_%s", material.toString()),
                gemStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(orePrefix, material));
        }
    }

    public static void processFrame(OrePrefix framePrefix, SolidMaterial material) {
        if (material.hasFlag(GENERATE_PLATE | GENERATE_ROD)) {
            boolean isWoodenFrame = ModHandler.isMaterialWood(material);
            ItemStack frameStack = OreDictUnifier.get(framePrefix, material, 4);
            ModHandler.addShapedRecipe(String.format("frame_%s", material), frameStack,
                "PPP", "SSS", isWoodenFrame ? "SsS" : "SwS",
                'P', new UnificationEntry(isWoodenFrame ? OrePrefix.plank : OrePrefix.plate, material),
                'S', new UnificationEntry(OrePrefix.stick, material));
            
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            	.input(OrePrefix.plate, material, 3)
            	.input(OrePrefix.stick, material, 5)
            	.circuitMeta(1)
            	.outputs(frameStack)
            	.EUt(74).duration(200)
            	.buildAndRegister();
        }
    }

    public static void processBlock(OrePrefix blockPrefix, DustMaterial material) {
        ItemStack blockStack = OreDictUnifier.get(blockPrefix, material);
        long materialAmount = blockPrefix.getMaterialAmount(material);
        if (material.getMaterialFluid() != null) {
            RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                .fluidInputs(material.getFluid((int) (materialAmount * L / M)))
                .outputs(blockStack)
                .duration((int) material.getAverageMass()).EUt(8)
                .buildAndRegister();
        }
        if (material.hasFlag(MatFlags.GENERATE_PLATE)) {
            ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
            RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                .input(blockPrefix, material)
                .outputs(GTUtility.copyAmount((int) (materialAmount / M), plateStack))
                .duration((int) (material.getAverageMass() * 8L)).EUt(24)
                .buildAndRegister();
        }
        if(!material.hasFlag(EXCLUDE_BLOCK_CRAFTING_RECIPES)) {
        	if(material instanceof GemMaterial) {
        		ModHandler.addShapedRecipe(String.format("block_compress_%s", material.toString()), 
        				blockStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(OrePrefix.gem, material));
        		ModHandler.addShapelessRecipe(String.format("block_decompress_%s", material.toString()), 
        				OreDictUnifier.get(OrePrefix.gem, material, 9), blockStack);
        	}
        	else if(material instanceof IngotMaterial) {
        		ModHandler.addShapedRecipe(String.format("block_compress_%s", material.toString()), 
        				blockStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(OrePrefix.ingot, material));
        		ModHandler.addShapelessRecipe(String.format("block_decompress_%s", material.toString()), 
        				OreDictUnifier.get(OrePrefix.ingot, material, 9), blockStack);
        		
        		RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                	.input(OrePrefix.ingot, material, (int) (materialAmount / M))
                	.notConsumable(MetaItems.SHAPE_EXTRUDER_BLOCK)
                	.outputs(blockStack)
                	.duration(10).EUt(74)
                	.buildAndRegister();
        		
        		RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                	.input(OrePrefix.ingot, material, (int) (materialAmount / M))
                	.notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                	.outputs(blockStack)
                	.duration(100).EUt(24)
                	.buildAndRegister();
        	}
        	else {
        		ModHandler.addShapedRecipe(String.format("block_compress_%s", material.toString()), 
        				blockStack, "XXX", "XXX", "XXX", 'X', new UnificationEntry(OrePrefix.dust, material));
        		ModHandler.addShapelessRecipe(String.format("block_decompress_%s", material.toString()), 
        				OreDictUnifier.get(OrePrefix.dust, material, 9), blockStack);
        	}
        }
    }
}
