package ru.matveylegenda.tidiscord2fa.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

public class BlockChat implements Listener {
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (blockedUtil.isBlocked(player)) {
            event.setCancelled(true);
        }
    }
}