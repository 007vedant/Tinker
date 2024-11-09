package com.tinker.logger.entities;

import com.tinker.logger.appenders.LogAppender;
import com.tinker.logger.core.Logger;
import com.tinker.logger.enums.LogLevel;

/**
 * Class to model config to be provided to {{@link Logger}}
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
