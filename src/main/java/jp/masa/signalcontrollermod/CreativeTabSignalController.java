package jp.masa.signalcontrollermod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabSignalController extends CreativeTabs {
    public static final CreativeTabs tabUtils = new CreativeTabSignalController("SignalController");

    private CreativeTabSignalController(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(SignalControllerBlock.blockATSController);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "SignalController";
    }
}
