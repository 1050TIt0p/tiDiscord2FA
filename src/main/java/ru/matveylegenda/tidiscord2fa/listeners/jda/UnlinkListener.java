package ru.matveylegenda.tidiscord2fa.listeners.jda;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;

public class UnlinkListener extends ListenerAdapter {
    private final MainConfig mainConfig;
    private final MessagesConfig messagesConfig;
    private final Database database;

    public UnlinkListener(TiDiscord2FA plugin) {
        this.mainConfig = plugin.mainConfig;
        this.messagesConfig = plugin.messagesConfig;
        this.database = plugin.database;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(mainConfig.unlinkCommand)) {
            return;
        }

        if (event.isFromGuild()) {
            return;
        }

        User user = event.getUser();
        String playerName = event.getOption("player").getAsString();

        String discordId = database.getDiscordIdByPlayerName(playerName);

        if (discordId == null || !user.getId().equals(discordId)) {
            event.reply(messagesConfig.discord.playerNotFound)
                    .queue();

            return;
        }

        database.removeUser(playerName);
        event.reply(messagesConfig.discord.accountUnlinked.replace("{player}", playerName))
                .queue();
    }
}
