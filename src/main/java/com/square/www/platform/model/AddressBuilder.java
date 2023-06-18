package com.square.www.platform.model;

import com.ibm.icu.util.ULocale;

public class AddressBuilder {

    private final String addressLineOne;
    private final ULocale locale;
    private String addressLineTwo;

    public AddressBuilder(final String addressLineOne, final ULocale locale) {
        this.addressLineOne = addressLineOne;
        this.locale = locale;
    }

    public AddressBuilder withAddressLineTwo(final String addressLineTwo){
        this.addressLineTwo = addressLineTwo;
        return this;
    }

    public Address build(){
        return new Address(addressLineOne, locale, addressLineTwo);
    }
}
