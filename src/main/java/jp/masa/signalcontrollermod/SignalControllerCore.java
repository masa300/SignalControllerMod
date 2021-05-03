package jp.masa.signalcontrollermod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.SignalControllerGUIHandler;
import jp.masa.signalcontrollermod.network.PacketSignalController;

@Mod(modid = SignalControllerCore.MODID, version = SignalControllerCore.VERSION, name = SignalControllerCore.MODID)
public class SignalControllerCore {
    public static final String MODID = "SignalControllerMod";
    public static final String VERSION = "beta_2.0.0";

    @Mod.Instance(MODID)
    public static SignalControllerCore INSTANCE;

    public static final int guiId_ATSController = 0;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new SignalControllerGUIHandler());
        NETWORK_WRAPPER.registerMessage(PacketSignalController.class, PacketSignalController.class, 0, Side.SERVER);
        GameRegistry.registerTileEntity(TileEntitySignalController.class, "TE_SignalController");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Block登録
        new SignalControllerBlock().preInit();
        new SignalControllerItem().preInit();
    }
}
