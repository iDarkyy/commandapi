package me.idarkyy.commandapi.flags;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum SenderType {
    /**
     * ConsoleCommandSender
     */
    CONSOLE("Console"),

    /**
     * Player
     */
    PLAYER("Player"),

    /**
     * Any of the both
     */
    BOTH("");

    private @NotNull String name;

    SenderType(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Check if the sender is an instance of
     *  the SenderType
     * @param sender the sender
     * @return true if the sender is an instance of the enum
     */
    public boolean check(CommandSender sender) {
        Objects.requireNonNull(sender, "sender");

        if(this == CONSOLE && sender instanceof ConsoleCommandSender) {
            return true;
        } else if(this == PLAYER && sender instanceof Player) {
            return true;
        } else return this == BOTH;
    }
}
