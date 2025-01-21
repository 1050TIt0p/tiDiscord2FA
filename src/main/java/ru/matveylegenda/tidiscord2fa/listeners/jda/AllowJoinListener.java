package ru.matveylegenda.tidiscord2fa.listeners.jda;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class AllowJoinListener extends ListenerAdapter {

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
                event.editMessage(MessagesConfig.instance.discord.playerNotFound)
                        .setComponents()
                        .queue();

                return;
            }

            if (!BlockedList.instance.contains(player)) {
                event.editMessage(MessagesConfig.instance.discord.verifyNotRequired)
                        .setComponents()
                        .queue();

                return;
            }

            BlockedList.instance.remove(player);

            for (String message : MessagesConfig.instance.minecraft.allowJoin) {
                player.sendMessage(
                        colorize(
                                message.replace("{prefix}", MessagesConfig.instance.minecraft.prefix)
                        )
                );
            }

            event.editMessage(MessagesConfig.instance.discord.allowJoin)
                    .setComponents()
                    .queue();
        }
    }
}
