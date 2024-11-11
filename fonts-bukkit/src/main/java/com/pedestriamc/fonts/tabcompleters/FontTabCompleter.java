package com.pedestriamc.fonts.tabcompleters;

import com.google.common.collect.ImmutableList;
import com.pedestriamc.fonts.Fonts;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class FontTabCompleter implements TabCompleter
{

    private final ImmutableList<String> fontList;
    private final ImmutableList<String> empty;

    public FontTabCompleter(Fonts fonts)
    {
        File fontsFolder = new File(fonts.getDataFolder(), "fonts");
        String[] list = fontsFolder.list();
        if(list == null){
            fontList = ImmutableList.of();
        }else{
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(list));
            ListIterator<String> it = arrayList.listIterator();
            while(it.hasNext()){
                String next = it.next();
                if(!next.contains(".yml")){
                    it.remove();
                } else {
                    next = next.replace(".yml", "");
                    it.set(next);
                }
            }
            fontList = ImmutableList.copyOf(arrayList);
        }

        empty = ImmutableList.of();

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {

        if(args.length < 2)
        {
            return fontList;
        }

        if(args.length == 2)
        {
            ArrayList<String> list = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()){
                list.add(p.getName());
            }
            return list;
        }

        return empty;

    }
}
