package com.pedestriamc.fonts.text;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Font {

    private final Map<Character, Character> map;
    private final String name;

    public Font(String name, Map<Character, Character> map){
        this.map = map;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String translate(@NotNull String str){
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()){
            Character replacement = map.get(c);
            if(replacement != null){
                sb.append(replacement);
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        return name;
    }

}
