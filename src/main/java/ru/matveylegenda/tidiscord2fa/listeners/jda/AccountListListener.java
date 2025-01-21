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

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals(MainConfig.instance.accountsCommand)) {
            return;
        }

        if (event.isFromGuild()) {
            return;
        }

        User user = event.getUser();

        Database database = TiDiscord2FA.getDatabase();
        List<String> accountList = database.getAccountsByDiscordId(user.getId());

        if (accountList.isEmpty()) {
            event.reply(MessagesConfig.instance.discord.noLinkedAccounts)
                    .queue();
        }

        event.reply(MessagesConfig.instance.discord.accountList.replace("{accounts}", String.join(", ", accountList)))
                .queue();
    }
}
