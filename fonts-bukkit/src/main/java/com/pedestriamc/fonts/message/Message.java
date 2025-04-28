package com.pedestriamc.fonts.message;

import com.pedestriamc.common.message.CommonMessage;

public enum Message implements CommonMessage {

    NO_PERMS("no-perms"),
    TOO_MANY_ARGS("too-many-args"),
    PLAYER_ONLY("player-only"),
    FONT_NOT_FOUND("font-not-found"),
    PLAYER_NOT_FOUND("player-not-found"),
    NO_PERMS_FONT("no-perms-font"),
    FONT_CHANGED("font-changed"),
    FONT_CHANGED_OTHER("font-changed-other"),
    RELOADED("reloaded"),
    VERSION("version"),;

    private final String key;

    Message(String key) {
        this.key = key;
    }
    @Override
    public String getKey() {
        return key;
    }
}
