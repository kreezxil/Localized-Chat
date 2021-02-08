package com.kreezcraft.localizedchat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ChatListener {

	public static ITextComponent playerName(PlayerEntity player) {
		if (player.hasCustomName())
			return player.getCustomName();
		return player.getDisplayName();
	}

	@SubscribeEvent
	public void onChat(ServerChatEvent event) {
		String message = event.getMessage();
		int range = ChatConfig.SERVER.talkRange.get();

		System.out.println(message);
		World workingWorld = event.getPlayer().getEntityWorld();
		MinecraftServer server = workingWorld.getServer();
		List<? extends PlayerEntity> playerEntities = workingWorld.getPlayers();
		PlayerEntity mainPlayer = workingWorld.getPlayerByUuid(event.getPlayer().getUniqueID());
		
		//System.out.print(mainPlayer.getGameProfile());

		System.out.println(playerName(mainPlayer).getString());
		for (PlayerEntity name : playerEntities) {
			PlayerEntity comparePlayer = workingWorld.getPlayerByUuid(name.getUniqueID());
			if (mainPlayer == comparePlayer) { // handles local echo

				((ServerPlayerEntity) name).sendMessage(new StringTextComponent(ChatConfig.SERVER.angleBraceColor.get() + "<"
						+ ChatConfig.SERVER.nameColor.get() + playerName(mainPlayer).getString()
						+ ChatConfig.SERVER.angleBraceColor.get() + "> " + ChatConfig.SERVER.bodyColor.get() + message), mainPlayer.getUniqueID());
				
			} else if (!ChatConfig.SERVER.opAsPlayer.get() && mainPlayer != null && (server.getPlayerList().getOppedPlayers().getEntry(mainPlayer.getGameProfile()) != null)) {
				// handles ops chatting to everyone
				((ServerPlayerEntity) name).sendMessage(new StringTextComponent(ChatConfig.SERVER.bracketColor.get() + "["
						+ ChatConfig.SERVER.defaultColor.get()
						+ ((ChatConfig.SERVER.usePrefix.get()) ? ChatConfig.SERVER.prefix.get()
								: "From " + ChatConfig.SERVER.posColor.get()
										+ compareCoordinatesDistance(mainPlayer.getPosition(),
												comparePlayer.getPosition())
										+ ChatConfig.SERVER.defaultColor.get() + " blocks away")
						+ ChatConfig.SERVER.bracketColor.get() + "] " + ChatConfig.SERVER.angleBraceColor.get() + "<"
						+ ChatConfig.SERVER.nameColor.get() + playerName(mainPlayer).getString()
						+ ChatConfig.SERVER.angleBraceColor.get() + "> " + ChatConfig.SERVER.bodyColor.get() + message), mainPlayer.getUniqueID());
			} else { // handles normal player and/or customNPC
				if (compareCoordinatesDistance(event.getPlayer().getPosition(), comparePlayer.getPosition()) <= (double) range) {
					((ServerPlayerEntity) name).sendMessage(new StringTextComponent(ChatConfig.SERVER.bracketColor.get() + "["
							+ ChatConfig.SERVER.defaultColor.get()
							+ ((ChatConfig.SERVER.usePrefix.get()) ? ChatConfig.SERVER.prefix.get()
									: "From " + ChatConfig.SERVER.posColor.get()
											+ compareCoordinatesDistance(event.getPlayer().getPosition(),
													comparePlayer.getPosition())
											+ ChatConfig.SERVER.defaultColor.get() + " blocks away")
							+ ChatConfig.SERVER.bracketColor.get() + "] " + ChatConfig.SERVER.angleBraceColor.get() + "<"
							+ ChatConfig.SERVER.nameColor.get() + playerName(event.getPlayer()).getString()
							+ ChatConfig.SERVER.angleBraceColor.get() + "> " + ChatConfig.SERVER.bodyColor.get()
							+ message), mainPlayer.getUniqueID());
				} // end if
			}

		} // end for
		event.setCanceled(true); //Required for CustomNPC Support
	} // end onchat

	public static double compareCoordinatesDistance(BlockPos player1, BlockPos player2) {
		double x = Math.abs(player1.getX() - player2.getX());
		double y = Math.abs(player1.getY() - player2.getY());
		double z = Math.abs(player1.getZ() - player2.getZ());
		return x + y + z;
	}

}
