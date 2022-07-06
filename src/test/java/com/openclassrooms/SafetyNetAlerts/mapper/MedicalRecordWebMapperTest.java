package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.web.mapper.MedicalRecordWebMapper;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonWebMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordWebMapperTest {

    @InjectMocks
    private MedicalRecordWebMapper medicalRecordWebMapper;

    @Test
    public void toEntityTest(){
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto("John", "Boyd", "03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));
        MedicalRecord expected = new MedicalRecord(null,"03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"), null );

        MedicalRecord actual = medicalRecordWebMapper.toEntity(medicalRecordDto);

        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getMedications(), actual.getMedications());
        assertEquals(expected.getAllergies(), actual.getAllergies());
    }
}
