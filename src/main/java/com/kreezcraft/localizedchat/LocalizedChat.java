package com.kreezcraft.localizedchat;

import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = LocalizedChat.MODID, version = LocalizedChat.VERSION, name = LocalizedChat.NAME, acceptableRemoteVersions = "*")
public class LocalizedChat
{
    public static final String MODID = "localizedchat";
	public static final String NAME = "Localized Chat";
    public static final String VERSION = "@VERSION@";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ChatListener());
    }



    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new TalkCommand());
        event.registerServerCommand(new ShoutCommand());
    }

}
