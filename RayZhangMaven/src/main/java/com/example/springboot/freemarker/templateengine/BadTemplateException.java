package com.example.springboot.freemarker.templateengine;

/**
 * Mostly represents violation to template grammar.
 */
public class BadTemplateException extends RuntimeException {
    private final transient String template;

    public BadTemplateException(String message, String template, Throwable cause) {
        super(message, cause);
        this.template = template;
    }

    @Override
    public String toString() {
        return "BadTemplateException: " + getMessage() + "{" + "template='" + template + '\'' + '}';
    }
}
