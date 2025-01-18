package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TGListener implements LongPollingSingleThreadUpdateConsumer {
    private final String BOT_TOKEN;
    private final TelegramClient tgClient;

    public TGListener(String token) {
        this.BOT_TOKEN = token;
        this.tgClient = new OkHttpTelegramClient(BOT_TOKEN);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                player.sendSystemMessage(Component.literal("[TG] " + update.getMessage().getFrom().getUserName() + ": " + message_text));
            }

            LogUtils.getLogger().info(String.format("Received from chat_%s: %s", String.valueOf(chat_id), message_text));
        }
    }
}
