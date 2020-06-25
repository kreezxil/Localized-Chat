package com.kreezcraft.localizedchat.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.kreezcraft.localizedchat.ChatConfig;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TalkChat extends CommandBase {
	private List<String> aliases;

	public TalkChat() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("shout");
		this.aliases.add("t");
	}

	@Override
	public String getName() {
		return "talk";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return ChatConfig.colorCodes.usageColor + "talk <range> <text>";
	}

	@Override
	@Nonnull
	public List<String> getAliases() {
		return aliases;
	}

	public double compareCoordinatesDistance(BlockPos player1, BlockPos player2) {
		double x = Math.abs(player1.getX() - player2.getX());
		double y = Math.abs(player1.getY() - player2.getY());
		double z = Math.abs(player1.getZ() - player2.getZ());
		return x + y + z;
	}

	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		EntityPlayer player;

		if (sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;

		} else {
			return;
		}

		int range = 0;

		if (server.getPlayerList().getOppedPlayers().getGameProfileFromName(sender.getName()) != null
				&& !ChatConfig.restrictions.opAsPlayer) {
			// player is an operator

			range = 300000;
		} else {

			range = ChatConfig.restrictions.talkRange;
		}

		StringBuilder strBuilder = new StringBuilder();
		boolean done1stword = false;

		for (String word : args) {
			if (done1stword) {
				strBuilder.append(word).append(" ");
			}
			done1stword = true;
		}

		String message = strBuilder.toString();
		System.out.println(message);
		World workingWorld = player.getEntityWorld();
		List<EntityPlayer> playerEntities = workingWorld.playerEntities;
		EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(player.getName());

		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getName()));
			// dim chat with range checking
			if (compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition()) <= range) {
				((EntityPlayerMP) name).sendMessage(new TextComponentString(ChatConfig.colorCodes.bracketColor + "["
						+ ChatConfig.colorCodes.defaultColor + "From " + ChatConfig.colorCodes.posColor
						+ compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition())
						+ ChatConfig.colorCodes.defaultColor + " blocks away" + ChatConfig.colorCodes.bracketColor
						+ "] " + ChatConfig.colorCodes.angleBraceColor + "<" + ChatConfig.colorCodes.nameColor
						+ player.getName() + ChatConfig.colorCodes.angleBraceColor + "> "
						+ ChatConfig.colorCodes.bodyColor + message));
			} // end if

		} // end for

	} // end execute
}
