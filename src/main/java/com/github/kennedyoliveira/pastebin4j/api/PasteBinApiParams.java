package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.*;

/**
 * <p>Possible params for the API.</p>
 *
 * @author kennedy
 */
enum PasteBinApiParams {

    /**
     * API dev key, check the API documentation to see how to obtain one. <a href="http://pastebin.com/api#1">http://pastebin.com/api#1</a>.
     */
    DEV_KEY("api_dev_key"),
    /**
     * The contents of a {@link Paste}.
     *
     * @see Paste#content
     */
    PASTE_CODE("api_paste_code"),
    /**
     * The visibility of the paste.
     *
     * @see PasteVisibility
     * @see Paste#setVisibility(PasteVisibility)
     */
    PASTE_PRIVATE("api_paste_private"),
    /**
     * The title of the paste.
     *
     * @see Paste#title
     */
    PASTE_NAME("api_paste_name"),
    /**
     * Expiration time of the paste.
     *
     * @see PasteExpiration
     * @see Paste#setExpiration(PasteExpiration)
     */
    PASTE_EXPIRE_DATE("api_paste_expire_date"),
    /**
     * The sintax highligh for the paste.
     *
     * @see PasteHighLight
     * @see Paste#setHighLight(PasteHighLight)
     */
    PASTE_FORMAT("api_paste_format"),
    /**
     * The current Session User Key.
     *
     * @see AccountCredentials#userKey
     */
    USER_KEY("api_user_key"),
    /**
     * The options supported by the API
     *
     * @see PasteBinApiOptions
     */
    OPTION("api_option"),
    /**
     * Username to fetch a user session key
     */
    USER_NAME("api_user_name"),
    /**
     * Password to fetch a user session key
     */
    USER_PASSWORD("api_user_password"),
    /**
     * Limit for listing a users pastes.
     */
    LIST_RESULT_LIMIT("api_results_limit"),
    /**
     * Unique key of a paste
     *
     * @see Paste#key
     */
    UNIQUE_PASTE_KEY("api_paste_key");

    private final String param;

    PasteBinApiParams(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }
}
