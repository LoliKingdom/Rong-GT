package gregtech.common.metatileentities.electric;

import java.util.Random;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityLightningHarvester extends TieredMetaTileEntity {
	
	Random random = new Random();

	public MetaTileEntityLightningHarvester(ResourceLocation metaTileEntityId, int tier) {
		super(metaTileEntityId, tier);
		this.energyContainer = new EnergyContainerHandler(this, 25000000L * getTier(), 0L, 0L, GTValues.V[GTValues.UV], 4L);
	}
	
	private long lightningBolt = energyContainer.getEnergyCapacity() / getTier();

	@Override
	public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
		return new MetaTileEntityLightningHarvester(metaTileEntityId, getTier());
	}
	
	@Override
    public void update() {
		super.update();
		if(getWorld().isRemote) {
            return;
		}
		if(getWorld().isThundering() || getWorld().isRaining() || this.energyContainer.getEnergyCanBeInserted() >= lightningBolt) {
			if(getTimer() % 4800 == 0) {
				if(getPos().getY() > 70) {
					if(random.nextInt(32) == 16) {
						getWorld().addWeatherEffect(new EntityLightningBolt(getWorld(), getPos().getX(), getPos().getY() + 1, getPos().getZ(), true));
						this.energyContainer.addEnergy(lightningBolt);
					}
				}				
			}
		}		
	}
	
	@Override
	protected boolean isEnergyEmitter() {
        return true;
    }
	
	@Override
	protected long getMaxInputOutputAmperage() {
        return 3L;
    }
	
	@Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

	@Override
	protected ModularUI createUI(EntityPlayer entityPlayer) {
		return null;
	}
}