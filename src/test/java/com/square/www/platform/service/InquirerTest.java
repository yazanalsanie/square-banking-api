package com.square.www.platform.service;

import com.ibm.icu.util.ULocale;
import com.square.www.platform.exceptions.AccountNotFoundException;
import com.square.www.platform.model.*;
import com.square.www.platform.service.impl.InquirerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InquirerTest {

    private final static String NAME = "Alice";
    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";

    private Customer customer;
    private Account account;
    private InquirerImpl inquirer;

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
        Bank.updateAccount(account);
        inquirer = new InquirerImpl();
    }

    @Test
    public void givenValidAccountId_whenInquiryExecuted_thenSuccessfullyReturnBalance(){
        Account actual = inquirer.inquiry(account.getAccountId());
        Assertions.assertEquals(actual.getBalance().intValue(), 100);
    }

    @Test
    public void givenInValidAccountId_whenInquiryExecuted_thenAccountDoesNotExist(){
        Assertions.assertThrows(AccountNotFoundException.class, () -> inquirer.inquiry(UUID.randomUUID()));
    }

}
