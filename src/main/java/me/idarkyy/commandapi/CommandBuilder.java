package me.idarkyy.commandapi;

import me.idarkyy.commandapi.annotations.Permission;
import me.idarkyy.commandapi.annotations.Subcommand;
import me.idarkyy.commandapi.annotations.UsableBy;
import me.idarkyy.commandapi.event.SubcommandEvent;
import me.idarkyy.commandapi.flags.SenderType;
import org.bukkit.ChatColor;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CommandBuilder {
    protected static final String DEFAULT_PERMISSION_MESSAGE = ChatColor.RED + "You don't have access to this command.";
    protected final CommandManager commandManager;
    protected BaseCommand executor;
    protected String name, description = "";
    protected List<String> aliases;
    protected String permission, permissionMessage;
    protected SenderType usableBy;
    protected HashMap<String, BaseSubcommand> subcommands = new HashMap<>();


    protected CommandBuilder(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Set the command executor
     *
     * @param executor the command executor
     */
    public CommandBuilder executor(BaseCommand executor) {
        this.executor = executor;
        return this;
    }

    /**
     * Set the command name
     *
     * @param name the name
     */
    public CommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the command description
     *
     * @param description the description
     */
    public CommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set command aliases
     *
     * @param aliases an array of aliases
     */
    public CommandBuilder aliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
    }

    /**
     * Set command aliases
     *
     * @param aliases a list of aliases
     */
    public CommandBuilder aliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * Set the command permission
     *
     * @param permission the permission
     */
    public CommandBuilder permission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Set the permission message
     *
     * @param permissionMessage the message
     */
    public CommandBuilder permissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;

        for(BaseSubcommand sc : subcommands.values()) {
            sc.setPermissionMessage(permissionMessage);
        }

        return this;
    }

    /**
     * Set which sender type the command is usable by
     *
     * @param usableBy the sender type
     */
    public CommandBuilder usableBy(SenderType usableBy) {
        this.usableBy = usableBy;
        return this;
    }

    /**
     * Register an external sub-command
     * Requires the command to be annotated with @Subcommand
     *
     * @param subcommand the sub-command
     */
    public CommandBuilder addExternalSubcommand(ExternalSubcommand subcommand) {
        Objects.requireNonNull(subcommand, "subcommand");

        if (!subcommand.getClass().isAnnotationPresent(Subcommand.class)) {
            throw new IllegalArgumentException("Sub-command must be annotated with Subcommand.class");
        }

        for (String s : subcommand.getClass().getAnnotation(Subcommand.class).value().split("\\|")) {
            subcommands.put(s, subcommand);
        }

        return this;
    }

    /**
     * Register an external sub-command
     * Does not require the command to be annotated with @Subcommand
     *
     * @param name       the sub-command name
     * @param subcommand the sub-command
     */
    public CommandBuilder addExternalSubcommand(String name, ExternalSubcommand subcommand) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(subcommand, "subcommand");

        subcommand.setPermissionMessage(permissionMessage);

        if(subcommand.getClass().isAnnotationPresent(Permission.class)) {
            subcommand.setPermission(subcommand.getClass().getAnnotation(Permission.class).value());
        }

        for (String s : name.split("\\|")) {
            subcommands.put(s, subcommand);
        }

        return this;
    }


    /**
     * Register the command
     *
     * @param unregisterIfExists If a command with the specified name already exists,
     *                           should the registration proceed?
     */
    public void register(boolean unregisterIfExists) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(executor, "executor");

        if (usableBy == null) {
            usableBy = executor.getClass().isAnnotationPresent(UsableBy.class)
                    ? executor.getClass().getAnnotation(UsableBy.class).value()
                    : SenderType.BOTH;
        }

        if (permissionMessage == null) {
            permissionMessage = DEFAULT_PERMISSION_MESSAGE;
        }

        // find eligible methods
        Set<Method> methods = Arrays.stream(executor.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Subcommand.class))
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == SubcommandEvent.class)
                .collect(Collectors.toSet());

        for (Method method : methods) {
            InternalSubcommand sc = new InternalSubcommand(executor, method);

            sc.setPermission(method.isAnnotationPresent(Permission.class)
                    ? method.getAnnotation(Permission.class).value()
                    : null);

            sc.setPermissionMessage(permissionMessage);

            subcommands.put(method.getAnnotation(Subcommand.class).value(), sc);
        }

        commandManager.register(this, unregisterIfExists);

    }

    /**
     * Register the command
     */
    public void register() {
        this.register(true);
    }
}
