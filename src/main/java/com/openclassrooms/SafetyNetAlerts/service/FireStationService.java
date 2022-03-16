package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.data.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class FireStationService {
    private FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    public void saveAll(List<FireStation> fireStationList){
        fireStationRepository.saveAll(fireStationList);
    }
}
