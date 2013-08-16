package lendrick.deepstoragetanks;

import java.io.File;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import lendrick.deepstoragetanks.block.BlockDeepStorageTank;
import lendrick.deepstoragetanks.tile.TileEntityDeepStorageTank;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(modid="DeepStorageTanks", name="Deep Storage Tanks", version="0.0.1", dependencies = "after:MineFactoryReloaded")

@NetworkMod(clientSideRequired=true, serverSideRequired=false,
channels={"DeepStorageTanks"}, packetHandler = PacketHandlerDeepStorageTank.class)

public class DeepStorageTanks {
	private static Block findBlock(String itemname) {
        for(Block b : Block.blocksList){
        	if(b != null) {
        		if(b.getUnlocalizedName().equals(itemname)) {
        			return b;
        		}
        	}
    	}
        return null;
	}
	
	private static Item findItem(String itemname) {
        for(Item b : Item.itemsList){
        	if(b != null) {
        		if(b.getUnlocalizedName().equals(itemname)) {
        			return b;
        		}
        	}
    	}
        return null;
	}

    // The instance of your mod that Forge uses.
    @Instance("DeepStorageTanks")
    public static DeepStorageTanks instance;
   
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="lendrick.deepstoragetanks.client.ClientProxy", serverSide="lendrick.deepstoragetanks.CommonProxy")
    public static CommonProxy proxy;
    
   
    @PreInit    // used in 1.5.2
    public void preInit(FMLPreInitializationEvent event) {
            // Stub Method
    }
   
    @Init       // used in 1.5.2
    public void load(FMLInitializationEvent event) {
    	Logger myLog = FMLLog.getLogger();
    	myLog.setLevel(java.util.logging.Level.FINEST);
    	myLog.log(java.util.logging.Level.FINEST, "DST TEST");
    	
        LanguageRegistry.addName(blockDeepStorageTank, "Deep Storage Tank");
        MinecraftForge.setBlockHarvestLevel(blockDeepStorageTank, "pickaxe", 0);
        GameRegistry.registerBlock(blockDeepStorageTank, "deepStorageTank");
        
        GameRegistry.registerTileEntity(lendrick.deepstoragetanks.tile.TileEntityDeepStorageTank.class, "deepStorageTank");
       
        
        //GameData.dumpRegistry(Minecraft.getMinecraftDir());
        
        Item machineBlock = findItem("item.mfr.machineblock");
        Block bcTank = findBlock("tile.tankBlock");
        
        /*
        for(Block b : Block.blocksList){
        	if(b != null) {
        		myLog.log(Level.INFO, String.format("Block %d: %s / %s", b.blockID, b.getClass().getName(), b.getUnlocalizedName()));
        	}
    	}
        
        for(Item b : Item.itemsList){
        	if(b != null) {
        		myLog.log(Level.INFO, String.format("Item %d: %s / %s", b.itemID, b.getClass().getName(), b.getUnlocalizedName()));
        	}
    	}
    	*/
        
        ItemStack dstStack = new ItemStack(1700, 1, 0);
        
        if(bcTank != null && machineBlock != null) {
	    	GameRegistry.addRecipe(new ShapedOreRecipe(dstStack, new Object[]
				{
				"GGG",
				"PTP",
				"EME",
				'G', "sheetPlastic",
				'P', Item.enderPearl,
				'E', Item.eyeOfEnder,
				'T', bcTank, //bcTank.blockID,
				'M', machineBlock
				} ));
        }
        
    	NetworkRegistry.instance().registerGuiHandler(this, new GuiHandlerDeepStorageTank());
    	
        proxy.registerRenderers();
    }
   
    @PostInit   // used in 1.5.2
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
    
    public final static Block blockDeepStorageTank = new BlockDeepStorageTank(1700, Material.ground)
    .setHardness(0.5F).setStepSound(Block.soundGravelFootstep)
    .setUnlocalizedName("deepStorageTank").setCreativeTab(CreativeTabs.tabBlock);
}

