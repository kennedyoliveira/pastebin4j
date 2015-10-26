package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.PasteExpiration;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author kennedy
 */
public class PasteInfoUpdaterTest {

    @Test
    public void test_update_single_paste() throws Exception {
        String xml = "<paste>\n" +
                "<paste_key>aismmqn8</paste_key>\n" +
                "<paste_date>1445820681</paste_date>\n" +
                "<paste_title>Testing API</paste_title>\n" +
                "<paste_size>22</paste_size>\n" +
                "<paste_expire_date>1445824281</paste_expire_date>\n" +
                "<paste_private>1</paste_private>\n" +
                "<paste_format_long>Java</paste_format_long>\n" +
                "<paste_format_short>java</paste_format_short>\n" +
                "<paste_url>http://pastebin.com/aismmqn8</paste_url>\n" +
                "<paste_hits>1</paste_hits>\n" +
                "</paste>";

        final Paste paste = XMLUtils.unMarshal(xml, Paste.class);

        PasteInfoUpdater.updateDate(paste);

        assertThat(paste.getExpiration(), is(PasteExpiration.ONE_HOUR));
        assertThat(paste.getExpirationDate().getHour() - paste.getPasteDate().getHour(), is(1));
    }

    @Test
    public void test_null_safe() throws Exception {
        assertThat(PasteInfoUpdater.updateDate((BasePaste) null), is(equalTo(null)));
        assertThat(PasteInfoUpdater.updateDate((List<Paste>) null), is(equalTo(Collections.emptyList())));
    }

    @Test
    public void test_update_expiration_never() throws Exception {
        final BasePaste basePaste = PasteInfoUpdater.updateDate(new Paste());

        final Paste pasteTmp = (Paste) basePaste;

        assertThat(pasteTmp.getExpiration(), is(PasteExpiration.NEVER));
    }
}