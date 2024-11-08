package org.tinker.logger.entities;

import org.tinker.logger.enums.LogLevel;

/**
 * Class to model the message to be logged.
 */
public class LogMessage {
    private final Long timestamp;
    private final LogLevel logLevel;
    private final String message;

    public LogMessage(LogLevel logLevel, String message) {
        this.logLevel = logLevel;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[" + logLevel + "] " + "[" + timestamp + "]" + ": " + message;
    }
}
