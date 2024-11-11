package com.pedestriamc.fonts.commands;

import com.pedestriamc.common.commands.CommandBase;
import com.pedestriamc.fonts.Fonts;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FontsCommand extends CommandBase {

    private final Fonts fonts;

    public FontsCommand(Fonts fonts)
    {
        super();
        Map<String, CommandComponent> map = new HashMap<>();
        initialize(map, null);
        this.fonts = fonts;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }

}
