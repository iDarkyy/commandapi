package me.idarkyy.commandapi;

import org.bukkit.command.CommandSender;

public abstract class BaseCommand {
    public abstract void execute(CommandSender sender, String label, String[] args);
}
