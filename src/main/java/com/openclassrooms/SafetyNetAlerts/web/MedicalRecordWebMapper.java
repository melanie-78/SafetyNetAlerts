package com.openclassrooms.SafetyNetAlerts.web;

import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordWebMapper {

    public MedicalRecord toEntity(MedicalRecordDto medicalRecordDto){

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setBirthdate(medicalRecordDto.getBirthdate());
        medicalRecord.setMedications(medicalRecordDto.getMedications());
        medicalRecord.setAllergies(medicalRecordDto.getAllergies());

        return medicalRecord;
    }
}
