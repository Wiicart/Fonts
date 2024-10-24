package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    private final UserUtil util;

    public LeaveListener(Fonts fonts) {
        util = fonts.getUserUtil();
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        util.getUserMap().removeUser(event.getPlayer());
    }
}
