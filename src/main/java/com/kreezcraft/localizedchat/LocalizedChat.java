package com.kreezcraft.localizedchat;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = LocalizedChat.MODID, version = LocalizedChat.VERSION, name = LocalizedChat.NAME, acceptableRemoteVersions = "*")
public class LocalizedChat {
	public static final String MODID = "localizedchat";
	public static final String NAME = "Localized Chat";
	public static final String VERSION = "@VERSION@";

	public static Logger logger;
	public static LocalizedChat instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ChatListener());
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		// Just realized we didn't even need this, it is only for if we need to create new commands like shouting!
		//event.registerServerCommand(new TalkChat());
	}

}
