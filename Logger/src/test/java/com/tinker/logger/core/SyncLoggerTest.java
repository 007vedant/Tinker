package com.tinker.logger.core;

import com.tinker.logger.appenders.ConsoleAppender;
import com.tinker.logger.appenders.LogAppender;
import com.tinker.logger.entities.LogMessage;
import com.tinker.logger.entities.LoggerConfig;
import com.tinker.logger.enums.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Class to test functionality of SyncLogger.
 */
public class SyncLoggerTest {
    private SyncLogger logger;
    private LoggerConfig mockConfig;
    private LogAppender mockAppender;

    @BeforeEach
    public void setUp() {
        logger = SyncLogger.getInstance();
        mockConfig = mock(LoggerConfig.class);
        mockAppender = mock(ConsoleAppender.class);

        when(mockConfig.getLogLevel()).thenReturn(LogLevel.DEBUG);
        when(mockConfig.getLogAppender()).thenReturn(mockAppender);

        logger.setLoggerConfig(mockConfig);

    }

    @Test
    public void testSingletonInstance() {
        SyncLogger firstInstance = SyncLogger.getInstance();
        SyncLogger secondInstance = SyncLogger.getInstance();
        assertNotNull(firstInstance);
        assertSame(firstInstance, secondInstance, "getInstance should return the same instance");
    }

    @Test
    public void testLogAboveLogLevel() {
        logger.log(LogLevel.ERROR, "Test error log");

        verify(mockAppender, times(1)).append(any(LogMessage.class));
    }

    @Test
    public void testLogBelowLogLevel() {
        when(mockConfig.getLogLevel()).thenReturn(LogLevel.WARN);
        logger.setLoggerConfig(mockConfig);

        logger.log(LogLevel.INFO, "This should not be logged");

        verify(mockAppender, never()).append(any(LogMessage.class));
    }

    @Test
    public void testLogAtExactLogLevel() {
        logger.log(LogLevel.DEBUG, "Log at exact log level");

        verify(mockAppender, times(1)).append(any(LogMessage.class));
    }

    @Test
    public void testLogMessageConstruction() {
        logger.log(LogLevel.INFO, "Test message");

        verify(mockAppender).append(argThat(logMessage ->
                logMessage.getLogLevel() == LogLevel.INFO &&
                        "Test message".equals(logMessage.getMessage())
        ));
    }
}
