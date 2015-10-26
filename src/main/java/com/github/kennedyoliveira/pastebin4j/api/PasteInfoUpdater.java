package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.PasteExpiration;
import org.jetbrains.annotations.Nullable;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Utility method to use after parsing the Pastes to update informations.
 *
 * @author kennedy
 */
class PasteInfoUpdater {

    private PasteInfoUpdater() {}

    /**
     * <p>Updates the dates of a list of pastes.</p>
     * <p>This method mutate the objects of the list and return the list itself.</p>
     *
     * @param pastes List to be updated.
     * @return The list updated.
     */
    static List<Paste> updateDate(@Nullable List<Paste> pastes) {
        if (pastes == null || pastes.isEmpty())
            return Collections.emptyList();

        pastes.forEach(PasteInfoUpdater::updateDate);

        return pastes;
    }

    /**
     * <p>Update the date information of a paste.</p>
     * <p>This class mutates the paste.</p>
     *
     * @param paste The paste to be updated
     * @return The paste updated.
     */
    static BasePaste updateDate(@Nullable BasePaste paste) {
        // No dates to update
        if (paste == null)
            return null;

        if (paste instanceof Paste) {
            Paste tmpPaste = (Paste) paste;

            if (paste.getExpireDateTimestamp() > 0) {
                tmpPaste.setExpiration(PasteExpiration.getBySeconds(paste.getExpireDateTimestamp() - paste.getDateTimestamp()));
            } else {
                tmpPaste.setExpiration(PasteExpiration.NEVER);
            }
        }

        if (paste.getDateTimestamp() > 0) {
            final ZonedDateTime localPasteDate = new Date(paste.getDateTimestamp() * 1000L).toInstant().atZone(ZoneId.systemDefault());

            paste.setLocalPasteDate(localPasteDate);
            paste.setPasteDate(localPasteDate.toLocalDateTime());

            if (paste.getExpireDateTimestamp() > 0) {
                final ZonedDateTime zonedDateTime = new Date(paste.getExpireDateTimestamp() * 1000L).toInstant().atZone(ZoneId.systemDefault());

                paste.setLocalExpirationDate(zonedDateTime);
                paste.setExpirationDate(zonedDateTime.toLocalDateTime());
            }
        }

        return paste;
    }
}
