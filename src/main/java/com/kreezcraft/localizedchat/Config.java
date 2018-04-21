package com.kreezcraft.localizedchat;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

	public static Configuration cfg = LocalizedChat.config;

	public static String CATEGORY_RESTRICTIONS = "restrictions";

	public static Property talkRange, minHealthFactor, minHunger, requireHealthFactor, requireHunger;

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
	}

}