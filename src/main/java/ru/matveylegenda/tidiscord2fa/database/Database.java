package ru.matveylegenda.tidiscord2fa.database;

public interface Database {
    void addUser(String playerName, String discordId);
    void removeUser(String playerName);

    String getDiscordIdByPlayerName(String playerName);
    int getAccountCountByDiscordId(String discordId);

    void close();
}
