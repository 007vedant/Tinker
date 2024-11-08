package org.tinker.logger.core;

import org.tinker.logger.entities.LoggerConfig;
import org.tinker.logger.enums.LogLevel;

/**
 * Interface to define APIs for logging.
 */
public interface Logger {
    void log(LogLevel logLevel, String logMessage);

    void setLoggerConfig(LoggerConfig loggerConfig);

    default void debug(String logMessage) {
        log(LogLevel.DEBUG, logMessage);
    }

    default void info(String logMessage) {
        log(LogLevel.INFO, logMessage);
    }

    default void warn(String logMessage) {
        log(LogLevel.WARN, logMessage);
    }

    default void error(String logMessage) {
        log(LogLevel.ERROR, logMessage);
    }

    default void fatal(String logMessage) {
        log(LogLevel.FATAL, logMessage);
    }
}
