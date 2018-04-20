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
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ChunkCoordComparator;

import java.util.ArrayList;
import java.util.List;

public class ShoutCommand extends CommandBase{
    private List aliases;

    public ShoutCommand(){
        this.aliases = new ArrayList();
        this.aliases.add("s");
        this.aliases.add("sh");
        this.aliases.add("scream");
        this.aliases.add("talklikemorgan");
    }

    @Override
    public String getCommandName()
    {
        return "shout";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "shout <text>";
    }

    @Override
    public List getCommandAliases()
    {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        EntityPlayer player;

        if(icommandsender instanceof EntityPlayer){
            player = (EntityPlayer)icommandsender;

        }
        else {
            icommandsender.addChatMessage((IChatComponent) new ChatComponentText("Player only command!"));
            return;
        }
        if(astring.length < 1)
        {
            icommandsender.addChatMessage((IChatComponent) new ChatComponentText( EnumChatFormatting.DARK_RED + "Invalid arguments."));
            icommandsender.addChatMessage((IChatComponent) new ChatComponentText( EnumChatFormatting.DARK_GREEN + "Use /shout <Message... ...>"));
            return;
        }

        double range = 50;

        StringBuilder strBuilder = new StringBuilder();

        for (String word : astring){
            strBuilder.append(word).append(" ");
        }
        String message = strBuilder.toString();
        System.out.println(message);
        World workingWorld = icommandsender.getEntityWorld();
        List playerEntities = workingWorld.playerEntities;
        EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(icommandsender.getCommandSenderName());
        for(Object name : playerEntities){
            EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getCommandSenderName()));
            if (compareCoordinatesDistance(mainPlayer.getPlayerCoordinates(),comparePlayer.getPlayerCoordinates()) <= range){
                ((EntityPlayerMP) name).addChatMessage((IChatComponent) new ChatComponentText(EnumChatFormatting.GOLD + "[From " + EnumChatFormatting.RED + compareCoordinatesDistance(mainPlayer.getPlayerCoordinates(),comparePlayer.getPlayerCoordinates()) + EnumChatFormatting.GOLD + " blocks away] " + EnumChatFormatting.GRAY + "<" + icommandsender.getCommandSenderName() + "> " + EnumChatFormatting.WHITE + message));
            }

        }
    }

    public double compareCoordinatesDistance(ChunkCoordinates player1, ChunkCoordinates player2){
        double x = Math.abs(player1.posX - player2.posX);
        double y = Math.abs(player1.posY - player2.posY);
        double z = Math.abs(player1.posZ - player2.posZ);
        return x + y + z;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender,
                                        String[] astring)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i)
    {
        return false;
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		
	}
}
