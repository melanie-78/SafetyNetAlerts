package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.data.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.mapper.PersonMapper;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PersonService {

    private PersonRepository personRepository;
    private PersonMapper personMapper;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void saveAll(List<Person> personList){
        personRepository.saveAll(personList);
    }

    public Person findByFirstNameAndLastName(String firstName, String lastName){
        return this.personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

}


