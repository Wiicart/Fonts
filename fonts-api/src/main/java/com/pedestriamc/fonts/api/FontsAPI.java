package com.pedestriamc.fonts.api;

import org.bukkit.entity.Player;

public interface FontsAPI {
    Font getFont(String name);
    FontsUser getUser(Player player);
    FontsUser getUser(String name);
}
