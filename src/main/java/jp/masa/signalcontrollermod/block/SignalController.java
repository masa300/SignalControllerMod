package jp.masa.signalcontrollermod.block;

import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.CreativeTabSignalController;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SignalController extends BlockContainer {

    public SignalController() {
		super(Material.rock);
        this.setCreativeTab(CreativeTabSignalController.tabUtils);
		//modidないとテクスチャおかしくなる
        this.setBlockName(SignalControllerCore.MODID + ":" + "ATSController");
        this.setBlockTextureName(SignalControllerCore.MODID + ":" + "ATSController");
		this.setStepSound(Block.soundTypeStone);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		//ブロックを右クリックした際の動作
        player.openGui(SignalControllerCore.INSTANCE, SignalControllerCore.guiId_ATSController, player.worldObj, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySignalController();
	}
}
