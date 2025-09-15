package com.pedestriamc.fonts.text;

import com.pedestriamc.common.util.UnrestrictedBidiMap;
import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import org.apache.commons.collections4.BidiMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class FontLoader {

    private static final Font DEFAULT_FONT = new DefaultFont();

    private final Fonts fonts;
    private final HashMap<String, Font> fontMap;
    private final File fontsFolder;

    private final Font globalDefault;

    // Provides the default font, meaning what it takes in will be put out
    @NotNull
    @SuppressWarnings("unused")
    public static Font defaultFont() {
        return DEFAULT_FONT;
    }

    // Checks if it's a default like that provided from defaultFont().
    public static boolean isDefault(@Nullable Font font) {
        if (font == null) {
            return false;
        }
        return font instanceof DefaultFont;
    }

    public FontLoader(@NotNull Fonts fonts) {

        this.fonts = fonts;
        fontMap = new HashMap<>();

        fontsFolder = new File(fonts.getDataFolder(), "fonts");
        if (!fontsFolder.exists()) {
            boolean folderMade = fontsFolder.mkdirs();
            if (!folderMade) {
                fonts.getLogger().info("Failed to load fonts folder, disabling.");
                fonts.getServer().getPluginManager().disablePlugin(fonts);
            }
            saveDefaultFiles();
            fonts.getLogger().info("Fonts folder loaded.");
        }
        loadFonts(fontsFolder);

        globalDefault = loadGlobalDefault();
    }


    /**
     * Saves the fonts the plugin comes bundled with.
     */
    private void saveDefaultFiles() {
        saveFile("README.txt");
        saveFile("mono.yml");
        saveFile("circled.yml");
        saveFile("smallcaps.yml");
        saveFile("cyrillic.yml");
        saveFile("fraktur.yml");
        saveFile("script.yml");
        saveFile("script_bold.yml");
    }

    /**
     * Saves a File in the fonts/fonts directory
     * @param file The File to be saved
     */
    private void saveFile(String file) {
        try {
            fonts.saveResource("fonts/" + file, false);
        } catch (Exception e) {
            fonts.getLogger().info("Failed to save file '" + file + "'");
        }
    }

    /**
     * Provides a Font if it exists.
     * If the font exists but has not been loaded, this will load the font.
     *
     * @param name The name of the font to get
     * @return The font if it exists, otherwise the default font.
     */
    @Nullable
    public Font getFont(@NotNull String name) {
        if (!(fontMap.containsKey("name"))) {
            return loadFont(name);
        }

        return fontMap.get(name);
    }

    public Font getFontOrDefault(@NotNull String name) {
        Font font = getFont(name);
        return font != null ? font : DEFAULT_FONT;
    }

    @NotNull
    public Font globalDefault() {
        return globalDefault;
    }

    public Set<Font> getLoadedFonts() {
        return new HashSet<>(fontMap.values());
    }

    /**
     * Loads and creates a Font object from the "fonts" folder, and adds it to the FontLoader HashMap.
     *
     * @param name The name of the font to search for.
     * @return The specified font, if it exists, otherwise returns the default font.
     */
    private Font loadFont(@NotNull String name) {

        if (name.equalsIgnoreCase("default")) {
            return DEFAULT_FONT;
        }

        File file = new File(fontsFolder, name + ".yml");

        if (file.exists() && !file.isDirectory()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                ConfigurationSection section = config.getConfigurationSection("font");
                if (section == null) {
                    fonts.getLogger().info("Unable to load font '" + name + "', improper formatting.");
                    return null;
                }
                BidiMap<Character, String> map = new UnrestrictedBidiMap<>();
                for (String str : section.getKeys(false)) {
                    if (str.length() == 1) {
                        map.put(str.charAt(0), section.getString(str));
                    } else {
                        fonts.getLogger().info("Keys for fonts must be one character.");
                    }
                }
                Font font = new UnicodeFont(name, map);
                fontMap.put(name, font);
                fonts.getLogger().info("Loaded font '" + name + "'.");
                return font;
            } catch (Exception e) {
                fonts.getLogger().info("Failed to load font '" + name + "'");
            }
        }
        fonts.getLogger().info("Failed to load font '" + name + "', file not found.");
        return DEFAULT_FONT;
    }

    private Font loadGlobalDefault() {
        FileConfiguration config = fonts.getConfig();
        String defaultName = config.getString("default");
        if (defaultName == null) {
            return DEFAULT_FONT;
        }

        Font font = loadFont(defaultName);
        return font != null ? font : DEFAULT_FONT;
    }

    private void loadFonts(@NotNull File file) {
        String names[] = file.list();
        if (names == null) {
            return;
        }

        for (String name : names) {
            if (name.endsWith(".yml")) {
                loadFont(name.substring(0, name.length() - 4));
            }
        }
    }

}
