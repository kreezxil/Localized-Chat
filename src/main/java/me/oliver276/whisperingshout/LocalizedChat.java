package me.oliver276.whisperingshout;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;

@Mod(modid = LocalizedChat.MODID, version = LocalizedChat.VERSION, name = LocalizedChat.NAME)
public class WhisperingShout
{
    public static final String MODID = "localizedchat";
	public static final String NAME = "Localized Chat";
    public static final String VERSION = "@VERSION@";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //FMLCommonHandler.instance().bus().register(new ChatListener());
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        //- See more at: http://www.wuppy29.com/minecraft/modding-tutorials/wuppys-minecraft-forge-modding-tutorials-for-1-7-events/#sthash.EErev3zC.dpuf
		// some example code
        // System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());

    }



    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand((ICommand) new OutOfCharacter());
        event.registerServerCommand((ICommand) new TalkCommand());
        event.registerServerCommand((ICommand) new WhisperCommand());
        event.registerServerCommand((ICommand) new ShoutCommand());
        event.registerServerCommand((ICommand) new ActionCommand());
    }
}
