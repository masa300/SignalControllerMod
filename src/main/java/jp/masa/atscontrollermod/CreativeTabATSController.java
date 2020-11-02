package jp.masa.atscontrollermod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabATSController extends CreativeTabs {
    public static final CreativeTabs tabUtils = new CreativeTabATSController("ATSController");

    private CreativeTabATSController(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ATSControllerBlock.blockATSController);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "ATSController";
    }
}
