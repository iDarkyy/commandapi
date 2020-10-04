package me.idarkyy.commandapi;

import me.idarkyy.commandapi.annotations.Subcommand;
import me.idarkyy.commandapi.annotations.UsableBy;
import me.idarkyy.commandapi.event.SubcommandEvent;
import me.idarkyy.commandapi.flags.SenderType;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CommandBuilder {
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

    public CommandBuilder executor(BaseCommand executor) {
        this.executor = executor;
        return this;
    }

    public CommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder aliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
    }

    public CommandBuilder aliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder usableBy(SenderType usableBy) {
        this.usableBy = usableBy;
        return this;
    }

    public CommandBuilder addExternalSubcommand(ExternalSubcommand subcommand) {
        if(!subcommand.getClass().isAnnotationPresent(Subcommand.class)) {
            throw new IllegalArgumentException("Sub-command must be annotated with Subcommand.class");
        }

        for(String s : subcommand.getClass().getAnnotation(Subcommand.class).value().split("\\|")) {
            subcommands.put(s, subcommand);
        }

        return this;
    }


    public void register(boolean unregisterIfExists) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(executor, "executor");

        if (usableBy == null) {
            usableBy = executor.getClass().isAnnotationPresent(UsableBy.class)
                    ? executor.getClass().getAnnotation(UsableBy.class).value()
                    : SenderType.BOTH;
        }

        Set<Method> methods = Arrays.stream(executor.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Subcommand.class))
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == SubcommandEvent.class)
                .collect(Collectors.toSet());

        for(Method method : methods) {
            subcommands.put(method.getAnnotation(Subcommand.class).value(), new InternalSubcommand(executor, method));
        }

        commandManager.register(this, unregisterIfExists);

    }

    public void register() {
        this.register(false);
    }
}
