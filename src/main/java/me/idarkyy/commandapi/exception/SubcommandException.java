package me.idarkyy.commandapi.exception;

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
