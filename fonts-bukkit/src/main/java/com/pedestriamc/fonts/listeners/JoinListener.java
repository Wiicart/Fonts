package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class JoinListener implements Listener {

    private final UserUtil util;

    public JoinListener(@NotNull Fonts fonts) {
        util = fonts.getUserUtil();
    }

    @EventHandler
    public void onEvent(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        util.loadUserAsync(player.getUniqueId());

        // player.setResourcePack("https://cdn.modrinth.com/data/L0iIXINl/versions/5cd6k8ol/Serified%20Font%20v1.1%20f1-34.zip");
    }
}
