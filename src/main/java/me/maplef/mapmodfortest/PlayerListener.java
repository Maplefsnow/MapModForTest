package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;
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
    public void onPlayerChat(ClientChatEvent event) {
        LogUtils.getLogger().info("Player chat: " + event.getMessage());
    }
}
