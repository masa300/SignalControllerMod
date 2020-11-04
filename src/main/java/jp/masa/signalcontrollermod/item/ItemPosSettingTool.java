package jp.masa.signalcontrollermod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.masa.signalcontrollermod.CreativeTabSignalController;
import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.ngt.ngtlib.io.NGTLog;
import jp.ngt.ngtlib.util.NGTUtil;
import jp.ngt.rtm.electric.TileEntitySignal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemPosSettingTool extends Item {
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemPosSettingTool() {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabSignalController.tabUtils);
        this.setUnlocalizedName(SignalControllerCore.MODID + ":" + "ItemPosSettingTool");
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntitySignal) {
                if (!itemStack.hasTagCompound()) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }
                itemStack.getTagCompound().setIntArray("pos", new int[]{x, y, z});
                NGTLog.sendChatMessage(player, String.format("Position saved!(%s, %s ,%s)", x, y, z));
            } else if (tileEntity instanceof TileEntitySignalController) {
                if (itemStack.hasTagCompound()) {
                    NBTTagCompound tag = itemStack.getTagCompound();
                    int[] pos = tag.getIntArray("pos");
                    if (itemStack.getItemDamage() == 0) {
                        boolean added = ((TileEntitySignalController) tileEntity).addNextSignal(pos);
                        if (added) {
                            NGTLog.sendChatMessage(player, String.format("NextSignal added (%s, %s ,%s)!", pos[0], pos[1], pos[2]));
                        }else{
                            NGTLog.sendChatMessage(player,"NextSignal already added");
                        }
                    } else if (itemStack.getItemDamage() == 1) {
                        ((TileEntitySignalController) tileEntity).setDisplayPos(pos);
                        NGTLog.sendChatMessage(player, String.format("DisplayPos set to (%s, %s ,%s)!", pos[0], pos[1], pos[2]));
                    }
                    NGTUtil.sendPacketToClient(tileEntity);
                }
            }
        }
        return false;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return this.icons[damage];
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icons = new IIcon[2];
        this.icons[0] = register.registerIcon(SignalControllerCore.MODID.toLowerCase() + ":" + "Pos_Setting_Tool0");
        this.icons[1] = register.registerIcon(SignalControllerCore.MODID.toLowerCase() + ":" + "Pos_Setting_Tool1");
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }
}
