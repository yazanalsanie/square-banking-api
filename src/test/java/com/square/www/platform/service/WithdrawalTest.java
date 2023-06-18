package com.square.www.platform.service;

import com.ibm.icu.util.ULocale;
import com.square.www.platform.constants.TransactionType;
import com.square.www.platform.exceptions.InsufficientFundsExceptionUponWithdrawal;
import com.square.www.platform.logging.WriteAheadLogger;
import com.square.www.platform.model.*;
import com.square.www.platform.service.impl.WithdrawalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WithdrawalTest {

    private final static String NAME = "Alice";
    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";

    private Customer customer;
    private Account account;
    private WithdrawalImpl withdrawal;

    @BeforeEach
    public void init(){
        final Set<Account> accounts = new HashSet<>();
        final UUID accountId = UUID.randomUUID();
        final UUID customerId = UUID.randomUUID();
        final Address address = new AddressBuilder(ADDRESS_LINE_ONE, new ULocale("en_US")).build();
        customer = new Customer(accounts, customerId, NAME, address);
        final BigDecimal balance = new BigDecimal(100);
        account = new Account(accountId,balance,customerId);
        accounts.add(account);
        withdrawal = new WithdrawalImpl(new WriteAheadLogger());
    }

    @Test
    public void givenValidTransactionWithSufficientFunds_whenWithdrawalExecuted_thenSuccessfullyUpdateAccountSetsToReflectValue(){
        final Transaction transaction = new Transaction(account,new BigDecimal(50), customer, TransactionType.WITHDRAW);
        Account account = withdrawal.withdraw(transaction);
        Assertions.assertEquals(account.getBalance().intValue(), 50);
        Assertions.assertEquals(Bank.getAccount(account.getAccountId()).get().getBalance().intValue(),50);
    }

    @Test
    public void givenInValidTransaction_whenWithdrawalExecuted_thenRejectTheTransaction(){
        final Transaction transaction = new Transaction(account,new BigDecimal(200), customer, TransactionType.WITHDRAW);
        Assertions.assertThrows(InsufficientFundsExceptionUponWithdrawal.class, () -> withdrawal.withdraw(transaction));
    }
}
