package com.github.kennedyoliveira.pastebin4j.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kennedy
 */
public class WebUtilsTest {

    @Test
    public void testGet() throws Exception {
        final HashMap<Object, Object> params = new HashMap<>();

        params.put("i", "rrPV9pzZ");

        final Optional<String> s = WebUtils.get("http://pastebin.com/raw.php", params);

        assertThat(s.get(), is("test for pastebin4j api"));
    }

    @Test
    public void test_get_params() throws Exception {
        final HashMap<Object, Object> params = new LinkedHashMap<>();

        params.put("param1", 123);
        params.put("param2", "456");
        params.put(PasteBinApiParams.DEV_KEY, "asdqwe");

        assertThat(WebUtils.getParams(params), is("param1=123&param2=456&api_dev_key=asdqwe"));
    }

    @Test
    public void test_do_post() throws Exception {
        final String devkey = System.getProperty("pastebin4j.devkey");

        if (devkey == null)
            throw new RuntimeException("Dev key missing, especify the devkey in the running command with -Dpastebin4j.devkey=devkey");

        final HashMap<Object, Object> params = new HashMap<>();

        params.put(PasteBinApiParams.DEV_KEY, devkey);
        params.put(PasteBinApiParams.OPTION, PasteBinApiOptions.TRENDS);

        final Optional<String> resp = WebUtils.post(PasteBinApiUrls.API_POST_URL, params);

        assertThat(resp.isPresent(), is(true));

        final String respText = resp.get();

        assertThat(respText, not(containsString("Bad API request")));
        assertThat(respText, allOf(containsString("<paste>"), containsString(("</paste>"))));
    }
}