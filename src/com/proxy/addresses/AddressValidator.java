package com.proxy.addresses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proxy.util.AddressType;
import com.proxy.bean.Address;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AddressValidator {
    private final Logger log = Logger.getLogger("AddressValidator");

    public List<Address> loadAddresses()throws Exception{
        log.info("AddressService :: Entry Method : loadAddresses()");
        List<Address> addressList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileReader reader = new FileReader("src/addresses.json"))
        {
            try {
                JsonNode jsonArray = objectMapper.readTree(reader);
                for (JsonNode element : jsonArray) {
                    Address address = objectMapper.treeToValue(element, Address.class);
                    addressList.add(address);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(FileNotFoundException fileNotFoundException){
            System.out.println(fileNotFoundException.getMessage());
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        log.info("Exit Method : loadAddresses() addressList size :: " + addressList.size());
        return addressList;
    }

    public String prettyPrintAllAddresses(List<Address> addresses) throws Exception {
        StringBuilder prettyPrintAddress = new StringBuilder();
        for(Address address:addresses) {
            prettyPrintAddress.append(prettyPrintAddress(address));
        }
        return prettyPrintAddress.toString();
    }

    public String prettyPrintAddressType(List<Address> addresses, String typeOfAddress) throws Exception {
        log.info("Entry Method : prettyPrintAddressType() typeOfAddress printed " + typeOfAddress);
        StringBuilder prettyPrintAddress = new StringBuilder();
        for(Address address:addresses) {
            if (address.getType().getName().equalsIgnoreCase(typeOfAddress))
                prettyPrintAddress.append(prettyPrintAddress(address));
        }
        return prettyPrintAddress.toString();
    }

    public String prettyPrintAddress(Address address) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        System.out.print(gson.toJson(address));
        return gson.toJson(address);
    }

    public String printValidAddresses(List<Address> addresses) throws Exception {
        StringBuilder prettyPrintValidAddress = new StringBuilder();
        for(Address address:addresses) {
            if(validateAddress(address)) {
                prettyPrintValidAddress.append(prettyPrintAddress(address));
            }
        }
        return prettyPrintValidAddress.toString();
    }

    public boolean validateAddress(Address address){
        String regex = "\\d+";
        boolean isValidAddress = false;
        try {
            log.info("Entry Method : validateAddress() address and country name " + address.getCountry().getName());
            if((!(address.getCountry().getName().equalsIgnoreCase("South Africa"))&&!(address.getCountry().getCode().equalsIgnoreCase("ZA")))&&
                    address.getPostalCode().matches(regex)&&address.getCountry()!=null&&(!(address.getAddressLineDetail().getLine1()!=null||address.getAddressLineDetail().getLine2()!=null))){
                isValidAddress = true;
            }
            if((address.getCountry().getName().equalsIgnoreCase("South Africa")&&address.getCountry().getCode().equalsIgnoreCase("ZA")&&address.getProvinceOrState().getName()!=null)&&
                    address.getPostalCode().matches(regex)&&address.getCountry()!=null&&(address.getAddressLineDetail().getLine1()!=null||address.getAddressLineDetail().getLine2()!=null)){
                isValidAddress = true;
            }
        }catch (Exception ex){
            System.out.println(address.getType().getName()+" in "+address.getCityOrTown()+" in "+address.getCountry().getName() +" is an invalid address.");
        }
        return isValidAddress;
    }


    public static void main(String[] args) throws Exception {
        AddressValidator addressValidator = new AddressValidator();
        List<Address> addressList = addressValidator.loadAddresses();
        addressValidator.prettyPrintAllAddresses(addressList);
        addressValidator.prettyPrintAddressType(addressList, AddressType.PHYSICAL_ADDRESS);
        addressValidator.prettyPrintAddressType(addressList, AddressType.POSTAL_ADDRESS);
        addressValidator.prettyPrintAddressType(addressList, AddressType.BUSINESS_ADDRESS);
        addressValidator.printValidAddresses(addressList);
    }
}