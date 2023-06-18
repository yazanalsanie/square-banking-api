package com.square.www.platform.model;

import com.ibm.icu.util.ULocale;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Address implements Serializable {

    /** Explicit serialVersionUID for interoperability. **/
    private static final long serialVersionUID = 1L;

    private final String addressLineOne;
    private final ULocale locale;
    private final String addressLineTwo;
    private final String country;

    Address(final String addressLineOne, final ULocale locale, final String addressLineTwo) {
        this.addressLineOne = addressLineOne;
        this.locale = locale;
        this.addressLineTwo = addressLineTwo;
        this.country = setCountry(locale);
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public ULocale getLocale() {
        return locale;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public String getCountry() {
        return country;
    }

    private String setCountry(ULocale locale) {
        return locale.getDisplayCountry();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        return new EqualsBuilder().append(getAddressLineOne(),
                address.getAddressLineOne()).append(getLocale(), address.getLocale()).append(getAddressLineTwo(),
                address.getAddressLineTwo()).append(getCountry(), address.getCountry()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getAddressLineOne()).append(getLocale()).toHashCode();
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLineOne='" + addressLineOne + '\'' +
                ", locale=" + locale +
                ", addressLineTwo='" + addressLineTwo + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
