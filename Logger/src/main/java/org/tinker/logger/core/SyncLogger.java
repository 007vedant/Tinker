package org.tinker.logger.core;

import org.tinker.logger.appenders.ConsoleAppender;
import org.tinker.logger.entities.LogMessage;
import org.tinker.logger.entities.LoggerConfig;
import org.tinker.logger.enums.LogLevel;

import java.util.Objects;

/**
 * Logger to publish logs synchronously using custom appender.
 */
public class SyncLogger implements Logger {
    private static SyncLogger instance;
    private LoggerConfig loggerConfig;

    private SyncLogger() {
        this.loggerConfig = new LoggerConfig(LogLevel.INFO, new ConsoleAppender());
    }

    public static SyncLogger getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (SyncLogger.class) {
                if (Objects.isNull(instance)) {
                    instance = new SyncLogger();
                }
            }
        }
        return instance;
    }

    @Override
    public void setLoggerConfig(LoggerConfig loggerConfig) {
        this.loggerConfig = loggerConfig;
    }

    @Override
    public void log(LogLevel logLevel, String message) {
        if (logLevel.ordinal() >= loggerConfig.getLogLevel().ordinal()) {
            LogMessage logMessage = new LogMessage(logLevel, message);
            loggerConfig.getLogAppender().append(logMessage);
        }
    }
}
