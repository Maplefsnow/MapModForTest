package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import me.maplef.mapmodfortest.commands.CommandResponse;


public class TGBotManager {
    private static final TGBotManager instance = new TGBotManager();
    private final String BOT_TOKEN = ConfigManager.COMMON.bot_token.get();
    private final long player_group_id = ConfigManager.COMMON.player_group_id.get();
    private final TelegramClient tgClient = new OkHttpTelegramClient(BOT_TOKEN);

    private volatile boolean isRunning = false;

    private TGBotManager() {}

    public static TGBotManager getInstance() {
        return instance;
    }

    public void start() {
        isRunning = true;

        LogUtils.getLogger().info("bot_token: " + BOT_TOKEN);

        registerCommands();

        Thread tgBotThread = new Thread(() -> {
            try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
                botsApplication.registerBot(BOT_TOKEN, new TGListener(BOT_TOKEN));
                LogUtils.getLogger().info("MyTelegramBot successfully started!");
                this.sendMessage(player_group_id, "MapBotForge started from test server!");

                while (isRunning) {}
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (Exception ex) {}
        });

        tgBotThread.start();
    }

    public void stop() {
        this.sendMessage(player_group_id, "MapBotForge stopped from test server!");

        isRunning = false;
    }

    public void sendMessage(long chatId, String text) {
        new Thread(() -> {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(text).build();

            try {
                tgClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }).start();
    }

     static void registerCommands() {
         CommandManager.registerCommand("ping", (Integer argc, String[] argv) -> {
            CommandResponse rsp = new CommandResponse();
            rsp.text = "pong!";
            return rsp;
         });

         CommandManager.registerCommand("test", (Integer argc, String[] argv) -> {
            CommandResponse rsp = new CommandResponse();

            rsp.text = "Test command args: ";
            for (String arg : argv) {
                rsp.text += arg + " ";
            }
            rsp.text = rsp.text.trim();

            return rsp;
         });
    }
}
