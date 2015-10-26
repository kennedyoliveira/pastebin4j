package com.github.kennedyoliveira.pastebin4j.api;


import javax.xml.bind.JAXB;
import java.io.StringReader;

/**
 * @author kennedy
 */
class XMLUtils {

    public static <T> T unMarshal(String xml, Class<T> clazz) {
        return JAXB.unmarshal(new StringReader(xml), clazz);
    }
}
