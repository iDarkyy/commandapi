package me.idarkyy.commandapi.annotations;

import me.idarkyy.commandapi.flags.SenderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Usable by annotation
 * Specify which type(s) of command sender can use
 *  the command or sub-command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UsableBy {
    SenderType value();
}
