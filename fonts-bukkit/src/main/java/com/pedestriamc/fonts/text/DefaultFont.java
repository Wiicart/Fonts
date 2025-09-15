package com.pedestriamc.fonts.text;

import org.jetbrains.annotations.NotNull;

class DefaultFont extends UnicodeFont {

    public DefaultFont() {
        super("default", null);
    }

    @Override
    public @NotNull String translate(@NotNull String str) {
        return str;
    }

    @Override
    public @NotNull String revert(@NotNull String str) {
        return str;
    }

}
