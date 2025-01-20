package ru.matveylegenda.tidiscord2fa.listeners.jda;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;
import ru.matveylegenda.tidiscord2fa.utils.CodeMap;

public class CodeListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {
            return;
        }

        User user = event.getAuthor();
        Message message = event.getMessage();
        String messageContent = event.getMessage().getContentRaw();

        if (CodeMap.instance.containsValue(messageContent)) {
            Database database = TiDiscord2FA.getDatabase();
            int accountsCount = database.getAccountCountByDiscordId(user.getId());

            if (accountsCount >= MainConfig.instance.maxLinkAccounts) {
                message.reply("Вы достигли лимита по привязанным аккаунтам")
                        .queue();

                return;
            }

            String playerName = CodeMap.instance.getKey(messageContent);

            database.addUser(playerName, user.getId());
            CodeMap.instance.remove(playerName);

            message.reply(MessagesConfig.instance.discord.accountLinked.replace("{player}", playerName))
                    .queue();
        }
    }
}
