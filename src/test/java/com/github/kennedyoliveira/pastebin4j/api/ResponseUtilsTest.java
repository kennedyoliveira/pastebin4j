package com.github.kennedyoliveira.pastebin4j.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.management.RuntimeErrorException;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author kennedy
 */
public class ResponseUtilsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testValidateResponseNull() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Empty response");

        ResponseUtils.requiresValidResponse(null);
    }

    @Test
    public void testValidateResponseEmpty() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Empty response");

        ResponseUtils.requiresValidResponse(Optional.empty());
    }

    @Test
    public void testValidateResponseBadApiRequest() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Error: Bad API request, invalid api_dev_key");

        ResponseUtils.requiresValidResponse(Optional.of("Bad API request, invalid api_dev_key"));
    }

    @Test
    public void testValidateResponseGoodResponse() throws Exception {
        final Optional<String> s = ResponseUtils.requiresValidResponse(Optional.of("<paste></paste>"));

        assertThat(s.get(), is("<paste></paste>"));
    }
}