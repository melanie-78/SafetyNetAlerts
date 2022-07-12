package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.web.dto.FloodDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FloodService {
    List<FloodDto> getFlood(List<Integer> stations);
}
