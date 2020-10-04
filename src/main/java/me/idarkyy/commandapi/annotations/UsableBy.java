package me.idarkyy.commandapi.annotations;

import me.idarkyy.commandapi.flags.SenderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UsableBy {
    SenderType value();
}
