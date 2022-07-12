package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicalRecordService {
    void saveAll(List<MedicalRecord> medicalRecordList);
    void deleteAll(List<MedicalRecord> medicalRecords);
    void saveMedicalRecord(MedicalRecordDto medicalRecordDto);
    void deleteMedicalRecord(String firstName, String lastName);
    void updateMedicalRecord(String firstName, String lastName, MedicalRecordDto medicalRecordDto);
}
