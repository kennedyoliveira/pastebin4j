package com.github.kennedyoliveira.pastebin4j.api;

/**
 * Holds the URLs used by the API.
 *
 * @author kennedy
 * @since 1.0.0
 */
class PasteBinApiUrls {

    private PasteBinApiUrls(){}

    /**
     * URL for interacting with all the options from the API.
     */
    public static final String API_POST_URL = "http://pastebin.com/api/api_post.php";

    /**
     * URL For fetching a user session key
     */
    public static final String API_LOGIN_URL = "http://pastebin.com/api/api_login.php";

    /**
     * <p>This isn't for the API, but used to fetch raw paste contents.</p>
     * <p>Currently, it used a parameter {@code i} with the unique paste key to fetch the contents</p>
     */
    public static final String PASTE_RAW_URL = "http://pastebin.com/raw.php";
}
