package com.tinker.wctool.models;

/**
 * Pojo to capture results of operations
 */
public class Stats {

    private int charCount;
    private int wordCount;
    private int lineCount;

    // Public no-argument constructor
    public Stats() {
    }

    // Parameterized constructor
    public Stats(int charCount, int wordCount, int lineCount) {
        this.charCount = charCount;
        this.wordCount = wordCount;
        this.lineCount = lineCount;
    }

    // Public getters and setters
    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    // String representation
    @Override
    public String toString() {
        return "Stats{" +
                "charCount='" + charCount + '\'' +
                ", wordCount='" + wordCount + '\'' +
                ", lineCount='" + lineCount + '\'' +
                '}';
    }
}

