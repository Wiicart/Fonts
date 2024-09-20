package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final UserUtil util;

    public JoinListener(Fonts fonts){
        util = fonts.getUserUtil();
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent event){
        User user = util.loadUser(event.getPlayer());
        util.addUser(user);
        if(!(user.getActiveFont() instanceof DefaultFont)){
            util.saveUser(user);
        }
    }
}
