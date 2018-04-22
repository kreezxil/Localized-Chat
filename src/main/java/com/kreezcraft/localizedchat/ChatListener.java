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
		double range = Config.talkRange.getDouble();

		System.out.println(message);
		World workingWorld = event.getPlayer().getEntityWorld();
		List playerEntities = workingWorld.playerEntities;
		EntityPlayer mainPlayer = workingWorld
				.getPlayerEntityByUUID(event.getPlayer().getCommandSenderEntity().getUniqueID());
		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld
					.getPlayerEntityByUUID(((EntityPlayerMP) name).getCommandSenderEntity().getUniqueID()));
//			if (compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition()) <= range) {
//				((EntityPlayerMP) name).sendMessage(new TextComponentString(
//						Config.bracketColor.getString() + "["
//						+ Config.defaultColor.getString() + "From " 
//						+ Config.posColor.getString() + compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition())
//						+ Config.defaultColor.getString() + " blocks away" 
//						+ Config.bracketColor.getString() + "] " 
//						+ Config.angleBraceColor.getString() + "<" 
//						+ Config.nameColor.getString() + event.getPlayer().getName() 
//						+ Config.angleBraceColor.getString() + "> " 
//						+ Config.bodyColor.getString() + message));
//			}
			if (!Config.enableChannels.getBoolean()) {
				// dim chat with range checking
				if (compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition()) <= range) {
					((EntityPlayerMP) name).sendMessage(new TextComponentString(Config.bracketColor.getString() + "["
							+ Config.defaultColor.getString() + "From " + Config.posColor.getString()
							+ compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition())
							+ Config.defaultColor.getString() + " blocks away" + Config.bracketColor.getString() + "] "
							+ Config.angleBraceColor.getString() + "<" + Config.nameColor.getString() + event.getPlayer().getName()
							+ Config.angleBraceColor.getString() + "> " + Config.bodyColor.getString() + message));
				} //end if
			} else {
				// now it's just plain old dim chat
				((EntityPlayerMP) name).sendMessage(new TextComponentString(
						Config.bracketColor.getString() + "[" + Config.defaultColor.getString() + "From "
						// + Config.posColor.getString() +
						// compareCoordinatesDistance(mainPlayer.getPosition(),
						// comparePlayer.getPosition())
						// + Config.defaultColor.getString() + " blocks away"
								+ Config.bracketColor.getString() + "] " + Config.angleBraceColor.getString() + "<"
								+ Config.nameColor.getString() + event.getPlayer().getName() + Config.angleBraceColor.getString()
								+ "> " + Config.bodyColor.getString() + message));
			} //end else

		} //end for
		event.setCanceled(true);
	} //end onchat

	public double compareCoordinatesDistance(BlockPos player1, BlockPos player2) {
		double x = Math.abs(player1.getX() - player2.getX());
		double y = Math.abs(player1.getY() - player2.getY());
		double z = Math.abs(player1.getZ() - player2.getZ());
		return x + y + z;
	}

}
