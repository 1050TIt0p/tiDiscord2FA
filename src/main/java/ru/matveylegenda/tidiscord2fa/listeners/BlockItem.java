package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

public class BlockItem implements Listener {
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if(blockedUtil.isBlocked(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if(blockedUtil.isBlocked(player)) {
            event.setCancelled(true);
        }
    }
}
