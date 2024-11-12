package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.api.FontsUser;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User implements FontsUser {

    private final String name;
    private final UUID uuid;
    private final Player player;
    private Font activeFont;
    private final UserUtil userUtil;

    public User(Player player, Font activeFont, UserUtil userUtil) {
        this.name = player.getName();
        this.player = player;
        this.uuid = player.getUniqueId();
        this.activeFont = activeFont;
        this.userUtil = userUtil;
    }

    public Map<String, String> getDataMap() {
        HashMap<String, String> map = new HashMap<>(1);
        map.put("font", activeFont.toString());
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public Font getFont() {
        return activeFont;
    }

    public void setFont(Font activeFont) {
        this.activeFont = activeFont;
        userUtil.saveUser(this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName(){
        return name;
    }

}
