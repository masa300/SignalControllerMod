package jp.masa.signalcontrollermod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabSignalController extends CreativeTabs {
    public static final CreativeTabs tabUtils = new CreativeTabSignalController("SignalController");

    private CreativeTabSignalController(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
        return Item.getItemFromBlock(SignalControllerBlock.blockATSController);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "SignalController";
	}
}
