package ru.matveylegenda.tidiscord2fa.listeners.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import ru.matveylegenda.tidiscord2fa.socials.Discord;

public class JoinListener implements Listener {
    private final Discord discord;

    public JoinListener(Discord discord) {
        this.discord = discord;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        if (pluginManager.getPlugin("AuthMe") != null ||
            pluginManager.getPlugin("nLogin") != null ||
            pluginManager.getPlugin("OpeNLogin") != null) {
            return;
        }

        Player player = event.getPlayer();

        discord.checkPlayer(player);
    }
}
