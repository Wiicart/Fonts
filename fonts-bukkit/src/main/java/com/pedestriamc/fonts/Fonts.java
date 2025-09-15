package com.pedestriamc.fonts;

import com.pedestriamc.common.message.Messenger;
import com.pedestriamc.fonts.api.FontsRegistrar;
import com.pedestriamc.fonts.commands.font.FontCommand;
import com.pedestriamc.fonts.commands.FontsCommand;
import com.pedestriamc.fonts.impl.FontsImpl;
import com.pedestriamc.fonts.listeners.ChatListener;
import com.pedestriamc.fonts.listeners.JoinListener;
import com.pedestriamc.fonts.listeners.LeaveListener;
import com.pedestriamc.fonts.message.Message;
import com.pedestriamc.fonts.tabcompleters.FontTabCompleter;
import com.pedestriamc.fonts.tabcompleters.FontsTabCompleter;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.UserUtil;
import com.pedestriamc.fonts.users.YamlUserUtil;
import com.tchristofferson.configupdater.ConfigUpdater;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class Fonts extends JavaPlugin {

    public static final String DISTRIBUTOR = "hangar";
    public static final String VERSION = "1.2";
    public static final short VERSION_NUM = 2;

    private FontLoader fontLoader;
    private UserUtil userUtil;
    private FileConfiguration usersConfig;
    private File usersFile;
    private FileConfiguration messagesConfig;
    private Messenger<Message> messenger;

    @Override
    public void onLoad() {
        updateConfigs();
        reloadConfig();
    }

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
        log("Fonts version " + VERSION + " loaded.");
    }

    @Override
    public void onDisable() {
        this.fontLoader = null;
        this.userUtil = null;
        this.usersConfig = null;
        this.messagesConfig = null;
        this.messenger = null;
        HandlerList.unregisterAll(this);
        FontsRegistrar.unregister();
    }

    public void reload() {
        onDisable();
        onEnable();
    }

    private void loadUsers() {
        if (userUtil.isEmpty() && !Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                userUtil.loadUser(p.getUniqueId());
            }
        }
    }

    private void checkUpdate() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://www.wiicart.net/fonts/version.txt").openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            short latest = Short.parseShort(raw);
            if(latest > VERSION_NUM) {
                Bukkit.getLogger().info("+-------------[Fonts]-------------+");
                Bukkit.getLogger().info("|    A new update is available!   |");
                Bukkit.getLogger().info("|          Download at:           |");
                Bukkit.getLogger().info("|    https://wiicart.net/fonts    |");
                Bukkit.getLogger().info("+---------------------------------+");
            }
        } catch(IOException a) {
            Bukkit.getLogger().info("[Strings] Unable to check for updates.");
        }
    }

    private void loadApi() {
        FontsRegistrar.register(new FontsImpl(this));
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

    @SuppressWarnings("SameParameterValue")
    private void log(String msg) {
        getLogger().info(msg);
    }

    private void setupMetrics() {
        int pluginId = 23619;
        new Metrics(this, pluginId)
                .addCustomChart(new SimplePie("distributor", this::getDistributor));
    }

    private void updateConfig(@NotNull String name) {
        File file = new File(getDataFolder(), name);
        if (file.exists()) {
            try {
                ConfigUpdater.update(this, name, file);
            } catch(IOException e) {
                getLogger().warning("Failed to update " + name);
            }
        }
    }

    private void updateConfigs() {
        updateConfig("config.yml");
        updateConfig("messages.yml");
    }


    public void async(@NotNull Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

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
        try {
            getUsersFileConfig().save(usersFile);
        } catch(IOException e) {
            getLogger().info("Failed to save users file.");
        }
    }

    public String getDistributor() {
        return DISTRIBUTOR;
    }

}
