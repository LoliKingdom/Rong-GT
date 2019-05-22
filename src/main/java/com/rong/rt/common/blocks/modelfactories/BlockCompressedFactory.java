package com.rong.rt.common.blocks.modelfactories;

import java.util.ArrayList;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.rong.rt.api.model.AbstractBlockModelFactory;
import com.rong.rt.api.model.ResourcePackHook;
import com.rong.rt.api.unification.materials.MaterialIconType;
import com.rong.rt.api.unification.materials.types.Material;
import com.rong.rt.common.blocks.BlockCompressed;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockCompressedFactory extends AbstractBlockModelFactory {

    private static final String VARIANT_DEFINITION =
            "\"$MATERIAL$\": {\n" +
                "        \"textures\": {\n" +
                "          \"all\": \"$TEXTURE$\",\n" +
                "          \"particle\": \"$TEXTURE$\"\n" +
                "        }\n" +
                "      }";

    private static final Joiner COMMA_JOINER = Joiner.on(',');

    public static void init() {
        BlockCompressedFactory factory = new BlockCompressedFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockCompressedFactory() {
        super("compressed_block", "compressed_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        ImmutableList<Material> allowedValues = ((BlockCompressed)block).variantProperty.getAllowedValues();
        ArrayList<String> variants = new ArrayList<>();
        for(Material material : allowedValues) {
            variants.add(VARIANT_DEFINITION
                    .replace("$MATERIAL$", material.toString())
                    .replace("$TEXTURE$", MaterialIconType.block.getBlockPath(material.materialIconSet).toString())
            );
        }
        return blockStateSample.replace("$VARIANTS$", COMMA_JOINER.join(variants));
    }


}
