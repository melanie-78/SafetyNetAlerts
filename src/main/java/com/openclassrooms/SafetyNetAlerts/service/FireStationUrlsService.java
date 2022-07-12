package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FireStationUrlsService {
    List<FireStationUrlsDto> getFireStationUrls(String station);
}
