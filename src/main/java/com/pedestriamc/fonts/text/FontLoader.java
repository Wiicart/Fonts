package com.pedestriamc.fonts.text;

import com.pedestriamc.fonts.Fonts;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FontLoader {

    private final Fonts fonts;
    private final HashMap<String, Font> fontMap;

    public FontLoader(Fonts fonts){
        this.fonts = fonts;
        fontMap = new HashMap<>();
        loadFonts();
    }

    private void loadFonts(){
        FileConfiguration fontsFile = fonts.getFontsFile();


    }

    public Font getFont(String name){
        return fontMap.get(name);
    }

}
