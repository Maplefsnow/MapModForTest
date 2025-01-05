package me.maplef.mapmodfortest;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Mapmodfortest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerListener {
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getSide().isServer()) return;
        LogUtils.getLogger().info("Player interact!");
        Player player = event.getEntity();
        player.sendSystemMessage(Component.literal("weeeeeeeeeeeeeeeeeeeee"));
    }
}
