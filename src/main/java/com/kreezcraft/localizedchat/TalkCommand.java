package com.kreezcraft.localizedchat;

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

import com.mojang.realmsclient.gui.ChatFormatting;

public class TalkCommand extends CommandBase {
	private List aliases;

	public TalkCommand() {
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
		if (Config.enableChannels.getBoolean()) {
			return Config.usageColor.getString() + "Dimension chat\ntalk <text>";
		}
		return Config.usageColor.getString() + "talk <range> <text>";
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

		if (args.length < 2) {
			player.sendMessage(new TextComponentString(Config.errorColor.getString() + "Invalid arguments."));
			player.sendMessage(new TextComponentString(getUsage(sender)));
			return;
		}

		double range = 0;

		try {
			range = Double.valueOf(args[0]);
		} catch (Exception e) {
			player.sendMessage(
					new TextComponentString(Config.errorColor.getString() + "Not a recognised number: " + args[0]));
			player.sendMessage(new TextComponentString(getUsage(sender)));
			return;
		}

		if (range > Config.talkRange.getInt()) {
			player.sendMessage(new TextComponentString(Config.errorColor.getString()
					+ "Not a chance you can only talk as far out as " + Config.posColor.getString()
					+ Config.talkRange.getInt() + Config.errorColor.getString() + " blocks"));
			return;
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

		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getName()));
			if (!Config.enableChannels.getBoolean()) {
				// dim chat with range checking
				if (compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition()) <= range) {
					((EntityPlayerMP) name).sendMessage(new TextComponentString(Config.bracketColor.getString() + "["
							+ Config.defaultColor.getString() + "From " + Config.posColor.getString()
							+ compareCoordinatesDistance(mainPlayer.getPosition(), comparePlayer.getPosition())
							+ Config.defaultColor.getString() + " blocks away" + Config.bracketColor.getString() + "] "
							+ Config.angleBraceColor.getString() + "<" + Config.nameColor.getString() + player.getName()
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
								+ Config.nameColor.getString() + player.getName() + Config.angleBraceColor.getString()
								+ "> " + Config.bodyColor.getString() + message));
			} //end else

		} //end for
		
	} //end execute
}
