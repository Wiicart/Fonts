package com.pedestriamc.fonts.commands;

import com.pedestriamc.common.message.Messenger;
import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.message.Message;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("unused")
public class AbstractCommand {

    private final Fonts fonts;
    private final Messenger<Message> messenger;
    private final UserUtil userUtil;

    protected AbstractCommand(@NotNull Fonts fonts) {
        this.fonts = fonts;
        messenger = fonts.getMessenger();
        userUtil = fonts.getUserUtil();
    }

    protected Fonts plugin() {
        return fonts;
    }

    protected Messenger<Message> messenger() {
        return messenger;
    }

    protected UserUtil userUtil() {
        return userUtil;
    }

    @SuppressWarnings("SameParameterValue")
    protected void sendMessage(@NotNull CommandSender recipient, @NotNull Message message) {
        messenger().sendMessage(recipient, message);
    }

    protected void sendMessage(@NotNull CommandSender recipient, @NotNull Message message, @NotNull Map<String, String> placeholders) {
        messenger().sendMessage(recipient, message, placeholders);
    }

    protected User getUser(@NotNull Player player) {
        return userUtil().getUser(player.getUniqueId());
    }

    protected void saveUser(@NotNull User user) {
        userUtil().saveUser(user);
    }

}
