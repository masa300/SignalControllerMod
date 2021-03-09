package jp.masa.signalcontrollermod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class PacketTileEntity implements IMessage {
    protected BlockPos pos;

    public PacketTileEntity() {

    }

    public PacketTileEntity(TileEntity tileEntity) {
        this.pos = tileEntity.getPos();
    }

    @Override
    public final void toBytes(ByteBuf buffer) {
        buffer.writeLong(this.pos.toLong());
        this.write(buffer);
    }

    protected abstract void write(ByteBuf buffer);

    @Override
    public final void fromBytes(ByteBuf buffer) {
        this.pos = BlockPos.fromLong(buffer.readLong());
        this.read(buffer);
    }

    protected abstract void read(ByteBuf buffer);

    protected TileEntity getTileEntity(World world) {
        return world.getTileEntity(this.pos);
    }
}
