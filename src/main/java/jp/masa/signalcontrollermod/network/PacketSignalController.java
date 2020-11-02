package jp.masa.signalcontrollermod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.signalcontroller.SignalType;
import jp.ngt.ngtlib.util.NGTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSignalController extends PacketTileEntity implements IMessageHandler<PacketSignalController, IMessage> {
	private SignalType signalType;
	private int[][] nextSignal;
	private int[] displayPos;
	private boolean above;

    public PacketSignalController() {

	}

    public PacketSignalController(TileEntity tileEntity, SignalType signalType, int[][] nextSignal, int[] displayPos, boolean above) {
		super(tileEntity);
		this.signalType = signalType;
		this.nextSignal = nextSignal;
		this.displayPos = displayPos;
		this.above = above;
	}

	@Override
	protected void write(ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, signalType.toString());
		buffer.writeInt(this.nextSignal[0][0]);
		buffer.writeInt(this.nextSignal[0][1]);
		buffer.writeInt(this.nextSignal[0][2]);
		buffer.writeInt(this.displayPos[0]);
		buffer.writeInt(this.displayPos[1]);
		buffer.writeInt(this.displayPos[2]);
		buffer.writeBoolean(this.above);

	}

	@Override
	protected void read(ByteBuf buffer) {
		this.signalType = SignalType.getType(ByteBufUtils.readUTF8String(buffer));
		this.nextSignal = new int[1][3];
		this.nextSignal[0][0] = buffer.readInt();
		this.nextSignal[0][1] = buffer.readInt();
		this.nextSignal[0][2] = buffer.readInt();

		this.displayPos = new int[3];
		this.displayPos[0] = buffer.readInt();
		this.displayPos[1] = buffer.readInt();
		this.displayPos[2] = buffer.readInt();

		this.above = buffer.readBoolean();

	}

	//ここ鯖
	@Override
    public IMessage onMessage(PacketSignalController message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
        TileEntitySignalController tile = (TileEntitySignalController) message.getTileEntity(world);
		tile.setSignalType(message.signalType);
		tile.setNextSignal(message.nextSignal);
		tile.setDisplayPos(message.displayPos);
		tile.setAbove(message.above);
		NGTUtil.sendPacketToClient(tile);
		tile.markDirty();
		return null;
	}
}
