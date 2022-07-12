package com.openclassrooms.SafetyNetAlerts.json.mapper;


import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    @Autowired
    private AddressMapper addressMapper;
    /**
     * the objective of this method is to transform resource data into data stored in the H2
     *
     * this must be a protection for our model
     *
     * @param personDto an object that corresponds to the information of the json file
     * @return a person type saved in the H2
     */

    public Person toEntity(PersonDto personDto) {
        Person person = new Person();

        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setPhone(personDto.getPhone());
        person.setEmail(personDto.getEmail());
        person.setAddress(addressMapper.toEntity(personDto));

        return person;
    }

}