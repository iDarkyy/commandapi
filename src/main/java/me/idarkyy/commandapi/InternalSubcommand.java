package me.idarkyy.commandapi;

import me.idarkyy.commandapi.annotations.UsableBy;
import me.idarkyy.commandapi.event.SubcommandEvent;
import me.idarkyy.commandapi.exception.SubcommandException;
import me.idarkyy.commandapi.flags.SenderType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class InternalSubcommand extends BaseSubcommand {
    protected final SenderType usableBy;
    private final Object object;
    private final Method method;
    private final HashMap<String, InternalSubcommand> subcommands = new HashMap<>();

    protected InternalSubcommand(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.usableBy = method.isAnnotationPresent(UsableBy.class)
                ? method.getAnnotation(UsableBy.class).value()
                : SenderType.BOTH;
    }

    @Override
    public void execute(CommandSender sender, String subcommand, String[] args) {
        if (!usableBy.check(sender)) {
            sender.sendMessage(String.format(ChatColor.RED + "This command is usable by %s-only", usableBy.getName()));
            return;
        }

        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(permissionMessage != null
                    ? permissionMessage
                    : CommandBuilder.DEFAULT_PERMISSION_MESSAGE);
        }

        try {
            method.invoke(object, new SubcommandEvent(subcommand, sender, args));
        } catch (InvocationTargetException e) {
            throw new SubcommandException(e.getCause());
        } catch (IllegalAccessException e) {
            System.err.println("ERROR: Method " + method.getName()
                    + " in class " + method.getDeclaringClass().getName()
                    + " has private access."
                    + "\n The sub-command will not work.");
        }
    }

    @Override
    public void trigger(CommandSender sender, String subcommand, String[] args) {
        this.execute(sender, subcommand, args);
    }

    @Override
    public HashMap<String, InternalSubcommand> getSubcommands() {
        return subcommands;
    }
}
