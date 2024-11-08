package org.tinker.logger.appenders;

import org.tinker.logger.entities.LogMessage;

/**
 * Appender to write log messages to stdout.
 */
public class ConsoleAppender implements LogAppender {

    @Override
    public void append(LogMessage logMessage) {
        System.out.println(logMessage.toString());
    }
}
