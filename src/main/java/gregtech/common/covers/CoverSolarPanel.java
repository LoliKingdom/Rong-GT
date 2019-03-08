package gregtech.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import gregtech.api.render.Textures;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CoverSolarPanel extends CoverBehavior implements ITickable {

    private final long EUt;

    public CoverSolarPanel(ICoverable coverHolder, EnumFacing attachedSide, long EUt) {
        super(coverHolder, attachedSide);
        this.EUt = EUt;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null) != null && attachedSide == EnumFacing.UP;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.SOLAR_PANEL.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public void update() {
        World world = coverHolder.getWorld();
        BlockPos blockPos = coverHolder.getPos().up();
        if(world.canSeeSky(blockPos)) {
            IEnergyContainer energyContainer = coverHolder.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
            if(energyContainer != null) {
            	if(world.isRaining()) {
            		if(!world.isDaytime()) {
            			energyContainer.addEnergy(EUt / 16);
            		}
            		energyContainer.addEnergy(EUt / 4);
            	}
            	if(!world.isDaytime()) {
            		energyContainer.addEnergy(EUt / 8);
            	}
                energyContainer.addEnergy(EUt);
            }
        }
    }
}
