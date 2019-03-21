package gregtech.loaders.recipes;

import gregtech.api.items.OreDictNames;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;

public enum CraftingComponent {
    CIRCUIT {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
                case 1:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Intermediate);
                case 2:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
                case 3:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
                case 4:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master);
                case 5:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Expert);
                case 6:
                	return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
                default:
                    return new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    PUMP {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.ELECTRIC_PUMP_LV;
                case 1:
                    return MetaItems.ELECTRIC_PUMP_MV;
                case 2:
                    return MetaItems.ELECTRIC_PUMP_HV;
                case 3:
                    return MetaItems.ELECTRIC_PUMP_EV;
                case 4:
                    return MetaItems.ELECTRIC_PUMP_IV;
                case 5:
                    return MetaItems.ELECTRIC_PUMP_LuV;
                default:
                    return MetaItems.ELECTRIC_PUMP_UV;
            }
        }
    },
    CABLE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin);
                case 1:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper);
                case 2:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold);
                case 3:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium);
                case 4:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.Platinum);
                case 5:
                    return new UnificationEntry(OrePrefix.cableGtSingle, Materials.NiobiumTitanium);
                default:
                    return new UnificationEntry(OrePrefix.cableGtSingle, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    WIRE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Lead);
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Nickel);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Silver);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Titanium);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Osmium);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.VanadiumGallium);
                default:
                    return new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    CABLE_QUAD {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.RedAlloy);
                case 1:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Iron);
                case 2:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Electrum);
                case 3:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Nichrome);
                case 4:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Graphene);
                case 5:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.YttriumBariumCuprate);
                default:
                    return new UnificationEntry(OrePrefix.cableGtQuadruple, MarkerMaterials.Tier.Superconductor);
            }
        }
    },
    HULL {
        @Override
        Object getIngredient(int tier) {
            return MetaTileEntities.HULL[tier].getStackForm();
        }
    },
    TRANSFORMER {
        @Override
        Object getIngredient(int tier) {
            return MetaTileEntities.TRANSFORMER[tier].getStackForm();
        }
    },
    PIPE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                case 1:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze);
                case 2:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel);
                case 3:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel);
                case 4:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium);
                case 5:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel);
                default:
                    return new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel);
            }
        }
    },
    TANK {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
            case 0:
            	return MetaTileEntities.BRONZE_TANK.getStackForm();
            case 1:
            	return MetaTileEntities.STEEL_TANK.getStackForm();
            case 2:
            	return MetaTileEntities.STAINLESS_STEEL_TANK.getStackForm();
            case 3:
            	return MetaTileEntities.TITANIUM_TANK.getStackForm();
            case 4:
            	return MetaTileEntities.TUNGSTENSTEEL_TANK.getStackForm();
            case 5:
            	return MetaTileEntities.IRIDIUM_TANK.getStackForm();
            case 6:
            	return MetaTileEntities.ADAMANTINE_TANK.getStackForm();
            default:
            	return MetaTileEntities.WOODEN_TANK.getStackForm();           		
            }
        }
    },
    PLATE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.plate, Materials.Steel);
                case 1:
                    return new UnificationEntry(OrePrefix.plate, Materials.Aluminium);
                case 2:
                    return new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel);
                case 3:
                    return new UnificationEntry(OrePrefix.plate, Materials.Titanium);
                case 4:
                    return new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel);
                case 5:
                    return new UnificationEntry(OrePrefix.plate, Materials.HSSG);
                case 6:
                    return new UnificationEntry(OrePrefix.plate, Materials.HSSE);
                default:
                    return new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel);
            }
        }
    },
    MOTOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.ELECTRIC_MOTOR_LV;
                case 1:
                    return MetaItems.ELECTRIC_MOTOR_MV;
                case 2:
                    return MetaItems.ELECTRIC_MOTOR_HV;
                case 3:
                    return MetaItems.ELECTRIC_MOTOR_EV;
                case 4:
                    return MetaItems.ELECTRIC_MOTOR_IV;
                case 5:
                    return MetaItems.ELECTRIC_MOTOR_LuV;
                default:
                    return MetaItems.ELECTRIC_MOTOR_UV;
            }
        }
    },
    ROTOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Bronze);
                case 1:
                    return new UnificationEntry(OrePrefix.rotor, Materials.SterlingSilver);
                case 2:
                    return new UnificationEntry(OrePrefix.rotor, Materials.VanadiumSteel);
                case 3:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Magnalium);
                case 4:
                    return new UnificationEntry(OrePrefix.rotor, Materials.TungstenSteel);
                case 5:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Ultimet);
                default:
                    return new UnificationEntry(OrePrefix.rotor, Materials.Osmiridium);
            }
        }
    },
    SENSOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.SENSOR_LV;
                case 1:
                    return MetaItems.SENSOR_MV;
                case 2:
                    return MetaItems.SENSOR_HV;
                case 3:
                    return MetaItems.SENSOR_EV;
                case 4:
                    return MetaItems.SENSOR_IV;
                case 5:
                    return MetaItems.SENSOR_LuV;
                default:
                    return MetaItems.SENSOR_UV;
            }
        }
    },
    GRINDER {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                	return new UnificationEntry(OrePrefix.block, Materials.Flint);
                case 1:
                    return new UnificationEntry(OrePrefix.gem, Materials.Peridot);
                case 2:
                    return new UnificationEntry(OrePrefix.gem, Materials.Emerald);
                case 3:
                    return MetaItems.COMPONENT_GRINDER_DIAMOND;
                default:
                    return MetaItems.COMPONENT_GRINDER_TUNGSTEN;
            }
        }
    },
    PISTON {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.ELECTRIC_PISTON_LV;
                case 1:
                    return MetaItems.ELECTRIC_PISTON_MV;
                case 2:
                    return MetaItems.ELECTRIC_PISTON_HV;
                case 3:
                    return MetaItems.ELECTRIC_PISTON_EV;
                case 4:
                    return MetaItems.ELECTRIC_PISTON_IV;
                case 5:
                    return MetaItems.ELECTRIC_PISTON_LuV;
                default:
                    return MetaItems.ELECTRIC_PISTON_UV;
            }
        }
    },
    EMITTER {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.EMITTER_LV;
                case 1:
                    return MetaItems.EMITTER_MV;
                case 2:
                    return MetaItems.EMITTER_HV;
                case 3:
                    return MetaItems.EMITTER_EV;
                case 4:
                    return MetaItems.EMITTER_IV;
                case 5:
                    return MetaItems.EMITTER_LuV;
                default:
                    return MetaItems.EMITTER_UV;
            }
        }
    },
    CONVEYOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.CONVEYOR_MODULE_LV;
                case 1:
                    return MetaItems.CONVEYOR_MODULE_MV;
                case 2:
                    return MetaItems.CONVEYOR_MODULE_HV;
                case 3:
                    return MetaItems.CONVEYOR_MODULE_EV;
                case 4:
                    return MetaItems.CONVEYOR_MODULE_IV;
                case 5:
                    return MetaItems.CONVEYOR_MODULE_LuV;
                default:
                    return MetaItems.CONVEYOR_MODULE_UV;
            }
        }
    },
    ROBOT_ARM {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return MetaItems.ROBOT_ARM_LV;
                case 1:
                    return MetaItems.ROBOT_ARM_MV;
                case 2:
                    return MetaItems.ROBOT_ARM_HV;
                case 3:
                    return MetaItems.ROBOT_ARM_EV;
                case 4:
                    return MetaItems.ROBOT_ARM_IV;
                case 5:
                    return MetaItems.ROBOT_ARM_LuV;
                default:
                    return MetaItems.ROBOT_ARM_UV;
            }
        }
    },
    COIL_HEATING {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Copper);
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Cupronickel);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Kanthal);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Nichrome);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.TungstenSteel);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.HSSG);
                default:
                    return new UnificationEntry(OrePrefix.wireGtOctal, Materials.Nichrome);
            }
        }
    },
    COIL_ELECTRIC {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin);
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Lead);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtDouble, Materials.Copper);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Nickel);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtOctal, Materials.AnnealedCopper);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtOctal, Materials.Graphene);
                default:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.YttriumBariumCuprate);
            }
        }
    },
    STICK_MAGNETIC {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.stick, Materials.IronMagnetic);
                case 1:
                	return new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic);
                case 2:
                    return new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic);
                case 3:
                	return new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic);
                case 4:
                    return new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic);
                default:
                    return new UnificationEntry(OrePrefix.block, Materials.NeodymiumMagnetic);
            }
        }
    },
    FIELD_GENERATOR {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 2:
                    return MetaItems.FIELD_GENERATOR_HV;
                case 3:
                    return MetaItems.FIELD_GENERATOR_EV;
                case 4:
                    return MetaItems.FIELD_GENERATOR_IV;
                case 5:
                    return MetaItems.FIELD_GENERATOR_LuV;
                default:
                    return MetaItems.FIELD_GENERATOR_UV;
            }
        }
    },
    COIL_HEATING_DOUBLE {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Copper);
                case 1:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Cupronickel);
                case 2:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Kanthal);
                case 3:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Nichrome);
                case 4:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.TungstenSteel);
                case 5:
                    return new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.HSSG);
                default:
                    return new UnificationEntry(OrePrefix.wireGtHex, Materials.Naquadah);
            }
        }
    },
    STICK_ELECTROMAGNETIC {
        @Override
        Object getIngredient(int tier) {
            switch (tier) {
                case 0:
                    return new UnificationEntry(OrePrefix.stick, Materials.Iron);
                case 1:
                	return new UnificationEntry(OrePrefix.stick, Materials.Steel);
                case 2:
                    return new UnificationEntry(OrePrefix.stick, Materials.Steel);
                case 3:
                    return new UnificationEntry(OrePrefix.stick, Materials.Neodymium);
                default:
                    return new UnificationEntry(OrePrefix.stick, Materials.VanadiumGallium);
            }
        }
    },
    BATTERY {
    	@Override
    	Object getIngredient(int tier) {
    		switch (tier) {
    			case 1:
    				return MetaItems.BATTERY_SU_HV_SULFURIC_ACID;
    			case 2:
    				return MetaItems.BATTERY_RE_HV_CADMIUM;
    			case 3:
    				return MetaItems.ENERGY_CRYSTAL;
    			case 4:
    				return MetaItems.ENERGY_LAPOTRONIC_ORB;
    			case 5:
    				return MetaItems.ENERGY_LAPOTRONIC_ORB2;
    			default:
    				return MetaItems.ENERGY_LAPOTRONIC_ORB3;
    		}
    	}
    };
	abstract Object getIngredient(int tier);
}