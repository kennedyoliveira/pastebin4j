package com.github.kennedyoliveira.pastebin4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author kennedy
 */
public class PasteHighLightTest {

        @Test
    public void testNoDuplicates() throws Exception {
        Arrays.stream(PasteHighLight.values()).collect(Collectors.toMap(p -> p.toString().toLowerCase(), Function.identity()));
    }
}