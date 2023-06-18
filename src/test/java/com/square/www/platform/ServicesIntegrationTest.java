package com.square.www.platform;

import com.ibm.icu.util.ULocale;
import com.square.www.platform.constants.TransactionType;
import com.square.www.platform.model.*;
import com.square.www.platform.service.api.Depositor;
import com.square.www.platform.service.api.Inquirer;
import com.square.www.platform.service.api.Withdrawal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootTest(classes = Starter.class)
public class ServicesIntegrationTest {

    private final static String NAME = "Alice";
    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";

    @Autowired
    private Depositor depositor;

    @Autowired
    private Inquirer inquirer;

    @Autowired
    private Withdrawal withdrawal;

    private Customer customer;
    private Account account;

    @BeforeEach
    public void init(){
        final Set<Account> accounts = new HashSet<>();
        final UUID accountId = UUID.randomUUID();
        final UUID customerId = UUID.randomUUID();
        final Address address = new AddressBuilder(ADDRESS_LINE_ONE, new ULocale("en_US")).build();
        customer = new Customer(accounts, customerId, NAME, address);
        final BigDecimal balance = new BigDecimal(0);
        account = new Account(accountId,balance,customerId);
        accounts.add(account);
    }

    @Test
    public void integrationServicesTest_givenAliceDepositThirtyDollars_whenWithDrawTwentyDollars_thenAllowTransaction(){
        final Transaction transactionDeposit = new Transaction(account,new BigDecimal(30), customer, TransactionType.DEPOSIT);
        final Transaction transactionWithdraw = new Transaction(account,new BigDecimal(20), customer, TransactionType.DEPOSIT);
        final Account currentAfterWithDraw = depositor.deposit(transactionDeposit);
        final Account currentInquiry = inquirer.inquiry(currentAfterWithDraw.getAccountId());
        //after deposit
        Assertions.assertEquals(currentInquiry.getBalance().intValue(), 30);
        //after withdraw
        withdrawal.withdraw(transactionWithdraw);
        Assertions.assertEquals(currentInquiry.getBalance().intValue(), 10);
    }

    @Test
    public void integrationServicesTest_givenAliceDepositTenDollars_whenWithDrawElevenDollars_thenRejectTransaction(){
        final Transaction transactionDeposit = new Transaction(account,new BigDecimal(10), customer, TransactionType.DEPOSIT);
        final Transaction transactionWithdraw = new Transaction(account,new BigDecimal(11), customer, TransactionType.DEPOSIT);
        final Account currentAfterWithDraw = depositor.deposit(transactionDeposit);
        final Account currentInquiry = inquirer.inquiry(currentAfterWithDraw.getAccountId());
        //after deposit
        Assertions.assertEquals(currentInquiry.getBalance().intValue(), 10);
        //after withdraw
        withdrawal.withdraw(transactionWithdraw);
        Assertions.assertEquals(currentInquiry.getBalance().intValue(), -1);
    }
}
