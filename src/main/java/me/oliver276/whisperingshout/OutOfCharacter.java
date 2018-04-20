package me.oliver276.whisperingshout;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OutOfCharacter extends CommandBase {
    private List aliases;

    public OutOfCharacter(){
        this.aliases = new ArrayList();
        this.aliases.add("ooc");
    }

    @Override
    public String getCommandName()
    {
        return "ooc";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "ooc <text>";
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
            icommandsender.addChatMessage((IChatComponent) new ChatComponentText( EnumChatFormatting.DARK_GREEN + "Use /ooc <Message... ...>"));
            return;
        }

        StringBuilder strBuilder = new StringBuilder();

        for (String word : astring){
            strBuilder.append(word).append(" ");
        }
        String message = strBuilder.toString();
        System.out.println(message);
        World workingWorld = icommandsender.getEntityWorld();
        List playerEntities = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(icommandsender.getCommandSenderName());
        for(Object name : playerEntities){
            EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getCommandSenderName()));
            ((EntityPlayerMP) name).addChatMessage((IChatComponent) new ChatComponentText(EnumChatFormatting.BLUE + "[Global Out-Of-Character Chat] " + EnumChatFormatting.GRAY + "<" + icommandsender.getCommandSenderName() + "> " + EnumChatFormatting.WHITE + message));


        }
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
}
