package me.maplef.mapmodfortest;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PlayerListener {
    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        long chat_id = -1002439035442L;
        try {
            TGBotManager.getInstance().sendMessage(chat_id, event.getUsername() + ": " + event.getMessage().getString());
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}
