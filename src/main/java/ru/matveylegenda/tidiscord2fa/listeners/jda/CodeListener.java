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
    private final MainConfig mainConfig;
    private final MessagesConfig messagesConfig;
    private final CodeMap codeMap;
    private final Database database;

    public CodeListener(TiDiscord2FA plugin) {
        this.mainConfig = plugin.mainConfig;
        this.messagesConfig = plugin.messagesConfig;
        this.codeMap = plugin.codeMap;
        this.database = plugin.database;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {
            return;
        }

        User user = event.getAuthor();
        Message message = event.getMessage();
        String messageContent = event.getMessage().getContentRaw();

        if (codeMap.containsValue(messageContent)) {
            int accountsCount = database.getAccountCountByDiscordId(user.getId());

            if (accountsCount >= mainConfig.maxLinkAccounts) {
                message.reply(messagesConfig.discord.maxLinkAccounts)
                        .queue();

                return;
            }

            String playerName = codeMap.getKey(messageContent);

            database.addUser(playerName, user.getId());
            codeMap.remove(playerName);

            message.reply(messagesConfig.discord.accountLinked.replace("{player}", playerName))
                    .queue();
        }
    }
}
