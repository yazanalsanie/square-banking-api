package com.square.www.platform.service.api;

import com.square.www.platform.model.Account;
import com.square.www.platform.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface Withdrawal {
    /**
     * @apiNote withdraw a certain amount from a bank account
     * @param transaction
     * @return updated {@link Account}
     * **/
    Account withdraw(Transaction transaction);
}
