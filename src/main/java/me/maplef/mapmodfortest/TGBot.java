package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TGBot implements LongPollingSingleThreadUpdateConsumer {
    private static final TGBot instance = new TGBot();
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private TelegramBotsLongPollingApplication botsApp;
    private final TelegramClient tgClient;

    private static boolean isRunning = false;

    private TGBot() {
        tgClient = new OkHttpTelegramClient(BOT_TOKEN);
    }

    public static TGBot getInstance() {
        return instance;
    }

    public void start() {
        isRunning = true;

        try {
            LogUtils.getLogger().info("NekoTownBot Hello World!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            LogUtils.getLogger().info(String.format("Received from chat_%d: %s", chat_id, message_text));
        }
    }
}
