package com.kreezcraft.localizedchat;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ChatConfig {

	public static class Server {

		public final IntValue talkRange;
		public final BooleanValue opAsPlayer;

		public final ConfigValue<? extends String> prefix;
		public final BooleanValue usePrefix;


		public final ConfigValue<? extends String> bracketColor;
		public final ConfigValue<? extends String> angleBraceColor;
		public final ConfigValue<? extends String> posColor;
		public final ConfigValue<? extends String> nameColor;
		public final ConfigValue<? extends String> bodyColor;
		public final ConfigValue<? extends String> defaultColor;
		public final ConfigValue<? extends String> errorColor;
		public final ConfigValue<? extends String> usageColor;
		public final ConfigValue<? extends String> channelColor;

		Server(ForgeConfigSpec.Builder builder) {
			builder.comment("Chat Restrictions",
					"talkRange doesn't have a toggle value, because then the mod would be useless")
					.push("restrictions");

			talkRange = builder
					.comment("The maximum range at which a player local to another player can be heard without requiring being an op.",
							"Default: 100")
					.defineInRange("talkRange", 100, 0, Integer.MAX_VALUE);

			opAsPlayer = builder
					.comment("Set to true to treat operators like players. Aka talking hits the entire world", "Default: false")
					.define("opAsPlayer", false);

			builder.pop();

			builder.comment("Miscellaneous")
					.push("miscellaneous");

			prefix = builder
					.comment("If alternate prefix is enabled then the distance won't be displayed but this prefix will.")
					.define("prefix", "", o -> (o instanceof String));

			usePrefix = builder
					.comment("Enable to use the prefix you set above")
					.define("usePrefix", false);

			builder.pop();

			builder.comment("These codes control the output of the colors sent to the client for the various parts of the mod",
					"Color codes reference at http://minecraft.wikia.com/wiki/Formatting_Codes")
					.push("color_codes");

			bracketColor = builder
					.comment("Sets the color for brackets []")
					.define("bracketColor", "§6", o -> (o instanceof String));

			angleBraceColor = builder
					.comment("Sets the color for angle braces <>")
					.define("angleBraceColor", "§7", o -> (o instanceof String));

			posColor = builder
					.comment("Sets the color for positional information")
					.define("posColor", "§e", o -> (o instanceof String));

			nameColor = builder
					.comment("Sets the color to be used player names")
					.define("nameColor", "§f", o -> (o instanceof String));

			bodyColor = builder
					.comment("Sets the color the body of the message")
					.define("bodyColor", "§f", o -> (o instanceof String));

			defaultColor = builder
					.comment("The color to use when no other color will do")
					.define("defaultColor", "§f", o -> (o instanceof String));

			errorColor = builder
					.comment("The color to use when an error is issued")
					.define("errorColor", "§4", o -> (o instanceof String));

			usageColor = builder
					.comment("The color to use for the usage text")
					.define("usageColor", "§2", o -> (o instanceof String));

			channelColor = builder
					.comment("The color to use for channel names")
					.define("channelColor", "§2", o -> (o instanceof String));


			builder.pop();
		}
	}

	public static final ForgeConfigSpec serverSpec;
	public static final ChatConfig.Server SERVER;

	static {
		final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		serverSpec = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		LocalizedChat.logger.debug("Loaded Localized Chat's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading configEvent) {
		LocalizedChat.logger.debug("Localized Chat's config just got changed on the file system!");
	}
}