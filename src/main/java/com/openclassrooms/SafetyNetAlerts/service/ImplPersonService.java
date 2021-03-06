package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonUpdateDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonWebMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ImplPersonService implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ImplMedicalRecordService medicalRecordService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonWebMapper personWebMapper;

    @Override
    public void saveAll(List<Person> personList) {
        personRepository.saveAll(personList);
    }

    @Override
    public Person findByFirstNameAndLastName(String firstName, String lastName) {
        return this.personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    @Transactional
    public void savePerson(PersonDto personDto) {
        Person person= personWebMapper.toEntity(personDto);

        Address byLabel = addressRepository.findByLabel(personDto.getAddress());

        if(byLabel != null){
            person.setAddress(byLabel);
        }else{
            Address savedAddress = new Address();
            savedAddress.setLabel(personDto.getAddress());
            savedAddress.setCity(personDto.getCity());
            savedAddress.setZip(personDto.getZip());

            addressRepository.save(savedAddress );
            person.setAddress(savedAddress);
        }
        personRepository.save(person);
    }

    @Override
    @Transactional
    public void update(String firstName, String lastName, PersonUpdateDto personUpdateDto) {
        Person person = personRepository.findByFirstNameAndLastName(firstName,lastName);

        if(person == null){
            throw new NoSuchElementException("There is no person called " + firstName + " " + lastName);
        }else{
            person.setPhone(personUpdateDto.getPhone());
            person.setEmail(personUpdateDto.getEmail());

            Address byLabel = addressRepository.findByLabel(personUpdateDto.getAddress());
            if(byLabel != null){
                person.setAddress(byLabel);
            }
            Address savedAddress = new Address();

            savedAddress.setLabel(personUpdateDto.getAddress());
            savedAddress.setCity(personUpdateDto.getCity());
            savedAddress.setZip(personUpdateDto.getZip());

            addressRepository.save(savedAddress);
            person.setAddress(savedAddress);

            personRepository.save(person);
        }
    }

    @Override
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


