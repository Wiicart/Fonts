package com.pedestriamc.fonts.text;

import com.pedestriamc.fonts.api.Font;

public class ComponentFont implements Font {

    private final String name;

    public ComponentFont(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String translate(String message) {
        return message;
    }

    @Override
    public String revert(String message) {
        return message;
    }
}
