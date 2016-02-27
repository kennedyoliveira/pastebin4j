package com.github.kennedyoliveira.pastebin4j.api;

import java.util.Optional;

/**
 * Utility methods for checking response validity
 *
 * @author kennedy
 */
class ResponseUtils {

    private ResponseUtils() {}

    /**
     * <p>Validates the response of a request to the api, if it has no response or contains (bad_api_request) means it failed.</p>
     *
     * @param response the response.
     */
    public static void validateResponse(Optional<String> response) {
        if (response == null || !response.isPresent())
            throw new RuntimeException("Empty response");

        final String resp = response.get();

        if (resp.toLowerCase().contains("bad api request,"))
            throw new RuntimeException(("Error: " + resp));
    }

    /**
     * <p>Validates the exception and returns the response if it's OK.</p>
     * <p>This methods helps to use as functional style</p>
     *
     * @param response The response.
     * @return The response if its valid, the same object.
     */
    public static Optional<String> requiresValidResponse(Optional<String> response) {
        validateResponse(response);
        return response;
    }
}
