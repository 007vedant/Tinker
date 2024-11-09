package com.tinker.wctool.constants;

import java.util.List;

/**
 * Constants for the tool
 */
public class Constants {
    public static final String BYTES = "-c";
    public static final String LINES = "-l";
    public static final String WORDS = "-w";
    public static final String CHARS = "-m";
    public static final String ALL = "-a";

    public static final List<String> ACCEPTED_OPERATIONS = List.of(BYTES, LINES, WORDS, CHARS, ALL);
}
