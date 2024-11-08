package org.tinker.logger.factory;

import org.tinker.logger.core.AsyncLogger;
import org.tinker.logger.core.Logger;
import org.tinker.logger.core.SyncLogger;
import org.tinker.logger.enums.LoggerType;
import org.tinker.logger.entities.LoggerConfig;

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
