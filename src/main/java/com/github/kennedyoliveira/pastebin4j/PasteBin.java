package com.github.kennedyoliveira.pastebin4j;

import com.github.kennedyoliveira.pastebin4j.api.PasteBinApi;
import com.github.kennedyoliveira.pastebin4j.api.PasteBinApiFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Core class to interact with the PasteBin API.</p>
 * <p>All the interactions can be made by this class.</p>
 *
 * @author kennedy
 * @since 1.0.0
 */
public class PasteBin {

    private final AccountCredentials accountCredentials;
    private Optional<UserInformation> user;
    private PasteBinApi pasteBinApi;

    /**
     * Creates a new {@link PasteBin} object for interacting with PasteBin API.
     *
     * @param accountCredentials Credentials for acessing the API.
     */
    public PasteBin(@NotNull AccountCredentials accountCredentials) {
        this(accountCredentials, PasteBinApiFactory.createDefaultImplementation());
    }

    /**
     * Creates a new {@link PasteBin} object for interacting with PasteBin API, using a custom implementation for the API.
     *
     * @param accountCredentials Credentials for acessing the API.
     * @param pasteBinApi        The custom implemenation for the API, it can't be null.
     */
    public PasteBin(@NotNull AccountCredentials accountCredentials, @NotNull PasteBinApi pasteBinApi) {
        Objects.requireNonNull(accountCredentials);
        Objects.requireNonNull(pasteBinApi);

        this.accountCredentials = accountCredentials;
        this.pasteBinApi = pasteBinApi;
    }

    /**
     * @return The account credentials currently used.
     */
    public AccountCredentials getAccountCredentials() {
        return accountCredentials;
    }

    /**
     * <p>The user information</p>
     * <p>This information will be available only after you call {@link #fetchUserInformation()} or
     * any method that need the userInformation for interacting with the api, for example:</p>
     * <p>{@link #createPaste(Paste)}, {@link #listUserPastes()}</p>
     * <p>Note: The {@link UserInformation} just will be fetched if {@link AccountCredentials#userName} and {@link AccountCredentials#password} are valids.</p>
     *
     * @return The user information
     */
    public Optional<UserInformation> getUserInformation() {
        return user;
    }

    /**
     * <p>Fetch the user information using the {@link AccountCredentials#userName} and {@link AccountCredentials#password}.</p>
     * <p>After this method is called and returned with success you can acess the userInformation calling the method {@link #getUserInformation()}</p>
     *
     * @return the user information.
     */
    public UserInformation fetchUserInformation() {
        this.user = Optional.ofNullable(pasteBinApi.fetchUserInformation(accountCredentials));
        return this.user.orElseThrow(() -> new RuntimeException("Couldn't fetch the user information :("));
    }

    /**
     * <p>Creates a new Paste and return the link to the created paste.</p>
     * <p>To make easy to create pastes, you can you se {@link Paste#newBuilder()}.</p>
     *
     * @param paste The paste to be created.
     * @return The link for the created paste.
     * @throws NullPointerException if the paste is null or {@link Paste#content} is null.
     */
    public String createPaste(@NotNull Paste paste) {
        return pasteBinApi.createPaste(accountCredentials, paste);
    }

    /**
     * <p>List 50 user pastes.</p>
     * <p>To list more than 50 you can use the method {@link #listUserPastes(int)} and pass the limit up to 1000 pastes.</p>
     *
     * @return A list of pastes.
     */
    @NotNull
    public List<Paste> listUserPastes() {
        return pasteBinApi.listUserPastes(accountCredentials);
    }

    /**
     * <p>List pastes up to the number especified in the {@code limit} parameter.</p>
     *
     * @param limit Maximum pastes to list.
     * @return A list of pastes.
     */
    @NotNull
    public List<Paste> listUserPastes(int limit) {
        return pasteBinApi.listUserPastes(accountCredentials, limit);
    }

    /**
     * <p>Delete a paste</p>
     * <p>The current implementation uses the {@link Paste#key} to delete.</p>
     *
     * @param paste Paste to be deleted.
     * @return {@code True} if it was deleted, false otherwise.
     * @throws NullPointerException if the {@code paste} or {@link Paste#key} is null
     */
    public boolean deletePaste(@NotNull Paste paste) {
        return pasteBinApi.deletePaste(accountCredentials, paste);
    }

    /**
     * @return List the trending pastes.
     */
    public List<Paste> listTrendingPastes() {
        return pasteBinApi.listTrends(accountCredentials);
    }

    /**
     * <p>Fetchs the content of a paste.</p>
     * <p>The currently implementation only fetchs content of pastes {@link PasteVisibility#PUBLIC} or {@link PasteVisibility#UNLISTED}.</p>
     *
     * @param paste Paste to have the contents fetched.
     * @return The content of the paste.
     * @throws NullPointerException if the {@code paste} is null or if {@link Paste#visibility} is {@link PasteVisibility#PRIVATE}
     */
    public String getPasteContent(@NotNull Paste paste) {
        return pasteBinApi.getPasteContent(paste);
    }
}
