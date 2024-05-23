package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

public class BlockInventory implements Listener {
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(blockedUtil.isBlocked(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();

        if(blockedUtil.isBlocked(player)) {
            event.setCancelled(true);
        }
    }
}
