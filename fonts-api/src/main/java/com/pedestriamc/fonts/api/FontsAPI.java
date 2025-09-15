package com.pedestriamc.fonts.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface FontsAPI {

    /**
     * Provides a {@link Font} by name
     * @param name The Font
     * @return The Font or null
     */
    @Nullable Font getFont(@NotNull String name);

    /**
     * Provides a FontsUser
     * @param player The User
     * @return A FontsUser
     */
    @NotNull FontsUser getUser(@NotNull Player player);

    /**
     * Provides a Font that has no effect
     * @return The default font
     */
    @NotNull Font defaultFont();

    /**
     * Provides the Font that Users will use by default
     * @return The Font.
     */
    @NotNull Font globalDefaultFont();
}
