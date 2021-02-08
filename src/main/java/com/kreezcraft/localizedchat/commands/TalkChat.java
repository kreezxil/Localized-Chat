package com.kreezcraft.localizedchat.commands;

import com.kreezcraft.localizedchat.ChatConfig;
import com.kreezcraft.localizedchat.ChatListener;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TalkChat {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("shout").then(Commands.argument("message", MessageArgument.message()).executes(source -> {

			String message = MessageArgument.getMessage(source, "message").getString();

			int range = 0;

			if (TalkChat.isPlayerOpped(source.getSource().getServer(), source.getSource().asPlayer())
					&& !ChatConfig.SERVER.opAsPlayer.get()) {
				range = 300000;
			} else {
				range = ChatConfig.SERVER.talkRange.get();
			}

			World workingWorld = source.getSource().getWorld();

			for (PlayerEntity name : workingWorld.getPlayers()) {
				PlayerEntity comparePlayer = workingWorld.getPlayerByUuid(name.getUniqueID());
				if (ChatListener.compareCoordinatesDistance(source.getSource().asPlayer().getPosition(), comparePlayer.getPosition()) <= (double) range) {
					((ServerPlayerEntity) name).sendStatusMessage(new StringTextComponent(ChatConfig.SERVER.bracketColor.get() + "["
							+ ChatConfig.SERVER.defaultColor.get()
							+ ((ChatConfig.SERVER.usePrefix.get()) ? ChatConfig.SERVER.prefix.get()
							: "From " + ChatConfig.SERVER.posColor.get()
							+ ChatListener.compareCoordinatesDistance(source.getSource().asPlayer().getPosition(),
							comparePlayer.getPosition())
							+ ChatConfig.SERVER.defaultColor.get() + " blocks away")
							+ ChatConfig.SERVER.bracketColor.get() + "] " + ChatConfig.SERVER.angleBraceColor.get() + "<"
							+ ChatConfig.SERVER.nameColor.get() + ChatListener.playerName(source.getSource().asPlayer()).getString()
							+ ChatConfig.SERVER.angleBraceColor.get() + "> " + ChatConfig.SERVER.bodyColor.get() + message), false);
				} // end if
			}

			return 0;
		})));
	}

	public static boolean isPlayerOpped(MinecraftServer server, ServerPlayerEntity player) {
		return server.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile()) != null;
	}
}
