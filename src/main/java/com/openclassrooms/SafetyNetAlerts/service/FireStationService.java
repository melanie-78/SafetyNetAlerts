package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FireStationService {
    void saveAll(List<FireStation> fireStationList);
    void saveFireStation(FireStationDto fireStationDto);
    void deleteFireStation (FireStationDto fireStationDto);
    void updateFireStation(FireStationUpdateDto fireStationUpdateDto);
}
