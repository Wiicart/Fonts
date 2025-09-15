package com.pedestriamc.fonts.api;

import org.jetbrains.annotations.Nullable;

public interface FontsUser {

    String getName();

    void setFont(@Nullable Font font);

    Font getFont();
}
