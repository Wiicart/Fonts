package com.pedestriamc.fonts.commands;

import com.pedestriamc.common.commands.CommandBase;
import com.pedestriamc.common.message.Messenger;
import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FontsCommand extends CommandBase {

    public FontsCommand(Fonts fonts)
    {
        super();
        Map<String, CommandComponent> map = new HashMap<>();
        map.put("VERSION", new VersionCommand(fonts));
        map.put("V", new VersionCommand(fonts));
        map.put("RELOAD", new ReloadCommand(fonts));
        initialize(map, new VersionCommand(fonts));
    }

    protected static class ReloadCommand extends AbstractCommandComponent
    {
        private final Fonts fonts;
        private final Messenger<Message> messenger;
        Map<String, String> placeholders;

        public ReloadCommand(Fonts fonts)
        {
            this.fonts = fonts;
            messenger = fonts.getMessenger();
            placeholders = new HashMap<>();
            placeholders.put("{version}", fonts.getVersion());
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
        {
            if(
                    forbids(sender, "fonts.reload") &&
                    forbids(sender, "fonts.*") &&
                    forbids(sender, "*")
            ){
                messenger.sendMessage(sender, Message.NO_PERMS);
                return true;
            }

            fonts.reload();
            messenger.sendMessage(sender, Message.RELOADED, placeholders);

            return true;
        }

    }

    protected static class VersionCommand extends AbstractCommandComponent
    {

        private final Messenger<Message> messenger;
        Map<String, String> placeholders;

        public VersionCommand(Fonts fonts)
        {
            messenger = fonts.getMessenger();
            placeholders = new HashMap<>();
            placeholders.put("{version}", fonts.getVersion());
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
        {
            messenger.sendMessage(sender, Message.VERSION, placeholders);
            return true;
        }

    }

}
