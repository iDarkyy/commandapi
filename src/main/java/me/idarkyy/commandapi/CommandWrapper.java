package me.idarkyy.commandapi;

import me.idarkyy.commandapi.exception.CommandException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public class CommandWrapper extends BukkitCommand {
    private final CommandBuilder builder;
    private final HashMap<String, ? extends BaseSubcommand> subcommands;
    private final BaseCommand executor;

    protected CommandWrapper(CommandBuilder builder) {
        super(builder.name);
        setDescription(builder.description);
        setPermission(builder.permission);
        setPermissionMessage(builder.permissionMessage);

        if (builder.aliases != null) {
            setAliases(builder.aliases);
        }

        this.subcommands = builder.subcommands;
        this.executor = builder.executor;
        this.builder = builder;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!builder.usableBy.check(sender)) {
            sender.sendMessage(String.format(ChatColor.RED + "This command is usable by %s-only", builder.usableBy.getName()));
            return true;
        }

        if(args.length > 0 && subcommands.containsKey(args[0])) {
            subcommands.get(args[0]).trigger(sender, args[0], Arrays.copyOfRange(args, 1, args.length));
        } else {
            try {
                executor.execute(sender, commandLabel, args);
            } catch(Exception e) {
                throw new CommandException(e);
            }
        }

        return true;
    }
}
