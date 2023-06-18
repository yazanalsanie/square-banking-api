package com.square.www.platform.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountTest {

    private Account accountOne;
    private Account accountTwo;
    private Account accountThree;
    private UUID accountId;
    private UUID differentAccountID;
    private UUID customerId;
    private BigDecimal balance;

    @BeforeEach
    public void init(){
        accountId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        differentAccountID = UUID.randomUUID();
        balance = new BigDecimal(100);
        accountOne = new Account(accountId,balance,customerId);
        accountTwo = new Account(accountId,balance,customerId);
        accountThree = new Account(differentAccountID,balance,customerId);
    }

    @Test
    public void testEquality(){
        Assertions.assertEquals(accountOne, accountTwo);
        Assertions.assertNotEquals(accountOne, accountThree);
        Assertions.assertEquals(accountOne.toString(), accountTwo.toString());
        Assertions.assertNotEquals(accountOne.toString(), accountThree.toString());
    }
}
