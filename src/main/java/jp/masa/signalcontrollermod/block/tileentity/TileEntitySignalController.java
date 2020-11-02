package jp.masa.signalcontrollermod.block.tileentity;

import jp.masa.signalcontrollermod.gui.signalcontroller.SignalType;
import jp.ngt.ngtlib.util.NGTUtil;
import jp.ngt.rtm.electric.TileEntitySignal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TileEntitySignalController extends TileEntityCustom {
	private SignalType signalType;
	private int[][] nextSignal;
	private int[] displayPos;
	private boolean above;

    public TileEntitySignalController() {
		this.signalType = SignalType.none;
		this.nextSignal = new int[][]{{0, 0, 0}};
		this.displayPos = new int[]{0, 0, 0};
		this.above = true;
	}

	@Override
	public void updateEntity() {
		World world = this.getWorldObj();
		if (!world.isRemote) {
			int MAXSIGNALLEVEL = 6;
			List<Integer> nextSignalList = new ArrayList<>();

			for (int i = 0; i < this.nextSignal.length; i++) {
				Object nextSignal = this.getSignal(world, this.nextSignal[0][0], this.nextSignal[0][1], this.nextSignal[0][2]);

				if (nextSignal instanceof Integer) {
					nextSignalList.add((int) nextSignal);
				}
			}
			int nextSignalLevel = nextSignalList.stream().mapToInt(v -> v).max().orElse(0);

			// RS入力(停止現示)
			boolean isRSPowered = world.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord); //レッドストーン確認

			//表示する信号機の制御
			//変化したときだけ変更するようにすることで負荷を減らすこと
			int signalLevel = this.signalType.upSignalLevel(nextSignalLevel);
			Object currentSignal;
			if (signalLevel > MAXSIGNALLEVEL) signalLevel = MAXSIGNALLEVEL;
			if (isRSPowered) signalLevel = 1;

			for (int i = 0; i < displayPos.length; i++) {
				if (this.above) {
					currentSignal = getSignalAbove(world);
					if (currentSignal != null && (int) currentSignal != signalLevel) setSignalAbove(world, signalLevel);
				} else {
					currentSignal = getSignal(world, displayPos[0], displayPos[1], displayPos[2]);
					if (currentSignal != null && (int) currentSignal != signalLevel)
						setSignal(world, displayPos[0], displayPos[1], displayPos[2], signalLevel);
				}
			}
		}
	}

	private Object getSignal(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntitySignal)
			return NGTUtil.getField(TileEntitySignal.class, tileEntity, "signalLevel");
		return null;
	}

	private void setSignal(World world, int x, int y, int z, int level) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntitySignal) {
			((TileEntitySignal) tileEntity).setElectricity(x, y, z, level);
		}
	}

	private Object getSignalAbove(World world) {
		int searchMaxCount = 32;

		if (world == null) return null;
		for (int i = 1; i <= searchMaxCount; i++) {
			TileEntity tileEntity = world.getTileEntity(this.xCoord, this.yCoord + i, this.zCoord);
			if (tileEntity instanceof TileEntitySignal)
				return NGTUtil.getField(TileEntitySignal.class, tileEntity, "signalLevel");
		}
		return null;
	}

	private void setSignalAbove(World world, int level) {
		int searchMaxCount = 32;

		if (world == null) return;
		for (int i = 1; i <= searchMaxCount; i++) {
			setSignal(world, this.xCoord, this.yCoord + i, this.zCoord, level);
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.signalType = SignalType.getType(nbt.getString("signalType"));
		this.nextSignal[0] = nbt.getIntArray("nextSignal0");
		this.displayPos = nbt.getIntArray("displayPos");
		this.above = nbt.getBoolean("above");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("signalType", this.signalType.toString());
		nbt.setIntArray("nextSignal0", this.nextSignal[0]);
		nbt.setIntArray("displayPos", this.displayPos);
		nbt.setBoolean("above", this.above);
	}

	public SignalType getSignalType() {
		return (this.signalType == null) ? this.signalType = SignalType.none : this.signalType;
	}

	public void setSignalType(SignalType signalType) {
		this.signalType = signalType;
	}

	public int[][] getNextSignal() {
		return nextSignal;
	}

	public void setNextSignal(int[][] nextSignal) {
		this.nextSignal = nextSignal;
	}

	public int[] getDisplayPos() {
		return displayPos;
	}

	public void setDisplayPos(int[] displayPos) {
		this.displayPos = displayPos;
	}

	public boolean isAbove() {
		return above;
	}

	public void setAbove(boolean above) {
		this.above = above;
	}
}
