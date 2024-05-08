package com.example.reporting.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProfileMethod {

    enum Level {

        INFO, TRACE, DEBUG, ERROR

    }

    String tag() default "";

    Level level() default Level.INFO;

    boolean enabled() default true;
}