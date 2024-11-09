package com.tinker.wctool.core;

import com.tinker.wctool.models.Stats;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test functionality of WCService
 */
public class WCServiceTest {
    private BufferedReader reader;
    private WCService wcService;

    @BeforeEach
    public void setUp() {
        String input = "This is a sample text.\nIt has multiple lines.\nAnd some words.\n";
        reader = new BufferedReader(new StringReader(input));
        wcService = new WCService(reader);
    }

    @AfterEach
    public void tearDown() throws IOException {
        reader.close();
    }

    @Test
    public void testComputeStats() throws IOException {
        Stats stats = wcService.computeStats();

        assertEquals(62, stats.getCharCount());
        assertEquals(12, stats.getWordCount());
        assertEquals(3, stats.getLineCount());
    }
}

