package com.github.kennedyoliveira.pastebin4j;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Represents a experation for a {@link Paste}</p>
 *
 * @author kennedy
 * @since 1.0.0
 */
@XmlEnum
public enum PasteExpiration {

    @XmlEnumValue("N")NEVER("N", 0L),
    @XmlEnumValue("10M")TEN_MINUTES("10M", 10L * 60),
    @XmlEnumValue("1H")ONE_HOUR("1H", 60L * 60),
    @XmlEnumValue("1D")ONE_DAY("1D", 24L * 60 * 60),
    @XmlEnumValue("1W")ONE_WEEK("1W", 7L * 24 * 60 * 60),
    @XmlEnumValue("2W")TWO_WEEKS("2W", 14L * 24 * 60 * 60),
    @XmlEnumValue("1m")ONE_MONTH("1M", -1L);

    private final String expiration;
    private final long seconds;

    private final static Map<Long, PasteExpiration> cacheBySeconds;

    static {
        cacheBySeconds = new HashMap<>();

        for (PasteExpiration pasteExpiration : PasteExpiration.values()) {
            cacheBySeconds.put(pasteExpiration.seconds, pasteExpiration);
        }
    }

    PasteExpiration(String expiration, long seconds) {
        this.expiration = expiration;
        this.seconds = seconds;
    }

    /**
     * Returns a {@link PasteExpiration} based in a total of seconds
     *
     * @param seconds seconds
     * @return a Expiration related to the secs or {@link #ONE_MONTH} if not was found.
     */
    public static PasteExpiration getBySeconds(long seconds) {
        return cacheBySeconds.getOrDefault(seconds, ONE_MONTH);
    }

    @Override
    public String toString() {
        return expiration;
    }
}
