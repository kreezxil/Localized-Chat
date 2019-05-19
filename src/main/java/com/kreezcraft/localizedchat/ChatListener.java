package com.kreezcraft.localizedchat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ChatListener {

	private String playerName(EntityPlayer player, ServerChatEvent event) {
		NBTTagCompound tag = event.getPlayer().getEntityData();
		if (tag.hasKey("fakename")) {
			return tag.getString("fakename");
		}
		if (player.hasCustomName())
			return player.getCustomNameTag();
		return player.getDisplayNameString();
	}

	@SubscribeEvent
	public void onChat(ServerChatEvent event) {
		String message = event.getMessage();
		int range = ChatConfig.restrictions.talkRange;

		System.out.println(message);
		World workingWorld = event.getPlayer().getEntityWorld();
		MinecraftServer server = workingWorld.getMinecraftServer();
		List<EntityPlayer> playerEntities = workingWorld.playerEntities;
		EntityPlayer mainPlayer = workingWorld
				.getPlayerEntityByUUID(event.getPlayer().getCommandSenderEntity().getUniqueID());
		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld
					.getPlayerEntityByUUID(((EntityPlayerMP) name).getCommandSenderEntity().getUniqueID()));
			if (mainPlayer == comparePlayer) { // handles local echo
				((EntityPlayerMP) name).sendMessage(new TextComponentString(ChatConfig.colorCodes.angleBraceColor + "<"
						+ ChatConfig.colorCodes.nameColor + playerName(mainPlayer, event)
						+ ChatConfig.colorCodes.angleBraceColor + "> " + ChatConfig.colorCodes.bodyColor + message));
			} else if (!ChatConfig.restrictions.opAsPlayer && (server.getPlayerList().getOppedPlayers()
					.getGameProfileFromName(mainPlayer.getName()) != null)) { // handles ops chatting to everyone
				((EntityPlayerMP) name).sendMessage(new TextComponentString(ChatConfig.colorCodes.bracketColor + "["
						+ ChatConfig.colorCodes.defaultColor
						+ ((ChatConfig.miscellaneous.usePrefix) ? ChatConfig.miscellaneous.prefix
								: "From " + ChatConfig.colorCodes.posColor
										+ compareCoordinatesDistance(mainPlayer.getPosition(),
												comparePlayer.getPosition())
										+ ChatConfig.colorCodes.defaultColor + " blocks away")
						+ ChatConfig.colorCodes.bracketColor + "] " + ChatConfig.colorCodes.angleBraceColor + "<"
						+ ChatConfig.colorCodes.nameColor + playerName(mainPlayer, event)
						+ ChatConfig.colorCodes.angleBraceColor + "> " + ChatConfig.colorCodes.bodyColor + message));
			} else { // handles normal player
				if (compareCoordinatesDistance(mainPlayer.getPosition(),
						comparePlayer.getPosition()) <= (double) range) {
					((EntityPlayerMP) name).sendMessage(new TextComponentString(ChatConfig.colorCodes.bracketColor + "["
							+ ChatConfig.colorCodes.defaultColor
							+ ((ChatConfig.miscellaneous.usePrefix) ? ChatConfig.miscellaneous.prefix
									: "From " + ChatConfig.colorCodes.posColor
											+ compareCoordinatesDistance(mainPlayer.getPosition(),
													comparePlayer.getPosition())
											+ ChatConfig.colorCodes.defaultColor + " blocks away")
							+ ChatConfig.colorCodes.bracketColor + "] " + ChatConfig.colorCodes.angleBraceColor + "<"
							+ ChatConfig.colorCodes.nameColor + playerName(mainPlayer, event)
							+ ChatConfig.colorCodes.angleBraceColor + "> " + ChatConfig.colorCodes.bodyColor
							+ message));
				} // end if
			}

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
