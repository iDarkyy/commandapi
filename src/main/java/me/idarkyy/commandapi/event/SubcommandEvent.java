package me.idarkyy.commandapi.event;

import org.bukkit.command.CommandSender;

public class SubcommandEvent {
    private final String label;
    private final CommandSender sender;
    private final String[] args;


    public SubcommandEvent(String label, CommandSender sender, String[] args) {
        this.label = label;
        this.sender = sender;
        this.args = args;
    }

    public String getLabel() {
        return label;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }
}
