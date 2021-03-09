package jp.masa.signalcontrollermod.network;

import io.netty.buffer.ByteBuf;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.signalcontroller.SignalType;
import jp.ngt.ngtlib.network.PacketNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PacketSignalController extends PacketTileEntity implements IMessage, IMessageHandler<PacketSignalController, IMessage> {
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
        this.nextSignal.stream().mapToLong(BlockPos::toLong).forEach(buffer::writeLong);
        buffer.writeInt(this.displayPos.size());
        this.displayPos.stream().mapToLong(BlockPos::toLong).forEach(buffer::writeLong);
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
        IntStream.range(0, nextSignalSize).forEach(i -> this.nextSignal.add(BlockPos.fromLong(buffer.readLong())));
        int displayPosSize = buffer.readInt();
        this.displayPos = new ArrayList<>();
        IntStream.range(0, displayPosSize).forEach(i -> this.displayPos.add(BlockPos.fromLong(buffer.readLong())));
        this.above = buffer.readBoolean();
    }

    //ここ鯖
    @Override
    public IMessage onMessage(PacketSignalController message, MessageContext ctx) {
        World world = ctx.getServerHandler().player.getEntityWorld();
        TileEntitySignalController tile = (TileEntitySignalController) message.getTileEntity(world);
        tile.setSignalType(message.signalType);
        tile.setLast(message.last);
        tile.setRepeat(message.repeat);
        tile.setReducedSpeed(message.reducedSpeed);
        tile.setNextSignal(message.nextSignal);
        tile.setDisplayPos(message.displayPos);
        tile.setAbove(message.above);
        PacketNBT.sendToClient(tile);
        tile.markDirty();
        return null;
    }
}
