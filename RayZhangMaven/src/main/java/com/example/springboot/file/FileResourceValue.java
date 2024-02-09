package com.example.springboot.file;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotate a field whose value can be loaded from a file.
 * ONLY support String.
 *
 * @author Robyn Liu
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface FileResourceValue {
}