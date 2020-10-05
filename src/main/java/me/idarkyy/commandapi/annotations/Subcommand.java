package me.idarkyy.commandapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Subcommand annotation
 * Use to specify which Method or class is a sub-command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Subcommand {
    /**
     * Sub-command name
     * The value can be split with character '|' to
     *  allow multiple names (or aliases)
     */
    String value();
}
