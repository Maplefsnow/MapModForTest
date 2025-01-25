package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

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
            String params = "";
            if (strParts.length > 1) {
                params = strParts[1];
            }

            CommandResponse rsp = CommandExecutor.execute(commandHead, params);

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

        LogUtils.getLogger().info(String.format("Received from chat_%ld: %s", chat_id, message_text));
    }
}

class CommandExecutor {
    private static final HashSet<String> commandHeaders = new HashSet<>();
    private static final HashMap<String, Function<String, CommandResponse>> commands = new HashMap<>();

    public static void registerCommand(String header, Function<String, CommandResponse> func) {
        commandHeaders.add(header);
        commands.put(header, func);
    }

    public static CommandResponse execute(String cmdHeader, String param) {
        CommandResponse rsp = new CommandResponse();

        if (!commandHeaders.contains(cmdHeader)) {
            rsp.number = 0;
            rsp.text = "no such command!";
            return rsp;
        }

        try {
            Function<String, CommandResponse> func = commands.get(cmdHeader);
            rsp = func.apply(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rsp;
    }
}

class CommandResponse {
    public String text;
    public int number;
}
