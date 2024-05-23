package ru.matveylegenda.tidiscord2fa.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.ColorParser;

public class PlayerJoin implements Listener {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig();
        JDA jda = TiDiscord2FA.getInstance().jda;
        Player player = event.getPlayer();
        String discordID = config.getString("users." + player.getName());

        if (discordID != null) {
            blockedUtil.add(player);

            for (String message : config.getStringList("messages.minecraft.join")) {
                player.sendMessage(ColorParser.hex(message));
            }

            String buttonEmoji = plugin.getConfig().getString("messages.discord.buttonEmoji");
            String buttonText = plugin.getConfig().getString("messages.discord.buttonText");
            Button button = Button.danger("2fa-allow-join", buttonText)
                    .withStyle(ButtonStyle.SECONDARY)
                    .withEmoji(Emoji.fromUnicode(buttonEmoji));

            String joinDiscordMessage = plugin.getConfig().getString("messages.discord.join");
            jda.openPrivateChannelById(discordID).queue(channel ->
                    channel.sendMessage(joinDiscordMessage)
                            .setComponents(
                                    ActionRow.of(button)
                            )
                            .queue()
            );

            int time = plugin.getConfig().getInt("settings.time");
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(!player.isOnline()) {
                        return;
                    }

                    if(blockedUtil.isBlocked(player)) {
                        String kickMessage = ColorParser.hex(plugin.getConfig().getString("messages.minecraft.kick"));
                        player.kickPlayer(kickMessage);
                    }
                }
            }.runTaskLater(plugin, time * 20L);
        }
    }
}
