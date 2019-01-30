package com.kreezcraft.localizedchat;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
@Config(modid = LocalizedChat.MODID, category = "")
public class ChatConfig {

	@Config.Comment({ "Chat Restrictions",
			"talkRange doesn't have a toggle value, because then the mod would be useless" })
	@Config.Name("Restrictions")
	public static Restrictions restrictions = new Restrictions();

	@Config.Comment({ "Informational Messages",
			"Applied when certain conditions are met, for instance when not enough health or saturation" })
	@Config.Name("Messages")
	public static Informational informational = new Informational();

	@Config.Comment({
			"These codes control the output of the colors sent to the client for the various parts of the mod",
			"Color codes reference at http://minecraft.wikia.com/wiki/Formatting_Codes" })
	@Config.Name("Color Codes")
	public static ColorCodes colorCodes = new ColorCodes();

	@Config.Name("Channel Settings")
	@Config.Comment({
			"Using this section overrides the chat restriction section, it's mechanics, and turns regular talk into the dimension chat" })
	public static Channels channels = new Channels();

	public static class Restrictions {

		@Config.Comment({
				"The maximum range at which a player local to another player can be heard without requiring shouting.",
				"Default: 1000" })
		@Config.Name("Range")
		public int talkRange = 1000;

		@Config.Comment({ "Set to false to treat operators like players. Aka talking hits the entire world", "Default: false" })
		@Config.Name("Operator Override")
		public boolean opOverride = false;

		@Config.Comment({ "Enable actually needing health to perform a shout." })
		@Config.Name("requireHealthFactor")
		public boolean requireHealthFactor = false;

		@Config.Comment({
				"The factor by which the health bar is divided and thus the minimum health required before a player can shout to the server",
				"Default: 2" })
		@Config.Name("minHealthFactor")
		public int minHealthFactor = 2;

		@Config.Comment({ "Enable actually needing hunger saturation before being able to shout." })
		@Config.Name("requireHunger")
		public boolean requireHunger = true;

		@Config.Comment({
				"Hunger is 20 integer (whole number) units, set this to the hunger cost per shout. The higher the number the less they can shout." })
		@Config.Name("minHunger")
		public int minHunger = 5;
	}

	public static class Informational {
		@Config.Comment({ "Enable this to display the health message", "Default: true" })
		@Config.Name("Should the low health message be enabled?")
		public boolean enableHealthMessage = true;

		@Config.Comment({ "The message to say when health requirement is enabled and the player is too weak to shout" })
		@Config.Name("Health Message")
		public String healthMessage = "You are too weak to shout!";

		@Config.Comment({ "Enable this to display the health message" })
		@Config.Name("Should the low hunger message be enabled?")
		public boolean enableFoodMessage = true;

		@Config.Comment({ "The message to say when the player is too hungry" })
		@Config.Name("Hunger Message")
		public String foodMessage = "You are too hungry to shout!";

	}

	public static class ColorCodes {
		@Config.Comment({ "Sets the color for brackets []" })
		@Config.Name("Bracket Color:")
		public String bracketColor = "§6";

		@Config.Comment({ "Sets the color for angle braces <>" })
		@Config.Name("Angle Brace Color")
		public String angleBraceColor = "§7";

		@Config.Comment({ "Sets the color for positional information" })
		@Config.Name("Postional Color")
		public String posColor = "§e";

		@Config.Comment({ "Sets the color to be used player names" })
		@Config.Name("Name Color")
		public String nameColor = "§f";

		@Config.Comment({ "Sets the color the body of the message" })
		@Config.Name("Message Color")
		public String bodyColor = "§f";

		@Config.Comment({ "The color to use when no other color will do" })
		@Config.Name("Default Color")
		public String defaultColor = "§f";

		@Config.Comment({ "The color to use when an error is issued" })
		@Config.Name("Error Color")
		public String errorColor = "§4";

		@Config.Comment({ "The color to use for the usage text" })
		@Config.Name("Usage Color")
		public String usageColor = "§2";

		@Config.Comment({ "The color to use for channel names" })
		@Config.Name("Channel Name Color")
		public String channelColor = "§2";
	}

	public static class Channels {
		@Config.Comment({ "Enables the channels Dim and World.", "Default: true" })
		@Config.Name("Enable Channels")
		public boolean enableChannels = true;
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(LocalizedChat.MODID)) {
			ConfigManager.sync(LocalizedChat.MODID, Config.Type.INSTANCE);
		}
	}
}