package com.kreezcraft.localizedchat;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

	public static Configuration cfg = LocalizedChat.config;

	public static String CATEGORY_RESTRICTIONS = "restrictions";
	public static String CATEGORY_INFORMATION = "informational";
	public static String CATEGORY_COLORS = "color codes";
	
	public static String CATEGORY_CHANNELS = "channel settings";
	
	public static Property talkRange, minHealthFactor, minHunger, requireHealthFactor, requireHunger;
	
	public static Property healthMessage, foodMessage, defaultHealthMessage, defaultFoodMessage;
	public static Property enableHealthMessage, enableFoodMessage;

	public static Property bracketColor, angleBraceColor, posColor, nameColor, bodyColor, defaultColor, errorColor, usageColor, channelColor;
	
	public static Property enableChannels, chanList;
	
	// Call this from CommonProxy.preInit(). It will create our config if it doesn't
	// exist yet and read the values if it does exist.
	public static void readConfig() {
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e1) {
			LocalizedChat.logger.log(Level.ERROR, "Problem loading config file!", e1);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_RESTRICTIONS, "Chat Restrictions");
		
		cfg.addCustomCategoryComment(CATEGORY_RESTRICTIONS, "talkRange doesn't will not have a toggle value, because then the mod would be useless");
		talkRange = cfg.get(CATEGORY_RESTRICTIONS, "talkRange", 1000, "The maximum range at which a player local to another player can be heard without requiring shouting.");

		requireHealthFactor = cfg.get(CATEGORY_RESTRICTIONS, "requireHealthFactor", false, "Enable actually needing health to perform a shout.");
		minHealthFactor = cfg.get(CATEGORY_RESTRICTIONS, "minHealthFactor", 2, "The factor by which the health bar is divided and thus the minimum health required before a player can shout to the server");
		
		requireHunger = cfg.get(CATEGORY_RESTRICTIONS, "requireHunger", true, "Enable actually needing hunger saturation before being able to shout.");
		minHunger = cfg.get(CATEGORY_RESTRICTIONS, "minHunger", 5, "Hunger is 20 integer (whole number) units, set this to the hunger cost per shout. The higher the number the less they can shout.");
		
		cfg.addCustomCategoryComment(CATEGORY_INFORMATION, "Informational");
		cfg.addCustomCategoryComment(CATEGORY_INFORMATION, "Applied when certain conditions are met, for instance when not enough health or saturation");
		
		healthMessage = cfg.get(CATEGORY_INFORMATION, "healthMessage", "You are too weak to shout!", "The message could be anything! It could be a link to your discord! or Buycraft! Anything at all!");
		enableHealthMessage = cfg.get(CATEGORY_INFORMATION, "enableHealthMessage", true, "Enable this to display the health message");
		
		foodMessage = cfg.get(CATEGORY_INFORMATION, "foodMessage", "You are too hungry to shout!", "The message could be anything! It could be a link to your discord! or Buycraft! Anything at all!");
		enableFoodMessage = cfg.get(CATEGORY_INFORMATION, "enableFoodMessage", true, "Enable this to display the health message");
		
		defaultHealthMessage = cfg.get(CATEGORY_INFORMATION, "defaultHealthMessage", "You can barely think to shout due to your weakness!","This displays the enableHealthMessage is false. Can be anything.");
		defaultFoodMessage = cfg.get(CATEGORY_INFORMATION, "defaultFoodMessage", "You try to shout but all your hear is your stomach growling!", "If enabledFoodMessage is false this will display. Can be anything.");
		
		cfg.addCustomCategoryComment(CATEGORY_COLORS, "Color Codes");
		cfg.addCustomCategoryComment(CATEGORY_COLORS, "These codes control the output of the colors sent to the client for the various parts of the mod");
		cfg.addCustomCategoryComment(CATEGORY_COLORS, "Color codes reference at http://minecraft.wikia.com/wiki/Formatting_Codes");
		
		bracketColor = cfg.get(CATEGORY_COLORS, "bracketColor", "§6", "Sets the color for brackets []");
		angleBraceColor = cfg.get(CATEGORY_COLORS, "angleBraceColor", "§7", "Sets the color for angle braces <>");
		posColor = cfg.get(CATEGORY_COLORS, "posColor","§e","Sets the color for positional information");
		nameColor = cfg.get(CATEGORY_COLORS, "nameColor", "§f", "Sets the color to be used player names");
		bodyColor = cfg.get(CATEGORY_COLORS, "bodyColor","§f","Sets the color the body of the message");
		defaultColor = cfg.get(CATEGORY_COLORS, "defaultColor", "§f","The color to use when no other color will do");
		errorColor = cfg.get(CATEGORY_COLORS, "errorColor", "§4","The color to use when an error is issued");
		usageColor = cfg.get(CATEGORY_COLORS, "usageColor", "§2","The color to use for the usage text");
		channelColor = cfg.get(CATEGORY_COLORS, "channelColor", "§2", "The color to use for channel names");
		
		cfg.addCustomCategoryComment(CATEGORY_CHANNELS, "Channel Settings");
		cfg.addCustomCategoryComment(CATEGORY_CHANNELS, "Using this section overrides the chat restriction section, it's mechanics, and turns regular talk into the dimension chat");
		
		enableChannels = cfg.get(CATEGORY_CHANNELS, "enableChannels", true, "Allows channels if true, overrides chat restrictions if enabled.");
		chanList = cfg.get(CATEGORY_CHANNELS, "chanList", new String[] {"server","market"},"Global channel names to use with /chat, currently there is no channel buffer so this is the same as switch radio stations or tv stations");
	}

}