package com.pedestriamc.fonts.text;

import com.pedestriamc.fonts.api.Font;
import org.apache.commons.collections4.BidiMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnicodeFont implements Font {

    private final BidiMap<Character, String> map;
    private final String name;

    public UnicodeFont(String name, BidiMap<Character, String> map) {
        this.name = name;
        this.map = map;
    }

    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String translate(@NotNull String str) {

        String temp = "";
        if(str.charAt(0) == '§') {
            temp = str.substring(0, 2);
            str = str.substring(2);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(temp);
        for (char c : str.toCharArray()) {
            String replacement = map.get(c);
            if (replacement != null) {
                sb.append(replacement);
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public @NotNull String revert(@NotNull String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            String replacement = String.valueOf(map.getKey(c));
            if (replacement != null) {
                sb.append(replacement);
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public boolean allows(@NotNull CommandSender sender) {
        return sender.isOp() ||
                sender.hasPermission("*") ||
                sender.hasPermission("fonts.*") ||
                sender.hasPermission("fonts.font.*") ||
                sender.hasPermission("fonts.font." + getName());
    }

    @Override
    public String toString() {
        return name;
    }

}
