package me.maplef.mapmodfortest;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigManager {
    public static class Common
	{

		public final ConfigValue<String> bot_token;
        public final ConfigValue<Long> player_group_id;

		public Common(ForgeConfigSpec.Builder builder)
		{
			builder.push("bot");
            this.bot_token = builder.comment("DO NOT LEAK YOUR BOT TOKEN!")
                                .worldRestart()
                                .define("bot_token", "123:456789");
			builder.pop();

            builder.push("group");
            this.player_group_id = builder.comment("Player group_id")
                                .define("player_group", 123456L);
            builder.pop();
            
		}
	}

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	static //constructor
	{
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}
