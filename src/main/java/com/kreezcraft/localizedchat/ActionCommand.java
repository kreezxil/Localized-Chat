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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ChunkCoordComparator;

import java.util.ArrayList;
import java.util.List;

public class ActionCommand extends CommandBase{
    private List aliases;

    public ActionCommand(){
        this.aliases = new ArrayList();
        this.aliases.add("a");
        this.aliases.add("ac");
    }

    public String getCommandName()
    {
        return "action";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "action <action>";
    }

    public List getCommandAliases()
    {
        return this.aliases;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        EntityPlayer player;

        if(icommandsender instanceof EntityPlayer){
            player = (EntityPlayer)icommandsender;
        }
        else {
            icommandsender.sendMessage((ITextComponent) new ITextComponent("Player only command!"));
            return;
        }
        if(astring.length < 1)
        {
            icommandsender.sendMessage((ITextComponent) new ITextComponent( EnumChatFormatting.DARK_RED + "Invalid arguments."));
            icommandsender.sendMessage((ITextComponent) new ITextComponent( EnumChatFormatting.DARK_GREEN + "Use /action <Message... ...>"));
            return;
        }

        double range = 10;

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
                ((EntityPlayerMP) name).sendMessage((IChatComponent) new ChatComponentText(EnumChatFormatting.GOLD + "[From " + EnumChatFormatting.RED + compareCoordinatesDistance(mainPlayer.getPlayerCoordinates(),comparePlayer.getPlayerCoordinates()) + EnumChatFormatting.GOLD + " blocks away] " + EnumChatFormatting.AQUA + icommandsender.getCommandSenderName() + " " + EnumChatFormatting.DARK_AQUA + message));
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
