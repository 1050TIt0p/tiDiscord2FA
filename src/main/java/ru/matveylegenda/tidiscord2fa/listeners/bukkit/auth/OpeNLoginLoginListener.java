package ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth;

import com.nickuc.openlogin.bukkit.api.events.AsyncLoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.matveylegenda.tidiscord2fa.socials.Discord;

public class OpeNLoginLoginListener implements Listener {
    private final Discord discord;

    public OpeNLoginLoginListener(Discord discord) {
        this.discord = discord;
    }

    @EventHandler
    public void onLoginEvent(AsyncLoginEvent event) {
        Player player = event.getPlayer();

        discord.checkPlayer(player);
    }
}
