package com.rong.rt.common.blocks.modelfactories;

import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.rong.rt.api.model.AbstractBlockModelFactory;
import com.rong.rt.api.model.ResourcePackHook;
import com.rong.rt.api.unification.materials.MaterialIconType;
import com.rong.rt.common.blocks.BlockOre;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockOreFactory extends AbstractBlockModelFactory {

    private static final String VARIANT_DEFINITION =
            "      \"$STONE_TYPE$\": {\n" +
            "        \"textures\": {\n" +
            "          \"base_down\": \"$BASE_TEXTURE_TOP$\",\n" +
            "          \"base_up\": \"$BASE_TEXTURE_TOP$\",\n" +
            "          \"base_north\": \"$BASE_TEXTURE_SIDE$\",\n" +
            "          \"base_south\": \"$BASE_TEXTURE_SIDE$\",\n" +
            "          \"base_west\": \"$BASE_TEXTURE_SIDE$\",\n" +
            "          \"base_east\": \"$BASE_TEXTURE_SIDE$\",\n" +
            "          \"particle\": \"$BASE_TEXTURE_SIDE$\"\n" +
            "        }\n"+
            "      }";
    private static final Joiner COMMA_JOINER = Joiner.on(",\n");

    public static void init() {
        BlockOreFactory factory = new BlockOreFactory();
        ResourcePackHook.addResourcePackFileHook(factory);
    }

    private BlockOreFactory() {
        super("ore_block", "ore_");
    }

    @Override
    protected String fillSample(Block block, String blockStateSample) {
        return blockStateSample
                .replace("$STONE_TYPES$", COMMA_JOINER.join(((BlockOre) block).STONE_TYPE.getAllowedValues().stream()
                        .map(stoneType -> VARIANT_DEFINITION
                                .replace("$STONE_TYPE$", stoneType.name)
                                .replace("$BASE_TEXTURE_TOP$", stoneType.backgroundTopTexture.toString())
                                .replace("$BASE_TEXTURE_SIDE$", stoneType.backgroundSideTexture.toString()))
                        .collect(Collectors.toList())))
                .replace("$MATERIAL_TEXTURE_NORMAL$", MaterialIconType.ore.getBlockPath(((BlockOre) block).material.materialIconSet).toString());
    }

}