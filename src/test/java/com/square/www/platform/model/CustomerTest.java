package com.square.www.platform.model;

import com.ibm.icu.util.ULocale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomerTest {

    private final static Set<Account> accountsForCustomerOneTwo = new HashSet<>();
    private final static Set<Account> accountsForCustomerThree = new HashSet<>();
    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";
    private final static String ADDRESS_DIFF = "Chicago. Commercial Building. 778155 Appraisal";
    private final static ULocale locale = new ULocale("en_US");

    private Customer customerOne;
    private Customer customerTwo;
    private Customer customerThree;
    private UUID customerId;
    private UUID differentCustomerId;
    private BigDecimal balance;
    private Account accountOne;
    private Account accountTwo;
    private Account accountThree;
    private UUID accountId;
    private UUID differentAccountID;
    private Address addressOne;
    private Address addressDiff;

    @BeforeEach
    public void init(){
        customerId = UUID.randomUUID();
        differentCustomerId = UUID.randomUUID();
        accountId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        differentAccountID = UUID.randomUUID();
        balance = new BigDecimal(100);
        accountOne = new Account(accountId,balance,customerId);
        accountTwo = new Account(accountId,balance,customerId);
        accountThree = new Account(differentAccountID,balance,customerId);
        accountsForCustomerOneTwo.add(accountOne);
        accountsForCustomerOneTwo.add(accountTwo);
        accountsForCustomerThree.add(accountThree);
        addressOne = new AddressBuilder(ADDRESS_LINE_ONE, locale).build();
        addressDiff = new AddressBuilder(ADDRESS_DIFF, locale).build();
        customerOne = new Customer(accountsForCustomerOneTwo, customerId,"ALICE", addressOne);
        customerTwo = new Customer(accountsForCustomerOneTwo, customerId,"ALICE", addressOne);
        customerThree = new Customer(accountsForCustomerThree, differentCustomerId,"John", addressDiff);
    }

    @Test
    public void testEquality(){
        Assertions.assertEquals(customerOne, customerTwo);
        Assertions.assertNotEquals(customerOne, customerThree);
        Assertions.assertEquals(customerOne.toString(), customerTwo.toString());
        Assertions.assertNotEquals(customerOne.toString(), customerThree.toString());
    }

}
