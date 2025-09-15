package com.pedestriamc.fonts.api;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface Font {

    /**
     * Provides the Font name
     * @return The Font name
     */
    @NotNull String getName();

    /**
     * Translates a String into the Font
     * @param message The message to be translated
     * @return The translated message
     */
    @NotNull String translate(@NotNull String message);

    /**
     * Reverts a translated message to its original value
     * @param message The translated message
     * @return The original message
     */
    @NotNull String revert(@NotNull String message);

    /**
     * Tells if the User is allowed to use the Font
     * @param sender The CommandSender
     * @return The font
     */
    boolean allows(@NotNull CommandSender sender);

}
