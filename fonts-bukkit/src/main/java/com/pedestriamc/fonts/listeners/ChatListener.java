package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener {

    private final UserUtil userUtil;
    private final Font globalDefault;

    public ChatListener(@NotNull Fonts fonts) {
        userUtil = fonts.getUserUtil();
        globalDefault = fonts.getFontLoader().globalDefault();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(@NotNull AsyncPlayerChatEvent event) {
        event.setMessage(userUtil
                .getUser(event.getPlayer().getUniqueId())
                .getFontOrDefault(globalDefault)
                .translate(event.getMessage())
        );
    }
}
