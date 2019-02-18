package gregtech.api.unification.material;

import gregtech.api.GTValues;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.MarkerMaterials.Color;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.init.Enchantments;

import static com.google.common.collect.ImmutableList.of;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.FluidMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.Material.MatFlags.*;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.*;

@SuppressWarnings("WeakerAccess")
public class Materials {

    public static void register() {
        MarkerMaterials.register();
    }

    private static final long STD_SOLID = GENERATE_PLATE | GENERATE_ROD | GENERATE_BOLT_SCREW;
    private static final long STD_GEM = GENERATE_ORE | GENERATE_PLATE | GENERATE_ROD | GENERATE_LENSE;
    private static final long STD_METAL = GENERATE_PLATE;
    private static final long EXT_METAL = STD_METAL | GENERATE_ROD | GENERATE_BOLT_SCREW;
    private static final long EXT2_METAL = EXT_METAL | GENERATE_GEAR | GENERATE_FOIL | GENERATE_FINE_WIRE;

    public static MarkerMaterial _NULL = new MarkerMaterial("_null");
    
    /**
     * Direct Elements, not all as some are useless in this context
     */
    public static IngotMaterial Aluminium = new IngotMaterial(1, "aluminium", 0x80C8F0, MaterialIconSet.DULL, 2, of(), EXT2_METAL | GENERATE_SMALL_GEAR | GENERATE_ORE | GENERATE_RING, Element.Al, 10.0F, 128, 1700);
    public static IngotMaterial Americium = new IngotMaterial(2, "americium", 0xC8C8C8, MaterialIconSet.METALLIC, 3, of(), STD_METAL | GENERATE_ROD | GENERATE_LONG_ROD, Element.Am);
    public static IngotMaterial Antimony = new IngotMaterial(3, "antimony", 0xDCDCC8, MaterialIconSet.SHINY, 2, of(), EXT_METAL | MORTAR_GRINDABLE, Element.Sb);
    public static FluidMaterial Argon = new FluidMaterial(4, "argon", 0xBBBB00, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.Ar);
    public static DustMaterial Arsenic = new DustMaterial(5, "arsenic", 0xDDDDDD, MaterialIconSet.SAND, 2, of(), 0, Element.As);
    public static IngotMaterial Barium = new IngotMaterial(6, "barium", 0xFFFFFF, MaterialIconSet.SHINY, 2, of(), 0, Element.Ba);
    public static IngotMaterial Beryllium = new IngotMaterial(7, "beryllium", 0x64B464, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ORE, Element.Be, 14.0F, 64);
    public static IngotMaterial Bismuth = new IngotMaterial(8, "bismuth", 0x64A0A0, MaterialIconSet.METALLIC, 1, of(), GENERATE_ORE, Element.Bi, 6.0F, 64);
    public static DustMaterial Boron = new DustMaterial(9, "boron", 0xD2F0D2, MaterialIconSet.SAND, 2, of(), 0, Element.B);
    public static IngotMaterial Caesium = new IngotMaterial(10, "caesium", 0xFFFFFC, MaterialIconSet.DULL, 2, of(), 0, Element.Cs);
    public static IngotMaterial Calcium = new IngotMaterial(11, "calcium", 0xDDDDAA, MaterialIconSet.METALLIC, 2, of(), 0, Element.Ca);
    public static IngotMaterial Carbon = new IngotMaterial(12, "carbon", 0x333333, MaterialIconSet.DULL, 2, of(), 0, Element.C, 1.0F, 64);
    public static IngotMaterial Cadmium = new IngotMaterial(13, "cadmium", 0x505060, MaterialIconSet.SHINY, 2, of(), 0, Element.Cd);
    public static IngotMaterial Cerium = new IngotMaterial(14, "cerium", 0xEEEEEE, MaterialIconSet.METALLIC, 2, of(), 0, Element.Ce, 1068);
    public static FluidMaterial Chlorine = new FluidMaterial(15, "chlorine", 0xEEEECC, MaterialIconSet.GAS, of(), STATE_GAS, Element.Cl);
    public static IngotMaterial Chrome = new IngotMaterial(16, "chrome", 0xFFAAAB, MaterialIconSet.SHINY, 3, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Cr, 11.0F, 256, 1700);
    public static IngotMaterial Cobalt = new IngotMaterial(17, "cobalt", 0x2929BC, MaterialIconSet.METALLIC, 3, of(), EXT_METAL | GENERATE_ORE, Element.Co, 8.0F, 512);
    public static IngotMaterial Copper = new IngotMaterial(18, "copper", 0xFF8000, MaterialIconSet.SHINY, 1, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Cu);
    public static FluidMaterial Deuterium = new FluidMaterial(19, "deuterium", 0xEEEE00, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.D);
    //public static IngotMaterial Dysprosium = new IngotMaterial(20, "dysprosium", 0xFFFFEE, MaterialIconSet.SHINY, 2, of(), 0, Element.Dy, 1680);
    //public static IngotMaterial Erbium = new IngotMaterial(21, "erbium", 0xEEEEEE, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Er, 1802);
    public static IngotMaterial Europium = new IngotMaterial(22, "europium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ROD, Element.Eu, 1099);
    public static FluidMaterial Fluorine = new FluidMaterial(23, "fluorine", 0xFFFFAA, MaterialIconSet.GAS, of(), STATE_GAS, Element.F);
    //public static IngotMaterial Gadolinium = new IngotMaterial(24, "gadolinium", 0xDDDDFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.Gd, 1585);
    public static IngotMaterial Gallium = new IngotMaterial(25, "gallium", 0xEEEEFF, MaterialIconSet.SHINY, 2, of(), GENERATE_PLATE, Element.Ga, 1.0F, 64);
    public static IngotMaterial Gold = new IngotMaterial(26, "gold", 0xFFFF00, MaterialIconSet.SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Au, 12.0F, 64);
    //public static IngotMaterial Holmium = new IngotMaterial(27, "holmium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.Ho, 1734);
    public static FluidMaterial Hydrogen = new FluidMaterial(28, "hydrogen", 0x00FFAA, MaterialIconSet.GAS, of(), STATE_GAS, Element.H);
    public static FluidMaterial Helium = new FluidMaterial(29, "helium", 0xDDDD00, MaterialIconSet.GAS, of(), STATE_GAS, Element.He);
    public static FluidMaterial Helium3 = new FluidMaterial(30, "helium3", 0xDDDD00, MaterialIconSet.GAS, of(), STATE_GAS, Element.He_3);
    public static IngotMaterial Indium = new IngotMaterial(31, "indium", 0x6600BB, MaterialIconSet.METALLIC, 2, of(), 0, Element.In);
    public static IngotMaterial Iridium = new IngotMaterial(32, "iridium", 0xFFFFFF, MaterialIconSet.DULL, 3, of(), GENERATE_ORE | EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Ir, 6.0F, 2560, 2719);
    public static IngotMaterial Iron = new IngotMaterial(33, "iron", 0xAAAAAA, MaterialIconSet.METALLIC, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_RING, Element.Fe, 6.0F, 256);
    //public static IngotMaterial Lanthanum = new IngotMaterial(34, "lanthanum", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.La, 1193);
    public static IngotMaterial Lead = new IngotMaterial(35, "lead", 0x8C648C, MaterialIconSet.DULL, 1, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Pb, 8.0F, 64);
    public static IngotMaterial Lithium = new IngotMaterial(36, "lithium", 0xCBCBCB, MaterialIconSet.DULL, 2, of(), STD_METAL | GENERATE_ORE, Element.Li);
    public static IngotMaterial Lutetium = new IngotMaterial(37, "lutetium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.Lu, 1925);
    public static IngotMaterial Magnesium = new IngotMaterial(38, "magnesium", 0xFFBBBB, MaterialIconSet.METALLIC, 2, of(), 0, Element.Mg);
    public static IngotMaterial Manganese = new IngotMaterial(39, "manganese", 0xEEEEEE, MaterialIconSet.DULL, 2, of(), 0, Element.Mn, 7.0F, 512);
    public static FluidMaterial Mercury = new FluidMaterial(40, "mercury", 0xFFDDDD, MaterialIconSet.FLUID, of(), SMELT_INTO_FLUID, Element.Hg);
    public static IngotMaterial Molybdenum = new IngotMaterial(41, "molybdenum", 0xAAAADD, MaterialIconSet.DULL, 2, of(), GENERATE_ORE, Element.Mo, 7.0F, 512, 2);
    public static IngotMaterial Neodymium = new IngotMaterial(42, "neodymium", 0x777777, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ROD | GENERATE_ORE, Element.Nd, 7.0F, 512, 1297);
    public static IngotMaterial Darmstadtium = new IngotMaterial(43, "darmstadtium", 0xAAAAAA, MaterialIconSet.METALLIC, 6, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD, Element.Ds, 24.0F, 155360);
    public static IngotMaterial Nickel = new IngotMaterial(44, "nickel", 0xAAAAFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Ni, 6.0F, 64);
    public static IngotMaterial Niobium = new IngotMaterial(45, "niobium", 0x9486AA, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ORE, Element.Nb, 2750);
    public static FluidMaterial Nitrogen = new FluidMaterial(46, "nitrogen", 0x7090AF, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.N);
    public static IngotMaterial Osmium = new IngotMaterial(47, "osmium", 0x5050FF, MaterialIconSet.METALLIC, 4, of(), GENERATE_ORE | EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Os, 16.0F, 1280, 3306);
    public static FluidMaterial Oxygen = new FluidMaterial(48, "oxygen", 0x90AAEE, MaterialIconSet.FLUID, of(), STATE_GAS, Element.O);
    public static IngotMaterial Palladium = new IngotMaterial(49, "palladium", 0xCED0DD, MaterialIconSet.METALLIC, 2, of(), EXT2_METAL | GENERATE_ORE, Element.Pd, 8.0f, 512, 1228);
    public static DustMaterial Phosphorus = new DustMaterial(50, "phosphorus", 0xC8C800, MaterialIconSet.SAND, 2, of(), 0, Element.P);
    public static IngotMaterial Platinum = new IngotMaterial(51, "platinum", 0xFFFF99, MaterialIconSet.SHINY, 2, of(), EXT2_METAL | GENERATE_ORE, Element.Pt, 12.0F, 64);
    //public static IngotMaterial Plutonium = new IngotMaterial(52, "plutonium", 0xF03232, MaterialIconSet.METALLIC, 3, of(), EXT_METAL, Element.Pu, 6.0F, 512);
    public static IngotMaterial Plutonium239 = new IngotMaterial(53, "plutonium239", 0xFA4646, MaterialIconSet.SHINY, 3, of(), EXT_METAL, Element.Pu_239, 6.0F, 512);
    public static IngotMaterial Potassium = new IngotMaterial(54, "potassium", 0xCECECE, MaterialIconSet.METALLIC, 1, of(), EXT_METAL, Element.K);
    //public static IngotMaterial Praseodymium = new IngotMaterial(55, "praseodymium", 0xCECECE, MaterialIconSet.METALLIC, 2, of(), EXT_METAL, Element.Pr, 1208);
    //public static IngotMaterial Promethium = new IngotMaterial(56, "promethium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), EXT_METAL, Element.Pm, 1315);
    public static FluidMaterial Radon = new FluidMaterial(57, "radon", 0xFF00FF, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.Rn);
    public static IngotMaterial Rubidium = new IngotMaterial(58, "rubidium", 0xF01E1E, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Rb);
    //public static IngotMaterial Samarium = new IngotMaterial(59, "samarium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Sm, 1345);
    //public static IngotMaterial Scandium = new IngotMaterial(60, "scandium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Sc, 1814);
    public static IngotMaterial Silicon = new IngotMaterial(61, "silicon", 0x3C3C50, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_FOIL, Element.Si, 1687);
    public static IngotMaterial Silver = new IngotMaterial(62, "silver", 0xDCDCFF, MaterialIconSet.SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Ag, 10.0F, 64);
    public static IngotMaterial Sodium = new IngotMaterial(63, "sodium", 0x000096, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Na);
    //public static IngotMaterial Strontium = new IngotMaterial(64, "strontium", 0xC8C896, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Sr);
    public static DustMaterial Sulfur = new DustMaterial(65, "sulfur", 0xC8C800, MaterialIconSet.SAND, 2, of(), NO_SMASHING | NO_SMELTING | FLAMMABLE, Element.S);
    //public static IngotMaterial Tantalum = new IngotMaterial(66, "tantalum", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Ta);
    //public static IngotMaterial Tellurium = new IngotMaterial(67, "tellurium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Te);
    //public static IngotMaterial Terbium = new IngotMaterial(68, "terbium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Tb, 1629);
    public static IngotMaterial Thorium = new IngotMaterial(69, "thorium", 0x001E00, MaterialIconSet.SHINY, 2, of(), STD_METAL | GENERATE_ORE, Element.Th, 6.0F, 512);
    //public static IngotMaterial Thulium = new IngotMaterial(70, "thulium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Tm, 1818);
    public static IngotMaterial Tin = new IngotMaterial(71, "tin", 0xDCDCDC, MaterialIconSet.DULL, 1, of(), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR | GENERATE_ORE, Element.Sn);
    public static IngotMaterial Titanium = new IngotMaterial(72, "titanium", 0xDCA0F0, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_SPRING, Element.Ti, 7.0F, 1600, 1941);
    public static FluidMaterial Tritium = new FluidMaterial(73, "tritium", 0xFF0000, MaterialIconSet.METALLIC, of(), STATE_GAS, Element.T);
    public static IngotMaterial Tungsten = new IngotMaterial(74, "tungsten", 0x323232, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Element.W, 7.0F, 2560, 3000);
    public static IngotMaterial Uranium = new IngotMaterial(75, "uranium", 0x32F032, MaterialIconSet.METALLIC, 3, of(), STD_METAL, Element.U, 6.0F, 512);
    //public static IngotMaterial Uranium235 = new IngotMaterial(76, "uranium235", 0x46FA46, MaterialIconSet.SHINY, 3, of(), STD_METAL | GENERATE_ORE | GENERATE_ROD, Element.U_235, 6.0F, 512);
    public static IngotMaterial Vanadium = new IngotMaterial(77, "vanadium", 0x323232, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.V, 2183);
    public static IngotMaterial Ytterbium = new IngotMaterial(390, "ytterbium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Yb, 1097);
    public static IngotMaterial Yttrium = new IngotMaterial(78, "yttrium", 0xDCFADC, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Y, 1799);
    public static IngotMaterial Zinc = new IngotMaterial(79, "zinc", 0xFAF0F0, MaterialIconSet.METALLIC, 1, of(), STD_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_FOIL, Element.Zn);

    /**
     * Not possible to determine exact Components
     */
    public static RoughSolidMaterial Wood = new RoughSolidMaterial(80, "wood", 0x896727, MaterialIconSet.WOOD, 0, of(), STD_SOLID | FLAMMABLE | NO_SMELTING | GENERATE_GEAR | GENERATE_LONG_ROD, () -> OrePrefix.plank);
    public static FluidMaterial BioFuel = new FluidMaterial(81, "bio_fuel", 0xFF8000, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Biomass = new FluidMaterial(82, "biomass", 0x00FF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Creosote = new FluidMaterial(83, "creosote", 0x804000, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Ethanol = new FluidMaterial(84, "ethanol", 0xFF8000, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Fuel = new FluidMaterial(85, "fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Glue = new FluidMaterial(86, "glue", 0xC8C400, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Gunpowder = new DustMaterial(87, "gunpowder", 0x808080, MaterialIconSet.SAND, 0, of(), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static RoughSolidMaterial Paper = new RoughSolidMaterial(89, "paper", 0xFFFFFF, MaterialIconSet.PAPER, 0, of(), GENERATE_PLATE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE | GENERATE_RING | EXCLUDE_PLATE_COMPRESSOR_RECIPE, () -> OrePrefix.plate);
    public static DustMaterial RareEarth = new DustMaterial(90, "rare_earth", 0x808064, MaterialIconSet.ROUGH, 0, of(), 0);
    public static FluidMaterial SeedOil = new FluidMaterial(91, "seed_oil", 0xC4FF00, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Stone = new DustMaterial(92, "stone", 0xCDCDCD, MaterialIconSet.ROUGH, 1, of(), MORTAR_GRINDABLE | GENERATE_GEAR | GENERATE_PLATE | NO_SMASHING | NO_RECYCLING);
    public static DustMaterial Bedrock = new DustMaterial(93, "bedrock", 0x0A0A0A, MaterialIconSet.ROUGH, 5, of(), NO_SMASHING | NO_RECYCLING | GENERATE_PLATE);
    public static FluidMaterial Lava = new FluidMaterial(93, "lava", 0xFF4000, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Glowstone = new DustMaterial(94, "glowstone", 0xFFFF00, MaterialIconSet.SHINY, 1, of(), NO_SMASHING | SMELT_INTO_FLUID | GENERATE_PLATE | EXCLUDE_PLATE_COMPRESSOR_RECIPE);
    public static GemMaterial NetherStar = new GemMaterial(95, "nether_star", 0xFFFFFF, MaterialIconSet.NETHERSTAR, 4, of(), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 5120);
    public static DustMaterial Endstone = new DustMaterial(96, "endstone", 0xFFFFFF, MaterialIconSet.DULL, 1, of(), NO_SMASHING);
    public static DustMaterial Netherrack = new DustMaterial(97, "netherrack", 0xC80000, MaterialIconSet.ROUGH, 1, of(), NO_SMASHING | FLAMMABLE);
    public static FluidMaterial ConstructionFoam = new FluidMaterial(347, "construction_foam", 0x333333, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Concrete = new DustMaterial(296, "concrete", 0x646464, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Stone, 1)), NO_SMASHING | SMELT_INTO_FLUID);
    
    
    /**
     * First Degree Compounds
     */
    public static FluidMaterial Methane = new FluidMaterial(98, "methane", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4)), 0);
    public static FluidMaterial CarbonDioxide = new FluidMaterial(99, "carbon_dioxide", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 2)), GENERATE_PLASMA);
    public static FluidMaterial NobleGases = new FluidMaterial(100, "noble_gases", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(CarbonDioxide, 21), new MaterialStack(Helium, 9), new MaterialStack(Methane, 3), new MaterialStack(Deuterium, 1)), GENERATE_PLASMA);
    public static FluidMaterial Air = new FluidMaterial(101, "air", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial LiquidAir = new FluidMaterial(102, "liquid_air", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static DustMaterial Bone = new DustMaterial(103, "bone", 0xFFFFFF, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Calcium, 1)), 0);
    public static FluidMaterial Water = new FluidMaterial(104, "water", 0x0000FF, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING);
    public static DustMaterial Clay = new DustMaterial(105, "clay", 0xC8C8DC, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Water, 6)), MORTAR_GRINDABLE);
    public static DustMaterial DarkAsh = new DustMaterial(107, "dark_ash", 0x323232, MaterialIconSet.SAND, 1, of(new MaterialStack(Carbon, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Glyceryl = new FluidMaterial(106, "glyceryl", 0x009696, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Nitrogen, 3), new MaterialStack(Oxygen, 9)), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static FluidMaterial DistilledWater = new FluidMaterial(109, "distilled_water", 0x0000FF, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING);
    public static DustMaterial Ice = new DustMaterial(110, "ice", 0xC8C8FF, MaterialIconSet.ROUGH, 0, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_SMASHING | NO_RECYCLING | SMELT_INTO_FLUID | EXCLUDE_BLOCK_CRAFTING_RECIPES | DISABLE_DECOMPOSITION);
    public static FluidMaterial TitaniumTetrachloride = new FluidMaterial(108, "titanium_tetrachloride", 0xD40D5C, MaterialIconSet.FLUID, of(new MaterialStack(Titanium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Chlorine, 2)), DISABLE_DECOMPOSITION);
    public static DustMaterial MagnesiumChloride = new DustMaterial(111, "magnesium_chloride", 0xD40D5C, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Chlorine, 2)), 0);
    public static FluidMaterial NitroCarbon = new FluidMaterial(112, "nitro_carbon", 0x004B64, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Carbon, 1)), 0);
    public static FluidMaterial NitrogenDioxide = new FluidMaterial(113, "nitrogen_dioxide", 0x64AFFF, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 2)), 0);
    public static DustMaterial Obsidian = new DustMaterial(114, "obsidian", 0x503264, MaterialIconSet.DULL, 3, of(new MaterialStack(Magnesium, 1), new MaterialStack(Iron, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 8)), NO_SMASHING | EXCLUDE_BLOCK_CRAFTING_RECIPES);
    public static DustMaterial Phosphate = new DustMaterial(115, "phosphate", 0xFFFF00, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Phosphorus, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE | EXPLOSIVE);
    public static IngotMaterial Plastic = new IngotMaterial(116, "plastic", 0xC8C8C8, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2)), GENERATE_PLATE | FLAMMABLE | NO_SMASHING | SMELT_INTO_FLUID);
    public static IngotMaterial Epoxid = new IngotMaterial(117, "epoxid", 0xC88C14, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), EXT2_METAL);
    public static DustMaterial Silicone = new DustMaterial(118, "silicone", 0xDCDCDC, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 1)), GENERATE_PLATE | FLAMMABLE | NO_SMASHING | SMELT_INTO_FLUID);
    public static IngotMaterial Polycaprolactam = new IngotMaterial(119, "polycaprolactam", 0x323232, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 11), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)), GENERATE_PLATE);
    public static IngotMaterial Polytetrafluoroethylene = new IngotMaterial(120, "polytetrafluoroethylene", 0x646464, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)), GENERATE_PLATE | SMELT_INTO_FLUID | NO_WORKING, 2.0f, 256);
    public static DustMaterial Pumice = new DustMaterial(121, "pumice", 0xE6B9B9, MaterialIconSet.PAPER, 2, of(new MaterialStack(Stone, 1)), 0);
    public static IngotMaterial Rubber = new IngotMaterial(123, "rubber", 0x151515, MaterialIconSet.DULL, 0, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), GENERATE_PLATE | GENERATE_GEAR | GENERATE_RING | GENERATE_BOLT_SCREW | FLAMMABLE | NO_SMASHING | GENERATE_RING | NO_WORKING, 2.0f, 128);
    public static DustMaterial RawRubber = new DustMaterial(124, "raw_rubber", 0xCCC789, MaterialIconSet.SAND, 0, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), 0);
    public static FluidMaterial SodiumPersulfate = new FluidMaterial(125, "sodium_persulfate", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Sodium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), 0);
    public static FluidMaterial SodiumSulfide = new FluidMaterial(127, "sodium_sulfide", 0xAAAA00, MaterialIconSet.FLUID, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1)), 0);
    public static FluidMaterial HydrogenSulfide = new FluidMaterial(126, "hydrogen_sulfide", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1)), 0);
    public static FluidMaterial Steam = new FluidMaterial(128, "steam", 0xFFFFFF, MaterialIconSet.GAS, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING | GENERATE_FLUID_BLOCK);
    public static FluidMaterial Epichlorhydrin = new FluidMaterial(129, "epichlorhydrin", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Chlorine, 1), new MaterialStack(Oxygen, 1)), 0);
    public static FluidMaterial NitricAcid = new FluidMaterial(130, "nitric_acid", 0xCCCC00, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)), 0);
    public static DustMaterial Brick = new DustMaterial(355, "brick", 0xB75A40, MaterialIconSet.FINE, 1, of(new MaterialStack(Clay, 1)), EXCLUDE_BLOCK_CRAFTING_RECIPES | DECOMPOSITION_BY_CENTRIFUGING);
    public static DustMaterial Fireclay = new DustMaterial(356, "fireclay", 0x928073, MaterialIconSet.FINE, 2, of(new MaterialStack(Clay, 1), new MaterialStack(Brick, 1)), DECOMPOSITION_BY_CENTRIFUGING);
    public static GemMaterial Coke = new GemMaterial(357, "coke", 0x666666, MaterialIconSet.LIGNITE, 1, of(new MaterialStack(Carbon, 1)), FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    
    /**
     * Fuels/Oils
     */    
    public static FluidMaterial Oil = new FluidMaterial(131, "oil", 0x666666, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial OilHeavy = new FluidMaterial(132, "oil_heavy", 0x666666, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial OilMedium = new FluidMaterial(133, "oil_medium", 0x666666, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial OilLight = new FluidMaterial(134, "oil_light", 0x666666, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial NaturalGas = new FluidMaterial(135, "natural_gas", 0xFFFFFF, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_FLUID_BLOCK);
    public static FluidMaterial SulfuricGas = new FluidMaterial(136, "sulfuric_gas", 0xFFFFFF, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_FLUID_BLOCK);
    public static FluidMaterial Gas = new FluidMaterial(137, "gas", 0xFFFFFF, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_FLUID_BLOCK);
    public static FluidMaterial SulfuricNaphtha = new FluidMaterial(138, "sulfuric_naphtha", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial SulfuricLightFuel = new FluidMaterial(139, "sulfuric_light_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial SulfuricHeavyFuel = new FluidMaterial(140, "sulfuric_heavy_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Naphtha = new FluidMaterial(141, "naphtha", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial LightFuel = new FluidMaterial(142, "light_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial HeavyFuel = new FluidMaterial(143, "heavy_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial LPG = new FluidMaterial(144, "lpg", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial CrackedLightFuel = new FluidMaterial(145, "cracked_light_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial CrackedHeavyFuel = new FluidMaterial(146, "cracked_heavy_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Toluene = new FluidMaterial(147, "toluene", 0xFFFFFF, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial NitroFuel = new FluidMaterial(148, "nitro_fuel", 0xC8FF00, MaterialIconSet.FLUID, of(new MaterialStack(Glyceryl, 1), new MaterialStack(Fuel, 4)), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static FluidMaterial SulfuricAcid = new FluidMaterial(149, "sulfuric_acid", 0xFF8000, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), 0);
    public static IngotMaterial Graphite = new IngotMaterial(152, "graphite", 0x808080, MaterialIconSet.DULL, 2, of(), GENERATE_PLATE | GENERATE_ORE | NO_SMASHING |NO_SMELTING |FLAMMABLE);
    public static IngotMaterial Graphene = new IngotMaterial(153, "graphene", 0x808080, MaterialIconSet.SHINY, 2, of(), GENERATE_PLATE);

    /**
     * Random Dusts, good for separation
     */
    public static DustMaterial Phosphor = new DustMaterial(155, "phosphor", 0xFFFF00, MaterialIconSet.FLINT, 2, of(new MaterialStack(Calcium, 3), new MaterialStack(Phosphate, 2)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE | EXPLOSIVE);
    public static DustMaterial Sugar = new DustMaterial(156, "sugar", 0xFAFAFA, MaterialIconSet.SAND, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 25)), 0);
    public static DustMaterial PotassiumFeldspar = new DustMaterial(157, "potassium_feldspar", 0x782828, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 8)), 0);
    public static DustMaterial Pollucite = new DustMaterial(158, "pollucite", 0xF0D2D2, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Caesium, 2), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 4), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Mirabilite = new DustMaterial(159, "mirabilite", 0xF0FAD2, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial Mica = new DustMaterial(160, "mica", 0xC3C3CD, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)), 0);
    public static DustMaterial Trona = new DustMaterial(161, "trona", 0x87875F, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Sodium, 3), new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static DustMaterial Barite = new DustMaterial(162, "barite", 0xE6EBFF, MaterialIconSet.DULL, 2, of(new MaterialStack(Barium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Gypsum = new DustMaterial(163, "gypsum", 0xE6E6FA, MaterialIconSet.FINE, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial Alunite = new DustMaterial(164, "alunite", 0xE1B441, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 14)), 0);
    public static DustMaterial Dolomite = new DustMaterial(165, "dolomite", 0xE1CDCD, MaterialIconSet.FLINT, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Oxygen, 6)), 0);
    public static DustMaterial Wollastonite = new DustMaterial(166, "wollastonite", 0xF0F0F0, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3)), 0);
    public static DustMaterial Zeolite = new DustMaterial(167, "zeolite", 0xF0E6E6, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Sodium, 1), new MaterialStack(Calcium, 4), new MaterialStack(Silicon, 27), new MaterialStack(Aluminium, 9), new MaterialStack(Water, 28), new MaterialStack(Oxygen, 72)), 0);
    public static DustMaterial Kyanite = new DustMaterial(168, "kyanite", 0x6E6EFA, MaterialIconSet.FLINT, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 5)), 0);
    public static DustMaterial Kaolinite = new DustMaterial(169, "kaolinite", 0xF5EBEB, MaterialIconSet.DULL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)), 0);
    public static DustMaterial Ash = new DustMaterial(171, "ash", 0x969696, MaterialIconSet.SAND, 1, of(new MaterialStack(Carbon, 1)), 0);

    /**
     * For processing + extra chemicals for playing with
     */
    public static DustMaterial SiliconDioxide = new DustMaterial(172, "silicon_dioxide", 0xC8C8C8, MaterialIconSet.QUARTZ, 1, of(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2)), NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static DustMaterial Quicklime = new DustMaterial(173, "quicklime", 8421504, MaterialIconSet.SAND, 1, of(new MaterialStack(Materials.Calcium, 1), new MaterialStack(Materials.Oxygen, 1)), 0);
    public static DustMaterial Urea = new DustMaterial(174, "urea", 15657130, MaterialIconSet.SAND, 1, of(new MaterialStack(Materials.Carbon, 1), new MaterialStack(Materials.Hydrogen, 4), new MaterialStack(Materials.Nitrogen, 2), new MaterialStack(Materials.Oxygen, 1)), DISABLE_DECOMPOSITION);
    public static DustMaterial SodiumCyanate = new DustMaterial(175, "sodium_cyanate", 14800746, MaterialIconSet.SAND, 1, of(new MaterialStack(Materials.Sodium, 1), new MaterialStack(Materials.Oxygen, 1), new MaterialStack(Materials.Carbon, 1), new MaterialStack(Materials.Nitrogen, 1)), DISABLE_DECOMPOSITION);
    public static DustMaterial SodiumHydroxide = new DustMaterial(176, "sodium_hydroxide", 6466, MaterialIconSet.SAND, 1, of(new MaterialStack(Materials.Sodium, 1), new MaterialStack(Materials.Oxygen, 1), new MaterialStack(Materials.Hydrogen, 1)), DISABLE_DECOMPOSITION);
    public static DustMaterial SodiumBicarbonate = new DustMaterial(177, "sodium_bicarbonate", 16316671, MaterialIconSet.SAND, 1, of(new MaterialStack(Materials.Carbon, 1), new MaterialStack(Materials.Hydrogen, 1), new MaterialStack(Materials.Sodium, 1), new MaterialStack(Materials.Oxygen, 3)), DISABLE_DECOMPOSITION);
    public static DustMaterial SodiumCarbonate = new DustMaterial(178, "sodium_carbonate", 16316671, MaterialIconSet.SAND, 1, of(new MaterialStack(Materials.Carbon, 1), new MaterialStack(Materials.Hydrogen, 1), new MaterialStack(Materials.Sodium, 1), new MaterialStack(Materials.Oxygen, 3)), DISABLE_DECOMPOSITION);

    public static IngotMaterial Acrylic = new IngotMaterial(179, "acrylic", 15657130, MaterialIconSet.DULL, 1, of(new MaterialStack(Materials.Oxygen, 2), new MaterialStack(Materials.Carbon, 5), new MaterialStack(Materials.Hydrogen, 8)), GENERATE_PLATE | SMELT_INTO_FLUID | DISABLE_DECOMPOSITION);
    		
    public static FluidMaterial Ammonia = new FluidMaterial(180, "ammonia", 4011371, MaterialIconSet.GAS, of(new MaterialStack(Materials.Nitrogen, 1), new MaterialStack(Materials.Hydrogen, 3)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial NitricOxide = new FluidMaterial(181, "nitric_oxide", 6790328, MaterialIconSet.GAS, of(new MaterialStack(Materials.Nitrogen, 1), new MaterialStack(Materials.Oxygen, 1)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Dimethylamine = new FluidMaterial(182, "dimethylamine", 4931417, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 2), new MaterialStack(Materials.Hydrogen, 7), new MaterialStack(Materials.Nitrogen, 1)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Methanol = new FluidMaterial(183, "methanol", 8941584, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 1), new MaterialStack(Materials.Hydrogen, 4), new MaterialStack(Materials.Oxygen, 1)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial HypochlorousAcid = new FluidMaterial(184, "hypochlorous_acid", 6123637, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Hydrogen, 1), new MaterialStack(Materials.Chlorine, 1), new MaterialStack(Materials.Oxygen, 1)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING); 
    public static FluidMaterial Chloramine = new FluidMaterial(185, "chloramine", 4031340, MaterialIconSet.GAS, of(new MaterialStack(Materials.Nitrogen, 1), new MaterialStack(Materials.Hydrogen, 2), new MaterialStack(Materials.Chlorine, 1)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial NitrogenTetroxide = new FluidMaterial(186, "dinitrogen_tetroxide", 998766, MaterialIconSet.GAS, of(new MaterialStack(Materials.Nitrogen, 2), new MaterialStack(Materials.Oxygen, 4)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial HydrochloricAcid = new FluidMaterial(187, "hydrochloric_acid", 9477273, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Hydrogen, 1), new MaterialStack(Materials.Chlorine, 1)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial AceticAcid = new FluidMaterial(188, "acetic_acid", 10260096, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 2), new MaterialStack(Materials.Hydrogen, 4), new MaterialStack(Materials.Oxygen, 2)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial FermentedBiomass = new FluidMaterial(189, "fermented_biomass", 4147981, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial WoodVinegar = new FluidMaterial(190, "wood_vinegar", 10832655, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial WoodTar = new FluidMaterial(191, "wood_tar", 2957592, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial BioDiesel = new FluidMaterial(192, "bio_diesel", 12806415, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial Acetone = new FluidMaterial(193, "acetone", 9342606, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 3), new MaterialStack(Materials.Hydrogen, 6), new MaterialStack(Materials.Oxygen, 1)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial SulfurTrioxide = new FluidMaterial(194, "sulfur_trioxide", 8618781, MaterialIconSet.GAS, of(new MaterialStack(Materials.Sulfur, 1), new MaterialStack(Materials.Oxygen, 3)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial SulfurDioxide = new FluidMaterial(195, "sulfur_dioxide", 10263584, MaterialIconSet.GAS, of(new MaterialStack(Materials.Sulfur, 1), new MaterialStack(Materials.Oxygen, 2)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Glycerol = new FluidMaterial(196, "glycerol", 7384944, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 3), new MaterialStack(Materials.Hydrogen, 8), new MaterialStack(Materials.Oxygen, 3)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial DilutedSulfuricAcid = new FluidMaterial(197, "diluted_sulfuric_acid", 9987366, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Hydrogen, 2), new MaterialStack(Materials.Sulfur, 1), new MaterialStack(Materials.Oxygen, 4)), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial DilutedHydrochloricAcid = new FluidMaterial(198, "diluted_hydrochloric_acid", 8160900, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Hydrogen, 1), new MaterialStack(Materials.Chlorine, 1)), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial Ethylene = new FluidMaterial(199, "ethene", 11382189, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 2), new MaterialStack(Materials.Hydrogen, 4)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial MethylAcetate = new FluidMaterial(200, "methyl_acetate", 12427150, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 3), new MaterialStack(Materials.Hydrogen, 6), new MaterialStack(Materials.Oxygen, 2)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial CalciumAcetate = new FluidMaterial(201, "calcium_acetate", 11444113, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Calcium, 1), new MaterialStack(Materials.Carbon, 2), new MaterialStack(Materials.Oxygen, 4), new MaterialStack(Materials.Hydrogen, 6)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Ethenone = new FluidMaterial(202, "ethenone", 1776449, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 2), new MaterialStack(Materials.Hydrogen, 2), new MaterialStack(Materials.Oxygen, 1)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial BisphenolA = new FluidMaterial(204, "bisphenol_a", 10848014, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 15), new MaterialStack(Materials.Hydrogen, 16), new MaterialStack(Materials.Oxygen, 2)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial AllylChloride = new FluidMaterial(205, "allyl_chloride", 7450250, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Carbon, 3), new MaterialStack(Materials.Hydrogen, 5), new MaterialStack(Materials.Chlorine, 1)), GENERATE_FLUID_BLOCK | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Propane = new FluidMaterial(206, "propane", 12890952, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 3), new MaterialStack(Materials.Hydrogen, 8)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Propene = new FluidMaterial(207, "propene", 12954956, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 3), new MaterialStack(Materials.Hydrogen, 6)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Ethane = new FluidMaterial(208, "ethane", 10329540, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 2), new MaterialStack(Materials.Hydrogen, 6)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Butene = new FluidMaterial(209, "butene", 10700561, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 4), new MaterialStack(Materials.Hydrogen, 8)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial Butane = new FluidMaterial(203, "butane", 9385508, MaterialIconSet.GAS, of(new MaterialStack(Materials.Carbon, 4), new MaterialStack(Materials.Hydrogen, 10)), STATE_GAS | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial SodiumCyanide = new FluidMaterial(210, "sodium_cyanide", 16316671, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Sodium, 1), new MaterialStack(Materials.Carbon, 1), new MaterialStack(Materials.Nitrogen, 1)), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial AcetoneCyanohydrin = new FluidMaterial(211, "acetone_cyanohydrin", 12092939, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Oxygen, 1), new MaterialStack(Materials.Nitrogen, 1), new MaterialStack(Materials.Carbon, 4), new MaterialStack(Materials.Hydrogen, 7)), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static FluidMaterial MethylMethacrylate = new FluidMaterial(212, "methyl_methacrylate", 16777215, MaterialIconSet.FLUID, of(new MaterialStack(Materials.Oxygen, 2), new MaterialStack(Materials.Carbon, 5), new MaterialStack(Materials.Hydrogen, 8)), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
   
    /**
     * Ore Materials
     */
    public static DustMaterial Rutile = new DustMaterial(213, "rutile", 0xD40D5C, MaterialIconSet.DULL, 2, of(new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE | DISABLE_DECOMPOSITION);
    public static DustMaterial Ilmenite = new DustMaterial(214, "ilmenite", 0x463732, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE | DISABLE_DECOMPOSITION);
    public static DustMaterial Bauxite = new DustMaterial(215, "bauxite", 0xC86400, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Rutile, 2), new MaterialStack(Aluminium, 16), new MaterialStack(Hydrogen, 10), new MaterialStack(Oxygen, 11)), GENERATE_ORE);

    public static DustMaterial BandedIron = new DustMaterial(217, "banded_iron", 0x915A5A, MaterialIconSet.DULL, 1, of(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Taconite = new DustMaterial(218, "taconite", 16729344, MaterialIconSet.SAND, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE); 
    public static DustMaterial Siderite = new DustMaterial(219, "siderite", 15657130, MaterialIconSet.DULL, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Carbon, 1), new MaterialStack(Materials.Oxygen, 3)), GENERATE_ORE); 
    public static DustMaterial Geothite = new DustMaterial(220, "geothite", 15657130, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Carbon, 1), new MaterialStack(Materials.Silicon, 1), new MaterialStack(Materials.Oxygen, 3)), GENERATE_ORE);     	
    public static DustMaterial Hematite = new DustMaterial(221, "hematite", 9127187, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3)), GENERATE_ORE);     
    public static DustMaterial Pyrite = new DustMaterial(224, "pyrite", 0x967828, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    
    public static DustMaterial Magnetite = new DustMaterial(225, "magnetite", 0x1E1E1E, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 3), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial VanadiumMagnetite = new DustMaterial(226, "vanadium_magnetite", 0x23233C, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Magnetite, 1), new MaterialStack(Vanadium, 1)), GENERATE_ORE);

    public static DustMaterial Chalcopyrite = new DustMaterial(227, "chalcopyrite", 0xA07828, MaterialIconSet.DULL, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Malachite = new DustMaterial(228, "malachite", 0x055F05, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Copper, 2), new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 5)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Tetrahedrite = new DustMaterial(229, "tetrahedrite", 0xC82000, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Copper, 3), new MaterialStack(Antimony, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Iron, 1)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);

    public static DustMaterial Calcite = new DustMaterial(231, "calcite", 0xFAE6DC, MaterialIconSet.DULL, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    
    public static DustMaterial Cassiterite = new DustMaterial(232, "cassiterite", 0xDCDCDC, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    
    public static DustMaterial Spessartine = new DustMaterial(234, "spessartine", 0xFF6464, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Manganese, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Sphalerite = new DustMaterial(235, "sphalerite", 0xFFFFFF, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Stibnite = new DustMaterial(236, "stibnite", 0x464646, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Antimony, 2), new MaterialStack(Sulfur, 3)), GENERATE_ORE);
    public static DustMaterial Uraninite = new DustMaterial(237, "uraninite", 0x232323, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Uranium, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Chromite = new DustMaterial(238, "chromite", 0x23140F, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Chrome, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE);    
    public static DustMaterial Biotite = new DustMaterial(239, "biotite", 0x141E14, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 3), new MaterialStack(Aluminium, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 10)), GENERATE_ORE);
    public static DustMaterial Realgar = new DustMaterial(240, "realgar", 0x8C6464, MaterialIconSet.DULL, 2, of(new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur, 4)), GENERATE_ORE);
    public static DustMaterial Pentlandite = new DustMaterial(241, "pentlandite", 0xA59605, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Nickel, 9), new MaterialStack(Sulfur, 8)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Garnierite = new DustMaterial(242, "garnierite", 0x32C846, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Nickel, 1), new MaterialStack(Oxygen, 1)), GENERATE_ORE);
    public static DustMaterial Spodumene = new DustMaterial(243, "spodumene", 0xBEAAAA, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static DustMaterial Lepidolite = new DustMaterial(244, "lepidolite", 0xF0328C, MaterialIconSet.FINE, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Lithium, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)), GENERATE_ORE);
    public static DustMaterial Pitchblende = new DustMaterial(245, "pitchblende", 0xC8D200, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Uraninite, 3), new MaterialStack(Thorium, 1), new MaterialStack(Lead, 1)), GENERATE_ORE);
    public static DustMaterial Tungstate = new DustMaterial(246, "tungstate", 0x373223, MaterialIconSet.DULL, 3, of(new MaterialStack(Tungsten, 1), new MaterialStack(Lithium, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE | DECOMPOSITION_REQUIRES_HYDROGEN, null);
    public static DustMaterial Salt = new DustMaterial(247, "salt", 0xFFFFFF, MaterialIconSet.SAND, 1, of(new MaterialStack(Sodium, 1), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING);
    public static DustMaterial Saltpeter = new DustMaterial(248, "saltpeter", 0xE6E6E6, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE | NO_SMASHING |NO_SMELTING |FLAMMABLE);
    public static DustMaterial Scheelite = new DustMaterial(249, "scheelite", 0xC88C14, MaterialIconSet.DULL, 3, of(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE | DECOMPOSITION_REQUIRES_HYDROGEN);
    public static DustMaterial Galena = new DustMaterial(250, "galena", 0x643C64, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Lead, 3), new MaterialStack(Silver, 3), new MaterialStack(Sulfur, 2)), GENERATE_ORE | NO_SMELTING);
    public static DustMaterial Talc = new DustMaterial(251, "talc", 0x5AB45A, MaterialIconSet.FINE, 2, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Soapstone = new DustMaterial(252, "soapstone", 0x5F915F, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Pyrolusite = new DustMaterial(253, "pyrolusite", 0x9696AA, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Manganese, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Pyrope = new DustMaterial(254, "pyrope", 0x783264, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Wulfenite = new DustMaterial(255, "wulfenite", 0xFF8000, MaterialIconSet.DULL, 3, of(new MaterialStack(Lead, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static IngotMaterial Invar = new IngotMaterial(256, "invar", 0xB4B478, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 2), new MaterialStack(Nickel, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING, 6.0F, 256);
    public static DustMaterial Magnesite = new DustMaterial(257, "magnesite", 0xFAFAB4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Molybdenite = new DustMaterial(258, "molybdenite", 0x191919, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Molybdenum, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE);
    public static DustMaterial Cobaltite = new DustMaterial(259, "cobaltite", 0x5050FA, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Cobalt, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE);
    public static DustMaterial Cooperite = new DustMaterial(260, "cooperite", 0xFFFFC8, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Platinum, 3), new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Palladium, 1)), GENERATE_ORE);
    public static DustMaterial Powellite = new DustMaterial(261, "powellite", 0xFFFF00, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial RockSalt = new DustMaterial(262, "rock_salt", 0xF0C8C8, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING);
    public static DustMaterial Grossular = new DustMaterial(263, "grossular", 0xC86400, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Calcium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    
    public static DustMaterial Oilsands = new DustMaterial(387, "oilsands", 0x0A0A0A, MaterialIconSet.SAND, 1, of(new MaterialStack(Oil, 1L)), GENERATE_ORE);
    
    /**
     * Gem Materials
     */
    public static GemMaterial Apatite = new GemMaterial(264, "apatite", 0xC8C8FF, MaterialIconSet.EMERALD, 1, of(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Chlorine, 1)), STD_GEM | GENERATE_ROD | GENERATE_BOLT_SCREW | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static GemMaterial Almandine = new GemMaterial(265, "almandine", 0xFF0000, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Aluminium, 2), new MaterialStack(Iron, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), STD_GEM);
    public static GemMaterial Aquamarine = new GemMaterial(266, "aquamarine", 0x7FFFD4, MaterialIconSet.GEM_HORIZONTAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Beryllium, 2), new MaterialStack(Oxygen, 18)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);    
    public static GemMaterial BlueTopaz = new GemMaterial(269, "blue_topaz", 0x0000FF, MaterialIconSet.GEM_HORIZONTAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);
    public static GemMaterial Cinnabar = new GemMaterial(267, "cinnabar", 0x960000, MaterialIconSet.EMERALD, 1, of(new MaterialStack(Mercury, 1), new MaterialStack(Sulfur, 1)), STD_GEM | CRYSTALLISABLE);
    public static GemMaterial Diamond = new GemMaterial(268, "diamond", 0xC8FFFF, MaterialIconSet.DIAMOND, 3, of(new MaterialStack(Carbon, 1)), STD_GEM | GENERATE_BOLT_SCREW | GENERATE_LENSE | GENERATE_GEAR | NO_SMASHING | NO_SMELTING | FLAMMABLE | GENERATE_ORE, 8.0F, 1280);
    public static GemMaterial Emerald = new GemMaterial(270, "emerald", 0x50FF50, MaterialIconSet.EMERALD, 3, of(new MaterialStack(Beryllium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Oxygen, 18)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);
    public static GemMaterial Peridot = new GemMaterial(271, "peridot", 0x64C882, MaterialIconSet.GEM_HORIZONTAL, 2, of(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 4)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256); //Old form: Green Sapphire
    public static GemMaterial Lazurite = new GemMaterial(272, "lazurite", 0x6478FF, MaterialIconSet.LAPIS, 1, of(new MaterialStack(Aluminium, 6), new MaterialStack(Silicon, 6), new MaterialStack(Calcium, 8), new MaterialStack(Sodium, 8)), STD_GEM | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | DECOMPOSITION_BY_ELECTROLYZING);   
    public static GemMaterial Ruby = new GemMaterial(273, "ruby", 0xBD4949, MaterialIconSet.RUBY, 2, of(new MaterialStack(Chrome, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);
    public static GemMaterial Sapphire = new GemMaterial(274, "sapphire", 0x6464C8, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING, null, 7.0F, 256);
    public static GemMaterial Sodalite = new GemMaterial(275, "sodalite", 0x1414FF, MaterialIconSet.LAPIS, 1, of(new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Sodium, 4), new MaterialStack(Chlorine, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | DECOMPOSITION_BY_ELECTROLYZING);
    public static GemMaterial Tanzanite = new GemMaterial(276, "tanzanite", 0x4000C8, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Calcium, 2), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 13)), STD_GEM | NO_SMASHING | NO_SMELTING, null, 7.0F, 256);
    public static GemMaterial Topaz = new GemMaterial(277, "topaz", 0xFF8000, MaterialIconSet.GEM_HORIZONTAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), STD_GEM | NO_SMASHING | NO_SMELTING, null, 7.0F, 256);
    public static GemMaterial NetherQuartz = new GemMaterial(278, "nether_quartz", 0xE6D2D2, MaterialIconSet.QUARTZ, 1, of(), STD_SOLID | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | STD_GEM, null, 1.0F, 32);
    public static GemMaterial CertusQuartz = new GemMaterial(279, "certus_quartz", 0xD2D2E6, MaterialIconSet.QUARTZ, 1, of(), STD_SOLID | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | STD_GEM, null, 5.0F, 32);
    public static GemMaterial ChargedCertusQuartz = new GemMaterial(279, "charged_certus_quartz", 0xD2D2E6, MaterialIconSet.QUARTZ, 1, of(), STD_SOLID | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | STD_GEM, null, 7.0F, 32);
    public static GemMaterial Quartzite = new GemMaterial(280, "quartzite", 0xD2E6D2, MaterialIconSet.QUARTZ, 2, of(), NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | STD_GEM);
    public static GemMaterial Jasper = new GemMaterial(281, "jasper", 0xC85050, MaterialIconSet.EMERALD, 2, of(), STD_GEM | NO_SMASHING | NO_SMELTING);
    public static GemMaterial Olivine = new GemMaterial(282, "olivine", 0x66FF66, MaterialIconSet.RUBY, 2, of(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 1), new MaterialStack(SiliconDioxide, 2)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);
    public static GemMaterial Opal = new GemMaterial(283, "opal", 0x0000FF, MaterialIconSet.OPAL, 2, of(new MaterialStack(SiliconDioxide, 1)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);
    public static GemMaterial Amethyst = new GemMaterial(284, "amethyst", 0xD232D2, MaterialIconSet.RUBY, 2, of(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Iron, 1)), STD_GEM | NO_SMASHING | NO_SMELTING, 7.0F, 256);
    public static GemMaterial Lapis = new GemMaterial(388, "lapis", 0x4646DC, MaterialIconSet.LAPIS, 1, of(new MaterialStack(Lazurite, 12), new MaterialStack(Sodalite, 2), new MaterialStack(Pyrite, 1), new MaterialStack(Calcite, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | NO_WORKING | DECOMPOSITION_BY_ELECTROLYZING);
    public static GemMaterial Vinteum = new GemMaterial(389, "vinteum", 0x64C8FF, MaterialIconSet.EMERALD, 3, of(), STD_GEM | NO_SMASHING | NO_SMELTING, 10.0F, 128);
    public static GemMaterial Bastnasite = new GemMaterial(285, "bastnasite", 0xC71585, MaterialIconSet.OPAL, 2, of(new MaterialStack(Yttrium, 1), new MaterialStack(Cerium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Fluorine, 1), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING | NO_WORKING);
    public static GemMaterial Xenotime = new GemMaterial(222, "xenotime", 0xC62B0B, MaterialIconSet.GEM_HORIZONTAL, 1, of(new MaterialStack(Yttrium, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
       
    /**
     * Second Degree Compounds
     */
    public static DustMaterial Fiberglass = new DustMaterial(290, "fiberglass", 0xFFFFFF, MaterialIconSet.GLASS, 0, of(), GENERATE_PLATE | NO_SMASHING | NO_RECYCLING | SMELT_INTO_FLUID | EXCLUDE_BLOCK_CRAFTING_RECIPES);
    public static DustMaterial Niter = new DustMaterial(293, "niter", 0xFFC8C8, MaterialIconSet.FLINT, 0, of(new MaterialStack(Saltpeter, 1)), NO_SMASHING | NO_SMELTING);
    
    /**
     * Vanilla Materials
     */
    public static DustMaterial Redstone = new DustMaterial(291, "redstone", 0xC80000, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Boron, 1), new MaterialStack(Phosphorus, 1)), GENERATE_PLATE | GENERATE_ORE | NO_SMASHING | SMELT_INTO_FLUID);
    public static DustMaterial Blaze = new DustMaterial(294, "blaze", 0xFFC800, MaterialIconSet.POWDER, 1, of(new MaterialStack(DarkAsh, 1), new MaterialStack(Sulfur, 1)), NO_SMELTING | MORTAR_GRINDABLE | BURNING);
    public static GemMaterial EnderPearl = new GemMaterial(295, "ender_pearl", 0x6CDCC8, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Beryllium, 1), new MaterialStack(Potassium, 4), new MaterialStack(Nitrogen, 5)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 16);
    public static GemMaterial EnderEye = new GemMaterial(296, "ender_eye", 0x66FF66, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(EnderPearl, 1), new MaterialStack(Blaze, 1)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 16);
    public static RoughSolidMaterial Flint = new RoughSolidMaterial(297, "flint", 0x002040, MaterialIconSet.FLINT, 1, of(new MaterialStack(SiliconDioxide, 1)), NO_SMASHING | MORTAR_GRINDABLE, 6.0f, 55, () -> OrePrefix.gem);
    public static DustMaterial Glass = new DustMaterial(289, "glass", 0xFFFFFF, MaterialIconSet.GLASS, 0, of(new MaterialStack(SiliconDioxide, 1)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_RECYCLING | SMELT_INTO_FLUID | EXCLUDE_BLOCK_CRAFTING_RECIPES);

    /**
     * Alloys
     */
    public static IngotMaterial StainlessSteel = new IngotMaterial(298, "stainless_steel", 0xC8C8DC, MaterialIconSet.SHINY, 2, of(new MaterialStack(Iron, 6), new MaterialStack(Chrome, 1), new MaterialStack(Manganese, 1), new MaterialStack(Nickel, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR, null, 7.0F, 480, 1700);
    public static IngotMaterial Steel = new IngotMaterial(299, "steel", 0x505050, MaterialIconSet.DULL, 2, of(new MaterialStack(Iron, 50), new MaterialStack(Carbon, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_DENSE | DISABLE_DECOMPOSITION, null, 6.0F, 512, 1000);
    public static IngotMaterial SoulforgedSteel = new IngotMaterial(300, "soulforged_steel", 0x505050, MaterialIconSet.DULL, 2, of(new MaterialStack(Iron, 50), new MaterialStack(Carbon, 1)), DISABLE_DECOMPOSITION);
    public static IngotMaterial RedAlloy = new IngotMaterial(301, "red_alloy", 0xC80000, MaterialIconSet.DULL, 0, of(new MaterialStack(Copper, 1), new MaterialStack(Redstone, 4)), GENERATE_PLATE | GENERATE_FINE_WIRE);
    public static IngotMaterial SterlingSilver = new IngotMaterial(302, "sterling_silver", 0xFADCE1, MaterialIconSet.SHINY, 2, of(new MaterialStack(Copper, 1), new MaterialStack(Silver, 4)), EXT2_METAL, null, 13.0F, 128, 1700);
    public static IngotMaterial RoseGold = new IngotMaterial(303, "rose_gold", 0xFFE61E, MaterialIconSet.SHINY, 2, of(new MaterialStack(Copper, 1), new MaterialStack(Gold, 4)), EXT2_METAL, null, 14.0F, 128, 1600);
    public static IngotMaterial BismuthBronze = new IngotMaterial(304, "bismuth_bronze", 0x647D7D, MaterialIconSet.DULL, 2, of(new MaterialStack(Bismuth, 1), new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), EXT2_METAL, null, 8.0F, 256, 1100);
    public static IngotMaterial DamascusSteel = new IngotMaterial(305, "damascus_steel", 0x6E6E6E, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Steel, 1)), EXT_METAL, null, 8.0F, 1280, 1500);
    public static IngotMaterial TungstenSteel = new IngotMaterial(306, "tungsten_steel", 0x6464A0, MaterialIconSet.METALLIC, 4, of(new MaterialStack(Steel, 1), new MaterialStack(Tungsten, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_DENSE, null, 8.0F, 2560, 3000);
    public static IngotMaterial TungstenCarbide = new IngotMaterial(307, "tungsten_carbide", 0x330066, MaterialIconSet.METALLIC, 4, of(new MaterialStack(Tungsten, 1), new MaterialStack(Carbon, 1)), EXT2_METAL, null, 14.0F, 1280, 2460);
    public static IngotMaterial VanadiumSteel = new IngotMaterial(308, "vanadium_steel", 0xC0C0C0, MaterialIconSet.METALLIC, 3, of(new MaterialStack(Vanadium, 1), new MaterialStack(Chrome, 1), new MaterialStack(Steel, 7)), EXT2_METAL, null, 3.0F, 1920, 1453);
    public static IngotMaterial HSSG = new IngotMaterial(309, "hssg", 0x999900, MaterialIconSet.METALLIC, 3, of(new MaterialStack(TungstenSteel, 5), new MaterialStack(Chrome, 1), new MaterialStack(Molybdenum, 2), new MaterialStack(Vanadium, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD, null, 10.0F, 4000, 4500);
    public static IngotMaterial HSSE = new IngotMaterial(310, "hsse", 0x336600, MaterialIconSet.METALLIC, 4, of(new MaterialStack(HSSG, 6), new MaterialStack(Cobalt, 1), new MaterialStack(Manganese, 1), new MaterialStack(Silicon, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD, null, 10.0F, 5120, 5400);
    public static IngotMaterial HSSS = new IngotMaterial(314, "hsss", 0x660033, MaterialIconSet.METALLIC, 4, of(new MaterialStack(HSSG, 6), new MaterialStack(Iridium, 2), new MaterialStack(Osmium, 1)), EXT2_METAL | GENERATE_GEAR, null, 14.0F, 3000, 5400);
    public static IngotMaterial IronMagnetic = new IngotMaterial(312, "iron_magnetic", 0xC8C8C8, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE, null, 6.0F, 256);
    public static IngotMaterial SteelMagnetic = new IngotMaterial(311, "steel_magnetic", 0x808080, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Steel, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | MORTAR_GRINDABLE, null, 6.0F, 512, 1000);
    public static IngotMaterial NeodymiumMagnetic = new IngotMaterial(313, "neodymium_magnetic", 0x646464, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Neodymium, 1)), EXT2_METAL | GENERATE_LONG_ROD, null, 7.0F, 512, 1297);
    public static IngotMaterial Osmiridium = new IngotMaterial(315, "osmiridium", 0x6464FF, MaterialIconSet.METALLIC, 3, of(new MaterialStack(Iridium, 3), new MaterialStack(Osmium, 1)), EXT2_METAL, null, 7.0F, 1600, 2500);
    public static IngotMaterial SolderingAlloy = new IngotMaterial(316, "soldering_alloy", 0xDCDCE6, MaterialIconSet.DULL, 1, of(new MaterialStack(Tin, 9), new MaterialStack(Antimony, 1)), EXT_METAL | GENERATE_FINE_WIRE, null);
    public static IngotMaterial Nichrome = new IngotMaterial(317, "nichrome", 0xCDCEF6, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Nickel, 4), new MaterialStack(Chrome, 1)), EXT_METAL, null, 6.0F, 64, 2700);
    public static IngotMaterial NiobiumTitanium = new IngotMaterial(319, "niobium_titanium", 0x1D1D29, MaterialIconSet.DULL, 2, of(new MaterialStack(Niobium, 1), new MaterialStack(Titanium, 1)), EXT2_METAL, null, 4500);
    public static IngotMaterial VanadiumGallium = new IngotMaterial(320, "vanadium_gallium", 0x80808C, MaterialIconSet.SHINY, 2, of(new MaterialStack(Vanadium, 3), new MaterialStack(Gallium, 1)), STD_METAL | GENERATE_FOIL | GENERATE_ROD, null, 4500);
    public static IngotMaterial WroughtIron = new IngotMaterial(321, "wrought_iron", 0xC8B4B4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_LONG_ROD, null, 6.0F, 384);
    public static IngotMaterial Electrum = new IngotMaterial(322, "electrum", 0xFFFF64, MaterialIconSet.SHINY, 2, of(new MaterialStack(Silver, 1), new MaterialStack(Gold, 1)), EXT2_METAL | MORTAR_GRINDABLE, 12.0F, 64);
    public static IngotMaterial PigIron = new IngotMaterial(324, "pig_iron", 0xC8B4B4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1)), EXT_METAL | GENERATE_RING, 6.0F, 384);
    public static IngotMaterial AnnealedCopper = new IngotMaterial(326, "annealed_copper", 0xFF7814, MaterialIconSet.SHINY, 2, of(new MaterialStack(Copper, 1)), EXT2_METAL | MORTAR_GRINDABLE);
    public static IngotMaterial BatteryAlloy = new IngotMaterial(327, "battery_alloy", 0x9C7CA0, MaterialIconSet.DULL, 1, of(new MaterialStack(Lead, 4), new MaterialStack(Antimony, 1)), EXT_METAL);
    public static IngotMaterial Kanthal = new IngotMaterial(328, "kanthal", 0xC2D2DF, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Chrome, 1)), EXT_METAL, null, 6.0F, 64, 1800);
    public static IngotMaterial Magnalium = new IngotMaterial(329, "magnalium", 0xC8BEFF, MaterialIconSet.DULL, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2)), EXT2_METAL | GENERATE_LONG_ROD, 6.0F, 256);
    public static IngotMaterial Cupronickel = new IngotMaterial(330, "cupronickel", 0xE39680, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1)), EXT_METAL,  6.0F, 64);
    public static IngotMaterial YttriumBariumCuprate = new IngotMaterial(331, "yttrium_barium_cuprate", 0x504046, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Yttrium, 1), new MaterialStack(Barium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 7)), EXT_METAL | GENERATE_FOIL, null, 4500);
    public static IngotMaterial Brass = new IngotMaterial(332, "brass", 0xFFB400, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING, 7.0F, 96);
    public static IngotMaterial Bronze = new IngotMaterial(333, "bronze", 0xFF8000, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Tin, 1), new MaterialStack(Copper, 3)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR, 6.0F, 192);
    public static IngotMaterial Ultimet = new IngotMaterial(150, "ultimet", 0xB4B4E6, MaterialIconSet.SHINY, 4, of(new MaterialStack(Cobalt, 5), new MaterialStack(Chrome, 2), new MaterialStack(Nickel, 1), new MaterialStack(Molybdenum, 1)), EXT2_METAL, null, 9.0F, 2048, 2700);

    /**
     * Stones
     */
    //public static DustMaterial Basalt = new DustMaterial(335, "basalt", 0x1E1414, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Olivine, 1), new MaterialStack(Calcite, 3), new MaterialStack(Flint, 8), new MaterialStack(DarkAsh, 4)), NO_SMASHING);
    public static DustMaterial Andesite = new DustMaterial(336, "andesite", 0xBEBEBE, MaterialIconSet.ROUGH, 2, of(), NO_SMASHING);
    public static DustMaterial Diorite = new DustMaterial(337, "diorite", 0xFFFFFF, MaterialIconSet.ROUGH, 2, of(), NO_SMASHING);
    //public static DustMaterial Marble = new DustMaterial(338, "marble", 0xC8C8C8, MaterialIconSet.FINE, 1, of(new MaterialStack(Magnesium, 1), new MaterialStack(Calcite, 7)), NO_SMASHING);

    /**
     * Coal and Related Items
     */
    public static GemMaterial Charcoal = new GemMaterial(339, "charcoal", 0x644646, MaterialIconSet.LIGNITE, 1, of(new MaterialStack(Carbon, 1)), FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static GemMaterial Coal = new GemMaterial(340, "coal", 0x464646, MaterialIconSet.LIGNITE, 1, of(new MaterialStack(Carbon, 1)), GENERATE_ORE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static GemMaterial Lignite = new GemMaterial(341, "lignite", 0x644646, MaterialIconSet.LIGNITE, 0, of(new MaterialStack(Carbon, 2), new MaterialStack(Water, 4), new MaterialStack(DarkAsh, 1)), GENERATE_ORE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
   
    /**
     * Foods
     */
    public static FluidMaterial Milk = new FluidMaterial(350, "milk", 0xFEFEFE, MaterialIconSet.FINE, of(), 0);
    public static FluidMaterial Honey = new FluidMaterial(342, "honey", 0xD2C800, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Cocoa = new DustMaterial(343, "cocoa", 0xBE5F00, MaterialIconSet.ROUGH, 0, of(), 0);
    public static DustMaterial Wheat = new DustMaterial(344, "wheat", 0xFFFFC4, MaterialIconSet.FINE, 0, of(), 0);
    
    /**
     * Magic + Fantasy Materials
     */
    
    public static IngotMaterial Naquadah = new IngotMaterial(347, "naquadah", 0x323232, MaterialIconSet.METALLIC, 4, of(), EXT_METAL | GENERATE_ORE, Element.Nq, 6.0F, 1280, 5400);
    public static IngotMaterial NaquadahAlloy = new IngotMaterial(348, "naquadah_alloy", 0x282828, MaterialIconSet.METALLIC, 5, of(new MaterialStack(Naquadah, 1), new MaterialStack(Osmiridium, 1)), EXT2_METAL, null, 8.0F, 5120, 7200);
    public static IngotMaterial NaquadahEnriched = new IngotMaterial(349, "naquadah_enriched", 0x282828, MaterialIconSet.METALLIC, 4, of(), EXT_METAL | GENERATE_ORE, null, 6.0F, 1280, 4500);
    
    public static IngotMaterial Adamantium = new IngotMaterial(500, "adamantium", 0xFFFF99, MaterialIconSet.METALLIC, 5, of(), EXT_METAL, null, 8.0F, 4096, 4096);

    
    public static IngotMaterial ManaInfused = new IngotMaterial(333, "mana_infused", 0x0000FF, MaterialIconSet.SHINY, 2, of(), EXT2_METAL); 
    
    //Astral Sorcery
    public static FluidMaterial Starlight = new DustMaterial(344, "starlight", 0xDCDCDC, MaterialIconSet.FLUID, 0, of(), 0);
    public static IngotMaterial Starmetal = new IngotMaterial(355, "starmetal", 0x0000B3, MaterialIconSet.SHINY, 0, of(), 0);
    
    //Botania
    public static RoughSolidMaterial Livingwood = new RoughSolidMaterial(356, "livingwood", 0x896727, MaterialIconSet.WOOD, 1, of(), STD_SOLID | FLAMMABLE | NO_SMELTING | GENERATE_LONG_ROD | GENERATE_PLATE, () -> OrePrefix.plank);
    public static DustMaterial Livingrock = new DustMaterial(92, "livingrock", 0xCDCDCD, MaterialIconSet.ROUGH, 1, of(), MORTAR_GRINDABLE | GENERATE_PLATE | NO_SMASHING | NO_RECYCLING);
    public static IngotMaterial Manasteel = new IngotMaterial(355, "manasteel", 0x0000FF, MaterialIconSet.SHINY, 2, of(), EXT2_METAL);
    public static IngotMaterial Terrasteel = new IngotMaterial(355, "terrasteel", 0x32CD32, MaterialIconSet.SHINY, 3, of(), EXT2_METAL);
    public static IngotMaterial ElvenElementium = new IngotMaterial(355, "elven_elementium", 0xFF1493, MaterialIconSet.SHINY, 3, of(), EXT2_METAL);
    public static IngotMaterial Gaia = new IngotMaterial(355, "gaia", 0x708090, MaterialIconSet.DULL, 4, of(), EXT2_METAL);
    public static GemMaterial ManaPearl = new GemMaterial(295, "mana_pearl", 0x0000FF, MaterialIconSet.GEM_VERTICAL, 1, of(), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 16);
    public static GemMaterial ManaDiamond = new GemMaterial(295, "mana_diamond", 0x42F4F1, MaterialIconSet.GEM_VERTICAL, 3, of(), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 16);

    //Thaumcraft
    public static RoughSolidMaterial Greatwood = new RoughSolidMaterial(356, "greatwood", 0x896727, MaterialIconSet.WOOD, 1, of(), STD_SOLID | FLAMMABLE | NO_SMELTING | GENERATE_LONG_ROD | GENERATE_PLATE, () -> OrePrefix.plank);
    public static RoughSolidMaterial Silverwood = new RoughSolidMaterial(356, "silverwood", 0xF5F5F5, MaterialIconSet.WOOD, 1, of(), STD_SOLID | FLAMMABLE | NO_SMELTING | GENERATE_LONG_ROD | GENERATE_PLATE, () -> OrePrefix.plank);
    public static IngotMaterial Thaumium = new IngotMaterial(355, "thaumium", 0x9370DB, MaterialIconSet.SHINY, 3, of(), EXT2_METAL);
    public static IngotMaterial Void = new IngotMaterial(355, "void", 0x442B66, MaterialIconSet.SHINY, 3, of(), EXT2_METAL);
   
    /**
     * Fluids
     */
    public static FluidMaterial Lubricant = new FluidMaterial(88, "lubricant", 0xFFC400, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial DrillingFluid = new FluidMaterial(501, "drilling_fluid", 0xFFC400, MaterialIconSet.FLUID, of(), 0);
    
    /**
     * UU/Matters
     */   
    public static FluidMaterial UUAmplifier = new FluidMaterial(351, "uuamplifier", 0xAA00AA, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial UUMatter = new FluidMaterial(352, "uumatter", 0x770077, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial NeutralMatter = new FluidMaterial(353, "neutral_matter", 3956968, MaterialIconSet.FLUID, of(), DISABLE_DECOMPOSITION);
    public static FluidMaterial PositiveMatter = new FluidMaterial(354, "positive_matter", 11279131, MaterialIconSet.FLUID, of(), DISABLE_DECOMPOSITION);

    /**
     * Fluid Dyes
     */
    public static FluidMaterial WaterDyeBlack = new FluidMaterial(355, "water_dye_black", 1908001, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeRed = new FluidMaterial(356, "water_dye_red", 0xFF0000, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeGreen = new FluidMaterial(357, "water_dye_green", 0x32CD32, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeBrown = new FluidMaterial(358, "water_dye_brown", 0x8B4513, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeBlue = new FluidMaterial(359, "water_dye_blue", 0x0000B3, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyePurple = new FluidMaterial(360, "water_dye_purple", 0x9370DB, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeCyan = new FluidMaterial(361, "water_dye_cyan", 0x0000FF, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeLightGray = new FluidMaterial(362, "water_dye_lightgray", 0xD3D3D3, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeGray = new FluidMaterial(363, "water_dye_gray", 0x708090, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyePink = new FluidMaterial(364, "water_dye_pink", 0xFF1493, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeLime = new FluidMaterial(365, "water_dye_lime", 0x4DFF4D, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeYellow = new FluidMaterial(366, "water_dye_yellow", 0xFFFF00, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeLightBlue = new FluidMaterial(367, "water_dye_lightblue", 0xADE6E6, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeMagenta = new FluidMaterial(368, "water_dye_magenta", 0x8B008B, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeOrange = new FluidMaterial(369, "water_dye_orange", 0xFF4500, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial WaterDyeWhite = new FluidMaterial(370, "water_dye_white", 0xF5F5F5, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
	
    public static FluidMaterial ChemicalDyeBlack = new FluidMaterial(371, "chemical_dye_black", 1908001, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeRed = new FluidMaterial(372, "chemical_dye_red", 0xFF0000, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeGreen = new FluidMaterial(373, "chemical_dye_green", 0x32CD32, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeBrown = new FluidMaterial(374, "chemical_dye_brown", 0x8B4513, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeBlue = new FluidMaterial(375, "chemical_dye_blue", 0x0000B3, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyePurple = new FluidMaterial(376, "chemical_dye_purple", 0x9370DB, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeCyan = new FluidMaterial(377, "chemical_dye_cyan", 0x0000FF, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeLightGray = new FluidMaterial(378, "chemical_dye_lightgray", 0xD3D3D3, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeGray = new FluidMaterial(379, "chemical_dye_gray", 0x708090, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyePink = new FluidMaterial(380, "chemical_dye_pink", 0xFF1493, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeLime = new FluidMaterial(381, "chemical_dye_lime", 0x4DFF4D, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeYellow = new FluidMaterial(382, "chemical_dye_yellow", 0xFFFF00, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeLightBlue = new FluidMaterial(383, "chemical_dye_lightblue", 0xADE6E6, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeMagenta = new FluidMaterial(384, "chemical_dye_magenta", 0x8B008B, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeOrange = new FluidMaterial(385, "chemical_dye_orange", 0xFF4500, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial ChemicalDyeWhite = new FluidMaterial(386, "chemical_dye_white", 0xF5F5F5, MaterialIconSet.FLUID, of(), GENERATE_FLUID_BLOCK);   	
    
    static {/*
        for (DustMaterial dustMaterial : new DustMaterial[]{Bastnasite}) {
            dustMaterial.separatedOnto = Neodymium;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Magnetite, VanadiumMagnetite}) {
            dustMaterial.separatedOnto = Gold;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Pyrite, BandedIron, Siderite, Geothite, Hematite, Taconite, Nickel, Pentlandite, Tin, Antimony, Ilmenite, Manganese, Chrome, Chromite, Andradite}) {
            dustMaterial.separatedOnto = Iron;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Pyrite}) {
            dustMaterial.addFlag(BLAST_FURNACE_CALCITE_DOUBLE);
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Iron, PigIron, WroughtIron, Siderite, Hematite}) {
            dustMaterial.addFlag(BLAST_FURNACE_CALCITE_TRIPLE);
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Gold, Silver, Osmium, Platinum, Cooperite}) {
            dustMaterial.washedIn = Mercury;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Zinc, Nickel, Copper, Cobalt, Cobaltite, Tetrahedrite}) {
            dustMaterial.washedIn = SodiumPersulfate;
        }*/

        Neodymium.magneticMaterial = NeodymiumMagnetic;
        Steel.magneticMaterial = SteelMagnetic;
        Iron.magneticMaterial = IronMagnetic;

        NeodymiumMagnetic.setSmeltingInto(Neodymium);
        NeodymiumMagnetic.setArcSmeltingInto(Neodymium);
        NeodymiumMagnetic.setMaceratingInto(Neodymium);

        SteelMagnetic.setSmeltingInto(Steel);
        IronMagnetic.setArcSmeltingInto(Steel);
        IronMagnetic.setMaceratingInto(Steel);;

        IronMagnetic.setSmeltingInto(Iron);
        IronMagnetic.setArcSmeltingInto(WroughtIron);
        IronMagnetic.setMaceratingInto(Iron);

        Iron.setArcSmeltingInto(WroughtIron);
        Copper.setArcSmeltingInto(AnnealedCopper);

        Tetrahedrite.setDirectSmelting(Copper);
        Chalcopyrite.setDirectSmelting(Copper);
        Malachite.setDirectSmelting(Copper);
        Pentlandite.setDirectSmelting(Nickel);
        Sphalerite.setDirectSmelting(Zinc);
        Pyrite.setDirectSmelting(Iron);
        Cassiterite.setDirectSmelting(Tin);
        Garnierite.setDirectSmelting(Nickel);
        Cobaltite.setDirectSmelting(Cobalt);
        Stibnite.setDirectSmelting(Antimony);
        Cooperite.setDirectSmelting(Platinum);
        Pyrolusite.setDirectSmelting(Manganese);
        Magnesite.setDirectSmelting(Magnesium);
        Molybdenite.setDirectSmelting(Molybdenum);

        Salt.setOreMultiplier(2);
        Salt.setSmeltingMultiplier(2);
        RockSalt.setOreMultiplier(2);
        RockSalt.setSmeltingMultiplier(2);

        Scheelite.setOreMultiplier(2);
        Scheelite.setSmeltingMultiplier(2);
        Tungstate.setOreMultiplier(2);
        Tungstate.setSmeltingMultiplier(2);

        Cassiterite.setOreMultiplier(2);
        NetherQuartz.setOreMultiplier(2);
        NetherQuartz.setSmeltingMultiplier(2);
        CertusQuartz.setOreMultiplier(2);
        CertusQuartz.setSmeltingMultiplier(2);

        Phosphor.setOreMultiplier(3);
        Phosphor.setSmeltingMultiplier(3);
        Saltpeter.setOreMultiplier(4);
        Saltpeter.setSmeltingMultiplier(4);
        Apatite.setOreMultiplier(4);
        Apatite.setSmeltingMultiplier(4);
        Apatite.setByProductMultiplier(2);

        Redstone.setOreMultiplier(5);
        Redstone.setSmeltingMultiplier(5);
        Glowstone.setOreMultiplier(5);
        Glowstone.setSmeltingMultiplier(5);

        Lapis.setOreMultiplier(6);
        Lapis.setSmeltingMultiplier(6);
        Lapis.setByProductMultiplier(4);
        Sodalite.setOreMultiplier(6);
        Sodalite.setSmeltingMultiplier(6);
        Sodalite.setByProductMultiplier(4);
        Lazurite.setOreMultiplier(6);
        Lazurite.setSmeltingMultiplier(6);
        Lazurite.setByProductMultiplier(4);

        Coal.setBurnTime(1600); //default coal burn time in vanilla
        Charcoal.setBurnTime(1600); //default coal burn time in vanilla
        Lignite.setBurnTime(1200); //2/3 of burn time of coal
        Coke.setBurnTime(3200); //2x burn time of coal
        
        Vinteum.addEnchantmentForTools(Enchantments.FORTUNE, 1);
        RoseGold.addEnchantmentForTools(Enchantments.SMITE, 4);
        Invar.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 3);
        BismuthBronze.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 5);
        Iron.addEnchantmentForTools(Enchantments.SHARPNESS, 1);
        Bronze.addEnchantmentForTools(Enchantments.SHARPNESS, 1);
        Brass.addEnchantmentForTools(Enchantments.SHARPNESS, 2);
        Steel.addEnchantmentForTools(Enchantments.SHARPNESS, 2);
        WroughtIron.addEnchantmentForTools(Enchantments.SHARPNESS, 2);
        StainlessSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 3);
        DamascusSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        TungstenCarbide.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        HSSE.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        HSSG.addEnchantmentForTools(Enchantments.SHARPNESS, 4);
        HSSS.addEnchantmentForTools(Enchantments.SHARPNESS, 5);

        Lepidolite.addOreByProducts(Boron);
        Chalcopyrite.addOreByProducts(Pyrite, Cobalt, Cadmium, Gold);
        Sphalerite.addOreByProducts(Cadmium, Gallium, Zinc);
        Uraninite.addOreByProducts(Uranium, Thorium, Lead);
        Pitchblende.addOreByProducts(Thorium, Uranium, Lead);
        Galena.addOreByProducts(Sulfur, Silver, Lead);
        Lapis.addOreByProducts(Lazurite, Sodalite, Pyrite);
        Pyrite.addOreByProducts(Sulfur, Phosphor, Iron);
        Copper.addOreByProducts(Cobalt, Gold, Nickel);
        Nickel.addOreByProducts(Cobalt, Platinum, Iron);
        //GarnetRed.addOreByProducts(Spessartine, Pyrope, Almandine);
        //GarnetYellow.addOreByProducts(Andradite, Grossular, Uvarovite);
        Cooperite.addOreByProducts(Palladium, Nickel, Iridium);
        Cinnabar.addOreByProducts(Redstone, Sulfur, Glowstone);
        Pollucite.addOreByProducts(Caesium, Aluminium, Rubidium);
        Pentlandite.addOreByProducts(Iron, Sulfur, Cobalt);
        //Uranium.addOreByProducts(Lead, Thorium);
        Scheelite.addOreByProducts(Manganese, Molybdenum, Calcium);
        Tungstate.addOreByProducts(Manganese, Silver, Lithium);
        Bauxite.addOreByProducts(Grossular, Rutile, Gallium);
        Quartzite.addOreByProducts(CertusQuartz, Barite);
        CertusQuartz.addOreByProducts(Quartzite, Barite);
        Redstone.addOreByProducts(Cinnabar, RareEarth, Glowstone);
        Malachite.addOreByProducts(Copper, Calcite);
        Neodymium.addOreByProducts(RareEarth);
        Bastnasite.addOreByProducts(Neodymium, RareEarth);
        Glowstone.addOreByProducts(Redstone, Gold);
        Zinc.addOreByProducts(Tin, Gallium);
        Tungsten.addOreByProducts(Manganese, Molybdenum);
        Iron.addOreByProducts(Nickel, Tin);
        Lepidolite.addOreByProducts(Lithium, Caesium);
        Gold.addOreByProducts(Copper, Nickel);
        Tin.addOreByProducts(Iron, Zinc);
        Antimony.addOreByProducts(Zinc, Iron);
        Silver.addOreByProducts(Lead, Sulfur);
        Lead.addOreByProducts(Silver, Sulfur);
        Thorium.addOreByProducts(Uranium, Lead);
        Electrum.addOreByProducts(Gold, Silver);
        Bronze.addOreByProducts(Copper, Tin);
        Brass.addOreByProducts(Copper, Zinc);
        Coal.addOreByProducts(Lignite, Thorium);
        Ilmenite.addOreByProducts(Iron, Rutile);
        Manganese.addOreByProducts(Chrome, Iron);
        Sapphire.addOreByProducts(Aluminium);
        Peridot.addOreByProducts(Aluminium, Magnesium);
        Platinum.addOreByProducts(Nickel, Iridium);
        Emerald.addOreByProducts(Beryllium, Aluminium);
        Olivine.addOreByProducts(Pyrope, Magnesium);
        Chrome.addOreByProducts(Iron, Magnesium);
        Chromite.addOreByProducts(Iron, Magnesium);
        Tetrahedrite.addOreByProducts(Antimony, Zinc);
        Magnetite.addOreByProducts(Iron, Gold);
        //Basalt.addOreByProducts(Olivine, DarkAsh);
        VanadiumMagnetite.addOreByProducts(Magnetite, Vanadium);
        Lazurite.addOreByProducts(Sodalite, Lapis);
        Sodalite.addOreByProducts(Lazurite, Lapis);
        Spodumene.addOreByProducts(Aluminium, Lithium);
        Ruby.addOreByProducts(Chrome, Aluminium);
        Phosphor.addOreByProducts(Apatite, Phosphate);
        Iridium.addOreByProducts(Platinum, Osmium);
        Pyrope.addOreByProducts(Aluminium, Magnesium, Silicon);
        Almandine.addOreByProducts(Aluminium);
        Spessartine.addOreByProducts(Aluminium, Manganese);
        //Andradite.addOreByProducts(GarnetYellow, Iron);
        Grossular.addOreByProducts(Aluminium, Calcium);
        //Uvarovite.addOreByProducts(GarnetYellow, Chrome);
        Calcite.addOreByProducts(Malachite);
        NaquadahEnriched.addOreByProducts(Naquadah);
        Naquadah.addOreByProducts(NaquadahEnriched);
        Pyrolusite.addOreByProducts(Manganese);
        Molybdenite.addOreByProducts(Molybdenum);
        Stibnite.addOreByProducts(Antimony);
        Garnierite.addOreByProducts(Nickel);
        Lignite.addOreByProducts(Coal);
        Diamond.addOreByProducts(Graphite);
        Beryllium.addOreByProducts(Emerald);
        Apatite.addOreByProducts(Phosphor);
        Magnesite.addOreByProducts(Magnesium);
        NetherQuartz.addOreByProducts(Netherrack);
        PigIron.addOreByProducts(Iron);
        Steel.addOreByProducts(Iron);
        Graphite.addOreByProducts(Carbon);
        Netherrack.addOreByProducts(Sulfur);
        Flint.addOreByProducts(Obsidian);
        Cobaltite.addOreByProducts(Cobalt);
        Cobalt.addOreByProducts(Cobaltite);
        Sulfur.addOreByProducts(Sulfur);
        Saltpeter.addOreByProducts(Saltpeter);
        Endstone.addOreByProducts(Helium3);
        Osmium.addOreByProducts(Iridium);
        Magnesium.addOreByProducts(Olivine);
        Aluminium.addOreByProducts(Bauxite);
        Titanium.addOreByProducts(Almandine);
        Obsidian.addOreByProducts(Olivine);
        Ash.addOreByProducts(Carbon);
        DarkAsh.addOreByProducts(Carbon);
        //Marble.addOreByProducts(Calcite);
        Clay.addOreByProducts(Clay);
        Cassiterite.addOreByProducts(Tin);
        Phosphate.addOreByProducts(Phosphorus);
        Phosphorus.addOreByProducts(Phosphate);
        Tanzanite.addOreByProducts(Opal);
        Opal.addOreByProducts(Tanzanite);
        Amethyst.addOreByProducts(Amethyst);
        Topaz.addOreByProducts(BlueTopaz);
        BlueTopaz.addOreByProducts(Topaz);
        Niter.addOreByProducts(Saltpeter);
        Vinteum.addOreByProducts(Vinteum);
        Lithium.addOreByProducts(Lithium);
        Silicon.addOreByProducts(SiliconDioxide);
        Salt.addOreByProducts(RockSalt);
        RockSalt.addOreByProducts(Salt);
        //Andesite.addOreByProducts(Basalt);
        Diorite.addOreByProducts(NetherQuartz);

        RedAlloy.setCableProperties(GTValues.V[0], 1, 0);
        Tin.setCableProperties(GTValues.V[1], 1, 1);
        Copper.setCableProperties(GTValues.V[2], 1, 2);

        Cobalt.setCableProperties(GTValues.V[1], 2, 2);
        Lead.setCableProperties(GTValues.V[1], 2, 2);
        Tin.setCableProperties(GTValues.V[1], 1, 1);
        Zinc.setCableProperties(GTValues.V[1], 1, 1);
        SolderingAlloy.setCableProperties(GTValues.V[1], 1, 1);

        Iron.setCableProperties(GTValues.V[2], 2, 3);
        Nickel.setCableProperties(GTValues.V[2], 3, 3);
        Cupronickel.setCableProperties(GTValues.V[2], 2, 3);
        Copper.setCableProperties(GTValues.V[2], 1, 2);
        AnnealedCopper.setCableProperties(GTValues.V[2], 1, 1);

        Kanthal.setCableProperties(GTValues.V[3], 4, 3);
        Gold.setCableProperties(GTValues.V[3], 2, 2);
        Electrum.setCableProperties(GTValues.V[3], 3, 2);
        Silver.setCableProperties(GTValues.V[3], 1, 1);

        Nichrome.setCableProperties(GTValues.V[4], 3, 4);
        Steel.setCableProperties(GTValues.V[4], 2, 2);
        Titanium.setCableProperties(GTValues.V[4], 4, 2);
        Aluminium.setCableProperties(GTValues.V[4], 1, 1);

        Graphene.setCableProperties(GTValues.V[5], 1, 1);
        Osmium.setCableProperties(GTValues.V[5], 4, 2);
        Platinum.setCableProperties(GTValues.V[5], 2, 1);
        TungstenSteel.setCableProperties(GTValues.V[5], 3, 2);
        Tungsten.setCableProperties(GTValues.V[5], 2, 2);

        HSSG.setCableProperties(GTValues.V[6], 4, 2);
        NiobiumTitanium.setCableProperties(GTValues.V[6], 4, 2);
        VanadiumGallium.setCableProperties(GTValues.V[6], 4, 2);
        YttriumBariumCuprate.setCableProperties(GTValues.V[6], 4, 4);

        Naquadah.setCableProperties(GTValues.V[6], 2, 2);

        NaquadahAlloy.setCableProperties(GTValues.V[6], 2, 4);

        Copper.setFluidPipeProperties(25, 1000, true);
        Bronze.setFluidPipeProperties(35, 2000, true);
        Steel.setFluidPipeProperties(50, 2500, true);
        StainlessSteel.setFluidPipeProperties(100, 3000, true);
        Titanium.setFluidPipeProperties(200, 5000, true);
        TungstenSteel.setFluidPipeProperties(300, 7500, true);

        Plastic.setFluidPipeProperties(200, 350, true);
        Polytetrafluoroethylene.setFluidPipeProperties(200, 600, true);
        
        OrePrefix.gemChipped.setIgnored(Materials.Lignite);
        
        OrePrefix.gemChipped.setIgnored(Materials.CertusQuartz);
        
        OrePrefix.crystal.setIgnored(Materials.CertusQuartz);
        OrePrefix.crystal.setIgnored(Materials.NetherQuartz);
        
        /*OrePrefix.cluster.setIgnored(Materials.Iron);
        OrePrefix.cluster.setIgnored(Materials.Gold);
        OrePrefix.cluster.setIgnored(Materials.Copper);
        OrePrefix.cluster.setIgnored(Materials.Tin);
        OrePrefix.cluster.setIgnored(Materials.Silver);
        OrePrefix.cluster.setIgnored(Materials.Lead);
        OrePrefix.cluster.setIgnored(Materials.Cinnabar);*/
        
        OrePrefix.clump.setIgnored(Materials.Iron);
        OrePrefix.clump.setIgnored(Materials.Gold);
        OrePrefix.clump.setIgnored(Materials.Copper);
        OrePrefix.clump.setIgnored(Materials.Tin);
        OrePrefix.clump.setIgnored(Materials.Silver);
        OrePrefix.clump.setIgnored(Materials.Lead);
        OrePrefix.clump.setIgnored(Materials.Osmium);
        
        OrePrefix.shard.setIgnored(Materials.Iron);
        OrePrefix.shard.setIgnored(Materials.Gold);
        OrePrefix.shard.setIgnored(Materials.Copper);
        OrePrefix.shard.setIgnored(Materials.Tin);
        OrePrefix.shard.setIgnored(Materials.Silver);
        OrePrefix.shard.setIgnored(Materials.Lead);
        OrePrefix.shard.setIgnored(Materials.Osmium);   
        
        OrePrefix.crystal.setIgnored(Materials.Iron);
        OrePrefix.crystal.setIgnored(Materials.Gold);
        OrePrefix.crystal.setIgnored(Materials.Copper);
        OrePrefix.crystal.setIgnored(Materials.Tin);
        OrePrefix.crystal.setIgnored(Materials.Silver);
        OrePrefix.crystal.setIgnored(Materials.Lead);
        OrePrefix.crystal.setIgnored(Materials.Osmium);     
    }
}
