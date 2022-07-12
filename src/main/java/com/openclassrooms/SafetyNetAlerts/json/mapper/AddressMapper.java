package com.openclassrooms.SafetyNetAlerts.json.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    /**
     *
     * @param personDto an object that corresponds to the information of the json file
     *
     * @return an address type associated to a person saved in H2
     */

    public Address toEntity (PersonDto personDto){
        Address address = new Address();

        address.setCity(personDto.getCity());
        address.setLabel(personDto.getAddress());
        address.setZip(personDto.getZip());

        return address;
    }
}
