package ru.matveylegenda.tidiscord2fa.listeners.jda;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class AllowJoinListener extends ListenerAdapter {
    private final MessagesConfig messagesConfig;
    private final BlockedList blockedList;

    public AllowJoinListener(TiDiscord2FA plugin) {
        this.messagesConfig = plugin.messagesConfig;
        this.blockedList = plugin.blockedList;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.isFromGuild()) {
            return;
        }

        if (event.getComponentId().contains("2fa-allow-join")) {
            String playerName = event.getComponentId().replace("2fa-allow-join-", "");

            if (event.isAcknowledged()) {
                return;
            }

            Player player = Bukkit.getPlayerExact(playerName);

            if (player == null) {
                event.editMessage(messagesConfig.discord.playerNotFound)
                        .setComponents()
                        .queue();

                return;
            }

            if (!blockedList.contains(player)) {
                event.editMessage(messagesConfig.discord.verifyNotRequired)
                        .setComponents()
                        .queue();

                return;
            }

            blockedList.remove(player);

            GameMode gameMode = player.getGameMode();
            if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
                player.setFlying(false);
                player.setAllowFlight(false);
            }

            for (String message : messagesConfig.minecraft.allowJoin) {
                player.sendMessage(
                        colorize(
                                message.replace("{prefix}", messagesConfig.minecraft.prefix)
                        )
                );
            }

            event.editMessage(messagesConfig.discord.allowJoin)
                    .setComponents()
                    .queue();
        }
    }
}
