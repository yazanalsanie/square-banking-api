package com.square.www.platform.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class Customer implements Serializable {

    /** Explicit serialVersionUID for interoperability. **/
    private static final long serialVersionUID = 1L;

    private final Set<Account> accounts;
    private final UUID customerId;
    private final String name;
    private final Address address;

    public Customer(final Set<Account> accounts, final UUID customerId, final String name, final Address address) {
        this.accounts = accounts;
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return new EqualsBuilder().append(getAccounts(), customer.getAccounts()).append(getCustomerId(), customer.getCustomerId()).append(getName(), customer.getName()).append(getAddress(), customer.getAddress()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getCustomerId()).toHashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "accounts=" + accounts +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
