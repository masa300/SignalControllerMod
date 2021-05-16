package jp.masa.signalcontrollermod;


import jp.masa.signalcontrollermod.block.SignalController;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.SignalControllerGUIHandler;
import jp.masa.signalcontrollermod.item.ItemPosSettingTool;
import jp.masa.signalcontrollermod.network.PacketSignalController;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = SignalControllerCore.MODID, version = SignalControllerCore.VERSION, name = SignalControllerCore.MODID)
public class SignalControllerCore {
    public static final String MODID = "signalcontrollermod";
    public static final String VERSION = "1.4.0";


    public static Block SIGNAL_CONTROLLER_BLOCK = new SignalController();
    public static Item POS_SETTING_TOOL = new ItemPosSettingTool();

    @Mod.Instance(MODID)
    public static SignalControllerCore INSTANCE;

    public static final int guiId_ATSController = 0;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);


    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(POS_SETTING_TOOL);
        event.getRegistry().register(new ItemBlock(SIGNAL_CONTROLLER_BLOCK).setRegistryName(SignalControllerCore.MODID, "signalcontroller"));

    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(SIGNAL_CONTROLLER_BLOCK);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(POS_SETTING_TOOL, 0, new ModelResourceLocation(new ResourceLocation(SignalControllerCore.MODID, "Pos_Setting_Tool0"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(POS_SETTING_TOOL, 1, new ModelResourceLocation(new ResourceLocation(SignalControllerCore.MODID, "Pos_Setting_Tool1"), "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SIGNAL_CONTROLLER_BLOCK), 0, new ModelResourceLocation(new ResourceLocation(SignalControllerCore.MODID, "signalcontroller"), "inventory"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new SignalControllerGUIHandler());
        NETWORK_WRAPPER.registerMessage(PacketSignalController.class, PacketSignalController.class, 0, Side.SERVER);
        GameRegistry.registerTileEntity(TileEntitySignalController.class, new ResourceLocation(SignalControllerCore.MODID, "TE_SignalController"));
    }
}
