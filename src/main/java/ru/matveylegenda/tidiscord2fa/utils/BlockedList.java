package ru.matveylegenda.tidiscord2fa.utils;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BlockedList {
    private static Set<Player> blocked = new HashSet<>();

    public boolean isEmpty() {
        return blocked.isEmpty();
    }

    public boolean isBlocked(Player player) {
        return blocked.contains(player);
    }

    public void add(Player player) {
        blocked.add(player);
    }

    public void remove(Player player) {
        blocked.remove(player);
    }
}
