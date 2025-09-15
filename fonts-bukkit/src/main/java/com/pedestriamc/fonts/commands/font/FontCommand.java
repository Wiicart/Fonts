package com.pedestriamc.fonts.commands.font;

import com.pedestriamc.common.message.Messenger;
import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.message.Message;
import com.pedestriamc.fonts.text.FontLoader;
import com.pedestriamc.fonts.users.User;
import com.pedestriamc.fonts.users.UserUtil;
import net.wiicart.commands.command.CartCommandExecutor;
import net.wiicart.commands.command.CommandData;
import net.wiicart.commands.command.tree.CommandTree;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

public class FontCommand extends CommandTree implements CartCommandExecutor {

    private static final Map<String, String> INVALID_USAGE_PLACEHOLDERS = Map.of("{usage}", "/font {font}");

    private final FontLoader loader;
    private final UserUtil userUtil;
    private final Messenger<Message> messenger;

    public FontCommand(@NotNull Fonts fonts) {
        super(THIS, builder -> builder
                .withChild("list", child -> child.executes(new ListCommand(fonts)))
                .withChild("reset", child -> child.executes(new ResetCommand(fonts)))
        );
        loader = fonts.getFontLoader();
        userUtil = fonts.getUserUtil();
        messenger = fonts.getMessenger();
    }

    @Override
    public void onCommand(@NotNull CommandData data) {
        CommandSender sender = data.sender();
        String args[] = data.args();

        if (checkBasicPermsAndNotify(sender)) {
            return;
        }

        if (checkArgCountAndNotify(sender, args)) {
            return;
        }

        User target = getTargetAndNotify(sender, args);
        if (target == null) {
            return;
        }

        Font font = getFontAndNotify(sender, args);
        if (font == null) {
            return;
        }

        target.setFont(font);
        userUtil.saveUser(target);

        Map<String, String> placeholders = Map.of(
                "{font_name}", font.getName(),
                "{user}", target.getName()
        );

        if (!target.getPlayer().equals(sender)) {
            messenger.sendMessage(sender, Message.FONT_CHANGED_OTHER, placeholders);
        }
        messenger.sendMessage(target.getPlayer(), Message.FONT_CHANGED, placeholders);
    }

    private boolean checkBasicPermsAndNotify(@NotNull CommandSender sender) {
        if (doesNotHaveBasicPerms(sender)) {
            messenger.sendMessage(sender, Message.NO_PERMS);
            return true;
        }
        return false;
    }


    private boolean checkArgCountAndNotify(@NotNull CommandSender sender, @NotNull String args[]) {
        return switch (args.length) {
            case 0 -> {
                messenger.sendMessage(sender, Message.INVALID_USAGE, INVALID_USAGE_PLACEHOLDERS);
                yield true;
            }
            case 1 -> {
                if (!(sender instanceof Player)) {
                    messenger.sendMessage(sender, Message.CONSOLE_MUST_DEFINE_PLAYER);
                    yield true;
                }
                yield false;
            }
            case 2 -> false; // validate player later
            default -> {
                messenger.sendMessage(sender, Message.TOO_MANY_ARGS);
                yield true;
            }
        };
    }

    @Nullable
    private User getTargetAndNotify(@NotNull CommandSender sender, @NotNull String args[]) {
        if (args.length < 2) {
            return userUtil.getUser(((Player) sender).getUniqueId());
        }

        if (cantModifyOthers(sender)) {
            messenger.sendMessage(sender, Message.CANT_USE_ON_OTHERS);
            return null;
        }

        String name = args[1];
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            messenger.sendMessage(sender, Message.PLAYER_NOT_FOUND, Map.of("{user}", name));
            return null;
        }

        return userUtil.getUser(player.getUniqueId());
    }

    @Nullable
    private Font getFontAndNotify(@NotNull CommandSender sender, @NotNull String args[]) {
        String name = args[0].toLowerCase(Locale.ROOT);
        Font font = loader.getFont(name);
        if (font == null) {
            messenger.sendMessage(sender, Message.FONT_NOT_FOUND, Map.of("{font_name}", name));
            return null;
        }

        if (doesNotHavePermsForFont(sender, name)) {
            messenger.sendMessage(sender, Message.NO_PERMS_FONT, Map.of("{font_name}", name));
            return null;
        }

        return font;
    }

    private boolean doesNotHavePermsForFont(@NotNull CommandSender sender, @NotNull String name) {
        return !(sender.isOp() ||
                sender.hasPermission("*") ||
                sender.hasPermission("fonts.*") ||
                sender.hasPermission("fonts.font.*") ||
                sender.hasPermission("fonts.font." + name));
    }

    static boolean cantModifyOthers(@NotNull CommandSender sender) {
        return !(sender.isOp() ||
                sender.hasPermission("*") ||
                sender.hasPermission("fonts.*") ||
                sender.hasPermission("fonts.set.*") ||
                sender.hasPermission("fonts.set.other"));
    }

    static boolean doesNotHaveBasicPerms(@NotNull CommandSender sender) {
        return !(sender.isOp() ||
                sender.hasPermission("*") ||
                sender.hasPermission("fonts.*") ||
                sender.hasPermission("fonts.set") ||
                sender.hasPermission("fonts.set.*"));
    }

}
