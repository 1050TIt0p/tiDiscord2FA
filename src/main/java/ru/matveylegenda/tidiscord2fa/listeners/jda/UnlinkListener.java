package ru.matveylegenda.tidiscord2fa.listeners.jda;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;

public class UnlinkListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(MainConfig.instance.unlinkCommand)) {
            return;
        }

        if (event.isFromGuild()) {
            return;
        }

        User user = event.getUser();
        String playerName = event.getOption("player").getAsString();

        Database database = TiDiscord2FA.getDatabase();
        String discordId = database.getDiscordIdByPlayerName(playerName);

        if (discordId == null || !user.getId().equals(discordId)) {
            event.reply(MessagesConfig.instance.discord.playerNotFound)
                    .queue();

            return;
        }

        database.removeUser(playerName);
        event.reply(MessagesConfig.instance.discord.accountUnlinked.replace("{player}", playerName))
                .queue();
    }
}
