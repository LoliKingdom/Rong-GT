package com.rong.rt.common.blocks.modelfactories;

import com.rong.rt.api.model.AbstractBlockModelFactory;
import com.rong.rt.api.model.ResourcePackHook;
import com.rong.rt.api.unification.materials.MaterialIconType;
import com.rong.rt.api.unification.materials.types.Material;
import com.rong.rt.common.blocks.BlockFrame;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockFrameFactory extends AbstractBlockModelFactory {

    public static void init() {
        BlockFrameFactory factory = new BlockFrameFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockFrameFactory() {
        super("frame_block", "frame_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        Material material = ((BlockFrame) block).frameMaterial;
        return blockStateSample.replace("$MATERIAL$", material.toString())
            .replace("$TEXTURE$", MaterialIconType.frameSide.getBlockPath(material.materialIconSet).toString())
            .replace("$TEXTURE_TOP$", MaterialIconType.frameTop.getBlockPath(material.materialIconSet).toString());
    }


}
