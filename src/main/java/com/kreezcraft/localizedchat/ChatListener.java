package com.kreezcraft.localizedchat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;

import java.util.List;

public class ChatListener {

    @SubscribeEvent
    public void onChat(ServerChatEvent event){
        String message = event.message;
        double range = 10;

        System.out.println(message);
        World workingWorld = event.player.getEntityWorld();
        List playerEntities = workingWorld.playerEntities;
        EntityPlayer mainPlayer = workingWorld.getPlayerEntityByName(event.player.getCommandSenderName());
        for(Object name : playerEntities){
            EntityPlayer comparePlayer = (workingWorld.getPlayerEntityByName(((EntityPlayerMP) name).getCommandSenderName()));
            if (compareCoordinatesDistance(mainPlayer.getPlayerCoordinates(),comparePlayer.getPlayerCoordinates()) <= range){
                ((EntityPlayerMP) name).addChatMessage((IChatComponent) new ChatComponentText(EnumChatFormatting.GOLD + "[From " + EnumChatFormatting.RED + compareCoordinatesDistance(mainPlayer.getPlayerCoordinates(),comparePlayer.getPlayerCoordinates()) + EnumChatFormatting.GOLD + " blocks away] " + EnumChatFormatting.GRAY + "<" + event.player.getCommandSenderName() + "> " + EnumChatFormatting.WHITE + message));
            }

        }
        event.setCanceled(true);
    }

    public double compareCoordinatesDistance(ChunkCoordinates player1, ChunkCoordinates player2){
        double x = Math.abs(player1.posX - player2.posX);
        double y = Math.abs(player1.posY - player2.posY);
        double z = Math.abs(player1.posZ - player2.posZ);
        return x + y + z;
    }


}
