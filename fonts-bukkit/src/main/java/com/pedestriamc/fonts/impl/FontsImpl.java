package com.pedestriamc.fonts.impl;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.FontsAPI;
import com.pedestriamc.fonts.api.FontsUser;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FontsImpl implements FontsAPI {

    private final FontLoader fontloader;
    private final UserUtil userUtil;

    public FontsImpl(@NotNull Fonts fonts){
        fontloader = fonts.getFontLoader();
        userUtil = fonts.getUserUtil();
    }

    @Override
    @Nullable
    public Font getFont(@NotNull String name) {
        return fontloader.getFont(name);
    }

    @Override
    public @NotNull FontsUser getUser(@NotNull Player player) {
        return userUtil.getUser(player.getUniqueId());
    }

    @Override
    public @NotNull Font defaultFont() {
        return FontLoader.defaultFont();
    }

    @Override
    public @NotNull Font globalDefaultFont() {
        return fontloader.globalDefault();
    }

}
