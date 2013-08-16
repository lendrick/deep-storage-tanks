package lendrick.deepstoragetanks;

import lendrick.deepstoragetanks.gui.client.GuiDeepStorageTank;
import lendrick.deepstoragetanks.gui.container.ContainerDeepStorageTank;
import lendrick.deepstoragetanks.tile.TileEntityDeepStorageTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerDeepStorageTank implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityDeepStorageTank){
            return new ContainerDeepStorageTank(player.inventory, (TileEntityDeepStorageTank) tileEntity);
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityDeepStorageTank){
            return new GuiDeepStorageTank(player.inventory, (TileEntityDeepStorageTank) tileEntity);
        }
		return null;
	}

}
