package ru.matveylegenda.tidiscord2fa;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.matveylegenda.tidiscord2fa.commands.Discord2FA;
import ru.matveylegenda.tidiscord2fa.listeners.*;
import ru.matveylegenda.tidiscord2fa.utils.Config;
import ru.matveylegenda.tidiscord2fa.utils.Metrics;

import java.io.File;

public final class TiDiscord2FA extends JavaPlugin {
    private static TiDiscord2FA instance;
    public JDA jda;
    private File configFile = new File(getDataFolder() + "/config.yml");
    public Config config = new Config();

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("");
        getLogger().info("§b  _   _ ____  _                       _ ____  _____ _    ");
        getLogger().info("§b | |_(_)  _ \\(_)___  ___ ___  _ __ __| |___ \\|  ___/ \\   ");
        getLogger().info("§b | __| | | | | / __|/ __/ _ \\| '__/ _` | __) | |_ / _ \\  ");
        getLogger().info("§b | |_| | |_| | \\__ \\ (_| (_) | | | (_| |/ __/|  _/ ___ \\ ");
        getLogger().info("§b  \\__|_|____/|_|___/\\___\\___/|_|  \\__,_|_____|_|/_/   \\_\\");
        getLogger().info(" §fВерсия: §9" + getDescription().getVersion() + " §8| §fАвтор: §91050TI_top");
        getLogger().info("");

        getCommand("tidiscord2fa").setExecutor(new Discord2FA());

        reloadConfig0();

        String token = config.TOKEN;
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT
                    )

                    .addEventListeners(
                            new AllowJoin()
                    )

                    .build();
        } catch (Exception e) {
            getLogger().severe("Ошибка: " + e);
            getServer().getPluginManager().disablePlugin(this);
        }

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new BlockAttack(), this);
        pluginManager.registerEvents(new BlockChat(), this);
        pluginManager.registerEvents(new BlockCommand(), this);
        pluginManager.registerEvents(new BlockDamage(), this);
        pluginManager.registerEvents(new BlockInteract(), this);
        pluginManager.registerEvents(new BlockInventory(), this);
        pluginManager.registerEvents(new BlockItem(), this);
        pluginManager.registerEvents(new BlockMove(), this);
        pluginManager.registerEvents(new PlayerJoin(), this);
        pluginManager.registerEvents(new PlayerQuit(), this);

        int pluginId = 22007;
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {

    }

    public static TiDiscord2FA getInstance() {
        return instance;
    }

    public void reloadConfig0() {
        config.reload(configFile.toPath());
    }
}
