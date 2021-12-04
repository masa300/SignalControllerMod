package jp.masa.signalcontrollermod.item;

import jp.masa.signalcontrollermod.CreativeTabSignalController;
import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.ngt.ngtlib.io.NGTLog;
import jp.ngt.ngtlib.network.PacketNBT;
import jp.ngt.rtm.electric.TileEntitySignal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPosSettingTool extends Item {

    public ItemPosSettingTool() {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabSignalController.tabUtils);
        this.setUnlocalizedName(SignalControllerCore.MODID + ":" + "itempossettingtool");
        this.setRegistryName(SignalControllerCore.MODID, "itempossettingtool");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack itemStack = player.getHeldItem(hand);
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntitySignal) {
                NBTTagCompound tag = itemStack.getTagCompound();
                if (tag == null) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }
                itemStack.getTagCompound().setLong("pos", blockPos.toLong());
                NGTLog.sendChatMessage(player, String.format("Position saved!(%s)", blockPos.toString()));
            } else if (tileEntity instanceof TileEntitySignalController) {
                NBTTagCompound tag = itemStack.getTagCompound();
                if (tag != null && tag.hasKey("pos")) {
                    BlockPos pos = BlockPos.fromLong(tag.getLong("pos"));
                    if (itemStack.getItemDamage() == 0) {
                        boolean added = ((TileEntitySignalController) tileEntity).addNextSignal(pos);
                        if (added) {
                            NGTLog.sendChatMessage(player, String.format("NextSignal added (%s)!", pos.toString()));
                        } else {
                            NGTLog.sendChatMessage(player, "NextSignal already added");
                        }
                    } else if (itemStack.getItemDamage() == 1) {
                        boolean added = ((TileEntitySignalController) tileEntity).addDisplayPos(pos);
                        if (added) {
                            NGTLog.sendChatMessage(player, String.format("DisplayPos added (%s)!", pos.toString()));
                        } else {
                            NGTLog.sendChatMessage(player, "DisplayPos already added");
                        }
                    }
                    PacketNBT.sendToClient(tileEntity);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }
}
