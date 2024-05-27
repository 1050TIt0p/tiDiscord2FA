package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.ColorParser;
import ru.matveylegenda.tidiscord2fa.utils.Config;

public class BlockMove implements Listener {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(blockedUtil.isEmpty()) {
            return;
        }

        Player player = event.getPlayer();
        Config config = plugin.config;

        if(blockedUtil.isBlocked(player)) {
            for (String message : config.messages.minecraft.join) {
                player.sendMessage(ColorParser.hex(message));
            }

            event.setCancelled(true);
        }
    }
}
