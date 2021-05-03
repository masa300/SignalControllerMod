package jp.masa.signalcontrollermod.utils;

import net.minecraft.nbt.NBTTagCompound;

public class BlockPos {
    public int X;
    public int Y;
    public int Z;

    public BlockPos() {
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
    }

    public BlockPos(int x, int y, int z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    public boolean equals(BlockPos pos) {
        return this.X == pos.X && this.Y == pos.Y && this.Z == pos.Z;
    }

    public static NBTTagCompound writeToNBT(BlockPos blockPos) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("x", blockPos.X);
        nbt.setInteger("y", blockPos.Y);
        nbt.setInteger("z", blockPos.Z);
        return nbt;
    }

    public static BlockPos readFromNBT(NBTTagCompound nbt) {
        int x = nbt.getInteger("x");
        int y = nbt.getInteger("y");
        int z = nbt.getInteger("z");
        return new BlockPos(x, y, z);
    }

    public static BlockPos fromIntArray(int[] intArray) {
        if (intArray.length == 3) {
            return new BlockPos(intArray[0], intArray[1], intArray[2]);
        } else {
            return new BlockPos();
        }
    }
}
