package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

import static com.github.kennedyoliveira.pastebin4j.api.ResponseUtils.requiresValidResponse;
import static com.github.kennedyoliveira.pastebin4j.api.StringUtils.isNotNullNorEmpty;
import static com.github.kennedyoliveira.pastebin4j.api.StringUtils.isNullOrEmpty;
import static com.github.kennedyoliveira.pastebin4j.api.WebUtils.get;
import static com.github.kennedyoliveira.pastebin4j.api.WebUtils.post;

/**
 * Implementation of all the PasteBin exposed features.
 *
 * @author kennedy
 * @since 1.0.0
 */
public class PasteBinApiImpl implements PasteBinApi {

    // Curried api post url function
    private Function<Map<Object, Object>, Optional<String>> postFunc = params -> WebUtils.post(PasteBinApiUrls.API_POST_URL,
                                                                                               params);

    /**
     * Function to help reduce verbosity of the simple methods.
     *
     * @param accountCredentials Credentials to use.
     * @param option             The option being called
     * @param function           A function to apply
     * @param <R>                The type of the return
     * @return The result of a api method.
     */
    private <R> R doPost(AccountCredentials accountCredentials, PasteBinApiOptions option, Function<Map<Object, Object>, R> function) {
        Map<Object, Object> params = new HashMap<>();

        if (accountCredentials != null)
            params.put(PasteBinApiParams.DEV_KEY, accountCredentials.getDevKey());

        params.put(PasteBinApiParams.OPTION, option);

        return function.apply(params);
    }

    @Override
    public List<Paste> listTrends(@NotNull AccountCredentials accountCredentials) {
        Objects.requireNonNull(accountCredentials);

        return doPost(accountCredentials,
                      PasteBinApiOptions.TRENDS,
                      params -> postFunc.apply(params)
                                        .map(r -> String.format("<pastes>%s</pastes>", r))
                                        .map(r -> XMLUtils.unMarshal(r, Pastes.class))
                                        .map(Pastes::getPastes)
                                        .map(PasteInfoUpdater::updateDate)
                                        .orElse(Collections.emptyList()));
    }

    @Override
    public String fetchUserKey(@NotNull AccountCredentials accountCredentials) {
        Objects.requireNonNull(accountCredentials);

        if (!accountCredentials.getUserName().isPresent())
            throw new IllegalArgumentException("Missing username!");

        if (!accountCredentials.getPassword().isPresent())
            throw new IllegalArgumentException("Missing password!");

        Map<Object, Object> parameters = new HashMap<>();

        parameters.put(PasteBinApiParams.DEV_KEY, accountCredentials.getDevKey());
        parameters.put(PasteBinApiParams.USER_NAME, accountCredentials.getUserName().get());
        parameters.put(PasteBinApiParams.USER_PASSWORD, accountCredentials.getPassword().get());

        return requiresValidResponse(post(PasteBinApiUrls.API_LOGIN_URL, parameters))
                .orElseThrow(() -> new RuntimeException("Error while fething user session key."));
    }

    @Override
    public UserInformation fetchUserInformation(@NotNull AccountCredentials accountCredentials) {
        Objects.requireNonNull(accountCredentials);

        updateUserSessionKey(accountCredentials);

        return doPost(accountCredentials,
                      PasteBinApiOptions.USER_DETAILS,
                      params -> {
                          final String userSessionKey = accountCredentials.getUserSessionKey()
                                                                          .orElseThrow(() -> new RuntimeException(
                                                                                  "To fetch user information you need an User Key, please, fetch the user key first!"));

                          params.put(PasteBinApiParams.USER_KEY, userSessionKey);

                          final Optional<UserInformation> userInformation = requiresValidResponse(post(PasteBinApiUrls.API_POST_URL,
                                                                                                       params))
                                  .map(r -> XMLUtils.unMarshal(r, UserInformation.class));

                          if (userInformation.isPresent())
                              return userInformation.get();

                          throw new RuntimeException("Could not fetch User information.");
                      });
    }

    @Override
    public String createPaste(@NotNull AccountCredentials accountCredentials, @NotNull Paste paste) {
        Objects.requireNonNull(accountCredentials);
        Objects.requireNonNull(paste, "The paste can't be null, are you kidding?");
        Objects.requireNonNull(paste.getContent(), "The paste need to have some Content");

        final boolean isGuestPaste = paste instanceof GuestPaste;

        if (accountCredentials.getUserName().isPresent() && accountCredentials.getPassword().isPresent() && !isGuestPaste)
            updateUserSessionKey(accountCredentials);

        Map<Object, Object> parameters = new HashMap<>();

        //Required
        parameters.put(PasteBinApiParams.DEV_KEY, accountCredentials.getDevKey());
        parameters.put(PasteBinApiParams.OPTION, PasteBinApiOptions.PASTE);
        parameters.put(PasteBinApiParams.PASTE_CODE, paste.getContent());

        // Optionals
        // WHen is a guest paste, i don't use the api user key
        if (!isGuestPaste) {
            accountCredentials.getUserSessionKey().ifPresent(k -> parameters.put(PasteBinApiParams.USER_KEY, k));
        }

        if (isNotNullNorEmpty(paste.getTitle()))
            parameters.put(PasteBinApiParams.PASTE_NAME, paste.getTitle());

        if (paste.getHighLight() != null)
            parameters.put(PasteBinApiParams.PASTE_FORMAT, paste.getHighLight());

        if (paste.getVisibility() != null)
            parameters.put(PasteBinApiParams.PASTE_PRIVATE, paste.getVisibility());

        if (paste.getExpiration() != null)
            parameters.put(PasteBinApiParams.PASTE_EXPIRE_DATE, paste.getExpiration());

        final String pasteUrl = requiresValidResponse(post(PasteBinApiUrls.API_POST_URL, parameters)).get();

        // Update the paste key from the URL to the paste
        paste.setKey(PasteUtil.getPasteKeyFromUrl(pasteUrl));
        paste.setUrl(pasteUrl);

        return pasteUrl;
    }

