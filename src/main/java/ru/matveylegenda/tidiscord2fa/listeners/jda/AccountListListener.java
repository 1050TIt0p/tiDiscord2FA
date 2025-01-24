package ru.matveylegenda.tidiscord2fa.listeners.jda;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;

import java.util.List;

public class AccountListListener extends ListenerAdapter {
    private final MainConfig mainConfig;
    private final MessagesConfig messagesConfig;
    private final Database database;

    public AccountListListener(TiDiscord2FA plugin) {
        this.mainConfig = plugin.mainConfig;
        this.messagesConfig = plugin.messagesConfig;
        this.database = plugin.database;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals(mainConfig.accountsCommand)) {
            return;
        }

        if (event.isFromGuild()) {
            return;
        }

        User user = event.getUser();

        List<String> accountList = database.getAccountsByDiscordId(user.getId());

        if (accountList.isEmpty()) {
            event.reply(messagesConfig.discord.noLinkedAccounts)
                    .queue();
        }

        event.reply(messagesConfig.discord.accountList.replace("{accounts}", String.join(", ", accountList)))
                .queue();
    }
}
