package ru.matveylegenda.tidiscord2fa;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.matveylegenda.tidiscord2fa.commands.Discord2FA;
import ru.matveylegenda.tidiscord2fa.listeners.*;

public final class TiDiscord2FA extends JavaPlugin {
    private static TiDiscord2FA instance;
    public JDA jda;

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
        saveDefaultConfig();
        String token = getConfig().getString("token");
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

        getServer().getPluginManager().registerEvents(new BlockAttack(), this);
        getServer().getPluginManager().registerEvents(new BlockChat(), this);
        getServer().getPluginManager().registerEvents(new BlockCommand(), this);
        getServer().getPluginManager().registerEvents(new BlockDamage(), this);
        getServer().getPluginManager().registerEvents(new BlockInteract(), this);
        getServer().getPluginManager().registerEvents(new BlockInventory(), this);
        getServer().getPluginManager().registerEvents(new BlockItem(), this);
        getServer().getPluginManager().registerEvents(new BlockMove(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
    }

    public static TiDiscord2FA getInstance() {
        return instance;
    }
}
