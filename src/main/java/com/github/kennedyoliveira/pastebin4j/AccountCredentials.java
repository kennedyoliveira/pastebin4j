package com.github.kennedyoliveira.pastebin4j;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents the account credentials for logging into the API and fetch informations.
 *
 * @author kennedy
 * @since 1.0.0
 */
public class AccountCredentials {

    private String devKey;
    private Optional<String> userName;
    private Optional<String> password;
    private Optional<String> userKey;

    /**
     * <p>This constructor without the {@link #userName} and {@link #password} is limited about the interaction with the API.</p>
     * <p>Using it you can only list trends pastes and create guest pastes, the other actions needs the {@link #userName} and {@link #password}.</p>
     *
     * @param devKey API dev key, check the API documentation to see how to obtain one. <a href="http://pastebin.com/api#1">http://pastebin.com/api#1</a>
     */
    public AccountCredentials(@NotNull String devKey) {
        this(devKey, null, null);
    }

    /**
     * <p>Setup the credentials for logging into the API using the {@code username} and {@code password} provided, this enables
     * to use all the features provided be the PasteBin API.</p>
     *
     * @param devKey   API dev key, check the API documentation to see how to obtain one. <a href="http://pastebin.com/api#1">http://pastebin.com/api#1</a>
     * @param userName Username to log into the API.
     * @param password Password to log into the API.
     */
    public AccountCredentials(@NotNull String devKey, @Nullable String userName, @Nullable String password) {
        Objects.requireNonNull(devKey, " The dev key cannot be null!");
        this.devKey = devKey;
        this.password = Optional.ofNullable(password);
        this.userName = Optional.ofNullable(userName);
        this.userKey = Optional.empty();
    }

    /**
     * @return API dev key, check the API documentation to see how to obtain one. <a href="http://pastebin.com/api#1">http://pastebin.com/api#1</a>
     */
    public String getDevKey() {
        return devKey;
    }

    /**
     * @return The password to log into the API.
     */
    public Optional<String> getPassword() {
        return password;
    }

    /**
     * @return The username to log into the api.
     */
    public Optional<String> getUserName() {
        return userName;
    }

    /**
     * <p>this field hold the user session key after it was fetched by {@link PasteBin#fetchUserInformation()}</p>
     *
     * @return The user session key
     */
    public Optional<String> getUserSessionKey() {
        return userKey;
    }

    /**
     * <p>Sets the new user session key</p>
     * <p>This method is used by {@link PasteBin#fetchUserInformation()} to sets the fetched key, it shouldn't be used by clients!</p>
     *
     * @param userKey The new user session key.
     */
    public void setUserKey(Optional<String> userKey) {
        this.userKey = userKey;
    }
}
