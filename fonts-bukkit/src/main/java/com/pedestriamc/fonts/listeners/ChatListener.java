package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final UserUtil.UserMap userMap;

    public ChatListener(Fonts fonts) {
        userMap = fonts.getUserUtil().getUserMap();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(AsyncPlayerChatEvent event) {
        event.setMessage(userMap
                .getUser(event.getPlayer())
                .getFont()
                .translate(event.getMessage()));
    }
}
