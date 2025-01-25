package me.maplef.mapmodfortest;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerListener {
    private final long player_group_id = ConfigManager.COMMON.player_group_id.get();

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getSide().isServer()) return;
        LogUtils.getLogger().info("Player interact!");
        Player player = event.getEntity();
        player.sendSystemMessage(Component.literal("weeeeeeeeee"));
        
    }

    @SubscribeEvent
    synchronized public void onPlayerChat(ServerChatEvent event) {
        TGBotManager.getInstance().sendMessage(player_group_id, event.getUsername() + ": " + event.getMessage());
    }
}
