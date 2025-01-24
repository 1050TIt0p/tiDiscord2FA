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
    private final TiDiscord2FA plugin;
    private final MainConfig mainConfig;
    private final MessagesConfig messagesConfig;
    private final CodeMap codeMap;
    private final Database database;

    public Discord2FACommand(TiDiscord2FA plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.mainConfig = plugin.mainConfig;
        this.messagesConfig = plugin.messagesConfig;
        this.codeMap = plugin.codeMap;
        this.database = plugin.database;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (args.length != 1) {
            sendUsageMessages(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> handleReloadCommand(sender);
            case "link" -> handleLinkCommand(sender);
            default -> sendUsageMessages(sender);
        }

        return true;
    }

    private void handleReloadCommand(CommandSender sender) {
        if (!sender.hasPermission("tidiscord2fa.reload")) {
            return;
        }

        mainConfig.reload(Path.of(plugin.getDataFolder().getAbsolutePath(), "config.yml"));
        messagesConfig.reload(Path.of(plugin.getDataFolder().getAbsolutePath(), "messages.yml"));

        for (String message : messagesConfig.minecraft.reload) {
            sender.sendMessage(
                    colorize(
                            message.replace("{prefix}", messagesConfig.minecraft.prefix)
                    )
            );
        }
    }

    private void handleLinkCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only a player would use this command");
            return;
        }

        CompletableFuture.runAsync(() -> {
            if (database.getDiscordIdByPlayerName(player.getName()) != null) {
                for (String message : messagesConfig.minecraft.alreadyLinked) {
                    player.sendMessage(colorize(message
                            .replace("{prefix}", messagesConfig.minecraft.prefix)));
                }

                return;
            }

            String code = generateCode(player);
            for (String message : messagesConfig.minecraft.link) {
                player.sendMessage(colorize(message
                        .replace("{code}", code)
                        .replace("{prefix}", messagesConfig.minecraft.prefix)));
            }
        });
    }

    private void sendUsageMessages(CommandSender sender) {
        for (String message : messagesConfig.minecraft.usage) {
            sender.sendMessage(colorize(message
                    .replace("{prefix}", messagesConfig.minecraft.prefix)));
        }
    }

    private String generateCode(Player player) {
        String chars = mainConfig.code.chars;
        int length = mainConfig.code.length;
        String code;

        do {
            code = generateRandomCode(chars, length);
        } while (codeMap.containsValue(code));

        codeMap.put(player.getName(), code);
        scheduleCodeExpiration(player);

        return code;
    }

    private String generateRandomCode(String chars, int length) {
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            codeBuilder.append(chars.charAt(index));
        }

        return codeBuilder.toString();
    }

    private void scheduleCodeExpiration(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                codeMap.remove(player.getName());
            }
        }.runTaskLaterAsynchronously(plugin, 5 * 60 * 20);
    }
}
