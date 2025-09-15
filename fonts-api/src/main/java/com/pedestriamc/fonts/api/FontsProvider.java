package com.pedestriamc.fonts.api;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.UUID;

@SuppressWarnings("unused")
public final class FontsProvider {

    private static FontsAPI fonts;
    private static UUID uuid;

    private FontsProvider(){}

    /**
     * Provides the FontsAPI instance
     * @return A FontsAPI instance
     * @throws IllegalStateException If FontsAPI is uninitialized
     */
    public static FontsAPI get() throws IllegalStateException {
        if(fonts != null){
            return fonts;
        }

        throw new IllegalStateException("Fonts API not initialized.");

    }

    @Internal
    public static void register(FontsAPI fonts) {
        FontsProvider.fonts = fonts;
    }

    @Internal
    static void unregister() {
        FontsProvider.fonts = null;
    }

}
