package com.kreezcraft.localizedchat;

import com.kreezcraft.localizedchat.commands.TalkChat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LocalizedChat.MODID) //acceptableRemoteVersions = "*"
public class LocalizedChat {
	public static final String MODID = "localizedchat";
	public static final Logger logger = LogManager.getLogger(LocalizedChat.MODID);

	public LocalizedChat() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, ChatConfig.serverSpec);
		eventBus.register(ChatConfig.class);

		MinecraftForge.EVENT_BUS.register(new ChatListener());
		MinecraftForge.EVENT_BUS.addListener(this::onCommandRegister);

		//Make sure the client does not complain if it's not installed
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		// Just realized we didn't even need this, it is only for if we need to create new commands like shouting!
		TalkChat.register(event.getDispatcher());
	}
}
