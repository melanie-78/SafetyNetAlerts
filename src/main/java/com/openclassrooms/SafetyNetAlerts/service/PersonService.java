package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.PersonWebMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@Service
@NoArgsConstructor
@AllArgsConstructor

public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private com.openclassrooms.SafetyNetAlerts.json.mapper.PersonMapper personMapper;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonWebMapper personWebMapper;

    public void saveAll(List<Person> personList){
        personRepository.saveAll(personList);
    }

    public Person findByFirstNameAndLastName(String firstName, String lastName){
        return this.personRepository.findByFirstNameAndLastName(firstName, lastName);
    }


    public List<PersonDto> getAllPersons(){
     List<PersonDto> result = new ArrayList<>();
     Iterable<Person> all = personRepository.findAll();
     all.forEach(person -> result.add(PersonWebMapper.toDto(person)));
     return result;
    }

    @Transactional
    public void savePerson(PersonDto personDto) {

        Person person= personWebMapper.toEntity(personDto);

        Address byLabel = addressRepository.findByLabel(personDto.getAddress());

        if(byLabel != null){
            person.setAddress(byLabel);
        }else{
            Address savedAddress = addressRepository.save(byLabel);
            person.setAddress(savedAddress);
        }
     personRepository.save(person);
    }

    @Transactional
    public void update(String firstName, String lastName, PersonDto personDto) {
        Person person = personRepository.findByFirstNameAndLastName(firstName,lastName);

      if(person == null){
          throw new NoSuchElementException("There is no person called " + firstName + " " + lastName);
      }else{
          person.setPhone(personDto.getPhone());
          person.setEmail(personDto.getEmail());

          Address byLabel = addressRepository.findByLabel(personDto.getAddress());
          if(byLabel != null){
              person.setAddress(byLabel);
          }else{
              Address savedAddress = addressRepository.save(byLabel);
              person.setAddress(savedAddress);
          }
          personRepository.save(person);
      }
    }

    @Transactional
    public void deletePerson(String firstName, String lastName) {
     if (!personRepository.existsByFirstNameAndLastName(firstName, lastName)) {
         throw new NoSuchElementException("There is no person called" + firstName + " " + lastName);
     }
     List<Person> personList = personRepository.findAllByFirstNameAndLastName(firstName, lastName);
     List<MedicalRecord> medicalRecords = personList.stream()
             .map(person -> person.getMedicalRecord())
             .flatMap(medicalRecordList -> medicalRecordList.stream())
             .collect(Collectors.toList());
     medicalRecordService.deleteAll(medicalRecords);
     personRepository.deleteAllByFirstNameAndLastName(firstName,lastName);
    }
}


