package org.tinker.logger.appenders;

import org.tinker.logger.entities.LogMessage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Appender to write log messages to a file.
 */
public class FileAppender implements LogAppender {
    private final String path;

    public FileAppender(String path) {
        this.path = path;
    }

    @Override
    public synchronized void append(LogMessage logMessage) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.path, true))) {
            bufferedWriter.write(logMessage.toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
