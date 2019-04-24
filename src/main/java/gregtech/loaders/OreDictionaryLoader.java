package gregtech.loaders;

import static gregtech.api.GTValues.M;
import static gregtech.api.GTValues.W;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.common.MetaFluids;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {
	
	public static void postInit() {
        ItemStack cellStack = MetaItems.FLUID_CELL.getStackForm();

        for(Material m : Material.MATERIAL_REGISTRY) {   
        	if(!(m instanceof FluidMaterial)) return;
        	FluidMaterial material = (FluidMaterial)m;
        	if(material instanceof DustMaterial && ((DustMaterial)material).shouldGenerateSlurries()) {
        		if(FluidUtil.getFluidHandler(cellStack) != null) {
        			DustMaterial slurryMaterial = (DustMaterial)m;
        			IFluidHandlerItem fluidHandlerCleanSlurry = cellStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        			IFluidHandlerItem fluidHandlerDirtySlurry = cellStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        			fluidHandlerCleanSlurry.fill(new FluidStack(slurryMaterial.getMaterialCleanSlurry(), 1000), true);
        			fluidHandlerDirtySlurry.fill(new FluidStack(slurryMaterial.getMaterialDirtySlurry(), 1000), true);
        			ItemStack stackDirty = fluidHandlerDirtySlurry.getContainer();
        			ItemStack stackClean = fluidHandlerCleanSlurry.getContainer();
        			OreDictionary.registerOre("cellDirtySlurry" + slurryMaterial.toCamelCaseString(), stackDirty);
        			OreDictionary.registerOre("cellCleanSlurry" + slurryMaterial.toCamelCaseString(), stackClean);
        		}
        	}       
        	if(material.shouldGenerateFluid()) {
        		if(FluidUtil.getFluidHandler(cellStack) != null) {
                	IFluidHandlerItem fluidHandler = cellStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                	fluidHandler.fill(material.getFluid(1000), true);
                	ItemStack stack = fluidHandler.getContainer();
                	OreDictionary.registerOre("cell" + material.toCamelCaseString(), stack);
                	IFluidHandlerItem plasmaHandler = cellStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                	plasmaHandler.fill(material.getPlasma(1000), true);
                	ItemStack plasmaStack = plasmaHandler.getContainer();
                	OreDictionary.registerOre("cellPlasma" + material.toCamelCaseString(), stack);
                	
                }
        	}
        }
	}

    public static void init() {
        GTLog.logger.info("Registering OreDict entries.");
        
        OreDictUnifier.registerOre(new ItemStack(Items.CLAY_BALL, 1), OrePrefix.ingot, Materials.Clay);
        OreDictUnifier.registerOre(new ItemStack(Items.FLINT, 1), OrePrefix.gem, Materials.Flint);
        OreDictUnifier.registerOre(new ItemStack(Blocks.HARDENED_CLAY, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, M * 4)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, M * 4)));
        
        OreDictionary.registerOre("fuelCoke", OreDictUnifier.get(OrePrefix.gem, Materials.Coke));
        OreDictionary.registerOre("blockFuelCoke", OreDictUnifier.get(OrePrefix.block, Materials.Coke));
        OreDictionary.registerOre("blockCoke", OreDictUnifier.get(OrePrefix.block, Materials.Coke));

        OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_ORE), OrePrefix.ore, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_ORE), OrePrefix.ore, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_ORE), OrePrefix.ore, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_ORE), OrePrefix.ore, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_ORE), OrePrefix.ore, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_ORE), OrePrefix.ore, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_ORE), OrePrefix.ore, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_ORE), OrePrefix.ore, Materials.NetherQuartz);
        OreDictUnifier.registerOre(new ItemStack(Items.DYE, 1, 4), OrePrefix.gem, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Items.ENDER_EYE), OrePrefix.gem, Materials.EnderEye);
        OreDictUnifier.registerOre(new ItemStack(Items.ENDER_PEARL), OrePrefix.gem, Materials.EnderPearl);
        OreDictUnifier.registerOre(new ItemStack(Items.DIAMOND), OrePrefix.gem, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Items.EMERALD), OrePrefix.gem, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Items.COAL), OrePrefix.gem, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Items.COAL, 1, 1), OrePrefix.gem, Materials.Charcoal);
        OreDictUnifier.registerOre(new ItemStack(Items.QUARTZ), OrePrefix.gem, Materials.NetherQuartz);
        OreDictUnifier.registerOre(new ItemStack(Items.NETHER_STAR), OrePrefix.gem, Materials.NetherStar);
        OreDictUnifier.registerOre(new ItemStack(Items.GOLD_NUGGET), OrePrefix.nugget, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Items.GOLD_INGOT), OrePrefix.ingot, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Items.IRON_INGOT), OrePrefix.ingot, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Items.PAPER), OrePrefix.plate, Materials.Paper);
        OreDictUnifier.registerOre(new ItemStack(Items.SUGAR), OrePrefix.dust, Materials.Sugar);
        OreDictUnifier.registerOre(new ItemStack(Items.STICK), OrePrefix.stick, Materials.Wood);
        OreDictUnifier.registerOre(new ItemStack(Items.REDSTONE), OrePrefix.dust, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Items.GUNPOWDER), OrePrefix.dust, Materials.Gunpowder);
        OreDictUnifier.registerOre(new ItemStack(Items.GLOWSTONE_DUST), OrePrefix.dust, Materials.Glowstone);
        OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_POWDER), OrePrefix.dust, Materials.Blaze);
        OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_ROD), OrePrefix.stick, Materials.Blaze);
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_BLOCK), OrePrefix.block, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_BLOCK), OrePrefix.block, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_BLOCK), OrePrefix.block, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_BLOCK), OrePrefix.block, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_BLOCK), OrePrefix.block, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_BLOCK), OrePrefix.block, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_BLOCK), OrePrefix.block, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_BLOCK), OrePrefix.block, Materials.NetherQuartz);
        OreDictUnifier.registerOre(new ItemStack(Blocks.BONE_BLOCK), OrePrefix.block, Materials.Bone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.ICE), OrePrefix.block, Materials.Ice);
        OreDictUnifier.registerOre(new ItemStack(Blocks.OBSIDIAN), OrePrefix.block, Materials.Obsidian);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GLASS), OrePrefix.block, Materials.Glass);

        //OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 5), OrePrefix.stone, Materials.Andesite);
        //OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 6), OrePrefix.stone, Materials.Andesite);
        //OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 3), OrePrefix.stone, Materials.Diorite);
        //OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 4), OrePrefix.stone, Materials.Diorite);

        OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL), "craftingAnvil");
        OreDictUnifier.registerOre(new ItemStack(Blocks.OBSIDIAN, 1, W), OrePrefix.stone, Materials.Obsidian);
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneMossy");
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneCobble");
        OreDictUnifier.registerOre(new ItemStack(Blocks.COBBLESTONE, 1, W), "stoneCobble");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, W), "stoneSmooth");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, W), "stoneBricks");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 1), "stoneMossy");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 2), "stoneCracked");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 3), "stoneChiseled");
        OreDictUnifier.registerOre(new ItemStack(Blocks.NETHERRACK, 1, W), OrePrefix.stone, Materials.Netherrack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.END_STONE, 1, W), OrePrefix.stone, Materials.Endstone);

        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_TORCH, 1, W), "craftingRedstoneTorch");

        OreDictUnifier.registerOre(new ItemStack(Blocks.PISTON, 1, W), "craftingPiston");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STICKY_PISTON, 1, W), "craftingPiston");

        OreDictUnifier.registerOre(new ItemStack(Blocks.CHEST, 1, W), "chestWood");
        OreDictUnifier.registerOre(new ItemStack(Blocks.TRAPPED_CHEST, 1, W), "chestWood");

        OreDictUnifier.registerOre(new ItemStack(Blocks.FURNACE, 1, W), "craftingFurnace");

        OreDictUnifier.registerOre(new ItemStack(Items.FEATHER, 1, W), "craftingFeather");

        OreDictUnifier.registerOre(new ItemStack(Items.WHEAT, 1, W), "itemWheat");
        OreDictUnifier.registerOre(new ItemStack(Items.PAPER, 1, W), "paperEmpty");
        OreDictUnifier.registerOre(new ItemStack(Items.MAP, 1, W), "paperMap");
        OreDictUnifier.registerOre(new ItemStack(Items.FILLED_MAP, 1, W), "paperMap");
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "bookEmpty");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "bookWritable");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "bookWritten");
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "bookEnchanted");
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "craftingBook");
        
        for(ItemStack woodPlateStack : OreDictUnifier.getAll(new UnificationEntry(OrePrefix.plate, Materials.Wood))) {
            OreDictUnifier.registerOre(woodPlateStack, OrePrefix.plank, Materials.Wood);
        }
    }
}