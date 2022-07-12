package com.openclassrooms.SafetyNetAlerts.mapper;


import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.json.mapper.MedicalRecordMapper;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.ImplPersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class MedicalRecordMapperTest {
    @InjectMocks
    private MedicalRecordMapper medicalRecordMapper;

    @Mock
    ImplPersonService personService;

   @Test
    public void toEntityTest(){
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto("Jacob","Boyd","03/06/1989",Arrays.asList("pharmacol:5000mg"),new ArrayList<>());
        MedicalRecord expected = new MedicalRecord(null, "03/06/1989",Arrays.asList("pharmacol:5000mg"), Arrays.asList(), null);
        Person person = new Person();
        person.setFirstName("Jacob");
        person.setLastName("Boyd");
        when(personService.findByFirstNameAndLastName(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName())).thenReturn(person);

        MedicalRecord actual = medicalRecordMapper.toEntity(medicalRecordDto);

        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getMedications(), actual.getMedications());
        assertEquals(expected.getAllergies(), actual.getAllergies());
        verify(personService, Mockito.times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
    }

}
