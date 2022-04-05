package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.MedicalRecordRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.mapper.MedicalRecordWebMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@Service
@NoArgsConstructor
@AllArgsConstructor

public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordWebMapper medicalRecordWebMapper;


    public void saveAll(List<MedicalRecord> medicalRecordList) {
        medicalRecordRepository.saveAll(medicalRecordList);
    }

    public void deleteAll(List<MedicalRecord> medicalRecords) {
        this.medicalRecordRepository.deleteAll(medicalRecords);
    }

    @Transactional
    public void saveMedicalRecord(MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = medicalRecordWebMapper.toEntity(medicalRecordDto);

        Person byFirstNameAndLastName = personRepository.findByFirstNameAndLastName(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName());

        if (byFirstNameAndLastName == null) {
            throw new IllegalArgumentException("we can't create a medicalRecord for this person because she is not existing");
        } else {
            medicalRecord.setPerson(byFirstNameAndLastName);
        }
        medicalRecordRepository.save(medicalRecord);
    }

    @Transactional
    public void deleteMedicalRecord(String firstName, String lastName) {
        Person byFirstNameAndLastName = personRepository.findByFirstNameAndLastName(firstName, lastName);
        if (byFirstNameAndLastName == null) {
            throw new NoSuchElementException("There is no person called " + firstName + " " + lastName);
        } else {
            List<MedicalRecord> medicalRecords = byFirstNameAndLastName.getMedicalRecord();
            medicalRecordRepository.deleteAll(medicalRecords);
        }
    }

    @Transactional
    public void updateMedicalRecord(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {
        Person byFirstNameAndLastName = personRepository.findByFirstNameAndLastName(firstName, lastName);
        if (byFirstNameAndLastName== null){
            throw new NoSuchElementException("There is no person called " + firstName + " " + lastName);
        }else {
            List<MedicalRecord> medicalRecords = byFirstNameAndLastName.getMedicalRecord();
            // medicalRecords changes from medicalRecordDto
            List<MedicalRecord> medicalRecordList = medicalRecords.stream().map(medicalRecord -> {
                medicalRecord.setBirthdate(medicalRecordDto.getBirthdate());
                medicalRecord.setMedications(medicalRecordDto.getMedications());
                medicalRecord.setAllergies(medicalRecordDto.getAllergies());
                return medicalRecord;
            }).collect(Collectors.toList());
            medicalRecordRepository.saveAll(medicalRecordList);
        }
    }
}



