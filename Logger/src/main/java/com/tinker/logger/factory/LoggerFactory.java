package com.tinker.logger.factory;

import com.tinker.logger.core.AsyncLogger;
import com.tinker.logger.core.Logger;
import com.tinker.logger.core.SyncLogger;
import com.tinker.logger.enums.LoggerType;

/**
 * Factory class to provide logger instance based on user configuration.
 */
public class LoggerFactory {

    public static Logger getLogger(LoggerType loggerType) {
        return switch (loggerType) {
            case ASYNC -> AsyncLogger.getInstance();
            case SYNC -> SyncLogger.getInstance();
            default -> throw new IllegalArgumentException("Invalid logger type provided!");
        };
    }
}
