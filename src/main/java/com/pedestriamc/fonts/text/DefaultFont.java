package com.pedestriamc.fonts.text;

import org.jetbrains.annotations.NotNull;

public class DefaultFont extends Font{

    public DefaultFont() {
        super("default", null);
    }

    @Override
    public String translate(@NotNull String str){
        return str;
    }


}
