package gregtech.common.metatileentities.multi.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

import static gregtech.api.multiblock.BlockPattern.RelativeDirection.*;

public class MetaTileEntityFusionReactor extends RecipeMapMultiblockController {
	
	private final int tier;
	private EnergyContainerList inputEnergyContainers;

	public MetaTileEntityFusionReactor(ResourceLocation metaTileEntityId, int tier) {
		super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
		this.recipeMapWorkable = new MultiblockRecipeMapWorkable(this) {
			@Override
			protected int getOverclockingTier(long voltage) {
				return 0;
			}
		};
		this.tier = tier;
		this.reinitializeStructurePattern();
		this.energyContainer = new EnergyContainerHandler(this, Integer.MAX_VALUE, 0, 0, 0, 0) {
			@Override
			public String getName() {
				return "EnergyContainerInternal";
			}
		};
	}

	@Override
	public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
		return new MetaTileEntityFusionReactor(metaTileEntityId, tier);
	}

	@Override
	protected BlockPattern createStructurePattern() {
		FactoryBlockPattern.start();
		return FactoryBlockPattern.start(LEFT, DOWN, BACK)
				.aisle("###############", "######OCO######", "###############")
				.aisle("######ICI######", "####CCcccCC####", "######ICI######")
				.aisle("####CC###CC####", "###EccOCOccE###", "####CC###CC####")
				.aisle("###C#######C###", "##EcEC###CEcE##", "###C#######C###")
				.aisle("##C#########C##", "#CcE#######EcC#", "##C#########C##")
				.aisle("##C#########C##", "#CcC#######CcC#", "##C#########C##")
				.aisle("#I###########I#", "OcO#########OcO", "#I###########I#")
				.aisle("#C###########C#", "CcC#########CcC", "#C###########C#")
				.aisle("#I###########I#", "OcO#########OcO", "#I###########I#")
				.aisle("##C#########C##", "#CcC#######CcC#", "##C#########C##")
				.aisle("##C#########C##", "#CcE#######EcC#", "##C#########C##")
				.aisle("###C#######C###", "##EcEC###CEcE##", "###C#######C###")
				.aisle("####CC###CC####", "###EccOCOccE###", "####CC###CC####")
				.aisle("######ICI######", "####CCcccCC####", "######ICI######")
				.aisle("###############", "######OSO######", "###############")
				.where('S', selfPredicate())
				.where('C', statePredicate(getCasingState()))
				.where('c', statePredicate(getCoilState()))
				.where('O', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_FLUIDS)))
				.where('I', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
				.where('E', statePredicate(getCasingState()).or(tilePredicate((state, tile) -> {
					for (int i = tier; i < GTValues.V.length; i++) {
						if (tile.metaTileEntityId.equals(MetaTileEntities.ENERGY_INPUT_HATCH[i].metaTileEntityId)) return true;
					}
					return false;
				})))
				.where('#', (tile) -> true)
				.build();
		}

	@Override
	public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
		return Textures.FUSION_BASE;
	}

	private IBlockState getCasingState() {
		switch(tier) {
		case 5:
			return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.FUSION_CASING);
		default:
			return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.FUSION_CASING_MK2);
		}
	}

	private IBlockState getCoilState() {
		switch(tier) {
		case 5:
			return MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.FUSION_COIL);
		default:
			return MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.SUPERCONDUCTOR);
		}
	}

	private long getMaxEU() {
		return this.inputEnergyContainers.getContainerList().size() * 100000L * (tier - 4);
	}

	@Override
	protected void formStructure(PatternMatchContext context) {
		long energyStored = energyContainer.getEnergyStored();
		super.formStructure(context);
		this.initializeAbilities();
		((EnergyContainerHandler)energyContainer).setEnergyStored(energyStored);
	}

	private void initializeAbilities() {
		this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
		this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
		this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
		this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
		this.inputEnergyContainers = new EnergyContainerList(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
		this.energyContainer = new EnergyContainerHandler(this, getMaxEU(), GTValues.V[tier], 0, 0, 0) {
			@Override
			public String getName() {
				return "EnergyContainerInternal";
			}
		};
	}

	@Override
	protected void updateFormedValid() {
		if(!getWorld().isRemote) {
			if(inputEnergyContainers.getEnergyStored() > 0) {
				long energyAdded = energyContainer.addEnergy(inputEnergyContainers.getEnergyStored());
				if(energyAdded > 0)  {
					inputEnergyContainers.addEnergy(-(energyAdded));
				}
			}
			if(recipeMapWorkable.isWorkingEnabled()) {
				Recipe previousRecipe = recipeMapWorkable.getPreviousRecipe();
				recipeMapWorkable.updateWorkable();
				Recipe currentRecipe = recipeMapWorkable.getPreviousRecipe();
				if(previousRecipe != null && currentRecipe != null && previousRecipe != currentRecipe) {
					int euToStart = currentRecipe.getProperty("eu_to_start");
					if(energyContainer.getEnergyStored() >= euToStart) {
						recipeMapWorkable.setActive(true);
					}
				}
				/*if(recipeMapWorkable.notEnoughEnergy()) {
					recipeMapWorkable.setActive(false);
					recipeMapWorkable.setMaxProgress(0);
					ObfuscationReflectionHelper.setPrivateValue(RecipeMapWorkableHandler.class, recipeMapWorkable, 0, "progressTime");
					recipeMapWorkable.setMaxProgress(0);
					ObfuscationReflectionHelper.setPrivateValue(RecipeMapWorkableHandler.class, recipeMapWorkable, 0, "recipeEUt");
					ObfuscationReflectionHelper.setPrivateValue(RecipeMapWorkableHandler.class, recipeMapWorkable, null, "fluidOutputs");
					ObfuscationReflectionHelper.setPrivateValue(RecipeMapWorkableHandler.class, recipeMapWorkable, null, "itemOutputs");
					ObfuscationReflectionHelper.setPrivateValue(RecipeMapWorkableHandler.class, recipeMapWorkable, false, "hasNotEnoughEnergy");
					ObfuscationReflectionHelper.setPrivateValue(RecipeMapWorkableHandler.class, recipeMapWorkable, true, "wasActiveAndNeedsUpdate");
					return;
				}*/
			}
		}
	}

	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		if(!isStructureFormed()) {
			textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure").setStyle((new Style()).setColor(TextFormatting.RED))));
		}
		if(this.isStructureFormed()) {
			if(!recipeMapWorkable.isWorkingEnabled()) {
				textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
			} 
			else if(recipeMapWorkable.isActive()) {
				textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
				int currentProgress;
				if(energyContainer.getEnergyCapacity() > 0) {
					currentProgress = (int) (this.recipeMapWorkable.getProgressPercent() * 100.0D);
					textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
				} 
				else {
					currentProgress = -recipeMapWorkable.getRecipeEUt();
					textList.add(new TextComponentTranslation("gregtech.multiblock.generation_eu", currentProgress));
				}
			} 
			else {
				textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
			}
			if(this.recipeMapWorkable.notEnoughEnergy()) {
				textList.add((new TextComponentTranslation("gregtech.multiblock.not_enough_energy").setStyle((new Style()).setColor(TextFormatting.RED))));
			}
		}
		textList.add(new TextComponentString("EU: " + this.energyContainer.getEnergyStored() + " / " + this.energyContainer.getEnergyCapacity()));
	}

	@Override
	public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
		this.getBaseTexture(null).render(renderState, translation, pipeline);
		Textures.FUSION_REACTOR_OVERLAY.render(renderState, translation, pipeline, this.getFrontFacing(), this.recipeMapWorkable.isActive());
	}
}
