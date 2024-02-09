package com.example.springboot.freemarker.templateengine;

import javax.annotation.Nullable;

public interface VendorTemplateResolver {
    String resolveTemplate(@Nullable String templateStr, Object properties);
}
