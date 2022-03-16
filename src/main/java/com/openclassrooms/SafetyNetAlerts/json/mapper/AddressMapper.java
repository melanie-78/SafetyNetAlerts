package com.openclassrooms.SafetyNetAlerts.json.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;

public class AddressMapper {
    /**
     *
     * @param personDto an object that corresponds to the information of the json file
     *
     * @return an address type associated to a person saved in H2
     */

    public static Address toEntity (PersonDto personDto){
        Address address = new Address();

        address.setCity(personDto.getCity());
        address.setLabel(personDto.getAddress());
        address.setZip(personDto.getZip());

        return address;
    }
}
