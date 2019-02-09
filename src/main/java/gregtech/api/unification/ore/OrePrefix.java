package gregtech.api.unification.ore;

import com.google.common.base.Preconditions;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.Condition;
import gregtech.api.util.GTUtility;
import gregtech.api.recipes.ModHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.GENERATE_LENSE;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.*;
import static gregtech.api.unification.ore.OrePrefix.Conditions.isToolMaterial;
import static gregtech.api.unification.ore.OrePrefix.Flags.*;

public enum OrePrefix {

    //oreBlackgranite("Black Granite Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    //oreRedgranite("Red Granite Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    //oreMarble("Marble Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    //oreBasalt("Basalt Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreSand("Sand Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat.hasFlag(GENERATE_ORE)), // In case of an Sand-Ores Mod. Ore -> Material is a Oneway Operation!
    oreGravel("Gravel Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat.hasFlag(GENERATE_ORE)), // In case of an Gravel-Ores Mod. Ore -> Material is a Oneway Operation!

    oreNetherrack("Netherrack Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreNether("Nether Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!

    oreEndstone("Endstone Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreEnd("End Ores", -1,null,  MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    
    //oreBedrock("Bedrock Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreSandstone("Sandstone Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreRedSandstone("Red Sandstone Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    ore("Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)), // Regular Ore Prefix. Ore -> Material is a Oneway Operation! Introduced by Eloraam

    crushedCentrifuged("Centrifuged Ores", -1, null, MaterialIconType.crushedCentrifuged, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),
    crushedPurified("Purified Ores", -1, null, MaterialIconType.crushedPurified, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),
    crushed("Crushed Ores", -1, null, MaterialIconType.crushed, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),
    
    cluster("Native Ore Clusters", -1, null, MaterialIconType.cluster, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),
    
    shard("Crystallised Shards", -1, null, MaterialIconType.shard, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),
    clump("Clumps", -1, null, MaterialIconType.clump, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),
    crystal("Crystals", -1, null, MaterialIconType.crystal, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat.hasFlag(GENERATE_ORE)),

    ingotHot("Hot Ingots", M, null, MaterialIconType.ingotHot, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> (mat instanceof IngotMaterial) && ((IngotMaterial) mat).blastFurnaceTemperature > 1750), // A hot Ingot, which has to be cooled down by a Vacuum Freezer.
    ingot("Ingots", M, null, MaterialIconType.ingot, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof IngotMaterial), // A regular Ingot. Introduced by Eloraam

    gem("Gemstones", M, null, MaterialIconType.gem, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial), // A regular Gem worth one Dust. Introduced by Eloraam
    gemChipped("Chipped Gemstones", M, null, MaterialIconType.gemChipped, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial), // A gem that would need polishing.
    //gemFlawed("Flawed Gemstones", M / 2, null, MaterialIconType.gemFlawed, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial), // A regular Gem worth two small Dusts. Introduced by TerraFirmaCraft
    //gemFlawless("Flawless Gemstones", M * 2, null, MaterialIconType.gemFlawless, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial), // A regular Gem worth two Dusts. Introduced by TerraFirmaCraft
    //gemExquisite("Exquisite Gemstones", M * 4, null, MaterialIconType.gemExquisite, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial), // A regular Gem worth four Dusts. Introduced by TerraFirmaCraft

    dustTiny("Tiny Dusts", M / 9, null, MaterialIconType.dustTiny, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial), // 1/9th of a Dust.
    dustSmall("Small Dusts", M / 4, null, MaterialIconType.dustSmall, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial), // 1/4th of a Dust.
    dustImpure("Impure Dusts", M, null, MaterialIconType.dustImpure, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat.hasFlag(GENERATE_ORE)), // Dust with impurities. 1 Unit of Main Material and 1/9 - 1/4 Unit of secondary Material
    dustPure("Purified Dusts", M, null, MaterialIconType.dustPure, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat.hasFlag(GENERATE_ORE)),
    dust("Dusts", M, null, MaterialIconType.dust, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial), // Pure Dust worth of one Ingot or Gem. Introduced by Alblaka.

    nugget("Nuggets", M / 9, null, MaterialIconType.nugget, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof IngotMaterial), // A Nugget. Introduced by Eloraam

    plate("Plates", M, null, MaterialIconType.plate, ENABLE_UNIFICATION, mat -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_PLATE)), // Regular Plate made of one Ingot/Dust. Introduced by Calclavia

    foil("Foils", M / 4, null, MaterialIconType.foil, ENABLE_UNIFICATION, mat -> mat instanceof IngotMaterial && mat.hasFlag(GENERATE_FOIL)), // Foil made of 1/4 Ingot/Dust.

    stickLong("Long Sticks/Rods", M, null, MaterialIconType.stickLong, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_LONG_ROD)), // Stick made of an Ingot.
    stick("Sticks/Rods", M / 2, null, MaterialIconType.stick, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_ROD)), // Stick made of half an Ingot. Introduced by Eloraam

