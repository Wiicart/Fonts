package com.pedestriamc.fonts;

import com.pedestriamc.common.message.Messenger;
import com.pedestriamc.fonts.api.FontsProvider;
import com.pedestriamc.fonts.commands.FontCommand;
import com.pedestriamc.fonts.commands.FontsCommand;
import com.pedestriamc.fonts.impl.FontsImpl;
import com.pedestriamc.fonts.listeners.ChatListener;
import com.pedestriamc.fonts.listeners.JoinListener;
import com.pedestriamc.fonts.listeners.LeaveListener;
import com.pedestriamc.fonts.message.Message;
import com.pedestriamc.fonts.tabcompleters.FontTabCompleter;
import com.pedestriamc.fonts.tabcompleters.FontsTabCompleter;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import com.pedestriamc.fonts.users.YamlUserUtil;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public final class Fonts extends JavaPlugin {

    @SuppressWarnings("FieldCanBeLocal")
    private final String distributor = "hangar";
    @SuppressWarnings("FieldCanBeLocal")
    private final String version = "1.0";
    @SuppressWarnings("FieldCanBeLocal")
    private final short versionNum = 1;

    @SuppressWarnings("FieldCanBeLocal")
    private Metrics metrics;

    private FontLoader fontLoader;
    private UserUtil userUtil;
    private FileConfiguration usersConfig;
    private File usersFile;
    private FileConfiguration messagesConfig;
    private Messenger<Message> messenger;
    private UUID apiKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupFiles();
        createObjects();
        registerClasses();
        loadUsers();
        loadApi();
        setupMetrics();
        checkUpdate();
        log("Fonts version " + getVersion() + " loaded.");
    }

    @Override
    public void onDisable() {
        userUtil.getUserMap().clear();
        this.fontLoader = null;
        this.userUtil = null;
        this.usersConfig = null;
        this.messagesConfig = null;
        this.messenger = null;
        HandlerList.unregisterAll(this);
        try{
            FontsProvider.unregister(this, apiKey);
        } catch (SecurityException ignored) {}
        log("Fonts disabled.");
    }

    public void reload(){
        onDisable();
        onEnable();
    }

    private void loadUsers(){
        if(userUtil.getUserMap().isEmpty() && !Bukkit.getOnlinePlayers().isEmpty()){
            for(Player p : Bukkit.getOnlinePlayers()){
                User user = userUtil.loadUser(p);
                userUtil.getUserMap().addUser(user);
            }
        }
    }

    private void checkUpdate(){
        try{
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://www.wiicart.net/fonts/version.txt").openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            short latest = Short.parseShort(raw);
            if(latest > versionNum){
                Bukkit.getLogger().info("+-------------[Fonts]-------------+");
                Bukkit.getLogger().info("|    A new update is available!   |");
                Bukkit.getLogger().info("|          Download at:           |");
                Bukkit.getLogger().info("|    https://wiicart.net/fonts    |");
                Bukkit.getLogger().info("+---------------------------------+");
            }
        } catch(IOException a){
            Bukkit.getLogger().info("[Strings] Unable to check for updates.");
        }
    }

    private void loadApi(){
        FontsImpl fontsImpl = new FontsImpl(this);
        apiKey = UUID.randomUUID();
        FontsProvider.register(fontsImpl, this, apiKey);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setupFiles() {
        usersFile = new File(getDataFolder(), "users.yml");
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
        String prefix = getMessagesConfig().getString("prefix");
        if(prefix == null){
            prefix = "&8[&cFonts&8] &f";
        }
        messenger = new Messenger<>(getMessagesConfig(), prefix, Message.class);
        fontLoader = new FontLoader(this);
        userUtil = new YamlUserUtil(this);
    }

    @SuppressWarnings("ConstantConditions")
    private void registerClasses() {
        getCommand("font").setExecutor(new FontCommand(this));
        getCommand("font").setTabCompleter(new FontTabCompleter(this));
        getCommand("fonts").setExecutor(new FontsCommand(this));
        getCommand("fonts").setTabCompleter(new FontsTabCompleter());
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this), this);
    }

    private void log(String msg){
        getLogger().info(msg);
    }

    private void setupMetrics(){
        int pluginId = 23619;
        metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("distributor", this::getDistributor));
    }

    // All getter methods from this point on

    public FileConfiguration getUsersFileConfig() {
        return usersConfig;
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

    public Messenger<Message> getMessenger() {
        return messenger;
    }

    public void saveUsersFile() {
        try{
            getUsersFileConfig().save(usersFile);
        }catch (IOException e){
            getLogger().info("Failed to save users file.");
        }
    }

    public String getDistributor() {
        return distributor;
    }

    public String getVersion() {
        return version;
    }

    @SuppressWarnings("unused")
    public short getVersionNum() {
        return versionNum;
    }

    public User getUser(Player player) {
        return userUtil
                .getUserMap()
                .getUser(player);
    }

    @SuppressWarnings("unused")
    public void removeUser(Player player) {
        userUtil.getUserMap().removeUser(player);
    }


}
