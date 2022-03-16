package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.data.MedicalRecordRepository;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository){
            this.medicalRecordRepository= medicalRecordRepository;
    }

    public void saveAll(List<MedicalRecord> medicalRecordList){
        medicalRecordRepository.saveAll(medicalRecordList);
    }
}
