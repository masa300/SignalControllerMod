package jp.masa.atscontrollermod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.masa.atscontrollermod.block.ATSController;
import net.minecraft.block.Block;

public class ATSControllerBlock {
    public static Block blockATSController;

    public void preInit() {
        blockATSController = new ATSController();
        GameRegistry.registerBlock(blockATSController, "ATSController");
    }
}
