package com.square.www.platform.model;

import com.square.www.platform.constants.TransactionType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Transaction implements Serializable {

    /** Explicit serialVersionUID for interoperability. **/
    private static final long serialVersionUID = 1L;

    private final ZonedDateTime timeStamp;
    private final Account account;
    private final BigDecimal value;
    private final Customer customer;
    private final TransactionType transactionType;

    public Transaction(final Account account, final BigDecimal value, final Customer customer, TransactionType transactionType) {
        this.timeStamp = ZonedDateTime.now();
        this.account = account;
        this.value = value;
        this.customer = customer;
        this.transactionType = transactionType;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TransactionType getTransactionType() {return transactionType;}

    public void setBalance(final BigDecimal value) {
        this.account.setBalance(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        return new EqualsBuilder().append(getTimeStamp(), that.getTimeStamp()).append(getAccount(), that.getAccount()).append(getValue(), that.getValue()).append(getCustomer(), that.getCustomer()).append(getTransactionType(), that.getTransactionType()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getTimeStamp()).append(getAccount()).append(getValue()).append(getCustomer()).append(getTransactionType()).toHashCode();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "timeStamp=" + timeStamp +
                ", account=" + account +
                ", value=" + value +
                ", customer=" + customer +
                ", transactionType=" + transactionType +
                '}';
    }

}
