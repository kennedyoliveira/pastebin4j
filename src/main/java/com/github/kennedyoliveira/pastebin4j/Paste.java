package com.github.kennedyoliveira.pastebin4j;

import com.github.kennedyoliveira.pastebin4j.api.BasePaste;
import com.github.kennedyoliveira.pastebin4j.api.PasteBinApiFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * <p>Represents a paste.</p>
 * <p>Can be used to create pastes and fetch remote content,
 * check {@link #paste(AccountCredentials)}, {@link #paste(PasteBin)} and {@link #getContent()}</p>
 *
 * @author kennedy
 * @since 1.0.0
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class Paste extends BasePaste {

    /**
     * Key to the Paste
     */
    @XmlElement(name = "paste_key")
    private String key;

    /**
     * Title of the paste
     */
    @XmlElement(name = "paste_title")
    private String title;

    /**
     * Size in bytes of the paste.
     */
    @XmlElement(name = "paste_size")
    private long size;

    /**
     * URL for acessing the paste.
     */
    @XmlElement(name = "paste_url")
    private String url;

    /**
     * How many times this paste was viewed.
     */
    @XmlElement(name = "paste_hits")
    private long hits;

    /**
     * Visibility of the paste.
     */
    @XmlElement(name = "paste_private")
    private PasteVisibility visibility;

    /**
     * How long to the paste expire.
     */
    private PasteExpiration expiration;

    /**
     * Sintax highlight.
     */
    @XmlElement(name = "paste_format_short")
    private PasteHighLight highLight;

    /**
     * The content
     */
    private String content;

    /**
     * <p>Creates an empty paste.</p>
     * <p>We recomend using the builder methods, see {@link #newBuilder()}</p>
     */
    public Paste() {
    }

    /**
     * <p>Creates a paste with an unique key, use this constructor when you wanna get a pastes content or delete a paste.</p>
     *
     * @param key Paste unique key
     * @throws NullPointerException if the {@code key} is null
     */
    public Paste(@NotNull String key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    /**
     * <p>Creates a completly paste ready to be uploaded</p>
     *
     * @param title      The title of the paste, can be null
     * @param content    The content of the paste, can't be null
     * @param highLight  The highlightm default is text, can be null
     * @param expiration The expiration, can be null
     * @param visibility The visibility, can be null
     * @throws NullPointerException if the {@code content} is null.
     */
    public Paste(@Nullable String title, @NotNull String content, @Nullable PasteHighLight highLight, @Nullable PasteExpiration expiration, @Nullable PasteVisibility visibility) {
        Objects.requireNonNull(content);

        this.title = title;
        this.content = content;
        this.expiration = expiration;
        this.visibility = visibility;
        this.highLight = highLight;
    }

    private Paste(Builder builder) {
        setTitle(builder.title);
        setContent(builder.content);
        setHighLight(builder.highLight);
        setExpiration(builder.expiration);
        setVisibility(builder.visibility);
    }

    public static Builder newBuilder() {return new Builder();}

    public static Builder newBuilder(Paste copy) {
        Builder builder = new Builder();
        builder.title = copy.title;
        builder.content = copy.content;
        builder.highLight = copy.highLight;
        builder.expiration = copy.expiration;
        builder.visibility = copy.visibility;
        return builder;
    }

    /**
     * @return How many times this paste was hit.
     */
    public long getHits() {
        return hits;
    }

    /**
     * @return The unique key of that paste.
     */
    public String getKey() {
        return key;
    }

    /**
     * <p>Sets a new unique key.</p>
     * <p>Beware, this information is fetched by the API, doens't set it manually unless you know what you are doing.</p>
     *
     * @param key the new key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return The size of the contents of this paste.
     */
    public long getSize() {
        return size;
    }

    /**
     * @return Paste's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for the paste.
     *
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The url to this paste.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return Visibility of the paste.
     */
    public PasteVisibility getVisibility() {
        return visibility;
    }

    /**
     * Sets a new visibility for this paste.
     *
     * @param visibility the new visibility.
     */
    public void setVisibility(PasteVisibility visibility) {
        this.visibility = visibility;
    }

    /**
     * @return The paste expiration
     */
    public PasteExpiration getExpiration() {
        return expiration;
    }

    /**
     * Sets a new expiration for this paste.
     *
     * @param expiration new expiration.
     */
    public void setExpiration(PasteExpiration expiration) {
        this.expiration = expiration;
    }


    /**
     * @return The current {@link PasteHighLight}.
     */
    public PasteHighLight getHighLight() {
        return highLight;
    }

    /**
     * Sets a new {@link PasteHighLight}
     *
     * @param highLight the new {@link PasteHighLight}
     */
    public void setHighLight(PasteHighLight highLight) {
        this.highLight = highLight;
    }

    /**
     * @return The paste content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets new contents for the paste.
     *
     * @param content new content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <p>Fetch the online contents of a paste.</p>
     * <p><b>Important Note:</b> This method will replace the {@link #content} with the fetched content!</p>
     *
     * @return the fetched content
     */
    public String fetchContent() {
        this.content = PasteBinApiFactory.createDefaultImplementation().getPasteContent(this);
        return content;
    }

    /**
     * <p>Shortcut method to create a paste using a {@link AccountCredentials}</p>
     *
     * @param accountCredentials The credentials to log in the api and create the paste.
     * @return A link to the paste.
     * @throws NullPointerException if the {@code accountCredentials} is null or {@link #content} is null.
     */
    public String paste(@NotNull AccountCredentials accountCredentials) {
        return PasteBinApiFactory.createDefaultImplementation().createPaste(accountCredentials, this);
    }

    /**
     * <p>Uses an already created {@link PasteBin} to post.</p>
     * <p>It's the same as doing {@link PasteBin#createPaste(Paste)} and passing the this paste.</p>
     *
     * @param pasteBin the {@link PasteBin} to be used to create the paste.
     * @return A link to the paste.
     */
    public String paste(@NotNull PasteBin pasteBin) {
        return pasteBin.createPaste(this);
    }

    /**
     * <p>Gets the date that this paste will expire, but doesn't consider the current locale</p>
     * <p>For a date considering the current locale, check the {@link #getLocalExpirationDate()}</p>
     *
     * @return The date that this paste will expire.
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public ZonedDateTime getLocalExpirationDate() {
        return localExpirationDate;
    }

    public ZonedDateTime getLocalPasteDate() {
        return localPasteDate;
    }

    /**
     * <p>Gets the creation date time of this paste, but doens't consider the current locale</p>
     * <p>For a date considering the current locale, check the {@link #getLocalPasteDate()} method.</p>
     *
     * @return Creation date time of this paste.
     */
    public LocalDateTime getPasteDate() {
        return pasteDate;
    }

    /**
     * {@code Paste} builder static inner class.
     */
    public static final class Builder {
        private String title;
        private String content;
        private PasteHighLight highLight;
        private PasteExpiration expiration;
        private PasteVisibility visibility;

        private Builder() {}

        /**
         * Sets the {@code title} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param title the {@code title} to set
         * @return a reference to this Builder
         */
        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the {@code content} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param content the {@code content} to set
         * @return a reference to this Builder
         */
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Sets the {@code highLight} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param highLight the {@code highLight} to set
         * @return a reference to this Builder
         */
        public Builder withHighLight(PasteHighLight highLight) {
            this.highLight = highLight;
            return this;
        }

        /**
         * Sets the {@code expiration} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param expiration the {@code expiration} to set
         * @return a reference to this Builder
         */
        public Builder withExpiration(PasteExpiration expiration) {
            this.expiration = expiration;
            return this;
        }

        /**
         * Sets the {@code visibility} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param visibility the {@code visibility} to set
         * @return a reference to this Builder
         */
        public Builder withVisibility(PasteVisibility visibility) {
            this.visibility = visibility;
            return this;
        }

        /**
         * Returns a {@code Paste} built from the parameters previously set.
         *
         * @return a {@code Paste} built with parameters of this {@code Paste.Builder}
         */
        public Paste build() {return new Paste(this);}
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paste{");
        sb.append("key='").append(key).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", highLight=").append(highLight);
        sb.append(", visibility=").append(visibility);
        sb.append(", expiration=").append(expiration);
        sb.append(", localExpirationDate=").append(getLocalExpirationDate());
        sb.append(", localPasteDate=").append(getLocalPasteDate());
        sb.append(", url='").append(url).append('\'');
        sb.append(", size=").append(size);
        sb.append(", hits=").append(hits);
        sb.append('}');
        return sb.toString();
    }
}
