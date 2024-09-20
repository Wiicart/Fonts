package com.pedestriamc.fonts;

import com.pedestriamc.fonts.listeners.ChatListener;
import com.pedestriamc.fonts.listeners.JoinListener;
import com.pedestriamc.fonts.listeners.LeaveListener;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.text.Font;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Fonts extends JavaPlugin {

    private Font defaultFont;
    private FontLoader fontLoader;
    private UserUtil userUtil;

    @Override
    public void onEnable() {
        createObjects();
        registerListeners();

    }

    @Override
    public void onDisable() {

    }

    private void createObjects(){
        defaultFont = new DefaultFont();
        fontLoader = new FontLoader(this);
        userUtil = new UserUtil(this);
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this), this);

    }

    public FileConfiguration getFontsFile(){ return null; }
    public FileConfiguration getUsersFile(){ return null; }
    public Font getDefaultFont(){ return defaultFont; }
    public FontLoader getFontLoader(){ return fontLoader; }
    public UserUtil getUserUtil(){ return userUtil; }
}
