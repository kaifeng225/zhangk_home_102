package com.example.springboot.freemarker.templateengine;

/**
 * Extract a Key from obj to make a Key-Value(obj) pair
 */
@FunctionalInterface
public interface KeyExtractor<O, K> {
    K extractKey(O obj);
}
