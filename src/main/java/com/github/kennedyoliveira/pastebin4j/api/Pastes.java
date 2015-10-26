package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.Paste;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class is just a wrapper for converting pastes from XML..
 *
 * @author kennedy
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Pastes {

    @XmlElement(name = "paste")
    private List<Paste> pastes;

    public List<Paste> getPastes() {
        return pastes;
    }
}
