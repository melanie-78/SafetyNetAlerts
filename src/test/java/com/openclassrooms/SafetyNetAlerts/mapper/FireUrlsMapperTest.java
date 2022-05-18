package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireUrlsDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FireUrlsMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class FireUrlsMapperTest {
    @InjectMocks
    private FireUrlsMapper fireUrlsMapper;

    @Mock
    CalculateAge calculateAge;

    @Test
    public void toDtoTest(){
        Person person = new Person(null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com",null);
        MedicalRecord medicalRecord = new MedicalRecord(null, "03/06/1984",Arrays.asList("pharmacol:5000mg"), Arrays.asList(), null);
        person.setMedicalRecord(Arrays.asList(medicalRecord));
        FireUrlsDto expected = new FireUrlsDto("Boyd", 37, "841-874-6512", Arrays.asList("pharmacol:5000mg"));
        int age = 37;
        when(calculateAge.calculateAge(any(), any())).thenReturn(age);

        FireUrlsDto actual = fireUrlsMapper.toDto(person);

        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertIterableEquals(expected.getMedications(), actual.getMedications());
    }


}
