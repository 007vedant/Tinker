package com.tinker;

import com.tinker.logger.appenders.ConsoleAppender;
import com.tinker.logger.appenders.FileAppender;
import com.tinker.logger.core.AsyncLogger;
import com.tinker.logger.core.Logger;
import com.tinker.logger.entities.LoggerConfig;
import com.tinker.logger.enums.LogLevel;
import com.tinker.logger.enums.LoggerType;
import com.tinker.logger.factory.LoggerFactory;

public class MainLogger {
    public static void main(String[] args) {
        LoggerConfig loggerConfig = new LoggerConfig(LogLevel.DEBUG, new ConsoleAppender());
        Logger logger = LoggerFactory.getLogger(LoggerType.SYNC);
        logger.setLoggerConfig(loggerConfig);

        logger.info("This is a message for info log published through Sync Logger");
        logger.debug("This is a message for debug log published through Sync Logger");
        logger.error("This is a message for error log published through Sync Logger");

        LoggerConfig loggerConfig1 = new LoggerConfig(LogLevel.DEBUG, new FileAppender("app.log"));
        Logger logger1 = LoggerFactory.getLogger(LoggerType.ASYNC);
        logger1.setLoggerConfig(loggerConfig1);

        logger1.warn("This is a message for warn log published through Async Logger");
        logger1.debug("This is a message for debug log published through Async Logger");
        logger1.fatal("This is a message for fatal log published through Async Logger");

        ((AsyncLogger) logger1).shutDown();
    }
}