    comb("Combs", M, null, null, DISALLOW_RECYCLING, null), // contain dusts

    screw("Screws", M / 9, null, MaterialIconType.screw, ENABLE_UNIFICATION, mat -> mat instanceof IngotMaterial && mat.hasFlag(GENERATE_BOLT_SCREW)), // consisting out of a Bolt.

    ring("Rings", M / 4, null, MaterialIconType.ring, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_RING)), // consisting out of 1/2 Stick.

    spring("Springs", M, null, MaterialIconType.spring, ENABLE_UNIFICATION, and(mat -> mat instanceof IngotMaterial, hasFlag(GENERATE_SPRING), noFlag(NO_SMASHING))), // consisting out of 2 Sticks.

    wireFine("Fine Wires", M / 8, null, MaterialIconType.wireFine, ENABLE_UNIFICATION, mat -> mat instanceof IngotMaterial && mat.hasFlag(GENERATE_FINE_WIRE)), // consisting out of 1/8 Ingot or 1/4 Wire.

    rotor("Rotors", M * 4 + M / 4, null, MaterialIconType.rotor, ENABLE_UNIFICATION, mat -> mat instanceof IngotMaterial && mat.hasFlag(GENERATE_ROTOR)), // consisting out of 4 Plates, 1 Ring and 1 Screw.

    gearSmall("Small Gears", M, null, MaterialIconType.gearSmall, ENABLE_UNIFICATION, mat -> mat instanceof IngotMaterial && mat.hasFlag(GENERATE_SMALL_GEAR)),
    gear("Gears", M * 4, null, MaterialIconType.gear, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_GEAR)), // Introduced by me because BuildCraft has ruined the gear Prefix...

    lens("Lenses", (M * 3) / 4, null, MaterialIconType.lens, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_LENSE)), // 3/4 of a Plate or Gem used to shape a Lense. Normally only used on Transparent Materials.

    bucket("Buckets", M, MarkerMaterials.Empty, null, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER, null), // A vanilla Iron Bucket filled with the Material.
    bottle("Bottles", -1, MarkerMaterials.Empty, null, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING, null), // Glass Bottle containing a Fluid.
    capsule("Capsules", M, MarkerMaterials.Empty, null, SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING, null),

    toolHeadFile("File Heads", M * 2, null, MaterialIconType.toolHeadFile, ENABLE_UNIFICATION, isToolMaterial), // made of 2 Ingots.
    toolHeadHammer("Hammer Heads", M * 6, null, MaterialIconType.toolHeadHammer, ENABLE_UNIFICATION, isToolMaterial), // made of 6 Ingots.
    toolHeadSaw("Saw Blades", M * 2, null, MaterialIconType.toolHeadSaw, ENABLE_UNIFICATION, isToolMaterial), // made of 2 Ingots.
    toolHeadBuzzSaw("Buzzsaw Blades", M * 4, null, MaterialIconType.toolHeadBuzzSaw, ENABLE_UNIFICATION, isToolMaterial), // made of 4 Ingots.
    toolHeadScrewdriver("Screwdriver Tips", M, null, MaterialIconType.toolHeadScrewdriver, ENABLE_UNIFICATION, isToolMaterial), // made of 1 Ingots.
    toolHeadDrill("Drill Tips", M * 4, null, MaterialIconType.toolHeadDrill, ENABLE_UNIFICATION, isToolMaterial), // made of 4 Ingots.
    toolHeadChainsaw("Chainsaw Tips", M * 2, null, MaterialIconType.toolHeadChainsaw, ENABLE_UNIFICATION, isToolMaterial), // made of 2 Ingots.
    toolHeadWrench("Wrench Tips", M * 4, null, MaterialIconType.toolHeadWrench, ENABLE_UNIFICATION, isToolMaterial), // made of 4 Ingots.

    turbineBlade("Turbine Blades", M * 6, null, MaterialIconType.turbineBlade, ENABLE_UNIFICATION, isToolMaterial), // made of 6 Ingots.

    toolSword("Swords", M * 2, null, null, 0, null), // vanilly Sword
    toolPickaxe("Pickaxes", M * 3, null, null, 0, null), // vanilly Pickaxe
    toolShovel("Shovels", M, null, null, 0, null), // vanilly Shovel
    toolAxe("Axes", M * 3, null, null, 0, null), // vanilly Axe
    toolHoe("Hoes", M * 2, null, null, 0, null), // vanilly Hoe
    toolShears("Shears", M * 2, null, null, 0, null), // vanilly Shears
    tool("Tools", -1, null, null, DISALLOW_RECYCLING, null), // toolPot, toolSkillet, toolSaucepan, toolBakeware, toolCuttingboard, toolMortarandpestle, toolMixingbowl, toolJuicer

    glass("Glasses", -1, Materials.Glass, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    paneGlass("Glass Panes", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    blockGlass("Glass Blocks", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),

    blockWool("Wool Blocks", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),

    block("Storage Blocks", M * 9, null, MaterialIconType.block, ENABLE_UNIFICATION, null), // Storage Block consisting out of 9 Ingots/Gems/Dusts. Introduced by CovertJaguar

    craftingTool("Crafting Tools", -1, null, null, DISALLOW_RECYCLING, null), // Special Prefix used mainly for the Crafting Handler.
    craftingLens("Crafting Ingredients", -1, null, null, DISALLOW_RECYCLING, null), // Special Prefix used mainly for the Crafting Handler.
    crafting("Crafting Ingredients", -1, null, null, DISALLOW_RECYCLING, null), // Special Prefix used mainly for the Crafting Handler.

    log("Logs", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Logs. Usually as "logWood". Introduced by Eloraam
    slab("Slabs", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Slabs. Usually as "slabWood" or "slabStone". Introduced by SirSengir
    stair("Stairs", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Stairs. Usually as "stairWood" or "stairStone". Introduced by SirSengir
    fence("Fences", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Fences. Usually as "fenceWood". Introduced by Forge
    plank("Planks", -1, null, null, DISALLOW_RECYCLING, null), // Prefix for Planks. Usually "plankWood". Introduced by Eloraam
    treeSapling("Saplings", -1, Materials.Wood, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix for Saplings.
    treeLeaves("Leaves", -1, Materials.Wood, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix for Leaves.
    tree("Tree Parts", -1, Materials.Wood, null, DISALLOW_RECYCLING, null), // Prefix for Tree Parts.
    stoneCobble("Cobblestones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Cobblestone Prefix for all Cobblestones.
    stoneSmooth("Smoothstones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Smoothstone Prefix.
    stoneMossyBricks("mossy Stone Bricks", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Mossy Stone Bricks.
    stoneMossy("Mossy Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Mossy Cobble.
    stoneBricks("Stone Bricks", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Stone Bricks.
    stoneCracked("Cracked Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Cracked Bricks.
    stoneChiseled("Chiseled Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Chiseled Stone.
    stone("Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix to determine which kind of Rock this is.
    cobblestone("Cobblestones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    rock("Rocks", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix to determine which kind of Rock this is.
    record("Records", -1, MarkerMaterials.Empty, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    rubble("Rubbles", -1, Materials.Stone, null, ENABLE_UNIFICATION | SELF_REFERENCING | DISALLOW_RECYCLING, null),
    scraps("Scraps", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),
    scrap("Scraps", -1, null, null, DISALLOW_RECYCLING, null),

    book("Books", -1, null, null, DISALLOW_RECYCLING, null), // Used for Books of any kind.
    paper("Papers", -1, null, null, DISALLOW_RECYCLING, null), // Used for Papers of any kind.
    dye("Dyes", -1, null, null, DISALLOW_RECYCLING, null), // Used for the 16 dyes. Introduced by Eloraam
    stainedClay("Stained Clays", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Used for the 16 colors of Stained Clay. Introduced by Forge
    frameGt("Frame Boxes", (long) (M * 1.375), null, null, ENABLE_UNIFICATION, material -> material instanceof IngotMaterial && material.hasFlag(GENERATE_ROD | GENERATE_PLATE)),
    pipeTiny("Tiny Pipes", M / 2, null, MaterialIconType.pipeTiny, ENABLE_UNIFICATION, null),
    pipeSmall("Small Pipes", M, null, MaterialIconType.pipeSmall, ENABLE_UNIFICATION, null),
    pipeMedium("Medium Pipes", M * 3, null, MaterialIconType.pipeMedium, ENABLE_UNIFICATION, null),
    pipeLarge("Large pipes", M * 6, null, MaterialIconType.pipeLarge, ENABLE_UNIFICATION, null),

    pipe("Pipes", -1, null, null, DISALLOW_RECYCLING, null),

    wireGtHex("Hex wires", M * 8, null, null, ENABLE_UNIFICATION, null),
    wireGtOctal("Octal wires", M * 4, null, null, ENABLE_UNIFICATION, null),
    wireGtQuadruple("Quadruple wires", M * 2, null, null, ENABLE_UNIFICATION, null),
    wireGtDouble("Double wires", M, null, null, ENABLE_UNIFICATION, null),
    wireGtSingle("Single wires", M / 2, null, null, ENABLE_UNIFICATION, null),

    cableGtHex("Hex cables", M * 8, null, null, ENABLE_UNIFICATION, null),
    cableGtOctal("Octal cables", M * 4, null, null, ENABLE_UNIFICATION, null),
    cableGtQuadruple("Quadruple cables", M * 2, null, null, ENABLE_UNIFICATION, null),
    cableGtDouble("Double cables", M, null, null, ENABLE_UNIFICATION, null),
    cableGtSingle("Single cables", M / 2, null, null, ENABLE_UNIFICATION, null),

    /* Electric Components.
     *
	 * usual Materials for this are:
	 * Primitive (Tier 1)
	 * Basic (Tier 2)
	 * Good (Tier 3)
	 * Advanced (Tier 4)
	 * Data (Tier 5)
	 * Elite (Tier 6)
	 * Master (Tier 7)
	 * Ultimate (Tier 8)
	 * Infinite
	 */
    batterySingleUse("Single Use Batteries", -1, null, null, DISALLOW_RECYCLING, null),
    battery("Reusable Batteries", -1, null, null, DISALLOW_RECYCLING, null), // Introduced by Calclavia
    circuit("Circuits", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Introduced by Calclavia
    chipset("Chipsets", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Introduced by Buildcraft
    computer("Computers", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null); // A whole Computer. "computerMaster" = ComputerCube

    public static class Flags {
        public static final long ENABLE_UNIFICATION = GTUtility.createFlag(0);
        public static final long SELF_REFERENCING = GTUtility.createFlag(1);
        public static final long FLUID_CONTAINER = GTUtility.createFlag(2);
        public static final long DISALLOW_RECYCLING = GTUtility.createFlag(3);
    }

	public static class Conditions {
		public static Condition<Material> isToolMaterial = mat -> mat instanceof SolidMaterial && ((SolidMaterial) mat).toolDurability > 0;
	}

    static {
        bottle.containerItem = new ItemStack(Items.GLASS_BOTTLE);
        bucket.containerItem = new ItemStack(Items.BUCKET);

        ingotHot.heatDamage = 3.0F;
        ingotHot.defaultStackSize = 16;

        //gemFlawless.defaultStackSize = 16;
        //gemExquisite.defaultStackSize = 8;

        rotor.defaultStackSize = 16;
        gear.defaultStackSize = 16;

        bucket.defaultStackSize = 4;
        bottle.defaultStackSize = 32;
        capsule.defaultStackSize = 32;

        toolHeadFile.defaultStackSize = 1;
        toolHeadHammer.defaultStackSize = 1;
        toolHeadSaw.defaultStackSize = 1;
        toolHeadBuzzSaw.defaultStackSize = 1;
        toolHeadScrewdriver.defaultStackSize = 1;
        toolHeadDrill.defaultStackSize = 1;
        toolHeadChainsaw.defaultStackSize = 1;
        toolHeadWrench.defaultStackSize = 1;

        toolSword.defaultStackSize = 1;
        toolPickaxe.defaultStackSize = 1;
        toolShovel.defaultStackSize = 1;
        toolAxe.defaultStackSize = 1;
        toolHoe.defaultStackSize = 1;
        toolShears.defaultStackSize = 1;
        tool.defaultStackSize = 1;

        record.defaultStackSize = 1;

        gem.setIgnored(Materials.Diamond);
        gem.setIgnored(Materials.Emerald);
        gem.setIgnored(Materials.Lapis);
        gem.setIgnored(Materials.NetherQuartz);

        gem.setIgnored(Materials.Coal);
        gemChipped.setIgnored(Materials.Coal);
        gem.setIgnored(Materials.Charcoal);
        gemChipped.setIgnored(Materials.Charcoal);
        gem.setIgnored(Materials.NetherStar);
        gemChipped.setIgnored(Materials.NetherStar);
        gem.setIgnored(Materials.EnderPearl);
        gemChipped.setIgnored(Materials.EnderPearl);
        gem.setIgnored(Materials.EnderEye);
        gemChipped.setIgnored(Materials.EnderEye);
        gem.setIgnored(Materials.Flint);
        gemChipped.setIgnored(Materials.Flint);

        dust.setIgnored(Materials.Redstone);
        dust.setIgnored(Materials.Glowstone);
        dust.setIgnored(Materials.Gunpowder);
        dust.setIgnored(Materials.Sugar);
        dust.setIgnored(Materials.Blaze);
        stick.setIgnored(Materials.Wood);
        stick.setIgnored(Materials.Bone);
        stick.setIgnored(Materials.Blaze);
        stick.setIgnored(Materials.Paper);
        ingot.setIgnored(Materials.Iron);
        ingot.setIgnored(Materials.Gold);
        ingot.setIgnored(Materials.Wood);
        ingot.setIgnored(Materials.Paper);
        nugget.setIgnored(Materials.Wood);
        nugget.setIgnored(Materials.Gold);
        nugget.setIgnored(Materials.Paper);
        nugget.setIgnored(Materials.Iron);
        plate.setIgnored(Materials.Paper);

        bucket.setIgnored(Materials.Lava);
        bucket.setIgnored(Materials.Milk);
        bucket.setIgnored(Materials.Water);
        bottle.setIgnored(Materials.Water);
        bottle.setIgnored(Materials.Milk);
        block.setIgnored(Materials.Iron);
        block.setIgnored(Materials.Gold);
        block.setIgnored(Materials.Lapis);
        block.setIgnored(Materials.Emerald);
        block.setIgnored(Materials.Redstone);
        block.setIgnored(Materials.Diamond);
        block.setIgnored(Materials.Coal);
        block.setIgnored(Materials.Glass);
        block.setIgnored(Materials.Marble);
        block.setIgnored(Materials.Stone);
        block.setIgnored(Materials.Glowstone);
        block.setIgnored(Materials.Endstone);
        block.setIgnored(Materials.Wheat);
        block.setIgnored(Materials.Oilsands);
        block.setIgnored(Materials.Wood);
        block.setIgnored(Materials.RawRubber);
        block.setIgnored(Materials.Clay);
        block.setIgnored(Materials.Bone);
        block.setIgnored(Materials.NetherQuartz);
        block.setIgnored(Materials.Ice);
        block.setIgnored(Materials.Netherrack);
        block.setIgnored(Materials.Blaze);
        block.setIgnored(Materials.Bedrock);

        cableGtHex.addSecondaryMaterial(new MaterialStack(Materials.Rubber, dustSmall.materialAmount * 4));
        cableGtOctal.addSecondaryMaterial(new MaterialStack(Materials.Rubber, dustSmall.materialAmount * 3));
        cableGtQuadruple.addSecondaryMaterial(new MaterialStack(Materials.Rubber, dustSmall.materialAmount * 2));
        cableGtDouble.addSecondaryMaterial(new MaterialStack(Materials.Rubber, dustSmall.materialAmount));
        cableGtSingle.addSecondaryMaterial(new MaterialStack(Materials.Rubber, dustSmall.materialAmount));

        bucket.addSecondaryMaterial(new MaterialStack(Materials.Iron, ingot.materialAmount * 3));

        oreSand.addSecondaryMaterial(new MaterialStack(Materials.SiliconDioxide, dustTiny.materialAmount));
        oreGravel.addSecondaryMaterial(new MaterialStack(Materials.Flint, dustTiny.materialAmount));

        oreNetherrack.addSecondaryMaterial(new MaterialStack(Materials.Netherrack, dust.materialAmount));
        oreNether.addSecondaryMaterial(new MaterialStack(Materials.Netherrack, dust.materialAmount));

        oreEndstone.addSecondaryMaterial(new MaterialStack(Materials.Endstone, dust.materialAmount));
        oreEnd.addSecondaryMaterial(new MaterialStack(Materials.Endstone, dust.materialAmount));
        
        //oreBedrock.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount));
        oreSandstone.addSecondaryMaterial(new MaterialStack(Materials.SiliconDioxide, dust.materialAmount));
        oreRedSandstone.addSecondaryMaterial(new MaterialStack(Materials.SiliconDioxide, dust.materialAmount));

        //oreDense.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount));
        //orePoor.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount * 2));
        //oreSmall.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount * 2));
        //oreNormal.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount * 2));
        //oreRich.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount * 2));
        ore.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount));

        crushed.addSecondaryMaterial(new MaterialStack(Materials.Stone, dust.materialAmount));

        toolHeadDrill.addSecondaryMaterial(new MaterialStack(Materials.Steel, plate.materialAmount * 4));
        toolHeadChainsaw.addSecondaryMaterial(new MaterialStack(Materials.Steel, plate.materialAmount * 4 + ring.materialAmount * 2));
        toolHeadWrench.addSecondaryMaterial(new MaterialStack(Materials.Steel, ring.materialAmount + screw.materialAmount * 2));
    }

    @SafeVarargs
    public static Condition<Material> and(Condition<Material>... conditions) {
        return new Condition.And<>(conditions);
    }

    public static Condition<Material> hasFlag(long generationFlags) {
        return (material) -> material.hasFlag(generationFlags);
    }

    public static Condition<Material> noFlag(long generationFlags) {
        return (material) -> !material.hasFlag(generationFlags);
    }

    public final String categoryName;

    public final boolean isUnificationEnabled;
    public final boolean isSelfReferencing;
    public final boolean isRecyclingDisallowed;
    public final boolean isFluidContainer;

    public final @Nullable Condition<Material> generationCondition;
    public final @Nullable MaterialIconType materialIconType;

    public final long materialAmount;

    /**
     * Contains a default material type for self-referencing OrePrefix
     * For self-referencing prefixes, it is always guaranteed for it to be not null
     *
     * NOTE: Ore registrations with self-referencing OrePrefix still can occur with other materials
     */
    public @Nullable Material materialType;

    private final List<IOreRegistrationHandler> oreProcessingHandlers = new ArrayList<>();
    private final Set<Material> ignoredMaterials = new HashSet<>();
    private final Set<Material> generatedMaterials = new HashSet<>();

    public @Nullable ItemStack containerItem = null;
    public byte defaultStackSize = 64;
    public final List<MaterialStack> secondaryMaterials = new ArrayList<>();
    public float heatDamage = 0.0F; // Negative for Frost Damage

    OrePrefix(String categoryName, long materialAmount, Material material, MaterialIconType materialIconType, long flags, Condition<Material> condition) {
        this.categoryName = categoryName;
        this.materialAmount = materialAmount;
        this.isSelfReferencing = (flags & SELF_REFERENCING) != 0;
        this.isUnificationEnabled = (flags & ENABLE_UNIFICATION) != 0;
        this.isRecyclingDisallowed = (flags & DISALLOW_RECYCLING) != 0;
        this.isFluidContainer = (flags & FLUID_CONTAINER) != 0;
        this.materialIconType = materialIconType;
        this.generationCondition = condition;
        if(isSelfReferencing) {
            Preconditions.checkNotNull( material, "Material is null for self-referencing OrePrefix");
            this.materialType = material;
        }
    }

    public void addSecondaryMaterial(MaterialStack secondaryMaterial) {
        Preconditions.checkNotNull(secondaryMaterial, "secondaryMaterial");
        secondaryMaterials.add(secondaryMaterial);
    }

    public long getMaterialAmount(Material material) {
        if(this == block) {
            //glowstone and nether quartz blocks use 4 gems (dusts)
            if(material == Materials.Glowstone ||
                material == Materials.NetherQuartz)
                return M * 4;
            //glass, ice and obsidian gain only one dust
            else if(material == Materials.Glass ||
                material == Materials.Ice ||
                material == Materials.Obsidian)
                return M;
        }
        return materialAmount;
    }

    public static OrePrefix getPrefix(String prefixName) {
        return getPrefix(prefixName, null);
    }

    public static OrePrefix getPrefix(String prefixName, @Nullable OrePrefix replacement) {
        try {
            return Enum.valueOf(OrePrefix.class, prefixName);
        } catch (IllegalArgumentException invalidPrefixName) {
            return replacement;
        }
    }

    public boolean doGenerateItem(Material material) {
        return !isSelfReferencing && generationCondition != null && !isIgnored(material) && generationCondition.isTrue(material);
    }

    public boolean addProcessingHandler(IOreRegistrationHandler... processingHandler) {
        Preconditions.checkNotNull(processingHandler);
        Validate.noNullElements(processingHandler);
        return oreProcessingHandlers.addAll(Arrays.asList(processingHandler));
    }

    public <T extends Material> void addProcessingHandler(Class<T> materialFilter, BiConsumer<OrePrefix, T> handler) {
        addProcessingHandler((orePrefix, material) -> {
            if(materialFilter.isAssignableFrom(material.getClass())) {
                //noinspection unchecked
                handler.accept(orePrefix, (T) material);
            }
        });
    }

    public void processOreRegistration(@Nullable Material material) {
        if(this.isSelfReferencing && material == null) {
            material = materialType; //append default material for self-referencing OrePrefix
        }
        if(material != null) {
            generatedMaterials.add(material);
        }
    }

    public static void runMaterialHandlers() {
        for(OrePrefix orePrefix : values()) {
            orePrefix.runGeneratedMaterialHandlers();
        }
    }

    private static final ThreadLocal<OrePrefix> currentProcessingPrefix = new ThreadLocal<>();
    private static final ThreadLocal<Material> currentMaterial = new ThreadLocal<>();

    public static OrePrefix getCurrentProcessingPrefix() {
        return currentProcessingPrefix.get();
    }

    public static Material getCurrentMaterial() {
        return currentMaterial.get();
    }

    private void runGeneratedMaterialHandlers() {
        currentProcessingPrefix.set(this);
        for(Material registeredMaterial : generatedMaterials) {
            currentMaterial.set(registeredMaterial);
            for(IOreRegistrationHandler registrationHandler : oreProcessingHandlers) {
                registrationHandler.processMaterial(this, registeredMaterial);
            }
            currentMaterial.set(null);
        }
        //clear generated materials for next pass
        generatedMaterials.clear();
        currentProcessingPrefix.set(null);
    }

    @SideOnly(Side.CLIENT)
    public String getLocalNameForItem(Material material) {
        String specfiedUnlocalized = "item." + material.toString() + "." + this.name();
        if (I18n.hasKey(specfiedUnlocalized)) return I18n.format(specfiedUnlocalized);
        String unlocalized = "item.material.oreprefix." + this.name();
        String matLocalized = material.getLocalizedName();
        String formatted = I18n.format(unlocalized, matLocalized);
        return formatted.equals(unlocalized) ? matLocalized : formatted;
    }

    public boolean isIgnored(Material material) {
        return ignoredMaterials.contains(material);
    }

    public void setIgnored(Material material) {
        ignoredMaterials.add(material);
    }

}