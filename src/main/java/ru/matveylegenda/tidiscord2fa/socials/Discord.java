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
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import ru.matveylegenda.tidiscord2fa.listeners.jda.AccountListListener;
import ru.matveylegenda.tidiscord2fa.listeners.jda.AllowJoinListener;
import ru.matveylegenda.tidiscord2fa.listeners.jda.CodeListener;
import ru.matveylegenda.tidiscord2fa.listeners.jda.UnlinkListener;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

import java.util.concurrent.CompletableFuture;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class Discord {
    private final TiDiscord2FA plugin;
    private final MainConfig mainConfig;
    private final MessagesConfig messagesConfig;
    private final BlockedList blockedList;
    private final Database database;
    private JDA jda;

    public Discord(TiDiscord2FA plugin) {
        this.plugin = plugin;
        this.mainConfig = plugin.mainConfig;
        this.messagesConfig = plugin.messagesConfig;
        this.blockedList = plugin.blockedList;
        this.database = plugin.database;
    }

    public void enableBot() throws Exception {
        JDALogger.setFallbackLoggerEnabled(false);
        jda = JDABuilder.createDefault(mainConfig.discord.token)
                .enableIntents(
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT
                )
                .addEventListeners(
                        new AllowJoinListener(plugin),
                        new CodeListener(plugin),
                        new UnlinkListener(plugin),
                        new AccountListListener(plugin)
                )
                .build();

        setupCommands();
    }

    public void checkPlayer(Player player) {
        CompletableFuture.runAsync(() -> {
            String discordId = database.getDiscordIdByPlayerName(player.getName());

            if (discordId != null) {
                processBlockedPlayer(player);
                sendVerifyMessage(discordId, player);
                if (mainConfig.bossbar.enabled) {
                    createBossBar(player);
                }
                if (mainConfig.actionbar.enabled) {
                    startActionBarTimer(player);
                }
                scheduleKickTask(player, mainConfig.time);
            }
        });
    }

    private void processBlockedPlayer(Player player) {
        blockedList.add(player);

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public void sendVerifyMessage(String discordId, Player player) {
        Button allowButton = Button.primary(
                        "2fa-allow-join-" + player.getName(),
                        messagesConfig.discord.verifyMessage.buttons.allow.content
                )
                .withEmoji(Emoji.fromUnicode(messagesConfig.discord.verifyMessage.buttons.allow.emoji));

        jda.openPrivateChannelById(discordId).queue(channel -> {
            channel.sendMessage(messagesConfig.discord.verifyMessage.content)
                    .setComponents(
                            ActionRow.of(
                                    allowButton
                            )
                    )
                    .queue();
        });
    }

    private void createBossBar(Player player) {
        String barTitle = mainConfig.bossbar.title
                .replace("{time}", String.valueOf(mainConfig.time))
                .replace("{prefix}",messagesConfig.minecraft.prefix);
        BarColor barColor = BarColor.valueOf(mainConfig.bossbar.color);
        BarStyle barStyle = BarStyle.valueOf(mainConfig.bossbar.style);

        BossBar bossBar = Bukkit.createBossBar(
                barTitle,
                barColor,
                barStyle
        );

        bossBar.addPlayer(player);

        startBossBarTimer(player, bossBar, mainConfig.time);
    }

    private void startBossBarTimer(Player player, BossBar bossBar, int time) {
        new BukkitRunnable() {
            int remainingTime = time;

            @Override
            public void run() {
                if (remainingTime <= 0 || !player.isOnline() || !blockedList.contains(player)) {
                    bossBar.removePlayer(player);
                    cancel();
                    return;
                }

                remainingTime--;

                double progress = (double) remainingTime / time;
                bossBar.setProgress(progress);

                String barTitle = colorize(
                        mainConfig.bossbar.title
                                .replace("{time}", String.valueOf(remainingTime))
                                .replace("{prefix}", messagesConfig.minecraft.prefix)
                );
                bossBar.setTitle(barTitle);
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void startActionBarTimer(Player player) {
        new BukkitRunnable() {
            int time = mainConfig.time;

            @Override
            public void run() {
                if (time <= 0 || !player.isOnline() || !blockedList.contains(player)) {
                    player.spigot().sendMessage(
                            ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText("")
                    );
                    cancel();
                    return;
                }

                time--;

                String actionBarMessage = colorize(
                        mainConfig.actionbar.actionbarMessage
                                .replace("{time}", String.valueOf(time))
                                .replace("{prefix}", messagesConfig.minecraft.prefix)
                );
                player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(actionBarMessage)
                );
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void scheduleKickTask(Player player, int time) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (!player.isOnline()) {
                    return;
                }

                if (blockedList.contains(player)) {
                    String command = mainConfig.timeoutCommand
                            .replace("{player}", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            }
        }.runTaskLater(plugin, time * 20L);
    }

    private void setupCommands() {
        jda.updateCommands()
                .addCommands(
                        Commands.slash(mainConfig.unlinkCommand, mainConfig.unlinkDescription)
                                .addOption(OptionType.STRING, "player", "player", true),

                        Commands.slash(mainConfig.accountsCommand, mainConfig.accountsDescription)
                ).queue();
    }
}
