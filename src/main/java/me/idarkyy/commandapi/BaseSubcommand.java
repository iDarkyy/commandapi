package me.idarkyy.commandapi;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public abstract class BaseSubcommand {
    protected String permission;
    protected String permissionMessage;

    public abstract void execute(CommandSender sender, String subcommand, String[] args);

    public abstract HashMap<String, InternalSubcommand> getSubcommands();

    public abstract void trigger(CommandSender sender, String subcommand, String[] args);

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }
}
