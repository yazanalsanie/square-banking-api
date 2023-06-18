package com.square.www.platform.exceptions;

/**
 * The InsufficientFundsExceptionUponWithdrawal class can be thrown whenever a withdrawal exceeds available balance
 * in customer's account {@link com.square.www.platform.model.Account}
 * The exception is of type {@link RuntimeException} NOT to enforce checked Exceptions upon users of our implementation
 * **/
public class InsufficientFundsExceptionUponWithdrawal extends RuntimeException {
    private final String message;

    public InsufficientFundsExceptionUponWithdrawal(final String message){
        this.message = message;
    }

    /**
     * The get message method is used to get exception message of type {@link String}
     * @return message
     * **/
    @Override
    public String getMessage() {
        return message;
    }
}
