package jp.masa.signalcontrollermod;

import jp.masa.signalcontrollermod.item.ItemPosSettingTool;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SignalControllerItem {
    public static Item itemPosSettingTool;

    public void preInit() {
        ForgeRegistries.ITEMS.register(itemPosSettingTool = new ItemPosSettingTool());
    }
}
