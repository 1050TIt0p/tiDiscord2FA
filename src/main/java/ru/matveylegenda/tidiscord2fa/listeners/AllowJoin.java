package ru.matveylegenda.tidiscord2fa.listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.matveylegenda.tidiscord2fa.TiDiscord2FA;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;
import ru.matveylegenda.tidiscord2fa.utils.ColorParser;

public class AllowJoin extends ListenerAdapter {
    private TiDiscord2FA plugin = TiDiscord2FA.getInstance();
    private BlockedList blockedUtil = new BlockedList();

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        FileConfiguration config = TiDiscord2FA.getInstance().getConfig();
        String discordID = event.getUser().getId();

        if(!event.isFromGuild()) {
            if(event.getComponentId().equals("2fa-allow-join")) {
                for (String nick : config.getConfigurationSection("users").getKeys(false)) {
                    if (config.getString("users." + nick).equals(discordID)) {
                        Player player = Bukkit.getPlayer(nick);

                        if(player != null) {
                            if(blockedUtil.isBlocked(player)) {
                                blockedUtil.remove(player);

                                for (String message : config.getStringList("messages.minecraft.allowJoin")) {
                                    player.sendMessage(ColorParser.hex(message));
                                }

                                String allowJoinDiscordMessage = config.getString("messages.discord.allowJoin");
                                event.reply(allowJoinDiscordMessage)
                                        .queue();
                            } else {
                                String verifyNotRequiredMessage = config.getString("messages.discord.verifyNotRequired");
                                event.reply(verifyNotRequiredMessage)
                                        .queue();
                            }
                        } else {
                            String playerNotFoundMessage = config.getString("messages.discord.playerNotFound");
                            event.reply(playerNotFoundMessage)
                                    .queue();
                        }
                    }
                }
            }
        }
    }
}
