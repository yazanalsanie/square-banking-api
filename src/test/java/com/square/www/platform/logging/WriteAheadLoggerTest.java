package com.square.www.platform.logging;

import com.ibm.icu.util.ULocale;
import com.square.www.platform.constants.TransactionType;
import com.square.www.platform.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WriteAheadLoggerTest {

    private final static String TEST_PATH= "src/test/resources/logging/wal.txt";
    private final static String NAME = "Alice";
    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";
    private final static String INVALID_PATH = "invalidPath";

    private WriteAheadLogger wal;
    private Customer customer;
    private Account account;
    private BigDecimal value;
    private TransactionType transactionType;

    @BeforeEach
    public void init(){
        final Set<Account> accounts = new HashSet<>();
        final UUID accountId = UUID.randomUUID();
        final UUID customerId = UUID.randomUUID();

        final Address address = new AddressBuilder(ADDRESS_LINE_ONE, new ULocale("en_US")).build();
        customer = new Customer(accounts, customerId, NAME, address);
        final BigDecimal balance = new BigDecimal(100);
        account = new Account(accountId,balance,customerId);
        value = new BigDecimal(50);
        transactionType = TransactionType.WITHDRAW;
        accounts.add(account);
        wal = new WriteAheadLogger(TEST_PATH, true);

    }

    @Test
    public void givenWAL_whenInvokedWithValidTransaction_thenWalTxtIsUpdated() throws IOException {
        final Transaction expected = new Transaction(account, value, customer, transactionType);
        final String transactionExpected = expected.toString();
        wal.logTransactionOnFile(transactionExpected);
        final String actual = Files.readString(Path.of(TEST_PATH));
        assertEquals(transactionExpected.trim(), actual.trim());
    }

    @Test
    public void givenInvalidWALInstance_whenInvokedWithValidTransaction_thenRunTimeExceptionThatWrapsOriginalExceptionIsThrown(){
        final Transaction expected = new Transaction(account, value, customer, transactionType);
        final String transactionExpected = expected.toString();
        final WriteAheadLogger withWrongPath = new WriteAheadLogger(INVALID_PATH, true);
        assertThrows(RuntimeException.class, () -> withWrongPath.logTransactionOnFile(transactionExpected));
    }

    @Test
    public void givenWAL_whenInvokedWithLoggerDisabled_thenWalFileWillBeEmpty() throws IOException {
        final String transactionExpected = "";
        final WriteAheadLogger withDisabledLogger = new WriteAheadLogger(TEST_PATH, false);
        withDisabledLogger.logTransactionOnFile(transactionExpected);
        final String actual = Files.readString(Path.of(TEST_PATH));
        assertEquals(transactionExpected.trim(), actual.trim());
    }

    @AfterEach
    public void cleanup() throws FileNotFoundException {
        //clean up the file after each test is executed - this is important to keep your tests around one transaction / one test at a time
        PrintWriter pw = new PrintWriter(TEST_PATH);
        pw.close();
    }
}
