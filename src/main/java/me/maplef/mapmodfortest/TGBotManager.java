package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TGBotManager {
    private static final TGBotManager instance = new TGBotManager();
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private final TelegramClient tgClient = new OkHttpTelegramClient(BOT_TOKEN);

    private static boolean isRunning = false;

    private TGBotManager() {}

    public static TGBotManager getInstance() {
        return instance;
    }

    public void start() {
        isRunning = true;

        Thread tgBotThread = new Thread(() -> {
            try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
                botsApplication.registerBot(BOT_TOKEN, new TGListener(BOT_TOKEN));
                LogUtils.getLogger().info("MyTelegramBot successfully started!");

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        System.out.println("NekoTownBot GoodBye!");
                        botsApplication.unregisterBot(BOT_TOKEN);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }));

                Thread.currentThread().join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tgBotThread.start();
    }

    public void stop() {
        isRunning = false;
    }

    public void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                                .chatId(chatId)
                                .text(text).build();

        tgClient.execute(message);
    }
}
