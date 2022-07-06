package com.openclassrooms.SafetyNetAlerts.web.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonWebMapper {
    public static PersonDto toDto(Person person) {
        PersonDto personDto = new PersonDto();

        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setCity(person.getAddress().getCity());
        personDto.setEmail(person.getEmail());
        personDto.setZip(person.getAddress().getZip());
        personDto.setPhone(person.getPhone());
        personDto.setAddress(person.getAddress().getLabel());

        return personDto;
    }

    public Person toEntity(PersonDto personDto) {
        Person person = new Person();

        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setPhone(personDto.getPhone());
        person.setEmail(personDto.getEmail());

        Address address = new Address();
        address.setLabel(personDto.getAddress());
        address.setCity(personDto.getCity());
        address.setZip(personDto.getZip());

        person.setAddress(address);

        return person;
    }
}


