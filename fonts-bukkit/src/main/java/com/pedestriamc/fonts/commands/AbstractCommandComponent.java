package com.pedestriamc.fonts.commands;

import com.pedestriamc.common.commands.CommandBase;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommandComponent implements CommandBase.CommandComponent
{

    public boolean forbids(CommandSender sender, String permission)
    {
        return !sender.hasPermission(permission);
    }

}
