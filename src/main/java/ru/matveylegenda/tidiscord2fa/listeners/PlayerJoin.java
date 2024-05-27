package ru.matveylegenda.tidiscord2fa.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.ColorParser;
import ru.matveylegenda.tidiscord2fa.utils.Config;

public class PlayerJoin implements Listener {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();
    private BlockedList blockedUtil = new BlockedList();



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Config config = plugin.config;
        JDA jda = TiDiscord2FA.getInstance().jda;
        Player player = event.getPlayer();
        String discordID = config.users.get(player.getName());

        if (discordID != null) {
            blockedUtil.add(player);

            for (String message : config.messages.minecraft.join) {
                player.sendMessage(ColorParser.hex(message));
            }

            String buttonEmoji = config.messages.discord.buttonEmoji;
            String buttonText = config.messages.discord.buttonText;
            Button button = Button.danger("2fa-allow-join", buttonText)
                    .withStyle(ButtonStyle.SECONDARY)
                    .withEmoji(Emoji.fromUnicode(buttonEmoji));

            String joinDiscordMessage = config.messages.discord.join;
            jda.openPrivateChannelById(discordID).queue(channel ->
                    channel.sendMessage(joinDiscordMessage)
                            .setComponents(
                                    ActionRow.of(button)
                            )
                            .queue()
            );

            int time = config.settings.time;
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(!player.isOnline()) {
                        return;
                    }

                    if(blockedUtil.isBlocked(player)) {
                        String kickMessage = ColorParser.hex(config.messages.minecraft.kick);
                        player.kickPlayer(kickMessage);
                    }
                }
            }.runTaskLater(plugin, time * 20L);
        }
    }
}
