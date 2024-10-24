package com.pedestriamc.fonts.api;

public interface IFont {

    /**
     * Provides the Font name
     * @return The Font name
     */
    String getName();

    /**
     * Translates a String into the Font
     * @param message The message to be translated
     * @return The translated message
     */
    String translate(String message);

    /**
     * Reverts a translated message back to its original value
     * @param message The translated message
     * @return The original message
     */
    String revert(String message);

}
