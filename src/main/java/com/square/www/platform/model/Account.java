package com.square.www.platform.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Account implements Serializable {

    /** Explicit serialVersionUID for interoperability. **/
    private static final long serialVersionUID = 1L;

    private final UUID accountId;
    private final UUID customerId;
    private BigDecimal balance;

    public Account(final UUID accountId, final BigDecimal balance, final UUID customerId) {
        this.accountId = accountId;
        this.balance = balance;
        this.customerId = customerId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UUID getCustomer() {
        return customerId;
    }

    public void setBalance(final BigDecimal value) {
        this.balance = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        return new EqualsBuilder().append(getAccountId(), account.getAccountId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getAccountId()).toHashCode();
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", customerId=" + customerId +
                '}';
    }
}
