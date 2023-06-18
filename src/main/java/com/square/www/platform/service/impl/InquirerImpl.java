package com.square.www.platform.service.impl;

import com.square.www.platform.exceptions.AccountNotFoundException;
import com.square.www.platform.model.Account;
import com.square.www.platform.service.api.Inquirer;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.square.www.platform.model.Bank.getAccount;

@Component
public class InquirerImpl implements Inquirer {
    @Override
    public Account inquiry(UUID accountId) {
        return getAccount(accountId).orElseThrow(
                ()-> new AccountNotFoundException("Account doesn't exist in our records!"));
    }
}
