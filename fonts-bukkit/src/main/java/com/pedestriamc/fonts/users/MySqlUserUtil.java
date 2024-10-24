package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.text.FontLoader;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class MySqlUserUtil implements UserUtil{

    private final UserMap userMap;
    private final FontLoader fontLoader;
    private final DefaultFont defaultFont;

    public MySqlUserUtil(@NotNull Fonts fonts) throws Exception{
        userMap = new UserMap();
        fontLoader = fonts.getFontLoader();
        defaultFont = fonts.getDefaultFont();

    }

    @Override
    public void saveUser(@NotNull User user) {

    }

    @Override
    public User loadUser(@NotNull Player player) {
        return null;
    }

    @Override
    public UserMap getUserMap() {
        return userMap;
    }
}
