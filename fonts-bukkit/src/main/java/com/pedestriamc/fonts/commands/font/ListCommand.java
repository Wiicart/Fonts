package com.pedestriamc.fonts.commands.font;

import com.pedestriamc.fonts.Fonts;
import com.pedestriamc.fonts.api.Font;
import com.pedestriamc.fonts.commands.AbstractCommand;
import net.wiicart.commands.command.CartCommandExecutor;
import net.wiicart.commands.command.CommandData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class ListCommand extends AbstractCommand implements CartCommandExecutor {

    private static final String HEADER = ChatColor.translateAlternateColorCodes(
            '&', "&8[&cFonts&8] &7| &fAvailable Fonts"
    );

    private static final String ENTRY = ChatColor.DARK_GRAY + "• " + ChatColor.WHITE;

    ListCommand(@NotNull Fonts fonts) {
        super(fonts);
    }

    @Override
    public void onCommand(@NotNull CommandData data) {
        CommandSender sender = data.sender();
        List<String> fonts = getAllowedFonts(sender);

        String messages[] = new String[fonts.size() + 1];
        messages[0] = HEADER;
        int pos = 1;
        for (String font : fonts) {
            messages[pos++] = ENTRY + font;
        }

        sender.sendMessage(messages);
    }

    private List<String> getAllowedFonts(@NotNull CommandSender sender) {
        return plugin().getFontLoader().getLoadedFonts().stream()
                .filter(font -> font.allows(sender))
                .map(Font::getName)
                .toList();
    }
}
