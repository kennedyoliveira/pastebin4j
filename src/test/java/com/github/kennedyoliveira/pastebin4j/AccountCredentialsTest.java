package com.github.kennedyoliveira.pastebin4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kennedy
 */
public class AccountCredentialsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_prevent_missing_devkey_creating() throws Exception {
        expectedException.expect(NullPointerException.class);

        new AccountCredentials(null);
        fail("should throw nullpointerexception");
    }

    @Test
    public void test_nullable_fields_as_optionals() throws Exception {
        final AccountCredentials credentials = new AccountCredentials("devkey", null, null);

        assertThat(credentials.getDevKey(), is("devkey"));
        assertThat(credentials.getPassword(), is(Optional.empty()));
        assertThat(credentials.getUserName(), is(Optional.empty()));
        assertThat(credentials.getUserSessionKey(), is(Optional.empty()));
    }
}