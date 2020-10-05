package me.idarkyy.commandapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class CommandMapUtils {
    private static CommandMap commandMap;

    /**
     * Get the command map
     * @return instance of the bukkit command map
     */
    public static @NotNull CommandMap getCommandMap() {
        if (commandMap != null) {
            return commandMap;
        }

        try {
            Server server = Bukkit.getServer();
            Field field = server.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            return commandMap = (CommandMap) field.get(server);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        return null; // should never happen
    }
}
