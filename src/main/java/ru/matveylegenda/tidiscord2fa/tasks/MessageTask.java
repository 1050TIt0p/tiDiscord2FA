package ru.matveylegenda.tidiscord2fa.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.configs.MessagesConfig;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

import static ru.matveylegenda.tidiscord2fa.utils.ColorParser.colorize;

public class MessageTask {

    public MessageTask(TiDiscord2FA plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (BlockedList.instance.contains(player)) {
                        for (String message : MessagesConfig.instance.minecraft.join) {
                            player.sendMessage(
                                    colorize(
                                            message.replace("{prefix}", MessagesConfig.instance.minecraft.prefix)
                                    )
                            );
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0,20);
    }
}
