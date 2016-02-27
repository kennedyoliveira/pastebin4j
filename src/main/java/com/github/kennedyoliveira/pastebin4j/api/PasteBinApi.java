package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.AccountCredentials;
import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.UserInformation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the currently paste bin api exposed methods.
 *
 * @author kennedy
 */
public interface PasteBinApi {

    /**
     * List the currently trending pastes;
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @return A list of pastes.
     * @throws NullPointerException if the {@code accountCredentials} is null
     */
    List<Paste> listTrends(@NotNull AccountCredentials accountCredentials);

    /**
     * Fetchs a new User Session Key, this key is used to identify a user when creating, deleting ou listing pastes.
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @return The new User Session Key created by the API.
     * @throws NullPointerException     if the {@code accountCredentials} is null
     * @throws IllegalArgumentException if {@link AccountCredentials#getUserName()} or {@link AccountCredentials#getPassword()} is null
     */
    String fetchUserKey(@NotNull AccountCredentials accountCredentials);

    /**
     * <p>Log into the api using the {@link AccountCredentials#userName} and {@link AccountCredentials#password} and
     * fetch the user information, currently the support is only to fetch, you can't modify.</p>
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @return The user information.
     * @throws NullPointerException if the {@code accountCredentials} is null
     */
    UserInformation fetchUserInformation(@NotNull AccountCredentials accountCredentials);

    /**
     * Creates a new paste and return the url for this created paste.
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @param paste              The paste holding the information to be used in creating process.
     * @return A url for the created paste.
     * @throws NullPointerException if the {@code accountCredentials} is null
     */
    String createPaste(@NotNull AccountCredentials accountCredentials, @NotNull Paste paste);

    /**
     * <p>Lists up to 50 pastes created by the user.</p>
     * <p>The user is the one represented by the {@link AccountCredentials#userKey} that is fetched using a {@code username} and {@code password}</p>
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @return A list of pastes created by the user.
     * @throws NullPointerException if the {@code accountCredentials} is null
     */
    List<Paste> listUserPastes(@NotNull AccountCredentials accountCredentials);

    /**
     * <p>Lists user pastes up to the amount especified in the {@code limit} parameter.</p>
     * <p>The user is the one represented by the {@link AccountCredentials#userKey} that is fetched using a {@code username} and {@code password}</p>
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @param limit              Limit up to 1000 pastes to fetch.
     * @return A list of pastes fetched.
     * @throws NullPointerException if the {@code accountCredentials} is null
     */
    List<Paste> listUserPastes(@NotNull AccountCredentials accountCredentials, int limit);

    /**
     * <p>Deletes a paste from {@code PasteBin}</p>
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @param paste              The paste to be deleted.
     * @return {@code true} if delete is succefully complete, false otherwise
     * @throws NullPointerException if the {@code accountCredentials} is null
     */
    boolean deletePaste(@NotNull AccountCredentials accountCredentials, @NotNull Paste paste);

    /**
     * <p>Deletes a paste from {@code PasteBin}, this method uses a {@code unique key} from a paste to delete it.</p>
     * <p>This key is the {@link Paste#key} that is fetched from the site.</p>
     *
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @param pasteKey           The unique key of a paste.
     * @return {@code true} if delete is succefully complete, false otherwise
     * @throws NullPointerException if the {@code accountCredentials} is null or if the {@code pasteKey} is null
     */
    boolean deletePaste(@NotNull AccountCredentials accountCredentials, @NotNull String pasteKey);

    /**
     * <p>Fetch the online contents of a paste. This method only fetch contents of a {@code PUBLIC} or {@code UNLISTED} paste,
     * to fetch a private paste use {@link #getPasteContent(AccountCredentials, Paste)}</p>
     *
     * @param paste The paste to fetch contents.
     * @return The content of the paste.
     * @throws NullPointerException if the {@code paste} is null or {@link Paste#key}
     */
    String getPasteContent(@NotNull Paste paste);

    /**
     * <p>Fetch the online contents of a paste including {@code PRIVATE} pastes.</p>
     *
     * @param paste              The paste to fetch contents.
     * @param accountCredentials Account credentials to be used to log into the paste bin API.
     * @return The content of the paste.
     * @throws NullPointerException if the {@code paste} is null or {@link Paste#key}
     */
    String getPasteContent(@NotNull AccountCredentials accountCredentials, @NotNull Paste paste);
}
