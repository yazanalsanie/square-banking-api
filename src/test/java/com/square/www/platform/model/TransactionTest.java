package com.square.www.platform.model;

import com.ibm.icu.util.ULocale;
import com.square.www.platform.constants.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

public class TransactionTest {

    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";
    private final static ULocale locale = new ULocale("en_US");

    private Transaction transactionOne;
    private Transaction transactionTwo;
    private Customer customerOne;
    private Customer customerTwo;
    private UUID customerId;
    private BigDecimal balance;
    private Account accountOne;
    private Account accountTwo;
    private UUID accountId;
    private UUID customerIdDiff;
    private Address addressOne;

    @BeforeEach
    public void init(){
        customerId = UUID.randomUUID();
        accountId = UUID.randomUUID();
        customerIdDiff = UUID.randomUUID();
        balance = new BigDecimal(100);
        accountOne = new Account(accountId,balance,customerId);
        accountTwo = new Account(UUID.randomUUID(), balance, customerIdDiff);
        addressOne = new AddressBuilder(ADDRESS_LINE_ONE, locale).build();
        customerOne = new Customer(new HashSet<>(), customerId,"ALICE", addressOne);
        customerTwo = new Customer(new HashSet<>(), customerIdDiff,"JOHN", addressOne);
        transactionOne = new Transaction(accountOne, new BigDecimal(100), customerOne, TransactionType.DEPOSIT);
        transactionTwo = new Transaction(accountTwo, new BigDecimal(100), customerOne, TransactionType.DEPOSIT);
    }

    @Test
    public void testEquality(){
        Assertions.assertNotEquals(transactionOne, transactionTwo);
        Assertions.assertNotEquals(transactionOne.toString(), transactionTwo.toString());
    }

}
