package com.pedestriamc.fonts.message;

import com.pedestriamc.fonts.Fonts;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.EnumMap;
import java.util.Map;

public class Messenger {

    private final EnumMap<Message, Object> enumMap;
    private final String prefix;

    public Messenger(Fonts fonts) {
        enumMap = new EnumMap<>(Message.class);
        FileConfiguration config = fonts.getMessagesConfig();
        prefix = config.getString("prefix", "&8[&cFonts&8] &f");
        for (Message msg : Message.values()) {
            String configValue = msg.toString().replace("_", "-").toLowerCase();
            try {
                if(config.isList(configValue)) {
                    enumMap.put(msg, config.getStringList(configValue).toArray(new String[0]));
                } else {
                    enumMap.put(msg, config.getString(configValue));
                }
            } catch (NullPointerException e) {
                Bukkit.getLogger().warning("[Fonts] Unable to find message for " + msg);
            }
        }
    }

    /**
     * Sends a message.
     *
     * @param sender  Who the message should be sent to.
     * @param message The Message to be sent.
     */
    public void sendMessage(CommandSender sender, Message message) {
        Object obj = enumMap.get(message);
        if(obj instanceof String[] msg) {
            for (String str : msg) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
            }
        } else if(obj instanceof String) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enumMap.get(message)));
        } else {
            Bukkit.getLogger().info("[Fonts] Unknown object type or value not found for message " + message.toString());
        }
    }

    /**
     * Sends a message with placeholders.
     *
     * @param sender       Who the message should be sent to.
     * @param message      The Message to be sent.
     * @param placeholders A hashmap containing keys for the placeholder and values of the replacement.
     */
    public void sendMessage(CommandSender sender, Message message, Map<String, String> placeholders) {
        Object msg = enumMap.get(message);
        if(msg instanceof String[]) {
            for (String str : (String[]) msg) {
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    str = str.replace(entry.getKey(), entry.getValue());
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
            }
            return;
        }
        if(msg instanceof String str) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                str = str.replace(entry.getKey(), entry.getValue());
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
            return;
        }
        Bukkit.getLogger().info("[Fonts] Unknown object type or value not found for message " + message.toString());
    }


}
