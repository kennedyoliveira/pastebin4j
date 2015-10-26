package com.github.kennedyoliveira.pastebin4j.api;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author kennedy
 */
public class PasteBinApiFactoryTest {

    @Test
    public void test_default_implementation_singleton() throws Exception {
        final PasteBinApi defaultImplementation = PasteBinApiFactory.createDefaultImplementation();
        final PasteBinApi defaultImplementation1 = PasteBinApiFactory.createDefaultImplementation();
        final PasteBinApi defaultImplementation2 = PasteBinApiFactory.createDefaultImplementation();

        assertThat(defaultImplementation, is(CoreMatchers.equalTo(defaultImplementation1)));
        assertThat(defaultImplementation1, is(CoreMatchers.equalTo(defaultImplementation2)));
    }
}