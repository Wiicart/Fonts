package com.pedestriamc.fonts.tabcompleters;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.text.FontLoader;
import net.wiicart.commands.tabcomplete.TabCompleteUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FontTabCompleter implements TabCompleter {

    private static final List<String> EMPTY = List.of();

    private final FontLoader loader;

    public FontTabCompleter(@NotNull Fonts fonts) {
        loader = fonts.getFontLoader();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return switch (args.length) {
            case 0 -> genList(sender);
            case 1 -> TabCompleteUtil.filter(genList(sender), args[0]);
            case 2 -> TabCompleteUtil.filter(getOnlinePlayers(), args[1]);
            default -> EMPTY;
        };
    }

    @NotNull
    private List<String> genList(@NotNull CommandSender sender) {
        List<String> list = new ArrayList<>(loader.getLoadedFonts().stream()
                .filter(font -> font.allows(sender))
                .map(Font::getName)
                .toList());

        list.add("reset");
        list.add("list");

        return list;
    }

    private List<String> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();
    }
}
