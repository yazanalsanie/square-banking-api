package com.square.www.platform.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Bank {

    private final static Map<UUID,Account> ACCOUNTS = new HashMap();

    private Bank(){}

    public static void updateAccount(final Account account){
        if(account != null){
            ACCOUNTS.put(account.getAccountId(), account);
        }else {
            throw new IllegalArgumentException("Received account must not be null!");
        }
    }

    public static Optional<Account> getAccount(final UUID accountId){
        if(ACCOUNTS.containsKey(accountId)){
            return Optional.of(ACCOUNTS.get(accountId));
        }
        return Optional.empty();
    }

    public static Map<UUID,Account> getAccounts(){
        return ACCOUNTS;
    }
}
