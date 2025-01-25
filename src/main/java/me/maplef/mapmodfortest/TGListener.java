package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import me.maplef.mapmodfortest.commands.CommandResponse;

import java.util.ArrayList;
import java.util.List;

public class TGListener implements LongPollingSingleThreadUpdateConsumer {
    private final String BOT_TOKEN;
    private final TelegramClient tgClient;

    public TGListener(String token) {
        this.BOT_TOKEN = token;
        this.tgClient = new OkHttpTelegramClient(BOT_TOKEN);
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;

        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();

        if (message_text.startsWith("/")) {
            message_text = message_text.substring(1);
            String[] strParts = message_text.split(" ", 2);
            String commandHead = strParts[0];
            Integer paramCnt = 0;
            List<String> params = new ArrayList<>();
            if (strParts.length > 1) {
                paramCnt = strParts.length - 1;
                for (int i = 1; i < strParts.length; i++) {
                    params.add(strParts[i]);
                }
            }

            CommandResponse rsp = CommandManager.execute(commandHead, paramCnt, params.toArray(new String[params.size()]));

            SendMessage message = SendMessage.builder()
                                    .chatId(chat_id)
                                    .text(rsp.text)
                                    .build();
            try {
                tgClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        LogUtils.getLogger().info(String.format("Received from chat_%d: %s", chat_id, message_text));
    }
}

