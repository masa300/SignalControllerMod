package jp.masa.signalcontrollermod.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SignalControllerGUIHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == SignalControllerCore.guiId_ATSController) {
            return new GUISignalController((TileEntitySignalController) player.worldObj.getTileEntity(x, y, z));
        }
        return null;
    }
}
