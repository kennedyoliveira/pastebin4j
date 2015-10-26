package com.github.kennedyoliveira.pastebin4j.api;

import java.util.Optional;

/**
 * Utility methods for checking response validity
 *
 * @author kennedy
 */
class ResponseUtils {

    private ResponseUtils() {}

    public static void validateResponse(Optional<String> response) {
        if (!response.isPresent())
            throw new RuntimeException("Empty response");

        final String resp = response.get();

        if (resp.toLowerCase().contains("bad api request"))
            throw new RuntimeException(("Error: " + resp));
    }

    public static Optional<String> requiresValidResponse(Optional<String> response) {
        validateResponse(response);
        return response;
    }
}
