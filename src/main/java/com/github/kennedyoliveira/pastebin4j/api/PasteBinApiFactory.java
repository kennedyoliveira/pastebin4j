package com.github.kennedyoliveira.pastebin4j.api;

/**
 * Creates implementations of {@link PasteBinApi}
 *
 * @author kennedy
 * @since 1.0.0
 */
public class PasteBinApiFactory {

    private static PasteBinApi defaultApi;

    private PasteBinApiFactory() {
    }

    /**
     * <p>Creates a default implementation for the API.</p>
     * <p>Since the Implementation is Stateless this method returns the same implementation everytime its called.</p>
     *
     * @return Default api implementation.
     */
    public static PasteBinApi createDefaultImplementation() {
        if (defaultApi == null)
            defaultApi = new PasteBinApiImpl();

        return defaultApi;
    }
}
