package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonInfoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class PersonInfoMapperTest {
    @InjectMocks
    private PersonInfoMapper personInfoMapper;

    @Mock
    CalculateAge calculateAge;

   @Test
    public void toDtoTest(){
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        MedicalRecord medicalRecord = new MedicalRecord(null,"03/06/1984",Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"), null);
        p.setMedicalRecord(Arrays.asList(medicalRecord));
        PersonInfoDto expected = new PersonInfoDto("Boyd", "John","1509 Culver St", "jaboyd@email.com", 37, Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));
        when(calculateAge.calculateAge(any(), any())).thenReturn(37);

        PersonInfoDto actual = personInfoMapper.toDto(p);


        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getMedications(), actual.getMedications());
        assertEquals(expected.getAllergies(), actual.getAllergies());
    }
}
