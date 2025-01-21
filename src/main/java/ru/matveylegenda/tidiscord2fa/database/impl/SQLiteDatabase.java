package ru.matveylegenda.tidiscord2fa.database.impl;

import ru.matveylegenda.tidiscord2fa.database.Database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabase implements Database {
    private final String url;

    public SQLiteDatabase(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `discord_users` (`player_name` TEXT NOT NULL, `discord_id` TEXT NOT NULL, PRIMARY KEY (`player_name`));");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public void addUser(String playerName, String discordId) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO `discord_users` (`player_name`, `discord_id`) VALUES (?, ?);")) {
            statement.setString(1, playerName);
            statement.setString(2, discordId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(String playerName) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM `discord_users` WHERE `player_name` = ?;")) {
            statement.setString(1, playerName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDiscordIdByPlayerName(String playerName) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT `discord_id` FROM `discord_users` WHERE `player_name` = ?;")) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("discord_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getAccountCountByDiscordId(String discordId) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM `discord_users` WHERE `discord_id` = ?;")) {
            statement.setString(1, discordId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<String> getAccountsByDiscordId(String discordId) {
        List<String> playerNames = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT `player_name` FROM `discord_users` WHERE `discord_id` = ?;")) {
            statement.setString(1, discordId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                playerNames.add(resultSet.getString("player_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerNames;
    }

    @Override
    public void close() {

    }
}
