package com.pedestriamc.fonts.commands;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.message.Message;
import com.pedestriamc.fonts.message.Messenger;
import com.pedestriamc.fonts.text.DefaultFont;
import com.pedestriamc.fonts.text.Font;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class FontCommand implements CommandExecutor {

    private final FontLoader fontLoader;
    private final UserUtil userUtil;
    private final Messenger messenger;

    public FontCommand(Fonts fonts) {
        fontLoader = fonts.getFontLoader();
        userUtil = fonts.getUserUtil();
        messenger = fonts.getMessenger();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            messenger.sendMessage(sender, Message.PLAYER_ONLY);
            return true;
        }
        if (!sender.hasPermission("fonts.set")) {
            messenger.sendMessage(sender, Message.NO_PERMS);
            return true;
        }
        if (args.length > 2) {
            messenger.sendMessage(sender, Message.TOO_MANY_ARGS);
            return true;
        }

        Font font = fontLoader.getFont(args[0]);

        if (!args[0].equals("default") && font instanceof DefaultFont) {
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("{font_name}", args[0]);
            messenger.sendMessage(sender, Message.FONT_NOT_FOUND, placeholders);
            return true;
        }

        if (!sender.hasPermission("fonts.font." + font.getName())) {
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("{font_name}", font.getName());
            messenger.sendMessage(sender, Message.NO_PERMS_FONT, placeholders);
            return true;
        }

        if (args.length == 1) {
            User user = userUtil.getUserMap().getUser(player.getUniqueId());
            user.setFont(font);
            userUtil.saveUser(user);
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("{font_name}", font.getName());
            placeholders.put("{user}", user.getName());
            messenger.sendMessage(sender, Message.FONT_CHANGED, placeholders);
            return true;
        }

        if (!sender.hasPermission("fonts.set.other")) {
            messenger.sendMessage(sender, Message.NO_PERMS);
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("{user}", args[1]);
            messenger.sendMessage(sender, Message.PLAYER_NOT_FOUND, placeholders);
            return true;
        }

        User user = userUtil.getUserMap().getUser(target);
        user.setFont(font);
        userUtil.saveUser(user);
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("{font_name}", font.getName());
        placeholders.put("{user}", target.getName());
        messenger.sendMessage(sender, Message.FONT_CHANGED_OTHER, placeholders);
        messenger.sendMessage(target, Message.FONT_CHANGED, placeholders);

        return true;

    }
}
