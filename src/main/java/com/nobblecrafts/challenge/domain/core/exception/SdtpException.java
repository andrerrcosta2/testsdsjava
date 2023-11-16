package com.nobblecrafts.challenge.domain.core.exception;

public class SdtpException extends RuntimeException {

    public SdtpException(String message) {
        super(message);
    }

    public SdtpException(String message, Throwable cause) {
        super(message, cause);
    }
}
