package ru.matveylegenda.tidiscord2fa.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BlockedList {
    private static ArrayList<Player> blocked = new ArrayList<>();

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
