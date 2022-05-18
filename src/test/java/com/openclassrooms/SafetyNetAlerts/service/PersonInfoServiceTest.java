package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.PersonInfoRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonInfoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {
    @InjectMocks
    private PersonInfoService personInfoService;

    @Mock
    PersonInfoMapper personInfoMapper;
    @Mock
    PersonInfoRepository personInfoRepository;

    @Test
    public void getPersonInfoThrowsExceptionTest(){
        String lastName = "Boyd";
        List<Person> pList = new ArrayList<>();

        when(personInfoRepository.findAllByLastName("Boyd")).thenReturn(pList);

        assertThrows(NoSuchElementException.class, ()->personInfoService.getPersonInfo(lastName));
    }

    @Test
    public void getPersonInfoTest(){
        String lastName = "Cadigan";
        Person p = new Person(null,"Eric","Cadigan",new Address(null, "951 LoneTree Rd", "Culver", "97451", null, null ), "841-874-6512", "gramps@email.com", null );
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate("08/06/1945");
        medicalRecord.setMedications(Arrays.asList("tradoxidine:400mg"));
        medicalRecord.setAllergies(new ArrayList());
        p.setMedicalRecord(Arrays.asList(medicalRecord));

        List<Person> pList = Arrays.asList(p);

        PersonInfoDto personInfoDto = new PersonInfoDto("Cadigan","Eric","951 LoneTree Rd","gramps@email.com",76, Arrays.asList("tradoxidine:400mg"), new ArrayList());
        List<PersonInfoDto> expected = Arrays.asList(personInfoDto);
        when(personInfoRepository.findAllByLastName(lastName)).thenReturn(pList);
        when(personInfoMapper.toDto(p)).thenReturn(personInfoDto);

        List<PersonInfoDto> actual = personInfoService.getPersonInfo(lastName);

        assertEquals(expected.size(), actual.size());
    }
}
