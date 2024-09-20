package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.text.Font;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private final Player player;
    private Font activeFont;

    public User(Player player){
        this(player, null);
    }

    public User(Player player, Font activeFont){
        this.player = player;
        this.uuid = player.getUniqueId();
        this.activeFont = activeFont;
    }

    public Map<String, String> getDataMap(){
        HashMap<String, String> map = new HashMap<>(1);
        map.put("active-font", activeFont.toString());
        return map;
    }

    public Player getPlayer(){ return player; }

    public Font getActiveFont(){ return activeFont; }

    public void setActiveFont(Font activeFont){
        this.activeFont = activeFont;
    }

    public UUID getUuid(){ return uuid; }

}
