package ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.socials.Discord;

public class AuthMeLoginListener implements Listener {
    private TiDiscord2FA plugin;

    public AuthMeLoginListener(TiDiscord2FA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLoginEvent(LoginEvent event) {
        Player player = event.getPlayer();

        new Discord(plugin).checkPlayer(player);
    }
}
