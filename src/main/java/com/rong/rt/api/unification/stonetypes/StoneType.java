package com.rong.rt.api.unification.stonetypes;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.rong.rt.api.ControlledRegistry;
import com.rong.rt.api.unification.EnumOrePrefix;
import com.rong.rt.api.unification.materials.types.DustMaterial;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class StoneType implements Comparable<StoneType> {

    public static final int AFFECTED_BY_GRAVITY = 1;

    public final String name;
    public final String harvestTool;
    public final ResourceLocation backgroundSideTexture;
    public final ResourceLocation backgroundTopTexture;

    public final EnumOrePrefix processingPrefix;
    public final DustMaterial stoneMaterial;
    public final Supplier<IBlockState> stone;
    public final SoundType soundType;
    //we are using guava predicate because isReplaceableOreGen uses it
    @SuppressWarnings("Guava")
    private final com.google.common.base.Predicate<IBlockState> predicate;
    public final boolean affectedByGravity;

    public static final ControlledRegistry<String, StoneType> STONE_TYPE_REGISTRY = new ControlledRegistry<>(128);

    public StoneType(int id, String name, ResourceLocation backgroundSideTexture, ResourceLocation backgroundTopTexture, SoundType soundType, EnumOrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, int flags, Supplier<IBlockState> stone, Predicate<IBlockState> predicate) {
        this.name = name;
        this.backgroundSideTexture = backgroundSideTexture;
        this.backgroundTopTexture = backgroundTopTexture;
        this.soundType = soundType;
        this.processingPrefix = processingPrefix;
        this.stoneMaterial = stoneMaterial;
        this.harvestTool = harvestTool;
        this.affectedByGravity = (flags & AFFECTED_BY_GRAVITY) > 0;
        this.stone = stone;
        this.predicate = predicate::test;
        STONE_TYPE_REGISTRY.register(id, name, this);
    }

    public StoneType(int id, String name, ResourceLocation backgroundTexture, SoundType soundType, EnumOrePrefix processingPrefix, DustMaterial stoneMaterial, String harvestTool, int flags, Supplier<IBlockState> stone, Predicate<IBlockState> predicate) {
        this(id, name, backgroundTexture, backgroundTexture, soundType, processingPrefix, stoneMaterial, harvestTool, flags, stone, predicate);
    }

    @Override
    public int compareTo(StoneType stoneType) {
        return STONE_TYPE_REGISTRY.getIDForObject(this) - STONE_TYPE_REGISTRY.getIDForObject(stoneType);
    }

    public static void init() {
        StoneTypes.STONE.name.getBytes();
    }

    public static StoneType computeStoneType(IBlockState blockState, IBlockAccess world, BlockPos blockPos) {
        //TODO ADD CONFIG HOOK HERE FOR MATCHING BLOCKS WITH STONE TYPES
        for (StoneType stoneType : STONE_TYPE_REGISTRY) {
            if(blockState.getBlock().isReplaceableOreGen(blockState, world, blockPos, stoneType.predicate))
            return stoneType;
        }
        return null;
    }

}

