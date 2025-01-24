package ru.matveylegenda.tidiscord2fa.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class MessageTask {

    public MessageTask(TiDiscord2FA plugin) {
        BlockedList blockedList = plugin.blockedList;
        MessagesConfig messagesConfig = plugin.messagesConfig;

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().stream()
                        .filter(blockedList::contains)
                        .forEach(player -> messagesConfig.minecraft.join.forEach(message ->
                                player.sendMessage(colorize(message.replace("{prefix}", plugin.messagesConfig.minecraft.prefix)))
                        ));
            }
        }.runTaskTimerAsynchronously(plugin, 0,20);
    }
}
