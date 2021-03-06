package lendrick.deepstoragetanks.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityDeepStorageTank extends TileEntity implements ITankContainer {
	public final LiquidTank tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 2000000);
	public static int dstID = 0;
	public Integer thisID;
	
	public void sideLog(Level level, String msg) {
		Logger logger = FMLLog.getLogger();
	    if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
	        logger.log(level, "Server-side: " + thisID.toString() + " " + msg);
	    }
	    else if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
	        logger.log(level, "Client-side: " + thisID.toString() + " " + msg);
	    }
	    else {
	        logger.log(level, "Unknown-side?: " + msg);
	    }
	}
	
	public TileEntityDeepStorageTank() 
	{
		thisID = dstID;
		dstID++;
		Logger myLog = FMLLog.getLogger();
    	//sideLog(java.util.logging.Level.INFO, "Creating new Deep Storage Tank Tile Entity");
    	//		sideLog(java.util.logging.Level.INFO, ((Integer)thisID).toString());
	}
	
    /**
     * Fills liquid into internal tanks, distribution is left to the ITankContainer.
     * @param from Orientation the liquid is pumped in from.
     * @param resource LiquidStack representing the maximum amount of liquid filled into the ITankContainer
     * @param doFill If false filling will only be simulated.
     * @return Amount of resource that was filled into internal tanks.
     */
    public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
    {
    	return fill(0, resource, doFill);
    }
    /**
     * Fills liquid into the specified internal tank.
     * @param tankIndex the index of the tank to fill
     * @param resource LiquidStack representing the maximum amount of liquid filled into the ITankContainer
     * @param doFill If false filling will only be simulated.
     * @return Amount of resource that was filled into internal tanks.
     */
    public int fill(int tankIndex, LiquidStack resource, boolean doFill)
    {
    	int f = tank.fill(resource, doFill);
    	//sendUpdatePacket();
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

    	return f;
    }

    /**
     * Drains liquid out of internal tanks, distribution is left to the ITankContainer.
     * @param from Orientation the liquid is drained to.
     * @param maxDrain Maximum amount of liquid to drain.
     * @param doDrain If false draining will only be simulated.
     * @return LiquidStack representing the liquid and amount actually drained from the ITankContainer
     */
    public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
    	return drain(0, maxDrain, doDrain);
    }
    
    /**
     * Drains liquid out of the specified internal tank.
     * @param tankIndex the index of the tank to drain
     * @param maxDrain Maximum amount of liquid to drain.
     * @param doDrain If false draining will only be simulated.
     * @return LiquidStack representing the liquid and amount actually drained from the ITankContainer
     */
    public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
    {
    	LiquidStack d = tank.drain(maxDrain, doDrain);
    	//sendUpdatePacket();
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

    	return d;
    }

    /**
     * @param direction tank side: UNKNOWN for default tank set
     * @return Array of {@link LiquidTank}s contained in this ITankContainer for this direction
     */
    public ILiquidTank[] getTanks(ForgeDirection direction)
    {
    	return new ILiquidTank[]{tank};
    }

    /**
     * Return the tank that this tank container desired to be used for the specified liquid type from the specified direction
     *
     * @param direction the direction
     * @param type the liquid type, null is always an acceptable value
     * @return a tank or null for no such tank
     */
    public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
    {
    	return tank;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
    	//sideLog(java.util.logging.Level.INFO, "readFromNBT");
    	super.readFromNBT(data);
		LiquidStack liquid = LiquidStack.loadLiquidStackFromNBT(data.getCompoundTag("tank"));
		if (liquid != null) {
			tank.setLiquid(liquid);
			//sideLog(java.util.logging.Level.INFO, "Read Amount: " + ((Integer)liquid.amount).toString());
		}
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {    			
    	//sideLog(java.util.logging.Level.INFO, "writeToNBT");
    	if (tank.containsValidLiquid()) {
    		data.setTag("tank", tank.getLiquid().writeToNBT(new NBTTagCompound()));
    	}
    	super.writeToNBT(data);
    }
    
    @Override
    public Packet getDescriptionPacket() {
    	//sideLog(java.util.logging.Level.INFO, "getDescriptionPacket");
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound tag = packet != null ? packet.customParam1 : new NBTTagCompound();

    	if (tank.containsValidLiquid()) {
    		//sideLog(java.util.logging.Level.INFO, "Amount: " + ((Integer)tank.getLiquid().amount).toString());
        	tag.setTag("tank", tank.getLiquid().writeToNBT(new NBTTagCompound()));
    	}    	

        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }
    
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		//sideLog(java.util.logging.Level.INFO, "onDataPacket");
        readFromNBT(packet.customParam1);
	}
}
