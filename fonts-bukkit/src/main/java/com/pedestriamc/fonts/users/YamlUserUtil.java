package com.pedestriamc.fonts.users;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.text.FontLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class YamlUserUtil implements UserUtil {

    private final Fonts fonts;
    private final Map<UUID, User> users = new HashMap<>();
    private final FileConfiguration config;
    private final FontLoader fontLoader;

    public YamlUserUtil(@NotNull Fonts fonts) {
        this.fonts = fonts;
        config = fonts.getUsersFileConfig();
        fontLoader = fonts.getFontLoader();
    }

    /**
     * Saves a User to users.yml.
     * @param user The user to be saved.
     */
    @Override
    public void saveUser(@NotNull User user) {
        Map<String, String> map = user.getData();
        synchronized(config) {
            fonts.async(() -> {
                config.set("users." + user.getUuid(), null);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    config.set("users." + user.getUuid() + "." + entry.getKey(), entry.getValue());
                }
                fonts.saveUsersFile();
            });
        }
    }

    @Override
    @NotNull
    public User loadUser(@NotNull UUID uuid) {
        String fontName = config.getString("users." + uuid + ".font");
        Font font = null;
        if (fontName != null) {
            font = fontLoader.getFont(fontName);
        }

        User user = new User(uuid, font);
        users.put(uuid, user);

        return user;
    }

    @Override
    public @NotNull CompletableFuture<User> loadUserAsync(@NotNull UUID uuid) {
        CompletableFuture<User> future = new CompletableFuture<>();
        fonts.async(() -> {
            try {
                User user = loadUser(uuid);
                future.complete(user);
            } catch(Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @Override
    public @NotNull User getUser(@NotNull UUID uuid) {
        User user = users.get(uuid);
        return user != null ? user : loadUser(uuid);
    }

    @Override
    public void removeUser(@NotNull UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public boolean isEmpty() {
        return users.isEmpty();
    }

}
