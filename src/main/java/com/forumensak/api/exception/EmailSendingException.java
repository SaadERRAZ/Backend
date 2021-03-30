package com.forumensak.api.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public EmailSendingException(String exMessage) {
        super(exMessage);
    }
}