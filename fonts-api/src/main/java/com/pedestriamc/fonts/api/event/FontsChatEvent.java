package com.pedestriamc.fonts.api.event;

import com.pedestriamc.fonts.api.Font;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class FontsChatEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final AsyncPlayerChatEvent event;
    private final Font font;

    public FontsChatEvent(AsyncPlayerChatEvent event, Font font) {
        this.event = event;
        this.font = font;
    }

    public AsyncPlayerChatEvent getEvent() {
        return event;
    }

    public Font getFont() {
        return font;
    }


    public static HandlerList getHandlerList(){ return handlerList; }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
