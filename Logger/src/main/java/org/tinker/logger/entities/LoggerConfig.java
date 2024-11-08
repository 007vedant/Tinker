package org.tinker.logger.entities;

import org.tinker.logger.appenders.LogAppender;
import org.tinker.logger.enums.LogLevel;

/**
 * Class to model config to be provided to {{@link org.tinker.logger.core.Logger}}
 */
public class LoggerConfig {
    private LogLevel logLevel;
    private LogAppender logAppender;

    public LoggerConfig(LogLevel logLevel, LogAppender logAppender) {
        this.logAppender = logAppender;
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LogAppender getLogAppender() {
        return logAppender;
    }

    public void setLogAppender(LogAppender logAppender) {
        this.logAppender = logAppender;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
