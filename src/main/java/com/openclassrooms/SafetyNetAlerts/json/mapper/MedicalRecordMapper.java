package com.openclassrooms.SafetyNetAlerts.json.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.PersonService;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {
    /**
     *
     * @param medicalRecordDto an object that corresponds to the information of the json file
     *
     * @return a medicalRecord type associated to a person saved in H2
     */

    public MedicalRecord toEntity(MedicalRecordDto medicalRecordDto, PersonService personService) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate(medicalRecordDto.getBirthdate());
        medicalRecord.setMedications(medicalRecordDto.getMedications());
        medicalRecord.setAllergies(medicalRecordDto.getAllergies());
        Person byFirstNameAndLastName = personService.findByFirstNameAndLastName(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName());
        medicalRecord.setPerson(byFirstNameAndLastName);
        return medicalRecord;
    }
}
