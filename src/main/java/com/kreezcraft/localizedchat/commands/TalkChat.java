package com.kreezcraft.localizedchat.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.PlayerListComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ChunkCoordComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.kreezcraft.localizedchat.ChatConfig;
import com.mojang.realmsclient.gui.ChatFormatting;

public class TalkChat extends CommandBase {
	private List aliases;

	public TalkChat() {
		this.aliases = new ArrayList();
		this.aliases.add("talk");
		this.aliases.add("speak");
		this.aliases.add("t");
		this.aliases.add("ta");
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

		if (!ChatConfig.channels.enableChannels && args.length < 2) {
			player.sendMessage(new TextComponentString(ChatConfig.colorCodes.errorColor + "Invalid arguments."));
			player.sendMessage(new TextComponentString(getUsage(sender)));
			return;
		}

		double range = 0;

		if (!ChatConfig.channels.enableChannels) {

			try {
				range = Double.valueOf(args[0]);
			} catch (Exception e) {
				player.sendMessage(new TextComponentString(
						ChatConfig.colorCodes.errorColor + "Not a recognised number: " + args[0]));
				player.sendMessage(new TextComponentString(getUsage(sender)));
				return;
			}

			if (range > ChatConfig.restrictions.talkRange) {
				player.sendMessage(new TextComponentString(ChatConfig.colorCodes.errorColor
						+ "Not a chance you can only talk as far out as " + ChatConfig.colorCodes.posColor
						+ ChatConfig.restrictions.talkRange + ChatConfig.colorCodes.errorColor + " blocks"));
				return;
			}
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
		List playerEntities = workingWorld.playerEntities;
		EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(player.getName());

		String dimChan = workingWorld.getProviderName(); // the dim name hopefully

		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getName()));
			if (!ChatConfig.channels.enableChannels) {
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
			} else {
				// now it's just plain old dim chat
				((EntityPlayerMP) name).sendMessage(new TextComponentString(
						ChatConfig.colorCodes.bracketColor + "[" + ChatConfig.colorCodes.defaultColor + dimChan
						
								+ ChatConfig.colorCodes.bracketColor + "] " + ChatConfig.colorCodes.angleBraceColor
								+ "<" + ChatConfig.colorCodes.nameColor + player.getName()
								+ ChatConfig.colorCodes.angleBraceColor + "> " + ChatConfig.colorCodes.bodyColor
								+ message));
			} // end else

		} // end for

	} // end execute
}
