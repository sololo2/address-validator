package com.proxy.test;

import com.proxy.addresses.AddressValidator;
import org.junit.Test;
import static org.junit.Assert.*;

public class AddressValidatorTests {
    AddressValidator addressValidator = new AddressValidator();
    public  static final String POSTAL_ADDRESS = "Postal Address";
    public  static final String PHYSICAL_ADDRESS = "Physical Address";
    public  static final String BUSINESS_ADDRESS = "Business Address";

    @Test
    public void LoadAllAddressesTest() throws Exception {
        assertEquals(3,addressValidator.loadAddresses().size());
    }

    @Test
    public void prettyPrintPhysicalAddressTest() throws Exception {
        String testString = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"type\": {\n" +
                "    \"code\": \"1\",\n" +
                "    \"name\": \"Physical Address\"\n" +
                "  },";
        assertTrue(addressValidator.prettyPrintAddressType(addressValidator.loadAddresses(),PHYSICAL_ADDRESS).contains(testString));
    }

    @Test
    public void prettyPrintPostalAddressTest() throws Exception{
        String testString = "{\n" +
                "  \"id\": \"2\",\n" +
                "  \"type\": {\n" +
                "    \"code\": \"2\",\n" +
                "    \"name\": \"Postal Address\"\n" +
                "  },";
        assertTrue(addressValidator.prettyPrintAddressType(addressValidator.loadAddresses(),POSTAL_ADDRESS).contains(testString));
    }

    @Test
    public void prettyPrintBusinessAddressTest() throws Exception{
        String testString = "{\n" +
                "  \"id\": \"3\",\n" +
                "  \"type\": {\n" +
                "    \"code\": \"5\",\n" +
                "    \"name\": \"Business Address\"\n" +
                "  },";
        assertTrue(addressValidator.prettyPrintAddressType(addressValidator.loadAddresses(),BUSINESS_ADDRESS).contains(testString));

    }

    @Test
    public void firstAddressInTheListIsValid() throws Exception {
        assertTrue(addressValidator.validateAddress(addressValidator.loadAddresses().get(0)));
    }

    @Test
    public void secondAddressInTheListIsInValid() throws Exception {
        assertFalse(addressValidator.validateAddress(addressValidator.loadAddresses().get(1)));
    }

    @Test
    public void thirdAddressInTheListIsInValid() throws Exception {
        assertFalse(addressValidator.validateAddress(addressValidator.loadAddresses().get(2)));
    }
}