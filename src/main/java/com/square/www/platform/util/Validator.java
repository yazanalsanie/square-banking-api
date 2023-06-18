package com.square.www.platform.util;

import com.square.www.platform.logging.SystemEvents;
import com.square.www.platform.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * The Validator class acts as a transaction util class.
 * It validates each transaction of type {@link com.square.www.platform.model.Transaction} against a set of edge cases
 * This used to provide a well-defined system event to instruct the services on how to deal with certain transacrions
 * **/
public class Validator {

    private final static Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    private Validator(){}

    /**
     * @implSpec validate if a transaction of type {@link com.square.www.platform.constants.TransactionType} is supported and valid
     * @param transaction
     * @return {@link SystemEvents}
     * **/
    public static SystemEvents validateTransaction(final Transaction transaction){

        if(transaction == null || transaction.getAccount() == null || transaction.getValue() == null){
            throw new IllegalArgumentException("passed transaction and transaction account|value should not be null!");
        }
        switch (transaction.getTransactionType()){
            case DEPOSIT:
                return validateDeposit(transaction);
            case WITHDRAW:
                return validateWithdraw(transaction);
            case INQUIRY:
                if(transaction.getValue().compareTo(BigDecimal.ZERO) == 0){
                    return SystemEvents.TRANSACTION_ATTEMPT_SUCCESS;
                }else {
                    LOGGER.error("transaction value must be 0 on read only operations");
                    return SystemEvents.SYNC_ATTEMPT_FAILED;
                }
        }
        return SystemEvents.SYNC_ATTEMPT_FAILED;
    }

    private static SystemEvents validateWithdraw(final Transaction transaction){
        if(transaction.getAccount()
                .getBalance()
                .subtract(transaction.getValue())
                .compareTo(BigDecimal.ZERO) > 0){
            return SystemEvents.SUFFICIENT_FUNDS;
        }
        return SystemEvents.INSUFFICIENT_FUNDS;
    }

    private static SystemEvents validateDeposit(final Transaction transaction){
        if(transaction.getValue().compareTo(BigDecimal.ZERO) > 0){
            return SystemEvents.SUFFICIENT_FUNDS;
        }
        return SystemEvents.SYNC_ATTEMPT_FAILED;
    }

}
