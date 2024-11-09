package com.tinker.logger.appenders;

import com.tinker.logger.entities.LogMessage;

/**
 * Interface to define APIs for appending log message.
 */
public interface LogAppender {
    void append(LogMessage logMessage);
}
