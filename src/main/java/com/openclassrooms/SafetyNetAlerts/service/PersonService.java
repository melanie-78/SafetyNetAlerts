package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    void saveAll(List<Person> personList);
    Person findByFirstNameAndLastName(String firstName, String lastName);
    void savePerson(PersonDto personDto);
    void update(String firstName, String lastName, PersonUpdateDto personUpdateDto);
    void deletePerson(String firstName, String lastName);
}
