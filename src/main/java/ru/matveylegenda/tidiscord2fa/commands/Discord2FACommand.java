package ru.matveylegenda.tidiscord2fa.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MainConfig;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.database.Database;
import ru.matveylegenda.tidiscord2fa.utils.CodeMap;

import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class Discord2FACommand extends BukkitCommand {
    private TiDiscord2FA plugin;
    private Database database;

    public Discord2FACommand(TiDiscord2FA plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.database = plugin.database;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (args.length != 1) {
            for (String message : MessagesConfig.instance.minecraft.usage) {
                sender.sendMessage(
                        colorize(
                                message.replace("{prefix}", MessagesConfig.instance.minecraft.prefix)
                        )
                );
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("tidiscord2fa.reload")) {
            MainConfig.instance.reload(Path.of(plugin.getDataFolder().getAbsolutePath(), "config.yml"));
            MessagesConfig.instance.reload(Path.of(plugin.getDataFolder().getAbsolutePath(), "messages.yml"));

            for (String message : MessagesConfig.instance.minecraft.reload) {
                sender.sendMessage(
                        colorize(
                                message.replace("{prefix}", MessagesConfig.instance.minecraft.prefix)
                        )
                );
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("link")) {
            if (sender instanceof Player player) {
                CompletableFuture.runAsync(() -> {
                    if (database.getDiscordIdByPlayerName(player.getName()) != null) {
                        for (String message : MessagesConfig.instance.minecraft.alreadyLinked) {
                            player.sendMessage(colorize(message));
                        }

                        return;
                    }

                    if (MainConfig.instance.discord.linkType.equalsIgnoreCase("OAUTH2")) {
                        for (String message : MessagesConfig.instance.minecraft.linkOauth) {
                            player.sendMessage(
                                    colorize(
                                            message.replace("{link}", MainConfig.instance.discord.oauth2.oauthUri)
                                    )
                            );
                        }
                    } else {
                        for (String message : MessagesConfig.instance.minecraft.linkCode) {
                            player.sendMessage(
                                    colorize(
                                            message.replace("{code}", generateCode(player))
                                    )
                            );
                        }
                    }
                });
            } else {
                sender.sendMessage("Команду может использовать только игрок");
            }
        }

        return true;
    }

    private String generateCode(Player player) {
        String chars = MainConfig.instance.code.chars;
        int length = MainConfig.instance.code.length;
        String code;

        do {
            StringBuilder codeBuilder = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(chars.length());
                codeBuilder.append(chars.charAt(index));
            }

            code = codeBuilder.toString();
        } while (CodeMap.instance.containsValue(code));

        CodeMap.instance.put(player.getName(), code);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!CodeMap.instance.containsKey(player.getName())) {
                    return;
                }

                CodeMap.instance.remove(player.getName());
            }
        }.runTaskLaterAsynchronously(plugin, 5 * 60 * 20);

        return code;
    }
}
