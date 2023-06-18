package com.square.www.platform.util;

import com.ibm.icu.util.ULocale;
import com.square.www.platform.constants.TransactionType;
import com.square.www.platform.logging.SystemEvents;
import com.square.www.platform.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ValidatorTest {

    private final static String NAME = "Alice";
    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";

    private Customer customer;
    private Account account;

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

    }

    @Test
    public void givenValidTransactionWithSufficientFunds_whenWithdrawalIsTheType_thenAllowTransaction(){
        final BigDecimal value = new BigDecimal(50);
        final Transaction actualTransaction = new Transaction(account, value, customer, TransactionType.WITHDRAW);
        final SystemEvents actualEvent = Validator.validateTransaction(actualTransaction);
        SystemEvents expectedEvent = SystemEvents.SUFFICIENT_FUNDS;
        Assertions.assertEquals(actualEvent,expectedEvent);
    }

    @Test
    public void givenValidTransactionWithInSufficientFunds_whenWithdrawalIsTheType_thenRejectTransaction(){
        final BigDecimal value = new BigDecimal(150);
        final Transaction actualTransaction = new Transaction(account, value, customer, TransactionType.WITHDRAW);
        final SystemEvents actualEvent = Validator.validateTransaction(actualTransaction);
        SystemEvents expectedEvent = SystemEvents.INSUFFICIENT_FUNDS;
        Assertions.assertEquals(actualEvent,expectedEvent);
    }

    @Test
    public void givenValidTransactionWithSufficientFunds_whenDepositIsTheType_thenAllowTransaction(){
        final BigDecimal value = new BigDecimal(50);
        final Transaction actualTransaction = new Transaction(account, value, customer, TransactionType.DEPOSIT);
        final SystemEvents actualEvent = Validator.validateTransaction(actualTransaction);
        SystemEvents expectedEvent = SystemEvents.SUFFICIENT_FUNDS;
        Assertions.assertEquals(actualEvent,expectedEvent);
    }

    @Test
    public void givenValidTransactionWithInSufficientFunds_whenDepositIsTheType_thenRejectTransaction(){
        // negative value upon deposit
        final BigDecimal value = new BigDecimal(-50);
        final Transaction actualTransaction = new Transaction(account, value, customer, TransactionType.DEPOSIT);
        final SystemEvents actualEvent = Validator.validateTransaction(actualTransaction);
        SystemEvents expectedEvent = SystemEvents.SYNC_ATTEMPT_FAILED;
        Assertions.assertEquals(actualEvent,expectedEvent);
    }

    @Test
    public void givenValidTransactionWithValidValue_whenInquiryIsTheType_thenAllowTransaction(){
        final BigDecimal value = new BigDecimal(0);
        final Transaction actualTransaction = new Transaction(account, value, customer, TransactionType.INQUIRY);
        final SystemEvents actualEvent = Validator.validateTransaction(actualTransaction);
        SystemEvents expectedEvent = SystemEvents.TRANSACTION_ATTEMPT_SUCCESS;
        Assertions.assertEquals(actualEvent,expectedEvent);
    }

    @Test
    public void givenValidTransactionWithInValidValue_whenInquiryIsTheType_thenRejectTransaction(){
        final BigDecimal value = new BigDecimal(1);
        final Transaction actualTransaction = new Transaction(account, value, customer, TransactionType.INQUIRY);
        final SystemEvents actualEvent = Validator.validateTransaction(actualTransaction);
        SystemEvents expectedEvent = SystemEvents.SYNC_ATTEMPT_FAILED;
        Assertions.assertEquals(actualEvent,expectedEvent);
    }

    @Test
    public void givenInValidTransactionInstance_whenInvoked_thenThrowIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->Validator.validateTransaction(null));
    }
}
