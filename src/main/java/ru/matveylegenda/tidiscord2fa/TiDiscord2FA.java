package ru.matveylegenda.tidiscord2fa;

import org.bstats.bukkit.Metrics;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.matveylegenda.tidiscord2fa.commands.Discord2FACommand;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;
import ru.matveylegenda.tidiscord2fa.database.impl.MySQLDatabase;
import ru.matveylegenda.tidiscord2fa.database.impl.SQLiteDatabase;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.JoinListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.MainListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth.AuthMeLoginListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth.NLoginLoginListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth.OpeNLoginLoginListener;
import ru.matveylegenda.tidiscord2fa.socials.Discord;
import ru.matveylegenda.tidiscord2fa.tasks.MessageTask;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.CodeMap;

import java.io.File;
import java.nio.file.Path;

public final class TiDiscord2FA extends JavaPlugin {
    private final ConsoleCommandSender consoleSender = getServer().getConsoleSender();

    public Database database;

    public Discord discord;

    public MainConfig mainConfig = new MainConfig();
    public MessagesConfig messagesConfig = new MessagesConfig();

    public BlockedList blockedList = new BlockedList();
    public CodeMap codeMap = new CodeMap();

    @Override
    public void onEnable() {
        consoleSender.sendMessage("");
        consoleSender.sendMessage("§b  _   _ ____  _                       _ ____  _____ _    ");
        consoleSender.sendMessage("§b | |_(_)  _ \\(_)___  ___ ___  _ __ __| |___ \\|  ___/ \\   ");
        consoleSender.sendMessage("§b | __| | | | | / __|/ __/ _ \\| '__/ _` | __) | |_ / _ \\  ");
        consoleSender.sendMessage("§b | |_| | |_| | \\__ \\ (_| (_) | | | (_| |/ __/|  _/ ___ \\ ");
        consoleSender.sendMessage("§b  \\__|_|____/|_|___/\\___\\___/|_|  \\__,_|_____|_|/_/   \\_\\");
        consoleSender.sendMessage(" §fВерсия: §9" + getDescription().getVersion() + " §8| §fАвтор: §91050TI_top");
        consoleSender.sendMessage("");

        loadConfigs();
        initializeDatabase();
        initializeDiscord();
        registerCommands();
        registerListeners();

        new MessageTask(this);
        new Metrics(this, 22007);
    }

    private void loadConfigs() {
        mainConfig.reload(Path.of(getDataFolder().getAbsolutePath(), "config.yml"));
        messagesConfig.reload(Path.of(getDataFolder().getAbsolutePath(), "messages.yml"));
    }

    private void initializeDatabase() {
        try {
            if (mainConfig.database.type.equalsIgnoreCase("MySQL")) {
                database = new MySQLDatabase(
                        mainConfig.database.mysql.host,
                        mainConfig.database.mysql.port,
                        mainConfig.database.mysql.database,
                        mainConfig.database.mysql.user,
                        mainConfig.database.mysql.password
                );
            } else {
                database = new SQLiteDatabase(new File(getDataFolder(), "users.db"));
            }
        } catch (Exception e) {
            getLogger().warning("Error during database initialization: " + e.getMessage());
            getPluginLoader().disablePlugin(this);
        }
    }

    private void initializeDiscord() {
        try {
            discord = new Discord(this);
            discord.enableBot();
        } catch (Exception e) {
            getLogger().warning("Error starting the Discord bot: " + e.getMessage());
            getPluginLoader().disablePlugin(this);
        }
    }

    private void registerCommands() {
        getServer().getCommandMap().register(mainConfig.command, new Discord2FACommand(this, mainConfig.command));
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new JoinListener(discord), this);
        pluginManager.registerEvents(new MainListener(this), this);

        if (pluginManager.getPlugin("AuthMe") != null) {
            pluginManager.registerEvents(new AuthMeLoginListener(discord), this);
        }

        if (pluginManager.getPlugin("nLogin") != null) {
            pluginManager.registerEvents(new NLoginLoginListener(discord), this);
        }

        if (pluginManager.getPlugin("OpeNLogin") != null) {
            pluginManager.registerEvents(new OpeNLoginLoginListener(discord), this);
        }
    }

    @Override
    public void onDisable() {
        database.close();
    }
}
