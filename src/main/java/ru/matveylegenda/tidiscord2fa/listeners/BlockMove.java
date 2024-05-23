package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.ColorParser;

public class BlockMove implements Listener {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(blockedUtil.isBlocked(player)) {
            for (String message : plugin.getConfig().getStringList("messages.minecraft.join")) {
                player.sendMessage(ColorParser.hex(message));
            }

            event.setCancelled(true);
        }
    }
}
