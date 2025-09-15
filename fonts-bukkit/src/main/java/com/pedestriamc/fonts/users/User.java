package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.api.FontsUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User implements FontsUser {

    private final UUID uuid;
    private final Player player;

    private Font activeFont;

    public User(@NotNull UUID uuid, @Nullable Font activeFont) {
        this.uuid = uuid;
        player = Objects.requireNonNull(Bukkit.getPlayer(uuid));
        this.activeFont = activeFont;
    }

    @NotNull
    public Map<String, String> getData() {
        HashMap<String, String> map = new HashMap<>(1);
        if (activeFont != null) {
            map.put("font", activeFont.getName());
        }
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    @Nullable
    public Font getFont() {
        return activeFont;
    }

    @NotNull
    public Font getFontOrDefault(@NotNull Font defaultFont) {
        return activeFont != null ? activeFont : defaultFont;
    }

    public void setFont(@Nullable Font activeFont) {
        this.activeFont = activeFont;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName(){
        return player.getName();
    }

}
