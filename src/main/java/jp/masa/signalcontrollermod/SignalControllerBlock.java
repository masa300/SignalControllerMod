package jp.masa.signalcontrollermod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.masa.signalcontrollermod.block.SignalController;
import net.minecraft.block.Block;

public class SignalControllerBlock {
    public static Block blockATSController;

    public void preInit() {
        GameRegistry.registerBlock(blockATSController = new SignalController(), "ATSController");
    }
}
