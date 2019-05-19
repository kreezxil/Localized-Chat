package com.kreezcraft.localizedchat;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
@Config(modid = LocalizedChat.MODID, category = "")
public class ChatConfig {

	@Config.Comment({ "Chat Restrictions",
			"talkRange doesn't have a toggle value, because then the mod would be useless" })
	@Config.Name("Restrictions")
	public static Restrictions restrictions = new Restrictions();

@Config.Name("Miscellaneous")
public static Miscellaneous miscellaneous = new Miscellaneous();

	@Config.Comment({
			"These codes control the output of the colors sent to the client for the various parts of the mod",
			"Color codes reference at http://minecraft.wikia.com/wiki/Formatting_Codes" })
	@Config.Name("Color Codes")
	public static ColorCodes colorCodes = new ColorCodes();
	
	public static class Restrictions {

		@Config.Comment({
				"The maximum range at which a player local to another player can be heard without requiring being an op.",
				"Default: 100" })
		@Config.Name("Range")
		public int talkRange = 100;

		@Config.Comment({ "Set to true to treat operators like players. Aka talking hits the entire world", "Default: false" })
		@Config.Name("Operator as Players")
		public boolean opAsPlayer = false;

	}

	public static class Miscellaneous {
		@Config.Comment({"If alternate prefix is enabled then the distance won't be displayed but this prefix will."})
		@Config.Name("Alternate Prefix")
		public String prefix = "";
		
		@Config.Comment({"Enable to use the prefix you set above"})
		@Config.Name("Default Prefix Override")
		public boolean usePrefix = false;
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

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(LocalizedChat.MODID)) {
			ConfigManager.sync(LocalizedChat.MODID, Config.Type.INSTANCE);
		}
	}
}