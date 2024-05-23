package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

public class PlayerQuit implements Listener {
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(blockedUtil.isBlocked(player)) {
            blockedUtil.remove(player);
        }
    }
}
