package ru.matveylegenda.tidiscord2fa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ru.matveylegenda.tidiscord2fa.utils.BlockedList;

public class BlockAttack implements Listener {
    private BlockedList blockedUtil = new BlockedList();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if(blockedUtil.isBlocked(player)) {
                event.setCancelled(true);
            }
        }
    }
}
