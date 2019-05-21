package com.rong.rt.api.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.rong.rt.Values;
import com.rong.rt.api.utils.FileUtility;

import net.minecraft.block.Block;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class AbstractBlockModelFactory implements ResourcePackHook.IResourcePackFileHook {

    private final ResourceLocation sampleResourceLocation;
    private final String blockNamePrefix;

    public AbstractBlockModelFactory(String sampleName, String blockNamePrefix) {
        this.sampleResourceLocation = sampleName == null ? null : new ResourceLocation(Values.MOD_ID, "blockstates/autogenerated/" + sampleName + ".json");
        this.blockNamePrefix = blockNamePrefix;
    }

    private String blockStateSample;

    protected abstract String fillSample(Block block, String blockStateSample);

    @Override
    public void onResourceManagerReload(SimpleReloadableResourceManager resourceManager) {
        if(sampleResourceLocation != null) {
            try {
                blockStateSample = FileUtility.readInputStream(resourceManager.getResource(sampleResourceLocation).getInputStream());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @Override
    public boolean resourceExists(ResourceLocation location) {
        return location.getResourceDomain().equals(Values.MOD_ID)
            && location.getResourcePath().startsWith("blockstates/" + blockNamePrefix)
            && !location.getResourcePath().contains(".mcmeta");
    }

    @Override
    public InputStream getInputStream(ResourceLocation location) throws IOException {
        String resourcePath = location.getResourcePath(); // blockstates/compressed_1.json
        resourcePath = resourcePath.substring(0, resourcePath.length() - 5); //remove .json
        resourcePath = resourcePath.substring(12); //remove blockstates/
        if(resourcePath.startsWith(blockNamePrefix)) {
            Block block = Block.REGISTRY.getObject(new ResourceLocation(location.getResourceDomain(), resourcePath));
            if(block != null && block != Blocks.AIR) {
                return FileUtility.writeInputStream(fillSample(block, blockStateSample));
            }
            throw new IllegalArgumentException("Block not found: " + resourcePath);
        }
        throw new FileNotFoundException(location.toString());
    }

}
