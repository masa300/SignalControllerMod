package jp.masa.signalcontrollermod;

import jp.masa.signalcontrollermod.block.SignalController;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SignalControllerBlock {
    public static Block blockATSController;

    public void preInit() {
        ForgeRegistries.BLOCKS.register(blockATSController = new SignalController());
    }
}
