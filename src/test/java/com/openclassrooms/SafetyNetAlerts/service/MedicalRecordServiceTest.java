package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.MedicalRecordRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.mapper.MedicalRecordWebMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class MedicalRecordServiceTest {
    @InjectMocks
    private MedicalRecordService ms;

    @Mock
    MedicalRecordWebMapper medicalRecordWebMapper;
    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @Test
    public void saveMedicalRecordTest(){
        //GIVEN
        String firstName = "Jacob";
        String lastName = "Boyd";
        MedicalRecordDto medicalRecordDto= new MedicalRecordDto("Jacob","Boyd","03/06/1989",Arrays.asList("pharmacol:5000mg"), new ArrayList());
        MedicalRecord medicalRecord = new MedicalRecord();
        Person person = new Person();
        when(medicalRecordWebMapper.toEntity(medicalRecordDto)).thenReturn(medicalRecord);
        when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(person);

        //WHEN
        ms.saveMedicalRecord(medicalRecordDto);

        //THEN
        verify(medicalRecordRepository,Mockito.times(1)).save(any(MedicalRecord.class));
    }

   @Test
    public void saveMedicalRecordTestThrowsException(){
        //GIVEN
        String firstName = "Jacob";
        String lastName = "Boyd";
        MedicalRecordDto medicalRecordDto= new MedicalRecordDto("Jacob","Boyd","03/06/1989",Arrays.asList("pharmacol:5000mg"), new ArrayList());
        MedicalRecord medicalRecord = new MedicalRecord();
        when(medicalRecordWebMapper.toEntity(medicalRecordDto)).thenReturn(medicalRecord);
        when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);

        //WHEN
        assertThrows(IllegalArgumentException.class, ()->ms.saveMedicalRecord(medicalRecordDto));

        //THEN
        verify(medicalRecordWebMapper,Mockito.times(1)).toEntity(any(MedicalRecordDto.class));
        verify(personRepository, Mockito.times(1)).findByFirstNameAndLastName(any(String.class),any(String.class));
    }

   @Test
    public void deleteMedicalRecordTest(){
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Boyd");
        p.setMedicalRecord(Arrays.asList(new MedicalRecord(), new MedicalRecord()));
        when(personRepository.findByFirstNameAndLastName("John","Boyd")).thenReturn(p);
        doNothing().when(medicalRecordRepository).deleteAll(any());

        ms.deleteMedicalRecord("John","Boyd");

        verify(personRepository,Mockito.times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
        verify(medicalRecordRepository, Mockito.times(1)).deleteAll(anyList());
    }

    @Test
    public void deleteMedicalRecordThrowsExceptionTest(){
        String firstName = "Jacob";
        String lastName = "Boyd";
        when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);

        assertThrows(NoSuchElementException.class,()->ms.deleteMedicalRecord(firstName,lastName));

        verify(personRepository,Mockito.times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
    }

   @Test
    public void updateMedicalRecordTest(){
        MedicalRecordDto medicalRecordDto= new MedicalRecordDto("Tony","Cooper","03/06/1994",Arrays.asList("hydrapermazol:300mg", "dodoxadin:30mg"), Arrays.asList("shellfish"));
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Boyd");
        p.setMedicalRecord(Arrays.asList(new MedicalRecord(), new MedicalRecord()));
        when(personRepository.findByFirstNameAndLastName("John","Boyd")).thenReturn(p);

        ms.updateMedicalRecord("John","Boyd",medicalRecordDto);

        verify(personRepository,Mockito.times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
        verify(medicalRecordRepository, Mockito.times(1)).saveAll(anyList());

    }


    @Test
    public void updateMedicalRecordThrowsExceptionTest(){
        String firstName = "Jacob";
        String lastName = "Boyd";
        MedicalRecordDto medicalRecordDto= new MedicalRecordDto("Jacob","Boyd","03/06/1989",Arrays.asList("pharmacol:5000mg"), Arrays.asList());
        when(personRepository.findByFirstNameAndLastName(firstName,lastName)).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->ms.updateMedicalRecord(firstName,lastName,medicalRecordDto));

        verify(personRepository,Mockito.times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
    }
}
