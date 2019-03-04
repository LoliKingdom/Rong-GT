package gregtech.loaders.recipes;

import com.google.common.base.CaseFormat;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials.Tier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingRecipeLoader {

    public static void init() {
        loadCraftingRecipes();
    }

    private static void loadCraftingRecipes() {
    	
        ModHandler.addShapelessRecipe("clay_block_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Clay, 4), 'm', Blocks.CLAY);
        ModHandler.addShapelessRecipe("clay_ball_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Clay), 'm', Items.CLAY_BALL);
        ModHandler.addShapelessRecipe("brick_block_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Brick, 4), 'm', Blocks.BRICK_BLOCK);
        ModHandler.addShapelessRecipe("brick_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Brick), 'm', Items.BRICK);
        ModHandler.addShapelessRecipe("wheat_to_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1), 'm', Items.WHEAT);
        ModHandler.addShapelessRecipe("gravel_to_flint", new ItemStack(Items.FLINT, 1), 'm', Blocks.GRAVEL);
        ModHandler.addShapelessRecipe("blaze_rod_to_powder", new ItemStack(Items.BLAZE_POWDER, 3), 'm', Items.BLAZE_ROD);

        ModHandler.addShapedRecipe("plank_to_wooden_shape", MetaItems.WOODEN_FORM_EMPTY.getStackForm(), "   ", " X ", "s  ", 'X', new UnificationEntry(OrePrefix.plank, Materials.Wood));
        ModHandler.addShapedRecipe("wooden_shape_brick", MetaItems.WOODEN_FORM_BRICK.getStackForm(), "k ", " X", 'X', MetaItems.WOODEN_FORM_EMPTY.getStackForm());
        ModHandler.addShapedRecipe("compressed_clay", MetaItems.COMPRESSED_CLAY.getStackForm(8), "XXX", "XYX", "XXX", 'Y', MetaItems.WOODEN_FORM_BRICK.getStackForm(), 'X', Items.CLAY_BALL);
        ModHandler.addShapelessRecipe("fireclay_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Fireclay, 2), new UnificationEntry(OrePrefix.dust, Materials.Brick), new UnificationEntry(OrePrefix.dust, Materials.Clay));
        ModHandler.addSmeltingRecipe(MetaItems.COMPRESSED_CLAY.getStackForm(), MetaItems.COKE_OVEN_BRICK.getStackForm());
        ModHandler.addSmeltingRecipe(MetaItems.COMPRESSED_FIRECLAY.getStackForm(), MetaItems.FIRECLAY_BRICK.getStackForm());
    	
        //ModHandler.addSmeltingRecipe(new UnificationEntry(OrePrefix.nugget, Materials.Iron), OreDictUnifier.get(OrePrefix.nugget, Materials.WroughtIron));
        //ModHandler.addShapedRecipe("primitive_circuit", MetaItems.CIRCUIT_PRIMITIVE.getStackForm(), "SR", "RT", 'S', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'R', new UnificationEntry(OrePrefix.wireGtSingle, Materials.RedAlloy), 'T', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin));
        //ModHandler.addShapedRecipe("basic_circuit", MetaItems.CIRCUIT_BASIC.getStackForm(), "XXX", "NPN", "XXX", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'N', new UnificationEntry(OrePrefix.circuit, Tier.Primitive), 'X', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper));
        //ModHandler.addShapelessRecipe("integrated_circuit", MetaItems.INTEGRATED_CIRCUIT.getStackForm(), MetaItems.CIRCUIT_BASIC.getStackForm());

        ModHandler.addShapedRecipe("rubber_ring", OreDictUnifier.get(OrePrefix.ring, Materials.Rubber), "k", "X", 'X', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        ModHandler.addShapedRecipe("lignite_coal_torch", new ItemStack(Blocks.TORCH, 2), "X", "Y", 'X', new UnificationEntry(OrePrefix.gem, Materials.Lignite), 'Y', new UnificationEntry(OrePrefix.stick, Materials.Wood));

        ModHandler.addShapelessRecipe("iron_magnetic_stick", OreDictUnifier.get(OrePrefix.stick, Materials.IronMagnetic), new UnificationEntry(OrePrefix.stick, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Redstone), new UnificationEntry(OrePrefix.dust, Materials.Redstone), new UnificationEntry(OrePrefix.dust, Materials.Redstone), new UnificationEntry(OrePrefix.dust, Materials.Redstone));
        ModHandler.addShapedRecipe("paper_ring", OreDictUnifier.get(OrePrefix.ring, Materials.Paper), "PPk", 'P', new UnificationEntry(OrePrefix.plate, Materials.Paper));

        ModHandler.addShapedRecipe("torch_sulfur", new ItemStack(Blocks.TORCH, 2), "C", "S", 'C', new UnificationEntry(OrePrefix.dust, Materials.Sulfur), 'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));
        ModHandler.addShapedRecipe("torch_phosphor", new ItemStack(Blocks.TORCH, 6), "C", "S", 'C', new UnificationEntry(OrePrefix.dust, Materials.Phosphorus), 'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));

        ModHandler.addShapedRecipe("small_wooden_pipe", MetaBlocks.FLUID_PIPE.getItem(FluidPipeType.SMALL_OPAQUE, Materials.Wood), "XXX", "r s", "XXX", 'X', new UnificationEntry(OrePrefix.plank, Materials.Wood));
        ModHandler.addShapedRecipe("medium_wooden_pipe", MetaBlocks.FLUID_PIPE.getItem(FluidPipeType.SMALL_OPAQUE, Materials.Wood), "XXX", "s r", "XXX", 'X', new UnificationEntry(OrePrefix.plank, Materials.Wood));

        //Move these to automatic processing for all metals to be suitable for use of crafting a piston
        //ModHandler.addShapedRecipe("piston_bronze", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Bronze));
        //ModHandler.addShapedRecipe("piston_aluminium", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Aluminium));
        //ModHandler.addShapedRecipe("piston_steel", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Steel));
        //ModHandler.addShapedRecipe("piston_titanium", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Titanium));

        ModHandler.addShapelessRecipe("dynamite", MetaItems.DYNAMITE.getStackForm(), Items.STRING, Items.PAPER, Items.GUNPOWDER);

        ModHandler.addShapedRecipe("iron_bucket", new ItemStack(Items.BUCKET), "XhX", " X ", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:bucket"));
        
        ModHandler.addShapedRecipe("iron_pressure_plate", new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), "XXh", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:heavy_weighted_pressure_plate"));

        ModHandler.addShapedRecipe("gold_pressure_plate", new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), "XXh", 'X', new UnificationEntry(OrePrefix.plate, Materials.Gold));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:light_weighted_pressure_plate"));

        ModHandler.addShapedRecipe("iron_door", new ItemStack(Items.IRON_DOOR, 3), "XX ", "XXh", "XX ", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_door"));

        ModHandler.addShapedRecipe("iron_trapdoor", new ItemStack(Blocks.IRON_TRAPDOOR), "XX ", "XXh", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_trapdoor"));

        ModHandler.addShapedRecipe("cauldron", new ItemStack(Items.CAULDRON), "X X", "XhX", "XXX", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:cauldron"));

        ModHandler.addShapedRecipe("hopper", new ItemStack(Blocks.HOPPER), "XwX", "XCX", " X ", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron), 'C', "chestWood");
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:hopper"));

        ModHandler.addShapedRecipe("iron_bars", new ItemStack(Blocks.IRON_BARS, 8), " w ", "XXX", "XXX", 'X', new UnificationEntry(OrePrefix.stick, Materials.Iron));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_bars"));

        ModHandler.addShapedRecipe("bowl", new ItemStack(Items.BOWL), "k", "X", 'X', new UnificationEntry(OrePrefix.plank, Materials.Wood));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:bowl"));

        ModHandler.addShapedRecipe("stick_saw", new ItemStack(Items.STICK, 4), "s", "P", "P", 'P', new UnificationEntry(OrePrefix.plank, Materials.Wood));
        ModHandler.addShapedRecipe("stick_normal", new ItemStack(Items.STICK, 2), "P", "P", 'P', new UnificationEntry(OrePrefix.plank, Materials.Wood));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:stick"));

        //ModHandler.addShapelessRecipe("dust_electrum", OreDictUnifier.get(OrePrefix.dust, Materials.Electrum, 2), MetaItems.MORTAR.getStackForm(), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Gold));
        ModHandler.addShapelessRecipe("dust_brass", OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 4), MetaItems.MORTAR.getStackForm(), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Zinc));
        ModHandler.addShapelessRecipe("dust_brass_tetrahedrite", OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 3), MetaItems.MORTAR.getStackForm(), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Zinc));
        ModHandler.addShapelessRecipe("dust_bronze", OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 4), MetaItems.MORTAR.getStackForm(), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Tin));
        ModHandler.addShapelessRecipe("dust_bronze_tetrahedrite", OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 3), MetaItems.MORTAR.getStackForm(), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tin));
        /*
        ModHandler.addShapelessRecipe("dust_invar", OreDictUnifier.get(OrePrefix.dust, Materials.Invar, 3), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Nickel));
        ModHandler.addShapelessRecipe("dust_cupronickel", OreDictUnifier.get(OrePrefix.dust, Materials.Cupronickel, 2), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_nichrome", OreDictUnifier.get(OrePrefix.dust, Materials.Nichrome, 5), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Chrome));

        ModHandler.addShapelessRecipe("dust_rose_gold", OreDictUnifier.get(OrePrefix.dust, Materials.RoseGold, 5), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_sterling_silver", OreDictUnifier.get(OrePrefix.dust, Materials.SterlingSilver, 5), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_black_bronze", OreDictUnifier.get(OrePrefix.dust, Materials.BlackBronze, 5), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_bismuth_bronze", OreDictUnifier.get(OrePrefix.dust, Materials.BismuthBronze, 5), new UnificationEntry(OrePrefix.dust, Materials.Bismuth), new UnificationEntry(OrePrefix.dust, Materials.Zinc), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_black_steel", OreDictUnifier.get(OrePrefix.dust, Materials.BlackSteel, 5), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.BlackBronze), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel));
        ModHandler.addShapelessRecipe("dust_red_steel", OreDictUnifier.get(OrePrefix.dust, Materials.RedSteel, 8), new UnificationEntry(OrePrefix.dust, Materials.SterlingSilver), new UnificationEntry(OrePrefix.dust, Materials.BismuthBronze), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel));
        ModHandler.addShapelessRecipe("dust_blue_steel", OreDictUnifier.get(OrePrefix.dust, Materials.BlueSteel, 8), new UnificationEntry(OrePrefix.dust, Materials.RoseGold), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel));
        
        ModHandler.addShapelessRecipe("dust_ultimet", OreDictUnifier.get(OrePrefix.dust, Materials.Ultimet, 9), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Chrome), new UnificationEntry(OrePrefix.dust, Materials.Chrome), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Molybdenum));
        ModHandler.addShapelessRecipe("dust_cobalt_brass", OreDictUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 9), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Aluminium), new UnificationEntry(OrePrefix.dust, Materials.Cobalt));
        ModHandler.addShapelessRecipe("dust_stainless_steel", OreDictUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 9), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Manganese), new UnificationEntry(OrePrefix.dust, Materials.Chrome));
        ModHandler.addShapelessRecipe("dust_yttrium_barium_cuprate", OreDictUnifier.get(OrePrefix.dust, Materials.YttriumBariumCuprate, 6), new UnificationEntry(OrePrefix.dust, Materials.Yttrium), new UnificationEntry(OrePrefix.dust, Materials.Barium), new UnificationEntry(OrePrefix.dust, Materials.Barium), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_kanthal", OreDictUnifier.get(OrePrefix.dust, Materials.Kanthal, 3), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Aluminium), new UnificationEntry(OrePrefix.dust, Materials.Chrome));

        ModHandler.addShapelessRecipe("dust_tiny_ultimet", OreDictUnifier.get(OrePrefix.dust, Materials.Ultimet, 1), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome), new UnificationEntry(OrePrefix.dustTiny, Materials.Nickel), new UnificationEntry(OrePrefix.dustTiny, Materials.Molybdenum));
        ModHandler.addShapelessRecipe("dust_tiny_cobalt_brass", OreDictUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 1), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Aluminium), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt));
        ModHandler.addShapelessRecipe("dust_tiny_stainless_steel", OreDictUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 1), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Nickel), new UnificationEntry(OrePrefix.dustTiny, Materials.Manganese), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome));
        ModHandler.addShapelessRecipe("dust_tiny_yttrium_barium_cuprate", OreDictUnifier.get(OrePrefix.dustTiny, Materials.YttriumBariumCuprate, 6), new UnificationEntry(OrePrefix.dustTiny, Materials.Yttrium), new UnificationEntry(OrePrefix.dustTiny, Materials.Barium), new UnificationEntry(OrePrefix.dustTiny, Materials.Barium), new UnificationEntry(OrePrefix.dustTiny, Materials.Copper), new UnificationEntry(OrePrefix.dustTiny, Materials.Copper), new UnificationEntry(OrePrefix.dustTiny, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_tiny_kanthal", OreDictUnifier.get(OrePrefix.dustTiny, Materials.Kanthal, 3), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Aluminium), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome));

        ModHandler.addShapelessRecipe("dust_vanadium_steel", OreDictUnifier.get(OrePrefix.dust, Materials.VanadiumSteel, 9), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Vanadium), new UnificationEntry(OrePrefix.dust, Materials.Chrome));
        ModHandler.addShapelessRecipe("dust_hssg", OreDictUnifier.get(OrePrefix.dust, Materials.HSSG, 9), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.Chrome), new UnificationEntry(OrePrefix.dust, Materials.Molybdenum), new UnificationEntry(OrePrefix.dust, Materials.Molybdenum), new UnificationEntry(OrePrefix.dust, Materials.Vanadium));
        ModHandler.addShapelessRecipe("dust_hsse", OreDictUnifier.get(OrePrefix.dust, Materials.HSSE, 9), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Manganese), new UnificationEntry(OrePrefix.dust, Materials.Silicon));
        ModHandler.addShapelessRecipe("dust_hsss", OreDictUnifier.get(OrePrefix.dust, Materials.HSSS, 9), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.Iridium), new UnificationEntry(OrePrefix.dust, Materials.Iridium), new UnificationEntry(OrePrefix.dust, Materials.Osmium));

        ModHandler.addShapelessRecipe("powder_coal", new ItemStack(Items.GUNPOWDER, 6), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Sulfur), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter));
        ModHandler.addShapelessRecipe("powder_charcoal", new ItemStack(Items.GUNPOWDER, 6), new UnificationEntry(OrePrefix.dust, Materials.Charcoal), new UnificationEntry(OrePrefix.dust, Materials.Charcoal), new UnificationEntry(OrePrefix.dust, Materials.Charcoal), new UnificationEntry(OrePrefix.dust, Materials.Sulfur), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter));
        ModHandler.addShapelessRecipe("powder_carbon", new ItemStack(Items.GUNPOWDER, 6), new UnificationEntry(OrePrefix.dust, Materials.Carbon), new UnificationEntry(OrePrefix.dust, Materials.Carbon), new UnificationEntry(OrePrefix.dust, Materials.Carbon), new UnificationEntry(OrePrefix.dust, Materials.Sulfur), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter));
        */
             
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:paper"));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:sugar"));
        ModHandler.addShapedRecipe("paper_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 2), "SSS", " m ", 'S', new ItemStack(Items.REEDS));
        ModHandler.addShapedRecipe("sugar", OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), "Sm ", 'S', new ItemStack(Items.REEDS));
        ItemStack paperStack = OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2);
        Object[] paperRecipeIngredients = ModHandler.finalizeShapedRecipeInput(" C ", "SSS", " C ", 'S', OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.STONE_SLAB));
        ForgeRegistries.RECIPES.register(new CustomItemReturnShapedOreRecipe(null, paperStack,
            stack -> Block.getBlockFromItem(stack.getItem()) == Blocks.STONE_SLAB, paperRecipeIngredients)
.setMirrored(false).setRegistryName("paper"));

        ModHandler.addShapedRecipe("flint_and_steel", new ItemStack(Items.FLINT_AND_STEEL), "S ", " F", 'F', new ItemStack(Items.FLINT, 1), 'S', new UnificationEntry(OrePrefix.nugget, Materials.Steel));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:flint_and_steel"));
        
        MetaBlocks.FRAMES.values().forEach(CraftingRecipeLoader::registerColoringRecipes);
        
        ModHandler.addShapedRecipe("item_filter", MetaItems.ITEM_FILTER.getStackForm(), "XXX", "XYX", "XXX", 'X', new UnificationEntry(OrePrefix.foil, Materials.Zinc), 'Y', new UnificationEntry(OrePrefix.plate, Materials.Steel));
        ModHandler.addShapedRecipe("fluid_filter", MetaItems.FLUID_FILTER.getStackForm(), "XXX", "XYX", "XXX", 'X', new UnificationEntry(OrePrefix.foil, Materials.Zinc), 'Y', new UnificationEntry(OrePrefix.plate, Materials.Lapis));
        ModHandler.addShapedRecipe("ore_dictionary_filter", MetaItems.ORE_DICTIONARY_FILTER.getStackForm(), "XXX", "XYX", "XXX", 'X', new UnificationEntry(OrePrefix.foil, Materials.Zinc), 'Y', new UnificationEntry(OrePrefix.plate, Materials.Olivine));
        
        ModHandler.addShapelessRecipe("basic_to_configurable_circuit", MetaItems.INTEGRATED_CIRCUIT.getStackForm(), "circuitBasic");
        
        ModHandler.addShapedRecipe("lv_emitter", MetaItems.EMITTER_LV.getStackForm(), "RRS", "CGR", "SCR", 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Brass), 'S', "circuitBasic", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin), 'G', OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite));
        ModHandler.addShapedRecipe("mv_emitter", MetaItems.EMITTER_MV.getStackForm(), "RRS", "CGR", "SCR", 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Electrum), 'S', "circuitGood", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper), 'G', OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz));
        ModHandler.addShapedRecipe("hv_emitter", MetaItems.EMITTER_HV.getStackForm(), "RRS", "CGR", "SCR", 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Chrome), 'S', "circuitAdvanced", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Gold), 'G', OreDictUnifier.get(OrePrefix.gem, Materials.Emerald));
        ModHandler.addShapedRecipe("ev_emitter", MetaItems.EMITTER_EV.getStackForm(), "RRS", "CGR", "SCR", 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Platinum), 'S', "circuitElite", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Aluminium), 'G', OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl));
        ModHandler.addShapedRecipe("iv_emitter", MetaItems.EMITTER_IV.getStackForm(), "RRS", "CGR", "SCR", 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Osmium), 'S', "circuitMaster", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tungsten), 'G', OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye));

        ModHandler.addShapedRecipe("lv_sensor", MetaItems.SENSOR_LV.getStackForm(), "P G", "PR ", "SPP", 'P', "plateSteel", 'G', "gemQuartzite", 'R', "stickBrass", 'S', "circuitBasic");
        ModHandler.addShapedRecipe("mv_sensor", MetaItems.SENSOR_MV.getStackForm(), "P G", "PR ", "SPP", 'P', "plateAluminium", 'G', "gemNetherQuartz", 'R', "stickElectrum", 'S', "circuitGood");
        ModHandler.addShapedRecipe("hv_sensor", MetaItems.SENSOR_HV.getStackForm(), "P G", "PR ", "SPP", 'P', "plateStainlessSteel", 'G', "gemEmerald", 'R', "stickChrome", 'S', "circuitAdvanced");
        ModHandler.addShapedRecipe("ev_sensor", MetaItems.SENSOR_EV.getStackForm(), "P G", "PR ", "SPP", 'P', "plateTitanium", 'G', "gemEnderPearl", 'R', "stickPlatinum", 'S', "circuitElite");
        ModHandler.addShapedRecipe("iv_sensor", MetaItems.SENSOR_IV.getStackForm(), "P G", "PR ", "SPP", 'P', "plateTungstenSteel", 'G', "gemEnderEye", 'R', "stickOsmium", 'S', "circuitMaster");

        ModHandler.addShapedRecipe("lv_robot_arm", MetaItems.ROBOT_ARM_LV.getStackForm(), "CCC", "MRM", "PSR", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin), 'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(), 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Steel), 'P', MetaItems.ELECTRIC_PISTON_LV.getStackForm(), 'S', "circuitBasic");
        ModHandler.addShapedRecipe("mv_robot_arm", MetaItems.ROBOT_ARM_MV.getStackForm(), "CCC", "MRM", "PSR", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper), 'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(), 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Aluminium), 'P', MetaItems.ELECTRIC_PISTON_MV.getStackForm(), 'S', "circuitGood");
        ModHandler.addShapedRecipe("hv_robot_arm", MetaItems.ROBOT_ARM_HV.getStackForm(), "CCC", "MRM", "PSR", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Gold), 'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(), 'R', OreDictUnifier.get(OrePrefix.stick, Materials.StainlessSteel), 'P', MetaItems.ELECTRIC_PISTON_HV.getStackForm(), 'S', "circuitAdvanced");
        ModHandler.addShapedRecipe("ev_robot_arm", MetaItems.ROBOT_ARM_EV.getStackForm(), "CCC", "MRM", "PSR", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Aluminium), 'M', MetaItems.ELECTRIC_MOTOR_EV.getStackForm(), 'R', OreDictUnifier.get(OrePrefix.stick, Materials.Titanium), 'P', MetaItems.ELECTRIC_PISTON_EV.getStackForm(), 'S', "circuitExtreme");
        ModHandler.addShapedRecipe("iv_robot_arm", MetaItems.ROBOT_ARM_IV.getStackForm(), "CCC", "MRM", "PSR", 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tungsten), 'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(), 'R', OreDictUnifier.get(OrePrefix.stick, Materials.TungstenSteel), 'P', MetaItems.ELECTRIC_PISTON_IV.getStackForm(), 'S', "circuitElite");

        ModHandler.addShapedRecipe("lv_field_generator", MetaItems.FIELD_GENERATOR_LV.getStackForm(), "WSW", "SGS", "WSW", 'W', OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Osmium), 'S', "circuitBasic", 'G', OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl));
        ModHandler.addShapedRecipe("mv_field_generator", MetaItems.FIELD_GENERATOR_MV.getStackForm(), "WSW", "SGS", "WSW", 'W', OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Osmium), 'S', "circuitGood", 'G', OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye));
        ModHandler.addShapedRecipe("hv_field_generator", MetaItems.FIELD_GENERATOR_HV.getStackForm(), "WSW", "SGS", "WSW", 'W', OreDictUnifier.get(OrePrefix.wireGtQuadruple, Materials.Osmium), 'S', "circuitAdvanced", 'G', MetaItems.QUANTUM_EYE.getStackForm());
        ModHandler.addShapedRecipe("ev_field_generator", MetaItems.FIELD_GENERATOR_EV.getStackForm(), "WSW", "SGS", "WSW", 'W', OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.Osmium), 'S', "circuitExtreme", 'G', OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar));
        ModHandler.addShapedRecipe("iv_field_generator", MetaItems.FIELD_GENERATOR_IV.getStackForm(), "WSW", "SGS", "WSW", 'W', OreDictUnifier.get(OrePrefix.wireGtHex, Materials.Osmium), 'S', "circuitElite", 'G', MetaItems.QUANTUM_STAR.getStackForm());

        ModHandler.addShapedRecipe("lv_electric_pump_papercraft", MetaItems.ELECTRIC_PUMP_LV.getStackForm(), "SRH", "dPw", "HMC", 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Tin), 'R', OreDictUnifier.get(OrePrefix.rotor, Materials.Tin), 'H', OreDictUnifier.get(OrePrefix.ring, Materials.Paper), 'P', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Bronze), 'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(), 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin));
        
        MaterialStack[] cableFluids = {
                					   new MaterialStack(Materials.Rubber, 144),
                					   new MaterialStack(Materials.StyreneButadieneRubber, 108),
                					   new MaterialStack(Materials.SiliconeRubber, 72)
        							  };
        for(MaterialStack stack : cableFluids) {
        	IngotMaterial m = (IngotMaterial)stack.material;
            ModHandler.addShapedRecipe("lv_electric_pump_" + m.toString(), MetaItems.ELECTRIC_PUMP_LV.getStackForm(), "SRH", "dPw", "HMC", 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Tin), 'R', OreDictUnifier.get(OrePrefix.rotor, Materials.Tin), 'H', OreDictUnifier.get(OrePrefix.ring, m), 'P', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Bronze), 'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(), 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin));
            ModHandler.addShapedRecipe("mv_electric_pump_" + m.toString(), MetaItems.ELECTRIC_PUMP_MV.getStackForm(), "SRH", "dPw", "HMC", 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Bronze), 'R', OreDictUnifier.get(OrePrefix.rotor, Materials.Bronze), 'H', OreDictUnifier.get(OrePrefix.ring, m), 'P', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Steel), 'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(), 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Copper));
            ModHandler.addShapedRecipe("hv_electric_pump_" + m.toString(), MetaItems.ELECTRIC_PUMP_HV.getStackForm(), "SRH", "dPw", "HMC", 'S', OreDictUnifier.get(OrePrefix.screw, Materials.Steel), 'R', OreDictUnifier.get(OrePrefix.rotor, Materials.Steel), 'H', OreDictUnifier.get(OrePrefix.ring, m), 'P', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.StainlessSteel), 'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(), 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Gold));
            ModHandler.addShapedRecipe("ev_electric_pump_" + m.toString(), MetaItems.ELECTRIC_PUMP_EV.getStackForm(), "SRH", "dPw", "HMC", 'S', OreDictUnifier.get(OrePrefix.screw, Materials.StainlessSteel), 'R', OreDictUnifier.get(OrePrefix.rotor, Materials.StainlessSteel), 'H', OreDictUnifier.get(OrePrefix.ring, m), 'P', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Titanium), 'M', MetaItems.ELECTRIC_MOTOR_EV.getStackForm(), 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Aluminium));
            ModHandler.addShapedRecipe("iv_electric_pump_" + m.toString(), MetaItems.ELECTRIC_PUMP_IV.getStackForm(), "SRH", "dPw", "HMC", 'S', OreDictUnifier.get(OrePrefix.screw, Materials.TungstenSteel), 'R', OreDictUnifier.get(OrePrefix.rotor, Materials.TungstenSteel), 'H', OreDictUnifier.get(OrePrefix.ring, m), 'P', OreDictUnifier.get(OrePrefix.pipeMedium, Materials.TungstenSteel), 'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm(), 'C', OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tungsten));
        }
        
        ModHandler.addShapelessRecipe("superconducter_wire_gtsingle_doubling", OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtdouble_doubling", OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtquadruple_doubling", OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtoctal_doubling", OreDictUnifier.get(OrePrefix.wireGtHex, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor), OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtdouble_splitting", OreDictUnifier.get(OrePrefix.wireGtSingle, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtquadruple_splitting", OreDictUnifier.get(OrePrefix.wireGtDouble, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gtoctal_splitting", OreDictUnifier.get(OrePrefix.wireGtQuadruple, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor));
        ModHandler.addShapelessRecipe("superconducter_wire_gthex_splitting", OreDictUnifier.get(OrePrefix.wireGtOctal, Tier.Superconductor, 2), OreDictUnifier.get(OrePrefix.wireGtHex, Tier.Superconductor));
    
        for (ItemStack stack : OreDictionary.getOres("logWood")) {
            ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
            if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
                ItemStack woodStack = stack.copy();
                woodStack.setItemDamage(OreDictionary.WILDCARD_VALUE);
                ModHandler.removeFurnaceSmelting(woodStack);
            }
        }
    }
    
    private static void registerColoringRecipes(BlockColored block) {
        for(EnumDyeColor dyeColor : EnumDyeColor.values()) {
            String colorName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dyeColor.getName());
            String recipeName = String.format("%s_color_%s", block.getRegistryName().getResourcePath(), colorName);
            ModHandler.addShapedRecipe(recipeName, new ItemStack(block, 8, dyeColor.getMetadata()), "XXX", "XDX", "XXX",
                'X', new ItemStack(block, 1, GTValues.W), 'D', "dye" + colorName);
        }
    }
}
