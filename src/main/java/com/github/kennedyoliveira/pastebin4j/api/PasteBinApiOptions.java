package com.github.kennedyoliveira.pastebin4j.api;

/**
 * Options for the API.
 *
 * @author kennedy
 */
enum PasteBinApiOptions {

    /**
     * Used to create a new paste.
     */
    PASTE("paste"),
    /**
     * Used to list a user pastes.
     */
    LIST("list"),
    /**
     * Used to list the trending pastes.
     */
    TRENDS("trends"),
    /**
     * Used to delete a user paste.
     */
    DELETE("delete"),
    /**
     * Used to get information about the logged user.
     */
    USER_DETAILS("userdetails");

    private final String option;

    PasteBinApiOptions(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}
