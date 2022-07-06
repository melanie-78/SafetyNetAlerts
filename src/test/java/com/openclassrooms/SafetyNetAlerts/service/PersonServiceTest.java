package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonUpdateDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonWebMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)


public class PersonServiceTest {
    @InjectMocks
    private PersonService ps;

    @Mock
    PersonWebMapper personWebMapper;
    @Mock
    AddressRepository addressRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordService medicalRecordService;

    @Test
    public void savePersonWithAddressExistingTest() {
        //GIVEN
        PersonDto personDto = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person person = new Person();
        when(personWebMapper.toEntity(personDto)).thenReturn(person);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(any(Address.class));

        //WHEN
        ps.savePerson(personDto);

        //THEN
        verify(personRepository, Mockito.times(1)).save(any(Person.class));
    }

    @Test
    public void savePersonWithoutAddressExistingTest() {
        //GIVEN
        PersonDto personDto = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person person = new Person();
        when(personWebMapper.toEntity(personDto)).thenReturn(person);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(null);
        when(addressRepository.save(any())).thenReturn(null);
        when(personRepository.save(any())).thenReturn(null);

        //WHEN
        ps.savePerson(personDto);

        //THEN
        verify(addressRepository, Mockito.times(1)).save(any(Address.class));
        verify(personRepository, Mockito.times(1)).save(any(Person.class));
    }

    @Test
    public void updateWithExistingAddressTest() {
        //GIVEN
        String firstName = "Jacob";
        String lastName = "Boyd";
        PersonUpdateDto personUpdateDto = new PersonUpdateDto("1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com");
        Person person = new Person();
        Address address = new Address();
        when(personRepository.findByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(person);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(address);

        //WHEN
        ps.update(firstName, lastName, personUpdateDto);

        //THEN
        verify(personRepository, Mockito.times(1)).save(any(Person.class));
        verify(addressRepository, Mockito.times(1)).findByLabel(any(String.class));
    }

    @Test
    public void updateWithoutExistingAddressTest() {
        String firstName = "John";
        String lastName = "Boyd";
        PersonUpdateDto personUpdateDto = new PersonUpdateDto("1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person person = new Person();
        when(personRepository.findByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(person);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(null);

        ps.update(firstName, lastName, personUpdateDto);

        verify(personRepository, Mockito.times(1)).save(any(Person.class));
        ;
        verify(addressRepository, Mockito.times(1)).save(any(Address.class));
    }

    @Test
    public void updateThrowsException() {
        String firstName = "John";
        String lastName = "Boyd";
        PersonUpdateDto personUpdateDto = new PersonUpdateDto("1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        when(personRepository.findByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> ps.update(firstName, lastName, personUpdateDto));

        verify(personRepository, Mockito.times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
    }

    @Test
    public void deletePersonTest() {
        // GIVEN prépare le test :mock les appels vers les classes externes
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Boyd");
        p.setMedicalRecord(Arrays.asList(new MedicalRecord(), new MedicalRecord()));

        when(personRepository.existsByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(true);
        when(personRepository.findAllByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(Collections.singletonList(p));
        doNothing().when(medicalRecordService).deleteAll(any());
        doNothing().when(personRepository).deleteAllByFirstNameAndLastName(any(), any());

        // WHEN execute ce qu'on veut tester
        ps.deletePerson("John", "Boyd");

        // THEN vérifie que la fonction a bien fait son boulot (supprimer la personne)
        verify(medicalRecordService, Mockito.times(1)).deleteAll(anyList());
        verify(personRepository, Mockito.times(1)).deleteAllByFirstNameAndLastName(any(String.class), any(String.class));
    }

    @Test
    public void deletePersonThrowsExceptionTest() {
        String firstName = "John";
        String lastName = "Boyd";
        when(personRepository.existsByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> ps.deletePerson(firstName, lastName));

        verify(personRepository, Mockito.times(1)).existsByFirstNameAndLastName(any(String.class), any(String.class));
    }
}
