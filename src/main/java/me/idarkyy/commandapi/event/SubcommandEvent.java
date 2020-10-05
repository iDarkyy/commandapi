package me.idarkyy.commandapi.event;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SubcommandEvent {
    private final String label;
    private final CommandSender sender;
    private final String[] args;


    public SubcommandEvent(@NotNull String label, @NotNull CommandSender sender, @NotNull String[] args) {
        this.label = label;
        this.sender = sender;
        this.args = args;
    }

    /**
     * Command name (label)
     * @return label of the command
     */
    public @NotNull String getLabel() {
        return label;
    }

    /**
     * Command sender
     * @return sender of the command
     */
    public @NotNull CommandSender getSender() {
        return sender;
    }

    /**
     * Command arguments
     * @return array of the arguments
     */
    public @NotNull String[] getArgs() {
        return args;
    }
}
