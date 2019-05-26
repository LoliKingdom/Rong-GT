package com.rong.rt.common.items;

import static com.rong.rt.common.items.MetaItems.*;

import com.rong.rt.api.OreDictNames;
import com.rong.rt.api.metaitems.MaterialMetaItem;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.MarkerMaterials;
import com.rong.rt.api.unification.materials.Materials;
import com.rong.rt.common.items.behaviour.TurbineRotorBehaviour;

public class MetaItems1 extends MaterialMetaItem {
	
	@Override
    public void registerSubItems() {
		
		INTEGRATED_CIRCUIT = addItem(300, "circuit.integrated").addOreDict("circuitIntegrated");
       
        CARBON_FIBRE = addItem(301, "carbon_fibre").addOreDict("fibreCarbon");	
        
        DYNAMITE = addItem(302, "dynamite");
        
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

        
        COMPONENT_SAW_BLADE_DIAMOND = addItem(487, "component.sawblade.diamond").addOreDict(OreDictNames.craftingDiamondBlade);
        COMPONENT_GRINDER_DIAMOND = addItem(488, "component.grinder.diamond").addOreDict(OreDictNames.craftingGrinder);
        COMPONENT_GRINDER_TUNGSTEN = addItem(489, "component.grinder.tungsten").addOreDict(OreDictNames.craftingGrinder);
        
        //FLUID_CELL = addItem(511, "fluid_cell").addStats(new FluidStats(1000, Integer.MIN_VALUE, Integer.MAX_VALUE, false));

        COMPRESSED_CLAY = addItem(519, "compressed.clay");
        COMPRESSED_FIRECLAY = addItem(520, "compressed.fireclay");
        FIRECLAY_BRICK = addItem(521, "brick.fireclay");
        COKE_OVEN_BRICK = addItem(522, "brick.coke");
        
        QUANTUM_EYE = addItem(523, "quantum.eye");
        QUANTUM_STAR = addItem(524, "quantum.star");
        GRAVI_STAR = addItem(525, "gravi.star");
        
        FUEL_BINDER = addItem(526, "fuel_binder").setBurnValue(Materials.Coke.burnTime);
        SUPER_FUEL_BINDER = addItem(527, "super_fuel_binder").setBurnValue(Materials.Coke.burnTime * 3);
        MAGIC_FUEL_BINDER = addItem(528, "magic_fuel_binder").setBurnValue(Materials.Coke.burnTime + Materials.Coal.burnTime);

        THERMITE_DUST = addItem(529, "thermite_dust").addOreDict("dustThermite").setBurnValue(2000);     
        
        CIRCUIT_BASIC_LV = addItem(530, "circuit.basic").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Basic);
        CIRCUIT_INTERMEDIATE_MV = addItem(531, "circuit.intermediate").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Intermediate);
        CIRCUIT_ADVANCED_HV = addItem(532, "circuit.advanced").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Advanced);
        CIRCUIT_ELITE_EV = addItem(533, "circuit.elite").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Elite);
        CIRCUIT_MASTER_IV = addItem(534, "circuit.master").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Master);
        CIRCUIT_EXPERT_LuV = addItem(535, "circuit.expert").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Expert);
        CIRCUIT_ULTIMATE_UV = addItem(536, "circuit.ultimate").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
        CIRCUIT_MAGIC = addItem(537, "circuit.magic").setUnificationData(EnumOrePrefix.circuit, MarkerMaterials.Tier.Magic);
        
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

        PHENOLIC_BOARD = addItem(566, "phenolic_board").addOreDict("boardPhenolic");
        
        //TURBINE_ROTOR = addItem(540, "turbine_rotor").addStats(new TurbineRotorBehaviour());
        
        RUBBER_DROP = addItem(541, "rubber_drop");
	}

}
