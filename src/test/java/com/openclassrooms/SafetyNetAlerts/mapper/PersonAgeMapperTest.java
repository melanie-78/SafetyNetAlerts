package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonAgeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class PersonAgeMapperTest {
    @InjectMocks
    private PersonAgeMapper personAgeMapper;

    @Mock
    CalculateAge calculateAge;

    @Test
    public void toDtoTest(){
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        MedicalRecord medicalRecord = new MedicalRecord(null,"03/06/1984",Arrays.asList("pharmacol:5000mg"), Arrays.asList(), null);
        p.setMedicalRecord(Arrays.asList(medicalRecord));
        PersonAgeDto expected = new PersonAgeDto("John", "Boyd", 37);
        int age = 37;
        when(calculateAge.calculateAge(any(), any())).thenReturn(37);

        PersonAgeDto actual = personAgeMapper.toDto(p);

        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAge(), actual.getAge());
    }
}
