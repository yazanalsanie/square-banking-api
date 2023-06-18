package com.square.www.platform.logging;

/**
 * The SystemEvents enum represents main system events that drives understandable messages across our services and logs
 * **/
public enum SystemEvents {
    SYNC_ATTEMPT_FAILED("Failed to perform the transaction due to "),
    TRANSACTION_ATTEMPT_SUCCESS("Performed the following transaction successfully: "),
    INSUFFICIENT_FUNDS("Insufficient funds: "),
    SUFFICIENT_FUNDS("Sufficient funds: ");

    private String message;

    SystemEvents(final String message) {
        this.message = message;
    }

    /**
     * The get message method is used to get {@link SystemEvents} value of type {@link String}
     * @return message
     * **/
    public String getMessage() {
        return message;
    }
}
