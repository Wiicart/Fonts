package com.pedestriamc.fonts.api;

import org.jetbrains.annotations.NotNull;

public class FontsRegistrar {

    private FontsRegistrar() {}

    public static void register(@NotNull FontsAPI fonts) {
        FontsProvider.register(fonts);
    }

    public static void unregister() {
        FontsProvider.unregister();
    }

}
