package org.tinker.logger.core;

import org.tinker.logger.appenders.ConsoleAppender;
import org.tinker.logger.entities.LogMessage;
import org.tinker.logger.entities.LoggerConfig;
import org.tinker.logger.enums.LogLevel;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Logger to publish logs asynchronously using custom appender.
 */
public class AsyncLogger implements Logger {
    private static AsyncLogger instance;
    private LoggerConfig loggerConfig;
    private BlockingQueue<LogMessage> queue;
    private Thread workerThread;
    private volatile boolean isShuttingDown = false;

    private AsyncLogger() {
        this.loggerConfig = new LoggerConfig(LogLevel.DEBUG, new ConsoleAppender());
        this.queue = new LinkedBlockingQueue<>();
        this.workerThread = new Thread(this::processQueue);
        this.workerThread.start();

    }

    public static AsyncLogger getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (SyncLogger.class) {
                if (Objects.isNull(instance)) {
                    instance = new AsyncLogger();
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
        if (!isShuttingDown && logLevel.ordinal() >= loggerConfig.getLogLevel().ordinal()) {
            LogMessage logMessage = new LogMessage(logLevel, message);
            queue.offer(logMessage);
        }
    }

    private void processQueue() {
        while (!isShuttingDown || !queue.isEmpty()) {
            try {
                LogMessage logMessage = queue.take(); // performing waiting action until shut down is called
                loggerConfig.getLogAppender().append(logMessage);
            } catch (InterruptedException e) {
                if (isShuttingDown) {
                    break; // when isShuttingDown=true then thread is interrupted, hence breaking from the loop for graceful exit
                }
                e.printStackTrace();
            }
        }
    }

    public void shutDown() {
        isShuttingDown = true;
        workerThread.interrupt();

        // Forcefully drain the queue in case the worker thread was interrupted early
        while (!queue.isEmpty()) {
            try {
                LogMessage logMessage = queue.poll();
                if (logMessage != null) {
                    loggerConfig.getLogAppender().append(logMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            workerThread.join(); // Wait for worker thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
