package com.github.kennedyoliveira.pastebin4j.utils;

import org.junit.Test;

import static com.github.kennedyoliveira.pastebin4j.api.StringUtils.isNotNullNorEmpty;
import static com.github.kennedyoliveira.pastebin4j.api.StringUtils.isNullOrEmpty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kennedy
 */
public class StringUtilsTest {

    @Test
    public void testIsNullOrEmpty() throws Exception {
        assertThat(isNullOrEmpty(null), is(true));
        assertThat(isNullOrEmpty(""), is(true));
        assertThat(isNullOrEmpty("a"), is(false));
    }

    @Test
    public void testIsNotNullNorEmpty() throws Exception {
        assertThat(isNotNullNorEmpty(""), is(false));
        assertThat(isNotNullNorEmpty(null), is(false));
        assertThat(isNotNullNorEmpty("a"), is(true));
    }
}