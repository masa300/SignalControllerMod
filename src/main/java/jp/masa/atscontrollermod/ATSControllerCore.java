package jp.masa.atscontrollermod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import jp.masa.atscontrollermod.block.tileentity.TileEntityATSController;
import jp.masa.atscontrollermod.gui.ATSControllerGUIHandler;
import jp.masa.atscontrollermod.network.PacketATSController;

@Mod(modid = ATSControllerCore.MODID, version = ATSControllerCore.VERSION, name = ATSControllerCore.MODID)
public class ATSControllerCore {
    public static final String MODID = "ATSControllerMod";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static ATSControllerCore INSTANCE;

    public static final int guiId_ATSController = 0;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ATSControllerGUIHandler());
        NETWORK_WRAPPER.registerMessage(PacketATSController.class, PacketATSController.class, 0, Side.SERVER);
        GameRegistry.registerTileEntity(TileEntityATSController.class, "TE_ATSController");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Block登録
        new ATSControllerBlock().preInit();
    }
}
