package jp.masa.signalcontrollermod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.signalcontroller.SignalType;
import jp.masa.signalcontrollermod.utils.BlockPos;
import jp.ngt.ngtlib.util.NGTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PacketSignalController extends PacketTileEntity implements IMessageHandler<PacketSignalController, IMessage> {
    private SignalType signalType;
    private List<BlockPos> nextSignal;
    private List<BlockPos> displayPos;
    private boolean above;
    private boolean last;
    private boolean repeat;
    private boolean reducedSpeed;

    public PacketSignalController() {

    }

    public PacketSignalController(TileEntity tileEntity, SignalType signalType, boolean last, boolean repeat, boolean reducedSpeed, List<BlockPos> nextSignal, List<BlockPos> displayPos, boolean above) {
        super(tileEntity);
        this.signalType = signalType;
        this.last = last;
        this.repeat = repeat;
        this.reducedSpeed = reducedSpeed;
        this.nextSignal = nextSignal;
        this.displayPos = displayPos;
        this.above = above;
    }

    @Override
    protected void write(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, signalType.toString());
        buffer.writeBoolean(this.last);
        buffer.writeBoolean(this.repeat);
        buffer.writeBoolean(this.reducedSpeed);
        buffer.writeInt(this.nextSignal.size());
        for (BlockPos pos : this.nextSignal) {
            buffer.writeInt(pos.X);
            buffer.writeInt(pos.Y);
            buffer.writeInt(pos.Z);
        }
        buffer.writeInt(this.displayPos.size());
        for (BlockPos pos : this.displayPos) {
            buffer.writeInt(pos.X);
            buffer.writeInt(pos.Y);
            buffer.writeInt(pos.Z);
        }
        buffer.writeBoolean(this.above);
    }

    @Override
    protected void read(ByteBuf buffer) {
        this.signalType = SignalType.getType(ByteBufUtils.readUTF8String(buffer));
        this.last = buffer.readBoolean();
        this.repeat = buffer.readBoolean();
        this.reducedSpeed = buffer.readBoolean();
        int nextSignalSize = buffer.readInt();
        this.nextSignal = new ArrayList<>();
        for (int i = 0; i < nextSignalSize; i++) {
            int X = buffer.readInt();
            int Y = buffer.readInt();
            int Z = buffer.readInt();
            this.nextSignal.add(new BlockPos(X, Y, Z));
        }
        int displayPosSize = buffer.readInt();
        this.displayPos = new ArrayList<>();
        for (int i = 0; i < displayPosSize; i++) {
            int X = buffer.readInt();
            int Y = buffer.readInt();
            int Z = buffer.readInt();
            this.displayPos.add(new BlockPos(X, Y, Z));
        }
        this.above = buffer.readBoolean();
    }

    //ここ鯖
    @Override
    public IMessage onMessage(PacketSignalController message, MessageContext ctx) {
        World world = ctx.getServerHandler().playerEntity.worldObj;
        TileEntitySignalController tile = (TileEntitySignalController) message.getTileEntity(world);
        tile.setSignalType(message.signalType);
        tile.setLast(message.last);
        tile.setRepeat(message.repeat);
        tile.setReducedSpeed(message.reducedSpeed);
        tile.setNextSignal(message.nextSignal);
        tile.setDisplayPos(message.displayPos);
        tile.setAbove(message.above);
        NGTUtil.sendPacketToClient(tile);
        tile.markDirty();
        return null;
    }
}
