package com.github.kennedyoliveira.pastebin4j;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * The possible visibility that a {@link Paste} can has.
 *
 * @author kennedy
 * @since 1.0.0
 */
@XmlEnum
public enum PasteVisibility {

    @XmlEnumValue("0")PUBLIC("0"),
    @XmlEnumValue("1")UNLISTED("1"),
    @XmlEnumValue("2")PRIVATE("2");

    private final String visibility;

    PasteVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return visibility;
    }
}
