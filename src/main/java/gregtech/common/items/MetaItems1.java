package gregtech.common.items;

import static gregtech.common.items.MetaItems.*;

import gregtech.api.GTValues;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.items.metaitem.stats.IItemContainerItemProvider;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.RandomPotionEffect;
import gregtech.common.items.behaviors.ColorSprayBehaviour;
import gregtech.common.items.behaviors.DynamiteBehaviour;
import gregtech.common.items.behaviors.FoamSprayerBehaviour;
import gregtech.common.items.behaviors.IntCircuitBehaviour;
import gregtech.common.items.behaviors.TurbineRotorBehaviour;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class MetaItems1 extends MaterialMetaItem {

	@Override
    public void registerSubItems() {

        BOTTLE_PURPLE_DRINK = addItem(300, "bottle.purple.drink").addStats(new FoodStats(8, 0.2F, true, true, new ItemStack(Items.GLASS_BOTTLE), new RandomPotionEffect(MobEffects.HASTE, 800, 1, 90)));
        
        CARBON_FIBRE = addItem(301, "carbon_fibre");	
        
        SHAPE_EMPTY = addItem(303, "shape.empty");

        SHAPE_MOLD_PLATE = addItem(304, "shape.mold.plate");
        SHAPE_MOLD_GEAR = addItem(305, "shape.mold.gear");
        SHAPE_MOLD_INGOT = addItem(307, "shape.mold.ingot");
        SHAPE_MOLD_BLOCK = addItem(309, "shape.mold.block");
        SHAPE_MOLD_NUGGET = addItem(310, "shape.mold.nugget");
        SHAPE_MOLD_GEAR_SMALL = addItem(312, "shape.mold.gear.small");
        
        SHAPE_EXTRUDER_PLATE = addItem(313, "shape.extruder.plate");
        SHAPE_EXTRUDER_ROD = addItem(314, "shape.extruder.rod");
        SHAPE_EXTRUDER_SCREW = addItem(315, "shape.extruder.screw");
        SHAPE_EXTRUDER_RING = addItem(316, "shape.extruder.ring");
        SHAPE_EXTRUDER_CELL = addItem(317, "shape.extruder.cell");
        SHAPE_EXTRUDER_BLOCK = addItem(318, "shape.extruder.block");
        SHAPE_EXTRUDER_WIRE = addItem(319, "shape.extruder.wire");
        SHAPE_EXTRUDER_PIPE_TINY = addItem(320, "shape.extruder.pipe.tiny");
        SHAPE_EXTRUDER_PIPE_SMALL = addItem(321, "shape.extruder.pipe.small");
        SHAPE_EXTRUDER_PIPE_MEDIUM = addItem(322, "shape.extruder.pipe.medium");
        SHAPE_EXTRUDER_PIPE_LARGE = addItem(323, "shape.extruder.pipe.large");
        SHAPE_EXTRUDER_GEAR = addItem(324, "shape.extruder.gear");
        SHAPE_EXTRUDER_FILE = addItem(325, "shape.extruder.file");
        SHAPE_EXTRUDER_HAMMER = addItem(327, "shape.extruder.hammer");
        SHAPE_EXTRUDER_SAW = addItem(328, "shape.extruder.saw");
	
        SPRAY_EMPTY = addItem(329, "spray.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Tin, OrePrefix.plate.materialAmount * 2L), 
        																			   new MaterialStack(Materials.Redstone, OrePrefix.dust.materialAmount)));

        for (int i = 0; i < EnumDyeColor.values().length; i++) {
        	EnumDyeColor dyeColor = EnumDyeColor.values()[i];
            SPRAY_CAN_DYES[i] = addItem(330 * i, "spray.can.dyes." + dyeColor.getName()).setMaxStackSize(1);
            ColorSprayBehaviour behaviour = new ColorSprayBehaviour(SPRAY_EMPTY.getStackForm(), 512, i);
            SPRAY_CAN_DYES[i].addStats(behaviour);
        }
        
        BATTERY_HULL_LV = addItem(400, "battery.hull.lv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount)));
        BATTERY_HULL_MV = addItem(401, "battery.hull.hv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 3L)));
        BATTERY_HULL_HV = addItem(402, "battery.hull.mv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 9L)));

        BATTERY_SU_LV_SULFURIC_ACID = addItem(403, "battery.su.lv.sulfuricacid").addStats(ElectricStats.createBattery(18000, 1, false)).setModelAmount(8);
        BATTERY_SU_LV_MERCURY = addItem(404, "battery.su.lv.mercury").addStats(ElectricStats.createBattery(32000, 1, false)).setModelAmount(8);

        BATTERY_RE_LV_CADMIUM = addItem(405, "battery.re.lv.cadmium").addStats(ElectricStats.createRechargeableBattery(75000, 1)).setModelAmount(8);
        BATTERY_RE_LV_LITHIUM = addItem(406, "battery.re.lv.lithium").addStats(ElectricStats.createRechargeableBattery(100000, 1)).setModelAmount(8);
        BATTERY_RE_LV_SODIUM = addItem(407, "battery.re.lv.sodium").addStats(ElectricStats.createRechargeableBattery(50000, 1)).setModelAmount(8);

        BATTERY_SU_MV_SULFURIC_ACID = addItem(408, "battery.su.mv.sulfuricacid").addStats(ElectricStats.createBattery(72000, 2, false)).setModelAmount(8);
        BATTERY_SU_MV_MERCURY = addItem(409, "battery.su.mv.mercury").addStats(ElectricStats.createBattery(128000, 2, false)).setModelAmount(8);

        BATTERY_RE_MV_CADMIUM = addItem(410, "battery.re.mv.cadmium").addStats(ElectricStats.createRechargeableBattery(300000, 2)).setModelAmount(8);
        BATTERY_RE_MV_LITHIUM = addItem(411, "battery.re.mv.lithium").addStats(ElectricStats.createRechargeableBattery(400000, 2)).setModelAmount(8);
        BATTERY_RE_MV_SODIUM = addItem(412, "battery.re.mv.sodium").addStats(ElectricStats.createRechargeableBattery(200000, 2)).setModelAmount(8);

        BATTERY_SU_HV_SULFURIC_ACID = addItem(413, "battery.su.hv.sulfuricacid").addStats(ElectricStats.createBattery(288000, 3, false)).setModelAmount(8);
        BATTERY_SU_HV_MERCURY = addItem(414, "battery.su.hv.mercury").addStats(ElectricStats.createBattery(512000, 3, false)).setModelAmount(8);

        BATTERY_RE_HV_CADMIUM = addItem(415, "battery.re.hv.cadmium").addStats(ElectricStats.createRechargeableBattery(1200000, 3)).setModelAmount(8);
        BATTERY_RE_HV_LITHIUM = addItem(416, "battery.re.hv.lithium").addStats(ElectricStats.createRechargeableBattery(1600000, 3)).setModelAmount(8);
        BATTERY_RE_HV_SODIUM = addItem(417, "battery.re.hv.sodium").addStats(ElectricStats.createRechargeableBattery(800000, 3)).setModelAmount(8);

        ENERGY_LAPOTRONIC_ORB = addItem(418, "energy.lapotronicorb").addStats(ElectricStats.createRechargeableBattery(100000000, GTValues.IV)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Master).setModelAmount(8);
        ENERGY_LAPOTRONIC_ORB2 = addItem(419, "energy.lapotronicorb2").addStats(ElectricStats.createRechargeableBattery(Integer.MAX_VALUE, GTValues.LuV)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Expert).setModelAmount(8);
	
        ELECTRIC_MOTOR_LV = addItem(420, "electric.motor.lv");
        ELECTRIC_MOTOR_MV = addItem(421, "electric.motor.mv");
        ELECTRIC_MOTOR_HV = addItem(423, "electric.motor.hv");
        ELECTRIC_MOTOR_EV = addItem(424, "electric.motor.ev");
        ELECTRIC_MOTOR_IV = addItem(425, "electric.motor.iv");
        ELECTRIC_MOTOR_LuV = addItem(426, "electric.motor.luv");
        ELECTRIC_MOTOR_UV = addItem(427, "electric.motor.uv");

        ELECTRIC_PUMP_LV = addItem(428, "electric.pump.lv");
        ELECTRIC_PUMP_MV = addItem(429, "electric.pump.mv");
        ELECTRIC_PUMP_HV = addItem(430, "electric.pump.hv");
        ELECTRIC_PUMP_EV = addItem(431, "electric.pump.ev");
        ELECTRIC_PUMP_IV = addItem(432, "electric.pump.iv");
        ELECTRIC_PUMP_LuV = addItem(433, "electric.pump.luv");
        ELECTRIC_PUMP_UV = addItem(434, "electric.pump.uv");
        
        CONVEYOR_MODULE_LV = addItem(442, "conveyor.module.lv");
        CONVEYOR_MODULE_MV = addItem(443, "conveyor.module.mv");
        CONVEYOR_MODULE_HV = addItem(444, "conveyor.module.hv");
        CONVEYOR_MODULE_EV = addItem(445, "conveyor.module.ev");
        CONVEYOR_MODULE_IV = addItem(446, "conveyor.module.iv");
        CONVEYOR_MODULE_LuV = addItem(447, "conveyor.module.luv");
        CONVEYOR_MODULE_UV = addItem(448, "conveyor.module.uv");

        ELECTRIC_PISTON_LV = addItem(449, "electric.piston.lv");
        ELECTRIC_PISTON_MV = addItem(450, "electric.piston.mv");
        ELECTRIC_PISTON_HV = addItem(451, "electric.piston.hv");
        ELECTRIC_PISTON_EV = addItem(452, "electric.piston.ev");
        ELECTRIC_PISTON_IV = addItem(453, "electric.piston.iv");
        ELECTRIC_PISTON_LuV = addItem(454, "electric.piston.luv");
        ELECTRIC_PISTON_UV = addItem(455, "electric.piston.uv");
        
        ROBOT_ARM_LV = addItem(456, "robot.arm.lv");
        ROBOT_ARM_MV = addItem(457, "robot.arm.mv");
        ROBOT_ARM_HV = addItem(458, "robot.arm.hv");
        ROBOT_ARM_EV = addItem(459, "robot.arm.ev");
        ROBOT_ARM_IV = addItem(460, "robot.arm.iv");
        ROBOT_ARM_LuV = addItem(461, "robot.arm.luv");
        ROBOT_ARM_UV = addItem(462, "robot.arm.uv");

        //FIELD_GENERATOR_LV = addItem(463, "field.generator.lv");
        //FIELD_GENERATOR_MV = addItem(464, "field.generator.mv");
        FIELD_GENERATOR_HV = addItem(465, "field.generator.hv");
        FIELD_GENERATOR_EV = addItem(466, "field.generator.ev");
        FIELD_GENERATOR_IV = addItem(467, "field.generator.iv");
        FIELD_GENERATOR_LuV = addItem(468, "field.generator.luv");
        FIELD_GENERATOR_UV = addItem(469, "field.generator.uv");

        EMITTER_LV = addItem(470, "emitter.lv");
        EMITTER_MV = addItem(471, "emitter.mv");
        EMITTER_HV = addItem(472, "emitter.hv");
        EMITTER_EV = addItem(473, "emitter.ev");
        EMITTER_IV = addItem(474, "emitter.iv");
        EMITTER_LuV = addItem(475, "emitter.luv");
        EMITTER_UV = addItem(476, "emitter.uv");

        SENSOR_LV = addItem(477, "sensor.lv");
        SENSOR_MV = addItem(478, "sensor.mv");
        SENSOR_HV = addItem(479, "sensor.hv");
        SENSOR_EV = addItem(480, "sensor.ev");
        SENSOR_IV = addItem(481, "sensor.iv");
        SENSOR_LuV = addItem(482, "sensor.luv");
        SENSOR_UV = addItem(483, "sensor.uv");
        
        ENERGY_LAPOTRONIC_ORB3 = addItem(484, "energy.infused_lapotronicorb").addStats(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UV)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Ultimate).setModelAmount(8);
        
        FLUID_FILTER = addItem(485, "fluid_filter");

        DYNAMITE = addItem(486, "dynamite").addStats(new DynamiteBehaviour()).setMaxStackSize(16);
        
        COMPONENT_SAW_BLADE_DIAMOND = addItem(487, "component.sawblade.diamond").addOreDict(OreDictNames.craftingDiamondBlade);
        COMPONENT_GRINDER_DIAMOND = addItem(488, "component.grinder.diamond").addOreDict(OreDictNames.craftingGrinder);
        COMPONENT_GRINDER_TUNGSTEN = addItem(489, "component.grinder.tungsten").addOreDict(OreDictNames.craftingGrinder);
        
        //TODO: Sound stuff
        UPGRADE_MUFFLER = addItem(490, "upgrade.muffler").setInvisible();

        ITEM_FILTER = addItem(491, "item_filter");
        ORE_DICTIONARY_FILTER = addItem(492, "ore_dictionary_filter");
        
        COVER_CONTROLLER = addItem(493, "cover.controller").setInvisible();
        COVER_ACTIVITY_DETECTOR = addItem(494, "cover.activity.detector").setInvisible();
        COVER_FLUID_DETECTOR = addItem(495, "cover.fluid.detector").setInvisible();
        COVER_ITEM_DETECTOR = addItem(496, "cover.item.detector").setInvisible();
        COVER_ENERGY_DETECTOR = addItem(497, "cover.energy.detector").setInvisible();
        COVER_PLAYER_DETECTOR = addItem(498, "cover.player.detector").setInvisible();

        COVER_SHUTTER = addItem(502, "cover.shutter");

        COVER_SOLAR_PANEL_LV = addItem(504, "cover.solar.panel.lv");
        COVER_SOLAR_PANEL_MV = addItem(505, "cover.solar.panel.mv");
        COVER_SOLAR_PANEL_HV = addItem(506, "cover.solar.panel.hv");
        COVER_SOLAR_PANEL_EV = addItem(507, "cover.solar.panel.ev");
        COVER_SOLAR_PANEL_IV = addItem(508, "cover.solar.panel.iv");
        COVER_SOLAR_PANEL_LuV = addItem(509, "cover.solar.panel.luv");
        COVER_SOLAR_PANEL_UV = addItem(510, "cover.solar.panel.uv");
        
        FLUID_CELL = addItem(511, "fluid_cell").addStats(new FluidStats(1000, Integer.MIN_VALUE, Integer.MAX_VALUE, false));

        INTEGRATED_CIRCUIT = addItem(512, "circuit.integrated").addStats(new IntCircuitBehaviour());
        
        FOAM_SPRAYER = addItem(513, "foam_sprayer").addStats(new FoamSprayerBehaviour());
        
        ENERGY_CRYSTAL = addItem(514, "energy_crystal").addStats(ElectricStats.createRechargeableBattery(1000000L, GTValues.HV)).setModelAmount(8).setMaxStackSize(1);
        LAPOTRON_CRYSTAL = addItem(515, "lapotron_crystal").addStats(ElectricStats.createRechargeableBattery(4000000L, GTValues.EV)).setModelAmount(8).setMaxStackSize(1);
        ENERGIUM_DUST = addItem(516, "energium_dust");

        COMPRESSED_CLAY = addItem(519, "compressed.clay");
        COMPRESSED_FIRECLAY = addItem(520, "compressed.fireclay");
        FIRECLAY_BRICK = addItem(521, "brick.fireclay");
        COKE_OVEN_BRICK = addItem(522, "brick.coke");
        
        QUANTUM_EYE = addItem(523, "quantumeye");
        QUANTUM_STAR = addItem(524, "quantumstar");
        GRAVI_STAR = addItem(525, "gravistar");
        
        FUEL_BINDER = addItem(526, "fuel_binder").setBurnValue(Materials.Coke.burnTime);
        SUPER_FUEL_BINDER = addItem(527, "super_fuel_binder").setBurnValue(Materials.Coke.burnTime * 3);
        MAGIC_FUEL_BINDER = addItem(528, "magic_fuel_binder").setBurnValue(Materials.Coke.burnTime + Materials.Coal.burnTime);

        THERMITE_DUST = addItem(529, "thermite_dust").addOreDict("dustThermite").setBurnValue(200);     
        
        CIRCUIT_BASIC_LV = addItem(530, "circuit.basic").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
        CIRCUIT_INTERMEDIATE_MV = addItem(531, "circuit.intermediate").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Intermediate);
        CIRCUIT_ADVANCED_HV = addItem(532, "circuit.advanced").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
        CIRCUIT_ELITE_EV = addItem(533, "circuit.elite").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
        CIRCUIT_MASTER_IV = addItem(534, "circuit.master").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Master);
        CIRCUIT_EXPERT_LuV = addItem(535, "circuit.expert").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Expert);
        CIRCUIT_ULTIMATE_UV = addItem(536, "circuit.ultimate").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
        CIRCUIT_MAGIC = addItem(537, "circuit.magic").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Magic);
        
        BOARD_BASIC = addItem(542, "board.basic").addOreDict("boardBasic");
        BOARD_INTERMEDIATE = addItem(543, "board.intermediate").addOreDict("boardIntermediate");
        BOARD_ADVANCED = addItem(544, "board.advanced").addOreDict("boardAdvanced");
        BOARD_ELITE = addItem(545, "board.elite").addOreDict("boardElite");
        BOARD_MASTER = addItem(546, "board.master").addOreDict("boardMaster");
        BOARD_EXPERT = addItem(547, "board.expert").addOreDict("boardExpert");
        BOARD_ULTIMATE = addItem(548, "board.ultimate").addOreDict("boardUltimate");
        BOARD_MAGIC = addItem(549, "board.magic").addOreDict("boardMagic");
        
        WIRING_BASIC = addItem(550, "wiring.basic").addOreDict("wiringBasic");
        WIRING_INTERMEDIATE = addItem(551, "wiring.intermediate").addOreDict("wiringIntermediate");
        WIRING_ADVANCED = addItem(552, "wiring.advanced").addOreDict("wiringAdvanced");
        WIRING_ELITE = addItem(553, "wiring.elite").addOreDict("wiringElite");
        WIRING_MASTER = addItem(554, "wiring.master").addOreDict("wiringMaster");
        WIRING_EXPERT = addItem(555, "wiring.expert").addOreDict("wiringExpert");
        WIRING_ULTIMATE = addItem(556, "wiring.ultimate").addOreDict("wiringUltimate");
        WIRING_MAGIC = addItem(557, "wiring.magic").addOreDict("wiringMagic");
        
        SOC_BASIC = addItem(558, "soc.basic").addOreDict("socBasic");
        SOC_INTERMEDIATE = addItem(559, "soc.intermediate").addOreDict("socIntermediate");
        SOC_ADVANCED = addItem(560, "soc.advanced").addOreDict("socAdvanced");
        SOC_ELITE = addItem(561, "soc.elite").addOreDict("socElite");
        SOC_MASTER = addItem(562, "soc.master").addOreDict("socMaster");
        SOC_EXPERT = addItem(563, "soc.expert").addOreDict("socExpert");
        SOC_ULTIMATE = addItem(564, "soc.ultimate").addOreDict("socUltimate");
        SOC_MAGIC = addItem(565, "soc.magic").addOreDict("socMagic");
        
        GLASS_TUBE = addItem(538, "glass_tube");
        CIRCUIT_VACUUM_TUBE_LV = addItem(539, "vacuum_tube").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
        PHENOLIC_BOARD = addItem(566, "phenolic_board");
        
        TURBINE_ROTOR = addItem(540, "turbine_rotor").addStats(new TurbineRotorBehaviour());
        
        RUBBER_DROP = addItem(541, "rubber_drop");
	}
	
	public void registerRecipes() {

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.Redstone).input(OrePrefix.plate, Materials.Tin, 3)
            .outputs(SPRAY_EMPTY.getStackForm())
            .duration(200).EUt(8)
            .buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
        	.input(OrePrefix.plate, Materials.Tin, 6)
        	.inputs(SPRAY_EMPTY.getStackForm())
        	.input(OrePrefix.paneGlass.name(), 1)
        	.outputs(FOAM_SPRAYER.getStackForm())
        	.duration(200).EUt(8)
        	.buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_LV_SULFURIC_ACID.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_LV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_MV_SULFURIC_ACID.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_MV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_HV_SULFURIC_ACID.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_HV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        // Upgrades recipes
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Aluminium).input(OrePrefix.dust, Materials.AcrylonitrileButadieneStyrene, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Aluminium).input(OrePrefix.dust, Materials.Wood, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron).input(OrePrefix.dust, Materials.AcrylonitrileButadieneStyrene, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron).input(OrePrefix.dust, Materials.Wood, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.WroughtIron).input(OrePrefix.dust, Materials.AcrylonitrileButadieneStyrene, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.WroughtIron).input(OrePrefix.dust, Materials.Wood, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
	}
}
