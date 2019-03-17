package gregtech.common.blocks;

import gregtech.common.blocks.VariantBlock;
import gregtech.common.blocks.BlockMachineCasing.MachineCasingType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTransparent extends VariantBlock<BlockTransparent.BlockTypes> {

	public BlockTransparent() {
		super(Material.IRON);
		setUnlocalizedName("block_transparent");
		setHardness(5.0f);
		setResistance(2000.0f);
		setSoundType(SoundType.GLASS);
		setHarvestLevel("wrench", 2);
		setDefaultState(getState(BlockTypes.REINFORCED));
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Deprecated
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState state = blockAccess.getBlockState(pos.offset(side));
		Block block = state.getBlock();

		return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
	
	public enum BlockTypes implements IStringSerializable {

        //Voltage-tiered casings
        REINFORCED("reinforced");

        private final String name;

        BlockTypes(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }
}