package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener {

    private final UserUtil.UserMap userMap;

    public ChatListener(@NotNull Fonts fonts) {
        userMap = fonts.getUserUtil().getUserMap();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(@NotNull AsyncPlayerChatEvent event) {
        event.setMessage(
                userMap
                .getUser(event.getPlayer())
                .getFont()
                .translate(event.getMessage())
        );
    }
}
