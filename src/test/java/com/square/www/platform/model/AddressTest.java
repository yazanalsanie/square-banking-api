package com.square.www.platform.model;

import com.ibm.icu.util.ULocale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AddressTest {

    private final static String ADDRESS_LINE_ONE = "New York. NY. 10017. USA. Commercial Building. 778155 Appraisal";
    private final static String ADDRESS_DIFF = "Chicago. Commercial Building. 778155 Appraisal";
    private final static ULocale locale = new ULocale("en_US");

    private Address addressOne;
    private Address addressTwo;
    private Address addressThree;

    @BeforeEach
    public void init(){
        addressOne = new AddressBuilder(ADDRESS_LINE_ONE, locale).build();
        addressTwo = new AddressBuilder(ADDRESS_LINE_ONE, locale).build();
        addressThree = new AddressBuilder(ADDRESS_DIFF, locale).build();
    }

    @Test
    public void testEquality(){
        Assertions.assertEquals(addressOne, addressTwo);
        Assertions.assertNotEquals(addressOne, addressThree);
        Assertions.assertEquals(addressOne.toString(), addressTwo.toString());
        Assertions.assertNotEquals(addressOne.toString(), addressThree.toString());
    }
}
