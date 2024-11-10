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


        //player.setResourcePack("https://github.com/Wiicart/wiicart.net/raw/refs/heads/main/fonts/test/gon.zip");

    }
}
