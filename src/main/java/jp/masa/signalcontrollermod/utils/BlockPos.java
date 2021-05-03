package jp.masa.signalcontrollermod.utils;

import jdk.nashorn.internal.ir.Block;

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
}
