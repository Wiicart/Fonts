package com.pedestriamc.fonts;

import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.api.FontsProvider;
import com.pedestriamc.fonts.commands.FontCommand;
import com.pedestriamc.fonts.impl.FontsImpl;
import com.pedestriamc.fonts.listeners.ChatListener;
import com.pedestriamc.fonts.listeners.JoinListener;
import com.pedestriamc.fonts.listeners.LeaveListener;
import com.pedestriamc.fonts.message.Messenger;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.MySqlUserUtil;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import com.pedestriamc.fonts.users.YamlUserUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public final class Fonts extends JavaPlugin {

    @SuppressWarnings("FieldCanBeLocal")
    private final String distributor = "modrinth";
    @SuppressWarnings("FieldCanBeLocal")
    private final String version = "1.0";
    @SuppressWarnings("FieldCanBeLocal")
    private final short versionNum = 1;

    private Font defaultFont;
    private FontLoader fontLoader;
    private UserUtil userUtil;
    private FileConfiguration usersConfig;
    private FileConfiguration messagesConfig;
    private Messenger messenger;
    private UUID apiKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupFiles();
        createObjects();
        registerClasses();
        loadUsers();
        loadApi();
        getLogger().info("Fonts version " + getVersion() + " loaded.");
    }

    @Override
    public void onDisable() {
        userUtil.getUserMap().clear();
        this.defaultFont = null;
        this.fontLoader = null;
        this.userUtil = null;
        this.usersConfig = null;
        this.messagesConfig = null;
        this.messenger = null;
        HandlerList.unregisterAll(this);
        try{
            FontsProvider.unregister(this, apiKey);
        } catch (SecurityException ignored) {
        }
        getLogger().info("Fonts disabled.");
    }

    public void reload(){
        onDisable();
        onEnable();
    }

    public void loadUsers(){
        if(userUtil.getUserMap().isEmpty() && !Bukkit.getOnlinePlayers().isEmpty()){
            for(Player p : Bukkit.getOnlinePlayers()){
                User user = userUtil.loadUser(p);
                userUtil.getUserMap().addUser(user);
            }
        }
    }

    public void loadApi(){
        FontsImpl fontsImpl = new FontsImpl(this);
        apiKey = UUID.randomUUID();
        FontsProvider.register(fontsImpl, this, apiKey);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setupFiles() {
        File usersFile = new File(getDataFolder(), "users.yml");
        if (!usersFile.exists()) {
            usersFile.getParentFile().mkdirs();
            saveResource("users.yml", false);
        }
        usersConfig = YamlConfiguration.loadConfiguration(usersFile);

        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private void createObjects() {
        defaultFont = new DefaultFont();
        fontLoader = new FontLoader(this);
        messenger = new Messenger(this);
        if(!getConfig().getBoolean("mysql")) {
            userUtil = new YamlUserUtil(this);
        }else{
            try{
                userUtil = new MySqlUserUtil(this);
                getLogger().info("Connected to database.");
            }catch(Exception e){
                getLogger().info("Failed to connect to database, defaulting to yaml storage.");
                userUtil = new YamlUserUtil(this);
            }

        }
    }

    @SuppressWarnings("ConstantConditions")
    private void registerClasses() {
        getCommand("font").setExecutor(new FontCommand(this));
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this), this);
    }

    public FileConfiguration getUsersFile() {
        return usersConfig;
    }

    public DefaultFont getDefaultFont() {
        return (DefaultFont) defaultFont;
    }

    public FontLoader getFontLoader() {
        return fontLoader;
    }

    public UserUtil getUserUtil() {
        return userUtil;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    @SuppressWarnings("unused")
    public String getDistributor(){
        return distributor;
    }

    @SuppressWarnings("unused")
    public String getVersion(){
        return version;
    }

    @SuppressWarnings("unused")
    public short getVersionNum(){
        return versionNum;
    }
}
