package gregtech.integration.tinkers;

import javax.annotation.Nonnull;

import gregtech.api.GTValues;
import nc.capability.radiation.RadiationCapabilityHandler;
import nc.capability.radiation.entity.IEntityRads;
import nc.capability.radiation.source.IRadiationSource;
import nc.config.NCConfig;
import nc.radiation.RadSources;
import nc.radiation.RadiationHandler;
import nc.util.RadiationHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;

public class TraitRadioactive extends AbstractTraitLeveled {
	
	public static final @Nonnull TraitRadioactive instance = new TraitRadioactive(1);
	public static final @Nonnull TraitRadioactive instance2 = new TraitRadioactive(2);
	public static final @Nonnull TraitRadioactive instance3 = new TraitRadioactive(3);
	public static final @Nonnull TraitRadioactive instance4 = new TraitRadioactive(4);

	public TraitRadioactive(int levels) {
		super("radioactive", String.valueOf(levels), 0x30FF37, 4, levels);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(entity == null) return;
		if(!entity.isEntityAlive()) return;
		EntityLivingBase entityLiving = (EntityLivingBase)entity;
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 1, 1));
		int tick = entity.ticksExisted;
		if(tick > 40) {
			tick = 0;
		}
		if(GTValues.isModLoaded("nuclearcraft") && tick == 40) {
			Chunk chunk = world.getChunkFromBlockCoords(entity.getPosition());
			IRadiationSource chunkRadiation = chunk.getCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null);
			RadiationHelper.addToSourceRadiation(chunkRadiation, RadSources.URANIUM * levels);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if(!world.isRemote) {
			if(player.world.rand.nextFloat() < 0.09f * levels) {
				player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100, 1));
			}
			if(player.world.rand.nextFloat() < 0.01f * levels) {
				player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 20, 1));
			}
		}
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		damage += levels;
	    return newDamage;
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if(wasHit && !player.world.isRemote) {
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, levels));
			if(player.world.rand.nextFloat() < 0.05f * levels) {
				player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100, 1));
			}
			if(target.isEntityAlive()) {
				target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 60, levels));
			}
		}
	}
}
