package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class LeaveListener implements Listener {

    private final UserUtil util;

    public LeaveListener(@NotNull Fonts fonts) {
        util = fonts.getUserUtil();
    }

    @EventHandler
    public void onEvent(@NotNull PlayerQuitEvent event) {
        util.removeUser(event.getPlayer().getUniqueId());
    }
}
