package com.pedestriamc.fonts.listeners;

import com.pedestriamc.fonts.Fonts;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener {

    private final Fonts fonts;

    public ChatListener(@NotNull Fonts fonts) {
        this.fonts = fonts;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(@NotNull AsyncPlayerChatEvent event) {
        event.setMessage(
                fonts
                .getUser(event.getPlayer())
                .getFont()
                .translate(event.getMessage())
        );
    }
}
