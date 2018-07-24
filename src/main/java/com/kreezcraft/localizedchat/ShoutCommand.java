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

public class ShoutCommand extends CommandBase {
	private List aliases;

	public ShoutCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("s");
		this.aliases.add("sh");
		this.aliases.add("scream");
	}

	public double compareCoordinatesDistance(BlockPos player1, BlockPos player2) {
		double x = Math.abs(player1.getX() - player2.getX());
		double y = Math.abs(player1.getY() - player2.getY());
		double z = Math.abs(player1.getZ() - player2.getZ());
		return x + y + z;
	}

	@Override
	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

		// is opOnlyShout enabled? is the sender an Op
		if (Config.opOnlyShout.getBoolean()) {
			if (server.getPlayerList().getOppedPlayers().getGameProfileFromName(sender.getName()) != null) {
				return true;
			} else {
				return false;
			}
		} 
		
		//if we got here then everyone is allowed to show
		return true;

	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

	@Override
	public String getName() {
		return "shout";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "shout <text>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player;

		if (sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;

		}

		else {
			// what's the purpose of this?
			return;
		}
		if (!Config.enableChannels.getBoolean()) {
			player.sendMessage(new TextComponentString(
					Config.errorColor.getString() + "Global chat is enabled, so please use /chan list for channels"));
			return;
		}
		if (args.length < 1) {
			player.sendMessage(new TextComponentString(Config.errorColor.getString() + "Invalid arguments."));
			player.sendMessage(new TextComponentString(Config.usageColor.getString() + "Use /shout <Message... ...>"));
			return;
		}
		if (Config.requireHealthFactor.getBoolean() && player.getHealth() < player.getMaxHealth() / 4) {
			if (Config.enableFoodMessage.getBoolean()) {
				player.sendMessage(
						new TextComponentString(Config.bodyColor.getString() + Config.healthMessage.getString()));
			} else {
				player.sendMessage(new TextComponentString(
						Config.bodyColor.getString() + Config.defaultHealthMessage.getString()));
			}
			return;
		}

		if (Config.requireHunger.getBoolean() && player.getFoodStats().getFoodLevel() < Config.minHunger.getInt()) {
			if (Config.enableHealthMessage.getBoolean()) {
				player.sendMessage(
						new TextComponentString(Config.bodyColor.getString() + Config.healthMessage.getString()));
			} else {
				player.sendMessage(new TextComponentString(
						Config.bodyColor.getString() + Config.defaultHealthMessage.getString()));
			}
			return;
		} else {
			if (Config.requireHunger.getBoolean()) {
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - Config.minHunger.getInt());
			}
		}

		StringBuilder strBuilder = new StringBuilder();

		for (String word : args) {
			strBuilder.append(word).append(" ");
		}
		String message = strBuilder.toString();
		// this doesn't need to go to the log, chatting puts it there anyway
		// System.out.println(message);

		// Since I want shout to be server wide, we will get all the players instead
		// just the current world players
		// World workingWorld = player.getEntityWorld();

		World workingWorld = server.getEntityWorld();

		List playerEntities = workingWorld.playerEntities;
		EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(player.getName());
		for (Object name : playerEntities) {
			EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getName()));
			((EntityPlayerMP) name).sendMessage(new TextComponentString(
					Config.angleBraceColor.getString() + "<" + Config.nameColor.getString() + player.getName()
							+ Config.angleBraceColor.getString() + "> " + Config.bodyColor.getString() + message));
		}
	}

	@Override
	@Nonnull
	public List<String> getAliases() {
		return aliases;
	}
}
