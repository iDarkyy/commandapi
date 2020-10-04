package me.idarkyy.commandapi;

import me.idarkyy.commandapi.utils.CommandMapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

public class CommandManager {
    private static final CommandMap COMMAND_MAP = CommandMapUtils.getCommandMap();
    private final Plugin plugin;
    private String fallbackPrefix;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
        this.fallbackPrefix = plugin.getName().toLowerCase();
    }

    public CommandBuilder create() {
        return new CommandBuilder(this);
    }

    public CommandBuilder create(String name, BaseCommand executor) {
        return new CommandBuilder(this).name(name).executor(executor);
    }

    public boolean unregister(String name) {
        Command command = COMMAND_MAP.getCommand(name);

        if(command != null) {
            return command.unregister(COMMAND_MAP);
        }

        return false;
    }

    public boolean isRegistered(String name) {
        Command command = COMMAND_MAP.getCommand(name);

        if(command != null) {
            return command.isRegistered();
        }

        return false;
    }

    protected void register(CommandBuilder builder, boolean unregisterIfExists) {
        if(isRegistered(builder.name) && unregisterIfExists) {
            unregister(builder.name);
        }

        COMMAND_MAP.register(fallbackPrefix, new CommandWrapper(builder));
    }

    public String getFallbackPrefix() {
        return fallbackPrefix;
    }

    public void setFallbackPrefix(String fallbackPrefix) {
        this.fallbackPrefix = fallbackPrefix;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
