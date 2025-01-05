package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TGBot implements LongPollingSingleThreadUpdateConsumer {
    private static final TGBot instance = new TGBot();
    private final String BOT_TOKEN = "8004078585:AAFB3M9VWNrb-595GTRpXZludcTveeAm17g";
    private final TelegramClient tgClient = new OkHttpTelegramClient(BOT_TOKEN);
    private final TelegramBotsLongPollingApplication botsApp = new TelegramBotsLongPollingApplication();

    private static final Object lock = new Object();
    private static boolean isRunning = false;

    private TGBot() {}

    public static TGBot getInstance() {
        return instance;
    }

    public void start() {
        isRunning = true;
        Thread botThread = new Thread(() -> {
            try {
                botsApp.registerBot(BOT_TOKEN, this);
                System.out.println("NekoTownBot Hello World!");
            } catch (Exception e) {
                e.printStackTrace();
            }

            synchronized (lock) {
                while (isRunning) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                System.out.println("NekoTownBot GoodBye!");
                botsApp.unregisterBot(BOT_TOKEN);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        botThread.start();
    }

    public void stop() {
        synchronized (lock) {
            isRunning = false;
            lock.notify();
        }
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
