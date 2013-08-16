package lendrick.deepstoragetanks.gui.client;

import lendrick.deepstoragetanks.gui.container.ContainerDeepStorageTank;
import lendrick.deepstoragetanks.tile.TileEntityDeepStorageTank;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class GuiDeepStorageTank extends GuiContainer {
	private TileEntityDeepStorageTank tileEntity = null;
	
	public GuiDeepStorageTank(Container container) 
	{
		super(container);
	}
	
    public GuiDeepStorageTank(InventoryPlayer inventoryPlayer, TileEntityDeepStorageTank te)
    {
    	super(new ContainerDeepStorageTank(inventoryPlayer, te));
    	tileEntity = te;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY) 
	{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/gui/demo_bg.png");
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		LiquidTank tank = tileEntity.tank;
		if(tank.containsValidLiquid()) {
			LiquidStack liquid = tank.getLiquid();
			fontRenderer.drawString("Liquid:", 30, 30, 4210752);
			fontRenderer.drawString(LiquidDictionary.findLiquidName(liquid), 30, 40, 4210752);
			
			fontRenderer.drawString("Stored:", 30, 70, 4210752);
			fontRenderer.drawString(((Integer)liquid.amount).toString(), 30, 80, 4210752);
		} else {
			fontRenderer.drawString("Liquid:", 30, 30, 4210752);
			fontRenderer.drawString("None", 30, 40, 4210752);
		}
		
		fontRenderer.drawString("Position:", 30, 100, 4210752);
		fontRenderer.drawString(((Integer)tileEntity.xCoord).toString(), 30, 110, 4210752);
		fontRenderer.drawString(((Integer)tileEntity.yCoord).toString(), 30, 120, 4210752);
		fontRenderer.drawString(((Integer)tileEntity.zCoord).toString(), 30, 130, 4210752);
		fontRenderer.drawString(((Integer)tileEntity.thisID).toString(), 30, 140, 4210752);
	}
}
