package me.idarkyy.commandapi.exception;

/**
 * Thrown when an error occurs while registering the command
 */
public class RegisterException extends RuntimeException {
    public RegisterException(String name) {
        super(name);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }

    public RegisterException(String name, Throwable cause) {
        super(name, cause);
    }
}
