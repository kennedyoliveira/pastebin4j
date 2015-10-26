package com.github.kennedyoliveira.pastebin4j;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Represents the possible account types.
 *
 * @author kennedy
 * @since 1.0.0
 */
@XmlEnum
public enum AccountType {

    @XmlEnumValue("0")NORMAL(0),
    @XmlEnumValue("1")PRO(1);

    private final int type;

    AccountType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
