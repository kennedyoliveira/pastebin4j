package com.github.kennedyoliveira.pastebin4j.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Kennedy Oliveira
 */
public class PasteUtilTest {

    @Test
    public void testGetPasteKeyFromUrl() throws Exception {

        final String pasteBinUrl = "http://pastebin.com/NCSQ6k9N";

        assertThat("NCSQ6k9N", is(equalTo(PasteUtil.getPasteKeyFromUrl(pasteBinUrl))));
    }
}