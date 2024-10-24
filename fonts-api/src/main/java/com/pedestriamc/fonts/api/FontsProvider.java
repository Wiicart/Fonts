package com.pedestriamc.fonts.api;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@SuppressWarnings("unused")
public final class FontsProvider {

    private static FontsAPI fontsAPI;
    private static UUID uuid;

    private FontsProvider(){}

    /**
     * Provides the FontsAPI instance
     * @return A FontsAPI instance
     * @throws IllegalStateException If FontsAPI is uninitialized
     */
    public static FontsAPI get() throws IllegalStateException {

        if(fontsAPI != null){
            return fontsAPI;
        }

        throw new IllegalStateException("Fonts API not initialized.");

    }

    /**
     * For internal use only.
     * @param fontsAPI FontsAPI instance
     * @param fonts Fonts plugin
     * @param uuid UUID determined by plugin
     * @throws IllegalStateException Thrown when FontsAPI already registered
     * @throws SecurityException Thrown when a plugin other than Fonts attempts to register
     */
    public static void register(FontsAPI fontsAPI, JavaPlugin fonts, UUID uuid) throws IllegalStateException, SecurityException {

        if(FontsProvider.fontsAPI != null){
            throw new IllegalStateException("FontsAPI already initialized.");
        }

        if(!FontsProvider.class.getClassLoader().equals(fonts.getClass().getClassLoader())){
            throw new SecurityException("Unauthorized attempt to load Fonts API.");
        }

        FontsProvider.fontsAPI = fontsAPI;
        FontsProvider.uuid = uuid;

    }

    /**
     * For internal use only.
     * @param fonts Fonts plugin
     * @param uuid UUID used at registration
     * @throws SecurityException Thrown if call is determined to be unauthorized
     */
    public static void unregister(JavaPlugin fonts, UUID uuid) throws SecurityException {

        if(!FontsProvider.class.getClassLoader().equals(fonts.getClass().getClassLoader())){
            throw new SecurityException("Unauthorized attempt to unregister Fonts API");
        }

        if(FontsProvider.uuid != uuid){
            throw new SecurityException("Attempt to unregister Fonts API with invalid UUID.");
        }

        FontsProvider.fontsAPI = null;
        FontsProvider.uuid = null;

    }

}
