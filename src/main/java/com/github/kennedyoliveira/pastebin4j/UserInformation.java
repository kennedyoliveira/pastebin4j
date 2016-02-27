package com.github.kennedyoliveira.pastebin4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>User information fetched from the API.</p>
 * <p>This class is imutable, and only represents the information fetched.</p>
 * <p>Currently the {@code pastebin api} doens't support altering this information, that why this class is immutable</p>
 *
 * @author kennedy
 * @since 1.0.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInformation {

    @XmlElement(name = "user_name")
    private String username;

    @XmlElement(name = "user_expiration")
    private PasteExpiration defaultPasteExpiration;

    @XmlElement(name = "user_avatar_url")
    private String userAvatarUrl;

    @XmlElement(name = "user_private")
    private PasteVisibility defaultPasteVisility;

    @XmlElement(name = "user_website")
    private String websiteUrl;

    @XmlElement(name = "user_email")
    private String email;

    @XmlElement(name = "user_location")
    private String location;

    @XmlElement(name = "user_account_type")
    private AccountType accountType;

    @XmlElement(name = "user_format_short")
    private PasteHighLight defaultHighLight;

    /**
     * @return The type of the user account.
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @return The default paste expiration configurated by the user.
     */
    public PasteExpiration getDefaultPasteExpiration() {
        return defaultPasteExpiration;
    }

    /**
     * @return The default paste expiration configurated by the user.
     */
    public PasteVisibility getDefaultPasteVisility() {
        return defaultPasteVisility;
    }

    /**
     * @return User's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return User's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return User's avatar URL.
     */
    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    /**
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return URL for the user's website.
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * @return The default syntax highlight for the user.
     */
    public PasteHighLight getDefaultHighLight() {
        return defaultHighLight;
    }
}