package com.pedestriamc.fonts.commands.font;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.commands.AbstractCommand;
import com.pedestriamc.fonts.message.Message;
import com.pedestriamc.fonts.users.User;
import net.wiicart.commands.command.CartCommandExecutor;
import net.wiicart.commands.command.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

class ResetCommand extends AbstractCommand implements CartCommandExecutor {

    ResetCommand(@NotNull Fonts fonts) {
        super(fonts);
    }

    @Override
    public void onCommand(@NotNull CommandData data) {
        CommandSender sender = data.sender();
        String args[] = data.args();

        if (checkBasicPermsAndNotify(sender)) {
            return;
        }

        if (args.length > 1) {
            sendMessage(sender, Message.TOO_MANY_ARGS);
            return;
        }

        User target = getTargetAndNotify(sender, args);
        if (target == null) {
            return;
        }

        target.setFont(null);
        saveUser(target);

        Map<String, String> placeholders = Map.of("{user}", target.getName());
        if (!target.getPlayer().equals(sender)) {
            sendMessage(target.getPlayer(), Message.FONT_RESET_OTHER, placeholders);
        }
        sendMessage(sender, Message.FONT_RESET, placeholders);
    }

    private boolean checkBasicPermsAndNotify(@NotNull CommandSender sender) {
        if (FontCommand.doesNotHaveBasicPerms(sender)) {
            sendMessage(sender, Message.NO_PERMS);
            return true;
        }
        return false;
    }

    @Nullable
    private User getTargetAndNotify(@NotNull CommandSender sender, @NotNull String args[]) {
        if (args.length == 0) {
            if (sender instanceof Player p) {
                return getUser(p);
            } else {
                sendMessage(sender, Message.CONSOLE_MUST_DEFINE_PLAYER);
                return null;
            }
        }

        if (FontCommand.cantModifyOthers(sender)) {
            sendMessage(sender, Message.CANT_USE_ON_OTHERS);
            return null;
        }

        String name = args[0];
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            sendMessage(sender, Message.PLAYER_NOT_FOUND, Map.of("{user}", name));
            return null;
        }

        return getUser(player);
    }
}
