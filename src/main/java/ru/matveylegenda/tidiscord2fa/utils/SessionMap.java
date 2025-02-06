package ru.matveylegenda.tidiscord2fa.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SessionMap {
    private final Map<String, SessionData> session = new HashMap<>();

    public void addSession(Player player, String ip) {
        session.put(
                player.getName(),
                new SessionData(ip, System.currentTimeMillis())
        );
    }

    public boolean contains(Player player) {
        return session.containsKey(player.getName());
    }

    public String getIp(Player player) {
        return session.get(player.getName()).ip;
    }

    public long getLastJoinTime(Player player) {
        return session.get(player.getName()).lastJoin;
    }

    public static class SessionData {
        public String ip;
        public long lastJoin;

        public SessionData(String ip, long lastJoin) {
            this.ip = ip;
            this.lastJoin = lastJoin;
        }

        public SessionData() {

        }
    }
}