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
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ChunkCoordComparator;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.realmsclient.gui.ChatFormatting;

public class chan extends CommandBase {
	private List aliases;

	public chan() {
		this.aliases = new ArrayList();
		this.aliases.add("chan");
		this.aliases.add("global");
		this.aliases.add("c");
	}

	@Override
	public String getName() {
		return "chan";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		if (Config.enableChannels.getBoolean()) {
			return Config.usageColor.getString() + "Channel chat\nchan <channel> message\nchan list - for channels";
		}
		return Config.usageColor.getString() + "Channels not enabled";
	}

	@Override
	@Nonnull
	public List<String> getAliases() {
		return aliases;
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

		if (!Config.enableChannels.getBoolean()) {
			player.sendMessage(new TextComponentString(getUsage(sender)));
			return;
		}

		if (args.length < 2) {
			player.sendMessage(new TextComponentString(Config.errorColor.getString() + "Invalid arguments."));
			player.sendMessage(new TextComponentString(getUsage(sender)));
			return;
		}

		if(args[1].toString().toLowerCase().equals("list")) {
			player.sendMessage(new TextComponentString(Config.usageColor.getString()+"Available channels are:\n"));
		
			for (String channel : Config.chanList.getStringList()) {
				player.sendMessage(new TextComponentString(Config.channelColor.getString()+channel));
			}
			return;
		}
		
		StringBuilder strBuilder = new StringBuilder();
		boolean done1stword = false;

		String channel = args[0];
		String[] temp = (String[]) Arrays.copyOfRange(args,1,args.length-1);
		
		for (String word : temp) {
			if (done1stword) {
				strBuilder.append(word).append(" ");
			}
			done1stword = true;
		}

		String message = strBuilder.toString();
		System.out.println(message);

		World workingWorld = sender.getEntityWorld();
		// List playerEntities =
		// MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		List<PlayerList> playerEntities = (List<PlayerList>) server.getServer().getPlayerList();
		EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(sender.getName());
		EntityPlayer targetPlayer = null;
		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getName()));
			targetPlayer = (EntityPlayer) name.getClass().cast(targetPlayer);
			targetPlayer.sendMessage(new TextComponentString(Config.bracketColor.getString() + "["
					+ Config.channelColor.getString() + "Global Out-Of-Character Chat" + Config.bracketColor.getString()
					+ "] " + Config.angleBraceColor.getString() + "<" + Config.nameColor.getString()
					+ targetPlayer.getName() + Config.angleBraceColor.getString() + "> " + Config.bodyColor.getString()
					+ message));

		} // end for

	} // end execute
}
