package ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth;

import com.nickuc.login.api.event.bukkit.auth.LoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.matveylegenda.tidiscord2fa.socials.Discord;

public class NLoginLoginListener implements Listener {
    private final Discord discord;

    public NLoginLoginListener(Discord discord) {
        this.discord = discord;
    }

    @EventHandler
    public void onLoginEvent(LoginEvent event) {
        Player player = event.getPlayer();

        discord.checkPlayer(player);
    }
}
