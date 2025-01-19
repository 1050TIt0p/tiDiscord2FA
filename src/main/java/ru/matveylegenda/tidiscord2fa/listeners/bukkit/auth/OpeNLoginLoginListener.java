package ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth;

import com.nickuc.openlogin.bukkit.api.events.AsyncLoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.socials.Discord;

public class OpeNLoginLoginListener implements Listener {

    private TiDiscord2FA plugin;

    public OpeNLoginLoginListener(TiDiscord2FA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLoginEvent(AsyncLoginEvent event) {
        Player player = event.getPlayer();

        new Discord(plugin).checkPlayer(player);
    }
}
