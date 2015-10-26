package com.github.kennedyoliveira.pastebin4j.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.github.kennedyoliveira.pastebin4j.api.StringUtils.isNotNullNorEmpty;
import static java.util.stream.Collectors.joining;

/**
 * @author kennedy
 */
class WebUtils {

    /**
     * User agent to be used for requests
     */
    private static final String USER_AGENT = "Mozilla/5.0";


    public static Optional<String> get(@NotNull String url, @NotNull Map<Object, Object> parameters) {
        try {
            final URL url1 = new URL(url + "?" + getPostParams(parameters));

            final HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("User-Agent", USER_AGENT);
            urlConnection.addRequestProperty("Accept", Locale.getDefault().getLanguage());
            urlConnection.setConnectTimeout(30000);

            final String s = doRequest(url, urlConnection, null);

            return Optional.of(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<String> post(@NotNull String url, @NotNull Map<Object, Object> parameters) {
        try {
            final URL url1 = new URL(url);

            final HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("User-Agent", USER_AGENT);
            urlConnection.addRequestProperty("Accept", Locale.getDefault().getLanguage());
            urlConnection.setConnectTimeout(30000);

            final String params = getPostParams(parameters);

            String result = doRequest(url, urlConnection, params);

            return Optional.of(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String doRequest(@NotNull String url, @NotNull HttpURLConnection urlConnection, @Nullable String params) throws IOException {
        if (isNotNullNorEmpty(params)) {
            urlConnection.setDoOutput(true);

            try (DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream())) {
                dataOutputStream.writeBytes(params);
            }
        }

        final int responseCode = urlConnection.getResponseCode();

        if (responseCode != 200)
            throw new RuntimeException(String.format("Error posting to %s using the params: %s", url, params));

        String result;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            result = br.lines().collect(joining());
        }

        return result;
    }

    /**
     * Creates a String from the Map to be used with parameters in post requests.
     *
     * @param parameters Map with parameters.
     * @return A string encoded for post requests.
     */
    static String getPostParams(@NotNull Map<Object, Object> parameters) {
        return parameters.entrySet()
                         .stream()
                         .map(e -> String.format("%s=%s", encodeUTF8(e.getKey().toString()), encodeUTF8(e.getValue().toString())))
                         .collect(joining("&"));
    }

    /**
     * Encodes the string as UTF url Parameter
     *
     * @param string String to be encoded
     * @return the string encoded.
     */
    static String encodeUTF8(@NotNull String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
