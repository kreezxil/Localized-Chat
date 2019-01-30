package com.kreezcraft.localizedchat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

public class ChatListener {

	@SubscribeEvent
	public void onChat(ServerChatEvent event) {
		String message = event.getMessage();
		double range = ChatConfig.restrictions.talkRange;

		System.out.println(message);
		World workingWorld = event.getPlayer().getEntityWorld();
		List playerEntities = workingWorld.playerEntities;
		EntityPlayer mainPlayer = workingWorld
				.getPlayerEntityByUUID(event.getPlayer().getCommandSenderEntity().getUniqueID());
		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld
					.getPlayerEntityByUUID(((EntityPlayerMP) name).getCommandSenderEntity().getUniqueID()));

			if (!ChatConfig.channels.enableChannels) {
				// dim chat with range checking
				if (compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition()) <= range) {
					((EntityPlayerMP) name).sendMessage(new TextComponentString(ChatConfig.colorCodes.bracketColor + "["
							+ ChatConfig.colorCodes.defaultColor + "From " + ChatConfig.colorCodes.posColor
							+ compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition())
							+ ChatConfig.colorCodes.defaultColor + " blocks away" + ChatConfig.colorCodes.bracketColor
							+ "] " + ChatConfig.colorCodes.angleBraceColor + "<" + ChatConfig.colorCodes.nameColor
							+ event.getPlayer().getName() + ChatConfig.colorCodes.angleBraceColor + "> "
							+ ChatConfig.colorCodes.bodyColor + message));
				} // end if
			} else {
				// now it's just plain old dim chat
				((EntityPlayerMP) name).sendMessage(new TextComponentString(ChatConfig.colorCodes.bracketColor + "["
						+ ChatConfig.colorCodes.defaultColor + "From "

						+ ChatConfig.colorCodes.bracketColor + "] " + ChatConfig.colorCodes.angleBraceColor + "<"
						+ ChatConfig.colorCodes.nameColor + event.getPlayer().getName()
						+ ChatConfig.colorCodes.angleBraceColor + "> " + ChatConfig.colorCodes.bodyColor + message));
			} // end else

		} // end for
		event.setCanceled(true);
	} // end onchat

	public double compareCoordinatesDistance(BlockPos player1, BlockPos player2) {
		double x = Math.abs(player1.getX() - player2.getX());
		double y = Math.abs(player1.getY() - player2.getY());
		double z = Math.abs(player1.getZ() - player2.getZ());
		return x + y + z;
	}

}
