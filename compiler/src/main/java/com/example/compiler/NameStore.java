package com.example.compiler;

public class NameStore {
    public static final String GENERATED_CLASS_SUFFIX = "$MyBinding";

    public static String getGeneratedClassName(String clsName) {
        return clsName + GENERATED_CLASS_SUFFIX;
    }
}
