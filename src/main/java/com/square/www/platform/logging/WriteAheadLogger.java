package com.square.www.platform.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The WriteAheadLogger class acts as a transaction writer.
 * It writes each transaction of type {@link com.square.www.platform.model.Transaction} string to a file before the transaction is executed
 * This used to provide atomicity and durability in case this service grew to support certain type of storage
 * **/
@Component
public class WriteAheadLogger {

    private final static Logger LOGGER = LoggerFactory.getLogger(WriteAheadLogger.class);

    @Value("${write-ahead-logger.path}")
    private String path;

    @Value("${write-ahead-logger.enabled}")
    private boolean isEnabled;

    public WriteAheadLogger(){}

    public WriteAheadLogger(final String path, final boolean isEnabled) {
        this.path = path;
        this.isEnabled = isEnabled;
    }

    /**
     * @param transaction represents a {@link com.square.www.platform.model.Transaction} string
     * @implSpec writes a transaction to our write ahead log currently stored in FS
     * **/
    public void logTransactionOnFile(final String transaction){
        if(isEnabled){
            try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardOpenOption.APPEND)){
                writer.write(transaction);
                writer.newLine();
            }catch (IOException exception){
                LOGGER.error(exception.getLocalizedMessage());
                throw new RuntimeException(exception);
            }
        }
    }
}
