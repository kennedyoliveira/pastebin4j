package com.github.kennedyoliveira.pastebin4j.api;

/**
 * @author kennedy
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * Checks if the String is null or empty.
     *
     * @param s String
     * @return {@code true} if String is null or has 0 length.
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * Checks if the string is not null nor it's empty.
     *
     * @param s String
     * @return {@code true} if String is not null AND not empty.
     */
    public static boolean isNotNullNorEmpty(String s) {
        return s != null && s.length() > 0;
    }
}
