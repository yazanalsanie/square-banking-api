package com.square.www.platform.exceptions;

/**
 * The AccountNotFoundException class can be thrown whenever an accountId doesn't exist ( doesn't correspond to an actual account)
 * in our Bank {@link com.square.www.platform.model.Bank}
 * The exception is of type {@link RuntimeException} NOT to enforce checked Exceptions upon users of our implementation
 * **/
public class AccountNotFoundException extends RuntimeException{
    private final String message;

    public AccountNotFoundException(final String message){
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
