package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.text.FontLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class YamlUserUtil implements UserUtil{

    private final UserMap userMap;
    private final FileConfiguration config;
    private final FontLoader fontLoader;
    private final DefaultFont defaultFont;

    public YamlUserUtil(@NotNull Fonts fonts) {
        userMap = new UserMap();
        config = fonts.getUsersFile();
        fontLoader = fonts.getFontLoader();
        defaultFont = fontLoader.getDefaultFont();
    }

    /**
     * Saves a User to users.yml.
     * @param user The user to be saved.
     */
    @Override
    public void saveUser(@NotNull User user) {
        Map<String, String> map = user.getDataMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            config.set("users." + user.getUuid() + entry.getKey(), entry.getValue());
        }
    }

    /**
     * Loads a User from the users.yml file.
     * Returns a new User with font DefaultFont if user not found.
     *
     * @param player The player of the User to load.
     * @return A User
     */
    @Override
    public User loadUser(@NotNull Player player) {
        String fontName = config.getString("users." + player.getUniqueId() + "active-font");

        if (fontName == null) {
            return new User(player, defaultFont);
        }

        return new User(player, fontLoader.getFont(fontName));
    }

    @Override
    public UserMap getUserMap() {
        return userMap;
    }

}
