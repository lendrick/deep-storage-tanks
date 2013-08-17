package lendrick.deepstoragetanks.block;

import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.world.World;
import lendrick.deepstoragetanks.DeepStorageTanks;
import lendrick.deepstoragetanks.tile.TileEntityDeepStorageTank;

public class BlockDeepStorageTank extends BlockContainer {
	
    public BlockDeepStorageTank (int id, Material material)
    {
        super(id, material);
    }
    
    @Override
	public TileEntity createNewTileEntity(World world) {
		//Logger myLog = FMLLog.getLogger();
    	//myLog.log(java.util.logging.Level.INFO, "createNewTileEntity");
    	return new TileEntityDeepStorageTank();
	}
    

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                    EntityPlayer player, int metadata, float what, float these, float are) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        
        player.openGui(DeepStorageTanks.instance, 0, world, x, y, z);
        return true;
    }
}
