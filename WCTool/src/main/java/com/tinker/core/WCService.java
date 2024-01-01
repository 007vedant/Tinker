package com.tinker.core;

import com.tinker.models.Stats;

import java.io.BufferedReader;
import java.io.IOException;

public class WCService {
    private final BufferedReader reader;

    public WCService(BufferedReader reader) {
        this.reader = reader;
    }

    public Stats computeStats() throws IOException {
        int charCount = 0, wordCount = 0, lineCount = 0, input = 0;
        boolean whiteSpaceFlag = false;

        while ((input = reader.read()) != -1) {
            char character = (char) input;

            if (Character.isWhitespace(character)) {
                wordCount += whiteSpaceFlag ? 0 : 1;
                lineCount += character == '\n' ? 1 : 0;
                whiteSpaceFlag = true;
            } else if (character != '\n') {
                whiteSpaceFlag = false;
            }

            charCount += 1;
        }

        return new Stats(charCount, wordCount, lineCount);

    }
}
