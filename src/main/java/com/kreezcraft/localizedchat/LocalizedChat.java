package com.kreezcraft.localizedchat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.kreezcraft.localizedchat.Channels.Channel;
import com.kreezcraft.localizedchat.Channels.ChannelStorage;
import com.kreezcraft.localizedchat.Channels.IChannel;


@Mod(modid = LocalizedChat.MODID, version = LocalizedChat.VERSION, name = LocalizedChat.NAME, acceptableRemoteVersions = "*")
public class LocalizedChat
{
    public static final String MODID = "localizedchat";
	public static final String NAME = "Localized Chat";
    public static final String VERSION = "@VERSION@";
    
    public static Logger logger;
    public static Configuration config;
    public static LocalizedChat instance;
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "localizedchat.cfg"));
		Config.readConfig();
	}

	@EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        CapabilityManager.INSTANCE.register(IChannel.class, new ChannelStorage(), Channel.class);
    }



    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new TalkCommand());
        event.registerServerCommand(new ShoutCommand());
    }

}
