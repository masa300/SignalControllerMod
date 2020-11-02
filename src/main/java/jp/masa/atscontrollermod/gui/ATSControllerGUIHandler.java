package jp.masa.atscontrollermod.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import jp.masa.atscontrollermod.ATSControllerCore;
import jp.masa.atscontrollermod.block.tileentity.TileEntityATSController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ATSControllerGUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == ATSControllerCore.guiId_ATSController) {
			return new GUIATSController((TileEntityATSController) player.worldObj.getTileEntity(x, y, z));
		}
		return null;
	}
}
