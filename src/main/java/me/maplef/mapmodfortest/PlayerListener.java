package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerListener {
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getSide().isServer()) return;
        LogUtils.getLogger().info("Player interact!");
        Player player = event.getEntity();
        player.sendSystemMessage(Component.literal("weeeeeeeeeeeeeeeeeeeee"));
        
    }

    @SubscribeEvent
    synchronized public void onPlayerChat(ServerChatEvent event) {
        long chat_id = -1002439035442L;
        TGBotManager.getInstance().sendMessage(chat_id, event.getUsername() + ": " + event.getMessage());
    }
}
