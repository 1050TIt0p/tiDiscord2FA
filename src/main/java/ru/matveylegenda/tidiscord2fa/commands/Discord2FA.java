package ru.matveylegenda.tidiscord2fa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.ColorParser;
import ru.matveylegenda.tidiscord2fa.utils.Config;

public class Discord2FA implements CommandExecutor {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Config config = plugin.config;

        if(args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if(sender.hasPermission("tidiscord2fa.reload")) {
                plugin.reloadConfig0();

                String reloadMessage = ColorParser.hex(config.messages.minecraft.reload);
                sender.sendMessage(reloadMessage);

                return true;
            } else {
                String noPermissionMessage = ColorParser.hex(config.messages.minecraft.noPermission);
                sender.sendMessage(noPermissionMessage);

                return true;
            }
        } else {
            if(sender.hasPermission("tidiscord2fa.use")) {
                for (String message : config.messages.minecraft.usage) {
                    sender.sendMessage(ColorParser.hex(message));
                }

                return true;
            } else {
                String noPermissionMessage = ColorParser.hex(config.messages.minecraft.noPermission);
                sender.sendMessage(noPermissionMessage);

                return true;
            }
        }
    }
}
