package jp.masa.signalcontrollermod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.masa.signalcontrollermod.item.ItemPosSettingTool;
import net.minecraft.item.Item;

public class SignalControllerItem {
    public static Item itemPosSettingTool;

    public void preInit() {
        GameRegistry.registerItem(itemPosSettingTool = new ItemPosSettingTool(), "ItemPosSettingTool");
    }
}
