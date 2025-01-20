package ru.matveylegenda.tidiscord2fa.utils;

import java.util.HashMap;
import java.util.Map;

public class CodeMap {
    public static CodeMap instance = new CodeMap();

    private Map<String, String> map = new HashMap<>();

    public void put(String playerName, String code) {
        map.put(playerName, code);
    }

    public void remove(String playerName) {
        map.remove(playerName);
    }

    public boolean containsKey(String playerName) {
        return map.containsKey(playerName);
    }

    public boolean containsValue(String code) {
        return map.containsValue(code);
    }

    public String getKey(String code) {
        for (String key : map.keySet()) {
            if (map.get(key).equals(code)) {
                return key;
            }
        }

        return null;
    }
}
