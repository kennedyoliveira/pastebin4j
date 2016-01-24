package com.github.kennedyoliveira.pastebin4j.api;

import org.jetbrains.annotations.Nullable;

/**
 * Utility class for extracting information from pastes
 *
 * @author Kennedy Oliveira
 */
class PasteUtil {

    /**
     * @deprecated Utility Class
     */
    private PasteUtil() {}

    /**
     * Get the key from a paste url.
     *
     * @param url Paste URL.
     * @return The paste Key.
     * @throws IllegalArgumentException if the {@code url} is not a valid pastebin url.
     */
    public static String getPasteKeyFromUrl(@Nullable String url) {
        if (url == null)
            return null;

        if (!url.contains("http://pastebin.com/")) {
            throw new IllegalArgumentException("Not a valid paste bin url!");
        }

        return url.substring(url.indexOf("http://pastebin.com/") + 20);
    }
}
