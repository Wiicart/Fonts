package com.pedestriamc.fonts.commands;

import com.pedestriamc.fonts.Fonts;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FontsCommand implements CommandExecutor {

    private final Fonts fonts;

    public FontsCommand(Fonts fonts){
        this.fonts = fonts;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }

}
