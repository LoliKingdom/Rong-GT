package com.rong.gt.api.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Int3 {

	public int x, y, z;
	public EnumFacing facing = EnumFacing.NORTH;
	
	public Int3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Int3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Int3(Int3 pos) {
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
	}

	public Int3(Int3 pos, EnumFacing facing) {
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
		this.facing = facing;
	}

	public Int3(BlockPos pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public Int3(BlockPos pos, EnumFacing facing) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.facing = facing;
	}

	public Int3 set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Int3 set(Int3 pos) {
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
		this.facing = pos.facing;
		return this;
	}

	public Int3 set(BlockPos pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		return this;
	}

	public void set(EnumFacing facing) {
		this.facing = facing;
	}

	public Int3 add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Int3 add(Int3 pos) {
		this.x += pos.x;
		this.y += pos.y;
		this.z += pos.z;
		return this;
	}

	public Int3 sub(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Int3 sub(Int3 pos) {
		this.x -= pos.x;
		this.y -= pos.y;
		this.z -= pos.z;
		return this;
	}

	public Int3 left(int n) {
		return offset(n, facing.rotateY());
	}

	public Int3 right(int n) {
		return offset(n, facing.rotateYCCW());
	}

	public Int3 forward(int n) {
		return offset(n, facing);
	}

	public Int3 back(int n) {
		return offset(n, facing.getOpposite());
	}

	public Int3 up(int n) {
		return offset(n, EnumFacing.UP);
	}

	public Int3 down(int n) {
		return offset(n, EnumFacing.DOWN);
	}

	public Int3 offset(int n, EnumFacing facing) {
		if (n == 0 || facing == null)
			return this;
		return set(x + facing.getFrontOffsetX() * n, y + facing.getFrontOffsetY() * n,
				z + facing.getFrontOffsetZ() * n);
	}

	public BlockPos asBlockPos() {
		return new BlockPos(x, y, z);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
