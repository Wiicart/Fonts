package com.pedestriamc.fonts.commands;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.message.Message;
import net.wiicart.commands.command.CartCommandExecutor;
import net.wiicart.commands.command.CommandData;
import net.wiicart.commands.command.tree.CommandTree;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FontsCommand extends CommandTree {

    private static final Map<String, String> placeholders = Map.of("{version}", Fonts.VERSION);

    public FontsCommand(@NotNull Fonts fonts) {
        super(builder()
                .workbench(bench -> bench.store("version", () -> new VersionCommand(fonts)))
                .executes("version")
                .withChild("version", b -> b
                        .executes("version")
                        .withAliases("v")
                )
                .withChild("reload", b -> b.executes(new ReloadCommand(fonts)))
                .build()
        );
    }

    private static class ReloadCommand extends AbstractCommand implements CartCommandExecutor {

        ReloadCommand(@NotNull Fonts fonts) {
            super(fonts);
        }

        @Override
        public void onCommand(@NotNull CommandData data) {
            CommandSender sender = data.sender();
            if (
                    !sender.isOp() &&
                    !sender.hasPermission("*") &&
                    !sender.hasPermission("fonts.*") &&
                    !sender.hasPermission("fonts.reload")
            ) {
                sendMessage(sender, Message.NO_PERMS);
                return;
            }

            plugin().reload();
            sendMessage(sender, Message.RELOADED, placeholders);
        }
    }

    private static class VersionCommand extends AbstractCommand implements CartCommandExecutor {

        VersionCommand(@NotNull Fonts fonts) {
            super(fonts);
        }

        @Override
        public void onCommand(@NotNull CommandData data) {
            sendMessage(data.sender(), Message.VERSION, placeholders);
        }

    }

}
