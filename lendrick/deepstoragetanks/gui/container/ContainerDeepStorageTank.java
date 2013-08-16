package lendrick.deepstoragetanks.gui.container;

import lendrick.deepstoragetanks.tile.TileEntityDeepStorageTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerDeepStorageTank extends Container {
    public TileEntityDeepStorageTank tileEntity;

    public ContainerDeepStorageTank(InventoryPlayer inventoryPlayer, TileEntityDeepStorageTank te)
    {
        tileEntity = te;
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
