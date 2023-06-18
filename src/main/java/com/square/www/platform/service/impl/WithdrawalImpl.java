package com.square.www.platform.service.impl;

import com.square.www.platform.exceptions.InsufficientFundsExceptionUponWithdrawal;
import com.square.www.platform.logging.SystemEvents;
import com.square.www.platform.logging.WriteAheadLogger;
import com.square.www.platform.model.Account;
import com.square.www.platform.model.Bank;
import com.square.www.platform.model.Transaction;
import com.square.www.platform.service.api.Withdrawal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.square.www.platform.util.Validator.validateTransaction;

@Component
public class WithdrawalImpl implements Withdrawal {

    private final static Logger LOGGER = LoggerFactory.getLogger(WithdrawalImpl.class);

    private final WriteAheadLogger writeAheadLogger;

    @Autowired
    public WithdrawalImpl(@Autowired final WriteAheadLogger writeAheadLogger){
        this.writeAheadLogger = writeAheadLogger;
    }

    @Override
    public Account withdraw(final Transaction transaction) {
        final SystemEvents events = validateTransaction(transaction);
        final Account account = transaction.getAccount();
        if(events == SystemEvents.SUFFICIENT_FUNDS){
            writeAheadLogger.logTransactionOnFile(transaction.toString());
            LOGGER.info(events.getMessage() + transaction);
            transaction.setBalance(account.getBalance().subtract(transaction.getValue()));
            Bank.updateAccount(account);
            return account;
        }
        LOGGER.error(events.getMessage() + transaction);
        throw new InsufficientFundsExceptionUponWithdrawal("Insufficient funds exception: you don't have enough credit to complete the transaction!");
    }
}
