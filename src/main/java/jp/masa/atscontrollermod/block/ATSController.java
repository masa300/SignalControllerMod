package jp.masa.atscontrollermod.block;

import jp.masa.atscontrollermod.ATSControllerCore;
import jp.masa.atscontrollermod.CreativeTabATSController;
import jp.masa.atscontrollermod.block.tileentity.TileEntityATSController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ATSController extends BlockContainer {

	public ATSController() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabATSController.tabUtils);
		//modidないとテクスチャおかしくなる
		this.setBlockName(ATSControllerCore.MODID + ":" + "ATSController");
		this.setBlockTextureName(ATSControllerCore.MODID + ":" + "ATSController");
		this.setStepSound(Block.soundTypeStone);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		//ブロックを右クリックした際の動作
		player.openGui(ATSControllerCore.INSTANCE, ATSControllerCore.guiId_ATSController, player.worldObj, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityATSController();
	}
}
