package gregtech.common;

import gregtech.api.GTValues;
import net.minecraftforge.common.config.Config;

@Config(modid = GTValues.MODID)
public class ConfigHolder {

    @Config.Comment("Whether to enable more verbose logging. Default: false")
    public static boolean debug = false;

    @Config.Comment("Whether to increase number of rolls for dungeon chests. Increases dungeon loot drastically.")
    public static boolean increaseDungeonLoot = true;

    @Config.Comment("Specifies min amount of veins in section")
    public static int minVeinsInSection = 0;

    @Config.Comment("Specifies additional random amount of veins in section")
    public static int additionalVeinsInSection = 1;

    @Config.Comment("Whether to disable vanilla ores generation in world. Default is false.")
    public static boolean disableVanillaOres = true;

    @Config.Comment("Whether machines should explode when overloaded with power. Default: true")
    public static boolean doExplosions = true;

    @Config.Comment("Energy use multiplier for electric items. Default: 10")
    public static int energyUsageMultiplier = 10;

    @Config.RangeInt(min = 0, max = 100)
    @Config.Comment("Chance with which flint and steel will create fire. Default: 75")
    public static int flintChanceToCreateFire = 75;
    
    @Config.Comment("Generate rubber trees or not?")
    public static boolean disableRubberTreeGeneration = false;

    @Config.Comment("If true, insufficient energy supply will reset recipe progress to zero. If false, progress will slowly go back (with 2x speed)")
    @Config.RequiresWorldRestart
    public static boolean insufficientEnergySupplyWipesRecipeProgress = false;

    @Config.Comment("Whether to use modPriorities setting in config for prioritizing ore dictionary item registrations. " +
        "By default, GregTech will sort ore dictionary registrations alphabetically comparing their owner Mod IDs.")
    public static boolean useCustomModPriorities = false;

    @Config.Comment("Specifies priorities of mods in ore dictionary item registration. First Mod ID has highest priority, last - lowest. " +
        "Unspecified Mod IDs follow standard sorting, but always have lower priority than last specified Mod ID.")
    @Config.RequiresMcRestart
    public static String[] modPriorities = new String[0];
    
    @Config.Comment("Setting this to true makes GregTech ignore error and invalid recipes that would otherwise cause crash. Default to true.")
    @Config.RequiresMcRestart
    public static boolean ignoreErrorOrInvalidRecipes = true;
  
    /*@Config.Comment("Setting this to true makes GregTech take priority over Tinker's in their tool stats")
    @Config.RequiresMcRestart
    public static boolean overrideTiConStats = false;*/
}
