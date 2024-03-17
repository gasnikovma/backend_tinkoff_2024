package edu.java.exceptions;

import org.apache.kafka.common.protocol.types.Field;

public class NoChatException extends RuntimeException{
    public NoChatException(String exMessage) {
        super(exMessage);

    }
}
