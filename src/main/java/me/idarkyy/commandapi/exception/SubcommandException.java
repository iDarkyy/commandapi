package me.idarkyy.commandapi.exception;

/**
 * Thrown when an error occurs while executing a sub-command
 */
public class SubcommandException extends RuntimeException {
    public SubcommandException(String name) {
        super(name);
    }

    public SubcommandException(Throwable cause) {
        super(cause);
    }

    public SubcommandException(String name, Throwable cause) {
        super(name, cause);
    }
}
