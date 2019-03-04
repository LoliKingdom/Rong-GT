package gregtech.common;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.FluidMaterial.MatFlags;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MetaFluids {

    private static final Set<ResourceLocation> fluidSprites = new HashSet<>();
    private static final Map<String, FluidMaterial> fluidToMaterialMappings = new HashMap<>();

    public static final ResourceLocation AUTO_GENERATED_FLUID_TEXTURE = new ResourceLocation(
        GTValues.MODID, "blocks/fluids/fluid.molten.autogenerated");

    public static final Fluid DISTILLED_WATER = new Fluid("distilled_water",
        new ResourceLocation("blocks/water_still"),
        new ResourceLocation("blocks/water_flow"));

    public enum FluidType {
        LIQUID(""),
        GAS(""),
        SLURRY("slurry."),
        CLEANSLURRY("clean_slurry."),
        PLASMA("plasma.");

        public final String prefix;

        FluidType(String prefix) {
            this.prefix = prefix;
        }
    }
    
    public static void registerSprites(TextureMap textureMap) {
        for(ResourceLocation spriteLocation : fluidSprites) {
            textureMap.registerSprite(spriteLocation);
        }
    }

    public static void init() {
        Materials.Water.setMaterialFluid(FluidRegistry.WATER);
        Materials.Lava.setMaterialFluid(FluidRegistry.LAVA);

        FluidRegistry.registerFluid(DISTILLED_WATER);
        Materials.DistilledWater.setMaterialFluid(DISTILLED_WATER);
        fluidSprites.add(AUTO_GENERATED_FLUID_TEXTURE);

        //TODO TWEAK VALUES
        registerFluid(Materials.Air, FluidType.GAS, 300, true);
        registerFluid(Materials.Oxygen, FluidType.GAS, 300, true);
        registerFluid(Materials.Hydrogen, FluidType.GAS, 300, true);
        registerFluid(Materials.Deuterium, FluidType.GAS, 300, true);
        registerFluid(Materials.Tritium, FluidType.GAS, 300, true);
        registerFluid(Materials.Helium, FluidType.GAS, 300, true);
        registerFluid(Materials.Argon, FluidType.GAS, 300, true);
        registerFluid(Materials.Radon, FluidType.GAS, 300, true);
        registerFluid(Materials.Fluorine, FluidType.GAS, 253, true);
        registerFluid(Materials.TitaniumTetrachloride, FluidType.LIQUID, 2200, true);
        registerFluid(Materials.Helium3, FluidType.GAS, 300, true);
        registerFluid(Materials.Methane, FluidType.GAS, 300, true);
        registerFluid(Materials.Nitrogen, FluidType.GAS, 300, true);
        registerFluid(Materials.NitrogenDioxide, FluidType.GAS, 300, true);
        registerFluid(Materials.Steam, FluidType.GAS, 380, true);

        registerFluid(Materials.OilHeavy, FluidType.LIQUID, 300, true);
        registerFluid(Materials.OilMedium, FluidType.LIQUID, 300, true);
        registerFluid(Materials.OilLight, FluidType.LIQUID, 300, true);

        registerFluid(Materials.HydrogenSulfide, FluidType.GAS, 300, true);
        registerFluid(Materials.SulfuricGas, FluidType.GAS, 300, true);
        registerFluid(Materials.Gas, FluidType.GAS, 300, true);

        registerFluid(Materials.SulfuricNaphtha, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SulfuricLightFuel, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SulfuricHeavyFuel, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Naphtha, FluidType.LIQUID, 300, true);
        registerFluid(Materials.LightFuel, FluidType.LIQUID, 300, true);
        registerFluid(Materials.HeavyFuel, FluidType.LIQUID, 300, true);
        registerFluid(Materials.LPG, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SteamCrackedLightFuel, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SteamCrackedHeavyFuel, FluidType.LIQUID, 300, true);

        registerFluid(Materials.UUAmplifier, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Chlorine, FluidType.GAS, 300, true);
        registerFluid(Materials.Mercury, FluidType.LIQUID, 300, true);
        registerFluid(Materials.NitroFuel, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SodiumPersulfate, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Glyceryl, FluidType.LIQUID, 300, true);

        registerFluid(Materials.Lubricant, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Creosote, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SeedOil, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Oil, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Fuel, FluidType.LIQUID, 300, true);
        //registerFluid(Materials.Honey, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Biomass, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Ethanol, FluidType.LIQUID, 300, true);
        registerFluid(Materials.SulfuricAcid, FluidType.LIQUID, 300, true);
        registerFluid(Materials.Milk, FluidType.LIQUID, 290, true);
        registerFluid(Materials.Glue, FluidType.LIQUID, 300, true);

        for (Material material : Material.MATERIAL_REGISTRY) {
            if (!(material instanceof FluidMaterial)) continue;
            FluidMaterial fluidMaterial = (FluidMaterial) material;
            if (fluidMaterial.shouldGenerateFluid() &&
                fluidMaterial.getMaterialFluid() == null) {
                int temperature = fluidMaterial.getFluidTemperature();
                FluidType fluidType = fluidMaterial.hasFlag(MatFlags.STATE_GAS) ? FluidType.GAS : FluidType.LIQUID;
                registerFluid(fluidMaterial, fluidType, temperature, false);                
            }
            if(fluidMaterial.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
            	registerFluid(material, FluidType.SLURRY, 1200);
            	registerFluid(material, FluidType.CLEANSLURRY, 1500);
            }
            if (fluidMaterial.shouldGeneratePlasma() &&
                fluidMaterial.getMaterialPlasma() == null) {
                registerFluid(fluidMaterial, FluidType.PLASMA, 30000, false);
            }
        }
    }

    public static void registerFluid(Material material, FluidType type, int temp) {
    	String materialName = material.toString();
        ResourceLocation textureLocation;{
            textureLocation = AUTO_GENERATED_FLUID_TEXTURE;
        }
        Fluid fluid = new Fluid(type.prefix + materialName, textureLocation, textureLocation) {
            @Override
            public String getUnlocalizedName() {
                return material.getUnlocalizedName();
            }

            @Override
            @SideOnly(Side.CLIENT)
            public String getLocalizedName(FluidStack stack) {
            	String localizedName = I18n.format(getUnlocalizedName());
            	if(type == FluidType.SLURRY) {
            		return I18n.format("gregtech.fluid.slurry", localizedName);
            	}
            	else {
            		return I18n.format("gregtech.fluid.cleanslurry", localizedName);
            	}
            }
        };
        fluid.setTemperature(temp);
        fluid.setColor(GTUtility.convertRGBtoOpaqueRGBA_MC(material.materialRGB));
        fluid.setGaseous(false);
    	fluid.setDensity(3000);
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);		
	}

	public static Fluid registerFluid(FluidMaterial material, FluidType type, int temp, boolean setCustomTexture) {
        String materialName = material.toString();
        ResourceLocation textureLocation;
        if(setCustomTexture) {
            textureLocation = new ResourceLocation(GTValues.MODID, "blocks/fluids/fluid." + materialName);
            fluidSprites.add(textureLocation);
        } else {
            textureLocation = AUTO_GENERATED_FLUID_TEXTURE;
        }
        Fluid fluid = new Fluid(type.prefix + materialName, textureLocation, textureLocation) {
            @Override
            public String getUnlocalizedName() {
                return material.getUnlocalizedName();
            }

            @Override
            @SideOnly(Side.CLIENT)
            public String getLocalizedName(FluidStack stack) {
                String localizedName = I18n.format(getUnlocalizedName());
                if(type == FluidType.PLASMA) {
                    return I18n.format("gregtech.fluid.plasma", localizedName);
                }
                return localizedName;
            }
        };
        fluid.setTemperature(temp);
        fluid.setColor(GTUtility.convertRGBtoOpaqueRGBA_MC(material.materialRGB));
        setFluidProperties(fluid, type, material);
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        if(material.hasFlag(MatFlags.GENERATE_FLUID_BLOCK)) {
            BlockFluidBase fluidBlock = new BlockFluidClassic(fluid, net.minecraft.block.material.Material.WATER);
            fluidBlock.setRegistryName("fluid." + materialName);
            MetaBlocks.FLUID_BLOCKS.add(fluidBlock);
        }
        fluidToMaterialMappings.put(fluid.getName(), material);
        return fluid;
    }

    public static FluidMaterial getMaterialFromFluid(Fluid fluid) {
        return fluidToMaterialMappings.get(fluid.getName());
    }

    private static void setFluidProperties(Fluid fluid, FluidType type, FluidMaterial material) {
        switch(type) {
            case LIQUID:
                fluid.setGaseous(false);
                fluid.setViscosity(1000);
                material.setMaterialFluid(fluid);
                break;
            case GAS:
                fluid.setGaseous(true);
                fluid.setDensity(-100);
                fluid.setViscosity(200);
                material.setMaterialFluid(fluid);
                break;
            case PLASMA:
                fluid.setGaseous(true);
                fluid.setDensity(55536);
                fluid.setViscosity(10);
                fluid.setLuminosity(15);
                material.setMaterialPlasma(fluid);
                break;
            case SLURRY:
            	fluid.setGaseous(false);
            	fluid.setDensity(3000);
            case CLEANSLURRY:
            	fluid.setGaseous(false);
            	fluid.setDensity(2800);
        }
    }

}
