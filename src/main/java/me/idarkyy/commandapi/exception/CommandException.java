package me.idarkyy.commandapi.exception;

/**
 * Thrown when an error occurs with the command
 */
public class CommandException extends RuntimeException {
    public CommandException(String name) {
        super(name);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public CommandException(String name, Throwable cause) {
        super(name, cause);
    }
}
