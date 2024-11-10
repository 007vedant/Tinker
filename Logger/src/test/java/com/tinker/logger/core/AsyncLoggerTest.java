package com.tinker.logger.core;

import com.tinker.logger.appenders.ConsoleAppender;
import com.tinker.logger.entities.LogMessage;
import com.tinker.logger.entities.LoggerConfig;
import com.tinker.logger.enums.LogLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

/**
 * Class to test functionality of AsyncLogger.
 */
public class AsyncLoggerTest {
    private AsyncLogger logger;
    private LoggerConfig mockConfig;
    private ConsoleAppender mockAppender;

    @BeforeEach
    public void setUp() throws Exception {
        resetSingletonInstance();
        logger = AsyncLogger.getInstance();
        mockConfig = mock(LoggerConfig.class);
        mockAppender = mock(ConsoleAppender.class);

        when(mockConfig.getLogLevel()).thenReturn(LogLevel.DEBUG);
        when(mockConfig.getLogAppender()).thenReturn(mockAppender);

        logger.setLoggerConfig(mockConfig);
    }

    @AfterEach
    public void tearDown() {
        logger.shutDown();
    }

    @Test
    public void testSingletonInstance() {
        AsyncLogger firstInstance = AsyncLogger.getInstance();
        AsyncLogger secondInstance = AsyncLogger.getInstance();

        assertNotNull(firstInstance, "Instance should not be null");
        assertSame(firstInstance, secondInstance, "getInstance should return the same instance");
    }

    @Test
    public void testLogMessageIsQueuedAndProcessed() throws InterruptedException {
        logger.log(LogLevel.DEBUG, "Test message");

        // Waiting for worker thread to process queue
        TimeUnit.MILLISECONDS.sleep(100);

        verify(mockAppender, times(1)).append(any(LogMessage.class));
    }

    @Test
    public void testLogLevelFiltering() throws InterruptedException {
        when(mockConfig.getLogLevel()).thenReturn(LogLevel.INFO);
        logger.setLoggerConfig(mockConfig);

        logger.log(LogLevel.DEBUG, "This should not be logged");

        TimeUnit.MILLISECONDS.sleep(100);

        verify(mockAppender, never()).append(any(LogMessage.class));
    }

    @Test
    public void testShutDownProcessesRemainingMessages() throws InterruptedException {
        logger.log(LogLevel.INFO, "Message 1");
        logger.log(LogLevel.INFO, "Message 2");

        logger.shutDown();

        verify(mockAppender, times(2)).append(any(LogMessage.class));
    }

    @Test
    public void testShutDownStopsWorkerThread() {
        logger.shutDown();

        // logging message after shutdown
        logger.log(LogLevel.INFO, "This should not be processed");

        verify(mockAppender, never()).append(any(LogMessage.class));
    }

    private void resetSingletonInstance() throws Exception {
        Field instanceField = AsyncLogger.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }
}
