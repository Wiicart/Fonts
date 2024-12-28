package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final UserUtil util;

    public JoinListener(Fonts fonts) {
        util = fonts.getUserUtil();
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = util.loadUser(player);
        util.getUserMap().addUser(user);

        if (!(user.getFont() instanceof DefaultFont)) {
            util.saveUser(user);
        }
        // player.setResourcePack("https://cdn.modrinth.com/data/L0iIXINl/versions/5cd6k8ol/Serified%20Font%20v1.1%20f1-34.zip");
    }
}
