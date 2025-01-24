package ru.matveylegenda.tidiscord2fa.utils;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BlockedList {
    private final Set<String> list = new HashSet<>();

    public boolean contains(Player player) {
        if (list.isEmpty()) {
            return false;
        }

        return list.contains(player.getName());
    }

    public void add(Player player) {
        list.add(player.getName());
    }

    public void remove(Player player) {
        list.remove(player.getName());
    }
}
