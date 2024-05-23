package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

public class BlockDamage implements Listener {
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(blockedUtil.isBlocked(player)) {
                event.setCancelled(true);
            }
        }
    }
}
