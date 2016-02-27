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

    @XmlEnumValue("0")NORMAL,
    @XmlEnumValue("1")PRO
}
