package ru.matveylegenda.tidiscord2fa;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.matveylegenda.tidiscord2fa.commands.Discord2FACommand;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;
import ru.matveylegenda.tidiscord2fa.database.impl.SQLiteDatabase;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.JoinListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.MainListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth.AuthMeLoginListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth.NLoginLoginListener;
import ru.matveylegenda.tidiscord2fa.listeners.bukkit.auth.OpeNLoginLoginListener;
import ru.matveylegenda.tidiscord2fa.socials.Discord;
import ru.matveylegenda.tidiscord2fa.utils.Metrics;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class TiDiscord2FA extends JavaPlugin {
    private final ConsoleCommandSender consoleSender = getServer().getConsoleSender();
    public Database database;

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

        MainConfig.instance.reload(Path.of(getDataFolder().getAbsolutePath(), "config.yml"));
        MessagesConfig.instance.reload(Path.of(getDataFolder().getAbsolutePath(), "messages.yml"));

        new Discord(this).enableBot();

        CompletableFuture.runAsync(() -> {
            try {
                database = new SQLiteDatabase(new File(getDataFolder(), "users.db"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        getServer().getCommandMap().register(MainConfig.instance.command, new Discord2FACommand(this, MainConfig.instance.command));

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new JoinListener(this), this);
        pluginManager.registerEvents(new MainListener(), this);
        pluginManager.registerEvents(new AuthMeLoginListener(this), this);
        pluginManager.registerEvents(new NLoginLoginListener(this), this);
        pluginManager.registerEvents(new OpeNLoginLoginListener(this), this);

        new Metrics(this, 22007);
    }

    @Override
    public void onDisable() {
        database.close();
    }
}
