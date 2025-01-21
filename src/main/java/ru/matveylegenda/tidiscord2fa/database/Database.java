package ru.matveylegenda.tidiscord2fa.database;

import java.util.List;

public interface Database {
    void addUser(String playerName, String discordId);
    void removeUser(String playerName);

    String getDiscordIdByPlayerName(String playerName);
    int getAccountCountByDiscordId(String discordId);
    List<String> getAccountsByDiscordId(String discordId);

    void close();
}
