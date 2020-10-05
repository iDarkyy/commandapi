package me.idarkyy.commandapi;

import me.idarkyy.commandapi.utils.CommandMapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

/**
 * Command Manager
 * Register commands without the need to specify them in the
 * plugin.yml file.
 */
public class CommandManager {
    private static final CommandMap COMMAND_MAP = CommandMapUtils.getCommandMap();
    private final Plugin plugin;
    private String fallbackPrefix;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
        this.fallbackPrefix = plugin.getName().toLowerCase();
    }

    /**
     * Create a new command
     *
     * @return the command builder
     * @see CommandBuilder
     */
    public CommandBuilder create() {
        return new CommandBuilder(this);
    }

    /**
     * Create a new command
     *
     * @param name     the command name
     * @param executor the command executor
     * @return the command builder
     * @see CommandBuilder
     */
    public CommandBuilder create(String name, BaseCommand executor) {
        return new CommandBuilder(this).name(name).executor(executor);
    }

    /**
     * Unregister a command
     *
     * @param name the command name
     * @return if the command has been successfully found and unregistered,
     * returns true, returns false if the command does not
     * exist or is not registered
     */
    public boolean unregister(String name) {
        Command command = COMMAND_MAP.getCommand(name);

        if (command != null) {
            return command.unregister(COMMAND_MAP);
        }

        return false;
    }

    /**
     * Is the command registered
     *
     * @param name the command name
     * @return false if the command does not exist and isn't registered,
     * otherwise returns true
     */
    public boolean isRegistered(String name) {
        Command command = COMMAND_MAP.getCommand(name);

        if (command != null) {
            return command.isRegistered();
        }

        return false;
    }

    /**
     * Register a command builder
     *
     * @param builder            the commnad builder
     * @param unregisterIfExists should the command be unregistered if it already exists
     */
    public void register(CommandBuilder builder, boolean unregisterIfExists) {
        if (isRegistered(builder.name) && unregisterIfExists) {
            unregister(builder.name);
        }

        COMMAND_MAP.register(fallbackPrefix, new CommandWrapper(builder));
    }

    /**
     * Fallback prefix
     *
     * @return alternate prefix
     */
    public String getFallbackPrefix() {
        return fallbackPrefix;
    }

    /**
     * Set the fallback prefix
     *
     * @param fallbackPrefix the prefix
     */
    public void setFallbackPrefix(String fallbackPrefix) {
        this.fallbackPrefix = fallbackPrefix;
    }

    /**
     * Registering plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }
}
