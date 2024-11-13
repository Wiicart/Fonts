package com.pedestriamc.fonts.tabcompleters;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FontsTabCompleter implements TabCompleter {

    private final ImmutableList<String> suggestions;

    public FontsTabCompleter() {
        suggestions = ImmutableList.of("reload", "version");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return suggestions;
    }
}
