package com.kreezcraft.localizedchat.Channels;

import com.kreezcraft.localizedchat.LocalizedChat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
	public static final ResourceLocation CHANNEL = new ResourceLocation(LocalizedChat.MODID, "channel");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent event) {
		if(!(event.getObject() instanceof EntityPlayer)) return;
		event.addCapability(CHANNEL, new ChannelProvider());
	}
}
