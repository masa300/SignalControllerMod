package jp.masa.signalcontrollermod.block;

import jp.masa.signalcontrollermod.CreativeTabSignalController;
import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalController extends BlockContainer {

    public SignalController() {
        super(Material.ROCK);
        this.setCreativeTab(CreativeTabSignalController.tabUtils);
        this.setUnlocalizedName("signalcontroller");
        this.setRegistryName(SignalControllerCore.MODID, "signalcontroller");
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(SignalControllerCore.INSTANCE, SignalControllerCore.guiId_ATSController, player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySignalController();
    }
}
