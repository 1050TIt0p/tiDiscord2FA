package ru.matveylegenda.tidiscord2fa.listeners.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.database.Database;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.SessionMap;

import java.util.concurrent.CompletableFuture;

public class QuitListener implements Listener {
    private final Database database;
    private final BlockedList blockedList;
    private final SessionMap sessionMap;

    public QuitListener(TiDiscord2FA plugin) {
        this.database = plugin.database;
        this.blockedList = plugin.blockedList;
        this.sessionMap = plugin.sessionMap;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (blockedList.contains(player)) {
            blockedList.remove(player);

            return;
        }

        CompletableFuture.runAsync(() -> {
            if (database.getDiscordIdByPlayerName(player.getName()) != null) {
                sessionMap.addSession(player, player.getAddress().getAddress().getHostAddress());
            }
        });
    }
}
