package com.example.springboot.freemarker.templateengine;

import freemarker.template.Template;

@FunctionalInterface
public interface TemplateMaker {
    Template makeTemplate(String templateStr);
}
