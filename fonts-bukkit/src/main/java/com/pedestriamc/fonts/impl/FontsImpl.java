package com.pedestriamc.fonts.impl;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.FontsAPI;
import com.pedestriamc.fonts.api.FontsUser;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class FontsImpl implements FontsAPI {

    private final FontLoader fontloader;
    private final UserUtil.UserMap userMap;

    public FontsImpl(Fonts fonts){
        fontloader = fonts.getFontLoader();
        userMap = fonts.getUserUtil().getUserMap();
    }

    @Override
    public Font getFont(String name) {
        return fontloader.getFont(name);
    }

    @Override
    public FontsUser getUser(Player player) {
        return userMap.getUser(player);
    }

    @Override
    @Nullable
    public FontsUser getUser(String name) {
        Player player = Bukkit.getPlayer(name);
        if(player == null){
            return null;
        }
        return userMap.getUser(player);
    }
}
