package me.idarkyy.commandapi;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public abstract class BaseSubcommand {
    public abstract void execute(CommandSender sender, String subcommand, String[] args);

    public abstract HashMap<String, InternalSubcommand> getSubcommands();

    public abstract void trigger(CommandSender sender, String subcommand, String[] args);

}
