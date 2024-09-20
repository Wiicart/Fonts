package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.text.FontLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserUtil {

    private final Map<UUID, User> userMap;
    private final FileConfiguration config;
    private final FontLoader fontLoader;

    public UserUtil(@NotNull Fonts fonts){
        userMap = new HashMap<>();
        config = fonts.getUsersFile();
        fontLoader = fonts.getFontLoader();
    }

    /**
     * Saves a User to users.yml.
     * @param user The user to be saved.
     */
    public void saveUser(@NotNull User user){
        Map<String, String> map = user.getDataMap();
        for(Map.Entry<String, String> entry : map.entrySet()){
            config.set("users." + user.getUuid() + entry.getKey(), entry.getValue());
        }
    }

    /**
     * Loads a User from the users.yml file.
     * @param player The player of the User to load.
     * @return A User
     */
    public User loadUser(@NotNull Player player){
        String fontName = config.getString("users." + player.getUniqueId() + "active-font");
        if(fontName == null){
            fontName = "default";
        }
        return new User(player, fontLoader.getFont(fontName));
    }

    /**
     * Adds a User to the User HashMap.
     * @param user The User to be added.
     */
    public void addUser(User user){
        userMap.put(user.getUuid(), user);
    }

    /**
     * Removes a User from the User HashMap.
     * @param uuid The UUID of the User to remove.
     */
    public void removeUser(UUID uuid){
        userMap.remove(uuid);
    }

    /**
     * Gets a User from the User HashMap.
     * @param uuid The UUID of the User.
     * @return Returns a User if it exists, otherwise null.
     */
    public User getUser(UUID uuid){
        return userMap.get(uuid);
    }

}
