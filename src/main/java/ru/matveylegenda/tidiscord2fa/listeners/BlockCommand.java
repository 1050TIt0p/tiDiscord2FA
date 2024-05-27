package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.Config;

import java.util.List;

public class BlockCommand implements Listener {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if(blockedUtil.isEmpty()) {
            return;
        }

        Player player = event.getPlayer();
        Config config = plugin.config;

        if(blockedUtil.isBlocked(player)) {
            String[] commandArgs = event.getMessage().split(" ");
            String command = commandArgs[0].toLowerCase();
            List<String> allowedCommands = config.settings.allowedCommands;
            if(!allowedCommands.contains(command)) {
                event.setCancelled(true);
            }
        }
    }
}
