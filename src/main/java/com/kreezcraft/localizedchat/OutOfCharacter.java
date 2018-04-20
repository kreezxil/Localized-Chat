package com.kreezcraft.localizedchat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OutOfCharacter extends CommandBase {
    private List aliases;

    public OutOfCharacter(){
        this.aliases = new ArrayList();
        this.aliases.add("ooc");
    }

    public String getCommandName()
    {
        return "ooc";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "ooc <text>";
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
            icommandsender.sendMessage((ITextComponent) new ITextComponent( EnumChatFormatting.DARK_GREEN + "Use /ooc <Message... ...>"));
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
        EntityPlayer mainPlayer = workingWorld.getPlayerEntityByUUID(icommandsender.getCommandSenderEntity());
        for(Object name : playerEntities){
            EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getCommandSenderEntity()));
            ((EntityPlayerMP) name).sendMessage((IChatComponent) new ChatComponentText(EnumChatFormatting.BLUE + "[Global Out-Of-Character Chat] " + EnumChatFormatting.GRAY + "<" + icommandsender.getCommandSenderEntity() + "> " + EnumChatFormatting.WHITE + message));


        }
    }

    public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        return true;
    }

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
