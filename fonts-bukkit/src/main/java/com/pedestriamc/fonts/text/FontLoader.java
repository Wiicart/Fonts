package com.pedestriamc.fonts.text;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class FontLoader {

    private final Fonts fonts;
    private final Font defaultFont;
    private final HashMap<String, Font> fontMap;
    private final File fontsFolder;

    public FontLoader(Fonts fonts) {

        this.fonts = fonts;
        defaultFont = new DefaultFont();
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

    }

    /**
     * Saves the fonts the plugin comes bundled with.
     */
    private void saveDefaultFiles() {
        saveFile("italic.yml");
        saveFile("mono.yml");
        saveFile("circled.yml");
        saveFile("smallcaps.yml");
        saveFile("cyrillic.yml");
        saveFile("cyrillic_bu.yml");
        saveFile("cyrillic_by.yml");
        saveFile("cyrillic_ru.yml");
        saveFile("cyrillic_ua.yml");
        saveFile("README.txt");
    }

    /**
     * Saves a File in the fonts/fonts directory
     * @param file The File to be saved
     */
    private void saveFile(String file) {
        try {
            fonts.saveResource("fonts/" + file, false);
        } catch (Exception e) {
            fonts.getLogger().info("Failed to save default font '" + file + "'");
        }
    }

    /**
     * Provides a Font, if it exists. If the font exists, but has not been loaded, this will load the
     * font.
     *
     * @param name The name of the font to get
     * @return The font if it exists, otherwise the default font.
     */
    public Font getFont(String name) {

        if (!(fontMap.containsKey("name"))) {
            return loadFont(name);
        }

        Font font = fontMap.get(name);
        return font != null ? font : defaultFont;

    }

    /**
     * Loads and creates a Font object from the "fonts" folder, and adds it to the FontLoader HashMap.
     *
     * @param name The name of the font to search for.
     * @return The specified font, if it exists, otherwise returns the default font.
     */
    private Font loadFont(String name) {

        if (name.equals("default")) {
            return defaultFont;
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
                BidiMap<Character, String> map = new DualHashBidiMap<>();
                for (String str : section.getKeys(false)) {
                    if (str.length() == 1) {
                        map.put(str.charAt(0), section.getString(str));
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
        return defaultFont;
    }

    public DefaultFont getDefaultFont(){
        return (DefaultFont) defaultFont;
    }

}