    @Override
    public List<Paste> listUserPastes(@NotNull AccountCredentials accountCredentials) {
        return listUserPastes(accountCredentials, 50);
    }

    @Override
    public List<Paste> listUserPastes(@NotNull AccountCredentials accountCredentials, int limit) {
        Objects.requireNonNull(accountCredentials);

        updateUserSessionKey(accountCredentials);

        accountCredentials.getUserSessionKey().orElseThrow(() -> new NullPointerException(
                "To list pastes you need an User Key, please, fetch the user key first!"));

        return doPost(accountCredentials,
                      PasteBinApiOptions.LIST,
                      params -> {
                          params.put(PasteBinApiParams.USER_KEY, accountCredentials.getUserSessionKey().get());
                          params.put(PasteBinApiParams.LIST_RESULT_LIMIT, limit);

                          return requiresValidResponse(post(PasteBinApiUrls.API_POST_URL, params))
                                  .map(r -> String.format("<pastes>%s</pastes>", r))
                                  .map(r -> XMLUtils.unMarshal(r, Pastes.class))
                                  .map(Pastes::getPastes)
                                  .map(PasteInfoUpdater::updateDate)
                                  .orElse(Collections.emptyList());
                      });
    }

    @Override
    public boolean deletePaste(@NotNull AccountCredentials accountCredentials, @NotNull Paste paste) {
        Objects.requireNonNull(paste);
        Objects.requireNonNull(paste.getKey(), "The key to the paste being deleted can't be null!");

        return deletePaste(accountCredentials, paste.getKey());
    }

    @Override
    public boolean deletePaste(@NotNull AccountCredentials accountCredentials, @NotNull String pasteKey) {
        Objects.requireNonNull(accountCredentials);

        if (isNullOrEmpty(pasteKey))
            throw new NullPointerException("The paste key can't be null!");

        accountCredentials.getUserSessionKey()
                          .orElseThrow(() -> new RuntimeException(
                                  "To delete a key you need a user session key, please, fetch the user session key first!"));

        return doPost(accountCredentials, PasteBinApiOptions.DELETE,
                      params -> {
                          params.put(PasteBinApiParams.USER_KEY, accountCredentials.getUserSessionKey().get());
                          params.put(PasteBinApiParams.UNIQUE_PASTE_KEY, pasteKey);

                          return requiresValidResponse(post(PasteBinApiUrls.API_POST_URL, params))
                                  .filter("Paste Removed"::equalsIgnoreCase)
                                  .isPresent();
                      });
    }

    @Override
    public String getPasteContent(@NotNull Paste paste) {
        Objects.requireNonNull(paste);

        if (paste.getVisibility() == PasteVisibility.PRIVATE) {
            throw new IllegalStateException("Getting private paste content not supported.");
        }

        Map<Object, Object> params = new HashMap<>();

        params.put("i", paste.getKey());

        return requiresValidResponse(get(PasteBinApiUrls.PASTE_RAW_URL, params, true)).get();
    }

    @Override
    public String getPasteContent(@NotNull AccountCredentials accountCredentials, @NotNull Paste paste) {
        Objects.requireNonNull(paste);

        if (paste.getVisibility() == PasteVisibility.PRIVATE) {
            return doPost(accountCredentials, PasteBinApiOptions.SHOW_PASTE, params -> {
                params.put(PasteBinApiParams.USER_KEY, accountCredentials.getUserSessionKey().get());
                params.put(PasteBinApiParams.UNIQUE_PASTE_KEY, paste.getKey().toUpperCase());

                return requiresValidResponse(post(PasteBinApiUrls.API_PASTE_CONTENT_URL, params)).get();
            });
        } else {
            return getPasteContent(paste);
        }
    }

    public void updateUserSessionKey(@NotNull AccountCredentials accountCredentials) {
        // If there's no key, i try to fetch one
        if (!accountCredentials.getUserSessionKey().isPresent())
            accountCredentials.setUserKey(Optional.ofNullable(fetchUserKey(accountCredentials)));
    }
}
