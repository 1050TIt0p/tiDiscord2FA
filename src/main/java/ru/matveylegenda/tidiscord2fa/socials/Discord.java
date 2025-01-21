package ru.matveylegenda.tidiscord2fa.socials;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;
import ru.matveylegenda.tidiscord2fa.listeners.jda.AllowJoinListener;
import ru.matveylegenda.tidiscord2fa.listeners.jda.CodeListener;
import ru.matveylegenda.tidiscord2fa.listeners.jda.UnlinkListener;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

import java.util.concurrent.CompletableFuture;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class Discord {
    private TiDiscord2FA plugin;
    private static JDA jda;

    public Discord(TiDiscord2FA plugin) {
        this.plugin = plugin;
    }

    public void enableBot() {
        try {
            JDALogger.setFallbackLoggerEnabled(false);
            jda = JDABuilder.createDefault(MainConfig.instance.discord.token)
                    .enableIntents(
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT
                    )

                    .addEventListeners(
                            new AllowJoinListener(),
                            new CodeListener(),
                            new UnlinkListener()
                    )

                    .build();

            jda.updateCommands()
                    .addCommands(
                            Commands.slash(MainConfig.instance.unlinkCommand, MainConfig.instance.unlinkDescription)
                                    .addOption(OptionType.STRING, "player", "player", true)
                    ).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkPlayer(Player player) {
        CompletableFuture.runAsync(() -> {
            Database database = TiDiscord2FA.getDatabase();
            String discordId = database.getDiscordIdByPlayerName(player.getName());

            if (discordId != null) {
                BlockedList.instance.add(player);

                for (String message : MessagesConfig.instance.minecraft.join) {
                    player.sendMessage(
                            colorize(
                                    message.replace("{prefix}", MessagesConfig.instance.minecraft.prefix)
                            )
                    );
                }

                Button allowButton = Button.primary(
                        "2fa-allow-join-" + player.getName(),
                           MessagesConfig.instance.discord.verifyMessage.buttons.allow.content
                        )
                        .withEmoji(Emoji.fromUnicode(MessagesConfig.instance.discord.verifyMessage.buttons.allow.emoji));

                jda.openPrivateChannelById(discordId).queue(channel -> {
                    channel.sendMessage(MessagesConfig.instance.discord.verifyMessage.content)
                            .setComponents(
                                    ActionRow.of(
                                            allowButton
                                    )
                            )
                            .queue();
                });

                int time = MainConfig.instance.time;

                if (MainConfig.instance.bossbar.enabled) {
                    String barTitle = MainConfig.instance.bossbar.title
                            .replace("{time}", String.valueOf(time))
                            .replace("{prefix}", MessagesConfig.instance.minecraft.prefix);
                    BarColor barColor = BarColor.valueOf(MainConfig.instance.bossbar.color);
                    BarStyle barStyle = BarStyle.valueOf(MainConfig.instance.bossbar.style);

                    BossBar bossBar = Bukkit.createBossBar(
                            barTitle,
                            barColor,
                            barStyle
                    );
                    bossBar.addPlayer(player);

                    BukkitRunnable bossBarTask = new BukkitRunnable() {
                        int remainingTime = time;

                        @Override
                        public void run() {
                            if(remainingTime <= 0 || !player.isOnline() || !BlockedList.instance.contains(player)) {
                                bossBar.removePlayer(player);
                                cancel();
                                return;
                            }

                            remainingTime--;

                            double progress = (double) remainingTime / time;
                            bossBar.setProgress(progress);

                            String barTitle = colorize(
                                    MainConfig.instance.bossbar.title
                                            .replace("{time}", String.valueOf(remainingTime))
                                            .replace("{prefix}", MessagesConfig.instance.minecraft.prefix)
                            );
                            bossBar.setTitle(barTitle);
                        }
                    };
                    bossBarTask.runTaskTimer(plugin, 0L, 20L);

                    BukkitRunnable kickTask = new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (!player.isOnline()) {
                                return;
                            }

                            if (BlockedList.instance.contains(player)) {
                                String command = MainConfig.instance.timeoutCommand
                                        .replace("{player}", player.getName());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                            }
                        }
                    };
                    kickTask.runTaskLater(plugin, time * 20L);
                }
            }
        });
    }

    public static JDA getJDA() {
        return jda;
    }
}
