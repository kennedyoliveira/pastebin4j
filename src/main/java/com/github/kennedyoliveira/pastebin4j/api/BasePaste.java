package com.github.kennedyoliveira.pastebin4j.api;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author kennedy
 */
public class BasePaste {
    /**
     * Date when the paste was created.
     */
    protected LocalDateTime pasteDate;
    /**
     * Date when the paste was created related to the current local zone.
     */
    protected ZonedDateTime localPasteDate;
    /**
     * Date when the paste will expire.
     */
    protected LocalDateTime expirationDate;
    /**
     * Date when the paste will expire, related to the current local zone.
     */
    protected ZonedDateTime localExpirationDate;
    /**
     * Timestamp when the paste was created, this field is used for generating the {@link #pasteDate}.
     */
    @XmlElement(name = "paste_date")
    private long dateTimestamp;

    /**
     * Timestamp when the past will expire, this field is used for generating the
     */
    @XmlElement(name = "paste_expire_date")
    private long expireDateTimestamp;

    long getDateTimestamp() {
        return dateTimestamp;
    }

    long getExpireDateTimestamp() {
        return expireDateTimestamp;
    }

    void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    void setExpireDateTimestamp(long expireDateTimestamp) {
        this.expireDateTimestamp = expireDateTimestamp;
    }

    void setLocalExpirationDate(ZonedDateTime localExpirationDate) {
        this.localExpirationDate = localExpirationDate;
    }

    void setLocalPasteDate(ZonedDateTime localPasteDate) {
        this.localPasteDate = localPasteDate;
    }

    void setPasteDate(LocalDateTime pasteDate) {
        this.pasteDate = pasteDate;
    }
}
