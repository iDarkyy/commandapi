package me.idarkyy.commandapi;

import me.idarkyy.commandapi.annotations.Permission;
import me.idarkyy.commandapi.annotations.Subcommand;
import me.idarkyy.commandapi.event.SubcommandEvent;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ExternalSubcommand extends BaseSubcommand {

    private final HashMap<String, InternalSubcommand> subcommands;

    public ExternalSubcommand() {
        subcommands = fetchSubcommands();
    }

    @Override
    public final void trigger(CommandSender sender, String subcommand, String[] args) {
        if (args.length > 0 && subcommands.containsKey(args[0].toLowerCase())) {
            subcommands.get(args[0].toLowerCase()).trigger(sender, subcommand, Arrays.copyOfRange(args, 1, args.length));
        } else {

            if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
                sender.sendMessage(permissionMessage != null
                        ? permissionMessage
                        : CommandBuilder.DEFAULT_PERMISSION_MESSAGE);
            }

            execute(sender, subcommand, args);
        }
    }

    @Override
    public final HashMap<String, InternalSubcommand> getSubcommands() {
        return subcommands;
    }

    private HashMap<String, InternalSubcommand> fetchSubcommands() {
        Set<Method> methods = Arrays.stream(this.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Subcommand.class))
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == SubcommandEvent.class)
                .collect(Collectors.toSet());

        HashMap<String, InternalSubcommand> subcommands = new HashMap<>();

        for (Method method : methods) {
            for (String s : method.getAnnotation(Subcommand.class).value().toLowerCase().split("\\|")) {
                InternalSubcommand sc = new InternalSubcommand(this, method);

                sc.setPermission(method.isAnnotationPresent(Permission.class)
                        ? method.getAnnotation(Permission.class).value()
                        : null);

                sc.setPermissionMessage(this.permissionMessage);

                subcommands.put(s, sc);
            }
        }

        return subcommands;
    }
}
