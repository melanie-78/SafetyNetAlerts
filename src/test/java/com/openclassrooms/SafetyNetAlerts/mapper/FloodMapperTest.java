package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.CalculateAge;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FloodMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class FloodMapperTest {
    @InjectMocks
    private FloodMapper floodMapper;

    @Mock
    CalculateAge calculateAge;

    @Test
    public void toDtoTest(){
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        MedicalRecord medicalRecord = new MedicalRecord(null,"03/06/1984", Arrays.asList("pharmacol:5000mg"), Arrays.asList(), null);
        p.setMedicalRecord(Arrays.asList(medicalRecord));
        int age = 37;
        FloodPersonDto expected = new FloodPersonDto("Boyd", "841-874-6512", "1509 Culver St", age, Arrays.asList("pharmacol:5000mg"), Arrays.asList());
        when(calculateAge.calculateAge(any(), any())).thenReturn(37);

        FloodPersonDto actual = floodMapper.toDto(p);

        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getMedications(), actual.getMedications());
        assertEquals(expected.getAllergies(), actual.getAllergies());
    }
}
