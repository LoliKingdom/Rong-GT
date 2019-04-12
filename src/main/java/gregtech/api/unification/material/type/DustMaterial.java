package gregtech.api.unification.material.type;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.GTValues;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.type.FluidMaterial.MatFlags;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static gregtech.api.unification.material.type.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.GENERATE_ORE;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.EXCLUDE_BLOCK_CRAFTING_RECIPES;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_DENSE;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_FINE_WIRE;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_FOIL;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_RING;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_ROTOR;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_SCREW;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.GENERATE_SMALL_GEAR;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;
import static gregtech.api.util.GTUtility.createFlag;

@ZenClass("mods.gregtech.material.DustMaterial")
@ZenRegister
public class DustMaterial extends FluidMaterial {

    public static final class MatFlags {

        public static final long GENERATE_ORE = createFlag(11);

        /**
         * Generate a plate for this material
         * If it's dust material, dust compressor recipe into plate will be generated
         * If it's metal material, bending machine recipes will be generated
         * If block is found, cutting machine recipe will be also generated
         */
        public static final long GENERATE_PLATE = createFlag(12);

        /**
         * Add to material if it cannot be worked by any other means, than smashing or smelting. This is used for coated Materials.
         */
        public static final long NO_WORKING = createFlag(13);
        /**
         * Add to material if it cannot be used for regular Metal working techniques since it is not possible to bend it.
         */
        public static final long NO_SMASHING = createFlag(14);

        /**
         * Add to material if it's impossible to smelt it
         */
        public static final long NO_SMELTING = createFlag(15);

        /**
         * Add to material if it is outputting less in an Induction Smelter.
         */
        public static final long INDUCTION_SMELTING_LOW_OUTPUT = createFlag(16);

        /**
         * Add to material if it melts into fluid (and it will also generate fluid for this material)
         */
        public static final long SMELT_INTO_FLUID = createFlag(17);

        public static final long EXCLUDE_BLOCK_CRAFTING_RECIPES = createFlag(18);

        public static final long EXCLUDE_PLATE_COMPRESSOR_RECIPE = createFlag(19);

        static {
            Material.MatFlags.registerMaterialFlagsHolder(MatFlags.class, DustMaterial.class);
        }

    }


    /**
     * List of ore by products
     */
    @ZenProperty
    public final List<FluidMaterial> oreByProducts = new ArrayList<>();

    /**
     * Crushed ore output amount multiplier during maceration
     */
    @ZenProperty
    public int oreMultiplier = 1;

    /**
     * Byproducts output amount multiplier during pulverization
     */
    @ZenProperty
    public int byProductMultiplier = 1;

    /**
     * Smelting item amount multiplier during vanilla item smelting
     */
    @ZenProperty
    public int smeltingMultiplier = 1;

    /**
     * Tool level needed to harvest block of this material
     */
    @ZenProperty
    public final int harvestLevel;

    /**
     * Material to which smelting of this material ore will result
     */
    @ZenProperty
    public SolidMaterial directSmelting;

    /**
     * Material in which this material's ore should be washed to give additional output
     */
    @ZenProperty
    public FluidMaterial washedIn;

    /**
     * During electromagnetic separation, this material ore will be separated onto this material and material specified by this field
     */
    @ZenProperty
    public DustMaterial separatedOnto;

    /**
     * Burn time of this material when used as fuel in furnace smelting
     * Zero or negative value indicates that this material cannot be used as fuel
     */
    @ZenProperty
    public int burnTime = 0;
    
    /**
     * For Mekanism, when it is loaded, slurries will load.
     * This is mainly for the purpose of better ore processing methods.
     */
    @Nullable
    private Fluid materialDirtySlurry;
    
    @Nullable
    private Fluid materialCleanSlurry;

    public DustMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Element element) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element);
        this.harvestLevel = harvestLevel;
    }

    public DustMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, int harvestLevel, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
        super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, null);
        this.harvestLevel = harvestLevel;
    }

    @Override
    protected void initializeMaterial() {
        super.initializeMaterial();
        if(isRadioactive()) {
        	setRadioactivity(2);
        }
        if(shouldGenerateFluid()) {
            setFluidTemperature(1200);
        }
    }
    
    @Override
    protected long verifyMaterialBits(long generationBits) {
        if((generationBits & GENERATE_ORE) > 0) {
            generationBits |= EXCLUDE_BLOCK_CRAFTING_RECIPES;
        }
        return super.verifyMaterialBits(generationBits);
    }

    @Override
    public boolean shouldGenerateFluid() {
        return hasFlag(MatFlags.SMELT_INTO_FLUID);
    }
    
    @ZenGetter("hasSlurry")
    public boolean shouldGenerateSlurries() {
        return hasFlag(GENERATE_ORE) && GTValues.isModLoaded("mekanism");
    }

    public void addOreByProducts(FluidMaterial... byProducts) {
        this.oreByProducts.addAll(Arrays.asList(byProducts));
    }

    //kept here for binary compatibility
    public void setDirectSmelting(IngotMaterial directSmelting) {
        this.directSmelting = directSmelting;
    }

    public void setDirectSmelting(SolidMaterial directSmelting) {
        this.directSmelting = directSmelting;
    }

    public void setOreMultiplier(int oreMultiplier) {
        this.oreMultiplier = oreMultiplier;
    }

    public void setSmeltingMultiplier(int smeltingMultiplier) {
        this.smeltingMultiplier = smeltingMultiplier;
    }

    public void setByProductMultiplier(int byProductMultiplier) {
        this.byProductMultiplier = byProductMultiplier;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }
    
    /**
     * Internal usage only
     */    
    public final void setMaterialDirtySlurry(@Nonnull Fluid dirtySlurry) {
        Preconditions.checkNotNull(dirtySlurry);
        this.materialDirtySlurry = dirtySlurry;
    }
    
    public final void setMaterialCleanSlurry(@Nonnull Fluid cleanSlurry) {
        Preconditions.checkNotNull(cleanSlurry);
        this.materialCleanSlurry = cleanSlurry;
    }  

    public final @Nullable Fluid getMaterialDirtySlurry() {
        return materialDirtySlurry;
    }

    public final @Nullable Fluid getMaterialCleanSlurry() {
        return materialCleanSlurry;
    }
}